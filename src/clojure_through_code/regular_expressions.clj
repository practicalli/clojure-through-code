(ns clojure-through-code.regular-expressions)


;;  Reference: https://gist.github.com/clojens/7932406

(def ptrn
  {:a {:pattern #"a(?!b)"
       :purpose "Only allow a if it is not preceded by a 'b' (negative lookahead)"
       :samples ["acdefg" ; ok
                 "abcdef"]} ; nil

   :b {:pattern #"(?i)(<title.*?>)(.+?)(</title>)"
       :purpose "Grouping and backreference to get the text from a HTML <title> tag."
       :samples ["titles are just dumb" ; nil
                 "<title>1337</title>"]}  ; ok

   :c {:pattern #"(\w)(\s+)([\.,])"
       :purpose "Remove whitespace between word and . or ,"
       :samples ["word ." ; ok
                 "to ,"   ; ok
                 "test."]}  ; nil

   :d {:pattern #"[a-zA-Z]{3}"
       :purpose "Get exactly 3 characters from A to Z in both upper- and lower-case. Not less."
       :samples ["FU"   ; nil
                 "John" ; Joh
                 "Joe"]}  ; Joe

   :e {:pattern #"[tT]rue|[yY]es"
       :purpose "returns true if the string matches exactly 'true' or 'True' or 'yes' or 'Yes"
       :samples [""      ; ?
                 ""      ; ?
                 ""]}      ; ?

   :f {:pattern #"^[^\d].*"
       :purpose "Returns true if the string does not have a number at the beginning"
       :samples ["1 beer please"          ; nil
                 "More beer for this 1!!" ; ok
                 "One beer two beer"]}      ; ok

   :g {:pattern #"[^0-9]*[12]?[0-9]{1,2}[^0-9]*"
       :purpose "Returns true if the string contains a number less then 300"
       :samples ["123456789101112134"      ; ok
                 "Someone rented 300"      ; ?
                 "99 bottles of beer"]}      ; ?

   :h {:pattern #"([\w&&[^b]])*"
       :purpose "Returns true if the string contains a arbitrary number of characters except b"
       :samples ["acdefghijklmnopqrstuvwxyz"      ; ok
                 "ABBA was a mid 70's band"       ; nil
                 "Berenbotje kon niet varen"]}      ; nil

   ;;

   ;;
   :X {:pattern #""
       :purpose ""
       :samples [""      ; ?
                 ""      ; ?
                 ""]}})      ; ?

(defn step
  [k]
  (map #(re-matches (get-in ptrn [k :pattern]) %) (get-in ptrn [k :samples])))


;; Java built-in regex support in string objects

;; A little threading macro to show how string instances have native
;; methods to deal with regular expressions.
(-> "foo bar" String. (.matches "fo.*"))

(step :h)


;; Filtering strings
;;

;; Starting with the  following

(ns disemvowel-trolls)

(def vowels #{\a \e \i \o \u \A \E \I \O \U})


(defn disemvowel
  [string]
  (apply str (filter (complement vowels) (seq string))))


;; Refactor

(def vowels
  (let [vowels "aeiou"]
    (into (set vowels) (clojure.string/upper-case vowels))))


(defn disemvowel
  [string]
  (clojure.string/join (remove vowels string)))


(disemvowel "Hello, World!")
"Hll, Wrld!"


;; Regular expressions and case insensitivity
;; Use the regex case-insensitivity flag with clojure.string/replace function
;; Replace all characters in the pattern `[aeiou]` regardless of case `(?i)` with an empty strings

(defn disemvowel-replace-case-insensitive
  [string]
  (clojure.string/replace string #"(?i)[aeiou]" ""))
