module Bob (responseFor) where

import Data.Char
import Data.List

responseFor :: String -> String
responseFor s
  | isSilence s = "Fine. Be that way!"
  | isShouting s = "Whoa, chill out!"
  | isQuestion s = "Sure."
  | otherwise = "Whatever."
  where
    trim = reverse . dropWhile isSpace . reverse . dropWhile isSpace
    isSilence = null . trim
    isShouting s = (any isUpper s) && (not $ any isLower s)
    isQuestion = ("?" `isSuffixOf`) . trim
