(ns clojure-through-code.10-changing-state)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Changing state in Clojure

;; The most common way to manage changing state in Clojure is the atom.

;; Essentially an atom is a box that can hold any other clojure data.  when you change the atom contents, you are essentially replacing one immutable value with another.

;; An atom is a mutable container for any valid data in Clojure.  So you can put numbers, strings and collections into an atom.

;; Unlike numbers, strings and collections, the contents of an atom changes when you use a function that changes a value or collection.

;; However, the value or collection itself is immutable, so does not change. Its the atom that shows you a different value.


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Using fictional characters to show atoms in action
;; (work in progress)

(def human-characters [])

(def mutant-characters (atom []))

(defn add-mutant [mutant]
  (swap! mutants conj mutant))

(add-mutant "Black Widow")
(add-mutant "Hulk")
(add-mutant "Ariel")



;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
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

(def players (atom []))

;; We also add a :validator as a condition to ensure we dont put more than
;; 2 players into the game

(def players (atom [] :validator #(<= (count %) 2)))

;; the second definition of players point the var name to something new
;; we havent changed what players was, it just points to something diferent

;; Add players
(swap! players conj "Player One")
(deref players)
@players

(swap! players conj "Player Two")

(reset! players ["Player One"])
(reset! players [])

;; Add players by name
(defn join-game [name]
  (swap! players conj name))

(join-game "Rachel")
(join-game "Harriet")
(join-game "Terry")         ;; cant add a third name due to the :validator condition on the atom
;; (join-game "Sally" "Sam") ;; too many parameters

@players

;;(defn reset-game
;; (reset! players [nil]))
;; reset! should be a vector

(reset! players [])


(def dick-account (ref 500))
(def toms-account (ref 500))
(def betty-account (ref 500))

@betty-account

(defn credit-table [player-account]
  (dosync
   (alter player-account - 100)
   (alter game-account + 100)))

(defn add-to-table [name]
  (swap! players conj name))

(defn join-game [name account]
;;  (if (< account 100 )
;;    (println "You're broke")
  (credit-table account)
  (add-to-table name))

(join-game "Betty" betty-account)


;; NOTE: If a map is used in the atom that has all the relevant information that needs changing you may not need to use the following ref example



;;;;;;;;;;;;;;;;
;; Using ref to manage multiple state changes


@player-ref
@toms-account

@betty-account

(def game-world (atom {:players [{:id 0 :name "harriet" :account 100}]
                       :game-account 0}))

#_(swap! update-in game-world
       :game-account add-player

       )


(def players-ref (ref [] :validator #(<= (count %) 2)))
(def game-account (ref 1000))
(def harriet-account (ref 0))

(defn join-game-safely [name player-account game-account]
  (dosync
   (alter players-ref conj name)
   (alter player-account + 100)
   (alter game-account - 100)))

(join-game-safely "Harriet" harriet-account game-account)

@harriet-account

@game-account

@players-ref

;; (alter game-account 1000)

(join-game-safely "Tom" toms-account game-account)

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
;(def flashes (agent {:green 0 :red 0 :blue 0}))
;(send flashes update-in [:red] inc)

;; Create an agent called cashier (that has not state)
;; (send-off cashier str "Debit X with £" stake)

;; (io! (str "Debit John with £" stake))


;;;;;;;;;;;;;
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
