module Triplet (isPythagorean, mkTriplet, pythagoreanTriplets) where

import Control.Monad (guard)
import qualified Data.List as L

isPythagorean :: (Ord a, Floating a) => [a] -> Bool
isPythagorean xs = let
  [a, b, c] = L.sort xs
  in a**2 + b**2 == c**2

mkTriplet :: a -> a -> a -> [a]
mkTriplet a b c = [a, b, c]

pythagoreanTriplets :: (Ord a, Floating a, Enum a) => a -> a -> [[a]]
pythagoreanTriplets start top = do
  a <- [start..top]
  b <- [a..top]
  c <- [b..top]
  guard . isPythagorean $ mkTriplet a b c
  return [a, b, c]
