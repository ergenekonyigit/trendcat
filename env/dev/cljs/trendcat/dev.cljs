(ns ^:figwheel-no-load trendcat.dev
  (:require
    [trendcat.core :as core]
    [devtools.core :as devtools]))


(enable-console-print!)

(devtools/install!)

(core/init!)
