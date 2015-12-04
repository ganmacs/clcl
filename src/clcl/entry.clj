(ns clcl.entry
  (:require [clcl.util :as util]))

(defonce entries (atom {}))

(defn reset-entries! []
  (reset! entries {}))

(defn- select-entries-by [event]
  (get @entries event))

(defn invoke [event payload]
  (doseq [entry (select-entries-by event)]
    (entry payload (util/with-oauth {}))))

(defn register-handler [event entry]
  (let [m (hash-map event [entry])]
    (swap! entries #(merge-with concat % m))
    @entries))
