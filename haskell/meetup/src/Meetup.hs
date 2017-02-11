module Meetup (Weekday(..), Schedule(..), meetupDay) where

import Data.Time.Calendar (Day, toGregorian, fromGregorian, addDays)
import Data.Time.Calendar.WeekDate (toWeekDate)
import Data.Maybe (fromJust)
import Data.List (find)
import Data.Function (on)

data Weekday = Monday
             | Tuesday
             | Wednesday
             | Thursday
             | Friday
             | Saturday
             | Sunday
             deriving (Enum, Bounded, Ord, Eq)

after :: (Ord a, Enum a, Bounded a) => a -> a -> Int
a `after` b
  | a == b = 0
  | a < b = (maxBound `after` b) + (a `after` minBound) + 1
  | otherwise = ((-) `on` fromEnum) a b

nextWeekday :: Weekday -> Day -> Day
nextWeekday x day = let
  (_, _, week) = toWeekDate day
  weekday = toEnum (week - 1) :: Weekday
  monthDays = toInteger $ x `after` weekday
  in addDays monthDays day

data Schedule = First
              | Second
              | Third
              | Fourth
              | Last
              | Teenth
              deriving (Enum)

dayOf :: Schedule -> [Day] -> Day
dayOf Teenth = fromJust . find isTeenth
  where
    isTeenth x = let
      (_, _, monthDay) = toGregorian x
      in 13 <= monthDay && monthDay < 20
dayOf Last = last
dayOf x = head . drop (fromEnum x)

meetupDay :: Schedule -> Weekday -> Integer -> Int -> Day
meetupDay schedule weekday year month = let
  first = nextWeekday weekday $ fromMonthDay 1
  final = fromMonthDay 31
  weekdays = takeWhile (<=final) $ map (`addDays` first) [0, 7..]
  in dayOf schedule weekdays
  where
    fromMonthDay :: Int -> Day
    fromMonthDay = fromGregorian year month
