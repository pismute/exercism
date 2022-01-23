pub fn sum_of_multiples(limit: u32, factors: &[u32]) -> u32 {
    let fs: Vec<&u32> = factors.iter().filter(|x| **x != 0).collect();

    (0..limit).filter(|x| fs.iter().any(|y| x % *y == 0)).sum()
}
