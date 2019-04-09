module Allergies (Allergen(..), allergies, isAllergicTo) where

import Data.Bits ((.&.))

data Allergen = Eggs
              | Peanuts
              | Shellfish
              | Strawberries
              | Tomatoes
              | Chocolate
              | Pollen
              | Cats
              deriving (Eq, Enum, Show)

allAllergen :: [Allergen]
allAllergen = enumFrom Eggs

fromAllergen :: Allergen -> Int
fromAllergen = (2^) . fromEnum

allergies :: Int -> [Allergen]
allergies x = filter (`isAllergicTo` x) allAllergen

isAllergicTo :: Allergen -> Int -> Bool
isAllergicTo x y = let
  score = fromAllergen x
  in score .&. y == score