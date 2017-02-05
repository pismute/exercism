module LinkedList
    ( LinkedList
    , datum
    , fromList
    , isNil
    , new
    , next
    , nil
    , reverseLinkedList
    , toList
    ) where

data LinkedList a = a :> LinkedList a | Nil

instance Foldable LinkedList where
   foldMap _ Nil = mempty
   foldMap f (x :> xs) = f x `mappend` foldMap f xs

   foldr _ z Nil = z
   foldr f z (x :> xs) = f x $ foldr f z xs

datum :: LinkedList a -> a
datum Nil = error "empty"
datum (a :> _) = a

fromList :: [a] -> LinkedList a
fromList = foldr (:>) Nil

isNil :: LinkedList a -> Bool
isNil Nil = True
isNil _ = False

new :: a -> LinkedList a -> LinkedList a
new = (:>)

next :: LinkedList a -> LinkedList a
next Nil = error "empty"
next (_ :> xs) = xs

nil :: LinkedList a
nil = Nil

reverseLinkedList :: LinkedList a -> LinkedList a
reverseLinkedList = foldl (flip (:>)) Nil

toList :: LinkedList a -> [a]
toList = foldr (:) []
