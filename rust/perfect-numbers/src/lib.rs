use std::iter::once;

#[derive(Debug, PartialEq, Eq)]
pub enum Classification {
    Abundant,
    Perfect,
    Deficient,
}

pub fn classify(num: u64) -> Option<Classification> {
    use Classification::*;

    let aliquot_sum: u64 = (2..)
        .take_while(|x| num / x > *x)
        .filter(|x| num % x == 0)
        .flat_map(|x| once(x).chain(once(num / x)))
        .sum::<u64>()
        + 1;

    match aliquot_sum {
        1 => {
            if num == 0 {
                None
            } else {
                Some(Deficient) // prime
            }
        }
        x if x == num => Some(Perfect),
        x if x > num => Some(Abundant),
        x if x < num => Some(Deficient),
        _ => None,
    }
}
