(ns clcl.event)

(def push :push)
(def issues :issues)
(def pull-request :pull_request)
(def commit-comment :commit_comment)
(def issue-comment :commit_comment)
(def pull-request-review-comment :pull_request_review_comment)

(defn parse [event]
  (case event
    "push" push
    "issues" issues
    "pull_request" pull-request
    "commit_comment" commit-comment
    "issue_comment" issue-comment
    "pull_request_review_comment" pull-request-review-comment
    nil))
