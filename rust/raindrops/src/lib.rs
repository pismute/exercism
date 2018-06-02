#![feature(slice_patterns)]

// immutable ?
static FACTORS: [(usize,&str); 3] = [(3, "Pling"), (5, "Plang"), (7, "Plong")];

pub fn raindrops(n: usize) -> String {
    // array ?
    let xs: Vec<&str> = FACTORS.iter()
        .flat_map(|x| if (n % x.0) == 0 {vec!(x.1)} else {vec!()})
        .collect();

    //if xs.is_empty() {n.to_string()} else {xs.concat()}
    match xs.as_slice() {
        [] => n.to_string(),
        ys => ys.concat()
    }
}
