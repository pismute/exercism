#![feature(iter_intersperse)]

use lazy_static::lazy_static;
use regex::Regex;

lazy_static! {
    static ref CONSONANTS: Regex =
        Regex::new("^(xr|yt|ch|thr|th|sch|qu|squ|rh|[^aeiou])(y?)(.*)").unwrap();
}

fn ay(word: &str) -> String {
    let captures = CONSONANTS
        .captures(word)
        .map(|x| {
            x.iter()
                .skip(1)
                .map(|y| y.map(|z| z.as_str()))
                .collect::<Option<Vec<_>>>()
        })
        .flatten()
        .unwrap_or_else(|| vec![]);

    match captures.as_slice() {
        ["xr" | "yt", ..] => word.to_string() + "ay",
        [consonants, "y", xs] => "y".to_string() + xs + consonants + "ay",
        [consonants, "", xs] => xs.to_string() + consonants + "ay",
        _ => word.to_string() + "ay",
    }
}

pub fn translate(input: &str) -> String {
    input
        .split_whitespace()
        .map(ay)
        .intersperse(" ".to_string())
        .collect()
}
