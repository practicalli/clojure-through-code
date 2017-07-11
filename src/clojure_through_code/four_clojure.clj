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
not=
