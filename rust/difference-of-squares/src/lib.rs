pub fn square_of_sum(n: u32) -> u32 {
    (1..=n).sum::<u32>().pow(2)
}

pub fn sum_of_squares(n: u32) -> u32 {
    (1..=n).map(|x| x * x).sum()
}

pub fn difference(n: u32) -> u32 {
    let (sum, sq) = (1..=n).fold((0, 0), |(sum, sq), x| (sum + x, sq + x * x));

    sum * sum - sq
}
