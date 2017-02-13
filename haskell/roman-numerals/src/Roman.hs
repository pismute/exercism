module Roman (numerals) where

import qualified Data.List as L
import qualified Data.Map as M

romans :: M.Map Int String
romans = M.fromList
    [ (1000, "M")
    , (900, "CM")
    , (500, "D")
    , (400, "CD")
    , (100, "C")
    , (90, "XC")
    , (50, "L")
    , (40, "XL")
    , (10, "X")
    , (9, "IX")
    , (5, "V")
    , (4, "IV")
    , (1, "I")
    ]


numerals :: Int -> Maybe String
numerals = Just . (>>= id) . L.unfoldr f'
  where
    g' x (y, z) = (z, x-y)

    f' 0 = Nothing
    f' x = g' x <$> M.lookupLE x romans
