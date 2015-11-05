(ns clojure-through-code.xx-emacs-fun
  (:require [clojure.string :as clj-string]))


(declare function)
(declare tab-through-sections)
(declare blah)

;; yasnippets

(defn expanded-snippet
  "This function was created by typing defn and using the binding M-x yas-expand.  I wonder if there is a good shortcut for this, such as using tab"
  [tab-through-sections]
  (str "I can tab through each of the sections of the snippet to complete it, yay!"))

(def five 5)

(Integer. "80")

(let [x  (+ 2 2)])


;; Snippets for clojure-mode
;; bench  defm  deft   for  import  map	       ns    print    test  when
;; bp     defn  doseq  if	 is	     map.lambda  opts  reduce   try   whenl
;; def    defr  fn     ifl  let	   mdoc	       pr    require  use



;; require
;; In an ns function, create a new line and type require, then call `yas-expand'


(function  (+ 2 2))

(function  ()() blah)

(+ 1 2 3)
(+ 1 2)
1

(defn meta-forward-slash
  "Code faster with yasnippets, pressing meta forward-slash to expand the snippet"
  [tab-through-sections for-fun-and-profit]
  )


;FIXME: surprisingly some of my code needs fixing

