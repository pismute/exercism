module Atbash (decode, encode) where

import Data.Maybe (mapMaybe)
import qualified Data.Map as M
import qualified Data.Char as C
import qualified Data.List.Split as LS

dic :: M.Map Char Char
dic = M.fromList $ zip (alphas ++ numbers) ((reverse alphas) ++ numbers)
  where
    alphas = ['a'..'z']
    numbers = ['0'..'9']

decode :: String -> String
decode = mapMaybe (`M.lookup` dic)

encode :: String -> String
encode = unwords . LS.chunksOf 5 . decode . map C.toLower