module School (School, add, empty, grade, sorted) where

import Data.List (sort)
import qualified Data.Map as M

type School = M.Map Int [String]
type Grades = [(Int, [String])]

add :: Int -> String -> School -> School
add g name = M.insertWith (flip (++)) g [name]

empty :: School
empty = M.empty

grade :: Int -> School -> [String]
grade = M.findWithDefault []

sorted :: School -> Grades
sorted = M.toList . M.map sort
