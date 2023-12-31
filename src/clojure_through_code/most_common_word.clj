(ns clojure-through-code.most-common-word
  (:require [clojure.string]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Most Common Words - Lisp Style

;; Book: The importance of being Earnest, Oscar Wilde
;; Source: Project Guttenburg (UTF-8 format)
;; Download from: http://www.gutenberg.org/cache/epub/844/pg844.txt
;; Local copy: resources/importance-of-being-earnest.txt

;; List of common works from the English language
;; Download http://www.textfixer.com/resources/common-english-words.txt
;; Local copy: resources/common-english-words.csv

;; Create an identity for the book

(def importance-of-being-earnest
  (slurp "resources/importance-of-being-earnest.txt"))

;; Create an identity for the common words, transforming into a collection of strings

(def common-english-words
  (set
   (clojure.string/split
    (slurp "resources/common-english-words.csv")
    #",")))


;; Process the book for the most common word

(defn most-common-words [book]
  (reverse
   (sort-by val
    (frequencies
     (remove common-english-words
      (map
        #(clojure.string/lower-case %)
        (re-seq #"[a-zA-Z0-9|']+" book)))))))

(most-common-words importance-of-being-earnest)


(comment
  ;; Experimenting with regular expressions

  (re-seq #"[\w|'-]+" "Morning-room in Algernon's flat in Half-Moon Street.")
  (re-seq #"\w+" "Morning-room in Algernon's flat in Half-Moon Street.")

)


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Most Common Words - Threading Macro Style


(def common-english-words
  (-> (slurp "resources/common-english-words.csv")
      (clojure.string/split #",")
      set))


(defn most-common-words [book]
  (->> book
       #_(re-seq #"[a-zA-Z0-9|']+")
       (re-seq #"[\w|'-]+")
       (map #(clojure.string/lower-case %))
       (remove common-english-words)
       frequencies
       (sort-by val)
       reverse))

;; Use importance-of-being-earnest book defined above
(most-common-words importance-of-being-earnest)



;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Deconstructing the code in the repl

;; To understand what each of the functions do in the most-common-words function you can comment out one or more expressions by placing the comment reader macro #_ in front of the expression

(defn most-common-words [book]
  (->> book
       #_(re-seq #"[a-zA-Z0-9|']+")
       #_(map #(clojure.string/lower-case %))
       #_(remove common-english-words)
       #_frequencies
       #_(sort-by val)
       #_reverse))

;; Now the most-common-words function will only return the result of evaluating book (the full text of the book). To see what each of the other lines do, simply remove the #_ character from the front of an expression and re-evaluate the most-common-words function in the repl




;; Accessing the book directly at Project Gutenburg
;; using Java Interop to ensure a clean connection that closes when finished

(with-open [in (java.util.zip.GZIPInputStream.
                (clojure.java.io/input-stream
                 "https://www.gutenberg.org/cache/epub/844/pg844.txt"))]
  (slurp in))





(defn decode
  [book-gzip]
  (with-open
   [uncompress-text (java.util.zip.GZIPInputStream.
                     (clojure.java.io/input-stream book-gzip))]
    (slurp uncompress-text)))

(defn common-words
  [csv]
  (-> (slurp csv)
      (clojure.string/split #",")
      set))

(defn most-common-word
  [book-gzip common-words]
  (->> (decode book-gzip)
       (re-seq #"[\w|'-]+")
       (map #(clojure.string/lower-case %))
       (remove common-words)
       frequencies
       (sort-by val >)))

(defn -main
  [book-gzip common-word-csv]
  (most-common-word book-gzip (common-words common-word-csv)))

(comment

  (-main "https://www.gutenberg.org/cache/epub/844/pg844.txt" "common-english-words.txt")

#_())


(def fish)

(defn blah
  (println "I am a bad function definition")
)

