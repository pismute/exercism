#![feature(iter_intersperse)]

use lazy_static::lazy_static;
use std::collections::HashMap;

lazy_static! {
    static ref NUMBERS: HashMap<u64, &'static str> = vec![
        (1, "one"),
        (2, "two"),
        (3, "three"),
        (4, "four"),
        (5, "five"),
        (6, "six"),
        (7, "seven"),
        (8, "eight"),
        (9, "nine"),
        (10, "ten"),
        (11, "eleven"),
        (12, "twelve"),
        (13, "thirteen"),
        (14, "fourteen"),
        (15, "fifteen"),
        (16, "sixteen"),
        (17, "seventeen"),
        (18, "eighteen"),
        (19, "nineteen"),
        (20, "twenty"),
        (30, "thirty"),
        (40, "forty"),
        (50, "fifty"),
        (60, "sixty"),
        (70, "seventy"),
        (80, "eighty"),
        (90, "ninety"),
        (100, "hundred"),
    ]
    .into_iter()
    .collect();
    static ref THOUSANDS: Vec<(u64, &'static str)> = vec![
        (1_000_000_000_000_000_000, " quintillion"),
        (1_000_000_000_000_000, " quadrillion"),
        (1_000_000_000_000, " trillion"),
        (1_000_000_000, " billion"),
        (1_000_000, " million"),
        (1_000, " thousand"),
        (1, ""),
    ];
}

fn say_99(n: u64) -> Option<String> {
    let ten = if n < 20 { 0 } else { n % 100 / 10 };
    let one = if n < 20 { n % 20 } else { n % 10 };

    let num: String = NUMBERS
        .get(&(ten * 10))
        .into_iter()
        .chain(NUMBERS.get(&one).into_iter())
        .copied()
        .intersperse("-")
        .collect();

    if num.is_empty() {
        None
    } else {
        Some(num)
    }
}

fn say_900(n: u64) -> Option<String> {
    let hundred = n / 100;

    let num: String = NUMBERS
        .get(&hundred)
        .into_iter()
        .flat_map(|x| std::iter::once(x).chain(NUMBERS.get(&100).into_iter()))
        .copied()
        .intersperse(" ")
        .collect();

    if num.is_empty() {
        None
    } else {
        Some(num)
    }
}

fn say_999(n: u64) -> Option<String> {
    let num: String = say_900(n % 1000)
        .into_iter()
        .chain(say_99(n % 100).into_iter())
        .intersperse(" ".to_string())
        .collect();

    if num.is_empty() {
        None
    } else {
        Some(num)
    }
}

pub fn encode(n: u64) -> String {
    if n == 0 {
        "zero".to_string()
    } else {
        THOUSANDS
            .iter()
            .filter_map(|(base, en)| match n / base {
                0 => None,
                div => Some((div % 1000, en)),
            })
            .filter_map(|(num, en)| say_999(num).map(|x| x + en))
            .intersperse(" ".to_string())
            .collect()
    }
}
