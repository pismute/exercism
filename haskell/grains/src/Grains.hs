module Grains (square, total) where

import Data.Maybe

square n
        | n > 0 && n < 65 = Just (2 ^ (n - 1))
        | otherwise = Nothing

total = sum $ map (fromJust . square) [1..64]
