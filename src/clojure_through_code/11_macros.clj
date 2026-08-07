(ns clojure-through-code.11-macros)

;; The language of Clojure is built on a small number special forms

;; if let loop recur do set! quote var

;; and to make Java/JVM interop work really nicely
;; new . throw try

;; There are nearly 700 functions in `clojure.core`
;; and around 50 macros to simplify and extend the language

;; Using a macro from `clojure.core` is a common part of writing Clojure

;; Defining your own macros is an exceptional part of writing Clojure.
;; Write a macro only when you really need to extend the Clojure language
;; and only if there is enough justifiction (e.g. a macro providing value over numerous projects)

;; Example: defn
;; defn is a macro to make it easier to create functions as well as keeping
;; the clojure code nice and clean

;; So lets define a function taking one argument and a simple body:

(defn my-function
  [args]
  (str args " " "is a macro"))

(my-function "defn")

;; To write this out without defn, we would use def function
;; (def anonymous-function-with-shared-name (fn [args] (str args " " "is a macro")))

;; We can check that we understand what the macro expands to
;; by using the function macroexpand.
;; The expression we want to expand needs to be quoted so its not evaluated

(macroexpand
  '(defn my-function
     [args]
     (str args " " "is a macro")))


;; Is def a macro ?

(macroexpand '(def my-string "Is def a macro"))


;; Example: or
;; Lets see how or is actually created

;; or will evaluate the things passed to it one by one,
;; returning the first that is true or the last.
(or 1 2)


;; how is this basically implemented as a macro false?

;; (let [local-param x]
;;   (if local-param local-param y))

;; set local-param to equal x
;; if x is true then return x, otherwise return y

;; if has to be a special op because only one


;; You can also see what this function looks like under the covers

(macroexpand '(or x y))

(macroexpand '(let [local-param x]
                (if local-param local-param y)))


;; Many common functions are actually macros, built using the core primatives
;; of clojure (if def let ...)

;; Take a look at the cond function, its behaviour can be created using one of
;; these core primatives of Clojure.  Can you work out which one ?

(macroexpand
  '(cond
     (< n 0) "negative"
     (> n 0) "positive"
     :else "zero"))


;; Time is an example of a more complex macro
(macroexpand '(time (print "timing")))


;; [TODO] not sure what this expression does
(time (print "timing"))
