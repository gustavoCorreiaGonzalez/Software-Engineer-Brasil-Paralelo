(ns takehome.core
  (:require [java-time :as time]))

(defn media-date-between-subscription-date? [midia subscription]
  (time/before? (:subscription-start subscription)
                (:released-at midia)
                (:subscription-end subscription)))

(defn can-access? [midia subscription]
  (if (= (:type midia) :movie)
    (and (= (:type subscription) :patriota)
         (media-date-between-subscription-date? midia subscription))))