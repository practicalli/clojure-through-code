;; ---------------------------------------------------------
;; Using the Java Language from Clojure
;;
;; Clojure is hosted on the Java Virtual Machine and can run any JVM language
;; `java.lang` classes and methods are always available from Clojure
;; Additional Java & JVM Language libraries can be imported into a Clojure namespace
;; ---------------------------------------------------------

(ns clojure-through-code.09-calling-java
  (:import
    java.util.Date))


;; From java.lang

;; String manipulation
(.toUpperCase "fred")


;; Imported library from the Java Language (java.util.Date)
;; Using java.util.Date import (include in the namespace definition above)
;; (import java.util.Date)

(Date.)


;; names with ** 'earmuffs' are dynamic, meant to be rebound to different values
(def ^{:dynamic true} *now* (Date.))

(str *now*)


;; From java.lang.System getProperty() as documented at:
;; http://docs.oracle.com/javase/8/docs/api/java/lang/System.html
(System/getProperty "java.vm.version")


;; Mathematics
(def pi Math/PI)


(defn circumference
  [diameter]
  (* pi diameter))


(circumference 42)


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
