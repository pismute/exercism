module DNA (toRNA) where

rnaStrand =
  [('G', 'C'),
   ('C', 'G'),
   ('T', 'A'),
   ('A', 'U')]

toRNA :: String -> Maybe String
toRNA = (foldr combine $ Just []) . (fmap find)
  where
    find = (`lookup` rnaStrand)
    combine maybe1 maybe2 =
      do m1 <- maybe1
         m2 <- maybe2
         return (m1 : m2)
