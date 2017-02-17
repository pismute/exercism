module Matrix
    ( Matrix
    , cols
    , column
    , flatten
    , fromList
    , fromString
    , reshape
    , row
    , rows
    , shape
    , transpose
    ) where

import Control.Monad (guard, join)
import qualified Data.Vector as V

data Matrix a =
  Matrix { flatten :: V.Vector a
         , rows :: Int
         , cols :: Int
         } deriving (Eq, Show)

column :: Int -> Matrix a -> V.Vector a
column n x = do
  guard $ 0 <= n && n < cols x
  (i, y) <- V.imap f' $ flatten x
  guard $ i `mod` cols x == n
  return y
  where
    f' i y = (i, y)

fromList :: [[a]] -> Matrix a
fromList xss = let
  cs = if null xss || null (head xss) then 0 else length $ head xss
  rs = if cs == 0 then 0 else length xss
  in Matrix (V.fromList $ join xss) rs cs

fromString :: Read a => String -> Matrix a
fromString = fromList . map parse' . lines
  where
    parse' s = case reads s of
                [(x, xs)] -> x : parse' xs
                _         -> []

reshape :: (Int, Int) -> Matrix a -> Matrix a
reshape (r, c) x = x { rows = r, cols = c }

row :: Int -> Matrix a -> V.Vector a
row n x = do
  guard $ 0 <= n && n < rows x
  V.take (cols x) $ V.drop (n * cols x) $ flatten x

shape :: Matrix a -> (Int, Int)
shape x = (rows x, cols x)

transpose :: Matrix a -> Matrix a
transpose x = let
  (r, c) = shape x
  ys = (>>= id) . V.fromList $ map (`column` x) [0..(c-1)]
  in Matrix ys c r
