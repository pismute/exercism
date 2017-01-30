{-# LANGUAGE FlexibleInstances #-}
module SecretHandshake (handshake, secretWords) where

import Numeric (readInt)
import qualified Data.Char as C
import Data.Bits ((.&.), shiftL)
import Data.Maybe (fromMaybe)

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

secretWords =
  [ (16, reverse)
  , (1, ("wink":))
  , (2, ("double blink":))
  , (4, ("close your eyes":))
  , (8, ("jump":))
  ]

handshake n =
  foldr (say n) [] secretWords
  where
    say n (bit, f) xs =
      if bit == (bit .&. (toInt n))
        then f xs
        else xs
