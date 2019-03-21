(ns trendcat.prod
  (:require
    [trendcat.core :as core]))

;;ignore println statements in prod
(set! *print-fn* (fn [& _]))

(core/init!)
