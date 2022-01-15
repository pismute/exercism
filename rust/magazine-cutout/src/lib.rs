use std::collections::HashMap;

fn count_words<'a>(magazine: &'a [&str]) -> HashMap<&'a str, usize> {
    magazine
        .iter()
        .fold(HashMap::<&str, usize>::new(), |mut acc, x| {
            let count = acc.entry(x).or_insert(0);
            *count += 1;
            acc
        })
}

pub fn can_construct_note(magazine: &[&str], note: &[&str]) -> bool {
    let magazine_counter: HashMap<&str, usize> = count_words(magazine);

    note.iter()
        .try_fold(magazine_counter, |mut acc, x| match acc.get_mut(x) {
            Some(count) if *count > 0 => {
                *count -= 1;
                Some(acc)
            }
            _ => None,
        })
        .is_some()
}
