
(ns trendcat.core
  (:require [reagent.core :as r]
            [trendcat.routes :refer [app-routes]]
            [trendcat.dispatcher :refer [current-page]]))

(defn mount-root []
  (r/render [current-page] (.getElementById js/document "app")))

(defn init! []
  (app-routes)
  (mount-root))
