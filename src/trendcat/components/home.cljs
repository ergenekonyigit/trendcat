(ns trendcat.components.home
  (:require [trendcat.db :refer [app-state]]
            [trendcat.components.list-view :refer [list-view]]
            [trendcat.actions :refer [get-repositories]]))

(defn home []
  (fn []
    (let [since (:since @app-state)
          since-name #(cond
                        (= since "daily") "Today"
                        (= since "weekly") "This Week"
                        (= since "monthly") "This Month")
          dropdown-items {"daily" "Today"
                          "weekly" "This Week"
                          "monthly" "This Month"}]
      [:div {:style {:background-color "#fafafa"}}
       [:div.container
        [:section.hero
         [:div.hero-body
          [:div.container {:style {:width "100%"}}
           [:div
            [:div {:style {:display "flex"
                           :flex-direction "row"
                           :justify-content "space-between"
                           :align-items "center"}}
             [:div.title.is-2 {:style {:color "#f34"}}
              "trendcat"]
             [:div {:style {:margin-bottom "24px"}}
              [:a.github-button {:href "https://github.com/ergenekonyigit/trendcat"
                                 :data-size "large"
                                 :aria-label "Star ergenekonyigit/trendcat on GitHub"}                                
               "Github"]]]
            [:div ;; {:style {:display "flex"
             ;;          :flex-direction "row"
             ;;          :justify-content "space-between"}}
             [:div.columns.is-desktop
              [:div {:style {:margin-bottom "10px"}}
               [:span.title "Trending repositories on GitHub"]
               [:span {:style {:margin-left "10px"}}
                (since-name)]]
              [:div {:style {:margin-left "auto"}}
               [:div.select
                [:select {:on-change #(do
                                        (swap! app-state assoc :lang (-> % .-target .-value))
                                        (get-repositories {:since (:since @app-state)
                                                           :lang (:lang @app-state)}))}
                 (for [language (:languages @app-state)]
                   ^{:key (last (last language))}
                   [:option {:value (last (first language))}
                    (last (last language))])]]
               [:div.dropdown.is-hoverable {:style {:margin "0 5px"}}
                [:div.dropdown-trigger
                 [:button {:class "button"
                           :aria-haspopup "true"
                           :aria-controls "dropdown-menu"
                           :style {:width "110px"}}
                  [:span (since-name)]]]
                [:div#dropdown-menu {:class "dropdown-menu"
                                     :role "menu"}
                 [:div.dropdown-content
                  (for [item dropdown-items]
                    ^{:key (first item)}
                    [:a.dropdown-item {:on-click #(do
                                                    (swap! app-state assoc :since (first item))
                                                    (get-repositories {:since (:since @app-state)
                                                                       :lang (:lang @app-state)}))}
                     (last item)])]]]
               ]]]]]]]
         [list-view]]])))
