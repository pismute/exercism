module Triplet (tripletsWithSum) where

import Control.Monad (guard)

tripletsWithSum :: Int -> [(Int, Int, Int)]
tripletsWithSum x = do
  let aMax = x `div` 3 - 1
  a <- [1..aMax]
  let bMin = a + 1
  let bMax = (x - a) `div` 2
  b <- [bMin..bMax]
  let c = x - a - b
  guard $ a ^ (2 :: Int) + b ^ (2 :: Int) == c ^ (2 :: Int)
  return (a, b, c)
