(ns clojure-through-code.collections)

;; The built in data structures of Clojure are great for representing simple and complex collections.

;; So what are examples of collecitons?  It could be all the movies you own or your music library.  It could be all the books you own, both paper and electronic.

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

;; If we give this music collection a name then it makes it easy to use with functions.
;; We will use this `album-collection' name to for the next examples to keep the examples simple to read.

(def album-collection [{:album-name "Tubular Bells"           :artist "Mike Oldfield"}
                       {:album-name "Smells like teen spirit" :artist "Nirvana"}
                       {:album-name "Vision Thing"            :artist "Sisters of Mercy"}
                       {:album-name "Here comes the war"      :artist "New Model Army"}
                       {:album-name "Thunder & Consolation"   :artist "New Model Army"}])

;; We could get a specific album by its position in the collection, like pulling out a specific album out of the shelf that holds our collection.

;; lets get the first album from our collection.  The first album is in position 0.
(get album-collection 0)
;; => {:album-name "Tubular Bells", :artist "Mike Oldfield"}

;; Or we can use the collection functions to also get specific albums
(first album-collection)
;; => {:album-name "Tubular Bells", :artist "Mike Oldfield"}

(last album-collection)
;; => {:album-name "Thunder & Consolation", :artist "New Model Army"}


;; If we want to get just the album name from a specific album. we first get the album as a result, as a map of key value pairs.
;; Then using the relevant key in the map we extract just the album name
(get (get album-collection 0) :album-name)
;; => "Tubular Bells"


;; Lets say we want all the album titles in our collection
(map #(get % :album-name) album-collection)
;; => ("Tubular Bells" "Smells like teen spirit" "Vision Thing" "Here comes the war" "Thunder & Consolation")


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; what if we just want to see all our albums for a specific artist ?

;; We can filter out all the album maps in our collection and just return those maps that contain the given artist.

;; First, lets remind ourselves how to get a specific value from a map using the get function and a key from the map
(get {:album-name "Thunder & Consolation"   :artist "New Model Army"} :artist)
;; => "New Model Army"

;; Now we can test the result to see if it is the name of the artist, by using the = function with the name of the artist we are looking for.
(= "New Model Army" (get {:album-name "Thunder & Consolation"   :artist "New Model Army"} :artist))
;; => true

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

(is-album-by-artist? "New Model Army" {:album-name "Thunder & Consolation" :artist "New Model Army"})
;; => true



(filter (partial is-album-by-artist? "New Model Army") album-collection)
;; => ({:album-name "Here comes the war", :artist "New Model Army"} {:album-name "Thunder & Consolation", :artist "New Model Army"})

;; not quite right...

;;(get (filter (partial is-album-by-artist? "New Model Army") album-collection) :album-name)
;; => nil
