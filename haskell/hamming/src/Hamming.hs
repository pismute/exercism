module Hamming (distance) where

zipMayAll :: Eq a => [a] -> [a] -> [(Maybe a, Maybe a)]
zipMayAll xs ys =
  takeWhile (/=(Nothing, Nothing)) $ zip (infin xs) (infin ys)
  where
    infin = (++(repeat Nothing)) . (Just <$>)

distance :: Eq a => [a] -> [a] -> Maybe Int
distance xs ys =
  length . filter (uncurry (/=)) <$> (mapM valid $ zipMayAll xs ys)
  where
    valid (_, Nothing) = Nothing
    valid (Nothing, _) = Nothing
    valid (Just x, Just y) = Just (x,y)
