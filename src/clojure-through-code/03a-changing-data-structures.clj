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

;; Lets add a number to a list of 4 numbers using the function cons
;; I think of cons as "constructs" a new data structure from the
;; existing data structure

(cons 5 '(1 2 3 4))

;; cons does not change the existing list, it create a new list
;; that contains the number 5 and a link to all the elements of the
;; existing list.

;; You can also use cons on other data structures and data types

;; vectors
(cons 5 [1 2 3 4])

;; list of strings
(cons "fish" '("and" "chips"))

;; Note what cons has returned...

(conj '(1 2 3 4) 5)

(conj [1 2 3 4] 5)

;; Conj does what you would expect.



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
list-two

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


