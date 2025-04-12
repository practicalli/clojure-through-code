;; ---------------------------------------
;; Fundamentals of Clojure syntax
;;
;; Comments
;; quote - read code as data, dont evaluate
;; nil
;; Creating namespaces
;; Evaluating functions
;; Dynamic vars
;; ---------------------------------------

;; ---------------------------------------
;; Comments

;; Two semi-colons define a line comment    ; single semi-colon is an end of line comment

(comment
  (str "The comment form is used to separate code "
       "from the rest of the namespace")

  (str "All expressions within `comment` are ignored by the Clojure REPL"
       "\n"
       "The `comment` function returns the `nil` value"))

;; ---------------------------------------

;; ---------------------------------------
;; ns - define a namespace

;; namespaces are used to organise Clojure code into logical groupings
;; names of namespaces are used to give context to the code
;; ns definitions can include a documentation string, `doc-string`, after the namespace name
;;
;; Namespace is similar to a package in Java, they are a grouping of data and behaviour

(ns clojure-through-code.01-basics
  "Communicate the purpose of a namespace via a doc-string.
   Include descriptions of data models defined in the namespace.")

;; The `require` function includes the code from another namespace into the current namespace

(ns clojure-through-code.01-basics
  "Communicate the purpose of a namespace via a doc-string.
   Include descriptions of data models defined in the namespace.
   `:require` key defines additional namespaces to include into the current namespace."
  (:require
   [clojure.string :refer [join split trim]]
   [practicalli.clojure-through-code.spec :as spec]))

;; `require` is also a function
;; e.g. optionally including a namespace within a `comment` form

;; Basic `require` form:
;; (:require 'clojure.string)

;; Include specific parts of another namespace
;; (require
;;  '[clojure.string] :refer [join split trim])

;; If a namespace is predominantly using the required namespace
;; (:require [clojure.test] :refer :all)

;; End of Namespace
;; ---------------------------------------------------------

;;;;;;;;;;;;;;;;;;;;;;;
;; Clojure Syntax

;; Clojure uses a prefix notation and () [] : {} # @ ! special characters
;; no need for lots of ; , and other silly things...

;; All behaviour in Clojure is defined in a List, with the first elemtent being a function
;; and the remaining elements being arguments to that function.

;;;;;;;;;;;;;;;;;;;;;;;;;;
;; What is my environment

;; Clojure has built in symbols, which start and end with *
;; Symbols can be evaluated outside of a list structure,
;; functions (the behaviour of your application) and their
;; arguments are contained within a list structure ()

;; The full clojure version
;; can be used to check you are running a particular version, major or minor of Clojure core
*clojure-version*

;; The version info for Clojure core, as a map containing :major :minor
;; :incremental and :qualifier keys. Feature releases may increment
;; :minor and/or :major, bugfix releases will increment :incremental.
;; Possible values of :qualifier include "GA", "SNAPSHOT", "RC-x" "BETA-x"

;; The directory where the Clojure compiler will create the .class files for the current project
;; This directory must be in the classpath for 'compile' to work.
*compile-path*

;; The path of the file being evaluated, as a String.  In the REPL the value is not defined.
*file*

;; A clojure.lang.Namespace object representing the current namespace
*ns*

;; most recent repl values
*1
*2
*3

;;;;;;;;;;;;;;;;;;;;;;;;;
;; Using Java Interoperability

;; java.lang is part of the Clojure runtime environment, so when ever you run a REPL you can call any methods without having to import them or include any dependencies

;; From java.lang.System getProperty() as documented at:
;; http://docs.oracle.com/javase/8/docs/api/java/lang/System.html

(System/getProperty "java.version")

(System/getProperty "java.vm.name")

;; Java properties can be obtained by calling System.getProperty
;; from java.lang.  As System.getProperty is called as a function
;; in clojure it needs to be wrapped in a list structure.

;; Make the result prettier using the Clojure str function
(str "Current Java version: " (System/getProperty "java.version"))

;; We can also get the version of the project
(System/getProperty "clojure-through-code.version")

;; Chaining a few Clojure functions together, to read from
;; the Leiningen project file

;; Getting the version number of the project

;; Information can be read from the Clojure project configuration file using the slurp function
(slurp "deps.edn")

;; The value returned by slurp is a bit messy, so we can tidy it up with read-string

(read-string (slurp "project.clj"))

;; => (defproject clojure-through-code "20.1.5-SNAPSHOT" :description "Learning Clojure by evaluating code on the fly" :url "http://jr0cket.co.uk" :license {:name "Eclipse Public License", :url "http://www.eclipse.org/legal/epl-v10.html"} :dependencies [[org.clojure/clojure "1.6.0"]])

;; rather than have all the information from the file, we just want to get the project version
;; Using the nth function we can select which element we actually want

(nth (read-string (slurp "project.clj")) 2)

;; => "20.1.5-SNAPSHOT"

;; The above code is classic Lisp, you read it from the inside out, so in this case you
;; start with (slurp ...) and what it returns is used as the argument to read-string...

;; Get the contents of the project.clj file using `slurp`
;; Read the text of that file using read-string
;; Select just the third string using nth 2 (using an index starting at 0)

;; You can format the code differently, but in this case its not much easier to read
(nth
 (read-string
  (slurp "project.clj"))
 2)

;; the same behaviour as above can be written using the threading macro
;; which can make code easier to read by reading sequentially down the list of functions.

(->
 "./project.clj"
 slurp
 read-string
 (nth 2))

;; Using the threading macro, the result of every function is passed onto the next function
;; in the list.  This can be seen very clearly usng ,,, to denote where the value is passed
;; to the next function

(->
 "./project.clj"
 slurp ,,,
 read-string ,,,
 (nth ,,, 2))

;; Remember, commas in clojure are ignored

;; To make this really simple lets create a contrived example of the threading macro.
;; Here we use the str function to join strings together.  Each individual string call
;; joins its own strings together, then passes them as a single string as the first argument to the next function

(->
 (str "This" " " "is" " ")
 (str "the" " " "treading" " " "macro" " ")
 (str "in" " " "action."))

;; Using the ->> threading macro, the result of a function is passed as the last parameter
;; of the next function call.  So in another simple series of str function calls,
;; our text comes out backwards.

(->>
 (str "This" " ")
 (str "is" " ")
 (str "backwards" " "))

;; Threading macro is very useful for passing a collection through a number of functions,
;; each function manipulating that collection in a specific way before passing it on to the next.

(-> [2 5 4 1 3 6]
    (reverse)
    (rest)
    (sort)
    (last))

;; add all project information to a map
(->> "project.clj"
     slurp
     read-string
     (drop 2)
     (cons :version)
     (apply hash-map))

;; Threading macros and println statements

;; If you use a print or println function in a threading macro, then part of the theaded value will be lost, as those functions return a value of nil.

(-> "message"
    (str " " "in a bottle")
    println
    (str ", The Police"))
;; => ", The Police"

;; The initial part of the message before the println funciton is called has been dropped because println returned nil.  Therefore nil was the value passed as the first argument to tne next function, rather than "message in a bottle".

;; Using the doto function with println we can pass the value on as the return value and pass the value to be printed in the console.

(doto "message in a bottle" println)
;; => "message in a bottle"

;; So putting this doto function in our threading macro now works

(-> "Message"
    (str " " "in a bottle")
    (doto  println)
    (str " - " "The Police"))
;; => "Message in a bottle - The Police"

;; defining namespace scoped names
(def literal-value-string "I am free")
(def literal-value-five 5)
(def literal-value-six 6)
(def collection-of-values [1 2 3 4 5 6 7 8])

;; names evaluate to the values they are bound to
literal-value-five

(reduce + collection-of-values)
(reduce + [1 2 3 4 5 6 7 8])
;; => 36

;;;; Defining functions
;; defn is a macro for defining functions
(defn my-function [] (str "I wish I was free"))

;; defining a function with def
(def my-function-expanded
  (fn [args] (str "behaviour" (clojure.string/join args))))

;; anonymous function
(fn [args] (str "behaviour" (clojure.string/join args)))

;; anonymous function syntax sugar

;; An anonymous function is defined with the #() syntax where % is a placeholder for the arguments.

(#(* % %) 6)  ;; square a number (fn [num] (* num num))
(#(+ % 3) 6)    ;; add three (fn [num] (+ num 3))
(#(+ %1 %3 %2 4 5) 1 2 3) ;; add 3 arguments (fn [arg1 arg2 arg3] (+ arg1 arg2 arg3 4 5))

;;

;;;;;;;;;;;;;;;;;;;;;;;
;; Working with strings

;; You could use the Java-like function `println` to output strings.

(println "Hello, whats different with me?  What value do I return")

;; However, something different happens when you evaluate this expression.  This is refered to as a side-effect because when you call this function it returns nil.  The actual text is output to the REPL or console.

;; In Clojure, you are more likely to use the `str` function when working with strings.
(str "Hello, I am returned as a value of this expression")

;; join strings together with the function str
(str "Hello" ", " "HackTheTower UK")

;; using println in LightTable shows the results in console window, as its a side affect
;; using srt you see the results of the evaluation inline with the code,
;  as the result of a definition or an expression.

;; Avoid code that creates side-effects where possible to keep your software less complex.
;; using the fast feedback of the REPL usually works beter than println statements in debuging

;;;;;;;;;;;;;;;;;;;;;;;;
;; Simple math to show you the basic structure of Clojure

; Math is straightforward
(+ 3 (/ 4 2) (* 3 5))
(+ 1 1 2489 459 2.7)

(->
 (+ 12 144 20 (* 3 (Math/sqrt 4)))
 (/ 7)
 (+ (* 5 11))
 (= (+ 0.0  (* 9 9))))

(Math/sqrt 4)

(- 2 1) ; => 1
(* 1 2) ; => 2
(/ 1 2)
(type  (/ 22 7.0))
(/ 5000 20000)

(type (Integer. "2"))

;; The above is the same as the Java statement Integer myInt = new Integer("2")

(/ (* 22/7 3) 3)

;; Ratios delay the need to drop into decimal numbers.  Once you create a decimal number then everything it touches had a greater potential to becoming a decimal

;; Clojure uses Prefix notation, so math operations on many arguments is easy.
(+ 1 2 3 4 5)

(+ 1 2 (* 3 4) (- 5 6 -7))

(+)
(*)
(* 2)
(+ 4)

;; Variadic functions
(+ 1 2 3)
(< 1 2 3)
(< 1 3 8 4)
;; (remaining 22 7)

(inc 3)
(dec 4)

(min 1 2 3 5 8 13)
(max 1 2 3 5 8 13)

(apply + [1 2 3])
(reduce + [1 2 3])

(apply / [1 2 3])
(/ 53)

(map + [1 2 3.0] [4.0 5 6])

(repeat 4 9)

; Equality is =
(= 1 1) ; => true
(= 2 1) ; => false

;; Equality is very useful when your data structures are immutable

; You need not for logic, too
(not true) ; => false

(identical? "foo" "bar")
(identical? "foo" "foo")
(= "foo" "bar")
(= "foo" "foo")

(identical? :foo :bar)
(identical? :foo :foo)

;; Keywords exist as identifiers and for very fast comparisons

(def my-map {:foo "a"})

(= "a" (:foo my-map))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Truethy experiments

;; some truthiness with math functions - looking at types
(+)
(class (+))
(*)
(true? +)
(false? +)
(true? *)
(false? *)
(true? 1)
(true? -1)
(true? true)
(true? (not false))
(- 2)
; (class (/))  ;; wrong number of arguments error

(= 4 (inc 2))
;; => false

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Conditionals

;; if something is true, do this, else do that

(if false
  "hello"
  (do (+ 3 4)
      (str  "goodbye")))

(if false "one" "two")
(if nil "uh" "oh")
(if false "Hello word" "Goodbye crule world")

;; cond - if condition true, do this, :otherwise do that

(cond
  (zero? (- (* 4 2) 8)) "The result is zero"
  (= 4 (inc 2))         "The result is 4"
  (= "Hello World" (str "Hello " "World")) "Hello World"
  :else                 "None of the above.")

(cond
  (= 7 (inc 2))          "(inc 2) is not 7, so this condition is false"
  (= 16 (* 8 2))         "This is the first correct condition so its associated expression is returned"
  (zero? (- (* 8 8) 64)) "This is true but not returned as a previous condition is true"
  :else                  "None of the above are true")

;; when true do the following

(when (> 8 2)
  "Higher")

;; Predicates - take a value and return a boolean result (true | false)
(true? true)
(true? (not true))
(true? false)
(true? (not false))
(true? nil)

; Types
;;;;;;;;;;;;;

; Clojure uses Java's object types for booleans, strings and numbers.
; Use `class` to inspect them.

(class 1)
; Integer literals are java.lang.Long by default
(class 1.1)    ; Float literals are java.lang.Double

(class "")
; Strings always double-quoted, and are java.lang.String

(class false)  ; Booleans are java.lang.Boolean
(class nil)    ; The "null" value is called nil

(class (list 1 2 3 4))

(class true)
(class ())
(class (list 1 2 34 5))
(class (str 2 3 4 5))
(class (+ 22/7))
(class 5)
(class "fish")
(type [1 2 3])
(type {:a 1 :b 2})

(type (take 3 (range 10)))

(take 10 (range))

(type (Integer. "8080"))

(type "This is a string")

(type '(1 2 3 4))

(type 1)
(type "I am a string")
(type :i-am-a-keyword)
(type 22/7)
(type 'def)
(type 'defn)
(type '(1 2 3 4))
(type [1 2 3 4])
(type {:a 1 :b 2})
(type #{1 2 3 4})

;; Ratios
;; To help maintain the precision of numbers, Clojure has a type called Ratio
;; So when you are dividing numbers you can keep the as a fraction using whole numbers
;; rather than constrain the result to a approximate
(/ 2)

;; A classic example is dividing 22 by 7 which is approximately the value of Pi
(/ 22 7)

(class (/ 22 7))

;; If you want to force Clojure to evaluate this then you can specify one of the
;; numbers with a decimal point
(class (/ 22 7.0))

(/ 14 4)

7/2

(*  (/ 22 7) 7)

(type 7.0)

;; Is something a thing

(instance? String "hello")

(instance? Double 3.14)

(instance? Number 3.14)

(instance? java.util.Date #inst "2015-01-01")

;; Lets try and break clojure with Math

;; (/ 22 0)

;; ArithmeticException Divide by zero  clojure.lang.Numbers.divide (Numbers.java:156)

;; java.lang.* packages are included in all Clojure projects, so you can call the
;; Java Math functions easily

(Math/sqrt 4)

(Math/sqrt 13)

(Math/PI)

(.toUpperCase "bla")

;;(new yourObjectName)
;; (. youObjectName)

;; Lets have some maths fun and see how Clojure deals with this

(Math/sqrt -1)

;; So Clojure, well Java really as its the Java Math class methods, deals with interesting
;; mathematical concepts like the square-root of minus one.

;; So what does NaN mean.  Well by consulting Stack Overflow, a NaN is produced if a floating point operation would produce an undefinable result.  Other examples include dividing 0.0 by 0.0 is arithmetically undefined.

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Clojure projects

;; Install Leiningen build tool from leingingen.org
;; Create a new project with lein new project-name
;; Run the repl using lein repl

;; Look at the project configuration file project.clj
;; The project is defined as Clojure code using defproject macro, keywords, maps & vectors

;; In the source code src/project-name/core.clj is some sample code in a specific namespace
;; Namespaces are similar to packages in Java

;; A namespace is a way to organise your clojure code (data structure and function definitions)
;; The namespace represents the underlying directory and filename which contain specific data structure and function definitions

(namespace 'clojure-through-code.01-basics/my-map)
(name 'clojure-through-code.01-basics/my-map)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Documentation in Clojure & LightTable

;; Clojure functions have Documentation
;; - in LightTable you can right-click on a function name and select "show docs"
;; documentation is shown inline

;; Lets look at the documentation for map
(class [])

(map? [1 2 3 4])

;;;;;;;;;;;;;;;;;;;;;;;;
;; LightTable Settings

;; The configuration in LightTable uses a Clojure syntax
;; which is easy enough to understand even if you dont know
;; clojure. Essentially the configurations are a
;; datastructure, a map with Clojure keywords to make it
;; simple to pull out the relevant settings

;; Set up keyboard shortcuts or look at the default shortcuts
;; Settings: User keymap
;; Settings: Default keymap

;; General configuration settings for LightTable - eg, Fonts, themes, editor settings
;; Settings: User behaviors

;; (doto)  ;; Chain functions together
;; ->

;;; cool stuff

;; user> (as-> 0 n)
;; 0
;; user> (as-> 0 n (inc n) (inc n))
;; 2
;; user> (def x [[1 2 3] [4 5 6]])
;; #'user/x
;; user> x
;; [[1 2 3] [4 5 6]]
;; user> (ffirst x)
;; 1
;; user> (first x)
;; [1 2 3]
;; user> (assoc nil :a)
;; ArityException Wrong number of args (2) passed to: core/assoc  clojure.lang.AFn.throwArity (AFn.java:429)
;; user> (assoc nil :a 1)
;; {:a 1}
;; user> (filter identity @)RuntimeException Unmatched delimiter: )  clojure.lang.Util.runtimeException (Util.java:221)
;; user> (filter identity '(1 2 3 nil))
;; (1 2 3)
;; user> (identity nil)
;; nil
;; user> (true? nil)
;; false

;; Paredit
;; Alt-up - get rid of parent

(def a "I have a name")

;; (def :a "What about keywords")
;; => keywords cannot be used as names, as keywords point to themselves

(class :a)
(get {"a" "ay"} "a")

(get-in {"a" {"aa" "You found me"}} ["a" "aa"])

(let [reason "sick"]
  (cond
    (= reason "sick")  "I ache all over and am seeing green elephant-shaped spots"
    (= reason "train") "That darn Southern Rail on strike again"
    (= reason "pet")   "My pet ate my homework"))

(let [greeting :fr]
  (case greeting
    :fr "bonjour monde clojure"
    :en "hello clojure world"
    :it "ciao mondo clojure"
    :es "hola mundo clojure"))

(let [language :fr
      message (case language
                :fr "bonjour monde clojure"
                :en "hello clojure world"
                :it "ciao mondo clojure"
                :es "hola mundo clojure")
      upper-case? true]
  (if upper-case?
    (clojure.string/upper-case message)
    message))

(let [reason "sick"]
  (cond
    (= reason "sick")  "I ache all over and am seeing green elephant-shaped spots"
    (= reason "train") "That darn Southern Rail on strike again"
    (= reason "pet")   "My pet ate my homework"))

(def my-details ["John" "Stevenson" "37" "Clojure" "London" "North Yorkshire" 12])

(first my-details)
(second my-details)
(rest my-details)

(nth ["John" "Stevenson" "37" "Clojure" "London" "North Yorkshire" 12] 2)

(nth my-details 0)

(first my-details)
(first "John")
(str (nth "John" 0))

["string" ["fish" "rabit"]]

{:key ["value1" "value2"]}

{:bicycle ["wheels" "frame" "handlbars"]}

{:meal ["starter" "main course" "desert"]}

{:meal
 ["starter" ["soup" "bread rolls"]
  "main course" ["fish" "chips" "mushy peas"]
  "desert" ["cheese" "wine"]]}

{:meal
 {"starter" ["soup" "bread rolls"]
  "main course" ["fish" "chips" "mushy peas"]
  "desert" ["cheese" "wine"]}}

{:meal
 {:starter ["soup" "bread rolls"]
  :main-course  ["fish" "chips" "mushy peas"]
  :desert ["cheese" "wine"]}}

{:meal
 {:ingredience []
  :recipe []}}

(map
 inc
 [1 2 3 4 5])
