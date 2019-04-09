module Sublist (sublist) where

-- The task is to create the data type `Sublist`, with `Eq` and
-- `Show` instances, and implement the function `sublist`.

import qualified Data.List as L
--import qualified Data.List.Split as LS

sublist :: Eq a => [a] -> [a] -> Maybe Ordering
sublist sub super
        | sub == super = Just EQ
        | isSublist sub super = Just LT
        | isSublist super sub = Just GT
        | otherwise = Nothing
        where
          -- this one is too slow
          -- sliding'' n = LS.divvy n 1

          -- sliding' n = LS.chop (\xs -> (take n xs , drop 1 xs))

          sliding n = map (take n) . L.tails

          isSublist xs = any (==xs) . sliding (length xs)
