;; ClojureBridge London challenges - solved and explained
;;
;; All the exercises from https://clojurebridgelondon.github.io/workshop
;; Solutions and discussions on how the exercises were solved
;;
;; Author: @jr0cket


(ns clojure-through-code.clojurebridge-exercises)


;; 01 Calculate the total number of years from all the following languages
;;
;; Clojure (10 years)
;; Haskell (27 years)
;; Python (26 years)
;; Javascript (21 years)
;; Java (22 years)
;; Ruby (22 years)
;; C (45 years)
;; C++ (34 years)
;; Lisp (59 years)
;; Fortran (60 years)
;; You can use the list of years here as a convenience 10 27 26 21 22 22 45 34 59 60

;; calculate the total by adding the age of each language together

(+ 10 27 26 21 22 22 34 45 59 60)


;; => 326

;; There are two values the same, so we could calculate the total as follows

(+ 10 27 26 21 (* 22 2) 34 45 59 60)


;; Find the average age of languages by adding the ages of all the language ages, then divide that total age with the number of languages

(/ (+ 10 27 26  21 22 22 45 34 59 60)
   10)


;; We can use the count function to find out how many language we have if we put them into a collection (eg.)
(/ (+ 10 27 26  21 22 22 45 34 59 60)
   (count '(10 27 26  21 22 22 45 34 59 60)))


;; 02 Convert between time and numbers
;;

;; Assuming its been 2 hours and 20 minutes since you woke up, lets calculate the time in minutes:

;; We add the result of multiplying 2 hours by 60 to get the number of minutes with the 25 minutes
(+ (* 2 60) 25)


;; To get the time in seconds, we can take the previous expression and wrap it with another expression that multiplies the result by 60.
(* (+ (* 2 60) 25) 60)


;; Assuming it has been 428 seconds since the workshop started, the number of minutes could be calculated like this:
(quot 428 60)


;; And the number of seconds calculated like this:
(rem 428 60)


;; 7 hours is 420 minutes, so there are 8 minutes remaining that do not fit into a whole hour.
;; So 428 minutes is the same as 7 hours and 8 minutes


;; 03 Strings
;;

;; Using a function you can create a string and join values to create a string
(str "My favorite colours are" " 23 " "green" " " "and" " " "purple")


;; Use a function to see if a string contains a colour.
;; Does the string "Rachel of York gave brown bread in vans" contain the colour brown?

;; The clojure.string library defines many functions that specifically work with String types.
(clojure.string/includes? "Rachel of York gave brown bread in vans" "brown")


;; Fixing spelling mistakes
;; One aspect of fixing spelling mistakes is to find and replace incorrect words.
;; The clojure.string library provides the replace function.

;; Replace in this string the characters ovr with the characters over

(clojure.string/replace "simplicity ovr complexity" "ovr" "over")


(clojure.string/replace "coed as data as coed" "coed" "code")


;; NOTE: Briefly discuss libraries and namespaces
;; clojure.core is included by default in all projects
;; on the JVM host, so is java.lang
;; clojure.xxx libraries can be used by fully qualifying their namespace
;; Other Clojure libraries need to be added as a project dependency too.



;; Simple Palendrome Checker
;; A Palindrome is a word that is spelt the same whether it is written backwards or forwards. So when you reverse the word it should still look the same.

;; Using functions on strings that are not part of the clojure.string library can give 'interesting' results.
;; Some clojure.core functions treat a String as a sequence of characters and would return a sequence of characters rather than a string.

(= "radar" (reverse "radar"))


;; The clojure.core/reverse function returns a list the individual characters in reverse order, rather than reversing the string as a single value.

;; Using the clojure.string/reverse function keeps the value as a string
(= "radar" (clojure.string/reverse "radar"))


;; 04 Assignment (binding symbols to values)
;;

;; The = function is used to compare values

;; We can use def to give a name to something we already know, like a string

(def jenny-loves "Jenny loves rubarb crumble and vanilla custard")

jenny-loves


;; We can also use def to give a name to something we dont know yet, eg. a calculation.

(def mangoes 4)
(def oranges 12)
(def total-fruit (+ mangoes oranges))
(def average-fruit-amount (/ total-fruit 2))
average-fruit-amount


;; NOTE: Its common to use a map to define multiple values in a namespace,
;; rather than multiple individual def functions

(def fruit-stock
  {:mangos   4
   :oranges 12})


(get fruit-stock :mangos)


;; 05 Collections - Vectors
;;

;; A vector of the temperatures for the next week
[9 2 -3 4 5 9 4]


;; assuming the first temperature is for Monday, then to get Thursday we can write
;; The index of a vector starts at zero

(nth [9 2 -3 4 5 9 4] 3)


;; Sequences - an abstraction over collections

(first  [9 2 -3 4 5 9 4])
(second [9 2 -3 4 5 9 4])
(last   [9 2 -3 4 5 9 4])
(rest   [9 2 -3 4 5 9 4])


;; how to we get the third value from the vector ["Birthdays are" "full of" "presents" "that you" "always dreamd" "of having"]

;; We can call one function and use its return value as an argument to another function.

;; The `rest` function returns a new collection with all values but the first one.
;; Using the `second` function new collection, we ca second value can then be returned from the new collection
(second
  (rest
    ["Birthdays are" "full of" "presents" "that you" "always dreamd" "of having"]))


;; Using Local Assignment
;; Use a local name to hold intermediary values in the processing of the collection.

;; the let function assignes a name to our collection of values
;; then we get the value in third place by using the name.

(let [rest-of-strings
      (rest ["Birthdays are" "full of" "presents" "that you" "always dreamd" "of having"])]
  (second rest-of-strings))


;; Find the age of the youngest programming language
;; The ages are not in order, so you cant just get the first value.

(first (sort [10 27 26 21 22 22 45 34 59 60]))


;; The min function will simplify our expression
(min [10 27 26 21 22 22 45 34 59 60])


;; NOTE there are over 600 functions in clojure.core so there is often a function you are looking for to simplify you code



;; Clojure compares values rather than types (in general)

(= [1 2 3] [1 2 3])


;; => true
(= [1 2 3] [4 5 6])
(= [1 2 3] [3 2 1])


;; => false
(= ["Hello" "World"] ["Hello" "World" "Wide" "Web"])
(= '(1 2 3) [1 2 3])


;; 06 Collections - Maps
;;

;; Maps are key-value pairs
;; Maps can be self describing if you use meaningful names for the keys

(count {:firstname "Sally" :lastname "Brown"})

(def sally {:firstname "Sally" :lastname "Brown"})

(merge {:first "Sally"} {:last "Brown"})

(assoc {:first "Sally"} :last "Brown")

(dissoc {:first "Sally" :last "Brown"} :last)

(get sally :lastname)


;; merge - join two maps together
;; assoc - add a key-value to a map
;; assoc-in - add a key-value to a nested map
;; update - change an existing value in a map using a function
;; update-in - change an existing value in a nested map using a function

;; keys - return the keys of a map
;; vals - return the values of a map


;; The assoc function can be used by assigning a new value to an existing key.

(def hello {:count 1 :words "hello"})

(assoc hello :count 0)


(assoc hello :words "Hello Clojure World")


;; The update function applies a function to the existing value to create a new value for a specific key

(def hello-update {:count 1 :words "hello"})

(update hello-update :count inc)


(update hello-update :words str ", world")


;; The update-in function works like update, but takes a vector of keys to update at a path to a nested map.

(def startrek-cat
  {:pet
   {:age 5 :name "Khan"}})


(update-in startrek-cat [:pet :age] + 1)


;; Map extraction with get
;; A map is a key-value pair, the key is used to get a value from a map.

;; If the key does not exist, then a nil value is returned.

(get {:firstname "Sally" :lastname "Brown"} :firstname)


(get {:firstname "Sally"} :lastname)


;; A default value can be included with the get function, so if a key is not found in the map, that default value is returned.

(get {:firstname "Sally"} :lastname "Unknown")


;; Keyword keys behave like functions, as to maps
;; When a key is a keyword then that keyword can be used as a function to lookup values in a map.

(:firstname {:firstname "Sally" :lastname "Brown"})


;; A map can also act like a function when given a keyword as an argument

({:firstname "Sally" :lastname "Brown"} :firstname)

({"firstname" "sally"} "firstname")

(def sally {(count [1 2]) "aa"})

sally


;; 07 - Filtering Collections and anonymous functions
;;

(filter odd? [1 2 3 4])


;; Write a function to use with filter that will remove the word "we" from the collection: ["are" "we" "there" "yet"]

(filter (fn [word] (= "we" word)) ["are" "we" "there" "yet"])


(filter (fn [word] (not= "we" word)) ["are" "we" "there" "yet"])


;; Or the short-hand form of anonymous function. The % acts as a placeholder for the function argument

(filter #(not= "we" %) ["are" "we" "there" "yet"])


;; Rather than use filter, we can use remove which is the inverse

(remove #(= "we" %) ["are" "we" "there" "yet"])


;; 08 - First Class Functions (map and reduce)
;;

;; map is a function that takes another function as an argument, along with a collection.
;; map calls the function provided to it on each member of the collection, then returns a new collection with the results of those function calls.

(map even? [0 1 2 3 4])


;; Count the number of characters in each word for a collection of strings eg. ["a" "abc" "abcdefg"]

(map count ["a" "abc" "abcdefg"])


;; reduce is another function that takes a function and collection as arguments.
;; (reduce some-fn ["my" "collection" "of" "values"])

;; Use reduce with a function that adds numbers together, eg. [10 20 30 40 50]

(reduce + [30 60 90])


;; Think of a function that joins strings together and use it with reduce to join the words in a collection eg ["h" "e" "l" "l" "o" " " "Clojure"]

(reduce str ["h" "e" "l" "l" "o"])

(reduce str ["h" "e" "l" "l" "o" " " "Clojure"])


;; Write map and reduce functions to create the map reduce sandwich

;; Create a collection of the raw ingredients for our sandwich

(def raw-ingredients ["bread" "cucumber" "pepper" "tomato" "lettuce" "onion"])


;; Create a function to "slice" the raw ingredients so to prepare to be made into a sandwich.

(defn prepare
  [all-ingredients]
  (map (fn [ingredient] (str "sliced " ingredient)) all-ingredients))


(prepare raw-ingredients)


;; => ("sliced bread" "sliced cucumber" "sliced pepper" "sliced tomato" "sliced lettuce" "sliced onion")


;; Reduce our ingredients to a sandwich

(defn make-sandwich
  [prepared-ingredience]
  (reduce str (interpose ", " prepared-ingredience)))


(str "A tasty sandwich made with " (make-sandwich (prepare raw-ingredients)))


;; The same thing can be written sequentially using the threading macro
(->> ["bread" "cucumber" "pepper" "tomato" "lettuce" "onion"]
     (map #(str "sliced " %) ,,,)
     (interpose ", " ,,,)
     (reduce str ,,,)
     (str "I have a tasty sandwich made with " ,,,))


;; Name Smash
;; Take two names and smash them together to create a new name

(def students ["John Stevenson" "Mani Sarkar"])


;; Split each string using the regular expression for space character.
;; Map split as an anonymous function over the student collection

(map #(clojure.string/split % #" ") students)


;; => (["John" "Stevenson"] ["Mani" "Sarkar"])


;; We can also flatten the result to make it look nicer

(flatten (map #(clojure.string/split % #" ") students))


;; => ("John" "Stevenson" "Mani" "Sarkar")



;; Write a function called name-split that a full name as a string and return two seperate strings, one for the first name and one for the last name.

(defn name-split
  "Splits a name into first & last names"
  [name]
  (clojure.string/split name #" "))


;;  Jumble the names
;; For example, take the first name from the first person and join it with the last name from the second person

(defn jumble-names
  [names]
  (let [first-person (first names)
        second-person (second names)
        first-person-first-name (first (name-split first-person))
        second-person-second-name (second (name-split second-person))]
    (str "Hello " first-person-first-name second-person-second-name)))


;; Call our function with an argument of the student collection.
(jumble-names students)


;; 09 Conditionals
;;

;; In English we say someone came 1st, rather than someone came 1.
;; Write a function called position with an argument called number
;; if the number equals 1, then the function should return "1st" If number is not 1, then return an error message, such as: "Sorry, the number is not supported"

;; Using if function for a single condition

#_(if (condition)
  true
  false)


(defn position
  [number]
  (if (= number 1)
    (str number "st")
    (str "Sorry, the number " number " is not supported")))


(position 2)


;; TODO fix in ClojureBridge London workshop content



;; cond function can evaluate multiple conditions

;; (cond
;;   predicate1 expression-to-evaluate-when-predicate1-is-true
;;   predicate2 expression-to-evaluate-when-predicate2-is-true
;;   ...
;;   :else      expression-to-evaluate-when-all-above-are-false)

;; To create the position function with cond


(defn positions
  [number]
  (cond
    (= number 1) "1st"
    (= number 2) "2nd"
    (= number 3) "3rd"
    :else   (str number "th")))


(positions 3)


;; => "3rd"
(positions 4)


;; Temperature conversion with cond
;;  Write a function which converts temperatures

;; (temperature-in-celcius 32.0 :fahrenheit)    ;=> 0.0
;; (temperature-in-celcius 300  :kelvin)        ;=> 26.85
;; (temperature-in-celcius 22.5 :celcius)       ;=> 22.5
;; (temperature-in-celcius 22.5 :fake)          ;=> "Unknown scale: :fake"
;; If an unknown temperature scale is used, an error message should be returned


;; Formulas to convert temperatures
;; Fahrenheit to Celcius: (* (- Fahrenheit 32) 5/9) = Celcius
;; Kelvin to Celcius: (+ Kelvin 273.15) = Celcius

(defn temperature-in-celcius
  [temperature scale]
  (cond
    (= scale :celcius)    temperature
    (= scale :fahrenheit) (* (- temperature 32) 5/9)
    (= scale :kelvin)     (- temperature 273.15)
    :else                 (str "Unknown scale: " scale)))


(temperature-in-celcius 32.0 :fahrenheit)


;; => 0.0
(temperature-in-celcius 300  :kelvin)
(temperature-in-celcius 22.5 :celcius)
(temperature-in-celcius 22.5 :gibberish)


;; 10 iterate with for (list comprehension)
;;

;; Create a combination lock

;; Generate numbers with range
(range 10)


;; Use for to create a range of numbers for each combination tumbler
;; then combine them together

(for [tumbler-1 (range 10)
      tumbler-2 (range 10)
      tumbler-3 (range 10)]
  [tumbler-1 tumbler-2 tumbler-3])


;; Calculate the total number of combinations

(count
  (for [tumbler-1 (range 10)
        tumbler-2 (range 10)
        tumbler-3 (range 10)]
    [tumbler-1 tumbler-2 tumbler-3]))


;; Make the combinations harder to guess
;; only allow the combinations where each tumbler wheel has a different number, so exclude combinations like 1-1-1, 1-2-2, 1-2-1, etc.
;; How many combinations does that give us?

;; for can set conditions that

(for [tumbler-1 (range 10)
      tumbler-2 (range 10)
      tumbler-3 (range 10)
      :when (or (not= tumbler-1 tumbler-2)
                (not= tumbler-2 tumbler-3)
                (not= tumbler-3 tumbler-1))]
  [tumbler-1 tumbler-2 tumbler-3])


;; NOTE editor truncates to first 100 results


(for [tumbler-1 (range 10)
      tumbler-2 (range 10)
      tumbler-3 (range 10)
      :when (or (= tumbler-1 tumbler-2)
                (= tumbler-2 tumbler-3)
                (= tumbler-3 tumbler-1))]
  [tumbler-1 tumbler-2 tumbler-3])


;; Polymorphic function via number of arguments
(defn hello-poly
  ([] (hello-poly "Jane Doe"))
  ([name] (str "Hello my friend " name)))


;; Calling without arguments returns a default result
(hello-poly)


;; => "Hello my friend Jane Doe"

(hello-poly "Mani")


;; => "Hello my friend Mani"
