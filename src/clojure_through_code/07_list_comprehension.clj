(ns clojure-through-code.07-list-comprehension)

;; Lots of powerful functions in Clojure for working with a list data structure.
;; List comprehension lets you work with multiple list.




;;;;;;;;;;;;;;;;;;;;;;

;; Some functions can return a very large collection, if you dont use lazy evaluation
;; around this kind of function you can blow up your repl, or have to break out of your repl.
;; For example if you run the following

;; (iterate inc 0)

;;(set! *print-length* 10)
;;10

;; Now it's perfectly fine. Yay!
;; (iterate inc 0)
;; (0 1 2 3 4 5 6 7 8 9 ...)





;; Sequence / list comprehension

(for [n (range 10)]
  (inc n))

; loop through the first 10 numbers, assigning an x & y values in an array to form a list result
(for [x (range 10) y (range 10)]
  [x y])

; create a list for x and y of the first 10 numbers as an array.
(let [x (range 10)
      y (range 10)]
  [x y])

;; add conditions using :when and :while
(for [x (range 10) y (range 10)
      :when (even?
             (+ x y))]
  [x y])



;; From http://integrallis.com/2010/12/on-mini-languages-and-clojure#.VorOFJfFs3y

(for [x (range 10)] x)

;;> (0 1 2 3 4 5 6 7 8 9)

;; using the :when to filter out odd items
(for [x (range 10) :when (even? x)] x)

;> (0 2 4 6 8)

;; using the :while keyword - Note that the evaluation
;; stops when the first item, in this case 1 fails the predicate
(for [x (range 10) :while (even? x)] x)

;;> (0)

