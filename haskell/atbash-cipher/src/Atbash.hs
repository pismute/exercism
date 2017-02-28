module Atbash (decode, encode) where

import qualified Data.Map as M
import qualified Data.Char as C
import qualified Data.List.Split as LS

plain :: String
plain = ['a'..'z']

dic :: M.Map Char Char
dic = M.fromList $ zip plain (reverse plain)

findWithId :: (Ord a) => a -> M.Map a a -> a
findWithId = M.findWithDefault <*> id

isLetterOrNumber :: Char -> Bool
isLetterOrNumber = (||) <$> C.isLetter <*> C.isNumber

decode :: String -> String
decode = map (`findWithId` dic) . filter isLetterOrNumber

encode :: String -> String
encode = group' 5 . encode' . filter isLetterOrNumber
  where
    group' n = unwords . LS.chunksOf n
    encode' = map ((`findWithId` dic) . C.toLower)
