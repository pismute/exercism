{-# LANGUAGE TupleSections #-}

module DNA (nucleotideCounts, Nucleotide(..)) where

import qualified Data.Map as M
import Text.Read (readEither)
import Control.Monad (foldM)
import Data.Bifunctor (first)

data Nucleotide = A | C | G | T deriving (Eq, Ord, Show, Enum, Read)

zeroNucleotides :: M.Map Nucleotide Int
zeroNucleotides = M.fromList $ (, 0) <$> enumFrom A

nucleotideCounts :: String -> Either String (M.Map Nucleotide Int)
nucleotideCounts =
  foldM accumulate zeroNucleotides
  where
    accumulate acc x = (\y -> M.adjust (+1) y acc) <$> readNucleotide [x]

    readNucleotide :: String -> Either String Nucleotide
    readNucleotide xs = first ((xs ++) . (": " ++)) $ readEither xs
