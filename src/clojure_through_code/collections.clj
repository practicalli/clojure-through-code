(ns clojure-through-code.collections)

;; The built in data structures of Clojure are great for representing simple and complex collections.

;; A colleciton is like a collection of toys you have as a child, or a collection of music and videos you collect as a teenager and into adulthood.

;; How would you represent a simple collecion of music?

;; You could simply list all the albums you have, in this case using a Clojure vector

["Smells like Teen Spirit", "Trash", "Vision Thing"]

;; and then give this collection a name

(def albums ["Smells like Teen Spirit", "Trash", "Vision Thing" "Tubular Bells"])

;; If you have a small collection, this could be okay.  However, it doesnt capture the names of the artists.  You may want to listen to something by Mike Oldfield but forget which albums they created

;; You could use a map data structure in Clojure that links the album with artist

{"Tubular Bells" "Mike Oldfield"}

;; or you could make this map more expressive by explictly adding keys that give some context to this data.

{:album-name "Tubular Bells" :artist "Mike Oldfield"}

;; To hold all the albums we have in our music collection we could have each map as an item in our vector

[{:album-name "Tubular Bells"           :artist "Mike Oldfield"}
 {:album-name "Smells like teen spirit" :artist "Nirvana"}
 {:album-name "Vision Thing"            :artist "Sisters of Mercy"}
 {:album-name "Here comes the war"      :artist "New Model Army"}
 {:album-name "Thunder & Consolation"   :artist "New Model Army"}]

;; If we give this music collection a name then it makes it easy to use with functions

(def album-collection [{:album-name "Tubular Bells"           :artist "Mike Oldfield"}
                       {:album-name "Smells like teen spirit" :artist "Nirvana"}
                       {:album-name "Vision Thing"            :artist "Sisters of Mercy"}
                       {:album-name "Here comes the war"      :artist "New Model Army"}
                       {:album-name "Thunder & Consolation"   :artist "New Model Army"}])

;; Lets say we want all the album titles in our collection

(map #(get % :album-name) album-collection)
;; => ("Tubular Bells" "Smells like teen spirit" "Vision Thing" "Here comes the war" "Thunder & Consolation")

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; what if we just want to see our albums for a specific artist ?

;; We can filter out all the album maps in our collection and just return those maps that contain the given artist.

;; First, lets look at how you get a value from a map using the get function
(get {:album-name "Thunder & Consolation"   :artist "New Model Army"} :artist)

;; Now we can test the result to see if it is the name of the artist
(= "New Model Army" (get {:album-name "Thunder & Consolation"   :artist "New Model Army"} :artist))

;; So we could take this test and apply it in turn to all of the album maps in our music collection

;; A common function for this is called filter

;; first try at is-by-artist? function - problem: trying to parse the whole collection here, rather than just a function to parse each album map in the calling expression.
#_(defn is-by-artist?
  "Checks album to see if it is by a particular artist"
  [artist collection]
  (if-some [result (= (map #(get % :artist) collection) artist)]
    result))

;; Lets define a function that takes the name of an artist and an album map and checks if that album is by the said artist.  This function will only take one album map, not the collection of album maps.

(defn is-album-by-artist?
  "simplified version"
  [artist album-map]
  (if (= artist (get album-map :artist))
    true
    false))

(is-album-by-artist? "New Model Army" {:album-name "Thunder & Consolation"   :artist "New Model Army"})



(filter (partial is-by-artist? "New Model Army") album-collection)

