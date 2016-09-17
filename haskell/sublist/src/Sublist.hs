module Sublist (Sublist(..), sublist) where

-- The task is to create the data type `Sublist`, with `Eq` and
-- `Show` instances, and implement the function `sublist`.

import Data.List

data Sublist = Equal | Sublist | Superlist | Unequal deriving(Eq, Show)

sublist sub super
        | sub == super = Equal
        | isSublist sub super = Sublist
        | isSublist super sub = Superlist
        | otherwise = Unequal
        where
          sliding n = map (take n) . tails
          isSublist sub = any (==sub) . sliding (length sub)
