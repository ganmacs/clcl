(ns clcl.util.namespace
  (:require [bultitude.core :as b]))

(defn require-namespaces []
  (doseq [n (b/namespaces-on-classpath :prefix "clcl.action")]
    (require n :reload)))
