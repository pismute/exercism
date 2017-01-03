{-# LANGUAGE TupleSections #-}
module DNA (count, nucleotideCounts) where

import qualified Data.Map as M
import qualified Data.Set as S

zeroNucleotides = M.fromList $ (, 0) <$> "ACGT"

validNucleotide :: Char -> Either String Char
validNucleotide nucleotide
  | M.member nucleotide zeroNucleotides = Right nucleotide
  | otherwise = Left $ "unknown nucleotide: " ++ [nucleotide]

nucleotideCounts :: String -> Either String (M.Map Char Int)
nucleotideCounts nucleotides =
  (foldr increment zeroNucleotides) <$> traverse validNucleotide nucleotides
  where
    increment = M.adjust succ

count :: Char -> String -> Either String Int
count nucleotide nucleotides = do
  x <- validNucleotide nucleotide
  xs <- nucleotideCounts nucleotides
  return $ xs M.! x
