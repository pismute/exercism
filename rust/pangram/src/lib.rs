use std::collections::HashSet;

/// Determine whether a sentence is a pangram.
pub fn is_pangram(sentence: &str) -> bool {
    sentence
        .chars()
        .filter(|x| x.is_ascii_alphabetic())
        .flat_map(|x| x.to_lowercase())
        .collect::<HashSet<char>>()
        .len()
        == 26
}
