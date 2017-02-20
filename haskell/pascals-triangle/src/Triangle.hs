module Triangle (rows) where

import qualified Data.List.Split as LS

rows :: Int -> [[Integer]]
rows n = reverse $ foldr f' [] [0..(n-1)]
  where
    next' [] = [1]
    next' xs = [1] ++ (sum <$> LS.divvy 2 1 xs) ++ [1]
    f' _ xs = (:xs) . next' $
      if null xs
        then []
        else head xs
