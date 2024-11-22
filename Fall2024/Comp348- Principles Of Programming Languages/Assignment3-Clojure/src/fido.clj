(ns fido
  (:require [food]
            [clojure.string :as str]
            [clojure.java.io :as io]))

(defn display-menu []
  (println "\nFido's Food Hunt!")
  (println "1. List map files")
  (println "2. Load a map and start search")
  (println "3. Exit"))

(defn list-map-files []
  (let [files (->> (file-seq (io/file "./maps"))
                   (filter #(.isFile %))
                   (filter #(str/ends-with? (.getName %) ".txt"))
                   (map #(.getName %)))]
    (if (empty? files)
      (println "No map files found!")
      (doseq [file files]
        (println "* " file)))))

(defn start-search []
  (print "Enter the map file name: ")
  (flush)
  (let [file-name (read-line)
        file-path (str "./maps/" file-name)]
    (if (.exists (io/file file-path))
      (let [map-data (slurp file-path)]
        (if (food/validate-map map-data)
          (do
            (println "\nFido's Challenge:")
            (println map-data)
            (println "\nSearch Result:")
            (println (food/search-food map-data)))
          (println "Invalid map format. Please check the map file.")))
      (println "File not found! Please provide a valid map file."))))

(defn -main []
  (loop []
    (display-menu)
    (print "\nChoose an option: ")
    (flush)
    (let [choice (read-line)]
      (case choice
        "1" (do (list-map-files)
                (recur))
        "2" (do (start-search)
                (recur))
        "3" (println "Goodbye!")
        (do (println "Invalid option. Please try again.")
            (recur))))))
