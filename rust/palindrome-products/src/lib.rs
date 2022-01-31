#![feature(int_log)]

#[derive(Debug, PartialEq, Eq)]
pub struct Palindrome {
    factors: Vec<(u64, u64)>,
    value: u64,
}

impl Palindrome {
    pub fn new(a: u64, b: u64) -> Palindrome {
        Palindrome {
            factors: vec![(a, b)],
            value: a * b,
        }
    }

    pub fn insert(&mut self, a: u64, b: u64) {
        self.factors.push((a, b))
    }
}

pub fn is_palindrome(value: u64) -> bool {
    let exponent = value.log10() + 1; // 1 for exclusive
    let mid = exponent / 2;

    (0..mid)
        .zip(((mid + exponent % 2)..exponent).rev())
        .fold(true, |acc, (x, y)| {
            acc && value / 10_u64.pow(x) % 10 == value / 10_u64.pow(y) % 10
        })
}

// Doesn't work in four digits factors.
// How could it be optimized?
pub fn palindrome_products(min: u64, max: u64) -> Option<(Palindrome, Palindrome)> {
    (min..=max)
        .flat_map(|i: u64| (i..=max).map(move |j: u64| (i, j)))
        .filter(|(x, y)| is_palindrome(x * y))
        .fold(None, |acc, (x, y)| {
            let value = x * y;

            match acc {
                None => Some((Palindrome::new(x, y), Palindrome::new(x, y))),
                Some((mut _min, mut _max)) => {
                    let next_min = if _min.value == value {
                        _min.insert(x, y);
                        _min
                    } else if value < _min.value {
                        Palindrome::new(x, y)
                    } else {
                        _min
                    };

                    let next_max = if _max.value == value {
                        _max.insert(x, y);
                        _max
                    } else if _max.value < value {
                        Palindrome::new(x, y)
                    } else {
                        _max
                    };

                    Some((next_min, next_max))
                }
            }
        })
}
