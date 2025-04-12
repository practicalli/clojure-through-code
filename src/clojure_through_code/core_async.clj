;; Include core.async in the project definition and the namespace it will be used in

(ns clojure-through-code.core-async
  (:require
    [clojure.core.async
     :refer
     [<! <!! >! >!! alts!! chan go put! take! timeout]]))


;;
;; Manually putting and taking messages from a channel

;; Define a name for a channel
;; If you do not specify the size of the channel it defaults to 1024 messages

(def manual-channel (chan))


;; use the take! function to listen to the channel.
;; the arguments include the channel name
;; and a function that will be used on the message taken from the channel

(take! manual-channel #(println "Got a value:" %))


;; there is nothing yet on the channel to take, so lets put a message on the channel

(put! manual-channel 42)


;; => Got a value: 42

;; Putting additional values on to the channel will queue up values on that channel

(put! manual-channel 42 #(println "Putting number on the channel:" %))

(put! manual-channel 43 #(println "Putting number on the channel:" %))


;; The put! operations return true as a signal that the put operation could be performed, even though the value hasn’t yet been taken.


;; Take a queued up messages from the channel, one at a time
(take! manual-channel #(println % "taken from the channel"))


;; You can also queue up take commands, that will trigger as soon as something is put on the channel


;; Channels can be closed, which will cause the put operations to not succeed

(close! manual-channel)


;; => nil

(put! manual-channel 42)


;; => false



;;
;; Using Transducers with channels

;; Create a channel with a simple transducer.
;; The transducer increments the value of the message added to the channel.

;; A transducer is an expression to transform values, that does not immediately
;; need those values.

;; In this example, map used the inc function to transform the collection of numbers

;; (map inc [1 2 3 4])

;; To make this a transducer, we simply do not include the value to be transformed
;; (the collection in this example) in the expression
;; Our transducer equivalent is (map inc)
;; and the value to be transformed is passed at run time, when the transducer is called

(def channel-increment (chan 1 (map inc)))

(put! channel-increment 41)

(take! channel-increment #(println % "taken from the channel"))


(def channel-odd? (chan 4 (map odd?)))


(do
  (put! channel-odd? 1)
  (put! channel-odd? 2)
  (put! channel-odd? 3)
  (put! channel-odd? 4))


(take! channel-odd? #(println % "taken from the channel"))


(def channel-filtered-values (chan 4 (filter odd?)))


(do
  (put! channel-filtered-values 1)
  (put! channel-filtered-values 2)
  (put! channel-filtered-values 3)
  (put! channel-filtered-values 4))


(take! channel-filtered-values #(println % "filtered results from channel"))


;; if we could see whats in the channel we would know when the transducer works...


;;
;; Processes and Go blocks

;; Create a channel as before

(def channel-for-processes (chan))


;; asynchronous puts and takes from the channel are done in within a go expression

;; Use the take function, <!, to listen for values put onto the channel
(go
  (println (<! channel-for-processes)))


;; Putting a message on the channel
(go
  (>! channel-for-processes "Hello core.async, how are you"))


;; Show how to two processes interact

;; Listen for a value to be put on the channel
(go
  (println [:process-a] "Trying to take from the channel")
  (println [:process-a] "Taken value:" (<! channel-for-processes)))


;; using Thread/sleep to stop println output getting mixed up

(go
  (println [:process-b] "Preparing to put value onto channel")
  (Thread/sleep 1200)
  (>! channel-for-processes 42)
  (Thread/sleep 1200)
  (println [:process-b] "Put on channel value 42"))


;; Results:
;; [:process-a] Trying to take from the channel
;; [:process-b] Preparing to put value onto channel
;; [:process-a] Taken value: 42
;; [:process-b] Put on channel value 42


;;
;; Adding a timeout to the channel

;; First define a function to get the current time
(defn current-time
  []
  (.format
    (java.text.SimpleDateFormat. "yyyy-MM-dd'T'hh:mm:ss")
    (new java.util.Date)))


;; Could use Java 8 java.time if I could get the formatting working
#_(java.time.LocalDateTime/now)


;; => #object[java.time.LocalDateTime 0x79c6028e "2018-04-02T18:04:42.398"]
#_(java.time.format.DateTimeFormatter "ISO_INSTANT" (java.time.LocalDateTime/now))


;; Print the current time
;; take from a channel the call to core.async/timeout
;; wrap it all in a time expression to see how long it all took
(time
  (go
    (println [:process-a] "In the go block at:" (current-time))
    (<! (timeout 1000))
    (println [:process-a] "I slept one second, bye!" (current-time))))


;;
;; Pushing multiple messages on to a process

;; First you need to define a channel that will be used to manage the messages between processes (threads).

(def channel-stream (chan))


;; We can create a simple message generator with dotimes that prints out 6 numbers
;; We will use this later to push messages on to the core.async channel

#_(dotimes [message 6]
  (println message))


;; Listen to a channel for ever using a non-blocking channel
(go
  (while true
    (Thread/sleep 1200)
    (println (<! channel-stream))))


;; Create a stream of 6 messages to put on to the channel.
(go
  (dotimes [message 6]
    (Thread/sleep 1200)
    (>! channel-stream message)))


;;
;; Blocking channel in listening mode

(def channel-buffered (chan 1))


(go
  (dotimes [message 24]
    (>!! channel-buffered message)))


(go
  (while true
    (Thread/sleep 3600)
    (println (str (<!! channel-buffered) " Mississippi"))))


;;
;; Go blocks are lightweight processes, not bound to threads

;; Create 1000 go blocks, each with a channel

(let [number-of-channels 1000
      channel-sequence (repeatedly number-of-channels chan)
      begin (System/currentTimeMillis)]

  (doseq [channel channel-sequence]
    (go (>! channel "Hello")))

  (dotimes [i number-of-channels]
    (let [[value channel] (alts!! channel-sequence)]
      (assert (= "Hello" value))))

  (println "Read" number-of-channels "messages in"
           (- (System/currentTimeMillis) begin) "ms"))
