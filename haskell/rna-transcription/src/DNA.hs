module DNA (toRNA) where

import qualified Data.Map as M

rnaStrand :: M.Map Char Char
rnaStrand =
  M.fromList [('G', 'C'), ('C', 'G'), ('T', 'A'), ('A', 'U')]

lookupEither :: Char -> Either Char Char
lookupEither x =
  case rnaStrand M.!? x of
    Nothing -> Left x
    Just y -> Right y

toRNA :: String -> Either Char String
-- toRNA = mapM (`lookup` rnaStrand)
toRNA = traverse lookupEither
