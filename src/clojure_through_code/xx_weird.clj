(ns clojure-through-code.xx-weird)


;;
;; Constantly
;; Returns a function that takes any number of arguments and returns the value originally passed to the constantly function.
;; https://clojuredocs.org/clojure.core/constantly

;; Call constantly function with the value of 42, wrapping the result of this call in () makes it call the returning function with [1 2 3 4 5] as an argument
((constantly 42) [1 2 3 4 5])


;; A cleaner example is to bind a name to the function that constantly returns.  Then use that name to call the function with any argument.  The original value of 42 is returned.
(def the-answer (constantly 42))

(the-answer "What is 6 times 9")
