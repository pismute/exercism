module Triangle (TriangleType(..), triangleType) where

import qualified Data.List as L

data TriangleType = Equilateral
                  | Isosceles
                  | Scalene
                  | Illegal
                  deriving (Eq, Show)

triangleType a b c = whichType $ L.sort [a, b, c]
  where
   whichType [a, b, c]
      | a <= 0 || a + b < c = Illegal
      | a == b && b == c = Equilateral
      | a == b || b == c = Isosceles
      | otherwise = Scalene
