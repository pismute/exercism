module Raindrops (convert) where

import qualified Data.Map as M

pliaong :: M.Map Int String
pliaong = M.fromList [(3, "Pling"), (5, "Plang"), (7, "Plong")]

convert :: Int -> String
convert n =
  case (pliaong M.!) <$> factors n of
    [] -> show n
    xs -> xs >>= id
  where
    factors x = [ i | i <- M.keys pliaong, x `mod` i == 0]
