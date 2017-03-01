module CryptoSquare (encode) where

import qualified Data.Char as C
import qualified Data.List as L
import qualified Data.List.Split as LS

encode :: String -> String
encode xs = let
  ys = map C.toLower $ filter C.isAlphaNum xs
  n = ceiling . sqrt $ length' ys
  in unwords . L.transpose $ LS.chunksOf n ys
  where
    length' :: [a] -> Double
    length' zs = fromIntegral $ length zs
