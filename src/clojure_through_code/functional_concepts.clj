(ns clojure-through-code.functional-concepts)

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

;; impure function - random numbers

(:import java.util.Date)
(defn current-date []
  (java.util.Date.))

(current-date)

;; Random numbers should be generated outside the function and passed as an argument, keeping the function pure.

(defn print-to-console [value-to-print]
  (println "The value is:" value-to-print))

(print-to-console "a side effect")


;;;;;;;;;;;;;;;
;; Higher Order functions

;; functions can be used as an arugument to other functions,
;; because a function always evaluates to a value

(filter
 even?
 (range 1 10))


;;;;;;;;;;;;;;;;
;; Persistent data structures

(def persistent-vector [0 1 2 3 4])

(conj persistent-vector 5)

(-> persistent-vector
    (conj 5)
    (conj 6))


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

;; The range function returns a lazy sequence of numbers in the range given by the arguents provided to range.
;; Calling the range function without arguments will force an infinate sequence of numbers to be generated, quickly resulting in an out of memory error in the heap.

;; (range) ;; dont run range without arguments unless you put another constraint around it

;; Instead, we can either pass arguments to the range function that limit the sequence size or wrap the range function in another function

(take 7 (range))

;; The take function defines how much of a sequence that range should generate.  So we can call range without arguments and it will lazily generate the sequence.


;;;;;;;;;;;;;;;;;;;
;; Pattern matching


;; destructuring

; in function arguemnts, collections (maps, vectors)


;;;;;;;;;;;;;;;
;; Immutability

"This is a string, consider me a value"

["This is a string in a vector (think of it as an array for now)"]

(def name-of-a-value ["This is a string in a vector, consider me a value"])

;; Lets evaluate the name and see what value it returns
name-of-a-value

(cons "A new string, what happens to the original string" name-of-a-value)

name-of-a-value

(def name-of-new-string
  (cons "A new string, what happens to the original string" name-of-a-value))

name-of-new-string

;; Persistent Data Structures

;; list, vector, map, set

'(1 2 3 "abc" 22/7)


;; sequence functions

(first [1 2 3])
(rest [1 2 3])
(last [1 2 3])

;;;;;;;;;;;;;;;;;;
;; Programming with functions

;; This is how you define a function (the easy eay)

(defn my-function-name [parameter-list]
  (str "This is the algorithum of my method, which could be many functions workin"))


;; Pure Functions Are Referentially Transparent

;; To return the same result when called with the same argument, pure functions rely only on 1) their own arguments and 2) immutable values to determine their return value. Mathematical functions, for example, are referentially transparent

;; First Class Functions


;; Idempot - given the same input you get the same output

;; eg like Faceboook react JS components for their lightweight DOM (lightweight JS componets for each HTML element)


;; Example: Adding up values from 1 to 40 and return the overall total

(apply + (range 1 40))

(reduce + (range 1 10))

(range 1 10)

(reduce + (1 2 3 4 5 6 7 8 9))

;; Both the reply and reduce functions take 2 arguments, the first is the function to apply to a data structure, the second is the data structure.  In this example, rather than type out the Integer numbers from 1 to 40, we use the range function to generate them for us.

;; Functiors
;; - put simply a function that takes a value and a function as its arugments, eg map, apply
;; - the value, typically a collection (vector, map, string) is unpacked and each element is passed
;; - to the function that as given as the argument.
;; - The function, eg. + is applied in turn to each value and returns a structured value as a result,
;; - eg. a list or vector

(map inc [1 2 3 4 5])

(inc 1 )

;; I dont think apply counts as a functor because is only returns a simple value
(apply + [1 3 5 7])

;; if that is the case, then is reduce a functor, it would suggest not


;; Polymorphism

(defn i-am-polly
  ([] (i-am-polly "My name is polly"))
  ([message] (str message)))

(i-am-polly)
(i-am-polly "I call different behaviour depeding on arguments sent")


;;;;;;;;;;;;;;;;;
;; recursion

;; processing a collection using recursion

(defn recursively-use-a-collection [collection]
  (println (first collection))
  (if (empty? collection)
    (print-str "no more values to process")
    (recursively-use-a-collection  (rest collection))))

(recursively-use-a-collection [1 2 3])


;;;

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

;; the second definition of players point the var name to something new
;; we havent changed what players was, it just points to something diferent

;; Add players by name
(defn join-game [name]
  (swap! players conj name))

(join-game "Rachel")
(join-game "Harriet")
(join-game "Terry")      ;; cant add a third name due to the :validator condition on the atom

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


;;;;;;;;;;;;;;;;;;
;; LISP features - extensibility via macros

;; examples fo macros

;; macro-expand


;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Rich Hickey

;; Time, perception, values, identity, visibility, state, persistence, memory, transience, process, place, change, communication. We use these terms everyday. Yet, despite its real-world modeling flavor, OO has little to say about them. What do these terms mean, and why do they matter to our programs?




;; Composable abstractions

