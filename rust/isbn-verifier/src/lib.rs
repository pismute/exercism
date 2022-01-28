/// Determines whether the supplied string is a valid ISBN number
pub fn is_valid_isbn(isbn: &str) -> bool {
    isbn.chars()
        .try_fold((0, 10), |(acc, weight), x| {
            match x {
                _ if weight < 1 => None,    // too long input
                '-' => Some((acc, weight)), // skip
                'X' if weight == 1 => Some((acc + 10 * weight, weight - 1)), // 'X' can be at last only
                ('0'..='9') => x.to_digit(10).map(|y| (acc + y * weight, weight - 1)),
                _ => None, // invalid
            }
        })
        .filter(|x| x.1 == 0) // too short input
        .filter(|x| x.0 % 11 == 0) // validation
        .is_some()
}
