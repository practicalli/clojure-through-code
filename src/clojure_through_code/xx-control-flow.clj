(ns clojure-through-code.xx-control-flow)

(def salary 60000)

;; (if test then else)
;; to put that in rough english: if something is true, then do this, else do the other thing
;; The if function evaluates the test returning the value in the "then" position
;; and the value in the "else" position if false.

;; As if is a function, it must always return a value, even if that value is nil.

;; Test if the salary is over 50,000 - if it is then you are a fat cat.
(if (> salary 50000) "You are a fat cat" "You are the salt of the earth")

(if (> 50000 salary) "Fat cat" "Salt")

;; What happens with no values after the test ?
;; (if (> salary 40000))

;; Or just one value after the test ?

;; (if (> salary 50000) "Yay, you have a job")

;; (if (> salary 80000) "Yay, you have a job")


;;;; Using when instead of if
;; You can use the when function if you have multiple things you want to do if true,
;; and you know there will never be an else condition

(when true
  (println "hello, i am a side effect, please destroy me")
  "return some value")

(when (> 3 2)
  (println "3 is greater than 2")
  "Higher")

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; for

;; process the items in a collection with destructing

(for [[i j k]
      [[0 1 2] [0 2 3] [1 0 4] [2 1 4] [3 2 4] [0 3 4]]]
  (println "i: " i "j: " j " and k: " k))

;;;;;;;;;;;;;;;;;;;;;;;;
;; while

;; another example of a more procedural control flow


;; create a counter using a mutable counter
(def counter (atom 10))

;; While the counter is positive (is a number greater than zero), print out the current value of the counter.
(while (pos? @counter)
  (do
    (println @counter)
    (swap! counter dec)))


;;;;;;;;;;;;;;;;;;;;;;;;
;; 
;;; If you have multiple things to do, you can still use if with a little help from do or the threading macro

(defn lower-public-spending []
  (println "No more silk ties for ministers"))

(defn raise-public-spending []
  (println "Time for a new duck castle, including a moat"))

(if (> salary 50000)
  (do
    (raise-public-spending)
    "Thank you for being a fat cat")
  (do
    (lower-public-spending)
    "You are the salt of the earth"))

;; You can also use the threading macro, especially if the expressions take each others result as an argument

;; > **comment** The threading macros are a way to read Clojure from left to right, rather than from inside out

;; a really simple example of the two threading macros

;; Thread-first macro
;; The result of the first evaluation is past as the first argument to the next function
(->
 7
 (/ 22))

;; Thread-last macro
;; The result of the first evaluation is past as the last argument to the next function
(->>
 7
 (/ 22))

;; Tip: if you find the threading macro hard to read,
;; you can place ,,, where the result of the evaluation would be in the following function
;; as commas are treated as whitespace

(->
 7
 (/ ,,, 22))

(->>
 7
 (/ 22 ,,,))


;; Its common to formatting the threading macro with each expression on a new line



;;;; Threading macro examples



;; (defn math-steps [x]
;;   (-> x
;;       (* 5)
;;       (+ 3)))


;;     (->> (seq accounts)
;;          (filter #(= (:type %) 'savings))
;;          (map :balance)
;;          (apply +))

;; expands to

;;     (apply +
;;       (map :balance
;;         (filter #(= (:type %) 'savings)
;;           (seq accounts))))


;; You can see for yourself:

;; (macroexpand `(-> 42 inc dec))


;; (doto person
;;   (.setFName "Joe")
;;   (.setLName "Bob")
;;   (.setHeight [6 2]))



;; Quite some time back I wrote a blog post on the Thrush Combinator implementation in Scala. Just for those who missed reading it, Thrush is one of the permuting combinators from Raymond Smullyan's To Mock a Mockingbird. The book is a fascinating read where the author teaches combinatorial logic using examples of birds from an enchanted forest. In case you've not yet read it, please do yourself a favor and get it from your favorite book store.

;; A Thrush is defined by the following condition: Txy = yx. Thrush reverses the order of evaluation. In our context, it's not really an essential programming tool. But if you're someone who takes special care to make your code readable to the human interface, the technique sometimes comes in quite handy.

;; Recently I came across Thrush in Clojure. You don't have to implement anything - it's there for you in the Clojure library implemented as a macro ..

;; Conside this simple example of bank accounts where we represent an account as a Map in Clojure ..

;; (def acc-1 {:no 101 :name "debasish" :type 'savings :balance 100})
;; (def acc-2 {:no 102 :name "john p." :type 'checking :balance 200})


;; We have a list of accounts and we need to find all savings accounts and compute the sum of their current balances .. well not too difficult in Clojure ..

;; (defn savings-balance
;;   [& accounts]
;;   (apply +
;;     (map :balance
;;       (filter #(= (:type %) 'savings)
;;         (seq accounts)))))


;; To a programmer familiar with the concepts of functional programming, it's quite clear what the above function does. Let's read it out for all of us .. From a list of accounts, filter the ones with type as savings, get their balances and report the sum of them. That was easy .. but did you notice that we read it inside out from the implementation, which btw is a 4 level nested function ?

;; Enter Thrush (Threading macro)

;; Being a permuting combinator, Thrush enables us to position the functions outside in, in the exact sequence that the human mind interprets the problem. In our Scala version we had to implement something custom .. with Clojure, it comes with the standard library .. have a look ..

;; (defn savings-balance
;;   [& accounts]
;;   (->> (seq accounts)
;;        (filter #(= (:type %) 'savings))
;;        (map :balance)
;;        (apply +)))


;; ->> is implemented as a macro in Clojure that does the following :

;; threads the first form (seq accounts) as the last argument of the second form (the filter), which makes (seq accounts) the last argument of filter
;; then makes the entire filter form, including the newly added argument the last argument of the map
;; .. and so on for all the forms present in the argument list. Effectively the resulting form that we see during runtime is our previous version using nested functions. The Thrush combinator only dresses it up nicely for the human eyes synchronizing the thought process with the visual implementation of the logic. And all this at no extra runtime cost! Macros FTW :)

;; ->> has a related cousin ->, which is same as ->>, but only threads the forms as the second argument of the next form instead of the last. These macros implement Thrush in Clojure. Idiomatic Clojure code is concise and readable and using a proper ubiquitous language of the domain, makes a very good DSL. Think about using Thrush when you feel that reordering the forms will make your API look more intuitive to the user.

;; Thrush also helps you implement the Decorator pattern in a very cool and concise way. In my upcoming book, DSLs In Action I discuss these techniques in the context of designing DSLs in Clojure.





;; Idea
;; A searchable website of examples in clojure by topic, function name, concept, or any combination of them
