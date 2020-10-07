;; keywords

(ns clojure-through-code.keywords)


;; keywords and strings
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; `keyword` will convert a string to a keyword and add the colon at the start of  the name

;; The keyword function can convert a string to a keyword
;; adding the : to the start of the string to make the keyword

(keyword "keyword-ify-me")
;; => :keyword-ify-me

;; What if the string has spaces

(keyword "i should be a single keyword")
;; => :i should be a single keyword

;; first replace the spaces with a dash and then convert

(clojure.string/replace "i should be a single keyword" " " "-")
;; => "i-should-be-a-single-keyword"

;; and then convet to a keyword

(keyword
  (clojure.string/replace "i should be a single keyword" " " "-"))
;; => :i-should-be-a-single-keyword


;; What if keyword is inside a string

(keyword ":trapped-in-a-string")
;; => ::trapped-in-a-string

;; This could be okay if an auto-resolved keyword were suitable,
;; the resulting keyword would be qualified by the name of the current namespace

;; Use `clojure.edn/read-string` to extract a  keyword that is wrapped in a string

(require '[clojure.edn])

(clojure.edn/read-string ":get-me-out-of-this-string")
