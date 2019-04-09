module Phone (number) where

import qualified Data.Char as C
import qualified Data.List.Split as LS
import Control.Monad (guard)

number :: String -> Maybe String
number xs = do
  nr <- (validNumber . filter C.isDigit) xs
  let [area, exchange] = LS.splitPlaces [3 :: Int, 3] nr
  guard $ '1' < head area
  guard $ '1' < head exchange
  return $ nr
  where
    validNumber [] = Nothing
    validNumber (y: ys) =
      case (y, length ys) of
        ('1', 10) -> Just ys
        (_, 9) -> Just (y:ys)
        _ -> Nothing
