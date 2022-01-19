use std::collections::HashSet;

fn sort(x: &str) -> String {
    let mut chars: Vec<char> = x.chars().collect();
    chars.sort_unstable();
    String::from_iter(chars.iter())
}

pub fn anagrams_for<'a>(word: &str, possible_anagrams: &[&'a str]) -> HashSet<&'a str> {
    let lower: String = word.to_lowercase();
    let w = sort(lower.as_str());

    possible_anagrams
        .iter()
        .filter(|x| {
            let ll = x.to_lowercase();

            sort(ll.as_str()) == w && ll != lower
        })
        .map(|x| *x)
        .collect()
}
