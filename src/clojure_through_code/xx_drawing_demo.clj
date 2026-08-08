;; A very simple example of using Java Swing library.
;; Convert to a LightTable instarepl to see the graphics change as you change the code
;; Use the "live" toggle button if you want to make a change without generating graphics

(ns xx-drawing-demo
  (:import
    (java.awt
      Dimension)
    (javax.swing
      JFrame
      JPanel)))


(defn make-panel
  []
  (let [panel (proxy [JPanel] []
                (paintComponent
                  [g]
                  (.drawLine g  50 50 100 200)))]
    (doto panel
      (.setPreferredSize (Dimension. 300 400)))))


(defn make-frame
  [panel]
  (doto (new JFrame)
    (.add panel)
    .pack
    .show))


(make-frame (make-panel))
