module Atbash (decode, encode) where

import Data.Maybe (fromMaybe)
import qualified Data.Map as M
import qualified Data.Char as C
import qualified Data.List.Split as LS

plain :: String
plain = ['a'..'z']

dic :: M.Map Char Char
dic = M.fromList $ zip plain (reverse plain)

withId :: (Ord a) => a -> M.Map a a -> a
withId x = fromMaybe x <$> M.lookup x

isLetterOrNumber :: Char -> Bool
isLetterOrNumber = (||) <$> C.isLetter <*> C.isNumber

decode :: String -> String
decode = map (`withId` dic) . filter isLetterOrNumber

encode :: String -> String
encode = group' 5 . encode' . filter isLetterOrNumber
  where
    group' n = unwords . LS.chunksOf n
    encode' = map ((`withId` dic) . C.toLower)
