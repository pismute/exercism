{-# LANGUAGE FlexibleInstances #-}
module SecretHandshake (handshake, secretWords) where

import qualified Data.List as L
import qualified Data.Char as C
import Data.Bits ((.&.), shiftL)
import Data.Maybe (fromMaybe)

class ToInt a where
  toInt :: a -> Int

instance ToInt Int where
  toInt n = n

instance ToInt String where
  toInt xs = fromMaybe 0 $ (foldl f 0) <$> traverse validDigit xs
    where
      f a b = shiftL a 1 + C.digitToInt b
      validDigit x = if C.isDigit x then Just x else Nothing

secretWords =
  [ (16, reverse)
  , (1, wink)
  , (2, doubleBlink)
  , (4, closeYourEyes)
  , (8, jump)
  ]
  where
    wink = ("wink":)
    doubleBlink = ("double blink":)
    closeYourEyes = ("close your eyes":)
    jump = ("jump":)

handshake n =
  foldr say [] $ filter (isSecret n) secretWords
  where
    isSecret n (bit, _) = bit == (bit .&. (toInt n))
    say (_, f) xs = f xs
