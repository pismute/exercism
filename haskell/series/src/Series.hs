module Series (slices) where

import qualified Data.Char as C
import qualified Data.List.Split as LS

slices :: Int -> String -> [[Int]]
slices 0 _ = [[]]
slices n xs = LS.divvy n 1 $ C.digitToInt <$> xs
