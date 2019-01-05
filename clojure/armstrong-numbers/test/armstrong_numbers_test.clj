(ns armstrong-numbers-test
  (:require [clojure.test :refer [deftest is testing]]
            [armstrong-numbers :refer [armstrong? digits pow]]))

(deftest digits-21897142587612075
  (testing "Single digit numbers are Armstrong numbers"
    (is (digits 21897142587612075) '(2 1 8 9 7 1 4 2 5 8 7 6 1 2 0 7 5))))

(deftest pow-4-5
  (testing "Single digit numbers are Armstrong numbers"
    (is (pow 4 5) 1)))

(deftest armstrong-number-5
  (testing "Single digit numbers are Armstrong numbers"
    (is (armstrong? 5))))

(deftest not-armstrong-number-10
  (testing "There are no 2 digit Armstrong numbers"
    (is (not (armstrong? 10)))))

(deftest armstrong-number-153
  (testing "Three digit number that is an Armstrong number"
    (is (armstrong? 153))))

(deftest not-armstrong-number-100
  (testing "Three digit number that is not an Armstrong number"
    (is (not (armstrong? 100)))))

(deftest armstrong-number-9474
  (testing "Four digit number that is an Armstrong number"
    (is (armstrong? 9474))))

(deftest not-armstrong-number-9475
  (testing "Four digit number that is not an Armstrong number"
    (is (not (armstrong? 9476)))))

(deftest armstrong-number-9926315
  (testing "Seven digit number that is an Armstrong number"
    (is (armstrong? 9926315))))

(deftest not-armstrong-number-9926314
  (testing "Seven digit number that is not an Armstrong number"
    (is (not (armstrong? 9926314)))))

(deftest armstrong-number-21897142587612075
  (testing "Seventeen digit number that is an Armstrong number"
    (is (armstrong? 21897142587612075))))
