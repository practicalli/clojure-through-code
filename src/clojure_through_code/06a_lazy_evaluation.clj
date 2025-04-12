(ns clojure-through-code.06a-lazy-evaluation)


;;
;; Lazy evaluation

;; Building up behaviour by passng functions to functions

;; Get the first 3 elements of a vector (a vector can be thought of as an immutable array)
(take 3 [1 2 3 4 5 6])


;; Instead of using a data structure, lets generate one on the fly

(take 10 (iterate inc 0))
(take 10 (iterate dec 0))


;; Seeing Lazy evaluation in action

;; By creating a lazy sequence

(defn lazy-random-numbers
  "Lazy evaluation of a random number generator, generates only the numbers required at evaluation time.  Once a number is generated though, it does not need to be generated again."
  [maximum-number]
  (lazy-seq
    (println "I just created a number in the lazy sequence")
    (cons (rand-int maximum-number)
          (lazy-random-numbers maximum-number))))


(def random-numbers
  (take 10 (lazy-random-numbers 100)))


;; to see the laziness of the sequence (list of numbers), just take a few of them

(first random-numbers)


;; sends 1 println to the REPl
;; => 36

(nth random-numbers 3)


;; sends 3 println to the REPl

(count random-numbers)


;; sends 6 println to the REPl first time this is called
;; sends 0 println to the REPl all consecutive times as all the numbers are now cached

(defn accumulate
  [operation value]
  (loop [result []
         args value]
    (if (empty? args)
      result
      (recur (conj result (operation (first args))) (rest args)))))


(accumulate #(* %) [1 2 3])


(defn accumulate_v1
  [operation arguments]
  (loop [result [] args arguments]
    (if (empty? args)
      (reverse result)
      (recur (cons (operation (first args)) result) (rest args)))))


(accumulate_v1 #(* %) [1 2 3])


;; Move to list section

;; add elements to a list using cons - I think of this as construct a list
(cons :z [:a :b])


;; Treating data as data with quote '

;; Conj needs to have a collection as the first argument
(conj (:a :b) :c)  ; fail

(conj '(:a :b) :c)
(conj {:a "A" :c "C"} {:b "B"})


;; concat returns a list as it needs to create a new data structure
;; its more efficient to create new data structures as lists
(concat [:a :b :c] [:d :e])


;; this returns a vector, althought there is a performance hit with this
;; In clojure you can decide for your self what sort of performance you want with your app
(vec (concat [:a :b :c] [:d :e]))


(count [1 2 3])
(count #{42})


;; reduce takes a sequence and returns a scala
(reduce + (map inc (range 10)))


;; filter in terms of reduce
;; otfrom tip: use acc and new for your parameters - accumulator and new value
(filter even? (range 10))


(reverse
  (reduce (fn [x y]
            (if (even? y) (conj x y) x))
          (list) (range 10)))


(first [1 2 3])
(next [1 2 3])
(rest [1 2 3])
(next [])
(rest [])
(last [1 2 3])
(get [1 2 3 4] 2)

(max 1 2 3)

(apply min [1 2 3 4 8 0 -30])


;; apply uses ariatic arguments, reduce can be faster - otfrom

(reduce min [1 2 3 4 8 0 -30])

(reductions min [1 2 3 4 8 0 -30])

(frequencies [1 2 3 2 4 4 5 1 1 2 4 6 5 4 4 2 3])

(partition 4 (range 10))


;; be weary of loosing data that does not fit into the partition
;; use partition-all or use a pad

(partition 4 3 (repeat :a) (range 10))


(type {})
(type (transient {}))


;; (doc split-at)

;; most seuences are evaluated in blocks of 32


;; (map inc (range))

(take 10 (cycle [:hearts]))


;; find if something is in a sequence, not performant as their is no index
(some #{:b} [:a :b :c])
(filter #{:b} [:a :b :c])


;; (get-in [{:a "A" :b "B"} {0 :a}])
