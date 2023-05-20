(defn checkerVector [vec, checker] (and (vector? vec) (every? checker vec)))
(defn isVector [vec] (checkerVector vec number?))
(defn checkerLen [checker, getSize, & source] (and (every? checker source) (apply = (map getSize source))))
(defn checkVectorLen [& vectors] (apply checkerLen isVector count vectors))
(defn applyOperation [operation, & source] (apply mapv operation source))

(defn vectorOperation [operation]
  (fn [& vectors]
    {:pre  [(apply checkVectorLen vectors)]
     :post [(isVector %)]
     }
    (apply (partial applyOperation operation) vectors))
  )
(def v+ (vectorOperation +))
(def v- (vectorOperation -))
(def v* (vectorOperation *))
(def vd (vectorOperation /))
(defn scalar [& vectors]
  {:pre  [(apply checkVectorLen vectors)]
   :post [(number? %)]
   }
  (apply + (apply v* vectors))
  )
(defn vect [& vectors]
  {:pre  [(apply (partial checkVectorLen [0, 0, 0]) vectors)]
   :post [(checkVectorLen [0, 0, 0] %)]
   }
  (reduce
    (fn [vec1, vec2]
      (let [[x1, y1, z1] vec1
            [x2, y2, z2] vec2
            ]
        (vector
          (- (* y1 z2) (* z1 y2)),
          (- (* z1 x2) (* x1 z2)),
          (- (* x1 y2) (* y1 x2)),
          )
        )
      )
    vectors
    )
  )

(defn v*s [vec, & numbers]
  {:pre  [(isVector vec) (every? number? numbers)]
   :post [(isVector %)]
   }
  (mapv (partial * (apply * numbers)) vec)
  )

(defn isMatrix [matrix]
  {:pre [(vector? matrix)]
   }
  (or (= [] matrix) (apply checkVectorLen matrix)))
(defn getMatrixSize [matrix] (mapv count matrix))
(defn checkMatrixLen [& matrices] (apply checkerLen isMatrix getMatrixSize matrices))

(defn matrixOperation [operation]
  (fn [& matrices]
    {:pre  [(apply checkMatrixLen matrices)]
     :post [(isMatrix %)]
     }
    (apply (partial applyOperation operation) matrices))
  )

(def m+ (matrixOperation v+))
(def m- (matrixOperation v-))
(def m* (matrixOperation v*))
(def md (matrixOperation vd))

(defn m*s [matrix, & numbers]
  {:pre  [(isMatrix matrix) (every? number? numbers)]
   :post [(isMatrix %)]
   }
  (let [number (apply * numbers)]
    (mapv #(v*s % number) matrix)
    )
  )
(defn m*v [matrix, & vectors]
  {:pre  [(isMatrix matrix) (every? isVector vectors)]
   :post [(isVector %)]
   }
  (let [vec (apply v* vectors)]
    (mapv #(scalar % vec) matrix)
    )
  )
(defn transpose [matrix]
  {:pre  [(isMatrix matrix)]
   :post [(isMatrix %)]
   }
  (apply mapv vector matrix))

(defn m*m [& matrices]
  {:pre  [(every? isMatrix matrices)]
   :post [(isMatrix %)]
   }
  (letfn [
          (multiply [matrix1, matrix2] (mapv (fn [vec] (mapv (partial scalar vec) (transpose matrix2))) matrix1))
          ]
    (reduce multiply matrices)
    )
  )

(defn isTensor [tensor] (or (number? tensor) (vector? tensor)))
(defn checkTensorLen [& tensors] (or (every? number? tensors) (apply checkerLen isTensor count tensors)))

(defn tensorOperation [operation]
  (fn [& tensors]
    {:pre [(apply checkTensorLen tensors)]
     :post [(apply checkTensorLen % (get tensors 0))]
     }
    (if (every? number? tensors)
      (apply operation tensors)
      (apply mapv (tensorOperation operation) tensors)
      )
    )
  )

(def t+ (tensorOperation +))
(def t- (tensorOperation -))
(def t* (tensorOperation *))
(def td (tensorOperation /))