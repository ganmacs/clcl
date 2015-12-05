(ns clcl.entry
  (:require [clcl.util :as util]
            [clcl.util.namespace :as ns]
            [clojure.tools.logging :as log]))

(def ns-prefix "clcl.handler")

(defonce entries (atom []))

(defn reset-entries! []
  (reset! entries []))

(defn load-entries! []
  (reset-entries!)
  (let [nss (ns/find-namespaces ns-prefix)
        es (map (fn [ns] {:event (ns/resolve-var "event-name" ns)
                          :body  (ns/resolve-var "run" ns)})
                nss)]
    (reset! entries es)))

(defn- filter-entries-by [event-name]
  (filter
   (fn [{:keys [event]}] (= (deref event) event-name))
   @entries))

(defn invoke [entry payload]
  (doseq [event (filter-entries-by entry)]
    (let [b (:body event)
          e (:event event)]
      (log/info (format "Executed %s by %s event" b e))
      (b payload (util/with-oauth {})))))
