;; Fibonachi series
;; simple description: the next number is the sum of the previous two, starting with 1 and 2

;;;;; Some references
;; http://squirrel.pl/blog/2010/07/26/corecursion-in-clojure/


;; could define a data structure, large enough for any series length you want
(def fibonachi-data #{1 2 3 5 8 13 21 34 55 89 144 223})

;; The evaluation of the set is not ordered, so use a function to create a sorted output
;; We can sort in accending < order, or decending > order
(sorted-set-by > 1 2 3 5 8 13 21 34 55 89 144 223)

;; We can of course also create an ordered set to start with and give it a name,
;; so when we refer to the set by name it always gives us an ordered set
(def fibonachi-data (sorted-set 1 2 3 5 8 13 21 34 55 89 144 223))

fibonachi-data


;; Rather than generate a long set of numbers, we can use lazy evaluation to generate just the series we need each time.
;; Clojure is very good at generating data structures and only calculating the part of the data structure needed at that time

;; need to work out a good algorithm for generating the fibonachi series
(defn fibonachi-series [series-length]
  (let [x series-length]
    x))

(fibonachi-series 8)


;; we just want to have a certain number of integers in the series, so we can use take
(take 2 '(1 2 3 5 ))

;; How do we generate a number sequence ?

(inc 3)

;; lets create a sequence to play with
(def my-sequence '(1 2 3 4 5))

;; I can get the first element of the sequence, or the rest of the sequence
(first my-sequence)
(rest my-sequence)

;; Add a condition, based on whether there is anything in the sequence
(if (not-empty ()) 1 -1)
(if (not-empty (rest my-sequence)) 1 2)

(defn gen-lazy-sequence [length]
  (let [fib
        [length]]
    fib))

(gen-lazy-sequence 4)




;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Other solutions to fibonachi

;;Programming in Clojure p. 136

;; (defn fibo [] (map first (iterate (fn [[a b]] [b (+ a b)]) [0 1])))

;; (defn proj-euler2 []
;;   (reduce + (for [x (fibo) :when (even? x) :while (< x 4000000)] x)))


;; Solutions from http://en.wikibooks.org/wiki/Clojure_Programming/Examples/Lazy_Fibonacci

;; A function which lazily produces Fibonacci numbers:

;; (def fib-seq
;;   ((fn rfib [a b]
;;      (lazy-seq (cons a (rfib b (+ a b)))))
;;    0 1))

;; (take 20 fib-seq)

;; We can use iterate to generate pairs of [a b] and then take the first of each one.

;; (defn fib-step [[a b]]
;;   [b (+ a b)])

;; (defn fib-seq []
;;   (map first (iterate fib-step [0 1])))

;; (take 20 (fib-seq))


;; Recursive Fibonacci with any start point and the amount of numbers that you want[edit]
;; (defn fib [start range]
;;   "Creates a vector of fibonnaci numbers"
;;   (if (<= range 0)
;;     start
;;     (recur (let[subvector (subvec start (- (count start) 2))
;;                 x (nth subvector 0)
;;                 y (nth subvector 1)
;;                 z (+ x y)]
;;              (conj start z))
;;            (- range 1))))


;; Self-Referential Version[edit]
;; Computes the Fibonacci sequence by mapping the sequence with itself, offset by one element.

;; (def fib (cons 1 (cons 1 (lazy-seq (map + fib (rest  fib))))))


;; From stack exchange

;; initial solution

;; (defn fib [x, n]
;;   (if (< (count x) n)
;;     (fib (conj x (+ (last x) (nth x (- (count x) 2)))) n)
;;     x))

;; To use this I need to seed x with [0 1] when calling the function. My question is, without wrapping it in a separate function, is it possible to write a single function that only takes the number of elements to return?

;; Doing some reading around led me to some better ways of achieving the same funcionality:

;; (defn fib2 [n]
;;   (loop [ x [0 1]]
;;     (if (< (count x) n)
;;       (recur (conj x (+ (last x) (nth x (- (count x) 2)))))
;;       x)))

;; and

;; (defn fib3 [n]
;;   (take n
;;     (map first (iterate (fn [[a b]] [b (+ a b)]) [0 1]))))

;; Multi-arity approach

;; (defn fib
;;   ([n]
;;      (fib [0 1] n))
;;   ([x, n]
;;      (if (< (count x) n)
;;        (fib (conj x (+ (last x) (nth x (- (count x) 2)))) n)
;;        x)))


;;;;; Thrush operator

;; You can use the thrush operator to clean up #3 a bit (depending on who you ask; some people love this style, some hate it; I'm just pointing out it's an option):

;; (defn fib [n]
;;   (->> [0 1]
;;     (iterate (fn [[a b]] [b (+ a b)]))
;;     (map first)
;;     (take n)))
;; That said, I'd probably extract the (take n) and just have the fib function be a lazy infinite sequence.

;; (defn fib
;;   (->> [0 1]
;;     (iterate (fn [[a b]] [b (+ a b)]))
;;     (map first)))

;; ;;usage
;; (take 10 fib)
;; ;;output (0 1 1 2 3 5 8 13 21 34)


;;;;; Avoiding recursion ?

;; In Clojure it's actually advisable to avoid recursion and instead use the loop and recur special forms.
;; This turns what looks like a recursive process into an iterative one, avoiding stack overflows and
;; improving performance.

;; Here's an example of how you'd implement a Fibonacci sequence with this technique:

;; (defn fib [n]
;;   (loop [fib-nums [0 1]]
;;     (if (>= (count fib-nums) n)
;;       (subvec fib-nums 0 n)
;;       (let [[n1 n2] (reverse fib-nums)]
;;         (recur (conj fib-nums (+ n1 n2)))))))

;; The loop construct takes a series of bindings, which provide initial values, and one or more body forms.
;; In any of these body forms, a call to recur will cause the loop to be called recursively with the
;; provided arguments.

