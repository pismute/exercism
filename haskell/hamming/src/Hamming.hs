module Hamming (distance) where

zipWithExactMay :: (a -> b -> c) -> [a] -> [b] -> Maybe [c]
zipWithExactMay with [] [] = Just []
zipWithExactMay with [] _ = Nothing
zipWithExactMay with _ [] = Nothing
zipWithExactMay with (x:xs) (y:ys) =
  (with x y: ) <$> (zipWithExactMay with xs ys)

distance :: Eq a => [a] -> [a] -> Maybe Int
distance xs ys =
  length . filter id <$> zipWithExactMay (/=) xs ys
