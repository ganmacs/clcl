(ns clcl.server
  (:gen-class)
  (:require [clojure.pprint :refer :all] ; for dev
            [clcl.event :as event]
            [clcl.entry :as entry]
            [compojure.route :as route]
            [compojure.core :refer [defroutes POST]]
            [compojure.handler :as handler]
            [ring.adapter.jetty :as server]
            [ring.middleware.defaults :as d]
            [ring.middleware.json :as middleware]))

(defn- root-handler [{:keys [body headers] :as request}]
  (let [e (get headers "x-github-event")]
    (when-let [event-name (event/parse e)]
      (entry/invoke event-name body))
    "ok"))

(defonce server (atom nil))

(defroutes app-routes
  (POST "/" _ root-handler)
  (route/not-found "Not Found"))

(def app
  (-> app-routes
      (middleware/wrap-json-body)
      (middleware/wrap-json-response)
      (d/wrap-defaults d/api-defaults)))

(defn start-server []
  (when-not @server
    (entry/load-entries!)
    (reset! server  (server/run-jetty #'app {:port 3000 :join? false}))))

(defn stop-server []
  (when @server
    (.stop @server)
    (reset! server nil)))

(defn -main [& args]
  (start-server))
