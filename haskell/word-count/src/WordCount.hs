module WordCount (wordCount) where

import qualified Data.List.Split as LS
import qualified Data.Char as C
import qualified Data.Map as M
import qualified Data.MultiSet as MultiSet

wordCount :: String -> M.Map String Int
wordCount xs = MultiSet.toMap . MultiSet.fromList . words' $ toLower' xs
  where
    toLower' = map C.toLower
    isWordChar' = (||) <$> (== '\'') <*> C.isAlphaNum
    drop' x =
      if head x == '\'' && last x == '\''
        then init $ tail x
        else x
    words' = map drop' <$> LS.wordsBy (not . isWordChar')
