(ns clojure-through-code.parse-cli-options)


;; TODO: review the common approach to parsing Clojure CLI command line options

(defn parse-cli-tool-options
  "Return the merged default options with any provided as a hash-map argument"
  [arguments]
  (merge {:replace false :report true :paths ["."]}
         arguments))


(parse-cli-tool-options {:paths ["src" "test"] :report false})


;; => {:replace false, :report false, :paths ["src" "test"]}


(parse-cli-tool-options {})


;; => {:replace false, :report true, :paths ["."]}
