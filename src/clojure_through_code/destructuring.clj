(ns clojure-through-code.destructuring)


;; A simple example of destructuring is assigning the values of a collection, in this case a vector.

(def co-ordinates [3 8])


(let [[x y] co-ordinates]
  (str "x: " x "y: " y))


;; => "x: 3y: 8"


;; Sometimes we do not need all the information, so we can just use the elements we need.
(def three-dee-co-ordinates [2 7 4])


(let [[x y] three-dee-co-ordinates]
  (str "I only need the 2D co-ordinates, X: " x " and Y: " y))


;; => "I only need the 2D co-ordinates, X: 2 and Y: 7"


;; Its quite common to take the first element as a specific name and use another name for the rest of the elements

(def shopping-list ["oranges" "apples" "spinach" "carrots" "potatoes" "beetroot"])


(defn get-item
  [items]
  (let [[next-item & other-items] items]
    (str "The next item to get is: " next-item)))


(get-item shopping-list)


;; => "The next item to get is: oranges"
