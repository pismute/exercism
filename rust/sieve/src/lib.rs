pub fn primes_up_to(upper_bound: u64) -> Vec<u64> {
    (2..=upper_bound).fold(vec![], |mut acc, x| match acc.iter().any(|y| x % y == 0) {
        true => acc,
        false => {
            acc.push(x);
            acc
        }
    });
}
