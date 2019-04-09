module ETL (transform) where

import qualified Data.Map as M
import qualified Data.Char as C

transform :: M.Map a String -> M.Map Char a
transform xs = M.fromList $ do
  (k, ys) <- M.toList xs
  y <- ys
  return (C.toLower y, k)