(ns clojure-through-code.composing-functions)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; map over collections with partial


(map (fn [animal] (str animal "s")) ["pig" "cow" "goat" "cat" "dog" "rabbit"])

;; or using the syntactic sugar for an anonymous function we get

(map #(str % "s") ["pig" "cow" "goat" "cat" "dog" "rabbit"])

;; by default map returns a list/sequence. We can specify a vector be returned instead using mapv

(mapv #(str % "s") ["pig" "cow" "goat" "cat" "dog" "rabbit"])


;; what about sheep, where the plural is sheep?  We would want to add a condition or filter somewhere

;; first lets abstact out the annonymous function

(defn pluralise
  "Pluralise a given string value"
  [string]
  (str string "s"))

;; and give a name to our collection of animals

(def animals ["pig" "cow" "goat" "cat" "dog" "rabbit"])

(map pluralise animals)


;; using an if statement as a filter 
(defn pluralise
  "Pluralise a given string value"
  [string]
  (if (= string "sheep")
    string
    (str string "s")))

(map pluralise animals)

;; but there are quite a lot of things that do not have a plural form

;; define a collection of things that are not plural

(def non-plural-words ["deer" "sheep" "shrimp" ])

(defn pluralise
  "Pluralise a given string value"
  [string]
  (if (some #{string} non-plural-words)
    string
    (str string "s")))

(def animals ["pig" "cow" "goat" "cat" "dog" "rabbit" "sheep" "shrimp" "deer"])

(map pluralise animals)

;; to keep the function pure, we should really pass the non-plural-words as an argument

(defn pluralise
  "Pluralise a given string value"
  [string non-plural-words]
  (if (some #{string} non-plural-words)
    string
    (str string "s")))

;; using an anonymous function we can send the two arguments required to the pluralise function, as map will replace the % character with an element from the animals collection for each element in the collection.
(map #(pluralise % non-plural-words) animals)


;; we could also use a partical function, saving on having to create an anonymous

(defn pluralise
  "Pluralise a given string value"
  [non-plural-words string]
  (if (some #{string} non-plural-words)
    string
    (str string "s")))

;; Now we can call pluralise by wrapping it as a partical function.  The argument that is the non-plural-words is constant, its the individual elements of animals I want to get out via map.  So when map runs it gets an element from the animals collection and adds it to the call to pluralise, along with non-plural-words
(map (partial pluralise non-plural-words) animals)

;;; Its like calling (pluralise non-plural-words ,,,) but each time including an element from animals where the ,,, is. 

;; at first I was getting incorrect output, ["deer" "sheep" "shrimp"], then I realised that it was returning the non-plural-words as string, so the arguements from the partial function were being sent in the wrong order.  So I simply changed the order in the pluralise function and it worked.

;; I checked this by adding some old-fashioned print statement.  There are probably better ways to do that in Clojure though.

(defn pluralise
  "Pluralise a given string value"
  [non-plural-words string]
  (if (some #{string} non-plural-words)
    (do
      (println (str string " its true"))
      string)
    (do
      (println (str string " its false"))
      (str string "s"))))

;; comp

;; Takes a set of functions and returns a fn that is the composition
;; of those fns.  The returned fn takes a variable number of args,
;; applies the rightmost of fns to the args, the next
;; fn (right-to-left) to the result, etc.

((comp str +) 8 8 8)

(filter (comp not zero?) [0 1 0 2 0 3 0 4])



(defn f1 "append 1 to string x" [x] (str x "1"))
(defn f2 "append 2 to string x" [x] (str x "2"))
(defn f3 "append 3 to string x" [x] (str x "3"))

(f1 "a") ; "a1"

(def g (comp f3 f2 f1 ))

(g "x") ; "x123"

;; (g "a" "x")
;; 1. Unhandled clojure.lang.ArityException
;;    Wrong number of args (2) passed to: user/f1

;; because f1 only takes 1 arg




;; example of apply

;; apply just takes all of its args, and feed to the function as multiple args, like unwrap the bracket

(apply str ["a" "b"]) ; "ab"

(apply str "1" ["a" "b"]) ; "1ab"

(apply str "1" "2" ["a" "b"]) ; "12ab"

;; here's what str does

;; str takes 1 or more args of string, and return a joined string
(str "a" "b") ; "ab"

;; can be just 1 arg
(str "a" ) ; "a"

;; if arg is not a string, it's converted to string
(str "a"  ["a" "b"]) ; "a[\"a\" \"b\"]"

(str "a" 1) ; "a1"






;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Misc

;; clojure.core/trampoline
;; (trampoline f args)
;; call f with args. If it returns a function, call it again (with no args). Repeat until it returns a value that is not a function, return that value. 

;; clojure.core/constantly
;; (constantly x)
;; Returns a function that takes any number of arguments and returns x. 

;; clojure.core/complement
;; (complement f)
;; Takes a fn f and returns a fn that takes the same arguments as f, has the same effects, if any, and returns the opposite truth value. 

;; clojure.core/memoize
;; (memoize f)
;; Returns a memoized version of a referentially transparent function. The memoized version of the function keeps a cache of the mapping from arguments to results and, when calls with the same arguments are repeated often, has higher performance at the expense of higher memory use. 

