(ns takehome.core-test
  (:require [clojure.test :refer :all]
            [java-time :as time]
            [takehome.core :as sub]))

(deftest media-date-between-subscription-date-test
  (are [result subscription] (= result
                            (sub/media-date-between-subscription-date?
                             {:released-at (time/local-date-time "2019-07-24T20:02:34.691")}
                             subscription))
    true  {:subscription-start (time/local-date-time "2019-01-24T11:46:22.811")
           :subscription-end   (time/local-date-time "2020-01-24T11:46:22.811") }
    false {:subscription-start (time/local-date-time "2017-01-24T11:46:22.811")
           :subscription-end   (time/local-date-time "2019-01-24T11:46:22.811") }
    false {:subscription-start (time/local-date-time "2020-01-24T08:30:00.154")
           :subscription-end   (time/local-date-time "2021-01-24T08:30:00.154")})
  (are [result subscription] (= result
                                (sub/media-date-between-subscription-date?
                                 {:released-at (time/local-date-time "2021-05-01T00:00:00.000")}
                                 subscription))
    false {:subscription-start (time/local-date-time "2019-01-24T11:46:22.811")
           :subscription-end   (time/local-date-time "2020-01-24T11:46:22.811")}
    false {:subscription-start (time/local-date-time "2017-01-24T11:46:22.811")
           :subscription-end   (time/local-date-time "2019-01-24T11:46:22.811")}
    false {:subscription-start (time/local-date-time "2020-01-24T08:30:00.154")
           :subscription-end   (time/local-date-time "2021-01-24T08:30:00.154")}))

(deftest patriota-access-test
  (are [result subscription] (= result
                            (sub/patriota-access?
                             {:type :series :name "1964: O Brasil entre Armas e Livros", :released-at (time/local-date-time "2019-07-24T20:02:34.691")}
                             subscription))
    true  {:type               :patriota
           :subscription-start (time/local-date-time "2019-01-24T11:46:22.811")
           :subscription-end   (time/local-date-time "2020-01-24T11:46:22.811")}
    true {:type               :patriota
           :subscription-start (time/local-date-time "2017-01-24T11:46:22.811")
           :subscription-end   (time/local-date-time "2019-01-24T11:46:22.811")})
  (are [result subscription] (= result
                                (sub/patriota-access?
                                 {:type :podcast :name "CORINGA - Podcast Cultura Paralela #1", :released-at (time/local-date-time "2020-03-29T20:02:34.337")}
                                 subscription))
    true  {:type               :patriota
           :subscription-start (time/local-date-time "2019-01-24T11:46:22.811")
           :subscription-end   (time/local-date-time "2020-01-24T11:46:22.811")}
    true {:type               :patriota
          :subscription-start (time/local-date-time "2020-02-15T14:36:27.567")
          :subscription-end   (time/local-date-time "2021-02-15T14:36:27.567")})
  (are [result subscription] (= result
                                (sub/patriota-access?
                                 {:type :interview :name "Congresso Brasil Paralelo - Rodrigo gurgel", :released-at (time/local-date-time "2019-01-24T11:45:57.229")}
                                 subscription))
    true  {:type               :patriota
           :subscription-start (time/local-date-time "2019-01-01T11:46:22.811")
           :subscription-end   (time/local-date-time "2020-01-24T11:46:22.811")}
    false {:type               :patriota
          :subscription-start (time/local-date-time "2020-02-15T14:36:27.567")
          :subscription-end   (time/local-date-time "2021-02-15T14:36:27.567")}))

(deftest test-patriota
  (are [result purchase] (= result
                            (sub/can-access?
                             {:type :series :name "1964: O Brasil entre Armas e Livros", :released-at (time/local-date-time "2019-07-24T20:02:34.691")}
                             purchase))
    true  {:type               :patriota
           :subscription-start (time/local-date-time "2019-01-24T11:46:22.811")
           :subscription-end   (time/local-date-time "2020-01-24T11:46:22.811") }
    false {:type               :patriota
           :subscription-start (time/local-date-time "2017-01-24T11:46:22.811")
           :subscription-end   (time/local-date-time "2019-01-24T11:46:22.811") }))
