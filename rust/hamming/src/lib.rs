use std::iter::repeat;

/// Return the Hamming distance between the strings,
/// or None if the lengths are mismatched.
pub fn hamming_distance(s1: &str, s2: &str) -> Option<usize> {
    // To zip all, a longest zip
    fn repeat_option_iter<'a>(s: &'a str) -> impl Iterator<Item = Option<char>> + 'a {
        s.chars().map(|x| Some(x)).chain(repeat(None))
    }

    repeat_option_iter(s1)
        .zip(repeat_option_iter(s2))
        .take_while(|(x, y)| !(*x == None && *y == None))
        .fold(Some(0), |acc, (x, y)| {
            x.zip(y)
                .zip(acc)
                .map(|((xx, yy), count)| count + (xx != yy) as usize)
        })
}
