(ns clcl.handler
  (:require [clcl.event :as event]
            [clojure.pprint :refer :all]
            [clcl.entry :as entry]
            [compojure.route :as route]
            [compojure.core :refer [defroutes POST]]
            [compojure.handler :as handler]
            [clcl.action.add-comment :refer :all]
            [ring.middleware.json :as middleware]))

(defn- github-event [headers]
  (get headers "x-github-event"))

(defn- root-handler [{:keys [body headers] :as request}]
  (let [e (github-event headers)]
    (when-let [event-name (event/parse e)]
      (entry/invoke event-name body)
      "ok")))

(defroutes app-routes
  (POST "/" _ root-handler)
  (route/not-found "Not Found"))

(def app
  (-> (handler/api app-routes)
      (middleware/wrap-json-body)
      (middleware/wrap-json-response)))
