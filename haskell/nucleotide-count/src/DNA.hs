module DNA (count, nucleotideCounts) where

import Data.Map (Map, fromList)
import qualified Data.Set as Set

acgt = "ACGT"
acgtSet = Set.fromList acgt

count :: Char -> String -> Either String Int
count nucleotide nucleotides
  | all (`elem` acgtSet) (nucleotide : nucleotides) =
    (Right . length . filter (==nucleotide)) nucleotides
  | otherwise =
    Left nucleotides

nucleotideCounts :: String -> Either String (Map Char Int)
nucleotideCounts nucleotides = fromList <$> (acgt `zip`) <$> traverse (`count` nucleotides) acgt
