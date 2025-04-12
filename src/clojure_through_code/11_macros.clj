(ns clojure-through-code.11-macros)


;; The language of Clojure is built on a small number special forms

;; if let loop recur do set! quote var

;; and to make Java/JVM interop work really nicely
;; new . throw try

;; Everything else can be built with macros, data structures & functions

;;
;; Example: defn
;; defn is a macro to make it easier to create functions as well as keeping
;; the clojure code nice and clean

;; So lets define a function taking one argumentand a simple body:

(defn my-function
  [args]
  (str args " " "is a macro"))


;; To write this out without defn, we would use def function
(def my-function (fn [args] (str args " " "is a macro")))

(my-function "defn")


;; We can check that we understand what the macro expands to
;; by using the function macroexpand.
;; The expression we want to expand needs to be quoted so its not evaluated

(macroexpand
  '(defn my-function
     [args]
     (str args " " "is a macro")))


;; Is def a macro ?

(macroexpand '(def my-string "Is def a macro"))


;; Leiningen also uses a macro for the project configuration

;; [TODO: it would seem that defproject is not a macro, doh!].

;; you may have noticed that the project.clj file is written using clojure
;; The defproject expression looks like a map, as it uses key value pairs
;; to define most of the project.  However, defproject is called as a function.

;; Lets take a look at what happens when defproject is called

;; (macroexpand-1
;;  '(defproject clojure-through-code "20.1.5-SNAPSHOT"
;;     :description "Learning Clojure by evaluating code on the fly"
;;     :url "http://jr0cket.co.uk"
;;     :license {:name "Eclipse Public License"
;;               :url "http://www.eclipse.org/legal/epl-v10.html"}
;;     :dependencies [[org.clojure/clojure "1.7.0"]]))


;;
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


;; You can make the output formatted, however using pprint will send the output
;; to the console if you are in LightTable or another IDE.
(clojure.pprint/pprint (macroexpand '(or x y)))


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
