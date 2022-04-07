(ns takehome.core-test
  (:require [clojure.test :refer :all]
            [java-time :as time]
            [takehome.core :as sub]))

(deftest media-date-between-subscription-date-test
  (are [result midia subscription] (= result
                                      (sub/media-date-between-subscription-date?
                                       midia
                                       subscription))
    true  {:released-at (time/local-date-time "2019-07-24T20:02:34.691")}
    {:subscription-start (time/local-date-time "2019-01-24T11:46:22.811")
     :subscription-end   (time/local-date-time "2020-01-24T11:46:22.811")}

    false {:released-at (time/local-date-time "2019-07-24T20:02:34.691")}
    {:subscription-start (time/local-date-time "2017-01-24T11:46:22.811")
     :subscription-end   (time/local-date-time "2019-01-24T11:46:22.811")}

    false {:released-at (time/local-date-time "2019-07-24T20:02:34.691")}
    {:subscription-start (time/local-date-time "2020-01-24T08:30:00.154")
     :subscription-end   (time/local-date-time "2021-01-24T08:30:00.154")}
    
    false {:released-at (time/local-date-time "2021-05-01T00:00:00.000")}
    {:subscription-start (time/local-date-time "2019-01-24T11:46:22.811")
     :subscription-end   (time/local-date-time "2020-01-24T11:46:22.811")}
    
    false {:released-at (time/local-date-time "2021-05-01T00:00:00.000")}
    {:subscription-start (time/local-date-time "2017-01-24T11:46:22.811")
     :subscription-end   (time/local-date-time "2019-01-24T11:46:22.811")}
    
    false {:released-at (time/local-date-time "2021-05-01T00:00:00.000")}
    {:subscription-start (time/local-date-time "2020-01-24T08:30:00.154")
     :subscription-end   (time/local-date-time "2021-01-24T08:30:00.154")}))

(deftest patriota-access-test
  (are [result midia subscription] (= result
                                      (sub/patriota-access?
                                       midia
                                       subscription))
    true 
    {:type :series :name "1964: O Brasil entre Armas e Livros", :released-at (time/local-date-time "2019-07-24T20:02:34.691")}  
    {:type :patriota
     :subscription-start (time/local-date-time "2019-01-24T11:46:22.811")
     :subscription-end   (time/local-date-time "2020-01-24T11:46:22.811")}
    
    true 
    {:type :series :name "1964: O Brasil entre Armas e Livros", :released-at (time/local-date-time "2019-07-24T20:02:34.691")}
    {:type :patriota
     :subscription-start (time/local-date-time "2017-01-24T11:46:22.811")
     :subscription-end   (time/local-date-time "2019-01-24T11:46:22.811")}
  
    true 
    {:type :podcast :name "CORINGA - Podcast Cultura Paralela #1", :released-at (time/local-date-time "2020-03-29T20:02:34.337")}
    {:type :patriota
     :subscription-start (time/local-date-time "2019-01-24T11:46:22.811")
     :subscription-end   (time/local-date-time "2020-01-24T11:46:22.811")}
    
    true 
    {:type :podcast :name "CORINGA - Podcast Cultura Paralela #1", :released-at (time/local-date-time "2020-03-29T20:02:34.337")}
    {:type :patriota
     :subscription-start (time/local-date-time "2020-02-15T14:36:27.567")
     :subscription-end   (time/local-date-time "2021-02-15T14:36:27.567")}
  
    true
    {:type :interview :name "Congresso Brasil Paralelo - Rodrigo gurgel", :released-at (time/local-date-time "2019-01-24T11:45:57.229")}
    {:type :patriota
     :subscription-start (time/local-date-time "2019-01-01T11:46:22.811")
     :subscription-end   (time/local-date-time "2020-01-24T11:46:22.811")}
    
    false 
    {:type :interview :name "Congresso Brasil Paralelo - Rodrigo gurgel", :released-at (time/local-date-time "2019-01-24T11:45:57.229")}
    {:type :patriota
     :subscription-start (time/local-date-time "2020-02-15T14:36:27.567")
     :subscription-end   (time/local-date-time "2021-02-15T14:36:27.567")}))

(deftest active-subscription-test
  (are [result subscription] (= result
                                (sub/active-subscription?
                                 subscription))
    false 
    {:subscription-start (time/local-date-time "2019-01-24T11:46:22.811")
     :subscription-end   (time/local-date-time "2020-01-24T11:46:22.811")}

    false 
    {:subscription-start (time/local-date-time "2017-01-24T11:46:22.811")
     :subscription-end   (time/local-date-time "2019-01-24T11:46:22.811")}

    false
    {:subscription-start (time/local-date-time "2021-01-24T08:30:00.154")
     :subscription-end   (time/local-date-time "2022-01-24T08:30:00.154")}
    
    true 
    {:subscription-start (time/local-date-time "2021-07-01T12:40:10.124")
     :subscription-end   (time/local-date-time "2022-07-01T12:40:10.124")}))

(deftest premium-access-test
  (are [result midia subscription] (= result
                                      (sub/premium-access?
                                       midia
                                       subscription))
    true
    {:type :interview :name "Congresso Brasil Paralelo - Rodrigo gurgel", :released-at (time/local-date-time "2019-01-24T11:45:57.229")}
    {:type :premium
     :subscription-start (time/local-date-time "2019-01-24T11:46:22.811")
     :subscription-end   (time/local-date-time "2020-01-24T11:46:22.811")}

    true
    {:type :interview :name "Congresso Brasil Paralelo - Alexandre Borges", :released-at (time/local-date-time "2019-11-16T21:40:51.621")}
    {:type :premium
     :subscription-start (time/local-date-time "2021-01-01T11:46:22.811")
     :subscription-end   (time/local-date-time "2022-01-01T11:46:22.811")}

    true
    {:type :podcast :name "Rap / Funk - Podcast Cultura Paralela #3", :released-at (time/local-date-time "2020-03-29T20:02:34.345")}
    {:type :premium
     :subscription-start (time/local-date-time "2017-01-24T11:46:22.811")
     :subscription-end   (time/local-date-time "2019-01-24T11:46:22.811")}

    false
    {:type :patron :name "Relatório Mecenas", :released-at (time/local-date-time "2020-08-10T20:00:00.656")}
    {:type :premium
     :subscription-start (time/local-date-time "2017-01-24T11:46:22.811")
     :subscription-end   (time/local-date-time "2019-01-24T11:46:22.811")}))

(deftest can-access-test
  (are [result purchase] (= result
                            (sub/can-access?
                             {:type :series :name "Brasil - A Última Cruzada", :released-at (time/local-date-time "2019-07-08T16:37:11.184")}
                             purchase))
    false  {:type               :patriota
           :subscription-start (time/local-date-time "2019-01-24T11:46:22.811")
           :subscription-end   (time/local-date-time "2020-01-24T11:46:22.811") }
    true {:type               :patriota
           :subscription-start (time/local-date-time "2021-07-01T12:40:10.124")
           :subscription-end   (time/local-date-time "2022-07-01T12:40:10.124") }))
