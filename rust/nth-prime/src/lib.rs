fn is_prime(n: u32) -> bool {
    let to = (n as f64).sqrt() as u32 + 1;

    !(2..to).any(|x| n % x == 0)
}

pub fn nth(n: u32) -> Option<u32> {
    if n < 1 {
        None
    } else {
        (2..).filter(|x| is_prime(*x)).nth((n-1) as usize)
    }
}
