module SumOfMultiples (sumOfMultiples) where

import Data.List as List

sumOfMultiples :: Integral a => [a] -> a -> a
sumOfMultiples xs limit =
  sum $ List.nub $ xs >>= (`multiples` limit)
  where
    multiples x limit =
      (filter (/=limit) . map (*x)) [1..(limit `div` x)]
