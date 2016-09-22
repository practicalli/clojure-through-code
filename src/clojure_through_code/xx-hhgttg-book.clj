;; Example of threading macros and the use of a connected REPL to give fast feedback
;; as you are developing code.
;; Original concept from Misophistful: Understanding thread macros in clojure
;; https://www.youtube.com/watch?v=qxE5wDbt964

;; (str "this is live evaluation via a connected repl")

;; Lets get the text of a book from a website using the slurp function

(def book (slurp "./hhgttg.txt"))

(def common-english-words
  (-> (slurp "common-english-words.txt")
      (clojure.string/split #",")
      set))

(->> book
     (re-seq #"[a-zA-Z0-9|']+")
     (map #(clojure.string/lower-case %))
     (remove common-english-words)
     frequencies
     (sort-by val)
     reverse)


;; C-c C-p to show output in a seperate buffer (spacemacs)


;; Algorithm
;; * Use regular expression to create a collection of individual words
;; * Convert all the words to lower case (so they match with common words source)
;; * Remove the common English words used in the book, leaving more context specific words
;; * Calculate the requencies of the remaining words, returning a map of word & word count pairs
;; * Sort the map by word count values
;; * Reverse the collection so the most commonly used word is the first element in the map



;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Offline sources of book and common English words

;; (def book (slurp "http://clearwhitelight.org/hitch/hhgttg.txt"))

;; (def common-english-words
;;   (-> (slurp "http://www.textfixer.com/resources/common-english-words.txt")
;;       (clojure.string/split #",")
;;       set))
