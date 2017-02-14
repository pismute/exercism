module Base (rebase) where

import Control.Monad (guard)
import qualified Data.List as L

rebase :: Integral a => a -> a -> [a] -> Maybe [a]
rebase inputBase outputBase inputDigits = do
  guard $ 2 <= inputBase
  guard $ 2 <= outputBase
  guard $ all (`isBaseOf'` inputBase) inputDigits
  return $ from10Base outputBase $ to10Base inputBase inputDigits
  where
    x `isBaseOf'` y = 0 <= x && x < y

    g' x a b = a*x + b
    to10Base x = foldl (g' x) 0

    h' _ 0 = Nothing
    h' x z = Just (z `mod` x, z `div` x)
    from10Base y z = reverse $ L.unfoldr (h' y) z
