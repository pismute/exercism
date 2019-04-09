module Strain (keep, discard) where

discard :: (a -> Bool) -> [a] -> [a]
discard f = keep (not . f)

keep :: (a -> Bool) -> [a] -> [a]
keep f = foldr g []
  where
    g x xs = if f x then x : xs else xs