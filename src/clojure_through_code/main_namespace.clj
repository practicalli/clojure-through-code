(ns clojure-through-code.main-namespace
  (:require
    [clojure.string :as string]))


(defn -main
  "Entry point into the application"
  [& args]
  (if (seq? args)
    (println "The application is running with these arguments:"
             (string/join " " args))
    (println "The application is running...")))
