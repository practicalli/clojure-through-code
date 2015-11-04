;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Changing state in Clojure

;; Example: Players for a game

(ns clojure-through-code.10-changing-state)

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
(defn joining-game [name]
  (swap! players conj name))

(joining-game "Rachel")
(joining-game "Harriet")
;; (joining-game "Terry")         ;; cant add a third name due to the :validator condition on the atom
;; (joining-game "Sally" "Sam") ;; too many parameters

@players

;;(defn reset-game
;; (reset! players [nil]))
;; reset! should be a vector

(reset! players [])


(def game-account (ref 1000))
(def toms-account (ref 500))
(def dick-account (ref 500))
(def harry-account (ref 500))
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



(def player-ref (ref [] :validator #(<= (count %) 2)))

@player-ref
@toms-account
@harry-account
@betty-account



(defn join-game-safely [name player-account]
  (dosync
   (alter player-ref conj name)
   (alter player-account - 100)
   (alter game-account + 100)))

(join-game-safely "Tom" toms-account)
(join-game-safely "Harry" harry-account)
;; (join-game-safely "Betty" betty-account)


;; adding accounts example
;; (+ @account1 @account2)


;; Agents
;(def flashes (agent {:green 0 :red 0 :blue 0}))
;(send flashes update-in [:red] inc)

;; Create an agent called cashier (that has not state)
;; (send-off cashier str "Debit X with £" stake)

;; (io! (str "Debit John with £" stake))


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

