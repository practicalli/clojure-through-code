(ns clojure-through-code.error-handling
  (:require
   [clojure.string :as string]))

;; Error handling when using filter?
;; e.g 2 vectors and filter one based on elements of the other

(def tmsall [{:givenName nil :surname nil :email "admin@system.com"}
             {:givenName "Alice" :surname "Spring" :email "alice@al.al"}
             {:givenName "Bob" :surname "Summer" :email "bob@b.b"}
             {:givenName "Carol" :surname "Autumn" :email "carol@c.c"}
             {:givenName nil :surname nil :email "cs@cs.cs"}
             {:givenName "Danny" :surname "Winter" :email "danny@d.d"}])

(def slack ["Bob Best Summer" "Alice Spring"])

(try
  (filter
   (fn [m]
     (some
      (fn [s]
        (and (string/includes? s (:givenName m))
             (string/includes? s (:surname m)))) slack))
   tmsall)
  (catch Exception e
    (str "exception: " (.getMessage e))))

;; clojure.string/include? throws java.lang.NullPointerException 
;; filter returns a lazy seq so it won't throw the exception until that is later realized
;; move the try/catch into the fn (or better, test for nil)

;; Or add another filter to just one with both a :givenName and a :surname

