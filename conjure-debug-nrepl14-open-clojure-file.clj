; Sponsored by @lanjoni ❤
; --------------------------------------------------------------------------------
; localhost:39209 (connected): /home/practicalli/projects/practicalli/clojure-through-code/.nrepl-port
; debug: send
{:id "aa3543a3-0b0e-4c6c-8e70-ce1011a65aa8" :op "describe"}
; debug: send
{:id "de12c21a-448b-408e-ae68-3c1601277f4d" :op "ls-sessions"}
; debug: /home/practicalli/.local/share/nvim-astro5/lazy/conjure/res/client/clojure/preamble.cljc resource not cached - reading
nil
; debug: send
{:code
 "(create-ns 'conjure.internal)
(intern 'conjure.internal 'initial-ns (symbol (str *ns*)))

(ns conjure.internal
  (:require [clojure.pprint :as pp]
            [clojure.test]
            [clojure.data]
            [clojure.string]))

;; This is a shim that inserts a pprint fn in the place that CIDER would create it if it's not found.
;; We shim instead of creating our own distinct function because babashka requires us
;; to refer to `cider.nrepl.pprint/pprint` if we want to use pretty printing.
;; https://github.com/Olical/conjure/issues/406
(when-not (find-ns 'cider.nrepl.pprint)
  (create-ns 'cider.nrepl.pprint)
  (intern 'cider.nrepl.pprint 'pprint
          (fn pprint [val w opts]
            (apply pp/write val
                   (mapcat identity (assoc opts :stream w))))))

(defn bounded-conj [queue x limit]
  (->> x (conj queue) (take limit)))

(def tap-queue-size 16)
(defonce tap-queue! (atom (list)))

;; Must be a defonce so that we always have the same function
;; reference to remove-tap and add-tap. If we make a new
;; function each time we'll end up adding more and more tap
;; functions.
(defonce enqueue-tap!
  (fn [x] (swap! tap-queue! bounded-conj x tap-queue-size)))

;; No setup for older Clojure versions.
(when (resolve 'add-tap)
  (remove-tap enqueue-tap!)
  (add-tap enqueue-tap!))

(defn dump-tap-queue! []
  (reverse (first (reset-vals! tap-queue! (list)))))

(when true
  (defmethod clojure.test/report :fail [m]
    (clojure.test/with-test-out
      (clojure.test/inc-report-counter :fail)
      (println \"\\nFAIL in\" (clojure.test/testing-vars-str m))
      (when (seq clojure.test/*testing-contexts*) (println (clojure.test/testing-contexts-str)))
      (when-let [message (:message m)] (println message))
      (print \"expected:\" (with-out-str (prn (:expected m))))
      (print \"  actual:\" (with-out-str (prn (:actual m))))
      (when (and (seq? (:actual m))
                 (= #'clojure.core/not (resolve (first (:actual m))))
                 (seq? (second (:actual m)))
                 (= #'clojure.core/= (resolve (first (second (:actual m)))))
                 (= 3 (count (second (:actual m)))))
        (let [[missing extra _] (clojure.data/diff (second (second (:actual m))) (last (second (:actual m))))
              missing-str (with-out-str (pp/pprint missing))
              missing-lines (clojure.string/split-lines missing-str)
              extra-str (with-out-str (pp/pprint extra))
              extra-lines (clojure.string/split-lines extra-str)]
          (when (some? missing) (doseq [m missing-lines] (println \"- \" m)))
          (when (some? extra) (doseq [e extra-lines] (println \"+ \" e))))))))

(in-ns initial-ns)
"
 :id
 "8706a094-ec9e-4f7e-8d1f-ae1689e49563"
 :op
 "eval"}
; debug: receive
{:id "de12c21a-448b-408e-ae68-3c1601277f4d"
 :session "4e501af3-49ac-47ac-9384-1df4d7e829f8"
 :sessions ["2bd00120-151e-48dd-a416-aa96e3ee0573"]
 :status ["done"]}
; debug: with-sessions id for enrichment
"2bd00120-151e-48dd-a416-aa96e3ee0573"
; debug: send
{:code "#?(:clj 'clj :cljs 'cljs :cljr 'cljr :default 'unknown)"
 :id "9a44e78b-8e44-4302-9a75-7bb40451feb4"
 :op "eval"
 :session "2bd00120-151e-48dd-a416-aa96e3ee0573"}
; debug: receive
{:aux
 {:cider-version
  {:incremental 0 :major 0 :minor 58 :qualifier {} :version-string "0.58.0"}
  :current-ns
  "user"}
 :id
 "aa3543a3-0b0e-4c6c-8e70-ce1011a65aa8"
 :ops
 {:add-middleware {}
  :analyze-last-stacktrace {}
  :apropos {}
  :cider-version {}
  :cider.clj-reload/reload {}
  :cider.clj-reload/reload-all {}
  :cider.clj-reload/reload-clear {}
  :cider/get-state {}
  :cider/log-add-appender {}
  :cider/log-add-consumer {}
  :cider/log-clear-appender {}
  :cider/log-exceptions {}
  :cider/log-format-event {}
  :cider/log-frameworks {}
  :cider/log-inspect-event {}
  :cider/log-levels {}
  :cider/log-loggers {}
  :cider/log-remove-appender {}
  :cider/log-remove-consumer {}
  :cider/log-search {}
  :cider/log-threads {}
  :cider/log-update-appender {}
  :cider/log-update-consumer {}
  :cider/profile-clear {}
  :cider/profile-summary {}
  :cider/profile-toggle-ns {}
  :cider/profile-toggle-var {}
  :classpath {}
  :clojuredocs-lookup {}
  :clojuredocs-refresh-cache {}
  :clone {}
  :close {}
  :complete {}
  :complete-doc {}
  :complete-flush-caches {}
  :completions {}
  :content-type {}
  :debug-input {}
  :debug-instrumented-defs {}
  :debug-middleware {}
  :describe {}
  :eldoc {}
  :eldoc-datomic-query {}
  :eval {}
  :fn-deps {}
  :fn-refs {}
  :format-code {}
  :format-edn {}
  :info {}
  :init-debugger {}
  :inspect-clear {}
  :inspect-def-current-value {}
  :inspect-display-analytics {}
  :inspect-last-exception {}
  :inspect-next-page {}
  :inspect-next-sibling {}
  :inspect-pop {}
  :inspect-prev-page {}
  :inspect-previous-sibling {}
  :inspect-print-current-value {}
  :inspect-push {}
  :inspect-refresh {}
  :inspect-set-max-atom-length {}
  :inspect-set-max-coll-size {}
  :inspect-set-max-nested-depth {}
  :inspect-set-page-size {}
  :inspect-tap-current-value {}
  :inspect-tap-indexed {}
  :inspect-toggle-pretty-print {}
  :inspect-toggle-view-mode {}
  :interrupt {}
  :load-file {}
  :lookup {}
  :ls-middleware {}
  :ls-sessions {}
  :macroexpand {}
  :ns-aliases {}
  :ns-list {}
  :ns-list-vars-by-name {}
  :ns-load-all {}
  :ns-path {}
  :ns-vars {}
  :ns-vars-with-meta {}
  :out-subscribe {}
  :out-unsubscribe {}
  :refresh {}
  :refresh-all {}
  :refresh-clear {}
  :resource {}
  :resources-list {}
  :retest {}
  :slurp {}
  :spec-example {}
  :spec-form {}
  :spec-list {}
  :stacktrace {}
  :stdin {}
  :swap-middleware {}
  :test {}
  :test-all {}
  :test-stacktrace {}
  :test-var-query {}
  :toggle-trace-ns {}
  :toggle-trace-var {}
  :undef {}
  :undef-all {}}
 :session
 "4d5dba0a-3e3d-4f5a-ace9-33930618f3b1"
 :status
 ["done"]
 :versions
 {:clojure {:incremental 4 :major 1 :minor 12 :version-string "1.12.4"}
  :java {:major 21 :version-string "21.0.9"}
  :nrepl {:incremental 0 :major 1 :minor 4 :version-string "1.4.0"}}}
; debug: receive
{:id "8706a094-ec9e-4f7e-8d1f-ae1689e49563"
 :ns "user"
 :session "06b828a4-5f3a-4a88-bfdd-696a9a881789"
 :value "#namespace[conjure.internal]"}
; debug: receive
{:id "8706a094-ec9e-4f7e-8d1f-ae1689e49563"
 :ns "user"
 :session "06b828a4-5f3a-4a88-bfdd-696a9a881789"
 :value "#'conjure.internal/initial-ns"}
; debug: receive
{:id "8706a094-ec9e-4f7e-8d1f-ae1689e49563"
 :ns "conjure.internal"
 :session "06b828a4-5f3a-4a88-bfdd-696a9a881789"
 :value "nil"}
; debug: receive
{:id "8706a094-ec9e-4f7e-8d1f-ae1689e49563"
 :ns "conjure.internal"
 :session "06b828a4-5f3a-4a88-bfdd-696a9a881789"
 :value "nil"}
; debug: receive
{:id "8706a094-ec9e-4f7e-8d1f-ae1689e49563"
 :ns "conjure.internal"
 :session "06b828a4-5f3a-4a88-bfdd-696a9a881789"
 :value "#'conjure.internal/bounded-conj"}
; debug: receive
{:id "8706a094-ec9e-4f7e-8d1f-ae1689e49563"
 :ns "conjure.internal"
 :session "06b828a4-5f3a-4a88-bfdd-696a9a881789"
 :value "#'conjure.internal/tap-queue-size"}
; debug: receive
{:id "8706a094-ec9e-4f7e-8d1f-ae1689e49563"
 :ns "conjure.internal"
 :session "06b828a4-5f3a-4a88-bfdd-696a9a881789"
 :value "nil"}
; debug: receive
{:id "8706a094-ec9e-4f7e-8d1f-ae1689e49563"
 :ns "conjure.internal"
 :session "06b828a4-5f3a-4a88-bfdd-696a9a881789"
 :value "nil"}
; debug: receive
{:id "8706a094-ec9e-4f7e-8d1f-ae1689e49563"
 :ns "conjure.internal"
 :session "06b828a4-5f3a-4a88-bfdd-696a9a881789"
 :value "nil"}
; debug: receive
{:id "8706a094-ec9e-4f7e-8d1f-ae1689e49563"
 :ns "conjure.internal"
 :session "06b828a4-5f3a-4a88-bfdd-696a9a881789"
 :value "#'conjure.internal/dump-tap-queue!"}
; debug: receive
{:id "8706a094-ec9e-4f7e-8d1f-ae1689e49563"
 :ns "conjure.internal"
 :session "06b828a4-5f3a-4a88-bfdd-696a9a881789"
 :value "#multifn[report 0x6348be48]"}
; debug: receive
{:id "8706a094-ec9e-4f7e-8d1f-ae1689e49563"
 :ns "user"
 :session "06b828a4-5f3a-4a88-bfdd-696a9a881789"
 :value "#namespace[user]"}
; debug: receive
{:id "8706a094-ec9e-4f7e-8d1f-ae1689e49563"
 :session "06b828a4-5f3a-4a88-bfdd-696a9a881789"
 :status ["done"]}
; debug: receive
{:changed-namespaces
 {:clojure-through-code.01-basics
  {:aliases {} :interns {}}
  :clojure.core
  {:aliases
   {}
   :interns
   {:* {:fn "true"}
    "*'" {:fn "true"}
    :*1 {}
    :*2 {}
    :*3 {}
    :*agent* {}
    :*allow-unresolved-vars* {}
    :*assert* {}
    :*clojure-version* {}
    :*command-line-args* {}
    :*compile-files* {}
    :*compile-path* {}
    :*compiler-options* {}
    :*data-readers* {}
    :*default-data-reader-fn* {}
    :*e {}
    :*err* {}
    :*file* {}
    :*flush-on-newline* {}
    :*fn-loader* {}
    :*in* {}
    :*loaded-libs* {}
    :*loading-verbosely* {}
    :*math-context* {}
    :*ns* {}
    :*out* {}
    :*pending-paths* {}
    :*print-dup* {}
    :*print-length* {}
    :*print-level* {}
    :*print-meta* {}
    :*print-namespace-maps* {}
    :*print-readably* {}
    :*read-eval* {}
    :*reader-resolver* {}
    :*repl* {}
    :*source-path* {}
    :*suppress-read* {}
    :*unchecked-math* {}
    :*use-context-classloader* {}
    :*verbose-defrecords* {}
    :*warn-on-reflection* {}
    :+ {:fn "true"}
    "+'" {:fn "true"}
    :- {:fn "true"}
    "-'" {:fn "true"}
    :-> {:macro "true"}
    :->> {:macro "true"}
    :->ArrayChunk {:fn "true"}
    :->Eduction {:fn "true"}
    :->Vec {:fn "true"}
    :->VecNode {:fn "true"}
    :->VecSeq {:fn "true"}
    :-cache-protocol-fn {:fn "true"}
    :-reset-methods {:fn "true"}
    :.. {:macro "true"}
    :/ {:fn "true"}
    :< {:fn "true"}
    :<= {:fn "true"}
    := {:fn "true"}
    :== {:fn "true"}
    :> {:fn "true"}
    :>0? {:fn "true"}
    :>1? {:fn "true"}
    :>= {:fn "true"}
    :EMPTY-NODE {}
    :Inst {}
    :NaN? {:fn "true"}
    :PrintWriter-on {:fn "true"}
    :StackTraceElement->vec {:fn "true"}
    :Throwable->map {:fn "true"}
    :abs {:fn "true"}
    :accessor {:fn "true"}
    :aclone {:fn "true"}
    :add-annotation {:fn "true"}
    :add-annotations {:fn "true"}
    :add-classpath {:deprecated "\"1.1\"" :fn "true"}
    :add-doc-and-meta {:macro "true"}
    :add-tap {:fn "true"}
    :add-watch {:fn "true"}
    :agent {:fn "true"}
    :agent-error {:fn "true"}
    :agent-errors {:deprecated "\"1.2\"" :fn "true"}
    :aget {:fn "true"}
    :alength {:fn "true"}
    :alias {:fn "true"}
    :all-ns {:fn "true"}
    :alter {:fn "true"}
    :alter-meta! {:fn "true"}
    :alter-var-root {:fn "true"}
    :amap {:macro "true"}
    :ams {}
    :ams-check {:macro "true"}
    :ancestors {:fn "true"}
    :and {:macro "true"}
    :any? {:fn "true"}
    :apply {:fn "true"}
    :areduce {:macro "true"}
    :array {:fn "true"}
    :array-map {:fn "true"}
    :as-> {:macro "true"}
    :aset {:fn "true"}
    :aset-boolean {:fn "true"}
    :aset-byte {:fn "true"}
    :aset-char {:fn "true"}
    :aset-double {:fn "true"}
    :aset-float {:fn "true"}
    :aset-int {:fn "true"}
    :aset-long {:fn "true"}
    :aset-short {:fn "true"}
    :asm-type {:fn "true"}
    :assert {:macro "true"}
    :assert-args {:macro "true"}
    :assert-same-protocol {:fn "true"}
    :assert-valid-fdecl {:fn "true"}
    :assoc {:fn "true"}
    :assoc! {:fn "true"}
    :assoc-in {:fn "true"}
    :associative? {:fn "true"}
    :atom {:fn "true"}
    :await {:fn "true"}
    :await-for {:fn "true"}
    :await1 {:fn "true"}
    :bases {:fn "true"}
    :bean {:fn "true"}
    :bigdec {:fn "true"}
    :bigint {:fn "true"}
    :biginteger {:fn "true"}
    :binding {:macro "true"}
    :binding-conveyor-fn {:fn "true"}
    :bit-and {:fn "true"}
    :bit-and-not {:fn "true"}
    :bit-clear {:fn "true"}
    :bit-flip {:fn "true"}
    :bit-not {:fn "true"}
    :bit-or {:fn "true"}
    :bit-set {:fn "true"}
    :bit-shift-left {:fn "true"}
    :bit-shift-right {:fn "true"}
    :bit-test {:fn "true"}
    :bit-xor {:fn "true"}
    :boolean {:fn "true"}
    :boolean-array {:fn "true"}
    :boolean? {:fn "true"}
    :booleans {:fn "true"}
    :bound-fn {:macro "true"}
    :bound-fn* {:fn "true"}
    :bound? {:fn "true"}
    :bounded-count {:fn "true"}
    :build-positional-factory {:fn "true"}
    :butlast {:fn "true"}
    :byte {:fn "true"}
    :byte-array {:fn "true"}
    :bytes {:fn "true"}
    :bytes? {:fn "true"}
    :case {:macro "true"}
    :case-map {:fn "true"}
    :cast {:fn "true"}
    :cat {:fn "true"}
    :char {:fn "true"}
    :char-array {:fn "true"}
    :char-escape-string {}
    :char-name-string {}
    :char? {:fn "true"}
    :chars {:fn "true"}
    :check-cyclic-dependency {:fn "true"}
    :check-valid-options {:fn "true"}
    :chunk {:fn "true"}
    :chunk-append {:fn "true"}
    :chunk-buffer {:fn "true"}
    :chunk-cons {:fn "true"}
    :chunk-first {:fn "true"}
    :chunk-next {:fn "true"}
    :chunk-rest {:fn "true"}
    :chunked-seq? {:fn "true"}
    :class {:fn "true"}
    :class? {:fn "true"}
    :clear-agent-errors {:deprecated "\"1.2\"" :fn "true"}
    :clojure-version {:fn "true"}
    :coll? {:fn "true"}
    :comment {:macro "true"}
    :commute {:fn "true"}
    :comp {:fn "true"}
    :comparator {:fn "true"}
    :compare {:fn "true"}
    :compare-and-set! {:fn "true"}
    :compile {:fn "true"}
    :complement {:fn "true"}
    :completing {:fn "true"}
    :concat {:fn "true"}
    :cond {:macro "true"}
    :cond-> {:macro "true"}
    :cond->> {:macro "true"}
    :condp {:macro "true"}
    :conj {:fn "true"}
    :conj! {:fn "true"}
    :cons {:fn "true"}
    :constantly {:fn "true"}
    :construct-proxy {:fn "true"}
    :contains? {:fn "true"}
    :count {:fn "true"}
    :counted? {:fn "true"}
    :create-ns {:fn "true"}
    :create-struct {:fn "true"}
    :ctor-sigs {:fn "true"}
    :cycle {:fn "true"}
    :data-reader-urls {:fn "true"}
    :data-reader-var {:fn "true"}
    :dec {:fn "true"}
    "dec'" {:fn "true"}
    :decimal? {:fn "true"}
    :declare {:macro "true"}
    :dedupe {:fn "true"}
    :def-aset {:macro "true"}
    :default-data-readers {}
    :definline {:macro "true"}
    :definterface {:macro "true"}
    :defmacro {:macro "true"}
    :defmethod {:macro "true"}
    :defmulti {:macro "true"}
    :defn {:macro "true"}
    :defn- {:macro "true"}
    :defonce {:macro "true"}
    :defprotocol {:macro "true"}
    :defrecord {:macro "true"}
    :defstruct {:macro "true"}
    :deftype {:macro "true"}
    :delay {:macro "true"}
    :delay? {:fn "true"}
    :deliver {:fn "true"}
    :denominator {:fn "true"}
    :deref {:fn "true"}
    :deref-as-map {:fn "true"}
    :deref-future {:fn "true"}
    :derive {:fn "true"}
    :descendants {:fn "true"}
    :descriptor {:fn "true"}
    :destructure {:fn "true"}
    :disj {:fn "true"}
    :disj! {:fn "true"}
    :dissoc {:fn "true"}
    :dissoc! {:fn "true"}
    :distinct {:fn "true"}
    :distinct? {:fn "true"}
    :doall {:fn "true"}
    :dorun {:fn "true"}
    :doseq {:macro "true"}
    :dosync {:macro "true"}
    :dotimes {:macro "true"}
    :doto {:macro "true"}
    :double {:fn "true"}
    :double-array {:fn "true"}
    :double? {:fn "true"}
    :doubles {:fn "true"}
    :drop {:fn "true"}
    :drop-last {:fn "true"}
    :drop-while {:fn "true"}
    :eduction {:fn "true"}
    :elide-top-frames {:fn "true"}
    :emit-defrecord {:fn "true"}
    :emit-deftype* {:fn "true"}
    :emit-extend-protocol {:fn "true"}
    :emit-extend-type {:fn "true"}
    :emit-hinted-impl {:fn "true"}
    :emit-impl {:fn "true"}
    :emit-method-builder {:fn "true"}
    :emit-protocol {:fn "true"}
    :empty {:fn "true"}
    :empty? {:fn "true"}
    :ensure {:fn "true"}
    :ensure-reduced {:fn "true"}
    :enumeration-seq {:fn "true"}
    :error-handler {:fn "true"}
    :error-mode {:fn "true"}
    :escape-class-name {:fn "true"}
    :eval {:fn "true"}
    :even? {:fn "true"}
    :every-pred {:fn "true"}
    :every? {:fn "true"}
    :ex-cause {:fn "true"}
    :ex-data {:fn "true"}
    :ex-info {:fn "true"}
    :ex-message {:fn "true"}
    :expand-method-impl-cache {:fn "true"}
    :extend {:fn "true"}
    :extend-protocol {:macro "true"}
    :extend-type {:macro "true"}
    :extenders {:fn "true"}
    :extends? {:fn "true"}
    :false? {:fn "true"}
    :ffirst {:fn "true"}
    :file-seq {:fn "true"}
    :filter {:fn "true"}
    :filter-key {:fn "true"}
    :filter-methods {:fn "true"}
    :filterv {:fn "true"}
    :find {:fn "true"}
    :find-field {:fn "true"}
    :find-keyword {:fn "true"}
    :find-ns {:fn "true"}
    :find-protocol-impl {:fn "true"}
    :find-protocol-method {:fn "true"}
    :find-var {:fn "true"}
    :first {:fn "true"}
    :fits-table? {:fn "true"}
    :flatten {:fn "true"}
    :float {:fn "true"}
    :float-array {:fn "true"}
    :float? {:fn "true"}
    :floats {:fn "true"}
    :flush {:fn "true"}
    :fn {:macro "true"}
    :fn? {:fn "true"}
    :fnext {:fn "true"}
    :fnil {:fn "true"}
    :for {:macro "true"}
    :force {:fn "true"}
    :format {:fn "true"}
    :frequencies {:fn "true"}
    :future {:macro "true"}
    :future-call {:fn "true"}
    :future-cancel {:fn "true"}
    :future-cancelled? {:fn "true"}
    :future-done? {:fn "true"}
    :future? {:fn "true"}
    :gen-class {:macro "true"}
    :gen-interface {:macro "true"}
    :generate-class {:fn "true"}
    :generate-interface {:fn "true"}
    :generate-proxy {:fn "true"}
    :gensym {:fn "true"}
    :get {:fn "true"}
    :get-in {:fn "true"}
    :get-method {:fn "true"}
    :get-proxy-class {:fn "true"}
    :get-super-and-interfaces {:fn "true"}
    :get-thread-bindings {:fn "true"}
    :get-validator {:fn "true"}
    :global-hierarchy {}
    :group-by {:fn "true"}
    :group-by-sig {:fn "true"}
    :halt-when {:fn "true"}
    :hash {:fn "true"}
    :hash-combine {:fn "true"}
    :hash-map {:fn "true"}
    :hash-ordered-coll {:fn "true"}
    :hash-set {:fn "true"}
    :hash-unordered-coll {:fn "true"}
    :ident? {:fn "true"}
    :identical? {:fn "true"}
    :identity {:fn "true"}
    :if-let {:macro "true"}
    :if-not {:macro "true"}
    :if-some {:macro "true"}
    :ifn? {:fn "true"}
    :imap-cons {:fn "true"}
    :implements? {:fn "true"}
    :import {:macro "true"}
    :in-ns {}
    :inc {:fn "true"}
    "inc'" {:fn "true"}
    :indexed? {:fn "true"}
    :infinite? {:fn "true"}
    :init-proxy {:fn "true"}
    :inst-ms {:fn "true"}
    :inst-ms* {:fn "true"}
    :inst? {:fn "true"}
    :instance? {:fn "true"}
    :int {:fn "true"}
    :int-array {:fn "true"}
    :int? {:fn "true"}
    :integer? {:fn "true"}
    :interleave {:fn "true"}
    :intern {:fn "true"}
    :interpose {:fn "true"}
    :into {:fn "true"}
    :into-array {:fn "true"}
    :into1 {:fn "true"}
    :ints {:fn "true"}
    :io! {:macro "true"}
    :is-annotation? {:fn "true"}
    :is-runtime-annotation? {:fn "true"}
    :isa? {:fn "true"}
    :iterate {:fn "true"}
    :iteration {:fn "true"}
    :iterator-seq {:fn "true"}
    :juxt {:fn "true"}
    :keep {:fn "true"}
    :keep-indexed {:fn "true"}
    :key {:fn "true"}
    :keys {:fn "true"}
    :keyword {:fn "true"}
    :keyword? {:fn "true"}
    :last {:fn "true"}
    :lazy-cat {:macro "true"}
    :lazy-seq {:macro "true"}
    :let {:macro "true"}
    :letfn {:macro "true"}
    :libspec? {:fn "true"}
    :lift-ns {:fn "true"}
    :line-seq {:fn "true"}
    :list {:fn "true"}
    :list* {:fn "true"}
    :list? {:fn "true"}
    :load {:fn "true"}
    :load-all {:fn "true"}
    :load-data-reader-file {:fn "true"}
    :load-data-readers {:fn "true"}
    :load-file {}
    :load-lib {:fn "true"}
    :load-libs {:fn "true"}
    :load-one {:fn "true"}
    :load-reader {:fn "true"}
    :load-string {:fn "true"}
    :loaded-libs {:fn "true"}
    :locking {:macro "true"}
    :long {:fn "true"}
    :long-array {:fn "true"}
    :longs {:fn "true"}
    :loop {:macro "true"}
    :macroexpand {:fn "true"}
    :macroexpand-1 {:fn "true"}
    :make-array {:fn "true"}
    :make-hierarchy {:fn "true"}
    :map {:fn "true"}
    :map-entry? {:fn "true"}
    :map-indexed {:fn "true"}
    :map? {:fn "true"}
    :mapcat {:fn "true"}
    :mapv {:fn "true"}
    :max {:fn "true"}
    :max-key {:fn "true"}
    :max-mask-bits {}
    :max-switch-table-size {}
    :maybe-destructured {:fn "true"}
    :maybe-min-hash {:fn "true"}
    :memfn {:macro "true"}
    :memoize {:fn "true"}
    :merge {:fn "true"}
    :merge-hash-collisions {:fn "true"}
    :merge-with {:fn "true"}
    :meta {:fn "true"}
    :method-sig {:fn "true"}
    :methods {:fn "true"}
    :min {:fn "true"}
    :min-key {:fn "true"}
    :mix-collection-hash {:fn "true"}
    :mk-am {:macro "true"}
    :mk-bound-fn {:fn "true"}
    :mod {:fn "true"}
    :most-specific {:fn "true"}
    :munge {:fn "true"}
    :name {:fn "true"}
    :namespace {:fn "true"}
    :namespace-munge {:fn "true"}
    :nary-inline {:fn "true"}
    :nat-int? {:fn "true"}
    :neg-int? {:fn "true"}
    :neg? {:fn "true"}
    :newline {:fn "true"}
    :next {:fn "true"}
    :nfirst {:fn "true"}
    :nil? {:fn "true"}
    :nnext {:fn "true"}
    :non-private-methods {:fn "true"}
    :normalize-slurp-opts {:fn "true"}
    :not {:fn "true"}
    :not-any? {:fn "true"}
    :not-empty {:fn "true"}
    :not-every? {:fn "true"}
    :not= {:fn "true"}
    :ns {:macro "true"}
    :ns-aliases {:fn "true"}
    :ns-imports {:fn "true"}
    :ns-interns {:fn "true"}
    :ns-map {:fn "true"}
    :ns-name {:fn "true"}
    :ns-publics {:fn "true"}
    :ns-refers {:fn "true"}
    :ns-resolve {:fn "true"}
    :ns-unalias {:fn "true"}
    :ns-unmap {:fn "true"}
    :nth {:fn "true"}
    :nthnext {:fn "true"}
    :nthrest {:fn "true"}
    :num {:fn "true"}
    :number? {:fn "true"}
    :numerator {:fn "true"}
    :object-array {:fn "true"}
    :odd? {:fn "true"}
    :or {:macro "true"}
    :overload-name {:fn "true"}
    :parents {:fn "true"}
    :parse-boolean {:fn "true"}
    :parse-double {:fn "true"}
    :parse-impls {:fn "true"}
    :parse-long {:fn "true"}
    :parse-opts {:fn "true"}
    :parse-opts+specs {:fn "true"}
    :parse-uuid {:fn "true"}
    :parsing-err {:fn "true"}
    :partial {:fn "true"}
    :partition {:fn "true"}
    :partition-all {:fn "true"}
    :partition-by {:fn "true"}
    :partitionv {:fn "true"}
    :partitionv-all {:fn "true"}
    :pcalls {:fn "true"}
    :peek {:fn "true"}
    :persistent! {:fn "true"}
    :pmap {:fn "true"}
    :pop {:fn "true"}
    :pop! {:fn "true"}
    :pop-thread-bindings {:fn "true"}
    :pos-int? {:fn "true"}
    :pos? {:fn "true"}
    :pr {:fn "true"}
    :pr-on {:fn "true"}
    :pr-str {:fn "true"}
    :pref {:fn "true"}
    :prefer-method {:fn "true"}
    :prefers {:fn "true"}
    :prep-hashes {:fn "true"}
    :prep-ints {:fn "true"}
    :prependss {:fn "true"}
    :preserving-reduced {:fn "true"}
    :prim->class {}
    :primitives-classnames {}
    :print {:fn "true"}
    :print-ctor {:fn "true"}
    :print-dup {:fn "true"}
    :print-initialized {}
    :print-map {:fn "true"}
    :print-meta {:fn "true"}
    :print-method {:fn "true"}
    :print-object {:fn "true"}
    :print-prefix-map {:fn "true"}
    :print-sequential {:fn "true"}
    :print-simple {:fn "true"}
    :print-str {:fn "true"}
    :print-tagged-object {:fn "true"}
    :print-throwable {:fn "true"}
    :printf {:fn "true"}
    :println {:fn "true"}
    :println-str {:fn "true"}
    :prn {:fn "true"}
    :prn-str {:fn "true"}
    :process-annotation {:fn "true"}
    :promise {:fn "true"}
    :protected-final-methods {:fn "true"}
    :protocol? {:fn "true"}
    :proxy {:macro "true"}
    :proxy-call-with-super {:fn "true"}
    :proxy-mappings {:fn "true"}
    :proxy-name {:fn "true"}
    :proxy-super {:macro "true"}
    :push-thread-bindings {:fn "true"}
    :pvalues {:macro "true"}
    :qualified-ident? {:fn "true"}
    :qualified-keyword? {:fn "true"}
    :qualified-symbol? {:fn "true"}
    :quot {:fn "true"}
    :rand {:fn "true"}
    :rand-int {:fn "true"}
    :rand-nth {:fn "true"}
    :random-sample {:fn "true"}
    :random-uuid {:fn "true"}
    :range {:fn "true"}
    :ratio? {:fn "true"}
    :rational? {:fn "true"}
    :rationalize {:fn "true"}
    :re-find {:fn "true"}
    :re-groups {:fn "true"}
    :re-matcher {:fn "true"}
    :re-matches {:fn "true"}
    :re-pattern {:fn "true"}
    :re-seq {:fn "true"}
    :read {:fn "true"}
    :read+string {:fn "true"}
    :read-line {:fn "true"}
    :read-string {:fn "true"}
    :reader-conditional {:fn "true"}
    :reader-conditional? {:fn "true"}
    :realized? {:fn "true"}
    :record? {:fn "true"}
    :reduce {:fn "true"}
    :reduce-kv {:fn "true"}
    :reduce1 {:fn "true"}
    :reduced {:fn "true"}
    :reduced? {:fn "true"}
    :reductions {:fn "true"}
    :ref {:fn "true"}
    :ref-history-count {:fn "true"}
    :ref-max-history {:fn "true"}
    :ref-min-history {:fn "true"}
    :ref-set {:fn "true"}
    :refer {:fn "true"}
    :refer-clojure {:macro "true"}
    :reify {:macro "true"}
    :release-pending-sends {:fn "true"}
    :rem {:fn "true"}
    :remove {:fn "true"}
    :remove-all-methods {:fn "true"}
    :remove-method {:fn "true"}
    :remove-ns {:fn "true"}
    :remove-tap {:fn "true"}
    :remove-watch {:fn "true"}
    :repeat {:fn "true"}
    :repeatedly {:fn "true"}
    :replace {:fn "true"}
    :replicate {:deprecated "\"1.3\"" :fn "true"}
    :require {:fn "true"}
    :requiring-resolve {:fn "true"}
    :reset! {:fn "true"}
    :reset-meta! {:fn "true"}
    :reset-vals! {:fn "true"}
    :resolve {:fn "true"}
    :rest {:fn "true"}
    :restart-agent {:fn "true"}
    :resultset-seq {:fn "true"}
    :reverse {:fn "true"}
    :reversible? {:fn "true"}
    :root-directory {:fn "true"}
    :root-resource {:fn "true"}
    :rseq {:fn "true"}
    :rsubseq {:fn "true"}
    :run! {:fn "true"}
    :satisfies? {:fn "true"}
    :second {:fn "true"}
    :select-keys {:fn "true"}
    :send {:fn "true"}
    :send-off {:fn "true"}
    :send-via {:fn "true"}
    :seq {:fn "true"}
    :seq-to-map-for-destructuring {:fn "true"}
    :seq? {:fn "true"}
    :seqable? {:fn "true"}
    :seque {:fn "true"}
    :sequence {:fn "true"}
    :sequential? {:fn "true"}
    :serialized-require {:fn "true"}
    :set {:fn "true"}
    :set-agent-send-executor! {:fn "true"}
    :set-agent-send-off-executor! {:fn "true"}
    :set-error-handler! {:fn "true"}
    :set-error-mode! {:fn "true"}
    :set-validator! {:fn "true"}
    :set? {:fn "true"}
    :setup-reference {:fn "true"}
    :shift-mask {:fn "true"}
    :short {:fn "true"}
    :short-array {:fn "true"}
    :shorts {:fn "true"}
    :shuffle {:fn "true"}
    :shutdown-agents {:fn "true"}
    :sigs {:fn "true"}
    :simple-ident? {:fn "true"}
    :simple-keyword? {:fn "true"}
    :simple-symbol? {:fn "true"}
    :slurp {:fn "true"}
    :some {:fn "true"}
    :some-> {:macro "true"}
    :some->> {:macro "true"}
    :some-fn {:fn "true"}
    :some? {:fn "true"}
    :sort {:fn "true"}
    :sort-by {:fn "true"}
    :sorted-map {:fn "true"}
    :sorted-map-by {:fn "true"}
    :sorted-set {:fn "true"}
    :sorted-set-by {:fn "true"}
    :sorted? {:fn "true"}
    :special-symbol? {:fn "true"}
    :spit {:fn "true"}
    :split-at {:fn "true"}
    :split-with {:fn "true"}
    :splitv-at {:fn "true"}
    :spread {:fn "true"}
    :str {:fn "true"}
    :stream-into! {:fn "true"}
    :stream-reduce! {:fn "true"}
    :stream-seq! {:fn "true"}
    :stream-transduce! {:fn "true"}
    :string? {:fn "true"}
    :strip-ns {:fn "true"}
    :struct {:fn "true"}
    :struct-map {:fn "true"}
    :subs {:fn "true"}
    :subseq {:fn "true"}
    :subvec {:fn "true"}
    :super-chain {:fn "true"}
    :supers {:fn "true"}
    :swap! {:fn "true"}
    :swap-vals! {:fn "true"}
    :symbol {:fn "true"}
    :symbol? {:fn "true"}
    :sync {:macro "true"}
    :system-newline {}
    :tagged-literal {:fn "true"}
    :tagged-literal? {:fn "true"}
    :take {:fn "true"}
    :take-last {:fn "true"}
    :take-nth {:fn "true"}
    :take-while {:fn "true"}
    :tap-loop {}
    :tap> {:fn "true"}
    :tapq {}
    :tapset {}
    :test {:fn "true"}
    :the-array-class {:fn "true"}
    :the-class {:fn "true"}
    :the-ns {:fn "true"}
    :thread-bound? {:fn "true"}
    :throw-if {:fn "true"}
    :time {:macro "true"}
    :to-array {:fn "true"}
    :to-array-2d {:fn "true"}
    :trampoline {:fn "true"}
    :transduce {:fn "true"}
    :transient {:fn "true"}
    :tree-seq {:fn "true"}
    :true? {:fn "true"}
    :type {:fn "true"}
    :unchecked-add {:fn "true"}
    :unchecked-add-int {:fn "true"}
    :unchecked-byte {:fn "true"}
    :unchecked-char {:fn "true"}
    :unchecked-dec {:fn "true"}
    :unchecked-dec-int {:fn "true"}
    :unchecked-divide-int {:fn "true"}
    :unchecked-double {:fn "true"}
    :unchecked-float {:fn "true"}
    :unchecked-inc {:fn "true"}
    :unchecked-inc-int {:fn "true"}
    :unchecked-int {:fn "true"}
    :unchecked-long {:fn "true"}
    :unchecked-multiply {:fn "true"}
    :unchecked-multiply-int {:fn "true"}
    :unchecked-negate {:fn "true"}
    :unchecked-negate-int {:fn "true"}
    :unchecked-remainder-int {:fn "true"}
    :unchecked-short {:fn "true"}
    :unchecked-subtract {:fn "true"}
    :unchecked-subtract-int {:fn "true"}
    :underive {:fn "true"}
    :unquote {}
    :unquote-splicing {}
    :unreduced {:fn "true"}
    :unsigned-bit-shift-right {:fn "true"}
    :update {:fn "true"}
    :update-in {:fn "true"}
    :update-keys {:fn "true"}
    :update-proxy {:fn "true"}
    :update-vals {:fn "true"}
    :uri? {:fn "true"}
    :use {:fn "true"}
    :uuid? {:fn "true"}
    :val {:fn "true"}
    :valid-java-method-name {:fn "true"}
    :validate-fields {:fn "true"}
    :validate-generate-class-options {:fn "true"}
    :vals {:fn "true"}
    :var-get {:fn "true"}
    :var-set {:fn "true"}
    :var? {:fn "true"}
    :vary-meta {:fn "true"}
    :vec {:fn "true"}
    :vector {:fn "true"}
    :vector-of {:fn "true"}
    :vector? {:fn "true"}
    :volatile! {:fn "true"}
    :volatile? {:fn "true"}
    :vreset! {:fn "true"}
    :vswap! {:macro "true"}
    :when {:macro "true"}
    :when-class {:macro "true"}
    :when-first {:macro "true"}
    :when-let {:macro "true"}
    :when-not {:macro "true"}
    :when-some {:macro "true"}
    :while {:macro "true"}
    :with-bindings {:macro "true"}
    :with-bindings* {:fn "true"}
    :with-in-str {:macro "true"}
    :with-loading-context {:macro "true"}
    :with-local-vars {:macro "true"}
    :with-meta {:fn "true"}
    :with-open {:macro "true"}
    :with-out-str {:macro "true"}
    :with-precision {:macro "true"}
    :with-redefs {:macro "true"}
    :with-redefs-fn {:fn "true"}
    :xml-seq {:fn "true"}
    :zero? {:fn "true"}
    :zipmap {:fn "true"}}}
  :clojure.pprint
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
    :tap-queue-size {}}}
  :user
  {:aliases {} :interns {}}}
 :id
 "8706a094-ec9e-4f7e-8d1f-ae1689e49563"
 :repl-type
 "clj"
 :session
 "06b828a4-5f3a-4a88-bfdd-696a9a881789"
 :status
 ["state"]}
; debug: receive
{:id "9a44e78b-8e44-4302-9a75-7bb40451feb4"
 :ns "clojure-through-code.01-basics"
 :session "2bd00120-151e-48dd-a416-aa96e3ee0573"
 :value "clj"}
; debug: receive
{:id "9a44e78b-8e44-4302-9a75-7bb40451feb4"
 :session "2bd00120-151e-48dd-a416-aa96e3ee0573"
 :status ["done"]}
; --------------------------------------------------------------------------------
; Assumed session: Samoyed (Clojure)
; debug: receive
{:changed-namespaces {}
 :id "9a44e78b-8e44-4302-9a75-7bb40451feb4"
 :repl-type "clj"
 :session "2bd00120-151e-48dd-a416-aa96e3ee0573"
 :status ["state"]}
