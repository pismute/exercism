module Series (largestProduct) where

import Control.Monad (guard)
import Data.List.Split (divvy)
import qualified Data.Char as C

largestProduct :: Int -> String -> Maybe Integer
largestProduct n xs = do
  guard $ n >= 0
  guard $ n <= length xs
  guard $ all C.isDigit xs
  return $ maximum . products' n $ toIntegers' xs
  where
    toIntegers' = map (toInteger . C.digitToInt)
    products' _ [] = [1]
    products' n' xs' = map product $ divvy n' 1 xs'
