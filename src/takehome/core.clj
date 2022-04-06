(ns takehome.core
  (:require [java-time :as time]))

(defn media-date-between-subscription-date? [midia subscription]
  (time/before? (:subscription-start subscription)
                (:released-at midia)
                (:subscription-end subscription)))

(defn patriota-access? [midia subscription]
  (or
   (= (:type midia) :series)
   (= (:type midia) :podcast)
   (= (:type midia) :debate)
   (and
    (= (:type midia) :interview)
    (media-date-between-subscription-date? midia subscription))))

(defn can-access? [midia subscription]
  (if (= (:type midia) :series)
    (and (= (:type subscription) :patriota)
         (media-date-between-subscription-date? midia subscription))))