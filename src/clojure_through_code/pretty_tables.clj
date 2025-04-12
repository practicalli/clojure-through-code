(ns clojure-through-code.pretty-tables)


;; Displaying resuts in ascii tables
;; https://github.com/cldwalker/table
;; In SpacEmacs the tables appear in the REPL window
;; The table function returns nil, just like println function

;; Clojure since 1.5 comes with clojure.pprint/print-table

;; (:require [table.core :refer [table]])

(require '[table.core :refer [table]])

(table [["1" "2"] ["3" "4"]])

(table '((1 2) (3 4)))
(table #{[1 2] [3 4]})

(table [{:a 11} {:a 3 :b 22}])


;; unicode
(table [[1 2] [3 4]] :style :unicode)


;; org-mode format
(table [[1 2] [3 4]] :style :org)


;; Generate tables for github's markdown
(table [[10 20] [3 4]] :style :github-markdown)


;; custom styles - cool
(table [[10 20] [3 4]] :style {:top ["◤ " " ▼ " " ◥"]
                               :top-dash "✈︎"
                               :middle ["▶︎ " "   " " ◀︎"]
                               :dash "✂︎"
                               :bottom ["◣ " " ▲ " " ◢"]
                               :bottom-dash "☺︎"
                               :header-walls ["  " "   " "  "]
                               :body-walls ["  " "   " "  "]})


;; Table can handle plain maps and vectors of course:

(require '[clojure.repl :refer :all])
(table (meta #'doc))


;; looking at the Java Class loader
(table (seq (.getURLs (java.lang.ClassLoader/getSystemClassLoader))))


;;
;; clojure.pprint/print-table
;; Prints a table from the data held in a collection (vector) of maps [{"key" "value"}]
;; When keys are provided as the first argument (also in a vector) they are used to control wich headers and colums of values are printed (and in what order)
(doc clojure.pprint/print-table)
(source clojure.pprint/print-table)


(clojure.pprint/print-table [{"name" "John"}])


;; You can specify the keys to use from the map collections
(clojure.pprint/print-table ["name" "address" "postcode"] [{"name" "john" "address" "Bromley" "postcode" "90210"}])


;; If you ommit a key, its header and values are not included in the output
(clojure.pprint/print-table ["name" "postcode"] [{"name" "john" "address" "Bromley" "postcode" "90210"}])


;; if you include a key that does not exist, a header is create for that key, but no values are included (as they do not exist)
(clojure.pprint/print-table ["name" "age"] [{"name" "john" "address" "Bromley" "postcode" "90210"}])


;; If you have a collection of maps whith a whole range of data about contacts, then its easy to print out just the information you need, in the order you want to see it.

(def my-customers
  [{:name "Sarah Smith" :location "London" :company "SalesForTheWin"}
   {:name "James Smith" :location "London" :company "SalesForTheWin"}
   {:name "Olive Oills" :location "Bristol" :company "Spinach4You"}
   {:name "Betty Boops" :location "Bristol" :company "Poodle Groom"}])


(clojure.pprint/print-table [:name :company] my-customers)


;; |       :name |       :company |
;; |-------------+----------------|
;; | Sarah Smith | SalesForTheWin |
;; | James Smith | SalesForTheWin |
;; | Olive Oills |    Spinach4You |
;; | Betty Boops |   Poodle Groom |


;; more examples at https://clojuredocs.org/clojure.pprint/print-table


;; You can control the headings by specifying the keys in a vector as the first argument to print-table.  The keys must be the same as those in the maps.
(clojure.pprint/print-table [:name :address :postcode]
                            [{:name "john" :address "Bromley" :postcode "90210"}
                             {:name "fred" :address "london" :postcode "12345"}])


;; chaning the order of the keys changes the order of the headings
(clojure.pprint/print-table [:name :postcode :address]
                            [{:name "john" :address "Bromley" :postcode "90210"}
                             {:name "fred" :address "london" :postcode "12345"}])


;; if you include an incorrect key, that key will be used to create the columm, and contain no informatoin as there are no valus associated with the key in the maps.
(clojure.pprint/print-table [:name :address :postcode]
                            [{:name "john" :adress "Bromley" :postcode "90210"}
                             {:name "fred" :adress "london" :postcode "12345"}])


(clojure.pprint/print-table [{"name" "John" "address" "bromley" "postcode" 123}
                             {"name" "Fred" "address" "london" "postcode" 321}])


;; If you use a string as the keys argument, it will treat the string as a vector of characters and create a heading for each.  However, as none of those keys match the keys in the map, no values are printed in the rows.
(clojure.pprint/print-table "position" [{"1" "2"} {"3" "4"}])


;; | p | o | s | i | t | i | o | n |
;; |---+---+---+---+---+---+---+---|
;; |   |   |   |   |   |   |   |   |
;; |   |   |   |   |   |   |   |   |



;; Kanban coaching eachange

;; should a scrum master in a large org be like quantum leap... except he help other people solve their own problems

;; this is not a blame culture, but that was your fault
;; this is not a blame culture, so we will be watching you...
;; this is not a blame culture, so if things go wrong its your fault


;; amy kuddy - fact it til you make it - TED talk

;; IQ vs EQ - just being intelligent is not enough, to be successful we need to EQ and IQ....
