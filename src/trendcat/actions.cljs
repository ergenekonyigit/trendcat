(ns trendcat.actions
  (:require [ajax.core :refer [GET]]
            [trendcat.db :refer [app-state api-url]]))

(defn handler [response]
  (swap! app-state assoc :trending response))

(defn error-handler [{:keys [status status-text]}]
  (.log js/console (str "Something bad happened: " status " " status-text)))

(defn get-repositories
  ([]
   (GET (str api-url "/repositories") {:handler handler
                                       :error-handler error-handler
                                       :response-format :json
                                       :keywords? true}))
  ([{:keys [lang since]}]
   (GET (str api-url "/repositories") {:params {:language lang
                                                :since since}
                                       :handler handler
                                       :error-handler error-handler
                                       :response-format :json
                                       :keywords? true})))
