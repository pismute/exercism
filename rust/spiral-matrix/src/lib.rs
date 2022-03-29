pub fn spiral_matrix(size: usize) -> Vec<Vec<u32>> {
    let mut matrix = vec![vec![0; size]; size];
    let mut num: u32 = 1;

    for i in (1..=size).rev() {
        let nth = size - i;
        let d: usize = nth / 2;
        if nth % 2 == 0 {
            // for 'ㄱ' shape
            for x in 0..i {
                matrix[d][x + d] = num;
                num += 1;
            }
            for x in 1..i {
                matrix[x + d][i - 1 + d] = num;
                num += 1;
            }
        } else {
            // for 'ㄴ' shape
            for x in (0..i).rev() {
                matrix[i + d][x + d] = num;
                num += 1;
            }
            for x in (1..i).rev() {
                matrix[x + d][d] = num;
                num += 1;
            }
        }
    }

    matrix
}
