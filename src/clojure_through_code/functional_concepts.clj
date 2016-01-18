(ns clojure-through-code.functional-concepts)


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

(take 10 (range))

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

(defn recursively-use-a-collection
"I create a lovely StackOverflowError, I need to put a check on the recursive call"
  [collection]
  (println (first collection))
  (if (empty? collection)
    (println "no more values to process")
    (recursively-use-a-collection  (rest collection))))

(recursively-use-a-collection [1 2 3])

;l map reduce


;; tail call optomisation

;; (recur)


;;;;;;;;;;;;;;;;;;
;; managing state



;;;;;;;;;;;;;;;;;;
;; LISP features - extensibility via macros

;; examples fo macros

;; macro-expand




;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Rich Hickey

;; Time, perception, values, identity, visibility, state, persistence, memory, transience, process, place, change, communication. We use these terms everyday. Yet, despite its real-world modeling flavor, OO has little to say about them. What do these terms mean, and why do they matter to our programs?

