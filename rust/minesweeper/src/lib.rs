const MINE: u8 = b'*';
const SPACE: u8 = b' ';
const ONE: u8 = b'1';

pub fn annotate(minefield: &[&str]) -> Vec<String> {
    let y_size = minefield.len();
    let x_size = if y_size == 0 { 0 } else { minefield[0].len() };

    let mut mf: Vec<Vec<u8>> = minefield.iter().map(|x| x.as_bytes().into()).collect();

    fn sweep(x: u8) -> u8 {
        match x {
            MINE => MINE,
            SPACE => ONE,
            n => n + 1,
        }
    }

    for i in 0..y_size {
        let y_min: usize = if i == 0 { 0 } else { i - 1 };
        let y_max: usize = (i + 2).min(y_size);

        for j in 0..x_size {
            match mf[i][j] {
                MINE => {
                    let x_min: usize = if j == 0 { 0 } else { j - 1 };
                    let x_max: usize = (j + 2).min(x_size);

                    for ii in y_min..y_max {
                        for jj in x_min..x_max {
                            println!("{}, {}, {}, {}", i, j, ii, jj);
                            mf[ii][jj] = sweep(mf[ii][jj]);
                        }
                    }
                }
                _ => (),
            }
        }
    }

    mf.iter()
        .map(|x| String::from_utf8_lossy(x).to_string())
        .collect()
}
