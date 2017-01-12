module Phone (areaCode, number, prettyPrint) where

import qualified Data.Char as C
import qualified Data.List.Split as LS

areaCode :: String -> Maybe String
areaCode = (take 3 <$>) . number

number :: String -> Maybe String
number = validNumber . filter C.isDigit
  where
    validNumber [] = Nothing
    validNumber (x: xs) =
      case (x, length xs) of
        ('1', 10) -> Just xs
        (_, 9) -> Just (x:xs)
        _ -> Nothing

prettyPrint :: String -> Maybe String
prettyPrint xs = do
  nr <- number xs
  let [area, three, four]  = LS.splitPlaces [3,3,4] nr
  return $ "(" ++ area ++ ") " ++ three ++ "-" ++ four
