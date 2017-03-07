module WordCount (wordCount) where

import qualified Data.List.Split as LS
import qualified Data.Char as C
import qualified Data.MultiSet as MultiSet

wordCount :: String -> [(String, Int)]
wordCount xs = MultiSet.toOccurList . MultiSet.fromList . words' $ toLower' xs
  where
    toLower' = map C.toLower
    isWordChar' = (||) <$> (== '\'') <*> C.isAlphaNum
    drop' x =
      if head x == '\'' && last x == '\''
        then init $ tail x
        else x
    words' = map drop' <$> LS.wordsBy (not . isWordChar')
