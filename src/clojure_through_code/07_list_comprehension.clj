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

;; filter out values :when they do not meet the condition
(for [x (range 10) :when (even? x)] x)

;> (0 2 4 6 8)

;; iterate :while condition is met
(for [x (range 10) :while (even? x)] x)

;;> (0)





;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Merge elements of collections to form game board grids
;; - using list comprehension

(def rows    "ABCDEFGHI")
(def columns "123456789")

(defn grid-reference
"Generate all the combinations for a given collection of row and column names.

  The collection of names can be in a string, set, list or vector.

  The function will return all combinations up to the length of the collection with the fewest elements."

  [row-names column-names]

  (for [row-element row-names
        column-element column-names]
    (str row-element column-element)))

(def board (grid-reference rows columns))

board


(defn grid-reference-as-keys
  [row-names column-names]
  (for [row-element row-names
        column-element column-names]
    (keyword (str row-element column-element))))

(def board-with-keywords (grid-reference-as-keys rows columns))

board-with-keywords


(def alphabet "ABCDEFGHIJKLMNOPQRSTUVWXYZ")

(defn tictactoe-board-grid
"Create a TicTacToe board grid so each cell in the board has a unique reference.
 Boards have the same number of rows and columns, so a typical 3x3 grid would have a row-column-size of 3"
  [row-column-size]
  (let [row-names (take row-column-size alphabet)
        column-names (range 1 (+ row-column-size 1))]
    (grid-reference-as-keys row-names column-names)))

(tictactoe-board-grid 3)
