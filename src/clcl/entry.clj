(ns clcl.entry
  (:require [clcl.util :as util]
            [clcl.util.namespace :as ns]))

(def ns-prefix "clcl.handler")

(defonce entries (atom []))

(defn reset-entries! []
  (reset! entries []))

(defn load-entries! []
  (reset-entries!)
  (let [nss (ns/namespaces-by-prefix ns-prefix)
        es (map (fn [e r] {:event e :body r})
                (ns/fetch-symbols "event-name" nss)
                (ns/fetch-symbols "run" nss))]
    (reset! entries es)))

(defn- filter-entries-by [event-name]
  (filter
   (fn [{:keys [event]}] (= (deref event) event-name))
   @entries))

(defn invoke [event payload]
  (doseq [e (map :body (filter-entries-by event))]
    (e payload (util/with-oauth {}))))
