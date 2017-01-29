(ns clojure-through-code.most-common-word)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Most Common Words - Lisp Style

;; Book: The importance of being Earnest, Oscar Wilde
;; Source: Project Guttenburg (UTF-8 format)
(def importance-of-being-earnest
  (slurp "http://www.gutenberg.org/cache/epub/844/pg844.txt"))

(def common-english-words
  (set
   (clojure.string/split
    (slurp
     "http://www.textfixer.com/resources/common-english-words.txt")
    #",")))

(defn most-common-words [book]
  (reverse
   (sort-by val
    (frequencies
     (remove common-english-words
      (map
        #(clojure.string/lower-case %)
        (re-seq #"[a-zA-Z0-9|']+" book)))))))

(most-common-words importance-of-being-earnest)


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Most Common Words - Threading Macro Style

;; Book: The importance of being Earnest, Oscar Wilde
;; Source: Project Guttenburg (UTF-8 format)
(def importance-of-being-earnest
  (slurp "http://www.gutenberg.org/cache/epub/844/pg844.txt"))


(def common-english-words
  (-> (slurp "http://www.textfixer.com/resources/common-english-words.txt")
      (clojure.string/split #",")
      set))


(defn most-common-words [book]
  (->> book
       (re-seq #"[a-zA-Z0-9|']+")
       (map #(clojure.string/lower-case %))
       (remove common-english-words)
       frequencies
       (sort-by val)
       reverse))

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
