(ns takehome.core
  (:require [java-time :as time]))

(defn active-subscription? [subscription]
  (let [today time/local-date-time]
    (time/before? (:subscription-start subscription)
                  (today)
                  (:subscription-end subscription))))

(defn media-date-between-subscription-date? [midia subscription]
  (time/before? (:subscription-start subscription)
                (:released-at midia)
                (:subscription-end subscription)))

(defn patriota-access? [midia subscription]
  (or
  ;;  (.contains [:series :podcast :debate] (:type midia))
   (contains? {:series nil :podcast nil :debate nil} (:type midia))
   (and
    (= (:type midia) :interview)
    (media-date-between-subscription-date? midia subscription))))

(defn premium-access? [midia subscription]
  (contains? {:series nil :podcast nil :debate nil :interview nil :course nil} (:type midia)))

(defn can-access? [midia subscription]
  (and
   (active-subscription? subscription)
   (= (:type subscription) :patriota) (patriota-access? midia subscription)))