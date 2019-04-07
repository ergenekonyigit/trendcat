(ns trendcat.actions
  (:require [ajax.core :refer [GET]]
            [clojure.string :as str]
            [cljs-time.core :as cljs-time]
            [trendcat.db :refer [app-state github-api-url hnews-api-url]]))


(defn dark-mode [mode light dark]
  (if mode dark light))


(defn generate-date [created-date]
  (try
    (let [current-date (js/Date.)
          created-date-as-js (js/Date. (* created-date 1000))
          min (cljs-time/in-minutes (cljs-time/interval created-date-as-js current-date))
          hour (cljs-time/in-hours (cljs-time/interval created-date-as-js current-date))
          day (cljs-time/in-days (cljs-time/interval created-date-as-js current-date))]
      (cond
        (< min 60) (if (= min 1) (str min " minute ago") (str min " minutes ago"))
        (< hour 24) (if (= hour 1) (str hour " hour ago") (str hour " hours ago"))
        :else
        (if (= day 1) (str day " day ago") (str day " days ago"))))
    (catch js/Error e
      "0 minutes ago")))


(defn github-handler [response]
  (swap! app-state assoc :github-trends response))


(defn error-handler [{:keys [status status-text]}]
  (js/console.log (str "Something bad happened: " status " " status-text)))


(defn get-github-trends
  ([]
   (get-github-trends nil))
  ([{:keys [lang since] :as args}]
   (GET (str github-api-url "/repositories") {:params (when args {:language lang :since since})
                                              :handler github-handler
                                              :error-handler error-handler
                                              :response-format :json
                                              :keywords? true})))


(defn hnews-story-items-handler [response]
  (let [time (generate-date (:time response))
        current-date (js/Date.)
        created-date-as-js (js/Date. (* (:time response) 1000))
        hour (cljs-time/in-hours (cljs-time/interval created-date-as-js current-date))
        rank (/ (- (:score response) 1) (Math/pow (+ hour 2) 1.8))
        item (assoc response :time time)
        item (assoc item :rank rank)
        items (conj (:temp-items @app-state) item)]
    (swap! app-state assoc :temp-items items)
    (when (= (count (:temp-items @app-state)) 50)
      (do
        (swap! app-state assoc :temp-items [])
        (swap! app-state assoc :hnews-story-items items)))))


(defn get-hnews-story-items [story-id]
  (GET (str hnews-api-url "/item/" story-id ".json") {:handler hnews-story-items-handler
                                                      :error-handler error-handler
                                                      :response-format :json
                                                      :keywords? true}))


(defn hnews-story-ids-handler [response]
  (swap! app-state assoc :hnews-story-ids (vec (take 50 response)))
  (doseq [id (:hnews-story-ids @app-state)]
    (get-hnews-story-items id)))


(defn get-hnews-stories [type]
  (GET (str hnews-api-url "/" type ".json") {:handler hnews-story-ids-handler
                                             :error-handler error-handler
                                             :response-format :json
                                             :keywords? true}))
