(ns clojure-through-code.09-calling-java
  (import java.util.Date))
;;;;;;;;;;;;;;;;;;;;;;;
;; From java.lang

(.toUpperCase "fred")

(.getName String)

(System/getProperty "java.vm.version")

Math/PI

(def pi Math/PI)


;; Using java.util.Date import (usually include as part of the namespace definition)

;; (import java.util.Date)

(Date.)

(def *now* (Date.))

(str *now*)


;; calling static methods in java

(let [d (java.util.Date.)]
  (. d getTime))

;; or more commonly use object Instantiation

(let [d (java.util.Date.)]
  (.getTime d)


;; Mixing Clojure and Java together in code

(map (memfn toUpperCase) ["a" "short" "message"])

;; The map function applies the function/method toUpperCase to each element in ["a" "short" "message"]

;; You can also use the bean function to wrap a Java bean in an immutable Clojure map.

(bean (new Person "Alexandre" "Martins"))
;; -> {:firstName "Alexandre", :lastName "Martins"}

;; Once converted, you can manipulate the new map using any of Clojure’s map functions, like:

(:firstName (bean (new Person "Alexandre" "Martins")))
;; -> Alexandre

