module Accumulate (accumulate) where

accumulate :: (a -> b) -> [a] -> [b]

-- accumulate _ [] = []
-- accumulate fn (x:xs) = fn x : accumulate fn xs

-- accumulate fn xs = foldr (\x acc-> fn x:acc) [] xs

accumulate fn = foldr ((:) . fn) []