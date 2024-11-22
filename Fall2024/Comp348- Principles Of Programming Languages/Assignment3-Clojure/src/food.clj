(ns food
  (:require [clojure.string :as str]))

(defn validate-map [map-data]
  (let [lines (str/split-lines map-data)
        line-lengths (map count lines)]
    (and (not-empty lines)
         (apply = line-lengths))))

(defn find-food [grid visited x y]
  (let [rows (count grid)
        cols (count (first grid))]
    (cond
      ;; Out of bounds or already visited
      (or (< x 0) (>= x rows) (< y 0) (>= y cols) (get-in visited [x y])) nil

      ;; Found food
      (= (get-in grid [x y]) \@) [[x y]]

      ;; Hit a wall
      (= (get-in grid [x y]) \#) nil

      :else
      ;; Continue searching
      (let [updated-visited (assoc-in visited [x y] true)
            paths [(find-food grid updated-visited (inc x) y) ; Down
                   (find-food grid updated-visited (dec x) y) ; Up
                   (find-food grid updated-visited x (inc y)) ; Right
                   (find-food grid updated-visited x (dec y))]] ; Left
        (some identity paths)))))

(defn mark-path [grid path symbol]
  (reduce (fn [g [x y]] (assoc-in g [x y] symbol)) grid path))

(defn search-food [map-data]
  (let [grid (mapv vec (str/split-lines map-data))
        rows (count grid)
        cols (count (first grid))
        initial-path (find-food grid {} 0 0)]
    (if initial-path
      (-> grid
          (mark-path initial-path \+)
          (str/join "\n"))
      "No path to food found!")))

