use std::fmt;

const A_DAY: i32 = 24 * 60;
const AN_HOUR: i32 = 60;

#[derive(Debug)]
pub struct Clock {
    minutes: i32,
}

impl fmt::Display for Clock {
    fn fmt(&self, f: &mut fmt::Formatter) -> fmt::Result {
        write!(f, "{:02}:{:02}", self.hours(), self.minutes())
    }
}

impl PartialEq for Clock {
    fn eq(&self, other: &Self) -> bool {
        self.minutes.rem_euclid(A_DAY) == other.minutes.rem_euclid(A_DAY)
    }
}

impl Clock {
    fn hours(&self) -> i32 {
        self.minutes.rem_euclid(A_DAY).div_euclid(AN_HOUR)
    }

    fn minutes(&self) -> i32 {
        self.minutes.rem_euclid(AN_HOUR)
    }

    pub fn new(hours: i32, minutes: i32) -> Self {
        Self {
            minutes: hours * AN_HOUR + minutes,
        }
    }

    pub fn add_minutes(&self, minutes: i32) -> Self {
        Self {
            minutes: self.minutes + minutes,
        }
    }
}
