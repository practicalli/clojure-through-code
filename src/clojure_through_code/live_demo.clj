;; Get Functional with Clojure

;;
;; What is Clojure

;; A general purpose programming language
;; - a modern LISP
;; - hosted on the Java Virtual Machine (JVM), Microsoft CLR, JavaScript Engines (V8, Node)

;;
;; Why Clojure

;; Functional
;; - all functions return a value & can be used as arguments / parameters
;; - deterministic: given the same input you get the same result

;; Dynamic
;; - types: making it faster to develop & test ideas
;; - runtime: continuously compiling & running code for fast feedback

;; Immutable
;; - everything is immutable by default
;; - easier to reason and build concurrent systems
;; - easier to scale via paralelism

;; Extensibility & Interop
;; - Macros allows the community to extend the language
;; - use other libraries on the hosted runtime (JVM, CLR, JS)

;; Persistent Data Structures
;; - list, map, vector, set are all immutable
;; - return new copies instead of being changed
;; - efficient memory sharing between data structure copies

;; Managing State with Software Transactional Memory (STM)
;; - atoms / refs wrap data structures to make mutable state
;; - STM controls state changes (like having an in-memory acid database)

;; Learning a new language helps improve how you use your current languages



;;
;; Namespaces

;; Define the scope of your functions and data structures, similar in concept to java packages
;; Namespaces encourage a modular / component approach to Clojure

(ns clojure-through-code.live-demo)


;; This namespace matches the directory structure of the project

;; project-name
;; - src
;;   - clojure_through_code
;;     - live_demo.clj


;;
;; Clojure Syntax

;; Clojure uses a prefix notation and () [] : {} # @ ! special characters
;; no need for lots of ; , and other unneccessary things.

;; bind a name to data
(def my-data [1 2 3 "frog"])

my-data


;; See the types used in the hosted language 
(type [1 2 3])

(def conference-name "CodeMotion: Tel Aviv")
(type conference-name)


;; bind a name to a function (behaviour)
(defn do-stuff
  [data]
  (str data))




;;
;; Evaluating Clojure

;; Values evaluate to themselves

1
"String"
[1 2 3 4 "Vector"]
{:key "value"}


;; Names can be bound to data or functions and when you evaluate the name it returns a value

;; Names built into the Clojure language
;; The full clojure version, major, minor & point version of Clojure
*clojure-version*


;; Calling simple functions

(+ 1 2 3 4 5)


;; precidence is explicit - no uncertanty
(+ 1 2 (- 4 -2) (* (/ 36 3) 4) 5)


;; calling a function we defined previously
(do-stuff my-data)


;; The first element of a list is evaluated as a function call.

;; In Clojure everything is in a List, after all its a dialect of LISP (which stands for List Processing).
;; Effectively you are writing your Clojure coe as an Abstract Syntax Tree (AST), so your code represents the structure.


;;
;; Using Java Interoperability

;; java.lang is part of the Clojure runtime environment, so when ever you run a Clojure REPL you can call any Java methods
;; without having to import them or include any dependencies

;; Using java.lang.String methods
(.toUpperCase "fred")


;; From java.lang.System getProperty() as documented at:
;; http://docs.oracle.com/javase/8/docs/api/java/lang/System.html

;; Using java.lang.System static methods
(System/getProperty "java.version")

(System/getProperty "java.vm.name")


;; Make the result prettier using the Clojure str function
(str "Current Java version: " (System/getProperty "java.version"))


;; Functions can be used as arguments to function calls
(slurp "project.clj")
(read-string (slurp "project.clj"))
(nth (read-string (slurp "project.clj")) 2)


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


;; Macros
;; the same behaviour as above can be written using the threading macro
;; which can make code easier to read by reading sequentially down the list of functions.

(->
  "./project.clj"
  slurp
  read-string
  (nth 2))


;; using the macroexpand function you can see what code is actually created

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
  (str "the" " " "treading" " " "macro")
  (str " in" " " "action."))


;; Using the ->> threading macro, the result of a function is passed as the last parameter
;; of the next function call.  So in another simple series of str function calls,
;; our text comes out backwards.

(->>
  (str " This")
  (str " is")
  (str " backwards"))


;; add all project information to a map
(->> "project.clj"
     slurp
     read-string
     (drop 2)
     (cons :version)
     (apply hash-map)
     (def project))


;; project
;;
;; Working with strings & side-effects

;; You could use the Java-like function `println` to output strings.

(str "Hello, whats different with me?  What value do I return")


;; However, something different happens when you evaluate this expression.  This is refered to as a side-effect because when you call this function it returns nil.  The actual text is output to the REPL or console.

;; In Clojure, you are more likely to use the `str` function when working with strings.
(str "Hello, I am returned as a value of this expression")


;; join strings together with the function str
(str "Hello" ", " "HackTheTower UK")


;; using println shows the results in console window, as its a side affect
;; using srt you see the results of the evaluation inline with the code,
;;  as the result of a definition or an expression.

;; Avoid code that creates side-effects where possible to keep your software less complex.
;; using the fast feedback of the REPL usually works beter than println statements in debuging


;;
;; Simple math to show you the basic structure of Clojure

;; Math is straightforward
(+ 1 1 2489 459 2.)
(- 2 1)
(* 1 2)
(/ 2 1)
(/ 22 7)
(/ 22 7.0)
(/ 5 20)
(/ 38 4)
(/ (* 22/7 3) 3)


;; Ratios delay the need to drop into decimal numbers.  Once you create a decimal number then everything it touches had a greater potential to becoming a decimal.

;; Prefix motation means we can define a value of 22/7 as a value without it being interprested as an operation or function to evaluate.

;; Clojure uses Prefix notation, so math operations on many arguments is easy.
(+ 1 2 3 4 5)
(+ 1 2 (* 3 4) (- 5 6 -7))

(+)
(*)
(* 2)
(+ 4)
(+ 1 2 3)
(< 1 2 3)
(< 1 3 8 4)


;; Clojure functions typically support a variable number of arguments (Variadic functions).

;; Functions can also be defined to respond with different behaviour based on the number of arguments given (multi-arity).  Arity means the number of arguments a function can be called with.

(inc 3)
(dec 4)

(min 1 2 3 5 8 13)
(max 1 2 3 5 8 13)

(apply + [1 2 3])

(apply / [1 2 3])
(/ 53)

(map + [1 2 3.0] [4.0 5 6])

(repeat 4 9)


;; Data oriented
;; Clojure & FP typically have many functions to manipulate data


;;
;; Equality & Identity
;; Clojure values make these things relatively trivial

;; Equality is =

(= 1 1)
(= 2 1)

(identical? "foo" "bar")
(identical? "foo" "foo")
(= "foo" "bar")
(= "foo" "foo")

(identical? :foo :bar)
(identical? :foo :foo)


;; Equality is very useful when your data structures are immutable

;; Keywords exist as identifiers and for very fast comparisons

(def my-map {:foo "a"})

my-map
(def my-map [1 2 3 4])
(= "a" (:foo my-map))


;; Use the not function for logic

(not true)


(if nil
  (str "Return if true")
  (str "Return if false"))


(defn is-small?
  [number]
  (if (< number 100) "yes" "no"))


(is-small? 50)


;;
;; Persistent Data Structures

;; Lists
;; you can use the list function to create a new list
(list 1 2 3 4)
(list -1 -0.234 0 1.3 8/5 3.1415926)
(list "cat" "dog" "rabit" "fish" 12 22/7)
(list :cat :dog :rabit :fish)


;; you can mix types because Clojure is dynamic and it will work it out later,
;; you can even have functions as elements, because functions always return a value
(list :cat 1 "fish" 22/7 (str "fish" "n" "chips"))


;; (1 2 3 4)

;; This list causes an error when evaluated


(quote (1 2 3 4))

'(1 2 3 4)
'(-1 -0.234 0 1.3 8/5 3.1415926)
'("cat" "dog" "rabit" "fish")
'(:cat :dog :rabit :fish)
'(:cat 1 "fish" 22/7 (str "fish" "n" "chips"))


;; one unique thing about lists is that the first element is always evaluated as a function call,
;; with the remaining elements as arguments.



;; Vectors
(vector 1 2 3 4)
[1 2 3 4]
[1 2.4 3.1435893 11/4 5.0 6 7]
[:cat :dog :rabit :fish]
[:cat 1 "fish" 22/7 (str "fish" "n" "chips")]
[]


;; Maps
;; Key - Value pairs, think of a Hash Map

{:key "value"}

(:live-the-universe-and-everything 42)


(def starwars-characters
  {:luke   {:fullname "Luke Skywarker" :skill "Targeting Swamp Rats"}
   :vader  {:fullname "Darth Vader"    :skill "Crank phone calls"}
   :jarjar {:fullname "JarJar Binks"   :skill "Upsetting a generation of fans"}})


;; Now we can refer to the characters using keywords

;; Using the get function we return all the informatoin about Luke
(get starwars-characters :luke)


;; By wrapping the get function around our first, we can get a specific
;; piece of information about Luke
(get (get starwars-characters :luke) :fullname)


;; There is also the get-in function that makes the syntax a little easier to read
(get-in starwars-characters [:luke :fullname])
(get-in starwars-characters [:vader :fullname])


;; Or you can get really Clojurey by just talking to the map directly
(starwars-characters :luke)
(:fullname (:luke starwars-characters))
(:skill (:luke starwars-characters))

(starwars-characters :vader)
(:skill (:vader starwars-characters))
(:fullname (:vader starwars-characters))


;; And finally we can also use the threading macro to minimise our code further

(-> starwars-characters
    :luke)


(-> starwars-characters
    :luke
    :fullname)


(-> starwars-characters
    :luke
    :skill)


;; Combination of data structures

{:starwars
 {:characters
  {:jedi   ["Luke Skywalker"
            "Obiwan Kenobi"]
   :sith   ["Darth Vader"
            "Darth Sideous"]
   :droids ["C3P0"
            "R2D2"]}
  :ships
  {:rebel-alliance  ["Millenium Falcon"
                     "X-wing figher"]
   :imperial-empire ["Intergalactic Cruser"
                     "Destroyer"
                     "Im just making these up now"]}}}


;; Sets



;;
;; Types in Clojure

;; There are types underneath Clojure, however Clojure manages them for your
;; You can take a peek at them if you want...


;; Vectors and Lists are java classes too!
(class [1 2 3])
(class '(1 2 3))
(class "Guess what type I am")


;;
;; Using Functions


;; Lets define a very simple anonymous function, that returns a string

(fn [] "Hello Clojurian, hope you are enjoying the REPL")


;; Anonnymous function that squares a number

(fn [x] (* x x))


;; We can also give a name to a function using def

(def i-have-a-name (fn [] "I am not a number, I am a named function - actually we call the name a symbol and it can be used as a reference to the function."))

(i-have-a-name)


;; You can shorten this process by using defn
(defn i-have-a-name
  []
  "Oh, I am a new function definition, but have the same name (symbol).")


(i-have-a-name)


;; The [] is the list of arguments for the function.

(defn hello
  [name]
  (str "Hello there " name))


(hello "Steve") ; => "Hello Steve"

;; You can also use the annonymous function shorthand, # (), to create functions, (not that useful in this simple example).  The %1 placeholder takes the first argument to the function.  You can use %1, %2 and %3

(def hello2 #(str "Hello " %1 ", are you awake yet?"))
(hello2 "Mike")


;; A function definition that calls different behaviour based on the number of
;; arguments it is called with

(defn greet
  ([] (greet "you"))
  ([name] (print "Hello" name)))


(greet)


;; When greet is called with no arguments, then the first line of the functions behaviour is called.  If you look closely, you see this is not adding duplicate code to our function as

(greet "World")


;; Refactor greet function

(defn greet
  ([] (greet "you"))
  ([name] (greet name 21 "London"))
  ([name age address] (print "Hello" name, "I see you are" age "and live at" address)))


;; Variadic functions

(defn greet
  [name & rest]
  (str "Hello " name " " rest))


(greet "John" "Paul" "George" "Ringo")


;; => Hello John (Paul George Ring)


;; unpack the additional arguments from the list

(defn greet
  [name & rest]
  (apply print "Hello" name rest))


;; Using functions as parameters


;; Pattern mantching example

;; The classic fizzbuzz game were you substitute any number cleanly divisible by 3 with fix and any number cleanly divisible by 5 with buzz.
;; If the number is cleanly divisible by 3 & 5 then substitute fizzbuzz.


;; Include the library that has the match function

(require '[clojure.core.match :refer [match]])


(defn fizzbuzz
  [number]
  (match [(mod number 3) (mod number 5)]
    [0 0] :fizzbuzz
    [0 _] :fizz
    [_ 0] :buzz
    :else number))


;; This is an example of a simple pattern matching problem.

;; First we calculate the modulus of the number given as an argument by 3 then the same number by 5.
;; If the modulus value is 0 then the number is divisible exactly without remainder.
;; The result of these two function calls are the elements of a vector (an array-like strucutre)
;;
;; Using the require function we include the match function from the library clojure/core.match
;; https://github.com/clojure/core.match
;; (match may seem similar to a case statement from other languages).
;; We use match to compare the two results returned from the modulus functions.

;; There are 3 possible patterns to match against, each returns the appropriate value (fizz, buzz, or fizzbuzz)
;; If there is no match, then the original number is returned.
;; The underscore character, _, means that any number will match in that position.


;; If you would like to try this out in the REPL, dont forget to include this namespace if you are not currently in it
;; (require '[clojure-through-code.live-demo :refer :all])

;; Now we can call fizbuzz for a specfic number

(fizzbuzz 1)
(fizzbuzz 3)
(fizzbuzz 4)
(fizzbuzz 15)


;; If we want to convert a sequence of numbers, then we can call fizzbuzz over a collection (eg, a vector) of numbers
;; using the map function

(map fizzbuzz [1 2 3 4 5])


;; We can make a function called play-fizzbuzz to make it easy to use
;; The function takes the highest number in the range and generates all the numbers from 0 to that number.
;; Finally, we convert the results into strings
(defn play-fizbuzz
  [max-number]
  (->> (range max-number)
       (map fizzbuzz)
       (map str)))


;; Now, lets call our play-fizzbuzz function with the highest number in the range of numbers we want to play fizzbuzz on.
(play-fizbuzz 30)


;;
;; Java Interoperability

(def hello-message
  (str "Hello " conference-name))


;; Create a simple Swing dialog box

(javax.swing.JOptionPane/showMessageDialog nil hello-message)


;;
;; Working with mutable state


;; Using an atom

;; We want to manage players for an online card-game
;; Use a vector wraped by an atom for safe mutable state

(def players (atom []))


;; Or we may want to limit the number of players

(def players (atom [] :validator #(<= (count %) 4)))


;; the second definition of players point the var name to something new
;; we havent changed what players was, it just points to something diferent


;; Add players
(swap! players conj "Player One")


;; View players
(deref players)
@players


(swap! players conj "Player Two")
(reset! players ["Player One"])

(reset! players [])


;; Add players by name
(defn joining-game
  [name]
  (swap! players conj name))


(joining-game "Rachel")
(joining-game "Harriet")
(joining-game "Idan")
(joining-game "Joe")


;; (joining-game "Terry")         ;; cant add a third name due to the :validator condition on the atom
;; (joining-game "Sally" "Sam") ;; too many parameters

@players


(def game-account (ref 1000))
(def toms-account (ref 500))
(def dick-account (ref 500))
(def harry-account (ref 500))
(def betty-account (ref 500))

@betty-account


(defn credit-table
  [player-account]
  (dosync
    (alter player-account - 100)
    (alter game-account + 100)))


(defn add-to-table
  [name]
  (swap! players conj name))


(defn join-game
  [name account]
  ;;  (if (< account 100 )
  ;;    (println "You're broke")
  (credit-table account)
  (add-to-table name))


(join-game "Betty" betty-account)


;; Using a ref

(def all-the-cats (ref 3))


(defn updated-cat-count
  [fn-key reference old-value new-value]
  ;; Takes a function key, reference, old value and new value
  (println (str "Number of cats was " old-value))
  (println (str "Number of cats is now " new-value)))


;; Watch for changes in the all-the-cats ref
(add-watch all-the-cats :cat-count-watcher updated-cat-count)


;; evaluate the following code to increment the cats
;; view the results in the repl output (to see the println output)
(dosync (alter all-the-cats inc))


;;
;; Resources

;; clojure.org
;; clojuredocs.org
;; 4clojure.org
;; braveclojure.com
;; jr0cket.co.uk


;; different map functions
;; both functions will take a sequence of numbers for a point on a map, eg [24 -15]
;; map will return a result as a list
;; mapv will return a result as a vector

(defn degrees->radians
  [point]
  (map #(Math/toRadians %) point))


(defn degrees->radians
  [point]
  (mapv #(Math/toRadians %) point))


(degrees->radians [1 10])
