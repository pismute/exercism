module SumOfMultiples (sumOfMultiples) where

import Data.List (nub)

sumOfMultiples :: Integral a => [a] -> a -> a
sumOfMultiples xs limit =
  sum . nub $ xs >>= multiples
  where
    multiples x =
      (filter (/=limit) . map (*x)) [1..(limit `div` x)]
