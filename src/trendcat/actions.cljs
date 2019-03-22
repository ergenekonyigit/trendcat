(ns trendcat.actions
  (:require [ajax.core :refer [GET]]
            [trendcat.db :refer [app-state api-url]]))


(defn handler [response]
  (swap! app-state assoc :trends response))


(defn error-handler [{:keys [status status-text]}]
  (js/alert (str "Something bad happened: " status " " status-text)))


(defn get-repositories
  ([]
   (get-repositories nil))
  ([{:keys [lang since] :as args}]
   (GET (str api-url "/repositories") {:params (when args {:language lang :since since})
                                       :handler handler
                                       :error-handler error-handler
                                       :response-format :json
                                       :keywords? true})))
