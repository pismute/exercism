module DNA (count, nucleotideCounts) where

import qualified Data.Map as M
import qualified Data.Set as S

acgt = S.fromList "ACGT"
zeroNucleotides = M.fromList $ (\x-> (x, 0)) <$> (S.toList acgt)

validNucleotide :: Char -> Either String Char
validNucleotide nucleotide
  | elem nucleotide acgt = Right nucleotide
  | otherwise = Left $ "unknown nucleotide: " ++ [nucleotide]

nucleotideCounts :: String -> Either String (M.Map Char Int)
nucleotideCounts nucleotides = (foldr increment zeroNucleotides) <$> traverse validNucleotide nucleotides
  where
    increment = flip (M.insertWith (+)) 1

count :: Char -> String -> Either String Int
count nucleotide nucleotides =
  do x <- validNucleotide nucleotide
     xs <- nucleotideCounts nucleotides
     return $ xs M.! x
