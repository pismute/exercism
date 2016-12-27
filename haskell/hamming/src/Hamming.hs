module Hamming (distance) where

distance xs ys =
  if length xs /= length ys
    then Nothing
    else Just $ length $ filter (uncurry (/=)) $ zip xs ys
