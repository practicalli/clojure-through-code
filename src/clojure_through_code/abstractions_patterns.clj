(ns clojure-through-code.abstractions-patterns
  (:require [clojure-through-code.database :as database]))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;
;; Functional abstractions
;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; see clojure-through-code/functional-concepts

;; - how pure are our functions ?


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;
;; Clojure patterns / idioms ?
;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


;; Alan J Perlis “It is better to have 100 functions operating on one data structure than to have 10 functions operate on 10 data structures.”

;; macros let,

;; Components
;; Stuart Siera's Components
;; - see duct project for example
;; Modular - JUXT

;; Schema - Prismatic

;; Transducers


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;
;; OO Design Patterns
;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Amsterdam Clojure meetup verdict: patterns are a sign of a limited language

;; Read "The Joy of Clojure" for a comparison of OO design patterns & functional approach in Clojure - its verdict, different paradigms.

;; Concepts and ideas from http://mishadoff.com/blog/clojure-design-patterns/

;;;;;;;;;;;;;;;;;;;;;;;;
;; Singleton

;; Assuming a list, vector, map or set can be used as a singleton.

;; However, if you bind a name using def, that def can be redefined elsewhere.  A redefinition typically only takes place during development time, but could also happen at runtime.

(def singleton-as-value 42)

;; when redefining we afect all functions that use this value via the symbol name.
(def singleton-as-value 47)


;; local bindings over-ride global def values

(def favourite-number 42)

(defn whats-my-favourte-number [number]
  (println "You said your favourite number was" favourite-number)
  (let [favourite-number (/ number 2)]
    (str "Your real favourite number is " favourite-number)))

(whats-my-favourte-number favourite-number)



;; defonce allows us to create a name for a value, assuming its not already been bound.
(defonce singleton-as-value 24)


;; Using the constantly function

(def the-answer (constantly 42))

(the-answer "What is 6 times 9")

;; it does not matter what arguments we give to 
;;;;;;;;;;;;;;;;;;;;;;;;
;; Command pattern

;; In OO, the command pattern is a behavioral design pattern in which an object is used to encapsulate all information needed to perform an action or trigger an event at a later time. This information includes the method name, the object that owns the method and values for the method parameters

(defn execute [command & args]
  (apply command args))

(execute database/login  "fey" "force@wakens")
(execute database/logout "fey")

;; Or using a shortcut form
(defn execute [command]
  (command))

(execute #(database/login  "rey" "force@wakens"))
(execute #(database/logout "rey"))


;; Where it could be useful in Clojure
;; - persistent storage - passing actions, connection & access details to minimise the code that needs specific database libraries



;;;;;;;;;;;;;;;;
;; Strategy pattern

;; enables an algorithm's behavior to be selected at runtime. The strategy pattern defines a family of algorithms, encapsulates each algorithm, and makes the algorithms interchangeable within that family.

;; Strategy lets the algorithm vary independently from clients that use it. 

;; In this example we want to pass in the sorting strategy when we want to get a sorted list of users with subscribers at the top


(def users [{:name "fred"   :subscription false}
            {:name "john"   :subscription true}
            {:name "sally"  :subscription false}
            {:name "zaphod" :subscription true}])

;; forward sort with subscribers last
(sort-by (juxt :subscription :name) users)

;; forward sort by name
(sort-by (juxt :name :subscription) users)

;; forward sort by name
(sort-by :name users)

;; forward sort with subscribers at top
(sort-by (juxt (complement :subscription) :name) users)

((complement :subscription) (first users))
((complement :subscription) (second users))
((complement :subscription) {})

;; reverse sort of users with subscribers at top
(sort-by (juxt :subscription :name) #(compare %2 %1) users)

;; too high level nonsense....





;;;;;;;;;;;;;;;;;;;;;;;;
;; State pattern

;; hmm... state pattern... its a bit like the stragegy pattern with slightly different encapsulation





;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;
;; Patterns I am weary of
;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


;;;;;;;;;;;;;;;;;;;;;;;;
;; Visitor pattern

;; if a language support multiple dispatch, you don't need Visitor pattern

(defmulti export
  (fn [item format] [(:type item) format]))

;; Message
{:type :message :content "Say what again!"}
;; Activity
{:type :activity :content "Quoting Ezekiel 25:17"}

;; Formats
;; :pdf, :xml

;; (defmethod export [:activity :pdf] [item format]
;;   (exporter/activity->pdf item))

;; (defmethod export [:activity :xml] [item format]
;;   (exporter/activity->xml item))

;; (defmethod export [:message :pdf] [item format]
;;   (exporter/message->pdf item))

;; (defmethod export [:message :xml] [item format]
;;   (exporter/message->xml item))


;; (defmethod export :default [item format]
;;   (throw (IllegalArgumentException. "not supported")))


;; (derive ::pdf ::format)
;; (derive ::xml ::format)


;; (defmethod export [:activity ::pdf])
;; (defmethod export [:activity ::xml])
;; (defmethod export [:activity ::format])

;; ;; If some new format (i.e. csv) appears in the system

;; (derive ::csv ::format)



;;;;;;;;;;;;;;;;;;;;;;;;
;; Template pattern




;; Strategy

(defn cooley-tukey [signal] ,,,)
(defn prime-factor [signal] ,,,)

(defn choose-fft []
  (if relatively-prime? prime-factor cooley-tukey))

(defn main []
  (let [signal (get-signal)] ((choose-fft) signal)))



;; Adapter

(defprotocol BarkingDog "this is a barking dog" (bark [this] "dog should bark"))

(extend-protocol BarkingDog clojure.lang.IPersistentVector
                 (bark [v] (conj v "bark!")))

(def a-vector [1 2 3 4])

(bark a-vector) [1 2 3 4 "bark!"]


;;  Template Method

(defn update-account-status [account-id get-fn status save-fn]
  (let [account (get-fn account-id)]
    (when (not= status (:status account))
      (log/info "Updating status for account:" account-id)
      (save-fn (assoc account :status status)))))

;; (defn get-account-from-mysql ,,,)
;; (defn get-account-from-datomic ,,,…)
;; (defn get-account-from-http ,,,)
