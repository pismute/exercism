pub fn encode(source: &str) -> String {
    source
        .chars()
        .fold(Vec::<(u32, char)>::new(), |mut acc, x| {
            let len = acc.len();
            match acc.get_mut(len.max(1) - 1) {
                Some((nr, y)) if *y == x => *nr += 1,
                _ => acc.push((1, x)),
            }
            acc
        })
        .into_iter()
        .map(|(nr, x)| {
            if nr == 1 {
                x.to_string()
            } else {
                let mut ret = nr.to_string();
                ret.push(x);
                ret
            }
        })
        .collect()
}

pub fn decode(source: &str) -> String {
    source
        .chars()
        .fold((0, vec![]), |(nr, mut acc), x| match (nr, x.to_digit(10)) {
            (0, None) => {
                acc.push(x);
                (0, acc)
            }
            (0, Some(d)) => (d, acc),
            (n, None) => {
                acc.append(vec![x; n as usize].as_mut());
                (0, acc)
            }
            (n, Some(d)) => (n * 10 + d, acc),
        })
        .1
        .iter()
        .collect()
}
