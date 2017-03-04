module WordCount (wordCount) where

import qualified Data.List as L
import qualified Data.List.Split as LS
import qualified Data.Char as C

wordCount :: String -> [(String, Int)]
wordCount xs = do
  w <- L.group . L.sort . words' $ toLower' xs
  return (head w, length w)
  where
    toLower' = map C.toLower
    isWordChar' = (||) <$> (== '\'') <*> C.isAlphaNum
    drop' x =
      if head x == '\'' && last x == '\''
        then init $ tail x
        else x
    words' = map drop' <$> LS.wordsBy (not . isWordChar')
