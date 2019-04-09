module Series (slices) where

import qualified Data.Char as C
import qualified Data.List.Split as LS

slices :: Int -> String -> [[Int]]
slices n xs =
  let ys = LS.divvy n 1 $ C.digitToInt <$> xs
  in if n == 0 -- Why do I need it?
    then [] : ys
    else ys
