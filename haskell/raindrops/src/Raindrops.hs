module Raindrops (convert) where

import qualified Data.Map as M

pliaong = M.fromList [(3, "Pling"), (5, "Plang"), (7, "Plong")]

convert n =
  case (pliaong M.!) <$> factors n of
    [] -> show n
    xs -> xs >>= id
  where
    factors n = [ i | i <- M.keys pliaong, n `mod` i == 0]
