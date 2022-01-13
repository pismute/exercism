const CARS_PER_HOUR: f64 = 221.0;

fn success_rate(speed: u8) -> Option<f64> {
    match speed {
        0 => Some(0.0),
        1..=4 => Some(1.0),
        5..=8 => Some(0.9),
        9..=10 => Some(0.77),
        _ => None,
    }
}

pub fn production_rate_per_hour(speed: u8) -> f64 {
    let maybe_rate = success_rate(speed).map(|x| speed as f64 * CARS_PER_HOUR * x);

    match maybe_rate {
        Some(rate) => rate,
        _ => panic!("Unexpected speed: {}", speed),
    }
}

pub fn working_items_per_minute(speed: u8) -> u32 {
    production_rate_per_hour(speed) as u32 / 60
}
