;; ---------------------------------------------------------
;; Common specifications across the project
;;
;; Brief description
;; ---------------------------------------------------------

;; ---------------------------------------
;; Namespace definition and requires

(ns clojure-through-code.spec
  "Specifications for data models and functions.
   Specs are be used to generate random data for unit tests"
  (:require
    [clojure.spec.alpha :as spec]))


;; ---------------------------------------

;; ---------------------------------------
;; Organisation specifications

(spec/def :org/legal-name      string?)
(spec/def :org/primary-contact string?)
(spec/def :org/joining-date    inst?)


;; ---------------------------------------

;; ---------------------------------------
;; Validate specifications

(comment
  (spec/valid? :org/legal-name "Johnny Practicalli")
  (spec/valid? :org/primary-contact "Johnny Practicalli")
  (spec/valid? :org/joining-date (java.util.Date.)))


;; ---------------------------------------
