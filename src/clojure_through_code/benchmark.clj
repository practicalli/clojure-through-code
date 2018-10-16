(ns clojure-through-code.benchmark
  (:require [criterium.core :refer [quick-bench]]))


;; cond
(let [number 5]
  (quick-bench (cond = 5 1 1 2 2 3 3 4 4 5 5)))

;; Evaluation count : 50788488 in 6 samples of 8464748 calls.
;; Execution time mean : 2.535916 ns
;; Execution time std-deviation : 0.096838 ns
;; Execution time lower quantile : 2.435814 ns ( 2.5%)
;; Execution time upper quantile : 2.686146 ns (97.5%)
;; Overhead used : 9.431514 ns

;; Found 1 outliers in 6 samples (16.6667 %)
;; low-severe  1 (16.6667 %)
;; Variance from outliers : 13.8889 % Variance is moderately inflated by outliers


(let [number 5]
  (quick-bench (condp = 5 1 1 2 2 3 3 4 4 5 5)))

;; Evaluation count : 3625284 in 6 samples of 604214 calls.
;; Execution time mean : 156.813816 ns
;; Execution time std-deviation : 2.560629 ns
;; Execution time lower quantile : 154.222522 ns ( 2.5%)
;; Execution time upper quantile : 160.425030 ns (97.5%)
;; Overhead used : 9.431514 ns

;; Found 1 outliers in 6 samples (16.6667 %)
;; low-severe  1 (16.6667 %)
;; Variance from outliers : 13.8889 % Variance is moderately inflated by outliers



(let [number 5]
  (quick-bench (case 5 1 1 2 2 3 3 4 4 5 5)))

;; Evaluation count : 56533626 in 6 samples of 9422271 calls.
;; Execution time mean : 1.158650 ns
;; Execution time std-deviation : 0.187322 ns
;; Execution time lower quantile : 1.021431 ns ( 2.5%)
;; Execution time upper quantile : 1.471115 ns (97.5%)
;; Overhead used : 9.431514 ns

;; Found 1 outliers in 6 samples (16.6667 %)
;; low-severe   1 (16.6667 %)
;; Variance from outliers : 47.5092 % Variance is moderately inflated by outliers
