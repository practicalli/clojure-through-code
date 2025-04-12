(ns clojure-through-code.working-with-maps)


;; Generating hash-maps

(hash-map)


;; => {}

;; Arguments must be a key value pair (or pairs)
#_(hash-map :key-without-value)


;; java.lang.IllegalArgumentException
;; No value supplied for key: :key-without-value


(hash-map :movie "The Last Jedi")


;; => {:movie "The Last Jedi"}


;; Arguments that include the same key will use the value associated with the last key in the argument list
(hash-map :movie "The Last Jedi" :movie "The Empire Strikes Back")


;; => {:movie "The Empire Strikes Back"}


;; example: defining pseudonyms that are author's nom de plume (writing names)
;; A vector of strings is used as the value as an author may have more than one writing name
(hash-map "Samuel Langhorne Clemens" ["Mark Twain" "Sieur Louis de Conte"]
          "Mary Ann Evans" ["George Eliot"]
          "Charlotte Brontë" ["Currer Bell"]
          "Anne Brontë" ["Anne Bell"]
          "Ellis Brontë" ["Acton Bell"]
          "Alice B. Sheldon" ["James Tiptree Jr"]
          "Charles Dodgson" ["Lewis Carroll"]
          "Robert A. Heinlein" ["Anson MacDonald" "Caleb Strong"]
          "Stephen King" ["Richard Bachman"])


;; Hash-map designs can be as intricate as required to model the data of the system.
;; However, the more complex a data structure is, the more complex it may be to work with


;; Accessing hash-maps

(def best-movie
  {:name "Empire Strikes Back"
   :actors {"Leia" "Carrie Fisher" "Luke" "Mark Hamill" "Han" "Harrison Ford"}})


(get best-movie :name)


;; => "Empire Strikes Back"

;; keywords are also functions
;; who look themselves up in maps!
(:name best-movie)


;; => "Empire Strikes Back"


;; access nested maps:
(get-in best-movie [:actors "Leia"])


;; => "Carrie Fisher"


;; => "Empire Strikes Back"

;; keywords are also functions
;; who look themselves up in maps!
(:name best-movie)


;; => "Empire Strikes Back"

(best-movie :actors)

(-> best-movie :actors)


;; access nested maps:
(get-in best-movie [:actors "Leia"])


;; => "Carrie Fisher"

(-> best-movie :actors "leia")


(def planets
  {:mercury [3.303e+23, 2.4397e6]
   :venus   [4.869e+24, 6.0518e6]
   ,,,})


(defn mass
  [planet]
  (first (planet planets)))


(defn radius
  [planet]
  (second (planet planets)))


(name :mercury)


;; => "mercury"

(keyword "mercury")


;; => :mercury


(keys planets)


;; => (:mercury :venus)



;; Combining data structures into maps
;;

(merge {:a 1 :b 2 :c 3} {:b 24 :d 4})


;; => {:a 1, :b 24, :c 3, :d 4}

(merge {:recipie "tofu curry"})


;; Iterate over nested maps
;;

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
      :when (= capex true)]
  (get-in [project "hours"] project-management))


;; create a second let to capture the project name
(for [project project-management
      :let [project-name (keys project)  ; intermediate data structure created by for is a vector, so project name not relevant
            capex (get-in [project "capex"] project-management)]
      :when (= capex true)]
  (get-in [project-name "hours"] project-management))


(for [project project-management
      :let [capex (get (second project) "capex")]
      :when (= capex true)]
  (get (second project) "hours"))


;; => (7 6)


;; refactor this further by removing duplicate code
;; the expression (second project) is called twice,
;; so we add that as a local binding in the let function.

(for [project project-management
      :let [project-details (second project)
            capex (get project-details "capex")]
      :when (= capex true)]
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
    (get-in project-management [project "hours"])))


;; => (7 3 6)

;; Now we want to add a condition to projects, so only those hours that are capex are returned

(let [projects (keys project-management)]
  (for [project projects
        :when (get-in project-management [project "capex"])]
    (get-in project-management [project "hours"])))


;; => (7 6)


;; Now we just need to total the hours and we are finished.

(let [projects (keys project-management)]
  (for [project projects
        :when (get-in project-management [project "capex"])]
    (reduce + (get-in project-management [project "hours"]))))


;; java.lang.IllegalArgumentException
;; Don't know how to create ISeq from: java.lang.Long
;; reduce is trying to work before the for has all the values?

(reduce
  +
  (let [projects (keys project-management)]
    (for [project projects
          :when (get-in project-management [project "capex"])]
      (reduce + (get-in project-management [project "hours"])))))


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
          :let    [hours (get-in project-management [project "hours"])]
          :when   (get-in project-management [project "capex"])]
      hours)))


project-capex-hours; => (7 6)

(reduce + project-capex-hours)


;; Generating hash-maps
;;


;; simple hash-maps

(zipmap [:a :b :c :d :e] (range 0 5))


;; => {:a 0, :b 1, :c 2, :d 3, :e 4}


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
          {map-key map-value})))


;; {:a {:a 0, :b 1, :c 2, :d 3, :e 4},
;;  :b {:a 0, :b 1, :c 2, :d 3, :e 4},
;;  :c {:a 0, :b 1, :c 2, :d 3, :e 4},
;;  :d {:a 0, :b 1, :c 2, :d 3, :e 4},
;;  :e {:a 0, :b 1, :c 2, :d 3, :e 4}}


;; Transforming collections of maps
;;

(def music-collection
  [{:album-name "Tubular Bells" :artist "MikeOldfield"}
   {:album-name "Smells like teen spirit" :artist "Nirvana"}
   {:album-name "Vision Thing" :artist "Sisters of Mercy"}
   {:album-name "Here comes the war" :artist "NewModelArmy"}
   {:album-name "Thunder & Consolation" :artist "NewModelArmy"}])


(comment
  music-collection
  )


;; if we want albums by artist

{"Mike Oldfield"    ["Tubular Bells"]
 "Nirvana"          ["Smells like teen spirit"]
 "Sisters of Mercy" ["Vision Thing"]
 "New Model Army"   ["Here comes the war" "Thunder & Consolation"]}


#_(reduce (fn [result [key value]]
            (assoc result value key))
          {} music-collection)


#_(into {} (map (fn [album]
                  )))


(loop [albums    music-collection
       by-artist {}]
  (let [album (first albums)]
    (if (empty? albums)
      by-artist
      (recur (rest albums)
             (update by-artist
                     (keyword (:artist album))
                     conj (:album-name album))))))


;; => {:Mike Oldfield ("Tubular Bells"), :Nirvana ("Smells like teen spirit"), :Sisters of Mercy ("Vision Thing"), :New Model Army ("Thunder & Consolation" "Here comes the war")}


(group-by :artist music-collection)


;; => {"Mike Oldfield" [{:album-name "Tubular Bells", :artist "Mike Oldfield"}], "Nirvana" [{:album-name "Smells like teen spirit", :artist "Nirvana"}], "Sisters of Mercy" [{:album-name "Vision Thing", :artist "Sisters of Mercy"}], "New Model Army" [{:album-name "Here comes the war", :artist "New Model Army"} {:album-name "Thunder & Consolation", :artist "New Model Army"}]}


(group-by (fn [album] {:artist [:album-name]})
          music-collection)


(reduce-kv
  (fn [albums artist title]
    (if (empty? title)
      (assoc albums (:artist) [:title])
      (update albums artist conj title)))
  {}
  music-collection)


(defn albums-by-artist
  [album f]
  (reduce-kv
    (fn [m k v]
      (assoc m (:artist album) (f (:album-name album)))) {} album))


(map #(albums-by-artist % conj) music-collection)


(defn albums-by-artist
  [album f]
  (reduce
    (fn [m k v]
      (assoc m (:artist album) (f (:album-name album)))) {} album))


(group-by :artist music-collection)


;; => {"Mike Oldfield" [{:album-name "Tubular Bells", :artist "Mike Oldfield"}], "Nirvana" [{:album-name "Smells like teen spirit", :artist "Nirvana"}], "Sisters of Mercy" [{:album-name "Vision Thing", :artist "Sisters of Mercy"}], "New Model Army" [{:album-name "Here comes the war", :artist "New Model Army"} {:album-name "Thunder & Consolation", :artist "New Model Army"}]}



#_(into {}
        (map (fn [album]
               (:artist album) )))


;; Renaming keys from a JSON rest call
;;

;; REST API represents an account and returns JSON:

;; { "userName": "foo", "password": "bar", "emailId": "baz" }

;; Existing Clojure function creates an account:

;; (create-account {:username "foo" :password "bar" :email "baz"})

;; Transform the Clojure keywords to the form of the REST API keywords

;; A low abstraction approach would be:

(def args {:username "foo" :password "bar" :email "baz"})


(def clj->rest
  {:username :userName
   :email    :emailId})


(apply hash-map
       (flatten (map
                  (fn [[k v]] [(or (clj->rest k) k) v])
                  args)))  ; args is the arguments to create-account, as above


;; improved abstraction using into, more idiomatic

(into {} (map
           (fn [[k v]] [(or (clj->rest k) k) v])
           args))


;; or

(defn kmap
  [f m]
  (into {} (map #(update-in % [0] f) m)))


(def clj->rest
  {:username :userName
   :password :password
   :email    :emailId})


(kmap clj->rest args)


;; using clojure.set/rename-keys is even nicer

(clojure.set/rename-keys args clj->rest)


;; clojure.data.json/write-str is probably the most idiomatic approach.

;; Add clojure.data.json as a dependency

#_(clojure.data.json/write-str args)


(def operands {"+" + "/" /})

((operands "/") 4 3)


;; ## keys in maps

(def recipe-map {:ingredients "tofu"})

(contains? recipe-map :ingredients)


;; => true

(some #{"tofu"} recipe-map)


;; => nil

(vals recipe-map)


;; => ("tofu")


(some #{"tofu"} (vals recipe-map))


;; => "tofu"



;; Testing for values in a map
;;

;; Does a collection of maps contain a value?

(map #(= (:category %) "base") [{:category "base"}])

(map #(= (:category %) "base") [{:category "base"} {:category "refinement"}])

(map #(= (:category %) "base") [{:category "base"} {:category "refinement"} {:category "amendment"}])

(some #(= (:category %) "base") [{:category "base"} {:category "refinement"} {:category "amendment"}])

(some #(= (:category %) "base") [{:category "refinement"} {:category "amendment"}])

(some (comp #{"base"} :category) [{:category "base"} {:category "refinement"} {:category "amendment"}])

(some (comp #(= "base" %) :category) [{:category "base"} {:category "refinement"} {:category "amendment"}])

(some (comp #{"base"} :category) [{:category "refinement"} {:category "amendment"}])

(map #(= (:key %) "fish") [{:key "fish"} {:key "chips"} {:key "peas"}])

(and true (seq [1 2 3]) true)


;; Merging maps
;;

;; Try avoid using deep merging of maps and get the level of maps you need to merge


;;

(some->> [{:category "base"} {:category "refinement"} {:category "amendment"}]
         (filter #(= "base" (:category %))))


;; => ({:category "base"})

(filter #(= "base" (:category %)) [{:category "base"} {:category "refinement"} {:category "amendment"}])


;; => ({:category "base"})



[{:category "base" :payload {:team-id 1}}
 {:category "refinement" :payload {:body-part "right-foot"}}
 {:category "refinement" :payload {:height "ground"}}]


(reduce merge [{:category "base" :payload {:team-id 1}}
               {:category "refinement" :payload {:body-part "right-foot"}}
               {:category "refinement" :payload {:height "ground"}}])


(merge [{:category "base" :payload {:team-id 1}}
        {:payload {:body-part "right-foot"}}
        {:payload {:height "ground"}}])


;; => [{:category "base", :payload {:team-id 1}} {:payload {:body-part "right-foot"}} {:payload {:height "ground"}}]

(reduce merge [{:category "base" :payload {:team-id 1}}
               {:payload {:body-part "right-foot"}}
               {:payload {:height "ground"}}])


;; => {:category "base", :payload {:height "ground"}}


(apply merge [{:category "base" :payload {:team-id 1}}
              {:payload {:body-part "right-foot"}}
              {:payload {:height "ground"}}])


;; => {:category "base", :payload {:height "ground"}}


(map merge [{:category "base" :payload {:team-id 1}}
            {:payload {:body-part "right-foot"}}
            {:payload {:height "ground"}}])


;; => ({:category "base", :payload {:team-id 1}} {:payload {:body-part "right-foot"}} {:payload {:height "ground"}})


(merge-with merge [{:category "base" :payload {:team-id 1}}
                   {:payload {:body-part "right-foot"}}
                   {:payload {:height "ground"}}])


(into {:category "base" :payload {:team-id 1}}
      (map :payload []))


(update {:category "base" :payload {:team-id 1}} :payload merge  {:body-part "right-foot"})


;; => {:category "base", :payload {:team-id 1, :body-part "right-foot"}}


(update {:category "base" :payload {:team-id 1}} :payload merge  {:body-part "right-foot"} {:height "ground"})


;; => {:category "base", :payload {:team-id 1, :body-part "right-foot", :height "ground"}}



(update {:category "base" :payload {:team-id 1}} :payload merge  [{:body-part "right-foot"} {:height "ground"}])


;; => {:category "base", :payload {:team-id 1, {:body-part "right-foot"} {:height "ground"}}}


(update {:category "base" :payload {:team-id 1}} :payload #(apply merge % [{:body-part "right-foot"} {:height "ground"}]))


;; => {:category "base", :payload {:team-id 1, :body-part "right-foot", :height "ground"}}



;;
