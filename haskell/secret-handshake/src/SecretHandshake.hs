{-# LANGUAGE FlexibleInstances #-}
module SecretHandshake (handshake, secretWords) where

import Numeric (readInt)
import qualified Data.Char as C
import Data.Bits ((.&.))

class ToInt a where
  toInt :: a -> Int

instance ToInt Int where
  toInt = id

instance ToInt String where
  toInt = parseInt . readBinary
    where
      readBinary = readInt 2 (`elem` "01") C.digitToInt
      parseInt [(n,"")] = n
      parseInt _ = 0

secretWords :: [(Int, [[Char]] -> [[Char]])]
secretWords =
  [ (16, reverse)
  , (1, ("wink":))
  , (2, ("double blink":))
  , (4, ("close your eyes":))
  , (8, ("jump":))
  ]

handshake :: ToInt a => a -> [[Char]]
handshake n =
  foldr (say n) [] secretWords
  where
    say code (bit, f) xs =
      if bit == (bit .&. (toInt code))
        then f xs
        else xs
