;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Debug clojure
;;
;; Functions and tools to help debug Clojure and understand errors
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


;; Simple debug and breakpoint directives
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


(dotimes [index  10]
  #dbg ^{:break/when (= index  7)}
   (prn index))


(dotimes [i 10]
  #break (= i 7)
  (prn i))

(dotimes [index 10]
  #break (prn index)
  index)


;; break on each iteration of the sequence,
;; showing the local value of the index
(dotimes [index 10]
  #break index)


;; evaluate until the condition is met and then break
(dotimes [index 10]
  #dbg ^{:break/when (= index  7)}
   index)


;; the break condition is met multiple times
(dotimes [index 10]
  #dbg ^{:break/when (odd? index)}
   index)





;; Clojure 1.10 error messages
;; https://insideclojure.org/2018/12/17/errors/

;; updated Clojure error reporting calls out where in the code or repl something went wrong


;; :::bad-keyword-syntax

;; clj-kondo identifies invalid keyword

;; cider error report

;;   Show: Project-Only All
;;   Hide: Clojure Java REPL Tooling Duplicates  (9 frames hidden)

;; 3. Unhandled clojure.lang.ExceptionInfo
;;    (No message)
;;    {:clojure.error/phase :read-source}

;;                   main.clj:  433  clojure.main/repl/read-eval-print/fn
;;                   main.clj:  432  clojure.main/repl/read-eval-print
;;                   main.clj:  458  clojure.main/repl/fn
;;                   main.clj:  458  clojure.main/repl
;;                   main.clj:  368  clojure.main/repl
;;                RestFn.java: 1523  clojure.lang.RestFn/invoke
;;     interruptible_eval.clj:   84  nrepl.middleware.interruptible-eval/evaluate
;;     interruptible_eval.clj:   56  nrepl.middleware.interruptible-eval/evaluate
;;     interruptible_eval.clj:  152  nrepl.middleware.interruptible-eval/interruptible-eval/fn/fn
;;                   AFn.java:   22  clojure.lang.AFn/run
;;                session.clj:  218  nrepl.middleware.session/session-exec/main-loop/fn
;;                session.clj:  217  nrepl.middleware.session/session-exec/main-loop
;;                   AFn.java:   22  clojure.lang.AFn/run
;;                Thread.java:  833  java.lang.Thread/run

;; 2. Caused by clojure.lang.LispReader$ReaderException
;;    java.lang.RuntimeException: Invalid token: :::bad-keyword-syntax
;;    {:clojure.error/column 1, :clojure.error/line 19}

;;            LispReader.java:  314  clojure.lang.LispReader/read
;;            LispReader.java:  216  clojure.lang.LispReader/read
;;            LispReader.java:  205  clojure.lang.LispReader/read
;;                   core.clj: 3756  clojure.core/read
;;                   core.clj: 3729  clojure.core/read
;;     interruptible_eval.clj:  108  nrepl.middleware.interruptible-eval/evaluate/fn
;;                   main.clj:  433  clojure.main/repl/read-eval-print/fn
;;                   main.clj:  432  clojure.main/repl/read-eval-print
;;                   main.clj:  458  clojure.main/repl/fn
;;                   main.clj:  458  clojure.main/repl
;;                   main.clj:  368  clojure.main/repl
;;                RestFn.java: 1523  clojure.lang.RestFn/invoke
;;     interruptible_eval.clj:   84  nrepl.middleware.interruptible-eval/evaluate
;;     interruptible_eval.clj:   56  nrepl.middleware.interruptible-eval/evaluate
;;     interruptible_eval.clj:  152  nrepl.middleware.interruptible-eval/interruptible-eval/fn/fn
;;                   AFn.java:   22  clojure.lang.AFn/run
;;                session.clj:  218  nrepl.middleware.session/session-exec/main-loop/fn
;;                session.clj:  217  nrepl.middleware.session/session-exec/main-loop
;;                   AFn.java:   22  clojure.lang.AFn/run
;;                Thread.java:  833  java.lang.Thread/run

;; 1. Caused by java.lang.RuntimeException
;;    Invalid token: :::bad-keyword-syntax

;;                  Util.java:  221  clojure.lang.Util/runtimeException
;;            LispReader.java:  412  clojure.lang.LispReader/interpretToken
;;            LispReader.java:  305  clojure.lang.LispReader/read
;;            LispReader.java:  216  clojure.lang.LispReader/read
;;            LispReader.java:  205  clojure.lang.LispReader/read
;;                   core.clj: 3756  clojure.core/read
;;                   core.clj: 3729  clojure.core/read
;;     interruptible_eval.clj:  108  nrepl.middleware.interruptible-eval/evaluate/fn
;;                   main.clj:  433  clojure.main/repl/read-eval-print/fn
;;                   main.clj:  432  clojure.main/repl/read-eval-print
;;                   main.clj:  458  clojure.main/repl/fn
;;                   main.clj:  458  clojure.main/repl
;;                   main.clj:  368  clojure.main/repl
;;                RestFn.java: 1523  clojure.lang.RestFn/invoke
;;     interruptible_eval.clj:   84  nrepl.middleware.interruptible-eval/evaluate
;;     interruptible_eval.clj:   56  nrepl.middleware.interruptible-eval/evaluate
;;     interruptible_eval.clj:  152  nrepl.middleware.interruptible-eval/interruptible-eval/fn/fn
;;                   AFn.java:   22  clojure.lang.AFn/run
;;                session.clj:  218  nrepl.middleware.session/session-exec/main-loop/fn
;;                session.clj:  217  nrepl.middleware.session/session-exec/main-loop
;;                   AFn.java:   22  clojure.lang.AFn/run
;;                Thread.java:  833  java.lang.Thread/run




;; Can the value be printed along with the exception in some cases like below

;; Current error on 1.10

(map identity (map 123 (range 10)))
;; Error printing return value (ClassCastException) at clojure.core/map$fn (core.clj:2753).
;; java.lang.Long cannot be cast to clojure.lang.IFn

;; Adding the value that cannot be cast might give better context

(map identity (map 123 (range 10)))
;; Error printing return value (ClassCastException) at clojure.core/map$fn (core.clj:2753).
;; java.lang.Long (123) cannot be cast to clojure.lang.IFn


;; The error message phase here is in printing the return value. So it can’t be printed, because that is where the error occurs. A lot of lazy sequence errors end up looking like this.

;; This particular error is a case where the invocation is wrong (args swapped), which is really a perfect case for a spec or precondition, to avoid constructing a broken lazy seq. I’ve been thinking we should probably improve the particular “can’t cast to IFn” too since it’s so common.

;; https://github.com/slipset/speculative is ok and has a spec that would catch this.
