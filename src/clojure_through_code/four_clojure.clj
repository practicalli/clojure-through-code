(ns clojure-through-code.four-clojure)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; problem 60

;; Write a function which behaves like reduce, but returns each intermediate value of the reduction.
;; Your function must accept either two or three arguments, and the return sequence must be lazy.

;; Special Restrictions: recursions

;; Notes from:
;; http://www.thesoftwaresimpleton.com/blog/2014/09/08/lazy-seq/

;; (= (take 5 (__ + (range))) [0 1 3 6 10])

;; Range by itself generates and infinate number of integer values, blowing up your heap eventually
;;(range)

;; we can specify the range to generated
(range 10)

;; or we can use the lazy sequence nature of range to get just the values we want

(take 10 (range))
;; => (0 1 2 3 4 5 6 7 8 9)

(class (take 10 (range)))
;; => clojure.lang.LazySeq


;; Solving the problem

;; (= (take 5 (map + (range))) [0 1 3 6 10])

;;(take 5 (map + (range)))

(= (take 5 (
            (fn my-reduct
              ([func coll]
               (my-reduct func (first coll) (rest coll)))

              ([func firstArg coll]
               (letfn [(reduct [f init se]
                         (lazy-seq (when-not (empty? se)
                                     (let [res (f init (first se))]
                                       (cons res (reduct f res (rest se)))))))]
                 (lazy-seq (cons firstArg (reduct func firstArg coll))))))
            + (range))) [0 1 3 6 10])

;; 4Clojure entered solution

(fn my-reduct
  ([func coll]
   (my-reduct func (first coll) (rest coll)))

  ([func firstArg coll]
   (letfn [(reduct [f init se]
             (lazy-seq (when-not (empty? se)
                         (let [res (f init (first se))]
                           (cons res (reduct f res (rest se)))))))]
     (lazy-seq (cons firstArg (reduct func firstArg coll))))))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; problem 68: recur


(loop [x 5
       result []]
  (if (> x 0)
    (recur (dec x) (conj result (+ 2 x)))
    result))

;; => [7 6 5 4 3]

;; The value of x is bound to 5 and result is bound to an empty vector.
;; If x is greater than zero, then call the loop again, passing two values
;; that replace the initial loop bindings.
;; The conj expression is evaluated before the loop is called again,
;; so the first value added to result is 7
;; When the value of x has be decremented to zero, the current value of
;; result is returned instead of calling the loop again.

;; 4Clojure entered solution
[7 6 5 4 3]


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; problem 73 - analyse tic-tac-toe board

;; A tic-tac-toe board is represented by a two dimensional vector. X is represented by :x, O is represented by :o, and empty is represented by :e.
;; A player wins by placing three Xs or three Os in a horizontal, vertical, or diagonal row.
;; Write a function which analyzes a tic-tac-toe board and returns :x if X has won, :o if O has won, and nil if neither player has won.


;; Define some tic-tac-toe board pattern to test

(def board-empty [[:e :e :e]
                  [:e :e :e]
                  [:e :e :e]])

(def board-x-first-row [[:x :x :x]
                        [:e :e :e]
                        [:e :e :e]])

(def board-o-diagonal [[:o :e :e]
                       [:e :o :e]
                       [:e :e :o]])

;; Algorithm
;; 1) Extract all the values from the board  (destructuring with let)
;; 2) Pattern match the values to winning combinations (=)
;; 3) If any combination matched with :x or :o then return winner (or)


;; Destructuring will bind a local name to a value from the board pattern.
;; In this case, a b and c will each represent a line of the board

(let [[a b c] board-empty]
  [a b c])
;; => [[:e :e :e] [:e :e :e] [:e :e :e]]

;; Now a b and c will represent the elements of the first row of the board pattern.

(let [[[a b c]] board-x-first-row]
  [a b c])
;; => [:o :e :e]


;; So we can pull out the whole board using the following let expression

(let [[[a b c]
       [d e f]
       [g h i]] board-o-diagonal]
  [[a b c] [d e f] [g h i]])
;; => [[:o :e :e] [:e :o :e] [:e :e :o]]


;; letfn creates a function within the scope of the let expression.
;; For example:

(letfn [(winner [[[a b c]
                  [d e f]
                  [g h i]]
                 player])])

;; Using macro-expansion we can see the letfn creates a function called winner
;; with two arguments: a board pattern and player
(letfn* [winner (fn winner [[[a b c] [d e f] [g h i]] player])])

;; We can call the local winner function whilst inside the letfn expression,
;; however we have not yet added any behaviour to that function.


;; Adding a check to see if a player has one the first row of the board as a first
;; attempt at defining the function behaviour.
;; If the first row matches the player character then we should get true.

(letfn [(winner [[[a b c]
                  [d e f]
                  [g h i]]
                 player]
          (println (str "first row: " [a b c])) ;; debugging
          (= player a b c))]
  (winner board-x-first-row :x))
;; => true

;; Note: A simple println statement was added to show the intermediate values,
;; as an example of a simple debugging statement

;; Now we have the basic logic, we can add the patterns for all the winning combinations
;; We only need one pattern to match, so we wrap the patterns in an or function
;; To test both players, we add a condition around calling the winner function for each player


(letfn [(winner [[[a b c]
                  [d e f]
                  [g h i]]
                 player]
          (or (= player a b c)
              (= player d e f)
              (= player g h i)
              (= player a d g)
              (= player b e h)
              (= player c f i)
              (= player a e i)
              (= player c e g)))]
  (cond (winner board-x-first-row :x) :x
        (winner board-x-first-row :o) :o
        :else (str "No Winner for this board: " board-x-first-row)))
;; => :x

;; The letfn expression was tested with each of the three board patterns defined, giving the correct results.

;; 4Clojure entered solution

(fn [board]
  (letfn [(winner [[[a b c]
                    [d e f]
                    [g h i]]
                   player]
            (or (= player a b c)
                (= player d e f)
                (= player g h i)
                (= player a d g)
                (= player b e h)
                (= player c f i)
                (= player a e i)
                (= player c e g)))]
    (cond (winner board :x) :x
          (winner board :o) :o
          :else nil)))


;; Alternative solutions

;; I quite like the solution by tclamb, its similar to the submitted solution in approach

(fn winner [[[a b c :as top]
             [d e f :as middle]
             [g h i :as bottom]]]
  (let [left     [a d g]
        center   [b e h]
        right    [c f i]
        forward  [a e i]
        backward [c e g]]
    (when-let [winner (some #(when (or (every? #{:x} %) (every? #{:o} %)) %)
                            [top middle bottom
                             left center right
                             forward backward])]
      (first winner))))



;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; problem 83: a half truth

;; Write a function which takes a variable number of booleans.
;; Your function should return true if some of the parameters are true, but not all of the parameters are true. Otherwise your function should return false.

;; a full way of describing the algorithm and thinking behind it
(fn [& args]
    (let [not-all-true (not (every? true? args))
          any-true (first (filter true? args))]
      (= not-all-true any-true)))

(fn [& args]
  (= (not (every? true? args))
     (first (filter true? args))))

;; To get a better golf score (fewer characters) an anonymous function can be used
;; The %& is a placeholder for all the parameters
(#(=
   (not (every? true? %&))
   (first (filter true? %&))) true)


  ;; From the first example, we can see that comparing true and false values
;; with give a boolean result.  So what about just comparing the values

;; If all the values are true => false
;; If all the values are false => false
;; If there is at lease one true & one false => true
;; So comparing all of the parameters being equal gives the wrong result, false when true and vice versa.
;; Inverting the comparison of all parameters give the correct result.

;; So by replacing the not and first expressions with their results I would get the following combinations of results

(= true true)   ;; => true
(= true false)  ;; => false
(= false false) ;; => true

;; These combinations give the opposite result we are looking for (false when it should be true, etc).
;; So we can invert the result using not or better still use the not= function

(not= true true)   ;; => false
(not= true false)  ;; => true
(not= false false) ;; => false

;; 4Clojure entered solution
;; not=

