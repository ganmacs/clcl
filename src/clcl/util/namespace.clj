(ns clcl.util.namespace
  (:require [bultitude.core :as b]))

(defn namespaces-by-prefix [prefix]
  (b/namespaces-on-classpath :prefix prefix))

(defn fetch-symbols [name namesapces]
  (map #(ns-resolve % (symbol name)) namesapces))
