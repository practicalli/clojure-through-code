;; Conjure Debug output - first evaluation of ns form
; --------------------------------------------------------------------------------
; eval (root-form): (ns clojure-through-code.01-basics "Communicate th...
; debug: send
{:code
 "(ns clojure-through-code.01-basics)"
 :id
 "131fa5a4-10ff-41a8-9a51-164ffba78b64"
 :nrepl.middleware.print/options
 {:associative 1 :length 500 :level 50 :right-margin 72}
 :nrepl.middleware.print/print
 "cider.nrepl.pprint/pprint"
 :op
 "eval"
 :session
 "44c3284a-41df-4f7e-a66d-bb30f484db91"}
; debug: send
{:code
 "(ns clojure-through-code.01-basics
  \"Communicate the purpose of a namespace via a doc-string.
   Include descriptions of data models defined in the namespace.\")"
 :column
 1
 :file
 "/home/practicalli/projects/practicalli/clojure-through-code/src/clojure_through_code/01_basics.clj"
 :id
 "e468d5cd-445f-4580-aec5-ce944c4ef3b2"
 :line
 36
 :nrepl.middleware.print/options
 {:associative 1 :length 500 :level 50 :right-margin 72}
 :nrepl.middleware.print/print
 "cider.nrepl.pprint/pprint"
 :ns
 "clojure-through-code.01-basics"
 :op
 "eval"
 :session
 "44c3284a-41df-4f7e-a66d-bb30f484db91"}
; debug: receive
{:id "e468d5cd-445f-4580-aec5-ce944c4ef3b2"
 :ns "clojure-through-code.01-basics"
 :session "44c3284a-41df-4f7e-a66d-bb30f484db91"
 :status ["namespace-not-found" "done" "error"]}
; Namespace not found: clojure-through-code.01-basics
; debug: receive
{:changed-namespaces
 {:clojure.pprint
  {:aliases
   {}
   :interns
   {:*code-table* {}
    :*current-length* {}
    :*current-level* {}
    :*default-page-width* {}
    :*format-str* {}
    :*print-base* {}
    :*print-circle* {}
    :*print-lines* {}
    :*print-miser-width* {}
    :*print-pprint-dispatch* {:fn "true"}
    :*print-pretty* {}
    :*print-radix* {}
    :*print-right-margin* {}
    :*print-shared* {}
    :*print-suppress-namespaces* {}
    :*symbol-map* {}
    :abort? {:fn "true"}
    :absolute-reposition {:fn "true"}
    :absolute-tabulation {:fn "true"}
    :add-core-ns {:fn "true"}
    :add-english-scales {:fn "true"}
    :add-to-buffer {:fn "true"}
    :ancestor? {:fn "true"}
    :arg-navigator {}
    :base-str {:fn "true"}
    :binding-map {:macro "true"}
    :boolean-conditional {:fn "true"}
    :brackets {:fn "true"}
    :buffer-blob {}
    :buffer-blob? {:fn "true"}
    :buffer-length {:fn "true"}
    :c-write-char {:fn "true"}
    :cached-compile {:fn "true"}
    :capitalize-string {:fn "true"}
    :capitalize-word-writer {:fn "true"}
    :check-arg-conditional {:fn "true"}
    :check-enumerated-arg {:fn "true"}
    :check-flags {:fn "true"}
    :choice-conditional {:fn "true"}
    :cl-format {:fn "true"}
    :code-dispatch {:fn "true"}
    :collect-clauses {:fn "true"}
    :column-writer {:fn "true"}
    :compile-directive {:fn "true"}
    :compile-format {:fn "true"}
    :compile-raw-string {:fn "true"}
    :compiled-directive {}
    :conditional-newline {:fn "true"}
    :consume {:fn "true"}
    :consume-while {:fn "true"}
    :convert-ratio {:fn "true"}
    :defdirectives {:macro "true"}
    :deftype {:macro "true"}
    :directive-table {}
    :dollar-float {:fn "true"}
    :downcase-writer {:fn "true"}
    :else-separator? {:fn "true"}
    :emit-nl {:fn "true"}
    :emit-nl? {:fn "true"}
    :end-block {:fn "true"}
    :end-block-t {}
    :end-block-t? {:fn "true"}
    :english-cardinal-tens {}
    :english-cardinal-units {}
    :english-ordinal-tens {}
    :english-ordinal-units {}
    :english-scale-numbers {}
    :execute-format {:fn "true"}
    :execute-sub-format {:fn "true"}
    :expand-fixed {:fn "true"}
    :exponential-float {:fn "true"}
    :extract-flags {:fn "true"}
    :extract-param {:fn "true"}
    :extract-params {:fn "true"}
    :fixed-float {:fn "true"}
    :flag-defs {}
    :float-parts {:fn "true"}
    :float-parts-base {:fn "true"}
    :format-ascii {:fn "true"}
    :format-cardinal-english {:fn "true"}
    :format-error {:fn "true"}
    :format-integer {:fn "true"}
    :format-logical-block {:fn "true"}
    :format-new-roman {:fn "true"}
    :format-old-roman {:fn "true"}
    :format-ordinal-english {:fn "true"}
    :format-roman {:fn "true"}
    :format-simple-cardinal {:fn "true"}
    :format-simple-number {:fn "true"}
    :format-simple-ordinal {:fn "true"}
    :formatter {:macro "true"}
    :formatter-out {:macro "true"}
    :fresh-line {:fn "true"}
    :general-float {:fn "true"}
    :get-column {:fn "true"}
    :get-field {:fn "true"}
    :get-fixed {:fn "true"}
    :get-format-arg {:fn "true"}
    :get-line {:fn "true"}
    :get-max-column {:fn "true"}
    :get-miser-width {:fn "true"}
    :get-pretty-writer {:fn "true"}
    :get-section {:fn "true"}
    :get-sub-section {:fn "true"}
    :get-writer {:fn "true"}
    :getf {:macro "true"}
    :group-by* {:fn "true"}
    :inc-s {:fn "true"}
    :indent {:fn "true"}
    :indent-t {}
    :indent-t? {:fn "true"}
    :init-cap-writer {:fn "true"}
    :init-navigator {:fn "true"}
    :insert-decimal {:fn "true"}
    :insert-scaled-decimal {:fn "true"}
    :integral? {:fn "true"}
    :iterate-list-of-sublists {:fn "true"}
    :iterate-main-list {:fn "true"}
    :iterate-main-sublists {:fn "true"}
    :iterate-sublist {:fn "true"}
    :java-base-formats {}
    :justify-clauses {:fn "true"}
    :level-exceeded {:fn "true"}
    :linear-nl? {:fn "true"}
    :logical-block {}
    :logical-block-or-justify {:fn "true"}
    :ltrim {:fn "true"}
    :make-buffer-blob {:fn "true"}
    :make-end-block-t {:fn "true"}
    :make-indent-t {:fn "true"}
    :make-nl-t {:fn "true"}
    :make-pretty-writer {:fn "true"}
    :make-start-block-t {:fn "true"}
    :map-params {:fn "true"}
    :map-passing-context {:fn "true"}
    :map-ref-type {:fn "true"}
    :miser-nl? {:fn "true"}
    :modify-case {:fn "true"}
    :multi-defn {:fn "true"}
    :needs-pretty {:fn "true"}
    :new-roman-table {}
    :next-arg {:fn "true"}
    :next-arg-or-nil {:fn "true"}
    :nl {:fn "true"}
    :nl-t {}
    :nl-t? {:fn "true"}
    :old-roman-table {}
    :opt-base-str {:fn "true"}
    :orig-pr {:fn "true"}
    :p-write-char {:fn "true"}
    :param-pattern {}
    :parse-lb-options {:fn "true"}
    :plain-character {:fn "true"}
    :pll-mod-body {:fn "true"}
    :pp {:macro "true"}
    :pp-newline {:fn "true"}
    :pprint {:fn "true"}
    :pprint-anon-func {:fn "true"}
    :pprint-array {:fn "true"}
    :pprint-binding-form {:fn "true"}
    :pprint-code-list {:fn "true"}
    :pprint-code-symbol {:fn "true"}
    :pprint-cond {:fn "true"}
    :pprint-condp {:fn "true"}
    :pprint-defn {:fn "true"}
    :pprint-hold-first {:fn "true"}
    :pprint-ideref {:fn "true"}
    :pprint-if {:fn "true"}
    :pprint-indent {:fn "true"}
    :pprint-let {:fn "true"}
    :pprint-list {:fn "true"}
    :pprint-logical-block {:macro "true"}
    :pprint-map {:fn "true"}
    :pprint-meta {:fn "true"}
    :pprint-newline {:fn "true"}
    :pprint-ns {:fn "true"}
    :pprint-ns-reference {:fn "true"}
    :pprint-pqueue {:fn "true"}
    :pprint-reader-macro {:fn "true"}
    :pprint-set {:fn "true"}
    :pprint-simple-code-list {:fn "true"}
    :pprint-simple-default {:fn "true"}
    :pprint-simple-list {:fn "true"}
    :pprint-tab {:fn "true"}
    :pprint-vector {:fn "true"}
    :pr-with-base {:fn "true"}
    :prefix-count {:fn "true"}
    :prerr {:fn "true"}
    :pretty-character {:fn "true"}
    :pretty-writer {:fn "true"}
    :pretty-writer? {:fn "true"}
    :print-length-loop {:macro "true"}
    :print-table {:fn "true"}
    :prlabel {:macro "true"}
    :process-bracket {:fn "true"}
    :process-clause {:fn "true"}
    :process-directive-table-element {:fn "true"}
    :process-nesting {:fn "true"}
    :readable-character {:fn "true"}
    :reader-macros {}
    :realize-parameter {:fn "true"}
    :realize-parameter-list {:fn "true"}
    :relative-reposition {:fn "true"}
    :relative-tabulation {:fn "true"}
    :remainders {:fn "true"}
    :render-clauses {:fn "true"}
    :right-bracket {:fn "true"}
    :round-str {:fn "true"}
    :rtrim {:fn "true"}
    :section {}
    :separator? {:fn "true"}
    :set-field {:fn "true"}
    :set-indent {:fn "true"}
    :set-logical-block-callback {:fn "true"}
    :set-max-column {:fn "true"}
    :set-miser-width {:fn "true"}
    :set-pprint-dispatch {:fn "true"}
    :setf {:macro "true"}
    :simple-dispatch {:fn "true"}
    :single-defn {:fn "true"}
    :special-chars {}
    :special-params {}
    :special-radix-markers {}
    :split-at-newline {:fn "true"}
    :start-block {:fn "true"}
    :start-block-t {}
    :start-block-t? {:fn "true"}
    :table-ize {:fn "true"}
    :tok {:fn "true"}
    :tokens-fit? {:fn "true"}
    :toks {:fn "true"}
    :translate-param {:fn "true"}
    :tuple-map {:fn "true"}
    :two-forms {:fn "true"}
    :type-map {}
    :unzip-map {:fn "true"}
    :upcase-writer {:fn "true"}
    :update-nl-state {:fn "true"}
    :use-method {:fn "true"}
    :walk {:fn "true"}
    :with-pprint-dispatch {:macro "true"}
    :with-pretty-writer {:macro "true"}
    :write {:fn "true"}
    :write-buffered-output {:fn "true"}
    :write-initial-lines {:fn "true"}
    :write-line {:fn "true"}
    :write-option-table {}
    :write-out {:fn "true"}
    :write-to-base {:macro "true"}
    :write-token {:fn "true"}
    :write-token-string {:fn "true"}
    :write-tokens {:fn "true"}
    :write-white-space {:fn "true"}}}
  :conjure.internal
  {:aliases
   {:pp "clojure.pprint"}
   :interns
   {:bounded-conj {:fn "true"}
    :dump-tap-queue! {:fn "true"}
    :enqueue-tap! {:fn "true"}
    :initial-ns {}
    :tap-queue! {}
    :tap-queue-size {}}}}
 :id
 "e468d5cd-445f-4580-aec5-ce944c4ef3b2"
 :repl-type
 "clj"
 :session
 "44c3284a-41df-4f7e-a66d-bb30f484db91"
 :status
 ["state"]}
; debug: receive
{:id "131fa5a4-10ff-41a8-9a51-164ffba78b64"
 :ns "clojure-through-code.01-basics"
 :session "44c3284a-41df-4f7e-a66d-bb30f484db91"
 :value "nil"}
; debug: receive
{:id "131fa5a4-10ff-41a8-9a51-164ffba78b64"
 :session "44c3284a-41df-4f7e-a66d-bb30f484db91"
 :status ["done"]}
; debug: receive
{:changed-namespaces {:clojure-through-code.01-basics {:aliases {} :interns {}}}
 :id "131fa5a4-10ff-41a8-9a51-164ffba78b64"
 :repl-type "clj"
 :session "44c3284a-41df-4f7e-a66d-bb30f484db91"
 :status ["state"]}
