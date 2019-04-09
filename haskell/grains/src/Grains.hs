module Grains (square, total) where

import Data.Maybe

square :: Integral a => a -> Maybe a
square n
        | n > 0 && n < 65 = Just (2 ^ (n - 1))
        | otherwise = Nothing

total :: Integer
total = sum $ map (fromJust . square) [1..64]
