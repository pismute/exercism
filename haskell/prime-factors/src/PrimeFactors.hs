module PrimeFactors (primeFactors) where

import qualified Data.List as L

primeFactors :: Integer -> [Integer]
primeFactors = L.unfoldr f'
  where
    f' x
      | x < 2 = Nothing
      | otherwise = g' <$> L.find ((==0) . (x `mod`)) [2..]
      where
        g' y = (y, x `div` y)