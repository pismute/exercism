module School (School, add, empty, grade, sorted) where

import Data.List (sort)
import qualified Data.Map as M

type School = M.Map Int [String]
type Grades = [(Int, [String])]

add :: Int -> String -> School -> School
add grade name school =
  M.alter append grade school
  where
    append (Nothing) = Just [name]
    append (Just xs) = Just $ xs ++ [name]

empty :: School
empty = M.empty

grade :: Int -> School -> [String]
grade = M.findWithDefault []

sorted :: School -> Grades
sorted = ((sort <$>) <$>) . M.toAscList
