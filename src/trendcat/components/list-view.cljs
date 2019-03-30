(ns trendcat.components.list-view
  (:require [reagent.core :as r]
            [trendcat.db :refer [app-state get-item]]
            [trendcat.actions :refer [get-repositories]]))


(defn repo-language [repo]
  [:span
   {:style {:display "inline-flex"
            :flex-direction "row"
            :align-items "center"
            :font-size "13px"
            :margin-right "30px"
            :color "#212529"}}
   [:span
    {:style {:background-color (:languageColor repo)
             :border-radius "50%"
             :display "inline-block"
             :height "12px"
             :position "relative"
             :width "12px"
             :margin-right "4px"}}]
   [:span
    (:language repo)]])


(defn stargazers [repo]
  [:a
   {:href (str (:url repo) "/stargazers")
    :target "_blank"
    :style {:display "inline-flex"
            :flex-direction "row"
            :align-items "center"
            :font-size "13px"
            :font-weight "500"
            :color "#6c757d"}}
   [:svg
    {:view-box "0 0 14 16" :version "1.1" :width "14" :height "16" :role "img"}
    [:path
     {:fill "#6c757d" :fill-rule "evenodd" :d "M14 6l-4.9-.64L7 1 4.9 5.36 0
6l3.6 3.26L2.67 14 7 11.67 11.33 14l-.93-4.74L14 6z"}]]
   \u00A0 (:stars repo)])


(defn network-members [repo]
  [:a
   {:href (str (:url repo) "/network/members")
    :target "_blank"
    :style {:display "inline-flex"
            :flex-direction "row"
            :align-items "center"
            :margin "0 15px"
            :font-size "13px"
            :font-weight "500"
            :color "#6c757d"}}
   [:svg
    {:view-box "0 0 10 16" :version "1.1" :width "10" :height "16" :role "img"}
    [:path
     {:fill "#6c757d" :fill-rule "evenodd" :d "M8 1a1.993 1.993 0 0 0-1 3.72V6L5
 8 3 6V4.72A1.993 1.993 0 0 0 2 1a1.993 1.993 0 0 0-1 3.72V6.5l3 3v1.78A1.993 1.993 0
 0 0 5 15a1.993 1.993 0 0 0 1-3.72V9.5l3-3V4.72A1.993 1.993 0 0 0 8 1zM2 4.2C1.34 4.2.8
 3.65.8 3c0-.65.55-1.2 1.2-1.2.65 0 1.2.55 1.2 1.2 0 .65-.55 1.2-1.2 1.2zm3 10c-.66
 0-1.2-.55-1.2-1.2 0-.65.55-1.2 1.2-1.2.65 0 1.2.55 1.2 1.2 0 .65-.55 1.2-1.2 1.2zm3-10c-.66
 0-1.2-.55-1.2-1.2 0-.65.55-1.2 1.2-1.2.65 0 1.2.55 1.2 1.2 0 .65-.55 1.2-1.2 1.2z"}]]
   \u00A0 (:forks repo)])


(defn current-period-stars [repo]
  [:span
   {:style {:display "inline-flex"
            :flex-direction "row"
            :align-items "center"
            :font-size "13px"
            :font-weight "500"
            :color "#6c757d"}}
   [:svg
    {:view-box "0 0 1024 1024", :version "1.1", :width "18", :height "18"}
    [:path
     {:fill "#f5c55f", :d "M 757.39087,486.83814 568.21799,459.22035 483.55951,287.37628
 c -7.58135,-15.34322 -22.38305,-23.10509 -37.36525,-23.10509 -14.8017,0 -29.42288,7.58136
 -37.00424,23.10509 l -84.65847,171.84407 -189.17288,27.61779 c -33.9356,4.87373
 -47.654239,46.7517 -22.92458,70.75932 L 249.44002,691.17374 216.9485,879.9856 c
 -4.69322,26.89576 16.78729,48.55678 40.79491,48.55678 6.3178,0 12.99661,-1.44407
 19.31441,-4.87373 l 169.31695,-89.17119 169.31695,89.17119 c 6.31779,3.24915 12.8161,4.87373
 19.1339,4.87373 24.00762,0 45.48813,-21.48051 40.97542,-48.55678 L 643.49002,691.17374
 780.49595,557.59746 c 24.54916,-24.00762 10.83051,-65.88559 -23.10508,-70.75932 z
 m -174.5517,142.42119 -32.67203,31.76949 7.76187,44.94661 16.96779,98.37712
 -88.26864,-46.39068 -40.25339,-21.3 -40.4339,21.3 -88.26864,46.39068
 16.96779,-98.37712 7.76187,-44.94661 -32.67204,-31.76949 -71.66186,-69.85678
 98.91864,-14.44068 45.12712,-6.4983 20.21695,-40.79492 44.04407,-89.5322 44.04407,89.5322
 20.21695,40.79492 45.12711,6.4983 98.91865,14.44068 z M 706.30697,293.15255 754.50273,196.94153
 850.71375,148.74577 754.50273,100.55 706.30697,4.338983 658.11121,100.55 l -96.21102,48.19577
 96.21102,48.19576 z m 259.9322,57.76271 -28.88135,-57.76271 -28.88136,57.76271
 -57.76271,28.88136 57.76271,28.88135 28.88136,57.76271 28.88135,-57.76271 57.76273,-28.88135 z"}]]
   \u00A0 (:currentPeriodStars repo)])


(defn repo-built-by [repo]
  [:div
   {:style {:font-size "13px"
            :color "#6c757d"
            :display "inline-flex"
            :flex-direction "row"
            :align-items "center"}}
   [:span
    {:style {:margin-right "4px"}}
    "Built by"]
   (for [user (:builtBy repo)]
     ^{:key (:avatar user)}
     [:span
      [:a
       {:href (:href user)
        :target "_blank"}
       [:img
        {:src (:avatar user)
         :alt (:username user)
         :style {:width "20px"
                 :height "20px"
                 :display "inline-block"
                 :border-radius "50%"
                 :vertical-align "middle"
                 :line-height "1"
                 :margin "0 2px"}}]]])])


(defn list-item-header [repo]
  [:div
   {:style {:margin-bottom "15px"}}
   [:div
    {:style {:font-size "20px"
             :font-weight "700"}}
    [:a
     {:href (:url repo)
      :target "_blank"
      :style {:color "#0366d6"}}
     [:span
      {:style {:font-weight "400"}}
      (:author repo)]
     " / "
     [:span
      (:name repo)]]]
   [repo-built-by repo]])


(defn list-item-body [repo]
  [:div
   {:style {:margin-bottom "15px"
            :color "#000"}}
   [:p
    (:description repo)]])


(defn list-item-footer [repo]
  [:div
   {:style {:margin-bottom "15px"
            :display "inline-flex"
            :flex-direction "row"
            :align-items "center"}}
   (when (:language repo)
     [repo-language repo])
   [stargazers repo]
   [network-members repo]
   [current-period-stars repo]])


(defn list-item [repo]
  [:div
   {:style {:margin-bottom "20px"
            :border-bottom "1px solid #e8e8e8"}}
   [list-item-header repo]
   [list-item-body repo]
   [list-item-footer repo]])


(defn list-view []
  (r/create-class
   {:component-did-mount #(get-repositories {:lang (:lang @app-state)
                                             :since (:since @app-state)})
    :reagent-render
    (fn []
      [:div
       {:style {:padding "35px"
                :margin "0 0 60px 0"
                :border-radius "8px"
                :background-color "#fff"
                :box-shadow "-5px 10px 60px -13px rgba(0,0,0,.2)"}}
       (if-let [trends (:trends @app-state)]
         (if (empty? trends)
           [:div
            {:style {:color "#000"
                     :text-align "center"}}
            "No Data"]
           (for [repo trends]
             ^{:key (:url repo)}
             [list-item repo]))
         [:div
          {:style {:color "#000"
                   :text-align "center"}}
          "Loading..."])])}))
