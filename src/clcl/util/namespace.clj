(ns clcl.util.namespace
  (:require [bultitude.core :as b]
            [clcl.util.namespace :as ns]))

(defn find-namespaces [prefix]
  (b/namespaces-on-classpath :prefix prefix))

(defn resolve-var [name ns]
  (require ns)
  (ns-resolve ns (symbol name)))
