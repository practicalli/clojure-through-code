;; namespaces are similar to packages in Java, in that they are a grouping of data and behaviour
;; namespaces allow you to structure your Clojure code into logical groupings
;; namesaces help you use functions defined in one namespace in a different namespace
;; using the (use) function in clojure.
(ns clojure-through-code.01-basics)



;;;;;;;;;;;;;;;;;;;;;;;
;; Clojure Syntax


;; Clojure uses a prefix notation and () [] : {} # @ ! special characters
;; no need for lots of ; , and other silly things...

;; All behaviour in Clojure is defined in a List, with the first elemtent being a function
;; and the remaining elements being arguments to that function.

;; join strings together with the function str
(str "Hello" " " "Hackerfernce UK")

;; Get the first 3 elements of a vector (a vector can be thought of as an immutable array)
(take 3 [1 2 3 4 5 6])

(println "Hello Hackference Birmingham")

;; using println in LightTable shows the results in console window, as its a side affect
;; using srt you see the results of the evaluation inline with the code,
;  as the result of a definition or an expression.

;; Avoid code that creates side-effects where possible to keep your software less complex.
;; using the fast feedback of the REPL usually works beter than println statements in debuging


;; Simple math to show you the basic structure of Clojure

; Math is straightforward
(+ 1 1 2489 459 2.) ; => 2
(- 2 1) ; => 1
(* 1 2) ; => 2
(/ 2 1) ; => 2

;; Clojure uses Prefix notation, so math operations on many arguments is easy.
(+ 1 2 3 4 5)

(+ 1 2 (* 3 4) (- 5 6 -7))

(apply + [1 2 3])

(repeat 4 9)

; Equality is =
(= 1 1) ; => true
(= 2 1) ; => false

;; Equality is very useful when your data structures are immutable

; You need not for logic, too
(not true) ; => false


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Truethy experiments

;; some truthiness with math functions - looking at types
(+)
(class (+))
(*)
(true? +)
(false? +)
(true? *)
(false? *)
(true? 1)
(true? -1)
(true? true)
(- 2)
; (class (/))  ;; wrong number of arguments error


;; Predicates - take a value and return a boolean result (true | false)
(true? true)
(true? (not true))
(true? false)
(true? (not false))
(true? nil)

; Types
;;;;;;;;;;;;;

; Clojure uses Java's object types for booleans, strings and numbers.
; Use `class` to inspect them.

(class 1)
; Integer literals are java.lang.Long by default
(class 1.1)    ; Float literals are java.lang.Double

(class "")
; Strings always double-quoted, and are java.lang.String

(class false)  ; Booleans are java.lang.Boolean
(class nil)    ; The "null" value is called nil

(class (list 1 2 3 4))


(class true)
(class ())
(class (list 1 2 34 5))
(class (str 2 3 4 5))
(class (+ 22/7))
(class my-data)
(class 5)
(class "fish")
(type [1 2 3])
(type {:a 1 :b 2})
(doc type)

(take data (range ))
; Built in symbols - start and end with *
*clojure-version*




;; Ratios
;; To help maintain precision, Clojure has a type called Ratio
;; so when you are dividing numbers you can keep the as a fraction using whole numbers
;; rather than constrain the result to a approximate
(/ 2)

;; A classic example is dividing 22 by 7 which is approximately the value of Pi
(/ 22 7)

(class (/ 22 7))

;; If you want to force Clojure to evaluate this then you can specify one of the
;; numbers with a decimal point
(class (/ 22 7.0))






;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Documentation in Clojure & LightTable


;; Clojure functions have Documentation
;; - in LightTable you can right-click on a function name and select "show docs"
;; documentation is shown inline

;; Lets look at the documentation for map
(class [])

(map? [1 2 3 4])



;;;;;;;;;;;;;;;;;;
;; Lazy evaluation

;; Building up behaviour by passng functions to functions

(take 10 (iterate inc 0))
(take 10 (iterate dec 0))


;; add elements to a list using cons - I think of this as construct a list
(cons :z [:a :b])


;; Treating data as data with quote '

;; Conj needs to have a collection as the first argument
(conj (:a :b) :c)  ;; fail

(conj '(:a :b) :c)
(conj {:a "A" :c "C"} {:b "B"})

;; concat returns a list as it needs to create a new data structure
;; its more efficient to create new data structures as lists
(concat [:a :b :c] [:d :e])

;; this returns a vector, althought there is a performance hit with this
;; In clojure you can decide for your self what sort of performance you want with your app
(vec(concat [:a :b :c] [:d :e]))


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

;;(doc split-at)

;; most seuences are evaluated in blocks of 32


;; (map inc (range))

(take 10 (cycle [:hearts]))

;; find if something is in a seuence, not performant as their is no index
(some #{:b} [:a :b :c])
(filter #{:b} [:a :b :c])

;; (get-in [{:a "A" :b "B"} {0 :a}])


;;; functions

;; A symbol is something that can be used the same as its value - refferential transparency
;; There is no requirement to use symbols, although it does make the code more readable
;; and makes it easy to reuse the value (by typing the symbol rather than the function again)

;; let binds symbols to values, only existing withing the local scope
;; requires an even number of mappings (eg. symbol value pairs)

(fn [x y]
  (let [square (fn [x] (* x x))]
     (Math/sqrt (+ (square x) (square y)))))

(fn [x y]
  (letfn [(square [x] (* x x))]
     (Math/sqrt (+ (square x) (square y)))))



;; You cannot next annonymous functions inside each other (good!).


#(Math/sqrt (+ (* %1 %1) (* %2 %2)))


;; You can do a lot without defining functions

;; def ar golbally accesssible slots to bind symbols to values
;; - the are like memory locations
(def pi 3.1415926)


(def pythagorus
  (fn [x y]
    (Math/sqrt (+ (* x x) (* y y)))))

;; a simpler way to bind behaviour to a symbol
;; a list program is usually a collection of defs
(defn pythagorass
  [x y]
  (Math/sqrt (+ (* x x) (* y y))))


;; Dynamic Vars
;;(def ^:dynamic *out*)

;; meta-data can be thought of as a bit like java annotations - but dont compile in the same way

;; beware of using dynamic vars as the can get splatted when you cross threading boundaries
;; dont use this...
;;(binding [*out* (io/writer (io/file "/tmp/foo.txt"))]
;;  (println "Hello"))

;; Clojures
;;a pure function can only be written in terms of its arguments

(let [x 3]
  (fn [y]
    (Math/sqrt (+ (* x x) (* y y)))))


;; Clojure can access values safely, unlike languages such as JavaScript
;; -- with JavaScript the values are mutable, so they could change
;; Clojure values are immutable so the same result is returned what ever happens

;; Partial functions - more idiomatic to spell out the function
;; not used that much
;; (partial pythagorus 3)



;;;;;;;;;;;;;;;;;;;;;;;;
;; LightTable Settings

;; The configuration in LightTable uses a Clojure syntax which is easy enough to understand even if you dont know clojure.
;; Essentially the configurations are a datastructure, a map with Clojure keywords to make it simple to pull out the relevant settings

;; Set up keyboard shortcuts or look at the default shortcuts
;; Settings: User keymap
;; Settings: Default keymap

;; General configuration settings for LightTable - eg, Fonts, themes, editor settings
;; Settings: User behaviors








;; (doto)  ;; Chain functions together
;; ->
