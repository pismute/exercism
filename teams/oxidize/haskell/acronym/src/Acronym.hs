module Acronym (abbreviate) where

import Data.List
import qualified Data.Char as Char
import qualified Data.Set as Set
import qualified Data.List.Split as Split

abbreviate :: String -> String
abbreviate xs = filter Char.isUpper $ intercalate "" $ fmap capitalize $ fmap nomalize $ words' xs
  where
    delims = Set.fromList " -,"

    words' :: String -> [String]
    words' = Split.splitWhen (`elem` delims)

    capitalize :: String -> String
    capitalize [] = []
    capitalize (y:ys) = Char.toUpper y : ys

    isAllCaps :: String -> Bool
    isAllCaps = all Char.isUpper

    nomalize ys
      | isAllCaps ys = fmap Char.toLower ys
      | otherwise = ys
