module Hamming (distance) where

zipWithExactMay :: (a -> b -> c) -> [a] -> [b] -> Maybe [c]
zipWithExactMay _ [] [] = Just []
zipWithExactMay f (x:xs) (y:ys) =
  (f x y: ) <$> (zipWithExactMay f xs ys)
zipWithExactMay _ _ _ = Nothing

distance :: Eq a => [a] -> [a] -> Maybe Int
distance xs ys =
  length . filter id <$> zipWithExactMay (/=) xs ys
