(ns clojure-through-code.thinking-functional-demo)

;;;;;;;;;;;;;;;;;;
;; Pure Functions

(defn increment-numbers [number-collection]
  (map inc number-collection))

(increment-numbers '(1 2 3 4 5))

;; takes in a specific argument and returns a deterministic value

;; impure function

(def global-value '(5 4 3 2 1))

(defn impure-increment-numbers [number-collection]
  (map inc global-value))

(impure-increment-numbers '(1 2 3 4 5))

;; uses a global value rather than the argument so the result is indeterministic

;;;;;;;;;;;;;;;;;;
;; impure function - random numbers

(:import java.util.Date)
(defn current-date []
  (java.util.Date.))

(current-date)

;; Random numbers should be generated outside the function and passed as an argument, keeping the function pure.

(defn print-to-console [value-to-print]
  (println "The value is:" value-to-print))

(print-to-console "a side effect")



;;;;;;;;;;;;;;;;
;; Persistent data structures

(def persistent-vector [0 1 2 3 4])

;; lets add a value to our persistent vector
(conj persistent-vector 5)

;; and another..
(conj persistent-vector 6)

;; Chain two function calls using the threading macro 
(-> persistent-vector
    (conj 5)
    (conj 6))




;;;;;;;;;;;;;;;
;; Higher Order functions

;; functions can be used as an arugument to other functions,
;; because a function always evaluates to a value

(map inc [1 2 3 4 5])

(reduce + (range 1 10))

(reduce + '(1 2 3 4 5 6 7 8 9))

(take 10 (range))

(filter
 even?
 (range 1 10))



;;;;;;;;;;;;;;;;;
;; recursion

;; processing a collection using recursion

(defn recursively-use-a-collection [collection]
  (println (first collection))
  (if (empty? collection)
    (print-str "no more values to process")
    (recursively-use-a-collection  (rest collection))))

(recursively-use-a-collection [1 2 3])


;; Polymorphism

(defn i-am-polly
  ([] (i-am-polly "My name is polly"))
  ([message] (str message)))

(i-am-polly)
(i-am-polly "I call different behaviour depeding on arguments sent")


;; recursion with polymorphism

(defn sum
  ([vals] (sum vals 0))
  ([vals accumulating-total]
    (if (empty? vals)
      accumulating-total
      (sum (rest vals) (+ (first vals) accumulating-total)))))

(sum [2 7 9 11 13])
(sum [1])
(sum [7 9 11 13] 9)

;; If we have a very large collection, we run the risk of blowing our heap space

;; (vec (range 0 9999999999))  ;; say goodbye to your heap space

;; Using tail call optomisation allows us to reuse a memory location when we call a function recursively.
;; Recur allows the processing of a very large data set without blowing the heap space.

(defn sum
  ([vals] (sum vals 0))
  ([vals accumulating-total]
   (if (empty? vals)
     accumulating-total
     (recur (rest vals) (+ (first vals) accumulating-total)))))

(sum (vec (range 0 9999999)))



;;;;;;;;;;;;;;;
;; Lazy Evaluation

;; Dividing an integer value by another results in a Ratio, a legal value in Clojure

(/ 22 7)

;; We can also just express a value as a ratio.  This works because of the prefix notation of Clojure

22/7

;; The lazyness can be overridden by specifying a precision, eg coersing the result into a specific type

(/  22 7.0)
(double (/ 22 7))
(float (/ 22 7))

(take 10 (range))



;;;;;;;;;;;;;;;;
;; Sequence / List comprehension

(range 10)

(for [x (range 10) :when (even? x)] x)

(for [x (range 10) :while (even? x)] x)

(for [x (range 10)
      y (range 10)]
  [x y])



;;;;;;;;;;;;;;;;;;
;; managing state

;; updating single values with atom

;; To create a player table I would define a vector to hold the player name.
;; As we are going to change state, we want to do it in a safe way,
;; so we define that vector with an atom

(def players (atom []))

;; We also add a :validator as a condition to ensure we dont put more than
;; 2 players into the game

(def players (atom [] :validator #(<= (count %) 2)))

;; Add players by name
(defn join-game [name]
  (swap! players conj name))

(join-game "Rachel")
(join-game "Harriet")
(join-game "Terry")      ;; the :validator condition on atom only allows 2 players

(reset! players ["Player 1"])
(reset! players [])

@players


;; updating multiple values with ref

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

