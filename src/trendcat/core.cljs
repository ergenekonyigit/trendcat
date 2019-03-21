(ns trendcat.core
  (:require [reagent.core :as r]
            [trendcat.routes :refer [app-routes current-page]]))

(defn mount-root []
  (app-routes)
  (r/render [current-page] (.getElementById js/document "app")))

(defn init! []
  (mount-root))
