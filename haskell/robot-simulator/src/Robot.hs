module Robot
    ( Bearing(East,North,South,West)
    , bearing
    , coordinates
    , mkRobot
    , simulate
    , turnLeft
    , turnRight
    ) where

data Bearing = North
             | East
             | South
             | West
             deriving (Eq, Show, Enum)

data Robot = Robot { bearing :: Bearing
                   , coordinates :: (Integer, Integer)
                   } deriving (Eq, Show)

mkRobot :: Bearing -> (Integer, Integer) -> Robot
mkRobot = Robot

turnLeft :: Bearing -> Bearing
turnLeft North = West
turnLeft bearing = pred bearing

turnRight :: Bearing -> Bearing
turnRight West = North
turnRight bearing = succ bearing

simulate :: Robot -> String -> Robot
simulate robot xs = foldl (\acc f -> f acc) robot $ map moveOn xs
  where
    advance North x y = (x, y+1)
    advance East x y = (x+1, y)
    advance South x y = (x, y-1)
    advance West x y = (x-1, y)

    turn f robot = robot { bearing = f $ bearing robot }

    move f robot = robot { coordinates = f $ coordinates robot }

    moveOn 'L' robot = turn turnLeft robot
    moveOn 'R' robot = turn turnRight robot
    moveOn 'A' robot = move (uncurry (advance $ bearing robot)) robot
