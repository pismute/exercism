pub fn factors(n: u64) -> Vec<u64> {
    std::iter::successors(Some((false, 2_u64, n)), |(_, div, num)| {
        if *num < 2 {
            None
        } else if num % div == 0 {
            Some((true, *div, num / div))
        } else {
            Some((false, div + 1, *num))
        }
    })
    .filter(|(is_yield, _, _)| *is_yield)
    .map(|(_, factor, _)| factor)
    .collect()
}
