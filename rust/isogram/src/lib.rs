use std::collections::HashSet;

pub fn check(candidate: &str) -> bool {
    candidate
        .to_lowercase()
        .chars()
        .filter(|x| x.is_alphabetic())
        .try_fold(
            HashSet::new(),
            |mut acc: HashSet<char>, x| {
                if acc.insert(x) {
                    Some(acc)
                } else {
                    None
                }
            },
        )
        .is_some()
}
