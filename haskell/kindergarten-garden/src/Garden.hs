module Garden
    ( Plant (..)
    , defaultGarden
    , garden
    , lookupPlants
    ) where

import qualified Data.List as L
import Data.List.Split (chunksOf)
import Data.Function (on)
import qualified Data.Map as M

data Plant = Clover
           | Grass
           | Radishes
           | Violets
           deriving (Eq, Show)

toPlant x
  | x == 'C' = Clover
  | x == 'G' = Grass
  | x == 'R' = Radishes
  | x == 'V' = Violets
  | otherwise = error $ x : ": unknown plant"

toPlants = map toPlant

defaultStudents =
    ["Alice", "Bob", "Charlie", "David",
     "Eve", "Fred", "Ginny", "Harriet",
     "Ileana", "Joseph", "Kincaid", "Larry"]

defaultGarden :: String -> M.Map String [Plant]
defaultGarden = garden defaultStudents

garden :: [String] -> String -> M.Map String [Plant]
garden xs = M.fromList . zip (L.sort xs) . parseGarden
  where
    combine (ys, zs) = ys ++ zs
    parseGarden xs = let
      (ys, _:zs) = span (/='\n') xs
      in toPlants <$> combine <$> (zip `on` chunksOf 2) ys zs

lookupPlants :: String -> M.Map String [Plant] -> [Plant]
lookupPlants = flip (M.!)
