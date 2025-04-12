(ns clojure-through-code.debugging-with-cider)


(defn bar
  [x]
  (inc x))


(defn foo
  [x]
  (bar x))


(foo 9)


;; Debugging workflow - work-around for the lack of support for Evil mode in cider debugging

;; Evaluate the two function, foo and bar - `, e f` in Evil normal state
;; `, d b` with the cursor on `(def foo ,,,)` to automatically add breakpoints to the foo function definition.

;; `, e f` with the cursor on (foo 9) to call the function and start the cider debugger

;; `C-z` to switch to Emacs editing state

;; `i` with the cursor still on x to jump into the bar function, the cursor jumps to the x in the bar definition
;; (its too late to step into the bar function when the cursor is on bar, as its already evaluated bar and showing the result)

;; `n` to show the result of inc x
;; `n` to show the result of bar x
;; `n` to finish with the result showing on (foo 9) function call...

;; You could instrument both functions, which is viable in small example, however, it would be impractical when there are many functions you want to step into that are called by a function, or when those function call other functions that you want to step into... etc.
