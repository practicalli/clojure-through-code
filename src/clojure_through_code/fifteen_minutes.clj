(ns clojure-through-code.fifteen-minutes)


;; Simple Values - Hello World and Maths

;; str will create a string out of all its arguments

(str "Hello Clojure World," " " "happy " 11 " th birthday in 2018")


;; Math uses function calls rather than operators
;; parentheses () define the order of evaluation
;; parentheses ensure there is no ambiguity in the order of calculation

(+ 1 1) ; => 2
(- 24 4 10) ; => 10
(* 1 2 (+ 1 2) (* 2 2)) ; => 24

;; A ratio is a value in Clojure, helping to maintain precision
(/ 22 7) ; => 22/7

;; A ratio can be coerced into a number type
(/ 22 7.0) ; => 3.142857142857143

;; Equality is comparing values with the = function
;; assignment is not done with the = function
(= 1 1) ; => true
(= 2 1) ; => false
(odd? 1) ; => true

;; You need not for logic, too
(not true) ; => false
(not= 1 2 3) ; => true


;; Collections & Sequences
;;

;; A list would be written as just (1 2 3), but we have to quote
;; it to stop the reader thinking it's a function.
;; Also, (list 1 2 3) is the same as '(1 2 3)

;; Both lists and vectors are collections:
(coll? '(1 2 3)) ; => true
(coll? [1 2 3]) ; => true

;; Only lists are seqs.
(seq? '(1 2 3)) ; => true
(seq? [1 2 3]) ; => false

;; Seqs are an interface for logical lists, which can be lazy.
;; "Lazy" means that a seq can define an infinite series, like so:
(range 4) ; => (0 1 2 3)
(range) ; => (0 1 2 3 4 ...) (an infinite series)
(take 4 (range)) ;  (0 1 2 3)

;; Use cons to add an item to the beginning of a list or vector
(cons 4 [1 2 3]) ; => (4 1 2 3)
(cons 4 '(1 2 3)) ; => (4 1 2 3)

;; Use conj to add an item to the beginning of a list,
;; or the end of a vector
(conj [1 2 3] 4) ; => [1 2 3 4]
(conj '(1 2 3) 4) ; => (4 1 2 3)

;; Use concat to add lists or vectors together
(concat [1 2] '(3 4)) ; => (1 2 3 4)

;; Use filter, map to interact with collections
(map inc [1 2 3]) ; => (2 3 4)
(filter even? [1 2 3]) ; => (2)

;; Use reduce to reduce them
(reduce + [1 2 3 4])


;; = (+ (+ (+ 1 2) 3) 4)
;; => 10

;; Reduce can take an initial-value argument too
(reduce conj [] '(3 2 1))


;; = (conj (conj (conj [] 3) 2) 1)
;; => [3 2 1]

;; Functions
;;

;; Use fn to create new functions. A function always returns
;; its last statement.
(fn [] "Hello World")


;; => #function[clojure-through-code.fifteen-minutes/eval12498/fn--12499]

;; (You need extra parens to call it)
((fn [] "Hello World")) ; => "Hello World"

;; Give a name to values using the def function
(def clojure-developer-salary 100000)


;; => #'clojure-through-code.fifteen-minutes/clojure-developer-salary

;; Use the name in other Clojure code
(str "Clojure developers could earn up to " clojure-developer-salary)


;; => "Clojure developers could earn up to 100000"

;; Define a name for function so you can call it elsewhere in your code
(def hello-world (fn [] "Hello World"))
(hello-world) ; => "Hello World"

;; You can shorten this syntax using the defn function (macro)
(defn hello-world
  []
  "Hello World")


;; The [] is the list of arguments for the function.
;; There can be zero or more arguments
(defn hello
  [name]
  (str "Hello " name))


(hello "Steve") ; => "Hello Steve"


;; #() is a shorthand for defining a functions,
;; most useful inline
(def hello-terse #(str "Hello " %1))
(hello-terse "Jenny") ; => "Hello Jenny"

;; You can have multi-variadic functions, useful when you have defaults
(defn hello3
  ([] "Hello World")
  ([name] (str "Hello " name)))


(hello3 "Jake") ; => "Hello Jake"
(hello3) ; => "Hello World"

;; Functions can pack extra arguments up in a seq for you
(defn count-args
  [& args]
  (str "You passed " (count args) " args: " args))


(count-args 1 2 3) ; => "You passed 3 args: (1 2 3)"

;; You can mix regular and packed arguments
(defn hello-count
  [name & args]
  (str "Hello " name ", you passed " (count args) " extra args"))


(hello-count "Finn" 1 2 3)


;; => "Hello Finn, you passed 3 extra args"

;; More arguments can be captured using the & and a name
;; In the hello-advanced we capture the mandatory name and address
;; Anything-else is checked to see if its empty and if so, a standard messages is added
;; If anything-else has values, they are added to the string instead.
(defn hello-advanced
  [name address & anything-else]
  (str "Hello " name
       ", I see you live at " address
       (if (nil? anything-else)
         ".  That is all."
         (str "and you also do " (clojure.string/join ", " anything-else)))))


;; => #'clojure-through-code.fifteen-minutes/hello-advanced

(hello-advanced "John Stevenson" "7 Paradise street")


;; => "Hello John Stevenson, I see you live at 7 Paradise street.  That is all."

(hello-advanced "John Stevenson" "7 Paradise street" "cycling" "swimming")


;; => "Hello John Stevenson, I see you live at 7 Paradise streetand you also do cycling, swimming"



;; Hashmaps
;;

(class {:a 1 :b 2 :c 3}) ; => clojure.lang.PersistentArrayMap

;; Keywords are like strings with some efficiency bonuses
(class :a) ; => clojure.lang.Keyword

;; Maps can use any type as a key, but usually keywords are best
(def string-keys-map (hash-map "a" 1, "b" 2, "c" 3))
string-keys-map  ; => {"a" 1, "b" 2, "c" 3}

(def keyword-keys-map (hash-map :a 1 :b 2 :c 3))
keyword-keys-map ; => {:a 1, :c 3, :b 2} (order is not guaranteed)

;; Getting values from maps using keys
(get keymap :c)


;; Maps can be called just like a function, with a key as the argument
;; This is a short-cut to the get function and useful inside inline functions
(string-keys-map "a") ; => 1
(keyword-keys-map :a) ; => 1

;; A Keyword key can also be used as a function that gets its associated value from the map
(:b keyword-keys-map) ; => 2

;; Retrieving a non-present value returns nil
(string-keys-map "d") ; => nil

;; Use assoc to add new keys to hash-maps
(assoc keyword-keys-map :d 4) ; => {:a 1, :b 2, :c 3, :d 4}

;; But remember, clojure types are immutable!
keyword-keys-map ; => {:a 1, :b 2, :c 3}

;; Use dissoc to remove keys
(dissoc keymap :a :b) ; => {:c 3}

;; Sets
;;

(class #{1 2 3}) ; => clojure.lang.PersistentHashSet
(set [1 2 3 1 2 3 3 2 1 3 2 1]) ; => #{1 2 3}

;; Add a member with conj
(conj #{1 2 3} 4) ; => #{1 2 3 4}

;; Remove one with disj
(disj #{1 2 3} 1) ; => #{2 3}

;; Test for existence by using the set as a function:
(#{1 2 3} 1) ; => 1
(#{1 2 3} 4) ; => nil

;; There are more functions in the clojure.sets namespace.

;; Useful forms
;;

;; Logic constructs in clojure are just macros, and look like
;; everything else
(if false "a" "b") ; => "b"
(if false "a") ; => nil

;; Use let to create temporary bindings
(let [a 1 b 2]
  (> a b)) ; => false

;; Group statements together with do
(do
  (print "Hello")
  "World") ; => "World" (prints "Hello")

;; Functions have an implicit do
(defn print-and-say-hello
  [name]
  (print "Saying hello to " name)
  (str "Hello " name))


(print-and-say-hello "Jeff") ; => "Hello Jeff" (prints "Saying hello to Jeff")

;; So does let
(let [name "Jenny"]
  (print "Saying hello to " name)
  (str "Hello " name)) ; => "Hello Jenny" (prints "Saying hello to Jenny")

;; Libraries
;;

;; Use "use" to get all functions from the module
(use 'clojure.set)


;; Now we can use set operations
(intersection #{1 2 3} #{2 3 4}) ; => #{2 3}
(difference #{1 2 3} #{2 3 4}) ; => #{1}

;; You can choose a subset of functions to import, too
(use '[clojure.set :only [intersection]])


;; Use require to import a module
(require 'clojure.string)


;; Use / to call functions from a module
(clojure.string/blank? "") ; => true

;; You can give a module a shorter name on import
(require '[clojure.string :as str])
(str/replace "This is a test." #"[a-o]" str/upper-case) ; => "THIs Is A tEst."
;; (#"" denotes a regular expression literal)

;; You can use require (and use, but don't) from a namespace using :require.
;; You don't need to quote your modules if you do it this way.
(ns test
  (:require
    [clojure.set :as set]
    [clojure.string :as str]))


;; Types underlying Clojure
;;

;; Types are inferred by Clojure so mostly not specified
;; Interop with the host platform (i.e. Java) may benefit from explicit type coercion

;; Clojure uses Java's object types for booleans, strings and numbers as these are immutable.
;; Use `class` or `type` to inspect them.
(class 1) ; Integer literals are java.lang.Long by default
(class 1.); Float literals are java.lang.Double
(class ""); Strings always double-quoted, and are java.lang.String
(class false) ; Booleans are java.lang.Boolean
(class nil); The "null" value is called nil

;; If you want to create a literal list of data, use ' to make a "symbol"
'(+ 1 2) ; => (+ 1 2)

;; You can eval symbols.
(eval '(+ 1 2)) ; => 3

;; Vectors and Lists are java classes too!
(class [1 2 3]); => clojure.lang.PersistentVector
(class '(1 2 3)); => clojure.lang.PersistentList



;; Java
;;

;; Java has a huge and useful standard library, so
;; you'll want to learn how to get at it.

;; Use import to load a java module
(import java.util.Date)


;; You can import from an ns too.
(ns test
  (:import
    (java.util
      Calendar
      Date)))


;; Use the class name with a "." at the end to make a new instance
(Date.) ; <a date object>

;; Use . to call methods. Or, use the ".method" shortcut
(. (Date.) getTime) ; <a timestamp>
(.getTime (Date.)) ; exactly the same thing.

;; Use / to call static methods
(System/currentTimeMillis) ; <a timestamp> (system is always present)

;; Use doto to make dealing with (mutable) classes more tolerable
(import java.util.Calendar)


(doto (Calendar/getInstance)
  (.set 2000 1 1 0 0 0)
  .getTime) ; => A Date. set to 2000-01-01 00:00:00
