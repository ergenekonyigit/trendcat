(ns trendcat.components.hnews-list
  (:require [reagent.core :as r]
            [clojure.string :as str]
            [trendcat.db :refer [app-state get-item]]
            [trendcat.actions :refer [get-hnews-stories]]))


(defn create-rank
  [number-of-votes hour]
  (/ (- number-of-votes 1) (Math/pow (+ hour 2) 1.8)))


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
      :style {:color "#878a8c"
              :margin "0 15px"}}
     [:span (str (:descendants item) " comments")]]
    [:span
     {:style {:color "#878a8c"
              :margin "0 15px"}}
     "No comments yet"]))


(defn list-item [item]
  [:div
   {:style {:padding "10px 0"
            :border-bottom "1px solid #edeff1"}}
   [:div
    {:style {:display "flex"
             :flex-direction "row"}}
    [score item]
    [:div
     [:div
      {:style {:font-weight "700"}}
      [:a
       {:href (:url item)
        :target "_blank"
        :style {:color "#363636"}}
       [:span (:title item)]]]
     [:div
      {:style {:font-size "13px"
               :font-weight "500"
               :color "#878a8c"
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
  (r/create-class
   {:component-did-mount #(get-hnews-stories (:stories @app-state))

    :reagent-render
    (fn []
      [:div
       {:style {:padding "20px"
                :margin "0 0 60px 0"
                :border-radius "8px"
                :background-color "#fff"
                :overflow "hidden"
                :border-width "1px"
                :border-style "solid"
                :border-color "#ccc"}}
       (if-let [story-items (:hnews-story-items @app-state)]
         (if (empty? story-items)
           [:div
            {:style {:text-align "center"}}
            "No Data"]
           (for [item (reverse (sort-by :rank story-items))]
             ^{:key (:id item)}
             [list-item item]))
         [:div
          {:style {:text-align "center"}}
          "Loading..."])])}))
