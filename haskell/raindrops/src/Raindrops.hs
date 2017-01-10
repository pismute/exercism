module Raindrops (convert) where

convert n =
  case foldr ((++) . pliaong) "" $ factors n of
    [] -> show n
    xs -> xs
  where
    factors n = [ i | i <- [1..n], n `mod` i == 0]
    pliaong 3 = "Pling"
    pliaong 5 = "Plang"
    pliaong 7 = "Plong"
    pliaong _ = ""
