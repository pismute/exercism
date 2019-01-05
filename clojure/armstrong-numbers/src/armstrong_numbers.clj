(ns armstrong-numbers)

(defn digits [num]
  (loop [n num
         size 0
         acc '()]
    (if (< n 1)
      (list size acc)
      (recur (long (/ n 10))
             (inc size)
             (conj acc (mod n 10))))))

;; (long (Math/pow 9 17)) = 16677181699666570
;; (pow 9 17) = 16677181699666569
;; (is-not (Math/pow 9 17) (pow 9 17))
(defn pow [base exponent]
  (apply * (repeat exponent base)))

(defn armstrong [num]
  (let [[size nrs] (digits num)]
    (apply + (map #(pow % size) nrs))))

(defn armstrong? [num]
  (= num (armstrong num)))
