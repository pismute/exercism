static FACTORS: [(usize, &str); 3] = [(3, "Pling"), (5, "Plang"), (7, "Plong")];

pub fn raindrops(n: usize) -> String {
    let xs: Vec<&str> = FACTORS
        .iter()
        .filter(|x| (n % x.0) == 0)
        .map(|x| x.1)
        .collect();

    match xs.as_slice() {
        [] => n.to_string(),
        ys => ys.concat(),
    }
}
