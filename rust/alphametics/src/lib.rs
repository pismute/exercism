use itertools::Itertools;
use std::collections::{HashMap, HashSet};

const EMPTY_CHAR: char = ' ';

type Dict = HashMap<char, u8>;

pub fn unsafe_parse(input: &str) -> Vec<Vec<char>> {
    input
        .split("==")
        .flat_map(|xs| xs.split('+'))
        .map(|x| x.trim().chars().collect())
        .collect()
}

pub fn transpose(nrs: &[Vec<char>]) -> Vec<Vec<char>> {
    let nr_of_nrs = nrs.len();
    let max_len = nrs.iter().map(|xs| xs.len()).max().unwrap_or_else(|| 0);

    let mut ret = vec![vec![EMPTY_CHAR; nr_of_nrs]; max_len];

    for i in 0..nr_of_nrs {
        for j in 0..max_len {
            ret[max_len - 1 - j][nr_of_nrs - 1 - i] = nrs
                .get(i)
                .and_then(|xs| {
                    (j + xs.len())
                        .checked_sub(max_len)
                        .and_then(|jj| xs.get(jj))
                })
                .copied()
                .unwrap_or_else(|| EMPTY_CHAR);
        }
    }

    ret
}

pub fn dict_iter<'a>(
    non_zeros: &'a HashSet<char>,
    base_dict: &'a Dict,
    up_num: u64,
    digits: &'a [char],
) -> impl Iterator<Item = (u64, Dict)> + 'a {
    let trimed: Vec<char> = digits
        .into_iter()
        .filter(|x| **x != EMPTY_CHAR)
        .copied()
        .collect();
    let non_exists_letters: HashSet<char> = trimed
        .iter()
        .filter(|x| !base_dict.contains_key(*x))
        .copied()
        .collect();
    let right = trimed[0];
    let left: Vec<char> = trimed.iter().skip(1).copied().collect();

    let exists_digits: HashSet<u8> = base_dict.values().copied().collect();

    let non_zeros_indexes: Vec<usize> = non_exists_letters
        .iter()
        .enumerate()
        .filter_map(|(i, x)| if non_zeros.contains(x) { Some(i) } else { None })
        .collect();

    (0..10)
        .filter(move |x| !exists_digits.contains(x)) // filter out digits in the dictionary.
        .permutations(non_exists_letters.len())
        .filter(move |xs| non_zeros_indexes.iter().all(|i| xs[*i] != 0))
        .map(move |xs| {
            non_exists_letters
                .iter()
                .copied()
                .zip(xs.into_iter())
                .collect()
        })
        .filter_map(move |dict: Dict| {
            fn get_as_u64(d1: &Dict, d2: &Dict, x: &char) -> u64 {
                d1.get(x)
                    .or_else(|| d2.get(x))
                    .copied()
                    .unwrap_or_else(|| 0) as u64
            }

            let left_num: u64 = up_num
                + left
                    .iter()
                    .map(|x| get_as_u64(&base_dict, &dict, x))
                    .sum::<u64>();
            let right_num: u64 = get_as_u64(&base_dict, &dict, &right);

            if left_num % 10 == right_num {
                let mut next_dict = base_dict.clone();
                next_dict.extend(dict.iter());
                Some((left_num / 10, next_dict))
            } else {
                None
            }
        })
}

/*
   Solve the problem from rightmost numbers iteratively.

   For "I + BB = ILL", it builds the dictionary in a row

     from "I + B = L",
          "B + 'the number from previous step' = L" and
          "'the number from previous step' = I"

   It is faster than the brute force, but still takes around a minute for all tests
*/
pub fn solve(input: &str) -> Option<Dict> {
    let nrs = unsafe_parse(input);
    let transposed = transpose(nrs.as_slice());

    // leading zero is not allowed.
    let non_zeros: HashSet<char> = nrs.iter().flat_map(|xs| xs.first()).copied().collect();

    transposed
        .iter()
        .fold(vec![(0, HashMap::new())], |dicts, xs| {
            dicts
                .iter()
                .flat_map(|(up_num, dict)| dict_iter(&non_zeros, dict, *up_num, xs.as_slice()))
                .collect()
        })
        .pop() // assume multiple solution is impossible.
        .map(|(_, dict)| dict)
}

// brute force, too slow
//
// pub fn to_num(digits: &Vec<char>, dict: &HashMap<char, u8>) -> u64 {
//     digits
//         .into_iter()
//         .rev()
//         .filter_map(|n| dict.get(n))
//         .enumerate()
//         .fold(0, |acc, (radix, digit)| {
//             *digit as u64 * 10_u64.pow(radix as u32) + acc
//         })
// }

// fn is_leading_zero(ns: &[Vec<char>], dict: &HashMap<char, u8>) -> bool {
//     ns.iter()
//         .filter_map(|xs| xs.first())
//         .filter_map(|x| dict.get(x))
//         .any(|x| *x == 0_u8)
// }

// pub fn solve(input: &str) -> Option<HashMap<char, u8>> {
//     let ns = unsafe_parse(input);
//     let right = ns.last()?;
//     let left = &ns[..(ns.len() - 1)];

//     let letters: HashSet<char> = input.chars().filter(|x| x.is_alphabetic()).collect();

//     (0..10)
//         .permutations(letters.len())
//         .map(|xs| letters.iter().copied().zip(xs.into_iter()).collect())
//         .find(|dict| {
//             let left_num: u64 = left.iter().map(|chars| to_num(chars, dict)).sum();
//             let right_num = to_num(right, dict);

//             left_num == right_num && !is_leading_zero(ns.as_slice(), dict)
//         })
// }
