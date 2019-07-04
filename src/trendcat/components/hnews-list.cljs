(ns trendcat.components.hnews-list
  (:require [reagent.core :as r]
            [clojure.string :as str]
            [trendcat.db :refer [app-state get-item]]
            [trendcat.actions :refer [get-hnews-stories]]))


(defn score [item]
  [:div
   {:style {:min-width "70px"
            :display "flex"
            :align-items "center"
            :justify-content "center"
            :font-weight "700"
            :color "#f60"}}
   (:score item)])


(defn- item-comment [item]
  (if (:kids item)
    [:a
     {:href (str "https://news.ycombinator.com/item?id=" (:id item))
      :target "_blank"
      :style {:color (if (:dark-mode @app-state) "#818384" "#878a8c")
              :margin "0 15px"}}
     [:span (str (:descendants item) " comments")]]
    [:span
     {:style {:color (if (:dark-mode @app-state) "#818384" "#878a8c")
              :margin "0 15px"}}
     "No comments yet"]))


(defn list-item [item]
  [:div
   {:style {:padding "10px 0"
            :border-bottom (if (:dark-mode @app-state) "1px solid #4a4a4a" "1px solid #edeff1")}}
   [:div
    {:style {:display "flex"
             :flex-direction "row"}}
    [score item]
    [:div
     [:div
      {:style {:font-size "16px"
               :font-weight "700"
               :word-break "break-word"}}
      [:a
       {:href (:url item)
        :target "_blank"
        :style {:color (if (:dark-mode @app-state) "#d7dadc" "#363636")}}
       [:span (:title item)]]]
     [:div
      {:style {:font-size "13px"
               :font-weight "500"
               :color (if (:dark-mode @app-state) "#818384" "#878a8c")
               :display "inline-flex"
               :flex-direction "row"
               :align-items "center"}}
      [:span
       (:time item)]
      [item-comment item]
      [:span
       (str "by " (:by item))]
      ]]]])


(defn hnews-list []
  [:div
   {:style {:padding "20px"
            :margin "0 0 60px 0"
            :border-radius "8px"
            :background-color (if (:dark-mode @app-state) "#1a1a1a" "#ffffff")
            :overflow "hidden"
            :border-width "1px"
            :border-style "solid"
            :border-color (if (:dark-mode @app-state) "#363636" "#cccccc")}}
   (if-let [story-items (:hnews-story-items @app-state)]
     (if (empty? story-items)
       [:div.button.is-loading
        {:style {:display "flex"
                 :justify-content "center"
                 :border (if (:dark-mode @app-state) "#1a1a1a" "#ffffff")
                 :background-color (if (:dark-mode @app-state) "#1a1a1a" "#ffffff")}}
        "Loading"]
       (for [item (reverse (sort-by :rank story-items))]
         ^{:key (:id item)}
         [list-item item]))
     [:div.button.is-loading
      {:style {:display "flex"
               :justify-content "center"
               :border (if (:dark-mode @app-state) "#1a1a1a" "#ffffff")
               :background-color (if (:dark-mode @app-state) "#1a1a1a" "#ffffff")}}
      "Loading"])])
