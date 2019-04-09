module Anagram (anagramsFor) where

import qualified Data.List as L
import qualified Data.Char as C

anagramsFor :: String -> [String] -> [String]
anagramsFor xs = filter isAnagram
  where
    lower = toLowers xs
    sorted = L.sort lower
    toLowers = map C.toLower
    isAnagram zs
      | lower /= toLowers zs &&
        sorted == (L.sort . toLowers) zs = True
      | otherwise = False