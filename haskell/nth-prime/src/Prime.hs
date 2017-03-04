module Prime (nth) where

import Control.Monad (guard)

nth :: Int -> Maybe Integer
nth n = do
  guard $ n > 0
  return . head . drop (n-1) $ filter isPrime' [2..]
  where
    sqrtFloor' = floor . (sqrt :: Double -> Double) . fromIntegral
    isFactorOf' x = (==0) . (x `mod`)
    isPrime' x = not $ any (isFactorOf' x) [2..(sqrtFloor' x)]
