pub fn build_proverb(list: &[&str]) -> String {
    list.iter()
        .zip(list.iter().skip(1))
        .map(|(x, y)| format!("For want of a {} the {} was lost.\n", x, y))
        .chain(
            list.iter()
                .take(1)
                .map(|x| format!("And all for the want of a {}.", x)),
        )
        .collect()
}
