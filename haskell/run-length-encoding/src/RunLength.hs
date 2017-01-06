module RunLength (decode, encode) where

import qualified Data.List as L
import qualified Data.Char as C

decode :: String -> String
decode xs = let
  Right ys = sequence . reverse $ foldl decodeOne [] xs
  in ys >>= id
  where
    decodeOne :: [Either Int String] -> Char -> [Either Int String]
    decodeOne (Left n: xs) a =
      if C.isDigit a
        then (Left $ n*10 + C.digitToInt a) : xs
        else (Right $ replicate n a) : xs
    decodeOne xs a =
      if C.isDigit a
        then (Left $ C.digitToInt a) : xs
        else (Right $ [a]) : xs

encode :: String -> String
encode = (>>= codify) . L.group
  where
    format 1 = ""
    format n = show n
    codify s = (format $ length s) ++ [head s]
