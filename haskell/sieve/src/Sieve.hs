module Sieve (primesUpTo) where

primesUpTo :: Integral a => a -> [a]
primesUpTo limit = sieve [2..limit]
  where
    sieve [] = []
    sieve (x:xs) = x : sieve (filter ((/=0) . (`mod` x)) xs)