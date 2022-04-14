use lazy_static::lazy_static;
use std::collections::HashMap;
use std::iter::once;

lazy_static! {
    static ref SCORES: HashMap<char, u64> = vec![
        ("AEIOULNRST", 1),
        ("DG", 2),
        ("BCMP", 3),
        ("FHVWY", 4),
        ("K", 5),
        ("JX", 8),
        ("QZ", 10),
    ]
    .into_iter()
    .flat_map(|(xs, score)| xs.chars().into_iter().map(move |x| (x, score)))
    .flat_map(|(x, score)| once((x, score)).chain(x.to_lowercase().map(move |y| (y, score))))
    .collect();
}

/// Compute the Scrabble score for a word.
pub fn score(word: &str) -> u64 {
    word.chars().filter_map(|x| SCORES.get(&x)).sum()
}
