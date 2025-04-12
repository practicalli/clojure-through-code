(ns clojure-through-code.xx-reactive-pattern-matching)


(def data
  {:firstName   "John",
   :lastName    "Smith",
   :address {:streetAddress    "21 2nd Stree",
             :state            "NY",
             :postalCode        10021}
   :phoneNumbers [{:type "home" :number "212 555-1234"}
                  {:type "fax" :number "646 555-4567"}]})


;; Utility Functions
(defn pretty_str
  [s]
  (str "\"" s "\""))


(defn comma_interposed_str
  [l]
  (apply str (interpose ", " l)))


(defn folded_str
  [[s e] l]
  (str s (comma_interposed_str l) e))


(def folded_list_str (partial folded_str "[]"))

(def folded_map_str (partial folded_str "{}"))


;; Show implementation in clojure as multi methods
(defmulti pretty_json type)


(defmethod pretty_json clojure.lang.PersistentHashMap
  [m]
  (folded_map_str
    (map (fn [[k v]]
           (str (pretty_str (name k)) ":" (pretty_json v)))
         (seq m))))


(defmethod pretty_json clojure.lang.PersistentVector
  [s]
  (folded_list_str (map pretty_json s)))


(defmethod pretty_json String [e] (pretty_str e))

(defmethod pretty_json nil [n] "null")

(defmethod pretty_json :default [s] (str s))
