module DNA (count, nucleotideCounts) where

import Data.Map (Map, fromList)

acgt = "ACGT"

count :: Char -> String -> Either String Int
count nucleotide nucleotides
  | all (`elem` acgt) (nucleotide : nucleotides) = (Right . length . filter (==nucleotide)) nucleotides
  | otherwise = Left nucleotides

nucleotideCounts :: String -> Either String (Map Char Int)
nucleotideCounts nucleotides = fromList <$> (acgt `zip`) <$> traverse (`count` nucleotides) acgt
