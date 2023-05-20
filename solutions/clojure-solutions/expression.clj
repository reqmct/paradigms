(require 'clojure.math)
(defn constant [value] (fn [variables] value))
(defn variable [name]
  (fn [variables]
    (variables name)
    )
  )

(defn makeOperation [operation]
  (fn [& args]
    (fn [variables]
      (apply operation (mapv #(% variables) args))
      )
    )
  )

(defn divideWithInf [& args]
  (if (== 1 (count args))
    (/ 1.0 (nth args 0))
    (reduce
      (fn [arg1, arg2] (/ (double arg1) (double arg2)))
      args)
    ))

(def add (makeOperation +))
(def subtract (makeOperation -))
(def multiply (makeOperation *))
(def divide (makeOperation divideWithInf))
(defn negate [argument] (subtract argument))
(defn sumexpOperation [& args] (apply + (mapv clojure.math/exp args)))
(def sumexp (makeOperation sumexpOperation))
(defn ln [argument] ((makeOperation clojure.math/log) argument))
(defn lse [& args] (ln (apply sumexp args)))
(def allOperation {'+ add, '- subtract, '* multiply, '/ divide, 'negate negate, 'lse lse, 'sumexp sumexp})
(defn abstractParser [constant, variable, allOperation, expression]
  (letfn
    [(parseExpression [element]
       (cond
         (number? element) (constant element)
         (symbol? element) (variable (str element))
         (list? element) (apply (allOperation (peek element)) (mapv parseExpression (pop element)))
         )
       )
     ]
    (parseExpression (read-string expression))
    )
  )
(defn parseFunction [expression] (abstractParser constant variable allOperation expression))

(load-file "baseClass.clj")

(def args (field :args))
(def evaluate (method :evaluate))
(def toString (method :toString))
(def diff (method :diff))
(def toStringInfix (method :toStringInfix))

(defn makeExpression [_evaluate, _toString, _diff, _toStringInfix]
  (constructor
    (fn [this & args]
      (assoc this :args args)
      )
    {:evaluate      _evaluate,
     :toString      _toString,
     :diff          _diff
     :toStringInfix _toStringInfix
     }
    )
  )

(def ZERO)
(def Constant
  (makeExpression
    (fn [this vars] (double (first (args this))))
    (fn [this] (str (first (args this))))
    (fn [this name] ZERO)
    (fn [this] (str (first (args this))))
    )
  )
(def ZERO (Constant 0))
(def ONE (Constant 1))

(def Variable
  (makeExpression
    (fn [this vars] (vars (clojure.string/lower-case (str (first (first (args this)))))))
    (fn [this] (first (args this)))
    (fn [this name] (if (= name (first (args this))) ONE ZERO))
    (fn [this] (first (args this)))
    )
  )

(defn makeObjectOperation [operation, operationSymbol, diffRule]
  (makeExpression
    (fn [this vars] (apply operation (mapv #(evaluate % vars) (args this))))
    (fn [this] (str "(" operationSymbol (if (= 0 (count (args this))) " " "")
                    (apply str (mapv #(str " " (toString %)) (args this))) ")"))
    (fn [this name] (apply diffRule name (args this)))
    (fn [this] (if (= 1 (count (args this)))
                 (str operationSymbol " " (toStringInfix (first (args this))) )
                 (str "(" (clojure.string/join (str " " operationSymbol " ")
                                               (map toStringInfix (args this))) ")")
                 )
      )
    )
  )

(def Add
  (makeObjectOperation
    +
    "+",
    (fn [name & args] (apply Add (mapv #(diff % name) args)))
    )
  )

(def Subtract
  (makeObjectOperation
    -
    "-",
    (fn [name & args] (apply Subtract (mapv #(diff % name) args)))
    )
  )

(def Negate
  (makeObjectOperation
    -
    "negate",
    (fn [name arg] (Negate (diff arg name)))
    )
  )

(def Multiply)
(def Divide)

(defn binaryDiffMultiply [name prev u]
  (let [[v v'] prev
        u' (diff u name)
        ]
    [(Multiply v u)
     (Add (Multiply u' v) (Multiply u v'))
     ]
    )
  )

(defn massDiffMultiply [name & args]
  (nth
    (reduce (partial binaryDiffMultiply name) [ONE, ZERO] args)
    1)
  )

(defn binaryDiffDivide [name v u]
  (let [v' (diff v name)
        u' (diff u name)]
    (Divide (Subtract (Multiply v' u) (Multiply v u')) (Multiply u u))
    )
  )

(defn massDiffDivide [name, firstArg, & args]
  (if (= 0 (count args))
    (binaryDiffDivide name ONE firstArg)
    (binaryDiffDivide name firstArg (apply Multiply args))
    )
  )

(def Multiply
  (makeObjectOperation
    *
    "*",
    massDiffMultiply
    )
  )

(def Divide
  (makeObjectOperation
    divideWithInf
    "/",
    massDiffDivide
    )
  )


(defn makeCompositeObjectOperation [expression, operationSymbol]
  (makeExpression
    (fn [this vars] (evaluate (apply expression (args this)) vars))
    (fn [this] (str "(" operationSymbol (if (= 0 (count (args this))) " " "")
                    (apply str (mapv #(str " " (toString %)) (args this))) ")"))
    (fn [this name] (diff (apply expression (args this)) name))
    (fn [this] (if (= 1 (count (args this)))
                 (str operationSymbol "(" (toStringInfix (first (args this))) ")")
                 (str "(" (clojure.string/join (str " " operationSymbol " ")
                                               (map toStringInfix (args this))) ")")
                 )
      )
    )
  )


(defn meansqFunction [& args]
  (Divide (apply Add (mapv #(Multiply % %) args)) (Constant (count args)))
  )

(def Meansq (makeCompositeObjectOperation meansqFunction, "meansq"))

(def Sqrt (makeObjectOperation
            clojure.math/sqrt,
            "sqrt",
            (fn [name arg] (Multiply (diff arg name)
                                     (Divide (Constant 1) (Multiply (Constant 2) (Sqrt arg))))
              )
            )
  )

(defn rmsFunction [& args]
  (Sqrt (apply meansqFunction args))
  )

(def RMS (makeCompositeObjectOperation rmsFunction, "rms"))

(defn makeBooleanOperation [operation, operationSymbol]
  (makeObjectOperation
    (fn [& args] (apply operation (map #(if (> % 0) 1 0) args)))
    operationSymbol
    (fn [name & args] (apply (makeBooleanOperation operation operationSymbol)
                             (mapv #(diff % name) args)))
    )
  )

(def And (makeBooleanOperation bit-and "&&"))
(def Or (makeBooleanOperation bit-or "||"))
(def Xor (makeBooleanOperation bit-xor "^^"))
(def Not (makeBooleanOperation #(- 1 %) "!"))

(def allObjectOperation {'+   Add, '- Subtract, '* Multiply, '/ Divide, 'negate Negate, 'meansq Meansq,
                         'rms RMS, '&& And, '|| Or, (symbol "^^") Xor, '! Not})

(defn parseObject [expression] (abstractParser Constant Variable allObjectOperation expression))

(load-file "parser.clj")

(def +seqarr (partial +seqf concat))
(def *digit (+char "0123456789"))
(def *number
  (+map read-string
        (+str
          (+seqarr
            (+seq (+opt (+char "-")))
            (+plus *digit)
            (+seq (+opt (+char ".")))
            (+star *digit)
            )
          )
        )
  )


(def *all-chars (mapv char (range 0 128)))
(def *letter (+char (apply str (filter #(Character/isLetter %) *all-chars))))
(def *space (+char (apply str (filter #(Character/isWhitespace %) *all-chars))))
(def *ws (+ignore (+star *space)))
(def *identifier (+str (+seqf cons *letter (+star (+or *letter *digit)))))


(def *const (+map Constant *number))
(def *variable (+map Variable *identifier))

(defn *symbols [symbols] (apply +seq (map (comp +char str) (str symbols))))
(defn *operationSymbol [operationSymbol]
  (+map
    (constantly (get allObjectOperation (symbol operationSymbol)))
    (*symbols operationSymbol)
    ))
(defn *operationSymbols [operationSymbols] (apply +or (map *operationSymbol operationSymbols)))

(defn *operation [prevOperation operationSymbols]
  (+seqarr *ws (+seq prevOperation)
           (+star (+seq (*operationSymbols operationSymbols) prevOperation)) *ws
           ))


(defn *makeObjectExpression [first, & args]
  (reduce (fn [left, [operation, right]] (operation left right))
          first,
          args
          )
  )

(defn *makeBinaryOperation [prevOperation operationSymbols]
  (+map (partial apply *makeObjectExpression) (*operation prevOperation operationSymbols))
  )

(def *primitive)

(defn *unaryOperation [operationSymbols]
  (+seq (*operationSymbols operationSymbols) *ws (delay *primitive)))

(defn *makeUnaryOperation [operationSymbols]
  (+map
    (fn [[operation, argument]] (operation argument))
    (*unaryOperation operationSymbols))
  )


(def *booleanOp3)
(def *primitive (+map first
                      (+seq *ws
                            (+or (*makeUnaryOperation ["negate", "!"])
                                 *const
                                 *variable
                                 (+seqn 1 (+char "(")
                                        *ws (delay *booleanOp3) *ws (+char ")"))
                                 )
                            *ws)))
(def *priority1 (*makeBinaryOperation *primitive ["*", "/"]))
(def *priority2 (*makeBinaryOperation *priority1 ["+", "-"]))
(def *booleanOp1 (*makeBinaryOperation *priority2 ["&&"]))
(def *booleanOp2 (*makeBinaryOperation *booleanOp1 ["||"]))
(def *booleanOp3 (*makeBinaryOperation *booleanOp2 ["^^"]))
(def parseObjectInfix (+parser *booleanOp3))
