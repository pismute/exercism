{-# LANGUAGE TupleSections #-}
module Scrabble (scoreLetter, scoreWord) where

import qualified Data.Map as M
import qualified Data.Char as C

scores :: [(String, Int)]
scores =
  [("AEIOULNRST", 1),
   ("DG", 2),
   ("BCMP", 3),
   ("FHVWY", 4),
   ("K", 5),
   ("JX", 8),
   ("QZ", 10)]

scoreDict :: M.Map Char Int
scoreDict = M.fromList $ scores >>= (uncurry expand)
  where
    expand xs n = (, n) <$> xs

scoreLetter :: Char -> Int
scoreLetter =
  flip (M.findWithDefault 0) scoreDict . C.toUpper

scoreWord :: [Char] -> Int
scoreWord =
  sum . map scoreLetter
