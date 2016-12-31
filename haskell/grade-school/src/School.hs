module School (School, add, empty, grade, sorted) where

import Data.List (sort)
import qualified Data.Map as M
import Control.Applicative ((<|>))

type School = M.Map Int [String]
type Grades = [(Int, [String])]

add :: Int -> String -> School -> School
add grade name =
  M.alter (fmap (++ [name]) . (<|> Just [])) grade

empty :: School
empty = M.empty

grade :: Int -> School -> [String]
grade = M.findWithDefault []

sorted :: School -> Grades
sorted = M.toAscList . M.map sort
