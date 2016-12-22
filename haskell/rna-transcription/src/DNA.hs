module DNA (toRNA) where

rnaStrand =
  [('G', 'C'),
   ('C', 'G'),
   ('T', 'A'),
   ('A', 'U')]

toRNA :: String -> Maybe String
-- toRNA = mapM (`lookup` rnaStrand)
toRNA = traverse (`lookup` rnaStrand)
