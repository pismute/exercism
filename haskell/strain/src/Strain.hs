module Strain (keep, discard) where

discard :: (a -> Bool) -> [a] -> [a]
discard f xs = [ x | x <- xs, not $ f x ]

keep :: (a -> Bool) -> [a] -> [a]
keep f xs = [ x | x <- xs, f x ]
