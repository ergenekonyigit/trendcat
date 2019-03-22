(ns trendcat.dispatcher
  (:require [trendcat.db :refer [app-state]]
            [trendcat.components.home :refer [home]]))


(defmulti current-page #(@app-state :page))


(defmethod current-page :home []
  [home])


(defmethod current-page :default []
  [:div ])

