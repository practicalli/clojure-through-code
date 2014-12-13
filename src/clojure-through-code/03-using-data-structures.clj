;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Using Clojure data structures

(ns clojure-through-code.03-using-data-structures)


;; Defining things is easy in Clojure

(def person "Jane Doe")

(def my-data [1, 2, 3, "frog" person])

(def developer-events
  [:devoxxuk :devoxxfr :devoxx :hackthetower] )

(def dev-event-details
  {:devoxxuk       {:URL "http://devoxx.co.uk"}
   :hackthetower {:URL "http://hackthetower.co.uk"}})

(def portfolio [ { :ticker "AAPL" :lastTrade 203.25 :open 204.50}
                 { :ticker "MSFT" :lastTrade 29.12  :open 29.08 }
                 { :ticker "ORCL" :lastTrade 21.90  :open 21.83 }])


;;;;;;;;;;;;;;;;
;; Evaluating things you have defined

person

developer-events

dev-event-details

;; (dev-event-details)
(dev-event-details :devoxxuk)



;; You can get the value of this map

(def luke {:name "Luke Skywarker" :skill "Targeting Swamp Rats"})
(def darth {:name "Darth Vader"    :skill "Crank phone calls"})
(def jarjar {:name "JarJar Binks"   :skill "Upsetting a generation of fans"})

(get luke :skill)


;; Set #{}
;; a unique set of elements


;; Data has behaviour too - homoiconicity


(#{:a :b :c} :c)
(#{:a :b :c} :z)

;; You can pull out data from a Vector
([1 2 3] 1)

;; ([1 2 3] 1 2)  ;; wrong number of arguments, vectors behaving as a function expect one parameter

;; ((1 2 3) 1) ;; you cant treat lists in the same way, there is another approach - assoc


;; and there are lots of functions that work on data structures

(def evil-empire #{"Palpatine" "Darth Vader" "Boba Fett" "Darth Tyranus"})

(contains? evil-empire "Darth Vader")



;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Changing data in data structures

;; Wait, I thought you said that data structures were immutable!
;; So how can we change them then?

;; Yes, lists, vectors, maps and sets are all imutable.  However,
;; you can get a new data structure that has the changes you want.

(cons 5 '(1 2 3 4))

(cons 5 [1 2 3 4])

;; Note what cons has returned...

(conj '(1 2 3 4) 5)

(conj [1 2 3 4] 5)

;; Conj does what you would expect.



