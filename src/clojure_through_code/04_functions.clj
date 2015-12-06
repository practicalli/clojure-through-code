;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Defining things in Clojure

;; Clojure design at its most basic comprises:
;; - one or more data structures
;; - functions that process those datastructures

;; namespace
(ns clojure-through-code.04-functions)

;;;;;;;;;;;;;;;;;;;;;;
;; Defining behaviour by writing functions

;; Clojure has functions, rather than methods for defining behaviour / "algorithms"
;;


; Use fn to create new functions. A function always returns
; the value of evaluating its last statement.

; Lets define a very simple function, that returns a string
(fn [] "Hello Clojurian, hope you are enjoying the REPL")

; When evaluated, this function definition returns a reference to this function definition.  This means the function can be called, in this case by putting the function as the first elemment of a list

((fn [] "Hello Clojurian, do you like the dynamic nature of Clojure"))

;; remember, the first item in a list is evaluted as a function call

; we have seen how to give a data structure a name (var) using def

(def x 1)
x

; We can also give a name to a function using def

(def i-have-a-name (fn [] "I am not a number, I am a named function - actually we call the name a symbol and it can be used as a reference to the function."))

(i-have-a-name)

; You can shorten this process by using defn
(defn i-have-a-name [] "Oh, I am a new function definition, but have the same name (symbol).")

(i-have-a-name)

; The [] is the list of arguments for the function.

(defn hello [name]
  (str "Hello there " name))

(hello "Steve") ; => "Hello Steve"

; You can also use the annonymous function shorthand, # (), to create functions, (not that useful in this simple example).  The %1 placeholder takes the first argument to the function.  You can use %1, %2 and %3

(def hello2 #(str "Hello " %1 ", are you awake yet?"))
(hello2 "Mike")

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; A brief divergence on anonymous functions and the #() syntatic sugar

;; The anonymous function shorthand is only useful for one function call - personally I am not that keen on it
(#(+ %1 %2 %3) 2 4 6)

;; A simple reverse anonymous function, using the threading macro
(#(-> [%2 %1]) "Bob" "Sue")  ;; => ["Sue" "Bob"]

;; #(...)  is shorthand for (fn [arg1 arg2 ...] (...))
;; where the number of argN depends on how many %N you have in the body . So when you write
;; #(%) is translated to(fn [arg1] (arg1))

;; Notice that this is different from the first anonymous function: (fn [arg1] arg1)

;; This version returns arg1 as a value, the version that comes from expanding the shorthand tries to call it as a function. You get an error because a string is not a valid function.

;; Since the shorthand supplies a set of parentheses around the body, it can only be used to execute a single function call or special form.


;; From ClojureBridge

;; user> (fn [coll] (filter even? coll))
;; #<user$eval4837$fn__4838 user$eval4837$fn__4838@660652c5>

;; user> ; anonymous function is defined, but how can we use this?
;; user> ; here's one way to use anonymous function
;; user> ((fn [coll] (filter even? coll)) [1 2 3 4 5 6])
;; (2 4 6)

;; user> ; if we use #() literal, an anonymous function can be written like this.
;; user> (#(filter even? %) [1 2 3 4 5 6])
;; (2 4 6)

;; user> ; to use anonymous function more than once, bind it to a name.
;; user> (def evens (fn [coll] (filter even? coll)))
;; #'user/evens
;; user> (evens [1 2 3 4 5 6])
;; (2 4 6)
;; You may have thought that the function above is the equivalent to:

;; user> (defn evens-by-defn
;;         [coll]
;;         (filter even? coll))
;; #'user/evens-by-defn
;; user> (evens-by-defn [1 2 3 4 5 6])
;; (2 4 6)
;; However, Clojure programmers use anonymous function very often. Why do we need anonymous function?

;; The biggest reason is to use functions for higher-order-function (See Higher-order Function ).

;; Another reason can been seen in the next example.

;; Let’s say we want to get the even numbers after two vectors are combined:

;; concat: http://clojuredocs.org/clojure_core/clojure.core/concat
;; If we use def to save the value…

;; user> (defn evens-with-def
;;         [vec1 vec2]
;;         (def combined (concat vec1 vec2))
;;         (filter even? combined))
;; #'user/evens-with-def
;; user> (evens-with-def [1 2 3] [4 5 6])
;; (2 4 6)
;; user> combined
;; [1 2 3 4 5 6]
;; combined is exposed outside of our function, and this is bug-prone.

;; If we use an anonymous function…

;; user> (defn evens-with-fn
;;         [vec1 vec2]
;;         ((fn [x] (filter even? x))
;;          (concat vec1 vec2)))
;; #'user/evens-with-fn
;; user> (evens-with-fn [1 2 3] [4 5 6])
;; (2 4 6)
;; There’s no variable for a combined vector.

;; We could also use let, which provides lexical binding and limits it to within the scope.

;; user> (defn evens-with-let
;;         [vec1 vec2]
;;         (let [combined-in-let (concat vec1 vec2)]
;;           (filter even? combined-in-let)))
;; #'user/evens-with-let
;; user> (evens-with-let [1 2 3] [4 5 6])
;; (2 4 6)
;; user> combined-in-let
;; CompilerException java.lang.RuntimeException: Unable to resolve symbol: combined-in-let in this c
;; ontext, compiling:(/private/var/folders/4b/c9gsjvv12tq9n4mph065qs480000gn/T/form-init632366111132
;; 2215411.clj:1:743)
;; user> ; combined-in-let is available only in let body
;; Advice to coaches

;; The third example here doesn’t use anonymous functions. This would be a good example of how Clojure provides many ways to do the same thing.


;;; some other examples

;; user=> (#(* % %) 3) ; square
;; 9
;; user=> (#(+ % (* 2 %2)) 1 3) ; 1*x + 2*y
;; 7
;; user=> (+ 1 (* 2 3))
;; 7

;; See also
;; http://java.dzone.com/articles/clojure-anonymous-functions
;; https://coderwall.com/p/panlza/function-syntax-in-clojure

;; Jay Fields - some good advice on avoiding annonymous functions
;; http://blog.jayfields.com/2012/10/clojure-avoiding-anonymous-functions.html


;; From http://en.wikibooks.org/wiki/Clojure_Programming/Examples/API_Examples/Function_Tools

;; fn example

;; (map (fn [a] (str "hi " a)) ["mum" "dad" "sister"])
;; ; => ("hi mum" "hi dad" "hi sister")

;; #() example
;; See the reader page, (Macro characters -> Dispatch -> Anonymous function literal) for an explanation of the '%' and other characters used to refer to function arguments.

;; user=> (def sq #(* % %))
;; #'user/sq
;; user=> (sq 3)
;; 9
;; user=> (map #(class %) [1 "asd"])
;; (java.lang.Integer java.lang.String)


;; "lambda" example
;; Use fn, or even better there is a custom syntax to create an anonymous function:

;; (map #(str "hi " %) ["mum" "dad" "sister"])

;; % example
;; Represents an optional argument to an unnamed function:

;; #(+ 2 %)
;; #(+ 2 %1 %2)
;; Arguments in the body are determined by the presence of argument literals taking the form %, %n or %&. % is a synonym for %1, %n designates the nth arg (1-based), and %& designates a rest arg.

;; complement example
;; Usage: (complement f)

;; Takes a fn f and returns a fn that takes the same arguments as f, has the same effects, if any, and returns the opposite truth value.

;; user=> (defn single-digit[x] (< x 10))
;; #'user/single-digit
;; user=> (single-digit 9)
;; true
;; user=> (single-digit 10)
;; false
;; user=> (def multi-digit (complement single-digit))
;; #'user/multi-digit
;; user=> (multi-digit 9)
;; false
;; user=> (multi-digit 10)
;; true


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Polymorphism

;; Clojure does not provide syntax for encapsulation as Java, C++ and C# developers know it.  No syntax is provided for defining a Class hierachy.  However, polymorphism syntax is available whendefining functions with multiple arity (different behaviour based on the number of arguments).

; You can have multi-variadic functions too
;; one function that behaves differently dependant on the number or arugments passed

(defn hello-polly-function
  ([] "Hello Hackference")
  ([name] (str "Hello " name ", hope your talk is awesome at Hackference")))

(hello-polly-function)
(hello-polly-function "Cristiano")


;; Functions can pack extra arguments up in a sequence for you, its the & notation that is important, although its a fairly common convention to use args (better if its given a more meaningful name though)

(defn count-args [& args]
  (str "You passed " (count args) " args: " args))
(count-args 1 2 3)

; You can mix regular and packed arguments
(defn hello-count [name & args]
  (str "Hello " name ", you passed " (count args) " extra args"))
(hello-count "Finn" 1 2 3)




;; using a local variables... (its not actulally a variable as it doenst vary)

(defn do-stuff [argument]
  (let [local-data (first argument)]
    (str local-data)))

(do-stuff [1 2 3])

;; (do-stuff my-data)

;; Remember, the first item in a list is evaluated as a call to a function.


;; We all love hello world, so here we go...

(defn hello-world [name]
  (println (str "Hello " name ", welcome to the world of Clojure")))

;; Something about the above function is not quite right...




;;;;;;;;;;;;;;;;;;;;;;
;; Defining more detailed data structures


(def john {:firstname "John" :lastname "Stevenson"})

;; datastructures can be called like a function, returning a .values

;; (john :firstname :lastname)
(john :firstname )
(:lastname john)
(get john :lastname)

(def people
  {:001 {:name "John Stevenson"   :twitter "jr0cket"}
   :002 {:name "Mike Mechanic"    :twitter "mechano"}
   :006 {:name "Patrick McGoohan" :twitter "theprisoner"}})

(people :001)

(get-in people [:006 :twitter])

(:twitter (people :006))

(def people-locations {
     :Birmingham [{:name "John Stevenson"   :twitter "jr0cket"}
                  {:name "Mike Mechanic"    :twitter "mechano"}]
     :TheIsland   {:name "Patrick McGoohan" :twitter "theprisoner"}})


(get-in people-locations [:Birmingham 1 :twitter])


;;;;;;;;;;;;;;;;;;;;;;;;;
;; Filtering out data



(def payroll-data [
     [:000-00-0000 "TYPE 1" "JACKSON" "FRED"]
     [:000-00-0001 "TYPE 2" "SIMPSON" "HOMER"]
     [:000-00-0002 "TYPE 4" "SMITH" "SUSAN"]])

(def data-format [0 3 2])

(defn clean-payroll-data
  [raw-data data-format]
  (map #(get-in raw-data [% data-format] nil) (range (count raw-data))))

;; the % symbol is used to refer to the functions return value

(map
   (partial clean-payroll-data payroll-data) data-format)

(apply interleave
  (map
   (partial clean-payroll-data payroll-data) data-format))

(clean-payroll-data payroll-data data-format)
;;=> (nill nil nil)



(def hack-data {:venue-capacity 200
                :sandwiches-per-person 1.5
                :previous-dropout-percent 10
                :weather-prediction "good"})


(defn random-sandwiches
  "Unintelligent guess at how many sandwiches will be eaten at Hackathon"
  [attendees-registered]
  (rand-int attendees-registered))

(defn how-many-sandwiches-to-order
  [hack-data]
  (* (hack-data :venue-capacity)
     (hack-data :sandwiches-per-person)))


(random-sandwiches 50)

(how-many-sandwiches-to-order hack-data)



(def hack-data-detailed {
     :max-sandwiches 300
     :max-attendees 200
     :weather "good"
     :languages {:dynamic "clojure" :slapdash "javascript" :scary "haskell" }})


(defn best-hack-language []
   (get-in hack-data-detailed [:languages :dynamic]))


(defn worst-hack-language []
   (get-in hack-data-detailed [:languages :scala] "No time for Scala"))


(best-hack-language)
(worst-hack-language)



;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Scope of functions

;; 



;; a little sugar on your syntax with the threading macro
;; this passes the data structue to each keyword and values are
;; values are effectively filtered out
;; It is not possible to provide a default result using this syntax
(-> hack-data-detailed :languages :dynamic)


;; Or you can try some destructuring with let
(let [{{best-language :dynamic} :languages} hack-data-detailed ]
  [best-language])


;; Fun with multiple arity (the number of parameters)

(defn any-sandwiches-left
  ([mike]"Yes, no rush")
  ([mike john]"Better move quickly...")
  ([mike john andy]"You have no chance, better go to morrisons"))


(any-sandwiches-left "mike")
(any-sandwiches-left 1 2 3)


;; How does this multi-arity work?
;; The parameters are bound to there aguments when compiled.

(def mult
  (fn multi-pass
      ([] 1)
      ([x] x)
      ([x y] (* x y))
      ([x y & more]
          (apply multi-pass (multi-pass x y) more))))




(def locations {
     :london {:latitude 34, :longtitude 57}})

(def location {
     :london     {:gps {:co-ords [12.37 53.78]}}
     :new-york   {:gps {:co-ords [12.37 53.78]}}
     :Birmingham {:gps {:co-ords [12.37 53.78]}}})



;;nested maps and some ways of getting at the keys

(def me {:name
         {:firstname "John"
          :middlename "Lawrence"
          :surname "Aspden"}
         :address
         {:street "Catherine Street"
          :town {:name "Cambridge"
                 :county "Cambridgeshire"
                 :country{
                          :name "England"
                          :history "Faded Imperial Power"
                          :role-in-world "Beating Australia at Cricket"}}}})


(:name me)
(get me :name)

(get-in me [:name :middlename])
(reduce get me [:address :town :country :role-in-world])
(-> me :address :town :county)

(assoc-in me [:name :initials] "JLA")
(update-in me [:address :street] #(str "33 " %))

;; In Emacs Live, the shorthand for a lambda function is represented as #() rather than # followed by parenthesis

;; Emacs Live also substitutes the lambda (fn ) symbol for a lambda function


;;;;;;;;;;;;;;;;;;;;;;
;; Documenting

(defn foo
  "Documenting your code is easy, simpky add a string as a description of your functions"
  [x]
  (str x " - Hello, World!"))

;; Once you have defined a function, you can call it passing any arguments it requires

(foo "Devoxx UK")

;; Evaluation in LightTable
;; - defined values have a green background
;; - evaluated values have a grey background

(defn lets-be-lazy
  "Create a range of Integer values up to the value of the argument"
  [x]
  (range x))

;; (doc range)
;;
;; clojure.core/range
;; ([] [end] [start end] [start end step])
;; Added in 1.0
;;  Returns a lazy seq of nums from start (inclusive) to end (exclusive), by step, where start defaults to 0, step to 1, and endto infinity. When step is equal to 0, returns an infinite sequence of start. When start is equal to end, returns empty list.


(lets-be-lazy 5)

;; => (1 2 3 4 5)



;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; More examples of functions


;;; functions

;; A symbol is something that can be used the same as its value - refferential transparency
;; There is no requirement to use symbols, although it does make the code more readable
;; and makes it easy to reuse the value (by typing the symbol rather than the function again)

;; let binds symbols to values, only existing withing the local scope
;; requires an even number of mappings (eg. symbol value pairs)

(fn [x y]
  (let [square (fn [x] (* x x))]
     (Math/sqrt (+ (square x) (square y)))))

(fn [x y]
  (letfn [(square [x] (* x x))]
     (Math/sqrt (+ (square x) (square y)))))



;; You cannot next annonymous functions inside each other (good!).


#(Math/sqrt (+ (* %1 %1) (* %2 %2)))


;; You can do a lot without defining functions

;; defs are golbally accesssible slots to bind symbols to values
;; - the are like memory locations
(def pi 3.1415926)


(def pythagorus
  (fn [x y]
    (Math/sqrt (+ (* x x) (* y y)))))

;; a simpler way to bind behaviour to a symbol
;; a list program is usually a collection of defs
(defn pythagorass
  [x y]
  (Math/sqrt (+ (* x x) (* y y))))


;; Dynamic Vars
;;(def ^:dynamic *out*)

;; meta-data can be thought of as a bit like java annotations - but dont compile in the same way

;; beware of using dynamic vars as the can get splatted when you cross threading boundaries
;; dont use this...
;;(binding [*out* (io/writer (io/file "/tmp/foo.txt"))]
;;  (println "Hello"))

;; Clojures
;;a pure function can only be written in terms of its arguments

(let [x 3]
  (fn [y]
    (Math/sqrt (+ (* x x) (* y y)))))


;; Clojure can access values safely, unlike languages such as JavaScript
;; -- with JavaScript the values are mutable, so they could change
;; Clojure values are immutable so the same result is returned what ever happens

;; Partial functions - more idiomatic to spell out the function
;; not used that much
;; (partial pythagorus 3)



;;;;;;;;;;;;;;;;;;;;;;;;
;; Understanding how functions work (move this to a later section)

;; To see how a funciton has been defined, use the source function followed by that functions name

;; Lets take a look at the srt function

;; (source str)

;; This gives the source code for str as a result

;; (defn str
;;   "With no args, returns the empty string. With one arg x, returns
;;   x.toString().  (str nil) returns the empty string. With more than
;;   one arg, returns the concatenation of the str values of the args."
;;   {:tag String
;;    :added "1.0"
;;    :static true}
;;   (^String [] "")
;;   (^String [^Object x]
;;    (if (nil? x) "" (. x (toString))))
;;   (^String [x & ys]
;;      ((fn [^StringBuilder sb more]
;;           (if more
;;             (recur (. sb  (append (str (first more)))) (next more))
;;             (str sb)))
;;       (new StringBuilder (str x)) ys)))
;; nil

;; The value nil is returned from the source function as the output of the function code is a side effect




;; Basic refactoring with multiple cursors

(defn refactor-me
  "A simple little function used to practice simple refactoring with emacs multiple cursors"
  [better-arg-name]
  (let [arg better-arg-name]
    (if (< (count arg) (count better-arg-name))
      (str better-arg-name)
      (str arg))))

(refactor-me "fish")




;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Fixed or Variable Arity

;; http://stuartsierra.com/2015/06/01/clojure-donts-optional-arguments-with-varargs

;; Say you want to define a function with a mix of required and optional arguments. I’ve often seen this:

(defn foo [a & [b]]
  (println "Required argument a is" a)
  (println "Optional argument b is" b))

;; This is a clever trick. It works because & [b] destructures the sequence of arguments passed to the function after a. Sequential destructuring doesn’t require that the number of symbols match the number of elements in the sequence being bound. If there are more symbols than values, they are bound to nil.

(foo 3 4)
;; Required argument a is 3
;; Optional argument b is 4
;;=> nil

(foo 9)
;; Required argument a is 9
;; Optional argument b is nil
;;=> nil

;; I don’t like this pattern for two reasons.

;; One. Because it’s variable arity, the function foo accepts any number of arguments. You won’t get an error if you call it with extra arguments, they will just be silently ignored.

(foo 5 6 7 8)
;; Required argument a is 5
;; Optional argument b is 6
;;=> nil

;; Two. It muddles the intent. The presence of & in the parameter vector suggests that this function is meant to be variable-arity. Reading this code, I might start to wonder why. Or I might miss the & and think this function is meant to be called with a sequence as its second argument.

;; A couple more lines make it clearer:

(defn foo
  ([a]
   (foo a nil))
  ([a b]
   (println "Required argument a is" a)
   (println "Optional argument b is" b)))

;; The intent here is unambiguous: The function takes either one or two arguments, with b defaulting to nil. Trying to call it with more than two arguments will throw an exception, telling you that you did something wrong.

;; And one more thing: it’s faster. Variable-arity function calls have to allocate a sequence to hold the arguments, then go through apply. Timothy Baldridge did a quick performance comparison showing that calls to a function with multiple, fixed arities can be much faster than variable-arity (varargs) function calls.


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Avoiding named parameters in functions

;; You could define a function to process a contact by specifying the particular arguments

(defn process-contact [name address email]
  (str name ", " address ", " email))

(process-contact "John" "Clojureville" "john@clj.com")

;; If we want to add more data to a contact then we end up changing the design of the function, breaking the api of our code.

;; This call to our function will break as it has the wrong number of arguments

;; (process-contact "John" "Clojureville" "john@clj.com" "07911000123")

;; We could define a multi-method function, writing the behaviour for every possible group of parameters,
;; however, unless we now all the possible parameters, we still need to update the functions.

;; Using a multi-method also adds more complexity to the design of the function.

;; Instead of specifically stating the parameters, you can use a clojure data structure.
;; Candidates structures would be vectors or maps.
;; The advantage of maps is that you can add symantic data in the keys to help clarify the meaning of the data

(defn process-contact-map [contact-map]
  (let [name (get contact-map :name)
        address (get contact-map :address)
        email (get contact-map :email)]
    (str name ", " address ", " email)))

(process-contact-map {:name "John" :address "Clojureville" :email "john@clj.com"})

;; We can add another element to the function call without having to change the argument sent to the function, it remains a map.
;; Our function will just ignore any additional entries in the map as we do not bind to them with the let function.

(process-contact-map {:name "John" :address "Clojureville" :email "john@clj.com" :phone "07911000123"})

;; This new version of the function seems more verbose and in this example it is.
;; However, its easy to call this with an incomplete set of values in the map or extra values in the map and they will be ignored,
;; rather than causing an error by not passing the correct number of arguments when calling a function.

;; The previous funciton can be simplified we use a vector as the functions parameter.
;; The let function uses destructuring to bind local symbols in the order of elements in the vector.

(defn process-contact-vector-destructured [contact-vector]
  (let
    [[name address email] contact-vector]
    (str name ", " address ", " email)))

(process-contact-vector-destructured ["John" "Clojureville" "john@clj.com"])

(process-contact-vector-destructured ["John" "Clojureville" "john@clj.com" "07911000123"])

;; We can also make the map version of our function simpler by using the keyword :keys
;; This new version of the function is much simpler to parse once you are comfortable with destructuring
;; For every key in the map, a locally scoped symbol with the same name is created that points to the respective
;; value for each of the keywords in the map.

(defn process-contact-map-destructured [{:keys [name address email]}]
    (str name ", " address ", " email))

(process-contact-map-destructured {:name "John" :address "Clojureville" :email "john@clj.com"})

;; Now if we change the map contents, we only have to change what we do with those elements.
;; If our code does nothing wiith those elements, then they are simply ignored.

(process-contact-map-destructured {:name "John" :address "Clojureville" :email "john@clj.com" :phone "07911000123"})
