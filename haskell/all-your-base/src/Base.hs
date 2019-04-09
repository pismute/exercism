module Base
        ( Error(..)
        , rebase
        )
where

import qualified Data.List     as L
import           Data.Maybe

data Error a = InvalidInputBase | InvalidOutputBase | InvalidDigit a
  deriving (Show, Eq)

rebase :: Integral a => a -> a -> [a] -> Either (Error a) [a]
rebase inputBase outputBase inputDigits =
  (validInputBase inputBase)
    >> (validOutputBase outputBase)
    >> (validInputDigits inputBase inputDigits)
    >> (Right $ from10Base outputBase $ to10Base inputBase inputDigits)
    where
        validInputBase x = if (2 > x) then Left InvalidInputBase else Right []
        validOutputBase x =
                if (2 > x) then Left InvalidOutputBase else Right []

        x `isBaseOf'` y = 0 <= x && x < y
        validInputDigits base xs =
                fromMaybe (Right xs)
                        $   (Left . InvalidDigit)
                        <$> L.find (not . (`isBaseOf'` base)) xs

        g' x a b = a * x + b
        to10Base x = foldl (g' x) 0

        h' _ 0 = Nothing
        h' x z = Just (z `mod` x, z `div` x)
        from10Base y z = reverse $ L.unfoldr (h' y) z
