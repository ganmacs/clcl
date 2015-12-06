(ns clcl.util
  (:require [clojure.tools.logging :as log]))

(defn env [name] (System/getenv name))

(defn env-with-log [n]
  (or (env n)
      (log/error (format "%s is not found." n))))

(defn- safe-slurp [file-path]
  (try (slurp file-path)
       (catch java.io.FileNotFoundException e
         (log/error (format "%s is not found." file-path)))))

(defn with-oauth [config]
  (assoc config :oauth-token
         (env-with-log "GITHUB_ACCESS_TOKEN")))
