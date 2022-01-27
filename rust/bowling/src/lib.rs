#[derive(Debug, PartialEq)]
pub enum Error {
    NotEnoughPinsLeft,
    GameComplete,
}

pub struct BowlingGame {
    rolls: Vec<Vec<u16>>,
}

impl BowlingGame {
    const STRIKE: u16 = 10;
    const MAX_FRAME: usize = 10;

    pub fn new() -> Self {
        BowlingGame { rolls: vec![] }
    }

    pub fn roll(&mut self, pins: u16) -> Result<(), Error> {
        let frame = self.rolls.len();

        if pins > 10 {
            Err(Error::NotEnoughPinsLeft)
        } else {
            match self.rolls.as_mut_slice() {
                [] => {
                    self.rolls.push(vec![pins]);
                    Ok(())
                }
                [.., last] if frame == Self::MAX_FRAME => match last.as_slice() {
                    [] => panic!("impossible"),
                    [Self::STRIKE, Self::STRIKE] => {
                        last.push(pins);
                        Ok(())
                    }
                    [Self::STRIKE, second] if second + pins > Self::STRIKE => {
                        Err(Error::NotEnoughPinsLeft)
                    }
                    [first, second] if first + second < Self::STRIKE => Err(Error::GameComplete),
                    [_, _, _] => Err(Error::GameComplete),
                    _ => {
                        last.push(pins);
                        Ok(())
                    }
                },
                [.., last] => match last.as_slice() {
                    [] => panic!("impossible"),
                    [_, _, ..] => {
                        self.rolls.push(vec![pins]);
                        Ok(())
                    }
                    [Self::STRIKE, ..] => {
                        self.rolls.push(vec![pins]);
                        Ok(())
                    }
                    [first, ..] if first + pins > Self::STRIKE => Err(Error::NotEnoughPinsLeft),
                    [_, ..] => {
                        last.push(pins);
                        Ok(())
                    }
                },
            }
        }
    }

    pub fn score(&self) -> Option<u16> {
        println!("{:?}", self.rolls);

        if self.rolls.is_empty()
            || self // is complete?
                .rolls
                .get(Self::MAX_FRAME - 1)
                .filter(|xs| match xs.as_slice() {
                    [_, _, _] => true,
                    [first, second, ..] if first + second < Self::STRIKE => true,
                    _ => false,
                })
                .is_none()
        {
            None
        } else {
            Some(
                (0..(self.rolls.len()))
                    .map(|i| match self.rolls[i].as_slice() {
                        [Self::STRIKE, ..] => {
                            let next_two: u16 = self.rolls[i..]
                                .iter()
                                .flat_map(|xs| xs.iter())
                                .skip(1)
                                .take(2)
                                .sum();

                            println!("!{:?} {}", self.rolls[i], next_two);

                            Self::STRIKE + next_two
                        }
                        [first, second, ..] => {
                            let nr_of_throws = if first + second == Self::STRIKE { 3 } else { 2 };

                            self.rolls[i..]
                                .iter()
                                .flat_map(|xs| xs.iter())
                                .take(nr_of_throws)
                                .sum()
                        }
                        _ => panic!("impossible"),
                    })
                    .sum(),
            )
        }
    }
}
