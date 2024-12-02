(ns food
  (:require [clojure.string :as str]))

;; Function to validate the map data
(defn validate-map [map-data]
  (let [lines (str/split-lines map-data)
        line-lengths (map count lines)]
    (and (not-empty lines)
         (apply = line-lengths))))

;; Recursive function to find the food and update the grid
(defn find-food [grid visited x y]
  (let [rows (count grid)
        cols (count (first grid))]
    (cond
      ;; Out of bounds or already visited
      (or (< x 0) (>= x rows) (< y 0) (>= y cols) (get-in visited [x y]))
      [grid nil]

      ;; Found food
      (= (get-in grid [x y]) \@)
      [grid [[x y]]]

      ;; Hit a wall
      (= (get-in grid [x y]) \#)
      [grid nil]

      :else
      ;; Continue searching
      (let [updated-visited (assoc-in visited [x y] true)
            directions [[(inc x) y] ; Down
                        [(dec x) y] ; Up
                        [x (inc y)] ; Right
                        [x (dec y)]]] ; Left
        ;; Recursively try all directions
        (loop [grid grid
               remaining-directions directions]
          (if (empty? remaining-directions)
            ;; No valid path found, mark as a bad path if it's not a wall
            (if (= (get-in grid [x y]) \-)
              [(assoc-in grid [x y] \!) nil]
              [grid nil])
            (let [[next-x next-y] (first remaining-directions)
                  [new-grid path] (find-food grid updated-visited next-x next-y)]
              (if path
                ;; If a valid path is found, return it
                [new-grid (conj path [x y])]
                ;; Otherwise, keep searching in other directions
                (recur new-grid (rest remaining-directions))))))))))

;; Function to mark the path on the grid
(defn mark-path [grid path symbol]
  (reduce (fn [g [x y]]
            (assoc-in g [x y] symbol))
          grid
          path))

;; Function to display a message and pause for user input
(defn pause []
  (println "\nPress any key to continue...")
  (.read (System/in)))

;; Main function to search for food
(defn search-food [map-data]
  (let [grid (mapv vec (str/split-lines map-data))]
    (if-not (validate-map map-data)
      (println "Invalid map format!")
      (let [[updated-grid path] (find-food grid {} 0 0)
            final-grid (if path
                         ;; Mark the path with `+` if found
                         (mark-path updated-grid path \+)
                         ;; Otherwise, use the grid with bad paths
                         updated-grid)]
        ;; Print the original map
        (println "\nThis is Fido's challenge:\n")
        (doseq [row grid]
          (println (apply str row)))

        ;; Print the search result
        (if path
          (do
            (println "\nHooray! Fido found her food!\n")
            (doseq [row final-grid]
              (println (apply str row))))
          (do
            (println "\nOh no - Fido could not find her food!\n")
            (doseq [row final-grid]
              (println (apply str row)))))

        ;; Pause after the result
        (pause)))))

