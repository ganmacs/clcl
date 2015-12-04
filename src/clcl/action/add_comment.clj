(ns clcl.action.add-comment
  (:require
   [clcl.entry :as entry]
   [clcl.event :as event]
   [clcl.payload-util :as putil]
   [tentacles.issues :as ti]))

(defn- issue-id [event]
  (get-in event ["issue" "number"]))

(defn add-comment [body event config]
  (ti/create-comment (putil/user event)
                     (putil/repository event)
                     (issue-id event)
                     body config))

(entry/register-handler event/issue-comment (partial add-comment ":santa:"))
