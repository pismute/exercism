module Triangle (rows) where

import qualified Data.List as L
import qualified Data.List.Split as LS

rows :: Int -> [[Integer]]
rows n = L.unfoldr (uncurry f') (n, [])
  where
    next' [] = [1]
    next' xs = [1] ++ (sum <$> LS.divvy 2 1 xs) ++ [1]
    f' m xs
      | m <= 0 = Nothing
      | otherwise = let
        ys = next' xs
        in Just (ys, (m - 1, ys))
