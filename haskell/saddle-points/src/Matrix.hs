module Matrix (saddlePoints) where

import qualified Data.Array as A
import Control.Monad (guard)

saddlePoints :: (A.Ix t, Ord a) => A.Array (t, t) a -> [(t, t)]
saddlePoints xss = do
  ((i, j), x) <- A.assocs xss
  guard $ x == minimum (row' j xss)
  guard $ x == maximum (col' i xss)
  return (i, j)
  where
    row' x array = let
      ((li, _), (ui, _)) = A.bounds array
      in A.ixmap (li, ui) (\y -> (y, x)) array
    col' y array = let
      ((_, lj), (_, uj)) = A.bounds array
      in A.ixmap (lj, uj) (\x -> (y, x)) array
