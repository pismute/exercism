module Beer (song) where

import qualified Data.List as L
import qualified Data.Char as C

song :: String
song = sing 99
  where
    capitalize [] = []
    capitalize (x:xs) = C.toUpper x : xs

    bottles 0 suffix = "no more bottles" ++ suffix
    bottles 1 suffix = "1 bottle" ++ suffix
    bottles n suffix = show n ++ " bottles" ++ suffix

    one n prefix suffix = prefix ++ (if n == 1 then "it" else "one") ++ suffix

    bottlesOfBeer n =
      n `bottles` " of beer on the wall, " ++ n `bottles` " of beer.\n" ++
      if n == 0
        then "Go to the store and buy some more, 99 bottles of beer on the wall.\n"
        else let it = one n in
          "Take " `it` " down and pass it around, " ++ (n-1) `bottles` " of beer on the wall.\n"

    sing n = L.intercalate "\n" $ (capitalize . bottlesOfBeer) <$> [n, n-1..0]
