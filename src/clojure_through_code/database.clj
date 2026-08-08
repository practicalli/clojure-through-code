(ns clojure-through-code.database)

(defn login
  "A mock login function to access a database"
  [username password]
  (str "You logged in using " username " & " password))

(login "johnny" "supersecretpassword")

(defn logout
  "A mock logout function to access a database"
  [username]
  (str username " just logged out"))

(logout "johnny")
