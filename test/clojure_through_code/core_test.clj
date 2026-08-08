(ns clojure-through-code.core-test
  (:require
   [clojure-through-code.core :as code]
   [clojure.test :refer [deftest is testing]]))

(deftest adder-test
  (testing "Using a range of numbers to test the adder"
    #_(is (= 0 1))
    (is (= (+ 1 2) (code/adder 1 2)) "Adding 1 and 2")
    (is (= (+ 1 -2) (code/adder 1 -2)) "Adding 1 and -2")
    #_(is (not (= (+ 1 2)) (adder "a" "b")) "Adding strings as negative test")
    (is (false? (= 0 1)) "A simple failing test")
    (is (false? (= 0 (code/adder 3 4))) "Purposefully using failing data")))
