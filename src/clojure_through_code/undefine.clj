;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Removing evaluated vars from a running REPL
;;
;; Keep the REPL clean of stale vars due to code changes
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


(ns clojure-through-code.undefine)

(comment

;; Are any aliases currently defined
  (ns-aliases *ns*)
;; => {}

;; Require a neamespace
  (require '[practicalli.service :as service])

;; The service alias should now be in the current nammespace
  (ns-aliases *ns*)
;; => {service #namespace[practicalli.service]}

  (ns-unalias *ns* 'service)
;; => nil

  (ns-aliases *ns*)
;; => {}

  (require '[clojure.tools.namespace.repl :refer [refresh]])

  (refresh)
 ;; => #error {
 ;; :cause "Could not locate clojure/core/async__init.class, clojure/core/async.clj or clojure/core/async.cljc on classpath."
 ;; :via
 ;; [{:type clojure.lang.Compiler$CompilerException
 ;;   :message "Syntax error compiling at (clojure_through_code/core_async.clj:1:1)."
 ;;   :data #:clojure.error{:phase :compile-syntax-check, :line 1, :column 1, :source "clojure_through_code/core_async.clj"}
 ;;   :at [clojure.lang.Compiler load "Compiler.java" 7652]}
 ;;  {:type java.io.FileNotFoundException
 ;;   :message "Could not locate clojure/core/async__init.class, clojure/core/async.clj or clojure/core/async.cljc on classpath."
 ;;   :at [clojure.lang.RT load "RT.java" 462]}]
 ;; :trace
 ;; [[clojure.lang.RT load "RT.java" 462]
 ;;  [clojure.lang.RT load "RT.java" 424]
 ;;  [clojure.core$load$fn__6856 invoke "core.clj" 6115]
 ;;  [clojure.core$load invokeStatic "core.clj" 6114]
 ;;  [clojure.core$load doInvoke "core.clj" 6098]
 ;;  [clojure.lang.RestFn invoke "RestFn.java" 408]
 ;;  [clojure.core$load_one invokeStatic "core.clj" 5897]
 ;;  [clojure.core$load_one invoke "core.clj" 5892]
 ;;  [clojure.core$load_lib$fn__6796 invoke "core.clj" 5937]
 ;;  [clojure.core$load_lib invokeStatic "core.clj" 5936]
 ;;  [clojure.core$load_lib doInvoke "core.clj" 5917]
 ;;  [clojure.lang.RestFn applyTo "RestFn.java" 142]
 ;;  [clojure.core$apply invokeStatic "core.clj" 669]
 ;;  [clojure.core$load_libs invokeStatic "core.clj" 5974]
 ;;  [clojure.core$load_libs doInvoke "core.clj" 5958]
 ;;  [clojure.lang.RestFn applyTo "RestFn.java" 137]
 ;;  [clojure.core$apply invokeStatic "core.clj" 669]
 ;;  [clojure.core$require invokeStatic "core.clj" 5996]
 ;;  [clojure.core$require doInvoke "core.clj" 5996]
 ;;  [clojure.lang.RestFn invoke "RestFn.java" 408]
 ;;  [clojure_through_code.core_async$eval7357$loading__6737__auto____7358 invoke "core_async.clj" 3]
 ;;  [clojure_through_code.core_async$eval7357 invokeStatic "core_async.clj" 3]
 ;;  [clojure_through_code.core_async$eval7357 invoke "core_async.clj" 3]
 ;;  [clojure.lang.Compiler eval "Compiler.java" 7181]
 ;;  [clojure.lang.Compiler eval "Compiler.java" 7170]
 ;;  [clojure.lang.Compiler load "Compiler.java" 7640]
 ;;  [clojure.lang.RT loadResourceScript "RT.java" 381]
 ;;  [clojure.lang.RT loadResourceScript "RT.java" 372]
 ;;  [clojure.lang.RT load "RT.java" 459]
 ;;  [clojure.lang.RT load "RT.java" 424]
 ;;  [clojure.core$load$fn__6856 invoke "core.clj" 6115]
 ;;  [clojure.core$load invokeStatic "core.clj" 6114]
 ;;  [clojure.core$load doInvoke "core.clj" 6098]
 ;;  [clojure.lang.RestFn invoke "RestFn.java" 408]
 ;;  [clojure.core$load_one invokeStatic "core.clj" 5897]
 ;;  [clojure.core$load_one invoke "core.clj" 5892]
 ;;  [clojure.core$load_lib$fn__6796 invoke "core.clj" 5937]
 ;;  [clojure.core$load_lib invokeStatic "core.clj" 5936]
 ;;  [clojure.core$load_lib doInvoke "core.clj" 5917]
 ;;  [clojure.lang.RestFn applyTo "RestFn.java" 142]
 ;;  [clojure.core$apply invokeStatic "core.clj" 669]
 ;;  [clojure.core$load_libs invokeStatic "core.clj" 5974]
 ;;  [clojure.core$load_libs doInvoke "core.clj" 5958]
 ;;  [clojure.lang.RestFn applyTo "RestFn.java" 137]
 ;;  [clojure.core$apply invokeStatic "core.clj" 669]
 ;;  [clojure.core$require invokeStatic "core.clj" 5996]
 ;;  [clojure.core$require doInvoke "core.clj" 5996]
 ;;  [clojure.lang.RestFn invoke "RestFn.java" 421]
 ;;  [clojure.tools.namespace.reload$track_reload_one invokeStatic "reload.clj" 35]
 ;;  [clojure.tools.namespace.reload$track_reload_one invoke "reload.clj" 21]
 ;;  [clojure.tools.namespace.reload$track_reload invokeStatic "reload.clj" 52]
 ;;  [clojure.tools.namespace.reload$track_reload invoke "reload.clj" 43]
 ;;  [clojure.lang.AFn applyToHelper "AFn.java" 154]
 ;;  [clojure.lang.AFn applyTo "AFn.java" 144]
 ;;  [clojure.lang.Var alterRoot "Var.java" 308]
 ;;  [clojure.core$alter_var_root invokeStatic "core.clj" 5499]
 ;;  [clojure.core$alter_var_root doInvoke "core.clj" 5494]
 ;;  [clojure.lang.RestFn invoke "RestFn.java" 425]
 ;;  [clojure.tools.namespace.repl$do_refresh invokeStatic "repl.clj" 94]
 ;;  [clojure.tools.namespace.repl$do_refresh invoke "repl.clj" 83]
 ;;  [clojure.tools.namespace.repl$refresh invokeStatic "repl.clj" 145]
 ;;  [clojure.tools.namespace.repl$refresh doInvoke "repl.clj" 128]
 ;;  [clojure.lang.RestFn invoke "RestFn.java" 397]
 ;;  [clojure_through_code.undefine$eval7353 invokeStatic "NO_SOURCE_FILE" 32]
 ;;  [clojure_through_code.undefine$eval7353 invoke "NO_SOURCE_FILE" 32]
 ;;  [clojure.lang.Compiler eval "Compiler.java" 7181]
 ;;  [clojure.lang.Compiler eval "Compiler.java" 7136]
 ;;  [clojure.core$eval invokeStatic "core.clj" 3202]
 ;;  [clojure.core$eval invoke "core.clj" 3198]
 ;;  [nrepl.middleware.interruptible_eval$evaluate$fn__1245$fn__1246 invoke "interruptible_eval.clj" 87]
 ;;  [clojure.lang.AFn applyToHelper "AFn.java" 152]
 ;;  [clojure.lang.AFn applyTo "AFn.java" 144]
 ;;  [clojure.core$apply invokeStatic "core.clj" 667]
 ;;  [clojure.core$with_bindings_STAR_ invokeStatic "core.clj" 1977]
 ;;  [clojure.core$with_bindings_STAR_ doInvoke "core.clj" 1977]
 ;;  [clojure.lang.RestFn invoke "RestFn.java" 425]
 ;;  [nrepl.middleware.interruptible_eval$evaluate$fn__1245 invoke "interruptible_eval.clj" 87]
 ;;  [clojure.main$repl$read_eval_print__9110$fn__9113 invoke "main.clj" 437]
 ;;  [clojure.main$repl$read_eval_print__9110 invoke "main.clj" 437]
 ;;  [clojure.main$repl$fn__9119 invoke "main.clj" 458]
 ;;  [clojure.main$repl invokeStatic "main.clj" 458]
 ;;  [clojure.main$repl doInvoke "main.clj" 368]
 ;;  [clojure.lang.RestFn invoke "RestFn.java" 1523]
 ;;  [nrepl.middleware.interruptible_eval$evaluate invokeStatic "interruptible_eval.clj" 84]
 ;;  [nrepl.middleware.interruptible_eval$evaluate invoke "interruptible_eval.clj" 56]
 ;;  [nrepl.middleware.interruptible_eval$interruptible_eval$fn__1278$fn__1282 invoke "interruptible_eval.clj" 152]
 ;;  [clojure.lang.AFn run "AFn.java" 22]
 ;;  [nrepl.middleware.session$session_exec$main_loop__1348$fn__1352 invoke "session.clj" 218]
 ;;  [nrepl.middleware.session$session_exec$main_loop__1348 invoke "session.clj" 217]
 ;;  [clojure.lang.AFn run "AFn.java" 22]
 ;;  [java.lang.Thread run "Thread.java" 833]]}

  #_())

;; ;; You are having a problem loading a redefined namespace:
;; user=> (load "src/clj/com/tizra/layout_expander.clj")
;; #<IllegalStateException java.lang.IllegalStateException: Alias xml already exists in namespace com.tizra.layout-expander, aliasing com.tizra.xml-match>

;; ;; ns-unalias to the rescue!
;; user=> (ns-unalias (find-ns 'com.tizra.layout-expander) 'xml)
;; nil

;; user=> (load "src/clj/com/tizra/layout_expander.clj")
;; #'com.tizra.layout-expander/junk
