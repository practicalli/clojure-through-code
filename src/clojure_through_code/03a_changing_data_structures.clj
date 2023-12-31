(ns clojure-through-code.03a-changing-data-structures)


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Changing data in data structures

;; Wait, I thought you said that data structures were immutable!
;; So how can we change them then?

;; Yes, lists, vectors, maps and sets are all immutable.  However,
;; you can get a new data structure that has the changes you want.
;; To make this approach efficient, the new data structure contains only
;; the new data and links back to the existing data structure for shared
;; data elements.

;; We will see some of the most common functions that work with data
;; structures in this secion.  In actuality, everything can be
;; considered a function that works on a data structure though,
;; as that is the language design of clojure.

;; First we will use the conj function, think of this as the english phrase conjoin
;; conj will join a data structure and a value together to form a new data structure.

;; conj works on all Clojure persistent data structures (list, vector, map, set)

;; Lets add a number to a list of 4 numbers using the function conj
;; I think of cons as "constructs" a new data structure from the
;; existing data structure

(conj '(1 2 3 4) 5)

;; As a Clojure list is represented a linked list, then its is more efficient to add new elements to the start of a list.

;; conj does not change the existing list, it create a new list
;; that contains the number 5 and a link to all the elements of the
;; existing list.

;; You can also use conj on other data structures and data types

;; vectors
(conj [1 2 3 4] 5)

;; list of strings
(conj '("and" "chips") "fish")

(conj #{1 2 3} 4)

;; If you use cons (think construct) this returns a list, regardless of the original data structure type.



;; notice that conjoining to a vector is done at the end
(conj [1 2 3] 4)
;;=> [1 2 3 4]

;; notice conjoining to a list is done at the beginning
(conj '(1 2 3) 4)
;;=> (4 1 2 3)

(conj ["a" "b" "c"] "d")
;;=> ["a" "b" "c" "d"]

;; conjoining multiple items is done in order
(conj [1 2] 3 4)
;;=> [1 2 3 4]

(conj '(1 2) 3 4)
;;=> (4 3 1 2)

(conj [[1 2] [3 4]] [5 6])
;;=> [[1 2] [3 4] [5 6]]

;; conjoining to maps only take items as vectors of length exactly 2
(conj {1 2, 3 4} [5 6])
;;=> {5 6, 1 2, 3 4}

(conj {:firstname "John" :lastname "Doe"} {:age 25 :nationality "Chinese"})
;;=> {:nationality "Chinese", :age 25, :firstname "John", :lastname "Doe"}

;; conj on a set
(conj #{1 3 4} 2)
;;=> #{1 2 3 4}



;; from Clojure.org

;; conjoin shows similar behaviour to cons
;; The main difference being that conj works on collections
;; but cons works with seqs.
(conj ["a" "b" "c"] ["a" "b" "c"] )
;;=> ["a" "b" "c" ["a" "b" "c"]]

link

;; conjoin nil with x or xs
(conj nil 3)
;;=> (3)

(conj nil 3 4)
;;=> (4 3)

link

;; maps and sets are treated differently
(conj {1 2} {3 4})
;;=> {3 4, 1 2}   ; the contents of {3 4} are added to {1 2}

(conj #{1 2} #{3})
;;=> #{1 2 #{3}}  ; the whole set #{3} is added to #{1 2}

(clojure.set/union #{1 2} #{3})
;;=> #{1 2 3}  ; must use (clojure.set/union) to merge sets, not conj

link

;; When conjoining into a map, vector pairs may be provided:
(conj {:a 1} [:b 2] [:c 3])
;;=> {:c 3, :b 2, :a 1}

;; Or maps may be provided, with multiple pairings:
(conj {:a 1} {:b 2 :c 3} {:d 4 :e 5 :f 6})
;;=> {:f 6, :d 4, :e 5, :b 2, :c 3, :a 1}

;; But multiple pairings cannot appear in vectors:
(conj {:a 1} [:b 2 :c 3])
;;=> IllegalArgumentException Vector arg to map conj must be a pair...

;; And pairs may not be provided in lists:
(conj {:a 1} '(:b 2))
;;=> ClassCastException ...Keyword cannot be cast to ...Map$Entry...


;; Returns a new seq where x is the first element and seq is the rest.






#######################################
### In practice

;; Lets define a simple list and give it a name
(def list-one '(1 2 3))

;; the name evaluates to what we expect
list-one

;; If we add the number 4 using the cons function, then we
;; get a new list in return, with 4 added to the front (because thats how lists work with cons)
(cons 4 list-one)

;; If we want to keep the result of adding to the list, we can assign it a different name
(def list-two (cons 4 list-one))
;; and we get the result we want
listtwo

;; we can also assing the original name we used for the list to the new list
(def list-one (cons 4 list-one))

;; If we re-evaluate the definition above, then each time we will get an extra
;; number 4 added to the list.

list-one

;; Again, this is not changing the original list, we have just moved the name
;; of the list to point to the new list.
;; Any other function working with this data structure before reassigning the name
;; will not be affected by the re-assignment and will use the unchanged list.




;;;; Changing Maps

(def alphabet-soup {:a 1 :b 2 :c 3})

(assoc alphabet-soup :d 4)


;;;; Creating default maps from a known set of keys

(zipmap [:foo :bar :baz] (repeat nil))
;; => {:foo nil, :bar nil, :baz nil}

;; alternatively
(into {} (for [k [:foo :bar :baz]] [k nil]))
;; => {:foo nil, :bar nil, :baz nil}

;; creating a map with random integer values (use rand for decimal)
(zipmap [:foo :bar :baz] (repeatedly #(rand-int 11)))
;; => {:foo 9, :bar 2, :baz 9}

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Creating vectors

(into [] (take 10 (range)))


(def player-data [{:name "Oliver", :score 100} {:name "Revilo", :score 50}])


(require '[hiccup.page :refer [html5]])

;; Whilst you could use map to iterate over the hiccup data structure
(html5 (for [row player-data]
         [:tr (map (fn [x] [:td (val x)]) row)]))
;; => "<!DOCTYPE html>\n<html><tr><td>Oliver</td><td>100</td></tr><tr><td>Revilo</td><td>50</td></tr></html>"

;; Its more idiomatic to use a let form to define local names that are then used in the hiccup data structure
(html5 (for [row player-data
             :let [player (:name row)
                   score (:score row)]]
         [:tr [:td player] [:td score]]))
;; => "<!DOCTYPE html>\n<html><tr><td>Oliver</td><td>100</td></tr><tr><td>Revilo</td><td>50</td></tr></html>"
