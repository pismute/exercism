pub fn abbreviate(phrase: &str) -> String {
    phrase
        .split_inclusive(":")
        .take(1)
        .flat_map(|x| x.split_whitespace())
        .flat_map(|x| x.split("-"))
        .map(|x| {
            if x.chars().all(|x| x.is_uppercase()) && x.len() > 1 {
                &x[..1]
            } else {
                x
            }
        })
        .flat_map(|x| {
            x.chars()
                .skip_while(|y| !y.is_alphabetic())
                .take(1)
                .flat_map(|y| y.to_uppercase())
                .chain(
                    x.chars()
                        .skip_while(|y| !y.is_alphabetic())
                        .skip(1)
                        .filter(|y| y.is_uppercase()),
                )
        })
        .collect::<String>()
}
