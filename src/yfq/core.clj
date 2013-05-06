(ns yfq.core
  (:require [clj-http.client :as client])
  (:require [clojure.data.csv :as csv])
  (:require [clojure.java.io :as io]))

(defn- clean
  ;;;trims all whitespace from every item in a list and transforms them into keywords
  [lst]
  (for [item lst]
    (keyword (clojure.string/replace item #"\s+" ""))))

(defn- get-csv
  ;;;performs a GET request ands returns only the body
  ([sym month day year]
  (:body
   (client/get
    (str "http://ichart.finance.yahoo.com/table.csv?s=" sym "&a=" month
         "&b=" day "&c=" year "&d=" month "&e=" day "&f=" year))))
  ([sym month1 day1 year1 month2 day2 year2]
  (:body
   (client/get
    (str "http://ichart.finance.yahoo.com/table.csv?s=" sym "&a=" month1
         "&b=" day1 "&c=" year1 "&d=" month2 "&e=" day2 "&f=" year2)))))

(defn- parse-csv
  ;;;Returns a map with the keys ripped from the csv file heading
  [file]
  (let [[heading data] [(first (csv/read-csv file)) (rest (csv/read-csv file))]]
    (if (= 1 (count data))
      (zipmap (clean heading) (first data))
      (zipmap
       (clean heading)
       (partition (count data) (apply interleave data))))))

(defn- parse-date
  ;;;Ex. input: 12/19/1986 output: ["12" "19" "1986"]
  [date]
  (vec (re-seq #"\d+" date)))

(defn- dec-string
  ;;;Necessary since the yahoo GET has January mapped to 0
  [string]
  (str (dec (read-string string))))

(defn hist-quote
  ;;;Returns a map of stock data (for a given day), use keys to resolve what data is returned (as it may change)
  [sym date]
  (let [[month day year] (parse-date date)]
    (parse-csv
     (get-csv sym (dec-string month) day year))))

(defn hist-range
  ;;;Returns a map of stock data (for a given range), use keys to resolve what data is returned (as it may change)
  [sym date1 date2]
  (let [[month1 day1 year1] (parse-date date1)
        [month2 day2 year2] (parse-date date2)]
    (parse-csv
     (get-csv sym (dec-string month1) day1 year1 (dec-string month2) day2 year2))))
    
    
   



  
