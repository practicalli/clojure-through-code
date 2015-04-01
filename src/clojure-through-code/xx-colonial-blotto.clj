;; Everyone's heard of the Prisoner's Dilemma, but I hadn't heard
;; until recently of Colonel Blotto, which was another foundation of
;; Game Theory.

;; Colonel Blotto and his opponent each have 100 troops, and between
;; them are 10 hills.

;; They must allocate troops to the hills, without knowing each
;; other's decisions.

;; On each hill, the larger number of troops wins, and the side which
;; wins the most battles wins overall.

;; Imagine that Blotto sends ten troops to each hill, but his cunning
;; enemy sends 11 troops to nine hills, and sends one poor soldier on
;; a suicide mission to the tenth.

;; Blotto loses 9-1, which is a pearl-handled revolver performance.

;; In the next battle, his successor cunningly sends 12 troops to the
;; first eight hills, four to hill nine, and none to hill 10.

;; The enemy, however, has anticipated this, and sent one man to claim
;; the undefended hill ten, five to the lightly defended hill nine,
;; and thirteen to most of the others. Another stunning victory.

;; Even though it was originally thought of as a simple war game,
;; Blotto is a better model for election campaigning!

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Suppose that a colonial empire (let us without loss of generality
;; call them "The British") is desperately trying to hang on to its
;; last foreign possession against the heroic guerilla tactics of a
;; bunch of terrorists (the "Rebels"). The terrorists are badly
;; outnumbered, but agile.

;; The British are a slow moving bunch, who will divide their large
;; force equally among the strategic targets that they need to
;; protect.

;; The Rebels have, as well as the second mover advantage of getting
;; to see their enemy's troop distribution, the choice of how many
;; fronts to fight on.

;; They win if they manage to win on a majority of fronts.

;; Suppose that the British have four thousand men
(def colonial-troops 4000)
;; And the rebels have 1500 
(def rebel-troops 1500)

;; How many fronts should the rebels choose to fight on?

;; Suppose 7 is the rebel commander's lucky number
(def fronts 7)

;; The British send 571 men to each front
(quot colonial-troops fronts) ;-> 571

;; Which we might choose to represent as:
(repeat fronts (quot colonial-troops fronts))

;; Which leaves them with a reserve of three
(- colonial-troops (* fronts min-colonial)) ;-> 3

;; Which we might choose to represent as: 
(set! *print-length* 25)
(concat (repeat 3 1) (repeat 0))

;; They distribute their reserve also:
(def colonial-dist (map + 
                        (repeat fronts (quot colonial-troops fronts))
                        (concat (repeat 3 1) (repeat 0))))

;; To leave a final distribution:
colonial-dist ;-> (572 572 572 571 571 571 571)

(assert (= (reduce + colonial-dist) colonial-troops) "grr")

;; We can summarize this in the following function:
(defn colonial-troop-allocation[troops fronts]
  (let [min-colonial (quot troops fronts)
        excess (- troops (* min-colonial fronts))
        excess-dist (concat (repeat excess 1) (repeat 0))
        min-dist (repeat fronts min-colonial)
        colonial-dist (map + min-dist excess-dist)]
    (assert (= (reduce + colonial-dist) troops) "grr")
    colonial-dist))

(colonial-troop-allocation 120 1) ;-> (120)
(colonial-troop-allocation 120 2) ;-> (60 60)
(colonial-troop-allocation 120 3) ;-> (40 40 40)

(colonial-troop-allocation 225 3) ;-> (75 75 75)
(colonial-troop-allocation 225 5) ;-> (45 45 45 45 45)
(colonial-troop-allocation 225 7) ;-> (33 32 32 32 32 32 32)
(colonial-troop-allocation 225 8) ;-> (29 28 28 28 28 28 28 28)

(colonial-troop-allocation 7 8) ;-> (1 1 1 1 1 1 1 0)

;; Now the rebels have an easier task. They know how many fronts they
;; have to win on, and they only have to win by one soldier on each
;; front, so they can work out a winning allocation so:

(defn rebels-allocation [colonial-allocation]
  (let [majority (inc (quot (count colonial-allocation) 2))]
    (map inc (take majority (sort colonial-allocation)))))
        
    
(defn rebels-needed-for-rebellion [colonial-troops fronts]
  (let [ca (colonial-troop-allocation colonial-troops fronts)
        rn (rebels-allocation ca)]
    (reduce + rn)))

(map (partial rebels-needed-for-rebellion colonial-troops) (iterate inc 1)) 
;-> (4001 4002 2668 3003 2403 2670 2288 2505 2225 2406 2186 2338 2159 2292 2139 2259 2124 2230 2111 2211 2101 2192 2098 2176 2093 ...)

;; To win on one front, they need 4001 brave comrades. 
;; To win on two, they need 4002 (one win and one draw doesn't quite cut it).  
;; To win on three, they only need 2668.
;; As the number of fronts goes up, the number appears to be settling down 
;; to around 2000, which is a nice result.

;; "A perfectly informed flexible force fighting an enemy with a
;; uniform order-of-battle needs more than half as many troops to win"

;; How would you advise the rebel commander?

;; What advice would you give the Brits?
