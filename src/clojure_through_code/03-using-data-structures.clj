;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Using Clojure data structures

(ns clojure-through-code.03-using-data-structures)


;; Defining things is easy in Clojure

;; Defining things is as simple as giving a name to a value, of course in Clojure the evaluation of a function is also a value.

(def person "Jane Doe")

;; Names are of course case sensitive, so Person is not the same as person
(def Person "James Doh")

;; Clojure uses dynamic typing, this means its trivial to mix and match different kinds of data.
;; Here we are defining a name for a vector, which contains numbers, a string and name of another def. 
(def my-data [1 2 3 "frog" person])

my-data

;; You can also dynamically re-define a name to points to a different value
(def my-data [1 2 3 4 5 "frog" person])

;; the original value that defined my-data remains unchanged (its immutable), so anything using that value remains unaffected.  Essentially we are re-mapping my-data to a new value.


;; Lets define a name to point to a list of numbers
(def my-list '(1 2 3))

;; We are returned that list of numbers when we evaluate the name

my-list

;; We can use the cons function to add a number to our list,
;; however because lists are immutable, rather than changing the original list, a new one is returned
;; So if we want to keep on refering to our "changed" list, we need to give it a name
(def my-list-updated (cons 4 my-list))

;; As you can see we have not changed the original list
my-list

;; The new list does have the change though.
my-list-updated

;; As names in Clojure are dynamic, we can of course point the original name to the new list
(def my-list (cons 5 my-list))

;; so now when we evaluate the original name, we get the updated list 
my-list


;;;;;;;;;;;;;;;;;;;;;;;;;
;; Practising with lists


;; Create a simple collection of developer event names

;; first lets use a list of strings at it seems the easiest straight forward

(def developer-events-strings '("Devoxx UK" "Devoxx France" "Devoxx" "Hack the Tower"))

;; > Remember, in Clojure the first element of a list is treated as a function call, so we have used the quote ' character to tell Cloojure to also treat the first element as data.  We could of course have used the list function to define our list `(def developer-events-strings2 (list "Devoxx UK" "Devoxx France" "Devoxx" "Hack the Tower"))`

developer-events-strings

;; We can get just the first element in our collection of developer events
(first developer-events-strings)


;; using a Clojure Vector data structure seems a little more Clojurey, especially when the vector contains keywords.  Think of a Vector as an Array, although in Clojure it is again immutable in the same way a list is.

(def developer-events-vector
  [:devoxxuk :devoxxfr :devoxx :hackthetower] )


;; Lets create a slightly more involved data structure,
;; holding more data around each developer events.
;; This data structure is just a map, with each key being
;; the unique name of the developer event.
;; The details of each event (the value to go with the
;; event name key) is itself a map as there are several
;; pieces of data associated with each event name.
;; So we have a map where each value is itself a map.
(def dev-event-details
  {:devoxxuk     {:URL                 "http://jaxlondon.co.uk"
                  :event-type          "Conference"
                  :number-of-attendees 700
                  :call-for-papers     true}
   :hackthetower {:URL                 "http://hackthetower.co.uk"
                  :event-type          "hackday"
                  :number-of-attendees 60
                  :call-for-papers     false}})

;; lets call the data structre and see what it evaluates too, it should not be a surprise
dev-event-details

;; We can ask for the value of a specific key, and just that value is returned
(dev-event-details :devoxxuk)

;; In our example, the value returned from the :devoxxuk key is also a map,
;; so we can ask for a specific part of that map value by again using its key
(:URL (dev-event-details :devoxxuk))

;; Lets define a simple data structure for stocks data
;; This is a vector of maps, as there will be one or more company stocks
;; to track.  Each map represents the stock information for a company.

(def portfolio [ { :ticker "CRM"  :lastTrade 233.12 :open 230.66}
                 { :ticker "AAPL" :lastTrade 203.25 :open 204.50}
                 { :ticker "MSFT" :lastTrade 29.12  :open 29.08 }
                 { :ticker "ORCL" :lastTrade 21.90  :open 21.83 }])

;; We can get the value of the whole data structure by refering to it by name
portfolio

;; As the data structure is a vector (ie. array like) then we can ask for a specific element by its position in the array using the nth function

;; Lets get the map that is the first element (again as a vector has array-like properties, the first element is referenced by zero)
(nth portfolio 0)

;; The vector has 4 elements, so we can access the last element by referencing the vector using 3
(nth portfolio 3)

;; As portfolio is a collection (list, vector, map, set), also known as a sequence, then we can use a number of functions that provide common ways of getting data from a data structure

(first portfolio)
(rest portfolio)
(last portfolio)

;; To get specific information about the share in our portfolio, we can also use the keywords to get specific information

(get (second portfolio) :ticker)
;; => "AAPL"

(:ticker (first portfolio))
;; => "CRM"

(map :ticker portfolio)
;; => ("CRM" "AAPL" "MSFT" "ORCL")

;; return the portfolio in a vector rather than a list using mapv function
(mapv :ticker portfolio)
;; => ["CRM" "AAPL" "MSFT" "ORCL"]


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Map reduce

(def my-numbers [1 2 3 4 5])

(map even? my-numbers)
;; => (false true false true false)

;; Reduce to see if all the numbers are even, otherwise return false.
;; or is a macro so is quoted so its evaluated only when called by reduce
(reduce 'or (map even? my-numbers))
;; => false


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Map reduce sandwich

;; http://www.datasciencecentral.com/forum/topics/what-is-map-reduce

;; As several functions

(defn slice [item]
  (str "sliced " item))
;; => #'clojure-through-code.03-using-data-structures/slice
;; => #'clojure-through-code.03-using-data-structures/slice

(def prepared-ingredience
  (map slice ["bread" "cucumber" "pepper" "tomato" "lettuce" "onion"]))
;; => #'clojure-through-code.03-using-data-structures/sandwich

(def make-sandwich
  (reduce str (interpose ", " prepared-ingredience)))

(str "I have a tasty sandwich made with " make-sandwich)
;; => "I have a tasty sandwich made with sliced bread, sliced cucumber, sliced pepper, sliced tomato, sliced lettuce, sliced onion"


;; Or as one function
(str "I have a tasty sandwich made with "
     (reduce str (interpose ", " (map #(str "sliced " %) ["bread" "cucumber" "pepper" "tomato" "lettuce" "onion"]))))


;; Or using the threading macro

(->> ["bread" "cucumber" "pepper" "tomato" "lettuce" "onion"]
     (map #(str "sliced " %) ,,,)
     (interpose ", " ,,,)
     (reduce str ,,,)
     (str "I have a tasty sandwich made with " ,,,))



;; => "I have a tasty sandwich made with sliced bread, sliced cucumber, sliced pepper, sliced tomato, sliced lettuce, sliced onion"


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Map and filter

;; A collection of accounts, each account being a map with id and balance values
(def accounts [{:id "fred" :balance 10}
               {:id "betty" :balance 20}
               {:id "wilma" :balance 5}])

;; Get the balance for each account and add them together
(apply + (map :balance accounts))

;; We could also use reduce insdead of apply
(reduce + (map :balance accounts))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Evaluating things you have defined

;; person
;; developer-events
;; (dev-event-details)

(dev-event-details :devoxxuk)

(first portfolio)
(next portfolio)

;;; First and next are termed as sequence functions in Clojure,
;;; unlike other lisps, you can use first and next on other data structures too

(first person)
(rest person)

;;; these functions return the strings as a set of characters,
;;; as characters are the elements of a string

;;; returning the first element as a string is easy, simply by rapping the
;;; str function around the (first person) function
(str (first person))

;;; So how do we return the rest of the string as a string ?
(str (rest person))
(map str (rest person))
(str (map str (rest person)))
(apply str (rest person))


;; You can get the value of this map

(def luke {:name "Luke Skywarker" :skill "Targeting Swamp Rats"})
(def darth {:name "Darth Vader"    :skill "Crank phone calls"})
(def jarjar {:name "JarJar Binks"   :skill "Upsetting a generation of fans"})

(get luke :skill)
(first luke)


;; Getting the keys or values in a map using keywords

;; When you use a keyword, eg. :name, as the key in a map, then that keyword can be used as a function call on the map to return its associated value.
;; Maps can also act as functions too.

(:name luke)
(luke :name)

;; There are also functions that will give you all the keys of a map and all the values of that map
(keys luke)
(vals luke)


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Set #{}
;; a unique set of elements

(#{:a :b :c} :c)
(#{:a :b :c} :z)

;; You can pull out data from a Vector
([1 2 3] 1)

;; ([1 2 3] 1 2)  ;; wrong number of arguments, vectors behaving as a function expect one parameter

;; ((1 2 3) 1) ;; you cant treat lists in the same way, there is another approach - assoc


;; and there are lots of functions that work on data structures

(def evil-empire #{"Palpatine" "Darth Vader" "Boba Fett" "Darth Tyranus"})

(contains? evil-empire "Darth Vader")



;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Scope

;; All def names are publicly available via their namespace.
;; As def values are immutable, then keeping things private is of less concern than languages built around Object Oriented design.

;; Private definitions syntax can be used to limit the access to def names to the namespace they are declared in.

;; To limit the scope of a def, add the :private true metadata key value pair.

(def ^{:private true} some-var :value)

;; or
(def ^:private some-var :value)

;; The second form is syntax sugar for the first one.


;; You can define a macro for def-
(defmacro def- [item value]
  `(def ^{:private true} ~item ~value)
)

;; You can then use this macro as follows:

(def- private-definition "This is only accessible in the namespace")


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


;; Showing how changing data structures does not change the original data structure
;; Lets define a name for a data structure
(def name1 [1 2 3 4])

;; when we evaluate that name we get the original data we set
name1

;; Now we use a function called conj to adds (conjoin) another number to our data structure
(conj name1 5)

;; This returns a new value without changing the original data structre
name1

;; We cant change the original data structure, it is immutable.  Once it is set it cant be changed.
;; However, if we give a name to the resultl of changing the original data structure, we can refer to that new data structure
(def name2(conj name1 5))

;; Now name2 is the new data structure, but name1 remains unchanged
name2
name1

;; So we cannot change the data structure, however we can achieve something that looks like we have changed it
;; We can re-assign the original name to the result of changing the original data structure
(def name2(conj name1 5))

;; Now name1 and name2 are the same result
name2
name1

;; Analogy (Chris Ford)
;; You have the number 2.  If you add 1 to 2, what value is the number 2?
;; The number 2 is still 2 no mater that you add 1 to it, however, you get the value 3 in return


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



;; printing out data structures

(def data-structure-vector-of-vectors [[1 2 3] [4 5 6] [7 8 9]])

(defn- map-over-vector-of-vectors []
  (map println data-structure-vector-of-vectors))

(comp println map-over-vector-of-vectors)




;; It is often the case that you will want to bind same-named symbols to the map keys. The :keys directive allows you to avoid the redundancy:

(def my-map {:fred "freddy" :ethel "ethanol" :lucy "goosey"})

(let [{fred :fred ethel :ethel lucy :lucy} my-map]
  [fred ethel lucy])

;; can be written:

(let [{:keys [fred ethel lucy]} my-map]
  [fred ethel lucy])

;; As of Clojure 1.6, you can also use prefixed map keys in the map destructuring form:

(let [m {:x/a 1, :y/b 2}
      {:keys [x/a y/b]} m]
  (+ a b))


; As shown above, in the case of using prefixed keys, the bound symbol name will be the same as the right-hand side of the prefixed key. You can also use auto-resolved keyword forms in the :keys directive:

(let [m {::x 42}
      {:keys [::x]} m]
  x)


;; Pretty Printing Clojure data stuctures
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


;; Pretty print hash-maps

(require '[clojure.pprint])

(clojure.pprint/pprint
 {:account-id 232443344 :account-name "Jenny Jetpack" :balance 9999 :last-update "2021-12-12" :credit-score :aa})

{:account-id   232443344,
 :account-name "Jenny Jetpack",
 :balance      9999,
 :last-update  "2021-12-12",
 :credit-score :aa}


;; Showing data structures as a table

(clojure.pprint/print-table
 [{:location "Scotland" :total-cases 42826 :total-mortality 9202}
  {:location "Wales" :total-cases 50876 :total-mortality 1202}
  {:location "England" :total-cases 5440876 :total-mortality 200202}])


;; | :location | :total-cases | :total-mortality |
;; |-----------+--------------+------------------|
;; |  Scotland |        42826 |             9202 |
;; |     Wales |        50876 |             1202 |
;; |   England |      5440876 |           200202 |
