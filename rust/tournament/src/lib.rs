use std::collections::HashMap;

#[derive(Debug)]
struct Tally<'a> {
    name: &'a str,
    mp: u8,
    w: u8,
    d: u8,
    l: u8,
}

impl<'a> Tally<'a> {
    pub fn new(name: &'a str) -> Tally {
        Tally {
            name,
            mp: 0,
            w: 0,
            d: 0,
            l: 0,
        }
    }

    pub fn win(&mut self) {
        self.mp += 1;
        self.w += 1;
    }

    pub fn loss(&mut self) {
        self.mp += 1;
        self.l += 1;
    }

    pub fn draw(&mut self) {
        self.mp += 1;
        self.d += 1;
    }

    pub fn points(&self) -> u16 {
        (self.w as u16) * 3 + (self.d as u16)
    }
}

#[derive(Debug)]
enum MatchResult<'a> {
    Win { l: &'a str, r: &'a str },
    Loss { l: &'a str, r: &'a str },
    Draw { l: &'a str, r: &'a str },
}

impl<'a> TryFrom<&[&'a str]> for MatchResult<'a> {
    type Error = String;

    fn try_from(value: &[&'a str]) -> Result<Self, Self::Error> {
        match *value {
            [l, r, "win"] => Ok(MatchResult::Win { l, r }),
            [l, r, "loss"] => Ok(MatchResult::Loss { l, r }),
            [l, r, "draw"] => Ok(MatchResult::Draw { l, r }),
            _ => Err(format!("undefined match result: {:?}", value)),
        }
    }
}

pub fn tally(match_results: &str) -> String {
    let scores = match_results
        .split('\n')
        .try_fold(HashMap::<&str, Tally>::new(), |mut acc, x| {
            x.split(';')
                .collect::<Vec<_>>()
                .as_slice()
                .try_into()
                .and_then(|y| match y {
                    MatchResult::Win { l, r } => {
                        acc.entry(l).or_insert_with(|| Tally::new(l)).win();
                        acc.entry(r).or_insert_with(|| Tally::new(r)).loss();
                        Ok(acc)
                    }
                    MatchResult::Loss { l, r } => {
                        acc.entry(l).or_insert_with(|| Tally::new(l)).loss();
                        acc.entry(r).or_insert_with(|| Tally::new(r)).win();
                        Ok(acc)
                    }
                    MatchResult::Draw { l, r } => {
                        acc.entry(l).or_insert_with(|| Tally::new(l)).draw();
                        acc.entry(r).or_insert_with(|| Tally::new(r)).draw();
                        Ok(acc)
                    }
                })
        });

    scores
        .map(|xs| {
            let mut tallies = xs.into_values().collect::<Vec<Tally>>();

            tallies.sort_by(|x, y| {
                x.points()
                    .cmp(&y.points())
                    .then_with(|| x.name.cmp(y.name).reverse())
            });

            tallies.iter().rfold(
                format!("{:30} | MP |  W |  D |  L |  P", "Team"),
                |acc, x| {
                    acc + format!(
                        "\n{:30} | {:>2} | {:>2} | {:>2} | {:>2} | {:>2}",
                        x.name,
                        x.mp,
                        x.w,
                        x.d,
                        x.l,
                        x.points()
                    )
                    .as_ref()
                },
            )
        })
        .unwrap_or(format!("{:30} | MP |  W |  D |  L |  P", "Team"))
}
