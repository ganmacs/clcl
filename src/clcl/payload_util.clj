(ns clcl.payload-util
  (:require [clojure.string :as str]))

(defn full-name [payload]
  (get-in payload ["repository" "full_name"]))

(defn repository [payload]
  (second (str/split (full-name payload) #"/")))

(defn user [payload]
  (first (str/split (full-name payload) #"/")))
