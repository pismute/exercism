module Phone (areaCode, number, prettyPrint) where

import qualified Data.Char as C

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
  let (area, ys)  = splitAt 3 nr
  let (three, four) = splitAt 3 ys
  return $ "(" ++ area ++ ") " ++ three ++ "-" ++ four
