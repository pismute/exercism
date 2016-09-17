module LeapYear (isLeapYear) where

isLeapYear :: Int -> Bool
isLeapYear y
        | isLeapBy 400 = True
        | isLeapBy 100 = False
        | isLeapBy 4 = True
        | otherwise = False
        where
          isLeapBy = (==0) . (y `rem`)
