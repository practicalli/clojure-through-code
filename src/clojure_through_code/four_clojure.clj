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

