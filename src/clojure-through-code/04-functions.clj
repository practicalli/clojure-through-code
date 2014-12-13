;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Defining things in Clojure

;; Clojure design at its most basic comprises:
;; - one or more data structures
;; - functions that process those datastructures

;; namespace
(ns clojure-through-code.04-functions)

;;;;;;;;;;;;;;;;;;;;;;
;; Defining behaviour by writing functions

;; Clojure has functions, rather than methods for defining behaviour / "algorithms"
;;


; Use fn to create new functions. A function always returns
; its last statement.
(fn [] "Hello Hackference")

((fn [] "Hello Hackference"))

;; remember, the first item in a list is evaluted as a function call

; we have seen how to give a data structure a name (var) using def

(def x 1)
x

; We can also give a name to a function using def

(def hello-world (fn [] "Hello hacker world"))
(hello-world)

; You can shorten this process by using defn
(defn hello-world [] "Hello hackers, are you loving Clojure yet?")

; The [] is the list of arguments for the function.

(defn hello [name]
  (str "Hello " name))
(hello "Steve") ; => "Hello Steve"

; You can also use the annonymous function shorthand, #, to create functions

(def hello2 #(str "Hello " %1 ", are you awake yet?"))
(hello2 "Mike")

; You can have multi-variadic functions too
;; one function that behaves differently dependant on the number or arugments passed

(defn hello-with-args
  ([] "Hello Hackference")
  ([name] (str "Hello " name ", hope your talk is awesome")))
(hello-with-args "Cristiano")
(hello-with-args)

; Functions can pack extra arguments up in a seq for you
(defn count-args [& args]
  (str "You passed " (count args) " args: " args))
(count-args 1 2 3)

; You can mix regular and packed arguments
(defn hello-count [name & args]
  (str "Hello " name ", you passed " (count args) " extra args"))
(hello-count "Finn" 1 2 3)




;; using a local variables... (its not actulally a variable as it doenst vary)

(defn do-stuff [argument]
  (let [local-data (first argument)]
    (str local-data)))

(do-stuff my-data)

;; Remember, the first item in a list is evaluated as a call to a function.


;; We all love hello world, so here we go...

(defn hello-world [name]
  (println (str "Hello " name ", welcome to the world of Clojure")))

;; Something about the above function is not quite right...




;;;;;;;;;;;;;;;;;;;;;;
;; Defining more detailed data structures


(def john {:firstname "John" :lastname "Stevenson"})

;; datastructures can be called like a function, returning a .values

;; (john :firstname :lastname)
(john :firstname )
(:lastname john)
(get john :lastname)

(def people {
     :001 {:name "John Stevenson"   :twitter "jr0cket"}
     :002 {:name "Mike Mechanic"    :twitter "mechano"}
     :006 {:name "Patrick McGoohan" :twitter "theprisoner"}})

(people :001)

(get-in people [:006 :twitter])



(def people-locations {
     :Birmingham [{:name "John Stevenson"   :twitter "jr0cket"}
                  {:name "Mike Mechanic"    :twitter "mechano"}]
     :TheIsland   {:name "Patrick McGoohan" :twitter "theprisoner"}})


(get-in people-locations [:Birmingham 1 :twitter])


;;;;;;;;;;;;;;;;;;;;;;;;;
;; Filtering out data



(def payroll-data [
     [:000-00-0000 "TYPE 1" "JACKSON" "FRED"]
     [:000-00-0001 "TYPE 2" "SIMPSON" "HOMER"]
     [:000-00-0002 "TYPE 4" "SMITH" "SUSAN"]])

(def data-format [0 3 2])

(defn clean-payroll-data
  [raw-data data-format]
  (map #(get-in raw-data [% data-format] nil) (range (count raw-data))))

;; the % symbol is used to refer to the functions return value


(apply interleave
  (map
   (partial clean-payroll-data payroll-data) data-format))

;; (clean-payroll-data payroll-data data-format) ;=> (nill nil nil)




(def hack-data {:max-sandwiches 300
                :max-attendees 200
                :weather "good"})


(defn random-sandwiches
  "Unintelligent guess at how many sandwiches will be eaten at Hackference"
  [hackference]
  (rand-int hackference))

(defn how-many-sandwiches []
  (hack-data :max-sandwiches))


(random-sandwiches 50)
(how-many-sandwiches )




(def hack-data-detailed {
     :max-sandwiches 300
     :max-attendees 200
     :weather "good"
     :languages {:dynamic "clojure" :slapdash "javascript" :scary "haskell" }})


(defn best-hack-language []
   (get-in hack-data-detailed [:languages :dynamic]))


(defn worst-hack-language []
   (get-in hack-data-detailed [:languages :scala] "No time for Scala"))


(best-hack-language)
(worst-hack-language)




;; a little sugar on your syntax with the threading macro
;; this passes the data structue to each keyword and values are
;; values are effectively filtered out
;; It is not possible to provide a default result using this syntax
(-> hack-data-detailed :languages :dynamic)


;; Or you can try some destructuring with let
(let [{{best-language :dynamic} :languages} hack-data-detailed ]
  [best-language])


;; Fun with multiple arity (the number of parameters)

(defn any-sandwiches-left
  ([mike]"Yes, no rush")
  ([mike john]"Better move quickly...")
  ([mike john andy]"You have no chance, better go to morrisons"))


(any-sandwiches-left "mike")


;; How does this multi-arity work?
;; The parameters are bound to there aguments when compiled.

(def mult
  (fn this
      ([] 1)
      ([x] x)
      ([x y] (* x y))
      ([x y & more]
          (apply this (this x y) more))))




(def locations {
     :london {:latitude 34, :longtitude 57}})

(def location {
     :london     {:gps {:co-ords [12.37 53.78]}}
     :new-york   {:gps {:co-ords [12.37 53.78]}}
     :Birmingham {:gps {:co-ords [12.37 53.78]}}})



;;nested maps and some ways of getting at the keys

(def me {:name
         {:firstname "John"
          :middlename "Lawrence"
          :surname "Aspden"}
         :address
         {:street "Catherine Street"
          :town {:name "Cambridge"
                 :county "Cambridgeshire"
                 :country{
                          :name "England"
                          :history "Faded Imperial Power"
                          :role-in-world "Beating Australia at Cricket"}}}})


(:name me)
(get me :name)

(get-in me [:name :middlename])
(reduce get me [:address :town :country :role-in-world])
(-> me :address :town :county)

(assoc-in me [:name :initials] "JLA")
(update-in me [:address :street] #(str "33 " %))



;;;;;;;;;;;;;;;;;;;;;;
;; Documenting

(defn foo
  "Documenting your code is easy, simpky add a string as a description of your functions"
  [x]
  (str x " - Hello, World!"))

;; Once you have defined a function, you can call it passing any arguments it requires

(foo "Devoxx UK")

;; Evaluation in LightTable
;; - defined values have a green background
;; - evaluated values have a grey background

(defn lets-be-lazy [x]
  (range x))

(lets-be-lazy 5)


(foo "Fred")
