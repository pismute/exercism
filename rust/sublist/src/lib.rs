#![feature(half_open_range_patterns)]
#![feature(exclusive_range_pattern)]

#[derive(Debug, PartialEq)]
pub enum Comparison {
    Equal,
    Sublist,
    Superlist,
    Unequal,
}

pub fn sublist<T: PartialEq + std::fmt::Debug>(first_list: &[T], second_list: &[T]) -> Comparison {
    let diff = first_list.len() as i64 - second_list.len() as i64;

    let (bigger, smaller) = if diff >= 0 {
        (first_list, second_list)
    } else {
        (second_list, first_list)
    };

    let is_sub = smaller.is_empty() || bigger.windows(smaller.len()).any(|xs| xs == smaller);

    match (diff, is_sub) {
        (0, true) => Comparison::Equal,
        (1.., true) => Comparison::Superlist,
        (..0, true) => Comparison::Sublist,
        _ => Comparison::Unequal,
    }
}
