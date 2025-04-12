(ns clojure-through-code.xx-emacs-fun
  (:require
    [clojure.string :as clj-string]))


;; yasnippets

(defn expanded-snippet
  "This function was created by typing defn and using the binding M-x yas-expand.
  Adding snippets to the autocomplete configuration removes the need to call yas-expand"
  [tab-through-sections]
  (str "I can tab through each of the sections of the snippet to complete it, yay!"))


#_(defn meta-forward-slash
    "Code faster with yasnippets, pressing meta forward-slash to expand the snippet"
    [tab-through-sections for-fun-and-profit]
    )


;; Snippets for clojure-mode
;; bench  defm  deft   for  import  map        ns    print    test  when
;; bp     defn  doseq  if  is      map.lambda  opts  reduce   try   whenl
;; def    defr  fn     ifl  let    mdoc        pr    require  use



;; FIXME: surprisingly some of my code needs fixing

(comment
  ;; Reload the current namespace and print loaded namespaces in REPL buffer
  (require '[clojure-through-code.xx-emacs-fun] :reload :verbose)

  #_()) ; End of rich comment
