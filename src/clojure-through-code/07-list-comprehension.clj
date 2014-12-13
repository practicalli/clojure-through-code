(ns clojure-through-code.07-list-comprehension)

;; Lots of powerful functions in Clojure for working with a list data structure.
;; List comprehension lets you work with multiple list.


;; Sequence / list comprehension

(for [n (range 10)]
  (inc n))

; loop through the first 10 numbers, assigning an x & y values in an array to form a list result
(for [x (range 10) y (range 10)]
  [x y])

; create a list for x and y of the first 10 numbers as an array.
(let [x (range 10) y (range 10)]
  [x y])

;; add conditions using :when and :while
(for [x (range 10) y (range 10)
      :when (even?
             (+ x y))]
  [x y])

