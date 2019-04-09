module Bob (responseFor) where

import Data.Char
import Data.List

responseFor :: String -> String
responseFor s
  | isSilence s = "Fine. Be that way!"
  | isShoutingQuestion s = "Calm down, I know what I'm doing!"
  | isShouting s = "Whoa, chill out!"
  | isQuestion s = "Sure."
  | otherwise = "Whatever."
  where
    trim = reverse . dropWhile isSpace . reverse . dropWhile isSpace
    isSilence = null . trim
    isShouting x = (any isUpper x) && (not $ any isLower x)
    isQuestion = ("?" `isSuffixOf`) . trim
    isShoutingQuestion x = isShouting x && isQuestion x
