module Series (Error(..), largestProduct) where

import Data.List.Split (divvy)
import qualified Data.Char as C
import qualified Data.List as L

data Error = InvalidSpan | InvalidDigit Char deriving (Show, Eq)

largestProduct :: Int -> String -> Either Error Integer
largestProduct n xs =
  (if n >= 0 then Right undefined else Left InvalidSpan)
  >> (if n <= length xs then Right undefined else Left InvalidSpan)
  >> (case L.find (not . C.isDigit) xs of
        Nothing -> Right undefined
        Just x -> Left $ InvalidDigit x)
  >> (Right $ maximum . products' n $ toIntegers' xs)
  where
    toIntegers' = map (toInteger . C.digitToInt)
    products' _ [] = [1]
    products' n' xs' = map product $ divvy n' 1 xs'
