/// Check a Luhn checksum.
pub fn is_valid(code: &str) -> bool {
    code.chars()
        .try_rfold((0, 0), |(sum, index), x| {
            x.to_digit(10)
                .map(|y| if index % 2 == 1 { y * 2 } else { y })
                .map(|y| if y > 9 { y - 9 } else { y })
                .map(|y| (sum + y, index + 1))
                .or_else(|| {
                    if x.is_whitespace() {
                        Some((sum, index))
                    } else {
                        None
                    }
                })
        })
        .map_or(false, |(sum, index)| sum % 10 == 0 && index > 1)
}
