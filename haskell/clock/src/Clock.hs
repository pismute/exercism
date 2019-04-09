module Clock (clockHour, clockMin, fromHourMin, toString, addDelta) where


import Text.Printf (printf)


data Clock = Clock { clockHour :: Int
                   , clockMin :: Int
                   } deriving (Eq, Show)


instance Num Clock where
  (Clock h m) + (Clock h' m') = fromHourMin (h + h') (m + m')

  (Clock h m) - (Clock h' m') = fromHourMin (h - h') (m - m')

  fromInteger = fromHourMin 0 . fromIntegral

  negate x = Clock 0 0 - x

  _ * _ = error "not support"
  abs _ = error "not support"
  signum _ = error "not support"

fromHourMin :: Int -> Int -> Clock
fromHourMin h m = let
  (h', m') = normalize' m 60
  (_, h'') = normalize' (h + h') 24
  in Clock h'' m'
  where
    normalize' x base = let
      (a, b) = x `divMod` base
      in if b < 0
           then (a - 1, b + base)
           else (a, b)


toString :: Clock -> String
toString x = printf "%02d:%02d" (clockHour x) (clockMin x)


addDelta :: Int -> Int -> Clock -> Clock
addDelta h m clock = clock + (fromHourMin h m)
