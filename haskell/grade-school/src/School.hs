module School (School, add, empty, grade, sorted) where

import Data.Function (on)
import Data.List (sortBy, sort)

type Grade = (Int, [String])
type School = [Grade]

add :: Int -> String -> School -> School
add grade name grades =
  case span ((/=grade) . fst) grades of
    (ys, []) -> ys ++ [(grade, [name])]
    (ys, (z : zs)) -> ys ++ [(grade, (++[name]) . snd $ z)] ++ zs

empty :: School
empty = []

grade :: Int -> School -> [String]
grade g grades = maybe [] id $ lookup g grades

sorted :: School -> School
sorted grades = sortBy (compare `on` fst) $ (sort <$>) <$> grades
