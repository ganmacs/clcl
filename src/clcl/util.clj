(ns clcl.util)

(defn env [name] (System/getenv name))

(defn with-oauth [config]
  (assoc config :oauth-token (env "GITHUB_ACCESS_TOKEN")))
