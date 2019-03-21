(ns trendcat.routes
  (:import goog.history.Html5History)
  (:require [goog.events :as events]
            [goog.history.EventType :as EventType]
            [secretary.core :as secretary :include-macros true]
            [trendcat.db :refer [app-state]]
            [trendcat.components.home :refer [home]]))

(defn hook-browser-navigation! []
  (doto (Html5History.)
    (events/listen
     EventType/NAVIGATE
     (fn [event]
       (secretary/dispatch! (.-token event))))
    (.setEnabled true)))


(defn app-routes []
  (secretary/set-config! :prefix "#")

  (secretary/defroute "/" []
    (swap! app-state assoc :page :home))

  (hook-browser-navigation!))


(defmulti current-page #(@app-state :page))

(defmethod current-page :home []
  [home])

(defmethod current-page :default []
  [:div ])

