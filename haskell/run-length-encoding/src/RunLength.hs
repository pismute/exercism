module RunLength (decode, encode) where

import qualified Data.List as L
import qualified Data.Char as C

decode :: String -> String
decode [] = []
decode xs = let
  (ns, ys) = span C.isDigit xs
  n = parseCount ns
  h = head ys
  zs = tail ys
  in replicate n h ++ decode zs
  where
    parseCount [] = 1
    parseCount xs = (read xs :: Int)

encode :: String -> String
encode = (>>= codify) . L.group
  where
    format 1 = ""
    format n = show n
    codify s = (format $ length s) ++ [head s]
