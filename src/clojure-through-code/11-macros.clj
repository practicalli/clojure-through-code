(ns clojure-through-code.11-macros)

;; The language of Clojure is built on a small number special forms

;; if let loop recur do set! quote var

;; and to make Java/JVM interop work really nicely
;; new . throw try

;; Everything else can be built with macros, data structures & functions

;;;;;;;;;;;;;;;;;;;
;; Example: or
;; Lets see how or is actually created

;; or will evaluate the things passed to it one by one,
;; returning the first that is true or the last.
(or 1 2)


;; how is this basically implemented as a macro false?

(let [local-param x]
  (if local-param local-param y))

;; set local-param to equal x
;; if x is true then return x, otherwise return y

;; if has to be a special op because only one


;; You can also see what this function looks like under the covers

(macroexpand '(or x y))

