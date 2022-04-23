use std::collections::BTreeMap;

pub fn transform(h: &BTreeMap<i32, Vec<char>>) -> BTreeMap<char, i32> {
    h.into_iter()
        .flat_map(|(score, xs)| {
            xs.into_iter()
                .flat_map(|x| x.to_lowercase())
                .map(|x| (x, *score))
        })
        .collect()
}
