module DNA (toRNA) where

import qualified Data.Map as Map
import qualified Data.List as List

rnaStrand =
  Map.fromList
    [('G', 'C'),
     ('C', 'G'),
     ('T', 'A'),
     ('A', 'U')]

toRNA :: String -> Maybe String
toRNA = (foldr combine $ Just []) . (fmap lookup)
  where
    lookup = (`Map.lookup` rnaStrand)
    combine maybe1 maybe2 =
      do m1 <- maybe1
         m2 <- maybe2
         return (m1 : m2)
