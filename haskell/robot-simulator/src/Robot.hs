{-# LANGUAGE DeriveAnyClass  #-}
module Robot
    ( Bearing(East,North,South,West)
    , bearing
    , coordinates
    , mkRobot
    , simulate
    , turnLeft
    , turnRight
    ) where

class (Enum a, Bounded a, Eq a) => Circular a where
  prev :: a -> a
  prev x
    | x == minBound = maxBound
    | otherwise = pred x

  next :: a -> a
  next x
    | x == maxBound = minBound
    | otherwise = succ x

data Bearing = North
             | East
             | South
             | West
             deriving (Eq, Show, Enum, Bounded, Circular)

data Robot = Robot { bearing :: Bearing
                   , coordinates :: (Integer, Integer)
                   } deriving (Eq, Show)

mkRobot :: Bearing -> (Integer, Integer) -> Robot
mkRobot = Robot

turnLeft :: Bearing -> Bearing
turnLeft = prev

turnRight :: Bearing -> Bearing
turnRight = next

simulate :: Robot -> String -> Robot
simulate = foldl (flip moveOn)
  where
    advance North x y = (x, y+1)
    advance East x y = (x+1, y)
    advance South x y = (x, y-1)
    advance West x y = (x-1, y)

    turn robot f = robot { bearing = f $ bearing robot }

    move robot f = robot { coordinates = f $ coordinates robot }

    moveOn 'L' robot = robot `turn` turnLeft
    moveOn 'R' robot = robot `turn` turnRight
    moveOn 'A' robot = robot `move` (uncurry (advance $ bearing robot))
