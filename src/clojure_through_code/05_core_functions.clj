(ns clojure-through-code.05-core-functions)

;; There are a lot of functions in the Clojure core library

(count (ns-publics 'clojure.core))

;; when I checked on 7th April 2015 there were 599 functions defined in Clojure Core for version 1.6

;;;;;;;;;;;;;;;;;;;;;;;
;; Data structures behave a little like functions

;; we have already seen this


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Java

;; java.lang is included in the namespace by default

;; easy to import any other Java / JVM library (see calling java)


;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Clojure Core

;; There is a core set of functions included by default in any Clojure program

;; https://clojure.github.io/clojure/clojure.core-api.html

;; these are the building blocks for your own code and other Clojure libraries

;; There are a great number of functions defined in Clojure core
(count (ns-publics 'clojure.core))

;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Working with data structures

;; This section will become far too long, so need to refine the topic

;; Apply a function over the individual elements a data structure





;; Mapping a function over one or more collections, applying that function to the element of each collection

;; user> (doc map)
;; -------------------------
;; clojure.core/map
;; ([f coll] [f c1 c2] [f c1 c2 c3] [f c1 c2 c3 & colls])
;;   Returns a lazy sequence consisting of the result of applying f to the set of first items of each coll, followed by applying f to the set of second items in each coll, until any one of the colls is exhausted.  Any remaining items in other colls are ignored. Function f should accept number-of-colls arguments.
;; nil



(map + [1 2 3] [1 2 3])

(map + [1 2 3] [1 2])

(map + [1 2 3] [1])

(map + [1 2 3] [])

(map + [1 2 3])


(def fibonacci-sequence [1 2 3 5 8 13 21 34 55 89 144 278])

(take 20 fibonacci-sequence)

(take 10 [])

(first [1 2 3])
(first '())
(first (list))

(vector(map * (range 10) fibonacci-sequence))
;; return a vector for this

(lazy-seq)

(rand-int 10)
