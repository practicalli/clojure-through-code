(ns clojure-through-code.10-changing-state)


;;
;; Changing state in Clojure

;; The most common way to manage changing state in Clojure is the atom.

;; Essentially an atom is a box that can hold any other clojure data.  when you change the atom contents, you are essentially replacing one immutable value with another.

;; An atom is a mutable container for any valid data in Clojure.  So you can put numbers, strings and collections into an atom.

;; Unlike numbers, strings and collections, the contents of an atom changes when you use a function that changes a value or collection.

;; However, the value or collection itself is immutable, so does not change. Its the atom that shows you a different value.


;; Using fictional characters to show atoms in action
;; (work in progress)

(def human-characters (atom []))
(def mutant-characters (atom []))

(defn add-human
  [name]
  (swap! human-characters conj name))

(defn add-mutant
  [mutant]
  (swap! mutant-characters conj mutant))

(add-human "Black Widow")
(add-mutant "Hulk")
(add-mutant "Ariel")



;; Gambling Game example of state

;; The examples in this section relate to an online gambling game in which players compete against each other and against the tame itself.
;; The hands that the players are dealt and the money which they have in their account are mutable in these examples

;; Note: You could make the card hand that each player holds immutable using a new persistent data structure for each game and only make the deck of cards they are drawing from immutable for a particular round of games.  Actually you could make both immutable.

;; The only value that seems to really benefit from state is the current amount of a players account, however, even that could be immutable, so long as changes are written to a persistent storage


;; Imagine we have a list of players and we want to add a new player.
;; We have a table and we only have two places at the table as a maximum.
;; Create a join-game function that will swap in a player


;; To create a player table I would define a vector to hold the player name.
;; As we are going to change state, we want to do it in a safe way,
;; so we define that vector with an atom

;; (def players (atom []))

;; We also add a :validator as a condition to ensure we dont put more than
;; 2 players into the game

(def players (atom [] :validator #(<= (count %) 2)))



;; Add players
(swap! players conj "Player One")
(deref players)
@players

(swap! players conj "Player Two")

(reset! players ["Player One"])
(reset! players [])


;; Add players by name
(defn join-game
 [name]
 (swap! players conj name))


(join-game "Rachel")
(join-game "Harriet")
(join-game "Terry")         ; cant add a third name due to the :validator condition on the atom
;; (join-game "Sally" "Sam") ;; too many parameters

(str "Current players: " @players)

(defn reset-game
  []
  (reset! players []))

(reset-game)


;; Atom and assoc with multiple keyword updates
;;

(def game-accounts
  {:betty  280
   :jenny  460
   :sammy  100
   :dealer 100000})


;; (update game-accounts
;;         :betty (dec 50)
;;         :jenny (dec 75)
;;         :dealer (dec 100))
;;
;; update only accepts one key and one function per call — there's no built-in variadic version like assoc has.
;;
;; (dec 50) evaluates immediately to 49, not a function you can apply later. update needs a function (or a function + extra args), not a precomputed value.

(defn account-transaction
  "Helper function to update multiple keys within one call"
  [m & kvs]
  (reduce (fn [acc [k f]] (update acc k f))
          m
          (partition 2 kvs)))

(account-transaction
  game-accounts
   :betty  #(- % 50)
   :jenny  #(- % 75)
   :dealer #(- % 100))
;; => {:betty 230, :jenny 385, :sammy 100, :dealer 99900}



;; A simpler approach for updating accounts
;;
;; update is "subtract this amount from each account," this is the idiomatic one-liner:

(merge-with - game-accounts {:betty 50 :jenny 75 :dealer 100})
;; => {:betty 230, :jenny 385, :sammy 100, :dealer 99900}

;; `merge-with -` combines each key using -, so it computes 280 - 50, 460 - 75, 100000 - 100, and leaves :sammy alone since it's not in the second map.

;; Which to pick

;; If it's specifically "subtract an amount from several accounts" (like a betting round) → merge-with - is the cleanest, most idiomatic Clojure and needs no helper.

;; If you want a general-purpose tool that works with any function per key (not just subtraction), and want assoc-like syntax → the updates helper is more flexible.)

;; Given a domain such as a poker/betting simulator then `merge-with -` with a deltas map is the simplest approach.



;; Clojure ref types
;;

(def jennys-account (ref 500))
(def bettys-account (ref 500))
(def game-account (ref 5000000))

@jennys-account
@bettys-account


(defn credit-table
  [player-account]
  (dosync
    (alter player-account - 100)
    (alter game-account + 100)))


(defn add-to-table
  [name]
  (swap! players conj name))


(defn add-person-to-account
  [name account]
  ;;  (if (< account 100 )
  ;;    (println "You're broke")
  (credit-table account)
  (add-to-table name))


(add-person-to-account "Betty" bettys-account)


;; NOTE: If a map is used in the atom that has all the relevant information that needs changing you may not need to use the following ref example



;;
;; Using ref to manage multiple state changes
(def game-world
  (atom {:players [{:id 0 :name "harriet" :account 100}]
         :game-account 0}))


(swap! game-world update-in
     :game-account add-person-to-account)


(def players-ref (ref [] :validator #(<= (count %) 2)))
(def harriet-account (ref 0))


(defn join-game-safely
  [name player-account game-account]
  (dosync
    (alter players-ref conj name)
    (alter player-account + 100)
    (alter game-account - 100)))


(join-game-safely "Harriet" harriet-account game-account)

@harriet-account

@game-account

@players-ref


;; (alter game-account 1000)

(join-game-safely "Jenny" jennys-account game-account)


;; Refs are for Coordinated Synchronous access to "Many Identities".
;; Atoms are for Uncoordinated synchronous access to a single Identity.

;; Coordinated access is used when two Identities need to be changes together, the classic example being moving money from one bank account to another, it needs to either move completely or not at all.

;; Uncoordinated access is used when only one Identity needs to update, this is a very common case.


;; Agents are for Uncoordinated asynchronous access to a single Identity.
;; Vars are for thread local isolated identities with a shared default value.

;; Synchronous access is where the call expects to wait until all the identities are settled before continuing.

;; Asynchronous access is "fire and forget" and let the Identity reach its new state in its own time.

;; (join-game-safely "Betty" betty-account)


;; adding accounts example
;; (+ @account1 @account2)


;; Agents
;; (def flashes (agent {:green 0 :red 0 :blue 0}))
;; (send flashes update-in [:red] inc)

;; Create an agent called cashier (that has not state)
;; (send-off cashier str "Debit X with £" stake)

;; (io! (str "Debit John with £" stake))


;;
;; Futures
;; Can you do this in another thread
;; wrapper over the java futures
;; -- for when you are calculating in one thread and want to hand it over to another,
;; -- or create pipelines between threads
(future (+ 2 2))
(type (future (+ 2 2)))
(def f (future (+ 2 2)))
(realized? f)
@f

(def my-promise (promise))
(realized? my-promise)
(deliver my-promise 42)
(realized? my-promise)

(def my-delay (delay (+ 2 2)))
(realized? my-delay)
@my-delay
(realized? my-delay)
