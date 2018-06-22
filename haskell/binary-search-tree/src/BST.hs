module BST
    ( BST
    , bstLeft
    , bstRight
    , bstValue
    , empty
    , fromList
    , insert
    , singleton
    , toList
    ) where

data BST a = BST
  { bstValue :: Maybe a
  , bstLeft :: Maybe (BST a)
  , bstRight :: Maybe (BST a)
  } deriving (Eq, Show)

empty :: BST a
empty = BST None None None

fromList :: Ord a => [a] -> BST a
fromList = foldr insert Empty

insert :: Ord a => a -> BST a -> BST a
insert x Empty = singleton x
insert x (Node y l r)
  | x <= y = Node y (insert x l) r
  | otherwise = Node y l (insert x r)

singleton :: a -> BST a
singleton x = Bst (Just x) empty empty

toList :: BST a -> [a]
toList Empty = []
toList (Node x l r) = toList l ++ [x] ++ toList r
