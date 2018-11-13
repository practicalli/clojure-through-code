(ns clojure-through-code.four-clojure)

;; #18 - Sequences: filter
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Difficulty:	Elementary

;; Description
;; The filter function takes two arguments: a predicate function (f) and a sequence (s). Filter returns a new sequence consisting of all the items of s for which (f item) returns true.

;; Tests
(=  (filter #(> % 5) '(3 4 5 6 7)))


;; Analysis of the problem
;; filter will return a collection of all the elements that match the predicate (function).
;; filter is another function that iterates over a collection and returns a collection of results.  In this sense filter is very much like the map function.

;; Clojuredocs.org: clojure.core/filter
;; Available since 1.0 (source)
;; (filter pred)
;; (filter pred coll)
;; Returns a lazy sequence of the items in coll for which (pred item) returns logical true. pred must be free of side-effects.
;; Returns a transducer when no collection is provided.


;; The simplest way to see what filter is doing is to run that part of the expression in the REPL.
(filter #(> % 5) '(3 4 5 6 7))
;; => (6 7)

;; So filter returns the values that return true when passed to the `#(> % 5)` function.  This function is called the predicate.

;; If we used the same predicate function with map instead of filter, we would get part of the answer
(map #(> % 5) '(3 4 5 6 7))
;; => (false false false true true)

;; In the source code of filter, we can see that each value of the collection is taken and the predicate function applied.
;; If the predicate function returns true, then the value is constructed into a list along with any other matching results
;; Additional matching results are evaluated in a loop via a function call to the filter function, using the rest of the values.

;; (let [f (first s) r (rest s)]
;;   (if (pred f)
;;     (cons f (filter pred r))
;;     (filter pred r)))


;; The remove function does the inverse (complement) of filter and returns the values that return false from the predicate function.
;; It is essentailly a wrapper around the filter function, using the complement function to invert the result of the predicate function results.
;; So if the predicate function returned true, then complement would change that to false (and false would be changed to true).

;; (defn remove
;;   ([pred] (filter (complement pred)))
;;   ([pred coll]
;;    (filter (complement pred) coll)))


;; Answer
[6 7] ;; Code Golf Score: 4

;; The following would also be correct answers, although would give bigger golf scores

'(6 7) ;; As we are treating the list as just data, then we need to quote the list to avoid the REPL calling a function called 6 which does not exist.

(quote (6 7))  ;; is simply the long form of the above answer.

;; NOTE: In Clojure it is idiomatic to use Vectors rather than lists for data representation






;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; #26 - Fibonacci Sequence

;; http://www.4clojure.com/problem/26#prob-title
;; Difficulty:	Easy
;; Topics:	Fibonacci seqs

;; Write a function which returns the first X fibonacci numbers.

;; Tests
#_(= (__ 3) '(1 1 2))
#_(= (__ 6) '(1 1 2 3 5 8))
#_(= (__ 8) '(1 1 2 3 5 8 13 21))

;; First though is that we want to generate a range of numbers

(range 1 6)
;; => (1 2 3 4 5)

;; Looking at the range source code does not help that much as range is just a wrapper for clojure.lang.Range which is a Java class.


;; Analyse the problem
;; Looking at the tests we see that all fibonacci sequences start with `1 1`.
;; To get the third value in the sequence we add the first numbers together.  This is specific to the third value in the sequence.
;; To get each new value in the sequence we add the last to values in the sequence together

;; The problem asks us to write a function, so lets start there.

;; The function takes the size of fibonacci sequence to generate as an argument
;; and creates a local name to represent the initial fibonacci sequence.
;; Test to see if the fibonacci sequence is big enough and iterate if its too small.
(fn [size]
  (let [initial-sequence [1 1]]
    (if (< (count initial-sequence) size)
      "iterate"
      initial-sequence)))

;; Now we just need to iterate until we get a big enough fibonacci sequence
;; A simple way to iterate is to use loop recur, as we only only pass the size of fibonacci to generate as an argument to the function we are writing.
;; In each iteration we conjoin the result of adding all the values in the fib sequence together.
(fn fibs [size]
  (loop [fib [1 1]]
    (if (< (count fib) size)
      (recur (conj fib (reduce + fib)))
      fib)))

;; Test our function, generating a fibonacci sequence of size 3
((fn fibs [size]
   (loop [fib [1 1]]
     (if (< (count fib) size)
       (recur (conj fib (reduce + fib)))
       fib)))
 3)
;; => [1 1 2]

;; Test our function, generating a fibonacci sequence of size 6
((fn fibs [size]
   (loop [fib [1 1]]
     (if (< (count fib) size)
       (recur (conj fib (reduce + fib)))
       fib)))
 6)
;; => [1 1 2 4 8 16]
;; Although this generate a sequence of the right size, it is not the fibonacci sequence.
;; We need to add the last two values in the current sequence for each iteration

;; Change our recur to take the last and second to last values from the current value of fib.
;; As there is no second-last function, we reverse the sequence and take the second value.
(fn fibs [size]
  (loop [fib [1 1]]
    (if (< (count fib) size)
      (recur (conj fib (+ (second (reverse fib)) (last fib))))
      fib)))


((fn fibs [size]
   (loop [fib [1 1]]
     (if (< (count fib) size)
       (recur (conj fib (+ (second (reverse fib)) (last fib))))
       fib)))
 8)
;; => [1 1 2 3 5 8 13 21]

;; Code Golf Score: 101


;; NOTE: use a fibonacci sequence to draw a spiral using SVG for the clojurebridge exercises


;; Other solutions of note
;; There was one cheeky answer that caught my eye.  It does meet the current requirements of this problem very simply and is a way to codify your understanding of what result needs to be generated.

;; _pcl's solution:
;; TDD principle: Write the minimum amount of code required to make the test pass :)
(fn [i] (take i '(1 1 2 3 5 8 13 21)))

;; Obviously as soon as the requirements change to be a larger number than 8 for the fibonacci sequence, then this code is going to fail.


;; Write a function which behaves like reduce, but returns each intermediate value of the reduction.
;; Your function must accept either two or three arguments, and the return sequence must be lazy.

;; Special Restrictions: recursions

;; Notes from:
;; http://www.thesoftwaresimpleton.com/blog/2014/09/08/lazy-seq/

;; (= (take 5 (__ + (range))) [0 1 3 6 10])

;; Range by itself generates and infinate number of integer values, blowing up your heap eventually
;;(range)

;; we can specify the range to generated
(range 10)

;; or we can use the lazy sequence nature of range to get just the values we want

(take 10 (range))
;; => (0 1 2 3 4 5 6 7 8 9)

(class (take 10 (range)))
;; => clojure.lang.LazySeq


;; Solving the problem

;; (= (take 5 (map + (range))) [0 1 3 6 10])

;;(take 5 (map + (range)))

(= (take 5 (
            (fn my-reduct
              ([func coll]
               (my-reduct func (first coll) (rest coll)))

              ([func firstArg coll]
               (letfn [(reduct [f init se]
                         (lazy-seq (when-not (empty? se)
                                     (let [res (f init (first se))]
                                       (cons res (reduct f res (rest se)))))))]
                 (lazy-seq (cons firstArg (reduct func firstArg coll))))))
            + (range))) [0 1 3 6 10])

;; 4Clojure entered solution

(fn my-reduct
  ([func coll]
   (my-reduct func (first coll) (rest coll)))

  ([func firstArg coll]
   (letfn [(reduct [f init se]
             (lazy-seq (when-not (empty? se)
                         (let [res (f init (first se))]
                           (cons res (reduct f res (rest se)))))))]
     (lazy-seq (cons firstArg (reduct func firstArg coll))))))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; problem 68: recur


(loop [x 5
       result []]
  (if (> x 0)
    (recur (dec x) (conj result (+ 2 x)))
    result))

;; => [7 6 5 4 3]

;; The value of x is bound to 5 and result is bound to an empty vector.
;; If x is greater than zero, then call the loop again, passing two values
;; that replace the initial loop bindings.
;; The conj expression is evaluated before the loop is called again,
;; so the first value added to result is 7
;; When the value of x has be decremented to zero, the current value of
;; result is returned instead of calling the loop again.

;; 4Clojure entered solution
[7 6 5 4 3]


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; problem 73 - analyse tic-tac-toe board

;; A tic-tac-toe board is represented by a two dimensional vector. X is represented by :x, O is represented by :o, and empty is represented by :e.
;; A player wins by placing three Xs or three Os in a horizontal, vertical, or diagonal row.
;; Write a function which analyzes a tic-tac-toe board and returns :x if X has won, :o if O has won, and nil if neither player has won.


;; Define some tic-tac-toe board pattern to test

(def board-empty [[:e :e :e]
                  [:e :e :e]
                  [:e :e :e]])

(def board-x-first-row [[:x :x :x]
                        [:e :e :e]
                        [:e :e :e]])

(def board-o-diagonal [[:o :e :e]
                       [:e :o :e]
                       [:e :e :o]])

;; Algorithm
;; 1) Extract all the values from the board  (destructuring with let)
;; 2) Pattern match the values to winning combinations (=)
;; 3) If any combination matched with :x or :o then return winner (or)


;; Destructuring will bind a local name to a value from the board pattern.
;; In this case, a b and c will each represent a line of the board

(let [[a b c] board-empty]
  [a b c])
;; => [[:e :e :e] [:e :e :e] [:e :e :e]]

;; Now a b and c will represent the elements of the first row of the board pattern.

(let [[[a b c]] board-x-first-row]
  [a b c])
;; => [:o :e :e]


;; So we can pull out the whole board using the following let expression

(let [[[a b c]
       [d e f]
       [g h i]] board-o-diagonal]
  [[a b c] [d e f] [g h i]])
;; => [[:o :e :e] [:e :o :e] [:e :e :o]]


;; let can be used with functions as well as values
;; we can call the function using the local name winner,
;; passing a board and a player character to see if they have won.
;; We just use a single winning pattern here for simplicity.
(let [winner (fn [[[a b c]
                   [d e f]
                   [g h i]]
                  player]
               (= player a b c))]
  (winner board-x-first-row :x))
;; => true


;; We can streamline the let binding and anonymous function definition a little
;; letfn creates a function within the scope of the let expression.
;; letfn takes the name of the function to create followed by its arguments,
;; in this case the board patter we are destructuring and the player character.
;; For example:

(letfn [(winner [[[a b c]
                  [d e f]
                  [g h i]]
                 player])])

;; Using macro-expansion we can see the letfn creates a function called winner
;; with two arguments: a board pattern and player
(letfn* [winner (fn winner [[[a b c] [d e f] [g h i]] player])])

;; We can call the local winner function whilst inside the letfn expression,
;; however we have not yet added any behaviour to that function.


;; Adding a check to see if a player has one the first row of the board as a first
;; attempt at defining the function behaviour.
;; If the first row matches the player character then we should get true.

(letfn [(winner [[[a b c]
                  [d e f]
                  [g h i]]
                 player]
          (println (str "first row: " [a b c])) ;; debugging
          (= player a b c))]
  (winner board-x-first-row :x))
;; => true

;; Note: A simple println statement was added to show the intermediate values,
;; as an example of a simple debugging statement

;; Now we have the basic logic, we can add the patterns for all the winning combinations
;; We only need one pattern to match, so we wrap the patterns in an or function
;; To test both players, we add a condition around calling the winner function for each player


(letfn [(winner [[[a b c]
                  [d e f]
                  [g h i]]
                 player]
          (or (= player a b c)
              (= player d e f)
              (= player g h i)
              (= player a d g)
              (= player b e h)
              (= player c f i)
              (= player a e i)
              (= player c e g)))]
  (cond (winner board-x-first-row :x) :x
        (winner board-x-first-row :o) :o
        :else (str "No Winner for this board: " board-x-first-row)))
;; => :x

;; The letfn expression was tested with each of the three board patterns defined, giving the correct results.

;; 4Clojure entered solution

(fn [board]
  (letfn [(winner [[[a b c]
                    [d e f]
                    [g h i]]
                   player]
            (or (= player a b c)
                (= player d e f)
                (= player g h i)
                (= player a d g)
                (= player b e h)
                (= player c f i)
                (= player a e i)
                (= player c e g)))]
    (cond (winner board :x) :x
          (winner board :o) :o
          :else nil)))


;; Alternative solutions

;; I quite like the solution by tclamb, its similar to the submitted solution in approach

(fn winner [[[a b c :as top]
             [d e f :as middle]
             [g h i :as bottom]]]
  (let [left     [a d g]
        center   [b e h]
        right    [c f i]
        forward  [a e i]
        backward [c e g]]
    (when-let [winner (some #(when (or (every? #{:x} %) (every? #{:o} %)) %)
                            [top middle bottom
                             left center right
                             forward backward])]
      (first winner))))



;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; problem 83: a half truth

;; Write a function which takes a variable number of booleans.
;; Your function should return true if some of the parameters are true, but not all of the parameters are true. Otherwise your function should return false.

;; a full way of describing the algorithm and thinking behind it
(fn [& args]
    (let [not-all-true (not (every? true? args))
          any-true (first (filter true? args))]
      (= not-all-true any-true)))

(fn [& args]
  (= (not (every? true? args))
     (first (filter true? args))))

;; To get a better golf score (fewer characters) an anonymous function can be used
;; The %& is a placeholder for all the parameters
(#(=
   (not (every? true? %&))
   (first (filter true? %&))) true)


  ;; From the first example, we can see that comparing true and false values
;; with give a boolean result.  So what about just comparing the values

;; If all the values are true => false
;; If all the values are false => false
;; If there is at lease one true & one false => true
;; So comparing all of the parameters being equal gives the wrong result, false when true and vice versa.
;; Inverting the comparison of all parameters give the correct result.

;; So by replacing the not and first expressions with their results I would get the following combinations of results

(= true true)   ;; => true
(= true false)  ;; => false
(= false false) ;; => true

;; These combinations give the opposite result we are looking for (false when it should be true, etc).
;; So we can invert the result using not or better still use the not= function

(not= true true)   ;; => false
(not= true false)  ;; => true
(not= false false) ;; => false

;; 4Clojure entered solution
;; not=

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; #80 Perfect numbers

;; Description
;; A number is "perfect" if the sum of its divisors equal the number itself. 6 is a perfect number because 1+2+3=6. Write a function which returns true for perfect numbers and false otherwise.

;; Tests
#_(= (__ 6) true)
#_(= (__ 7) true)
#_(= (__ 496) true)
#_(= (__ 500) false)
#_(= (__ 8128) true)


;; Decompose the challenge
;; Find the range of numbers up to the number being tested as perfect.
;; Identify which of the numbers in the range are divisors
;; Add all the numbers that are divisors to get a total
;; Test if total and number being tested are equal


;; Passing the first test
;; We can see how the first test works, as its mentioned in the description

;; Put simply, we can add the range of numbers that we know are the divisors,
;; then compare them with the potential perfect number.
(= (+ 1 2 3) 6)
;; => true

;; This expression also works for the second test
(= (+ 1 2 3) 7)
;; => false

;; So far we have just hard-coded the range of divisors, which we knew in advance.
;; Very few of us just know the range of divisors for numbers as large as 496.  So we need a way to calculate the divisors


;; Lets start with the first test case again, as its easy for us to check we are getting the right result.

;; The `range` function generates a range of interger values
;; We can specify a start point (zero by default)
;; The end of the range is around the half of the perfect number

;; So we can generate the whole range of numbers from 1 to 6
(range 1 6)

;; or just the range from 1 to half way
(range 1 3)
;; => (1 2)

;; Range is exclusive of the last number, so it stops before it gets 3.  However, if we use one number higher, then we get the right results.
(range 1 4)
;; => (1 2 3)

;; We can get the half way point by dividing the perfect number by 2
(range 1 (/ 6 2))
;; => (1 2)


;; although we have to add 1 to the result of dividing the perfect number by 2
(range 1 (+ 1 (/ 6 2)))
;; => (1 2 3)

(range 1 (/ 7 2))
;; => (1 2 3)

(range 1 3.5)
;; => (1 2 3)

;; When reviewing the code after the dojo, I tried a decimal number with the range function and it worked just fine
;; Looking at the code for range, it checks if the arguments are of type Long and calls the relevant function.
#_(if (and (instance? Long start) (instance? Long end))
    (clojure.lang.LongRange/create start end)
    (clojure.lang.Range/create start end))

;; The way I chose to deal with numbers that do not divide into the perfect number exactly is to use the quot function.
;; The quot function returns the number of times one number goes into another.  Any remainder is discarded.
(quot 6 2)
;; => 3

(quot 7 2)
;; => 3

;; combining range and quot we can get can get the range
;; Lets put them into an anonymous function and test it out

((fn [number] (range 1 (+ 1 (quot number 2)))) 6)
;; => (1 2 3)


((fn [number] (range 1 (+ 1 (quot number 2)))) 7)
;; => (1 2 3)

;; now we have the range of numbers we can add them up using reduce to iterate the + function over the sequence.
(reduce + ((fn [number] (range 1 (+ 1 (quot number 2)))) 6))
;; => 6

;; add a comaparison to see if the result of adding up the divisors passes the test
((fn [number]
   (= number
      (reduce + ((fn [number] (range 1 (+ 1 (quot number 2)))) number)))) 6)
;; => true

;; Passes test as we are expecting false
((fn [number]
   (= number
      (reduce + ((fn [number] (range 1 (+ 1 (quot number 2)))) number)))) 7)
;; => false


;; Using a larger number that is perfect fails the test though
((fn [number]
   (= number
      (reduce + ((fn [number] (range 1 (+ 1 (quot number 2)))) number)))) 496)
;; => false

;; So what is going on?  Lets take a look at what is happening inside this expression.

;; If we see the total number of all the numbers in the range we see that number is far higher than the perfect number.
(reduce + ((fn [number] (range 1 (+ 1 (quot number 2)))) 496))
;; => 30876

;; Looking at the range of numbers we see that some of these numbers are not divisors
((fn [number] (range 1 (+ 1 (quot number 2)))) 496)
;; => (1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45 46 47 48 49 50 51 52 53 54 55 56 57 58 59 60 61 62 63 64 65 66 67 68 69 70 71 72 73 74 75 76 77 78 79 80 81 82 83 84 85 86 87 88 89 90 91 92 93 94 95 96 97 98 99 100 ...)


;; If we filter out only those number that divide into our perfect number with a remainder of zero, then we will have a range of just divisors for the perfect number
(filter #(= 0 (rem 496 %))
        ((fn [number] (range 1 (+ 1 (quot number 2)))) 496))
;; => (1 2 4 8 16 31 62 124 248)

;; Adding this filter to our expression should now pass the test for any number
(fn [number]
  (= number
     (reduce + (filter #(= 0 (rem number %))
                       ((fn [number] (range 1 (+ 1 (quot number 2)))) number)))))


;; alternative - use ratio? as the filter function to remove all numbers that are not an integer value

;; If we use the divide function on our perfect number then we will get a ratio type for each number in the range that does not divide equally

;; For example
(/ 496 37)
;; => 496/37

;; So we could use a filter that checks if the result is a ratio

(filter #(ratio? (/ 496 %))
        ((fn [number] (range 1 (+ 1 (quot number 2)))) 496))
;; => (3 5 6 7 9 10 11 12 13 14 15 17 18 19 20 21 22 23 24 25 26 27 28 29 30 32 33 34 35 36 37 38 39 40 41 42 43 44 45 46 47 48 49 50 51 52 53 54 55 56 57 58 59 60 61 63 64 65 66 67 68 69 70 71 72 73 74 75 76 77 78 79 80 81 82 83 84 85 86 87 88 89 90 91 92 93 94 95 96 97 98 99 100 101 102 103 104 105 106 107 ...)

;; filter generates all the numbers from the range that are ratio? types, so we want the inverse of this.
;; We could use not

(filter #(not (ratio? (/ 496 %)))
        ((fn [number] (range 1 (+ 1 (quot number 2)))) 496))
;; => (1 2 4 8 16 31 62 124 248)


;; Or we can use the a function that does the inverse of filter, a function called remove
(remove #(ratio? (/ 496 %))
        ((fn [number] (range 1 (+ 1 (quot number 2)))) 496))
;; => (1 2 4 8 16 31 62 124 248)


;; Further thoughts
;; Unless we need to optimise the processing of the range of numbers, we can just generate the whole range with a simpler expression
;; (range 1 number) instead of (range 1 (+ 1 (quot number 2)))

;; With a lazy sequence, we can still be fairly optimal in the way we work as the filter or remove function could be used to minimise the range being generated

(remove #(ratio? (/ 2305843008139952128 %))
        ((fn [number] (range 1 (+ 1 (quot number 2)))) 2305843008139952128))
