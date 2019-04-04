(ns trendcat.components.home
  (:require [trendcat.db :refer [app-state set-item! get-item]]
            [trendcat.components.github-list :refer [github-list]]
            [trendcat.components.hnews-list :refer [hnews-list]]
            [trendcat.actions :refer [get-github-trends get-hnews-stories]]))


(defn language-select []
  (let [lang-check #(when-not (= "All Languages" %) %)]
    [:div.select
     [:select
      {:default-value (:lang @app-state)
       :on-change #(do
                     (swap! app-state assoc :lang (-> % .-target .-value))
                     (set-item! "current-lang" (:lang @app-state))
                     (get-github-trends {:since (:since @app-state)
                                         :lang (:lang @app-state)}))}
      [:option
       {:value ""}
       "All Languages"]
      [:optgroup
       {:label "Popular"}
       (for [fav-language (:fav-languages @app-state)]
         ^{:key (:name fav-language)}
         [:option
          {:value (:urlParam fav-language)}
          (:name fav-language)])]
      [:optgroup
       {:label "Everything else"}
       (for [language (:languages @app-state)]
         ^{:key (:name language)}
         [:option
          {:value (:urlParam language)}
          (:name language)])]]]))


(defn github-since-select [since-name]
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
                        (set-item! "current-since" (:since @app-state))
                        (get-github-trends {:since (:since @app-state)
                                            :lang (:lang @app-state)}))}
          (second item)])]]]))


(defn hnews-select [story-name]
  (let [dropdown-items {"topstories" "Top"
                        "newstories" "New"
                        "showstories" "Show"}]
    [:div.dropdown.is-hoverable
     {:style {:margin "0 5px"}}
     [:div.dropdown-trigger
      [:button.button
       {:aria-haspopup "true"
        :aria-controls "dropdown-menu"
        :style {:width "110px"}}
       [:span
        story-name]]]
     [:div#dropdown-menu
      {:class "dropdown-menu"
       :role "menu"}
      [:div.dropdown-content
       (for [item dropdown-items]
         ^{:key (str (random-uuid))}
         [:a.dropdown-item
          {:on-click #(do
                        (swap! app-state assoc :stories (first item))
                        (set-item! "stories" (:stories @app-state))
                        (swap! app-state assoc :hnews-story-items [])
                        (get-hnews-stories (:stories @app-state)))}
          (second item)])]]]))


(defn github-button []
  [:a.button
   {:href "https://github.com/ergenekonyigit/trendcat"
    :target "_blank"}
   [:span
    {:style {:display "flex"
             :flex-direction "row"
             :align-items "center"}}
    [:svg
     {:style {:margin-right "5px"}
      :width "20px"
      :role "img",
      :view-box "0 0 24 24",
      :xmlns "http://www.w3.org/2000/svg"}
     [:path {:d "M12 .297c-6.63 0-12 5.373-12 12 0 5.303 3.438 9.8 8.205 11.385.6.113.82-.258.82-.577
0-.285-.01-1.04-.015-2.04-3.338.724-4.042-1.61-4.042-1.61C4.422 18.07 3.633 17.7 3.633
17.7c-1.087-.744.084-.729.084-.729 1.205.084 1.838 1.236 1.838 1.236 1.07 1.835 2.809
1.305 3.495.998.108-.776.417-1.305.76-1.605-2.665-.3-5.466-1.332-5.466-5.93 0-1.31.465-2.38
1.235-3.22-.135-.303-.54-1.523.105-3.176 0 0 1.005-.322 3.3 1.23.96-.267 1.98-.399 3-.405
1.02.006 2.04.138 3 .405 2.28-1.552 3.285-1.23 3.285-1.23.645 1.653.24 2.873.12 3.176.765.84
1.23 1.91 1.23 3.22 0 4.61-2.805 5.625-5.475 5.92.42.36.81 1.096.81 2.22 0 1.606-.015
2.896-.015 3.286 0 .315.21.69.825.57C20.565 22.092 24 17.592 24 12.297c0-6.627-5.373-12-12-12"}]]
    "Star"]])


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
    [github-button]]])


(defn github-header [since-name]
  [:div
   {:style {:display "flex"
            :flex-direction "row"
            :justify-content "space-between"}}
   [:div
    {:style {:margin-bottom "10px"}}
    [:span.title "GitHub"]]
   [:div
    {:style {:display "flex"
             :flex-direction "row"
             :justify-content "flex-end"
             :margin-bottom "10px"}}
    [language-select]
    [github-since-select since-name]]])


(defn hnews-header [story-name]
  [:div
   {:style {:display "flex"
            :flex-direction "row"
            :justify-content "space-between"}}
   [:div
    {:style {:margin-bottom "10px"}}
    [:span.title "Hacker News"]]
   [:div
    {:style {:margin-left "auto"
             :margin-bottom "10px"}}
    [hnews-select story-name]]])


(defn home []
  (fn []
    (let [since-name (case (:since @app-state)
                       "daily" "Today"
                       "weekly" "This Week"
                       "monthly" "This Month")
          story-name (case (:stories @app-state)
                       "topstories" "Top"
                       "newstories" "New"
                       "showstories" "Show")]
      [:div
       {:style {:background-color "#fafafa"}}
       [:div.container
        [:section.hero
         [:div.hero-body
          [first-header]]]
        [:div.columns
         [:div.column.is-half
          [github-header since-name]
          [github-list]]
         [:div.column.is-half
          [hnews-header story-name]
          [hnews-list]]]]])))
