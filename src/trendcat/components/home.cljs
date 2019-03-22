(ns trendcat.components.home
  (:require [trendcat.db :refer [app-state]]
            [trendcat.components.list-view :refer [list-view]]
            [trendcat.actions :refer [get-repositories]]))


(defn language-select []
  [:div.select
   [:select
    {:on-change #(do
                   (swap! app-state assoc :lang (-> % .-target .-value))
                   (get-repositories {:since (:since @app-state)
                                      :lang (:lang @app-state)}))}
    (for [language (:languages @app-state)]
      ^{:key (:name language)}
      [:option
       {:value (:urlParam language)}
       (:name language)])]])


(defn since-select [since-name]
  (let [dropdown-items {"daily" "Today"
                        "weekly" "This Week"
                        "monthly" "This Month"}]
    [:div.dropdown.is-hoverable
     {:style {:margin "0 5px"}}
     [:div.dropdown-trigger
      [:button.button
       {:aria-haspopup "true"
        :aria-controls "dropdown-menu"
        :style {:width "110px"}}
       [:span
        since-name]]]
     [:div#dropdown-menu
      {:class "dropdown-menu"
       :role "menu"}
      [:div.dropdown-content
       (for [item dropdown-items]
         ^{:key (str (random-uuid))}
         [:a.dropdown-item
          {:on-click #(do
                        (swap! app-state assoc :since (first item))
                        (get-repositories {:since (:since @app-state)
                                           :lang (:lang @app-state)}))}
          (last item)])]]]))


(defn first-header []
  [:div
   {:style {:display "flex"
            :flex-direction "row"
            :justify-content "space-between"
            :align-items "center"}}
   [:div.title.is-2
    {:style {:color "#f34"}}
    "trendcat"]
   [:div
    {:style {:margin-bottom "24px"}}
    [:a.github-button
     {:href "https://github.com/ergenekonyigit/trendcat"
      :data-icon "octicon-star"
      :data-size "large"
      :data-show-count "true"
      :aria-label "Star ergenekonyigit/trendcat on GitHub"}
     "Star"]]])


(defn second-header [since-name]
  [:div.columns.is-desktop
   [:div
    {:style {:margin-bottom "10px"}}
    [:span.title "Trending repositories on GitHub"]
    [:span
     {:style {:margin-left "10px"}}
     since-name]]
   [:div
    {:style {:margin-left "auto"}}
    [language-select]
    [since-select since-name]]])


(defn home []
  (fn []
    (let [since-name (case (:since @app-state)
                       "daily" "Today"
                       "weekly" "This Week"
                       "monthly" "This Month")]
      [:div
       {:style {:background-color "#fafafa"}}
       [:div.container
        [:section.hero
         [:div.hero-body
          [:div.container
           {:style {:width "100%"}}
           [:div
            [first-header]
            [:div
             [second-header since-name]]]]]]
        [list-view]]])))
