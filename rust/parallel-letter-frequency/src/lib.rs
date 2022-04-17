use rayon::prelude::*;
use std::collections::HashMap;

pub fn frequency(input: &[&str], worker_count: usize) -> HashMap<char, usize> {
    (0..worker_count)
        .into_par_iter()
        .map(|i| {
            let mut counts: HashMap<char, usize> = HashMap::new();
            for xs in input.into_iter().skip(i).step_by(worker_count) {
                for x in xs
                    .chars()
                    .filter(|x| x.is_alphabetic())
                    .flat_map(|x| x.to_lowercase())
                {
                    counts.entry(x).and_modify(|n| *n += 1).or_insert_with(|| 1);
                }
            }
            counts
        })
        .reduce(HashMap::new, |mut acc, xs| {
            for (x, n) in xs {
                acc.entry(x)
                    .and_modify(|acc_n| *acc_n += n)
                    .or_insert_with(|| n);
            }
            acc
        })
}
