module Robot
    ( Bearing(East,North,South,West)
    , bearing
    , coordinates
    , mkRobot
    , move
    ) where

prev :: (Eq a, Enum a, Bounded a) => a -> a
prev x
  | x == minBound = maxBound
  | otherwise = pred x

next :: (Eq a, Enum a, Bounded a) => a -> a
next x
  | x == maxBound = minBound
  | otherwise = succ x

data Bearing = North
             | East
             | South
             | West
             deriving (Eq, Show, Enum, Bounded)

data Robot = Robot { bearing :: Bearing
                   , coordinates :: (Integer, Integer)
                   } deriving (Eq, Show)

mkRobot :: Bearing -> (Integer, Integer) -> Robot
mkRobot = Robot

turnLeft :: Bearing -> Bearing
turnLeft = prev

turnRight :: Bearing -> Bearing
turnRight = next

move :: Robot -> String -> Robot
move = foldl (flip moveOn)
  where
    advance North x y = (x, y+1)
    advance East x y = (x+1, y)
    advance South x y = (x, y-1)
    advance West x y = (x-1, y)

    turn robot f = robot { bearing = f $ bearing robot }

    move' robot f = robot { coordinates = f $ coordinates robot }

    moveOn 'L' robot = robot `turn` turnLeft
    moveOn 'R' robot = robot `turn` turnRight
    moveOn 'A' robot = robot `move'` uncurry (advance $ bearing robot)
    moveOn _ _ = error "unexpected case"
