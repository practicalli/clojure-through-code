;; Example of threading macros and
;; the use of LightTable to give fast feedback
;; as you are developing code
;; Idea from Misophistful: Understanding thread macros in clojure
;; https://www.youtube.com/watch?v=qxE5wDbt964

(str "this is a Lighttable instarepl")

;; Lets play with some data

(def book (slurp "http://clearwhitelight.org/hitch/hhgttg.txt"))

(def common-words
  (-> (slurp "http://www.textfixer.com/resources/common-english-words.txt")
      (clojure.string/split #",")
      set))

(->> book
     (re-seq #"[a-zA-Z0-9|']+")
     (map #(clojure.string/lower-case %))
     (remove common-words)
     frequencies
     (sort-by val)
     reverse)
