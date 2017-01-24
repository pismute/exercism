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

scoreLetter =
  flip (M.findWithDefault 0) scoreDict . C.toUpper

scoreWord =
  sum . map scoreLetter
