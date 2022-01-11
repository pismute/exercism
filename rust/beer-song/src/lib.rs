fn bottles_of_beer(x: i32) -> String {
    match x {
        0 => format!("no more bottles of beer"),
        1 => format!("1 bottle of beer"),
        y => format!("{} bottles of beer", y),
    }
}

fn capitalize(xs: &str) -> String {
    if xs.is_empty() {
        String::new()
    } else {
        xs.chars()
            .enumerate()
            .filter_map(|(i, x)| {
                if i == 0 {
                    x.to_uppercase().nth(0).or(Some(x))
                } else {
                    Some(x)
                }
            })
            .collect()
    }
}

fn first(n: i32) -> String {
    let x = bottles_of_beer(n);
    format!("{} on the wall, {}.", capitalize(&x), x)
}

fn second(n: i32) -> String {
    let x = bottles_of_beer(n);
    match n {
        99 => format!("Go to the store and buy some more, {} on the wall.", x),
        0 => format!("Take it down and pass it around, {} on the wall.", x),
        _ => format!("Take one down and pass it around, {} on the wall.", x),
    }
}

pub fn verse(n: i32) -> String {
    match n {
        0 => {
            format!("{}\n{}\n", first(0), second(99))
        }
        x => {
            format!("{}\n{}\n", first(x), second(x - 1))
        }
    }
}

pub fn sing(start: i32, end: i32) -> String {
    (end..=start)
        .rev()
        .map(verse)
        .collect::<Vec<String>>()
        .join("\n")
}
