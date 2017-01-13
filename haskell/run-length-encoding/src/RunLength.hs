module RunLength (decode, encode) where

import qualified Data.List as L
import qualified Data.Char as C

decode :: String -> String
decode [] = []
decode xs = let
  (ns, y:ys) = span C.isDigit xs
  n = parseCount ns
  in replicate n y ++ decode ys
  where
    parseCount [] = 1
    parseCount xs = (read xs :: Int)

encode :: String -> String
encode = (>>= codify) . L.group
  where
    format 1 = ""
    format n = show n
    codify s = (format $ length s) ++ [head s]
