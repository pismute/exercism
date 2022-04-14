pub fn find_saddle_points(input: &[Vec<u64>]) -> Vec<(usize, usize)> {
    let y_size = input.len();
    let x_size = input[0].len();

    let max: Vec<u64> = input
        .iter()
        .map(|xs| xs.iter().fold(u64::MIN, |acc, x| acc.max(*x)))
        .collect();

    let min: Vec<u64> = (0..x_size)
        .map(|j| {
            (0..y_size).fold(u64::MAX, |acc, i| {
                let value = input[i][j];
                acc.min(value)
            })
        })
        .collect();

    (0..y_size)
        .flat_map(|i| (0..x_size).map(move |j| (i, j)))
        .filter(|(i, j)| {
            let value = input[*i][*j];
            value == max[*i] && value == min[*j]
        })
        .collect()
}
