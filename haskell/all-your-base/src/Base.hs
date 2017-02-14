module Base (rebase) where

import qualified Data.List as L

rebase :: Integral a => a -> a -> [a] -> Maybe [a]
rebase inputBase outputBase inputDigits = do
  _ <- validBase' inputBase
  _ <- validBase' outputBase
  _ <- validDigits' inputBase inputDigits
  return $ from10Base outputBase $ to10Base inputBase inputDigits
  where
    validBase' x = if x < 2 then Nothing else Just x

    validDigits' = traverse . f'
      where
        f' x y = if y < 0 || x <= y then Nothing else Just y

    g' x a b = a*x + b
    to10Base x = foldl (g' x) 0

    h' _ 0 = Nothing
    h' x z = Just (z `mod` x, z `div` x)
    from10Base y z = reverse $ L.unfoldr (h' y) z
