(ns trendcat.components.home
  (:require [reagent.core :as r]
            [trendcat.db :refer [app-state set-item! get-item moon-toggle]]
            [trendcat.components.github-list :refer [github-list]]
            [trendcat.components.hnews-list :refer [hnews-list]]
            [trendcat.actions :refer [get-github-trends get-hnews-stories]]))


(defn language-select []
  [:div.select
   [:select
    {:style {:width "200px"
             :color (if (:dark-mode @app-state) "#878a8c" "#363636")
             :background-color (if (:dark-mode @app-state) "#1a1a1a" "#ffffff")
             :border-color (if (:dark-mode @app-state) "#363636" "#cccccc")}
     :default-value (:lang @app-state)
     :on-change #(do
                   (swap! app-state assoc :lang (-> % .-target .-value))
                   (set-item! "current-lang" (:lang @app-state))
                   (get-github-trends {:force? true
                                       :since (:since @app-state)
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
        (:name language)])]]])


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
        :style {:width "110px"
                :color (if (:dark-mode @app-state) "#878a8c" "#363636")
                :background-color (if (:dark-mode @app-state) "#1a1a1a" "#ffffff")
                :border-color (if (:dark-mode @app-state) "#363636" "#cccccc")}}
       [:span
        since-name]]]
     [:div#dropdown-menu
      {:class "dropdown-menu"
       :role "menu"}
      [:div.dropdown-content
       {:style {:background-color (if (:dark-mode @app-state) "#1a1a1a" "#ffffff")}}
       (doall
        (for [item dropdown-items]
          ^{:key (str (random-uuid))}
          [:a.dropdown-item
           {:style {:color (if (:dark-mode @app-state) "#d7dadc" "#363636")}
            :on-click #(do
                         (swap! app-state assoc :since (first item))
                         (set-item! "current-since" (:since @app-state))
                         (get-github-trends {:force? true
                                             :since (:since @app-state)
                                             :lang (:lang @app-state)}))}
           (second item)]))]]]))


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
        :style {:width "110px"
                :color (if (:dark-mode @app-state) "#878a8c" "#363636")
                :background-color (if (:dark-mode @app-state) "#1a1a1a" "#ffffff")
                :border-color (if (:dark-mode @app-state) "#363636" "#cccccc")}}
       [:span
        story-name]]]
     [:div#dropdown-menu
      {:class "dropdown-menu"
       :role "menu"}
      [:div.dropdown-content
       {:style {:background-color (if (:dark-mode @app-state) "#1a1a1a" "#ffffff")}}
       (doall
        (for [item dropdown-items]
          ^{:key (str (random-uuid))}
          [:a.dropdown-item
           {:style {:color (if (:dark-mode @app-state) "#d7dadc" "#363636")}
            :on-click #(do
                         (swap! app-state assoc :stories (first item))
                         (set-item! "stories" (:stories @app-state))
                         (swap! app-state assoc :hnews-story-items [])
                         (get-hnews-stories {:force? true
                                             :type (:stories @app-state)}))}
           (second item)]))]]]))


(defn github-button []
  [:a
   {:href "https://github.com/ergenekonyigit/trendcat"
    :target "_blank"}
   [:svg
    {:width "24px"
     :role "img"
     :view-box "0 0 24 24"
     :xmlns "http://www.w3.org/2000/svg"}
    [:path
     {:fill (if (:dark-mode @app-state) "#d7dadc" "#363636")
      :d "M12 .297c-6.63 0-12 5.373-12 12 0 5.303 3.438 9.8 8.205 11.385.6.113.82-.258.82-.577
0-.285-.01-1.04-.015-2.04-3.338.724-4.042-1.61-4.042-1.61C4.422 18.07 3.633 17.7 3.633
17.7c-1.087-.744.084-.729.084-.729 1.205.084 1.838 1.236 1.838 1.236 1.07 1.835 2.809
1.305 3.495.998.108-.776.417-1.305.76-1.605-2.665-.3-5.466-1.332-5.466-5.93 0-1.31.465-2.38
1.235-3.22-.135-.303-.54-1.523.105-3.176 0 0 1.005-.322 3.3 1.23.96-.267 1.98-.399 3-.405
1.02.006 2.04.138 3 .405 2.28-1.552 3.285-1.23 3.285-1.23.645 1.653.24 2.873.12 3.176.765.84
1.23 1.91 1.23 3.22 0 4.61-2.805 5.625-5.475 5.92.42.36.81 1.096.81 2.22 0 1.606-.015
2.896-.015 3.286 0 .315.21.69.825.57C20.565 22.092 24 17.592 24 12.297c0-6.627-5.373-12-12-12"}]]])


(defn settings-icon []
  [:svg
   {:style {:cursor "pointer"}
    :width "24px"
    :height "24px"
    :role "img"
    :view-box "0 0 20 20"
    :xmlns "http://www.w3.org/2000/svg"}
   [:path
    {:fill (if (:dark-mode @app-state) "#d7dadc" "#363636")
     :d "M7.03093403,10 C7.03093403,8.36301971 8.36301971,7.03093403 10,7.03093403
C11.6369803,7.03093403 12.9679409,8.36301971 12.9679409,10 C12.9679409,11.6369803
11.6369803,12.969066 10,12.969066 C8.36301971,12.969066 7.03093403,11.6369803 7.03093403,
10 M16.4016617,8.49127796 C16.2362761,7.79148295 15.9606334,7.13669084 15.5916096,6.5437777
L16.5231696,5.06768276 C16.7526843,4.70315931 16.7684353,4.22387849 16.5231696,3.83572852
C16.1833977,3.29794393 15.4712269,3.13593351 14.9323172,3.47683044 L13.4562223,4.40839036
C12.8633092,4.03936662 12.208517,3.76259882 11.508722,3.59833825 L11.1250724,1.89947899
C11.0294412,1.47982699 10.7020452,1.12992949 10.2542664,1.02867298 C9.63322641,0.888038932
9.01556168,1.27843904 8.87492764,1.89947899 L8.49127796,3.59833825 C7.79148295,3.76259882
7.13669084,4.03936662 6.54265263,4.40726528 L5.06768276,3.47683044 C4.70315931,3.24731568
4.22387849,3.23156466 3.83572852,3.47683044 C3.29794393,3.81660229 3.13593351,4.5287731
3.47683044,5.06768276 L4.40726528,6.54265263 C4.03936662,7.13669084 3.76259882,7.79148295
3.59721318,8.49127796 L1.89947899,8.87492764 C1.47982699,8.97055879 1.12992949,9.29795485
1.02867298,9.74573365 C0.888038932,10.3667736 1.27843904,10.9844383 1.89947899,11.1250724
L3.59721318,11.508722 C3.76259882,12.208517 4.03936662,12.8633092 4.40726528,13.4573474
L3.47683044,14.9323172 C3.24731568,15.2968407 3.23156466,15.7761215 3.47683044,16.1642715
C3.81660229,16.7020561 4.5287731,16.8640665 5.06768276,16.5231696 L6.54265263,15.5927347
C7.13669084,15.9606334 7.79148295,16.2374012 8.49127796,16.4016617 L8.87492764,18.100521
C8.97055879,18.520173 9.29795485,18.8700705 9.74573365,18.971327 C10.3667736,19.1119611
10.9844383,18.721561 11.1250724,18.100521 L11.508722,16.4016617 C12.208517,16.2374012
12.8633092,15.9606334 13.4562223,15.5916096 L14.9323172,16.5231696 C15.2968407,16.7526843
15.7749964,16.7684353 16.1631464,16.5231696 C16.7020561,16.1833977 16.8629414,15.4712269
16.5231696,14.9323172 L15.5916096,13.4562223 C15.9606334,12.8633092 16.2362761,12.208517
16.4016617,11.508722 L18.100521,11.1250724 C18.520173,11.0294412 18.8700705,10.7020452
18.971327,10.2542664 C19.1119611,9.63322641 18.721561,9.01556168 18.100521,8.87492764
L16.4016617,8.49127796 Z"}]])


(defn splash-screen-setting []
  [:<>
   [:div
    {:style {:color (if (:dark-mode @app-state) "#d7dadc" "#363636")}}
    "Splash Screen:"]
   [:label
    {:style {:display "flex"
             :align-items "center"
             :color (if (:dark-mode @app-state) "#878a8c" "#4a4a4a")
             :padding "10px 0px"}}
    [:input {:style {:margin-right "10px"
                     :vertical-align "middle"
                     :position "relative"
                     :bottom "1px"}
             :type "checkbox"
             :default-checked (:splash-screen @app-state)
             :on-click #(set-item! "splash-screen" (-> % .-target .-checked))}]
    "Digital Clock"]])


(defn request-delay-setting []
  [:<>
   [:div
    {:style {:color (if (:dark-mode @app-state) "#d7dadc" "#363636")
             :display "flex"
             :align-items "center"
             :padding "10px 0px"}}
    "Request Delay:"]
   [:div.select
    [:select
     {:style {:width "200px"
              :color (if (:dark-mode @app-state) "#878a8c" "#363636")
              :background-color (if (:dark-mode @app-state) "#1a1a1a" "#ffffff")
              :border-color (if (:dark-mode @app-state) "#363636" "#cccccc")}
      :default-value (:request-delay @app-state)
      :on-change #(do
                    (swap! app-state assoc :request-delay (-> % .-target .-value))
                    (set-item! "request-delay" (:request-delay @app-state)))}
     (for [option (:delay-options @app-state)]
       ^{:key (:value option)}
       [:option
        {:value (:value option)}
        (:label option)])]]])


(defn settings-card []
  (fn []
    [:div.modal-content
     {:style {:padding "20px"
              :margin "0 0 60px 0"
              :border-radius "8px"
              :background-color (if (:dark-mode @app-state) "#1a1a1a" "#ffffff")
              :overflow "hidden"
              :border-width "1px"
              :border-style "solid"
              :border-color (if (:dark-mode @app-state) "#363636" "#cccccc")}}
     [:div
      {:style {:margin-bottom "10px"
               :padding-bottom "10px"
               :border-bottom (if (:dark-mode @app-state) "1px solid #4a4a4a" "1px solid #edeff1")}}
      [:span.title
       {:style {:color (if (:dark-mode @app-state) "#d7dadc" "#363636")}}
       "Settings"]]
     [splash-screen-setting]
     [request-delay-setting]]))


(defn first-header []
  (let [modal? (r/atom nil)]
    (fn []
      [:div
       {:style {:display "flex"
                :flex-direction "row"
                :justify-content "space-between"
                :align-items "center"}}
       [:div.title.is-2
        {:style {:color "#f34"}}
        "trendcat"]
       [:div
        {:style {:display "flex"
                 :flex-direction "row"
                 :margin-bottom "24px"}}

        [:div
         {:on-click #(reset! modal? true)}
         (when @modal?
           [:div.modal.is-active
            [:div.modal-background
             {:on-click #(do
                           (-> % .stopPropagation)
                           (reset! modal? false))}]
            [settings-card]])
         [settings-icon]]
        [:span
         {:style {:margin-left "10px"}}
         [:div
          {:on-click #(do
                        (swap! app-state assoc :dark-mode (not (:dark-mode @app-state)))
                        (set-item! "dark-mode" (:dark-mode @app-state)))}
          [:img {:style {:cursor "pointer"}
                 :width "24"
                 :height "24"
                 :role "presentation"
                 :src moon-toggle}]]]
        [:span
         {:style {:margin-left "10px"}}
         [github-button]]]])))


(defn github-header []
  (let [since-name (case (:since @app-state)
                     "daily" "Today"
                     "weekly" "This Week"
                     "monthly" "This Month")]
    [:div
     {:style {:display "flex"
              :flex-direction "row"
              :justify-content "space-between"
              :flex-wrap "wrap"}}
     [:div
      {:style {:margin-bottom "10px"}}
      [:span.title
       {:style {:color (if (:dark-mode @app-state) "#d7dadc" "#363636")}}
       "GitHub"]]
     [:div
      {:style {:display "flex"
               :flex-direction "row"
               :justify-content "flex-end"
               :margin-bottom "10px"}}
      [language-select]
      [github-since-select since-name]]]))


(defn hnews-header []
  (let [story-name (case (:stories @app-state)
                     "topstories" "Top"
                     "newstories" "New"
                     "showstories" "Show")]
    [:div
     {:style {:display "flex"
              :flex-direction "row"
              :justify-content "space-between"
              :flex-wrap "wrap"}}
     [:div
      {:style {:margin-bottom "10px"}}
      [:span.title
       {:style {:color (if (:dark-mode @app-state) "#d7dadc" "#363636")}}
       "Hacker News"]]
     [:div
      {:style {:margin-left "auto"
               :margin-bottom "10px"}}
      [hnews-select story-name]]]))


(defn home []
  (r/create-class
   {:component-did-mount #(do
                            (get-github-trends {:force? false
                                                :lang (:lang @app-state)
                                                :since (:since @app-state)})
                            (get-hnews-stories {:force? false
                                                :type (:stories @app-state)}))

    :reagent-render
    (let [on-mouse-move (r/atom false)]
      (fn []
        (let [timer (r/atom (js/Date.))
              time-updater (js/setInterval
                            #(reset! timer (js/Date.)) 1000)
              time-str (-> @timer .toTimeString (clojure.string/split " ") first)]
          [:div
           {:onMouseMove #(reset! on-mouse-move (boolean (-> % .-nativeEvent .-offsetX)))
            :style {:min-height "100vh"
                    :background-color (if (:dark-mode @app-state) "#111" "#edeff1")}}
           (if (and (:splash-screen @app-state) (not @on-mouse-move))
             [:div.hero.is-fullheight
              [:div.hero-body
               [:div.container
                [:div
                 {:style {:height "100%"
                          :display "flex"
                          :align-items "center"
                          :justify-content "center"
                          :font-variant-numeric "tabular-nums"
                          :font-size "10vw"
                          :color (if (:dark-mode @app-state) "#d7dadc" "#363636")}}
                 time-str]]]]
             [:div.container
              [:section.hero
               [:div.hero-body
                [first-header]]]
              [:div.columns.is-desktop
               {:style {:margin-left 0
                        :margin-right 0}}
               [:div.column
                [github-header]
                [github-list]]
               [:div.column
                [hnews-header]
                [hnews-list]]]])])))}))
