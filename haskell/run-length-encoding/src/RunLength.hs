module RunLength (decode, encode) where

import qualified Data.List as L
import qualified Data.Char as C

decode :: String -> String
decode = (>>= snd) . reverse . (drop 1) . foldl decodeOne [(0, "")]
  where
    decodeOne ((n, _) : xs) a =
      if C.isDigit a
        then (n*10 + C.digitToInt a, "") : xs
        else (0, "") : (0, replicate (max n 1) a) : xs

encode :: String -> String
encode = (>>= codify) . L.group
  where
    format 1 = ""
    format n = show n
    codify s = (format $ length s) ++ [head s]
