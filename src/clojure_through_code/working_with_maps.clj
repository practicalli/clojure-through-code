(ns clojure-through-code.working-with-maps)


(def best-movie {:name "Empire Strikes Back"
                 :actors {"Leia" "Carrie Fisher",
                          "Luke" "Mark Hamill",
                          "Han" "Harrison Ford"}})

(get best-movie :name)
;; => "Empire Strikes Back"

;; keywords are also functions
;; who look themselves up in maps!
(:name best-movie)
;; => "Empire Strikes Back"


;; access nested maps:
(get-in best-movie [:actors "Leia"])
;; => "Carrie Fisher"



(def planets {
              :mercury [3.303e+23, 2.4397e6]
              :venus   [4.869e+24, 6.0518e6]
              ,,, })


(defn mass [planet]
  (first (planet planets)))

(defn radius [planet]
  (second (planet planets)))

(name :mercury)
;; => "mercury"

(keyword "mercury")
;; => :mercury


(keys planets)
;; => (:mercury :venus)


;; Iterate over nested maps
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; TODO: link to Iterate over vectors and mixed data structures

;; Collection processing exercise - hours of true

;; The project manager is responsible for multiple projects.

;; How many capex hours have been spent across all projects?

(def project-management
  {"project A" {"hours" 7, "capex" true},
   "project B" {"hours" 3, "capex" false},
   "project C" {"hours" 6, "capex" true}})


;; Lets get the projects that have capex hours

(for [project project-management
      :when (= true (get-in [project "capex"] project-management))]
  (get-in [project "hours"] project))
;; => ()

(get-in ["project A" "capex"] ["project A" {"hours" 7, "capex" true}])
;; => nil


(get ["project A" {"hours" 7, "capex" true}] "capex")
;; => nil


(get ["project A" {"hours" 7, "capex" true}] "project A")
;; => nil


(get (second ["project A" {"hours" 7, "capex" true}]) "capex")
;; => true

(for [project project-management
      :when (= true (get-in [project "capex"] project-management))]
  (get (second project) "hours"))


(for [project project-management
      :when (= true (get-in [project "capex"] project-management))]
  (get-in [project "hours"] project-management))



;; project is the full map and not just the project key name
(for [project project-management
      :let [capex (get-in [project "capex"] project-management)]
      :when (= capex true )]
  (get-in [project "hours"] project-management))


;; create a second let to capture the project name
(for [project project-management
      :let [project-name (keys project)  ; intermediate data structure created by for is a vector, so project name not relevant
            capex (get-in [project "capex"] project-management)]
      :when (= capex true )]
  (get-in [project-name "hours"] project-management))


(for [project project-management
      :let [capex (get (second project) "capex")]
      :when (= capex true )]
  (get (second project) "hours"))
;; => (7 6)


;; refactor this further by removing duplicate code
;; the expression (second project) is called twice,
;; so we add that as a local binding in the let function.

(for [project project-management
      :let [project-details (second project)
            capex (get project-details "capex")]
      :when (= capex true )]
  (get project-details "hours"))






#_(while project-management
  (into []
        (hash-map (first project-management))))

#_(while project-management
  (into []
        (hash-map (keys project-management) (vals project-management))))



;; (filter #(get-in ))


;; mappy thinking

;; get the keys from the map

(keys project-management)
;; => ("project A" "project B" "project C")

;; we have the values for the keys, so we can navigate to the hours
(let [projects (keys project-management)]
  (for [project projects]
    (get-in project-management [project "hours"] )))
;; => (7 3 6)

;; Now we want to add a condition to projects, so only those hours that are capex are returned

(let [projects (keys project-management)]
  (for [project projects
        :when (get-in project-management [project "capex"] )]
    (get-in project-management [project "hours"] )))
;; => (7 6)


;; Now we just need to total the hours and we are finished.

(let [projects (keys project-management)]
  (for [project projects
        :when (get-in project-management [project "capex"] )]
    (reduce + (get-in project-management [project "hours"] ))))
;; java.lang.IllegalArgumentException
;; Don't know how to create ISeq from: java.lang.Long
;; reduce is trying to work before the for has all the values?

(reduce
 +
 (let [projects (keys project-management)]
   (for [project projects
         :when (get-in project-management [project "capex"] )]
     (reduce + (get-in project-management [project "hours"] )))))
;; java.lang.IllegalArgumentException
;; Don't know how to create ISeq from: java.lang.Long
;; reduce is trying to work before the for has all the values?

;; Using reduce on the final form is correct, so reduce must be trying to work before the values are put into a sequence.
(reduce + '(7 6))
;; => 13


;; working solution

(def project-capex-hours
  (let [projects (keys project-management)]
    (for [project projects
          :let [hours (get-in project-management [project "hours"])]
          :when (get-in project-management [project "capex"])]
      hours)))

project-capex-hours;; => (7 6)

(reduce + project-capex-hours)




;; Generating hash-maps
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


;; simple hash-maps

(zipmap [:a :b :c :d :e] (range 0 5))


;; Generate nested hash-maps

(into {}
      (for [character [:a :b :c :d :e]
            :let      [map-key character
                       map-value  (zipmap [:a :b :c :d :e] (range 0 5))]]
        {map-key map-value}))
;; => {:a {:a 0, :b 1, :c 2, :d 3, :e 4}, :b {:a 0, :b 1, :c 2, :d 3, :e 4}, :c {:a 0, :b 1, :c 2, :d 3, :e 4}, :d {:a 0, :b 1, :c 2, :d 3, :e 4}, :e {:a 0, :b 1, :c 2, :d 3, :e 4}}


;; pretty print the output of a nested map

(require 'clojure.pprint)

(clojure.pprint/pprint
  (into {}
        (for [character [:a :b :c :d :e]
              :let      [map-key character
                         map-value  (zipmap [:a :b :c :d :e] (range 0 5))]]
          {map-key map-value}))
  )
;; {:a {:a 0, :b 1, :c 2, :d 3, :e 4},
;;  :b {:a 0, :b 1, :c 2, :d 3, :e 4},
;;  :c {:a 0, :b 1, :c 2, :d 3, :e 4},
;;  :d {:a 0, :b 1, :c 2, :d 3, :e 4},
;;  :e {:a 0, :b 1, :c 2, :d 3, :e 4}}
