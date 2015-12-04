(ns clcl.server
  (:require [clcl.event :as event]
            [clojure.pprint :refer :all]
            [clcl.entry :as entry]
            [compojure.route :as route]
            [compojure.core :refer [defroutes POST]]
            [compojure.handler :as handler]
            [clcl.util.namespace :as ns]
            [ring.adapter.jetty :as j]
            [ring.middleware.defaults :as d]
            [ring.middleware.json :as middleware])
  (:gen-class))

(defn- github-event [headers]
  (get headers "x-github-event"))

(defn- root-handler [{:keys [body headers] :as request}]
  (let [e (github-event headers)]
    (when-let [event-name (event/parse e)]
      (println ";alsjdkfaksdjfakdfj")
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
    (entry/reset-entries!)
    (ns/require-namespaces)
    (reset! server (j/run-jetty #'app {:port 3000 :join? false}))))

(defn stop-server []
  (when @server
    (.stop @server)
    (reset! server nil)))

(defn -main [& args]
  (start-server))
