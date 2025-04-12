(ns clojure-through-code.09-calling-java
  (:import
    java.util.Date))


;;
;; From java.lang

(.toUpperCase "fred")

(.getName String)


;; (java.util.Date)
(Date.)


;; From java.lang.System getProperty() as documented at:
;; http://docs.oracle.com/javase/8/docs/api/java/lang/System.html
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


;; or more commonly use object instance

(let [d (java.util.Date.)]
  (.getTime d))


;; Mixing Clojure and Java together in code

(map (memfn toUpperCase) ["a" "short" "message"])


;; The map function applies the function/method toUpperCase to each element in ["a" "short" "message"]

;; You can also use the bean function to wrap a Java bean in an immutable Clojure map.

;; (bean (new Person "Alexandre" "Martins"))
;; -> {:firstName "Alexandre", :lastName "Martins"}

;; Once converted, you can manipulate the new map using any of Clojure’s map functions, like:

;; (:firstName (bean (new Person "Alexandre" "Martins")))
;; -> Alexandre


;; Miscellaneous

;; Java calls are not first class functions, so you need to wrap them in a function (either named or anonymous) before you can use them with functions like map, apply etc

;; So in this example, the direct use of Math/sqrt will fail because its not a first class function.

;; (map Math/sqrt (range 1 10))

;; however if you wrap the call to Math/sqrt in a function, in this case an anonymous function, then it will become a first calss function that can be mapped over a collection.

(map #(Math/sqrt %) (range 1 10))
