(ns clcl.handler.checklist
  (:require
   [clcl.event :as event]
   [clcl.action.add-comment :as c]))

;; for hook
(def event-name event/issue-comment)

(defn run [payload config]
  (c/add-comment ":santa:" payload config))
