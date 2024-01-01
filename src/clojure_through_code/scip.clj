(ns clojure-through-code.scip)

;; Pascals Triange
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn next-row [last-row]
  (let [sum-neighbor (map-indexed (fn [idx x]
                                    (+ x (get last-row (+ 1 idx) 0)))
                                  last-row)]
    (into [1] sum-neighbor)))

(defn pascal [n]
  (condp = n
    0 []
    1 [[1]]
    (let [prev-p (pascal (- n 1))]
      (conj prev-p (next-row (last prev-p))))))

;; Would be curious to hear if you think there's a more idiomatic clojure way to do this : }

;; seancorfield  01:08

(defn pascal [n]
  (take n (iterate #(mapv (fn [[x y]] (+ x y))
                          (partition 2 1 (cons 0 (into % [0])))) [1])))

;; For a given row, if you prepend and append 0 and then break into (overlapping) pairs, then add each pair, you get the next rows.
;; So you can have an infinite sequence of Pascal's triangle rows and you just take as many rows as you want.

;; You could safely use (concat [0] % [0]) instead of (cons 0 (into % [0])) if you find that clearer -- mapv is eager so you won't get a stack overflow from a lazy computation.

;; concat (is more forgiving about) doesn't have subtle bug possibilities via seq / vector conj location (edited)
