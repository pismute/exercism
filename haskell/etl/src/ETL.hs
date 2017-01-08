{-# LANGUAGE TupleSections #-}
module ETL (transform) where

import qualified Data.Map as M
import qualified Data.Char as C

transform :: M.Map a String -> M.Map Char a
transform = M.fromList . (>>= uncurry transformOne) . M.toList . ((map C.toLower) <$>)
  where
    transformOne a = ((, a) <$>)
