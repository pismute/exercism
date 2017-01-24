{-# LANGUAGE TupleSections #-}
module Scrabble (scoreLetter, scoreWord) where

import qualified Data.Map as M
import qualified Data.Char as C

scores =
  [("AEIOULNRST", 1),
   ("DG", 2),
   ("BCMP", 3),
   ("FHVWY", 4),
   ("K", 5),
   ("JX", 8),
   ("QZ", 10)]

scoreDict = M.fromList $ scores >>= (uncurry expand)
  where
    expand xs n = (, n) <$> xs

scoreLetter c = M.findWithDefault 0 (C.toUpper c) scoreDict

scoreWord xs = sum $ scoreLetter <$> filter C.isLetter xs
