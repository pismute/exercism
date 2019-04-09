module House (rhyme) where

import Text.Printf (printf)
import qualified Data.List as L

rhymes :: [(String, String)]
rhymes =
  [ ("_", "the horse and the hound and the horn")
  , ("belonged to", "the farmer sowing his corn")
  , ("kept", "the rooster that crowed in the morn")
  , ("woke", "the priest all shaven and shorn")
  , ("married", "the man all tattered and torn")
  , ("kissed", "the maiden all forlorn")
  , ("milked", "the cow with the crumpled horn")
  , ("tossed", "the dog")
  , ("worried", "the cat")
  , ("killed", "the rat")
  , ("ate", "the malt")
  , ("lay in", "the house that Jack built")
  ]

nRhymes :: Int
nRhymes = length rhymes

rhyme :: String
rhyme = L.intercalate "\n" $ phrase' <$> [0..(nRhymes - 1)]
  where
    it' 0 = "This"
    it' _ = "that"

    does' 0 _ = "is"
    does' _ (x, _) = x

    rhymes' x = drop (nRhymes - x - 1) rhymes

    sing' x xss = let
      xs = xss !! x
      it = it' x
      does = does' x xs
      whatever = snd xs
      in printf "%s %s %s" it does whatever

    phrase' x = let
      xss = rhymes' x
      period y = if y == x then ".\n" else ""
      sing'' z = sing' z xss ++ period z
      in L.intercalate "\n" $ sing'' <$> [0..x]