(ns clojure-through-code.spec-definitions)


#_(defspec score-cannot-decrease 100
    (prop/for-all
      [ops gen-score-ops]
      (let [counter (sut/new-counter)]
        (loop [ops ops]
          (if (empty? ops)
            true
            (let [prev-val (sut/value counter)]
              (try (apply-score-op counter (first ops))
                   (catch Throwable _))
              (if (< (sut/value counter) prev-val)
                false
                (recur (rest ops)))))))))
