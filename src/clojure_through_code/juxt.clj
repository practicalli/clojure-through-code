(ns clojure-through-code.juxt)


;; make a map out of some identifiers/values;
;; this that those -> {:this this :that that :those those}

(into {}
      (mapv (juxt keyword identity) ['this 'that 'those]))
