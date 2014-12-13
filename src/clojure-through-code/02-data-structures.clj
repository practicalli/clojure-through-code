;;;;;;;;;;;;;;;;;;;;;;;;
;; Creating data structures in Clojure

(ns clojure-through-code.02-data-structures)

;; Clojure has several datastructures as part of the language,
;; these data structures are used rather than defining your own types
;; Elements of Clojure data structures can be of any type,
;; Clojure works out the types only when needed

;; list ()
;; a general set of elements with a sequential lookup time

;; you can use the list function to create a new list
(list 1 2 3 4)
(list -1 -0.234 0 1.3 8/5 3.1415926)
(list "cat" "dog" "rabit" "fish")

;; you can use any "types" in your list or any other Clojure collection
(list :cat :dog :rabit :fish)

;; you can mix types because Clojure is dynamic and it will work it out later,
;; you can even have functions as elements, because functions always return a value
(list :cat 1 "fish" 22/7 (str "fish" "n" "chips"))


;; further examples of mixing types
(def five 5)

(list )
(list 1 2 3 4)
(list 1 2 "three" [4] five '(6 7 8 9))

'(1 2 3 4)

;; one unique thing about lists is that the first element is always evaluated as a function call,
;; with the remaining elements as arguments.

;; So, defining a list just using () will cause an error

;; This list definition will fail, unless you have defined a function called 1
(1 2 3 4)  ;;fail

;; There is a special function called quote that tells Clojure to just treat the
;; list as data.

(quote (1 2 3 4))

;; This syntax is actually more code to type than (list 1 2 3 4),
;; so there is a shortcut for the quote function using the ' character

'(1 2 3 4)
'(-1 -0.234 0 1.3 8/5 3.1415926)
'("cat" "dog" "rabit" "fish")
'(:cat :dog :rabit :fish)
'(:cat 1 "fish" 22/7 (str "fish" "n" "chips"))

;; The quote shortcust is uses where ever you have a list that you want to treat just as data.
;; Another example is when you are including functions from other namespaces
;;(ns my-namespace.core
;;  use 'my-namespace.library)



;; Vector []
;; A vector looks like an array and is better for random access.
;; A vector has an index to look up elements at a specific point, speeding up random access
;; Vectors and maps are the most common data structures use to hold data in Clojure

;; you can use the vector function to create a new vector
(vector 1 2 3 4)

;; Usually you just use the [] notation to create a vector to help keep your code readable
[1 2 3 4]
[1 2.4 3.1435893 11/4 5.0 6 7]
[:cat :dog :rabit :fish]
[:cat 1 "fish" 22/7 (str "fish" "n" "chips")]
[]

;; You can also put other data structures in vectors, in this example a list is an element of the vector
[1 2 3 '(4 5 6)]

;; remember we defined five earlier in the code
[1 2 3 4 (list five)]


;; Map {}
;; A key / value pair data structure
;; keys are usually defined as a :keyword, although they can be anything

;; Typicall a :keyword is used for a the key in a map, with the value being
;; a string, number or another keyword
{:key "value"}
{:key :value}

;; As with other collections, you can use anything as a key or a value,
;; they are all values underneath.
{:key 42}
{"key" "value"}


{:a 1 :b 2 :c 3 }

;; Its also quite common to have maps made up of other maps

;; Here is an example of a map made up of a :keyword as the key and
;; a map as the value.  The map representing the value is itself
;; defined with :keywords and strings
{:starwars-characters
 {:name "Luke Skywarker" :skill "Targeting Swamp Rats"}
 {:name "Darth Vader"    :skill "Crank phone calls"}
 {:name "JarJar Binks"   :skill "Upsetting a generation of fans"}}




; What is the difference between Collections & Sequences
;;;;;;;;;;;;;;;;;;;

; Vectors and Lists are java classes too!
(class [1 2 3])
(class '(1 2 3))

; A list would be written as just (1 2 3), but we have to quote
; it to stop the reader thinking it's a function.
; Also, (list 1 2 3) is the same as '(1 2 3)

; Both lists and vectors are collections:
(coll? '(1 2 3)) ; => true
(coll? [1 2 3]) ; => true

; Only lists are seqs.
(seq? '(1 2 3)) ; => true
(seq? [1 2 3]) ; => false


;;;;;;;;;;;;;;;;;;;;;;;;;
;; Be Lazy and get more done

; Seqs are an interface for logical lists, which can be lazy.
; "Lazy" means that a seq can define an infinite series, like so:
(range 4)

(range) ; => (0 1 2 3 4 ...) (an infinite series)

;; So we dont blow up our memory, just get the values we want
(take 4 (range)) ;  (0 1 2 3)

;; Clojure (and Lisps in general) tend to evaluate at the last possible moment

; Use cons to add an item to the beginning of a list or vector
(cons 4 [1 2 3]) ; => (4 1 2 3)
(cons 4 '(1 2 3)) ; => (4 1 2 3)

; Use conj to add an item to the beginning of a list,
; or the end of a vector
(conj [1 2 3] 4) ; => [1 2 3 4]
(conj '(1 2 3) 4) ; => (4 1 2 3)

; Use concat to add lists or vectors together
(concat [1 2] '(3 4)) ; => (1 2 3 4)

; Use filter, map to interact with collections
(map inc [1 2 3]) ; => (2 3 4)
(filter even? [1 2 3]) ; => (2)

; Use reduce to reduce them
(reduce + [1 2 3 4])
; = (+ (+ (+ 1 2) 3) 4)
; => 10

; Reduce can take an initial-value argument too
(reduce conj [] '(3 2 1))
; = (conj (conj (conj [] 3) 2) 1)
; => [3 2 1]



;; Playing with data structures

;; Destructuring

(let [[a b c & d :as e] [1 2 3 4 5 6 7]]
  [a b c d e])


(let [[[x1 y1][x2 y2]] [[1 2] [3 4]]]
  [x1 y1 x2 y2])

;; with strings
(let [[a b & c :as str] "asdjhhfdas"]
  [a b c str])

;; with maps
(let [{a :a, b :b, c :c, :as m :or {a 2 b 3}}  {:a 5 :c 6}]
  [a b c m])




;; It is often the case that you will want to bind same-named symbols to the map keys. The :keys directive allows you to avoid the redundancy:
(let [{fred :fred ethel :ethel lucy :lucy} m] )

;; can be written:

(let [{:keys [fred ethel lucy]} m] )

;; As of Clojure 1.6, you can also use prefixed map keys in the map destructuring form:

(let [m {:x/a 1, :y/b 2}
      {:keys [x/a y/b]} m]
  (+ a b))


; As shown above, in the case of using prefixed keys, the bound symbol name will be the same as the right-hand side of the prefixed key. You can also use auto-resolved keyword forms in the :keys directive:

(let [m {::x 42}
      {:keys [::x]} m]
  x)



;; 4Clojure - exercise 65

(= :map (if (keyword? (first(first {:a 1, :b 2}))) :map))

(if (keyword? (first(first {:a 1 :b 2}))) :map )

(first (first {:a 1 :b 2}))


(if (keyword? (first(first %))) :map )
