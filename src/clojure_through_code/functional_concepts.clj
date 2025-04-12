(ns clojure-through-code.functional-concepts)


#_(defn myfunction
    "This is a doc string"
    [args]
    (str "this is my behaviour"))


;;
;; Pure Functions
;;
;; A pure function is deterministic, given the same arguments the same value is returned

;; A pure function
;; - only uses arguments for data
;; - never uses shared data
;; - does not affect the state other than returning a value when called

(defn increment-numbers
  "Increment all the values in a collection of integers
   Arguments: a list or vector of integer values
   Return: a vector of integer values"
  [number-collection]
  (map inc number-collection))


(inc 1)


;; => 2

(increment-numbers [1 2 3 4 5])


;; => (2 3 4 5 6)

(defn sum-numbers
  "Sums up all the numbers in a given collection"
  [collection]
  (reduce + collection))


(sum-numbers [3 5 8 13 21 34])


;; => 84

;; ---------------------------------------
;; impure functions - side causes & side effects

(def shared-value [5 4 3 2 1])


(defn impure-increment-numbers
  [number-collection]
  (map inc shared-value))


(impure-increment-numbers [1 2 3 4 5])


;; => (6 5 4 3 2)

shared-value


;; => (5 4 3 2 1)

;; using a global value rather than the argument passed makes this function non-deterministic

;; Another example of impurity - Java Interop
(:import java.util.Date)


;; => nil

;; Create a new date object with the current date using either the new or . syntax
(new java.util.Date)


;; (java.util.Date.)

;; side causes example
(defn task-complete
  "Create a task, the date created is a side cause
  as it was not passed to the function and will change each time
  (although should always be a date value unless the signature of the function changes)"
  [task-name]
  (str "Setting task " task-name " completed on " (java.util.Date.)))


(task-complete "hack clojure")


;; A purer approach to the above

(defn task-completed-date
  [task-name completed-date]
  (str "Setting task " task-name " completed on " completed-date))


(task-completed-date "hack clojure" (java.util.Date.))


;; Generated numbers can be created outside a function and passed as an argument, keeping the function pure.

;; very basic side effect example

(defn print-to-console
  [value-to-print]
  (println "The value is:" value-to-print))


(print-to-console "a side effect")


;; ---------------------------------------
;; Clojure: anonymous functions

;; Define an anonymous in the general form as follows:

(fn [arguments] (str "some behaviour, typically using the aguments passed:" arguments))


;; To get a value from evaluating this function you need to pass it a value (or anonther function) as an argument as well as calling it as a function

((fn [arguments] (str "behaviour, typically using the aguments passed: " arguments)) "Is this the right room for an argument")


;; => "behaviour, typically using the aguments passed: Is this the right room for an argument"

;; TODO: add a more realistic use of anonymous fuctions

;; syntactic sugar for anonymous functions

;; In this example we use an anonymous function to add two numbers together

#(+ 1 2)


;; this just returns a pointer to the function.  To get the result we need to call this function as follows

(#(+ 1 2))


;; we can also pass arguments into anonymous functions

(#(inc %) 1)


;; the % is a placeholder for the argument and the argument is the value directly after the anonymous function.

;; Specific arguments can be refered to by using %1, %2, etc
;; sometimes position can be important as the following two versions of code demonstrate

(#(/ %1 %2) 24 6)

(#(/ %2 %1) 24 6)


;; These two expressions give different values (and return different types, Integer and Ratio) as the positions of the arguments have been reversed.

;; ---------------------------------------
;; Immutability - Values and Persistent data structures

;; Immutable numbers

(def two-little-ducks 22)

(inc two-little-ducks)


;; => 23

two-little-ducks


;; => 22

;; Immutable strings

(def message "Strings are immutable")

(str message "," " " "you cant change them")


;; => "Strings are immutable, you cant change them"

message


;; => "Strings are immutable"

;; local bindings are immutable

(let [five 5]
  (str "Within the let expression the value is " five))


;; five
;; => Unable to resolve symbol: five in this context

(let [number 5]
  (inc number)
  (str "The number is still " number))


;; => "The number is still 5"

;; just as with a def, a let binding can be redefined.  Any new definition over-rides the previous one.
(let [number 5
      number 6]
  number)


;; => 6

(def persistent-vector [0 1 2 3 4])


;; lets add a value to our persistent vector
(conj persistent-vector 5)

persistent-vector


;; and another..
(conj persistent-vector 6)


;; Chain two function calls using the threading macro
(-> persistent-vector
    (conj 5)
    (conj 6))


;; persistent data structures share memory, so even for large data structures the use of lists, maps, vectors & sets are efficient.

;; persistent data structures also use a relatively flat tre structure (typically 1-2 levels, up to 6 levels for very large data).  This flat structue minimises the time required to parse the tre

;; ---------------------------------------
;; Keywords

;; A simple idiom is using keywords for keys in a map

(let [fishes {:fish "trout"}]
  (get fishes :fish))


;; Keywords can be used as short-cut for the get function

(let [fishes {:fish "trout"}]
  (:fish fishes))


;; Maps also act like the get function when given a key as an argument
(let [fishes {:fish "trout"}]
  (:fish fishes))


;; ---------------------------------------
;; Sequence / List comprehension

(for [number [1 2 3]
      letter [:a :b :c]]
  (str number letter))


(mapcat (fn [number] (map (fn [letter] (str number letter)))))


;; ---------------------------------------
;; a 3 combination padlock

;; model the combinations
;; each tumbler wheel is a lazy sequence from 0 to 9
(for [tumbler-1 (range 10)
      tumbler-2 (range 10)
      tumbler-3 (range 10)]
  [tumbler-1 tumbler-2 tumbler-3])


;; now count the possible combinations
;; for is a simple macro for iterating through data structures
;; each loop creates a vector containing a combination
;; the for macro returns a list of all combinations created
;; as it iterates through each tumbler data structure.
(count (for [tumbler-1 (range 10)
             tumbler-2 (range 10)
             tumbler-3 (range 10)]
         [tumbler-1 tumbler-2 tumbler-3]))


;; => 1000

;; count the possible combinations
;; where no numbers are the same in any combination
;; e.g. dont count 1,1,1 or similar combinations
(count (for [tumbler-1 (range 10)
             tumbler-2 (range 10)
             tumbler-3 (range 10)
             :when (not (or (= tumbler-1 tumbler-2)
                            (= tumbler-2 tumbler-3)
                            (= tumbler-3 tumbler-1)))]
         [tumbler-1 tumbler-2 tumbler-3]))


;; => 720

;; lets look at the combinations again, we can see that there is always at least 2 matching values.  This is probably the opposite of what we want in real life.
(for [tumbler-1 (range 10)
      tumbler-2 (range 10)
      tumbler-3 (range 10)
      :when (or (= tumbler-1 tumbler-2)
                (= tumbler-2 tumbler-3)
                (= tumbler-3 tumbler-1))]
  [tumbler-1 tumbler-2 tumbler-3])


;; => ([0 0 0] [0 0 1] [0 0 2] [0 0 3] [0 0 4] [0 0 5] [0 0 6] [0 0 7] [0 0 8] [0 0 9] [0 1 0] [0 1 1] [0 2 0] [0 2 2] [0 3 0] [0 3 3] [0 4 0] [0 4 4] [0 5 0] [0 5 5] [0 6 0] [0 6 6] [0 7 0] [0 7 7] [0 8 0] [0 8 8] [0 9 0] [0 9 9] [1 0 0] [1 0 1] [1 1 0] [1 1 1] [1 1 2] [1 1 3] [1 1 4] [1 1 5] [1 1 6] [1 1 7] [1 1 8] [1 1 9] [1 2 1] [1 2 2] [1 3 1] [1 3 3] [1 4 1] [1 4 4] [1 5 1] [1 5 5] [1 6 1] [1 6 6] [1 7 1] [1 7 7] [1 8 1] [1 8 8] [1 9 1] [1 9 9] [2 0 0] [2 0 2] [2 1 1] [2 1 2] [2 2 0] [2 2 1] [2 2 2] [2 2 3] [2 2 4] [2 2 5] [2 2 6] [2 2 7] [2 2 8] [2 2 9] [2 3 2] [2 3 3] [2 4 2] [2 4 4] [2 5 2] [2 5 5] [2 6 2] [2 6 6] [2 7 2] [2 7 7] [2 8 2] [2 8 8] [2 9 2] [2 9 9] [3 0 0] [3 0 3] [3 1 1] [3 1 3] [3 2 2] [3 2 3] [3 3 0] [3 3 1] [3 3 2] [3 3 3] [3 3 4] [3 3 5] [3 3 6] [3 3 7] [3 3 8] [3 3 9] ...)

;; ---------------------------------------
;; generate ticket numbers in a certain patterns

;; generate all the upper case letters of the alphabet
(def capital-letters (map char (range (int \A) (inc (int \Z)))))
capital-letters


;; Exclude letters that can be mistaken for numbers
(def blacklisted #{\I \O})


(for [letter-1 capital-letters
      letter-2 capital-letters
      :when (and (not (blacklisted letter-1))
                 (not (blacklisted letter-2)))]
  (str letter-1 letter-2))


(for [number [1 2 3]
      :let [tripled (* number 3)]
      :while (odd? tripled)]
  tripled)


;; ---------------------------------------
;; Palindrome checker aproaches

;; Inline anonymous function
;; reverse will return a sequence of characters as a result
;; so we need to compare that with a seq of the string

((﻿fn [string-to-test]
      (= (seq string-to-test)
         (reverse string-to-test)))
 "racecar")


;; Using the clojure.string library we can simply compare strings as they are

((fn [string-to-test]
   (= string-to-test
      (clojure.string/reverse string-to-test)))
 "racecar")


(defn- palindrome?
  [number]
  (= (str number)
     (clojure.string/reverse (str number))))


;; create a collection of boolean results for testing each word

;; Something unusual
;; Generate two sets of numbers from 100 to 999
;; Multiply each number in each set
(apply min (for [three-digit-number-1 (range 100 1000)
                 three-digit-number-2 (range 100 1000)
                 :let [product (* three-digit-number-1 three-digit-number-2)]
                 :when (palindrome? product)]
             product))


(range 10)

(for [x (range 10) :when (odd? x)] x)

(for [x (range 10) :while (even? x)] x)


(for [x (range 10)
      y (range 10)]
  [x y])


(for [three-digit-number-1 (range 100 1000)
      three-digit-number-2 (range 100 1000)
      :let [product (* three-digit-number-1 three-digit-number-2)]]
  product)


(for [three-digit-number-1 (range 100 1000)
      three-digit-number-2 (range 100 1000)
      :let [product (* three-digit-number-1 three-digit-number-2)]
      :when (palindrome? product)]
  product)


;; ---------------------------------------
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

;; ---------------------------------------
;; Immutability

(type  "This is a string, consider me a value")

["This is a string in a vector (think of it as an array for now)"]

(def name-of-a-value ["This is a string in a vector, consider me a change"])


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
(second [1 2 3])
(rest [1 2 3])
(last [1 2 3])


;;
;; Local assignment is immutable

(:use 'clojure.string)


(defn- letter->clack
  [alphabet letter]
  (get alphabet letter))


(def sentence "Codemotion Amsterdam")
(def alphabet {"a" [001001]})


(let [words (clojure.string/upper-case sentence)]
  (map #(letter->clack alphabet %) (map str words)

       ;;
       ;; Higher Order functions

       ;; functions can be used as an arugument to other functions,
       ;; because a function always evaluates to a value

       ;; from wikipedia - is this example too abstract?  Isnt this just a simplification of the map function
       (defn twice
         [function-to-apply value]
         (function-to-apply (function-to-apply value)))

       (twice #(+ % 3) 7)))


(filter
  even?
  (range 1 10))


;; functions can be used as an arugument to other functions,
;; because a function always evaluates to a value

(map inc [1 2 3 4 5])


;; the above map funciton is roughly equivalent to
;; (conj [] (inc 1) (inc 2) (inc 3) (inc 4) (inc 5))

(map + [1 2 3 4] [5 6 7 8])

(range 1 10)

(reduce + (range 1 10))

(reduce + (quote (1 2 3 4 5 6 7 8 9)))


;; (range)

(take 10 (range))

(def sybol-name "value")


(defn function-name
  []
  (str "I am a function"))


(def function-name (fn [] (str "iaomdfiodjfod")))

(function-name)

(map (fn [number] (* number number)) [1 2 3 4])
(map #(* %1 %2) [1 2 3 4] [3 4 5 6])


;;
;; Programming with functions

;; This is how you define a function (the easy eay)

(defn my-function-name
  [parameter-list]
  (str "This is the algorithum of my method, which could be many functions workin"))


;; Pure Functions Are Referentially Transparent

;; To return the same result when called with the same argument, pure functions rely only on 1) their own arguments and 2) immutable values to determine their return value. Mathematical functions, for example, are referentially transparent

;; First Class Functions

;; Idempot - given the same input you get the same output

;; eg like Faceboook react JS components for their lightweight DOM (lightweight JS componets for each HTML element)

;; Example: Adding up values from 1 to 40 and return the overall total

(range 1 11)

(+ (range 1 11))

(apply + (range 1 40))

(reduce + (range 1 10))

(range 1 10)


;; range will act differently dependent on the context in which it is called.
;; TODO: these different types need some explaination.

(type (range))


;; => clojure.lang.Iterate

(type (take 4 (range)))


;; => clojure.lang.LazySeq

(class (take 4 (range)))


;; => clojure.lang.LazySeq

(take 4 (range))


;; => (0 1 2 3)

(type (range 1 10))


;; => clojure.lang.LongRange

(reduce + (1 2 3 4 5 6 7 8 9))


;; Both the reply and reduce functions take 2 arguments, the first is the function to apply to a data structure, the second is the data structure.  In this example, rather than type out the Integer numbers from 1 to 40, we use the range function to generate them for us.

;;
;; Sequences

;; data structures can be built by combining functions

(cons 1 (cons 2 (cons 3 (cons 4 nil))))


(->>
  nil
  (cons 4)
  (cons 3)
  (cons 2)
  (cons 1))


;; C-C RET to call cider-macroexpand-1 and show how Clojure converts this macro into its equivalent code

;; Sequence abstraction

(first '(1 2 3 4 5))
(rest '(1 2 3 4 5))
(last '(1 2 3 4 5))


(defn nth
  [items n]
  (if (= n 0)
    (first items)
    (recur (rest items) (- n 1))))


(define squares '(0 1 4 9 16 25))

(nth squares 3)


;;
;; Functor
;; - put simply a function that takes a value and a function as its arugments, eg map, apply
;; - the value, typically a collection (vector, map, string) is unpacked and each element is passed
;; - to the function that as given as the argument.
;; - The function, eg. + is applied in turn to each value and returns a structured value as a result,
;; - eg. a list or vector

(map inc [1 2 3 4 5])

(inc 1)


;; I dont think apply counts as a functor because is only returns a simple value
(apply + [1 3 5 7])


;; if that is the case, then is reduce a functor, it would suggest not

;;
;; Polymorphism

(defn name
  [args]
  ())


(defn i-am-polly
  ([] (i-am-polly "My name is polly"))
  ([message] (str message)))


(i-am-polly)
(i-am-polly "I call different behaviour depeding on arguments sent")


;;
;; recursion

;; processing a collection using recursion

(defn recursively-use-a-collection
  [collection]
  (println (first collection))
  (if (empty? collection)
    (print-str "no more values to process")
    (recursively-use-a-collection  (rest collection))))


(recursively-use-a-collection [1 2 3])


;; Other functions to consider
;; - every
;; - accumulating / accumulative
;; - keep

;;
;; Polymorphism

(defn i-am-polly
  ([] (i-am-polly "My name is polly"))
  ([message] (str message)))


(i-am-polly)
(i-am-polly "I call different behaviour depending on arguments sent")


;;
;; Recursion with polymorphism

(defn sum
  ([vals] (sum vals 0))
  ([vals accumulating-total]
   (if (empty? vals)
     accumulating-total
     (sum (rest vals) (+ (first vals) accumulating-total)))))


(sum [2 7 9 11 13])
(sum [1])
(sum [2 7 9 11 13] 9)


;;
;; Tail recursion

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


;;
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
(defn join-game
  [name]
  (swap! players conj name))


(join-game "Rachel")
(join-game "Harriet")
(join-game "Terry")      ; cant add a third name due to the :validator condition on the atom

(reset! players ["Player 1"])
(reset! players [])

deref players
@players


;; updating multiple values with ref

(def players-ref (ref [] :validator #(<= (count %) 2)))
(def game-account (ref 1000))
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


;;
;; Work in progress
;;

;;
;; reduces in depth

;; reduce takes 2 arguments, a function and a collections
;; if val is supplied, returns the result of applying the function to the val and the first item in the collection, itterating through the collection

;;
;; Pattern matching

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


;; destructuring

;; in function arguemnts, collections (maps, vectors)

;;
;; private defn & def

;; All def names are publicly available via their namespace.
;; As def values are immutable, then keeping things private is of less concern than languages built around Object Oriented design.

;; Private definitions syntax can be used to limit the access to def names to the namespace they are declared in.

;; To limit the scope of a def, add the :private true metadata key value pair.

(def ^{:private true} some-var :value)


;; or
(def ^:private some-var :value)


;; private functions could help with encapsulation and document in the code which functions form the `API' for a namespace.

(defn- my-private-function
  []
  (str "I can only be called from within my namespace"))


;; writing article on the curious case of defs

;;
;; Chaining functions - Threading macro

;; The chaining of functions may seem similar to the pipe in the Unix shell, where the output of one command is passed to the next command to process, eg. `ps ax | grep pattern'

;; clojure.core/->
;; -> is the thread first macro. It transforms code from a linear notation into nested notation. This is also known as “function chaining”. Clojure calls this “threading”.

(-> x f1) is equivalent to (f1 x)
(-> x f1 f2) is equivalent to (f2 (f1 x))
(-> x f1 f2 f3) is equivalent to (f3 (f2 (f1 x)))


;; examples of using thread first macro: ->

(defn f1
  "append \"1\" to a string"
  [x]
  (str x "1"))


(defn f2
  "append \"2\" to a string"
  [x]
  (str x "2"))


(f1 "a") ; "a1"

(f2 "a") ; "a2"

;; pipe arg to function
(-> "x" f1) ; "x1"

;; pipe. function chaining
(-> "x" f1 f2) ; "x12"

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

(let [calculated-value (* 10 (reduce + (map inc (range 5))))]
  calculated-value)


;; • ->> is similar to ->, but pass arg into the last argument position of function. clojure.core/->>

;;
;; Pass Binding

;; clojure.core/as->

;; (as-> expr name form1 form2 form3 …)
;; evaluate each form in turn, such that in form1 the name has value of expr, and in form2 the name has the value of form1's result, etc. Returns the result of the last form.

;; In this example, first the value 4 is bound to the name x.  Then the first expression is evaluated, returning 4.  This result is passed to the nested expression, so its x name becomes bound to the value 4.  Finaly we return the result of evaluating (list 3 4)

(as-> 4 x (list 3 x))


;; => (3 4)

(as-> "a" x     ; first bind the value "a" to the name x and pass on the result to the next expression
      (list 1 x)    ; (list 1 a) => (1 "a") which is passed to the next expression
      (list 2 x)    ; (list 2 (1 "a")) => (2 (1 "a"))
      (list 3 x)    ; (list 3 (2 (1 "a"))) => (3 (2 (1 "a")))
      (list 4 x)    ; (list 4 (2 (1 "a"))) => (4 (3 (2 (1 "a"))))
      (list 5 x))   ; (list 5 (4 (3 (2 (1 "a"))))) => (5 (4 (3 (2 (1 "a")))))

;; The

;;
;; Partial functions

(map (partial reduce +) [[1 2 3 4] [5 6 7 8]])


(defn sum
  "Sum two numbers together"
  [number1 number2]
  (+ number1 number2))


(sum 1 2)


;; => 3

;; (sum 1)
;; => clojure.lang.ArityException
;; => Wrong number of args (1) passed to: functional-concepts/sum

;; If we did need to call sum with fewer than the required arguments, for example if we are mapping sum over a vector, then we can use partial to help us call the sum function with the right number of arguments.

;; Lets add the value 2 to each element in our collection
(map (partial sum 2) [1 3 5 7 9])


;; TODO
;; partially applying continuations
;; -- this happens a lot when doing callbacks

;; currying in clojure

;; Currying is the process of taking some function that accepts multiple arguments, and turning it into a sequence of functions, each accepting a single argument.  Or put another way, to transform a function with multiple arguments into a chain of single-argument functions.

;; currying relies on having fixed argument sizes, whereas Clojure gets a lot of flexibility from variable argument lengths (variable arity).

;; Clojure therefore has the partial function gives results similar to currying, however the partical function also works with variable functions.

;; Partial refers to supplying some number of arguments to a function, and getting back a new function that takes the rest of the arguments and returns the final result

;; Once advantage of partial is to avoid having to write your own anonymous functions

;; Useful references
;; http://andrewberls.com/blog/post/partial-function-application-for-humans

(defn join-strings
  "join one or more strings"
  [& args]
  (apply str args))


;; the [& args] argument string says take all the arguments passed and refer to them by the name args.  Its the & character that has the semantic meaning, so any name after the & can be used, although args is common if there is no domain specific context involved.

(join-strings "Hello" " " "Clojure" " " "world")


;; ⇒ "Hello Clojure world"

(def wrap-message (partial join-strings "Hello Clojurians in "))

(wrap-message)


;; => "Hello Clojurians in "

(wrap-message "London")


;; => "Hello Clojurians in London"

;;
;; LISP features - extensibility via macros

;; examples fo macros

;; macro-expand

;;
;; Rich Hickey

;; Time, perception, values, identity, visibility, state, persistence, memory, transience, process, place, change, communication. We use these terms everyday. Yet, despite its real-world modeling flavor, OO has little to say about them. What do these terms mean, and why do they matter to our programs?

;; chaining functions

(let [calculated-value (* 10 (reduce +  (map inc (range 5))))]
  calculated-value)


;; things to figure out

;; ## Loop & Recur

;; There are also `loop` and `recur` functions in Clojure that can be used for control flow, however they are considered low level operations and higher order functions are typically used.

;; A common patter is to create a sequence of values (characters in a string, values in a map, vector, set or list) and apply one or more of clojure's sequence functions (doseq, dorun, take-while, etc.)

;; The following example reads the first username from /etc/passwd on unix like systems.

;; ```clojure
(require '[clojure.java [io :as io]])


(defn char-seq
  "create a lazy sequence of characters from an input stream"
  [i-stream]
  (map char
       (take-while
         (partial not= -1)
         (repeatedly #(.read i-stream)))))


;; process the sequence one token at a time
;; with-open will automatically close the stream for us

(with-open [is (io/input-stream "/etc/passwd")]
  (doseq [c (take-while (partial not= \:) (char-seq is))]
    ;; your processing is done here
    (prn c)))


;; ```

;; Example taken from Stack Overflow http://stackoverflow.com/questions/1053926/clojure-while-loop

;; zip should only take what it needs from the infinate sequence
(clojure.zip/zip "abc" #_(infinte sequence))


(clojure.zip/zipmap [:jen :sam :calli :jo]
                    ["map" "filter" "drop" "partition"])


;; Composable abstractions

(def alphabet
  {"A" [0 1 0 0 0 1]
   "B" [0 0 1 0 1 0]
   "C" [0 1 0 0 1 0]
   "D" [1 0 1 0 0 0]
   "E" [1 0 1 1 0 0]
   "F" [1 1 0 1 0 0]
   "G" [1 0 0 1 1 0]
   "H" [1 0 1 0 0 1]
   "I" [1 1 1 0 0 0]
   "J" [0 0 1 1 1 1]
   "K" [0 1 0 1 0 1]
   "L" [1 1 1 0 0 1]
   "M" [1 1 1 0 1 1]
   "N" [0 1 1 1 0 1]
   "O" [1 1 0 1 1 0]
   "P" [1 1 1 1 1 0]
   "Q" [1 0 1 1 1 0]
   "R" [1 1 1 1 0 0]
   "S" [0 1 1 1 1 0]
   "T" [1 0 0 1 1 1]
   "U" [0 0 1 0 1 1]
   "V" [0 1 1 0 0 1]
   "W" [1 1 0 1 0 1]
   "X" [1 0 1 0 1 0]
   "Y" [1 0 0 0 1 1]
   "Z" [1 1 0 0 1 1]
   "." [1 0 1 1 0 1]
   " " [0 0 1 0 0 0]})
