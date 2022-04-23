#![feature(iterator_try_collect)]

use std::{cmp::Ordering, str::FromStr};

type Error = String;

#[derive(PartialEq, PartialOrd, Eq, Ord, Clone, Copy, Debug)]
enum Color {
    C,
    D,
    H,
    S,
}

#[derive(PartialEq, Eq, PartialOrd, Ord, Clone, Copy, Debug)]
struct Card {
    color: Color,
    num: u8,
}

impl Card {
    fn to_num(x: &str) -> Result<u8, Error> {
        x.parse::<u8>().or_else(|_| match x.chars().next() {
            Some('J') => Ok(11),
            Some('Q') => Ok(12),
            Some('K') => Ok(13),
            Some('A') => Ok(14),
            _ => Err(format!("not able to parse: {}", x)),
        })
    }
}

impl FromStr for Card {
    type Err = Error;
    fn from_str(s: &str) -> Result<Self, Self::Err> {
        use Color::*;

        // for utf8-safety
        let len: usize = s.chars().count();
        let num_string: String = s.chars().take(len - 1).collect();

        let num = Self::to_num(&num_string)?;
        match s.chars().last() {
            Some('C') => Ok(Card { color: C, num }),
            Some('D') => Ok(Card { color: D, num }),
            Some('H') => Ok(Card { color: H, num }),
            Some('S') => Ok(Card { color: S, num }),
            _ => Err(format!("unable to parse to a card: {}", s)),
        }
    }
}

#[derive(PartialEq, Eq, PartialOrd, Ord, Debug)]
enum Rank {
    HighCard(Vec<u8>),
    OnePair(u8, Vec<u8>),
    TwoPair { big: u8, small: u8, rest: Vec<u8> },
    ThreeOfAKind(u8, Vec<u8>),
    Straight(Vec<u8>),
    Flush(Vec<u8>),
    FullHouse { three: u8, two: u8 },
    FourOfAKind(u8, Vec<u8>),
    StraightFlush(Vec<u8>),
    //    FiveOfAKind,
}

#[derive(Debug)]
struct Hand<'a> {
    orig: &'a str,
    rank: Rank,
}

impl<'a> Hand<'a> {
    fn new(orig: &'a str) -> Result<Hand, Error> {
        let rank = Self::rank(orig)?;

        Ok(Hand { orig, rank })
    }

    fn is_straight(cards_by_num: &[Card]) -> bool {
        cards_by_num
            .iter()
            .zip(cards_by_num.iter().skip(1))
            .find(|(x, y)| x.num - 1 != y.num)
            .is_none()
    }

    fn rank_straight_or_flush(
        cards_by_num: &Vec<Card>,
        cards_by_color: &Vec<Card>,
    ) -> Option<Rank> {
        let straight = Self::is_straight(cards_by_num.as_slice());

        let flush = cards_by_color
            .iter()
            .zip(cards_by_color.iter().skip(1))
            .find(|(x, y)| x.color != y.color)
            .is_none();

        match (straight, flush) {
            (true, true) => Some(Rank::StraightFlush(
                cards_by_num.iter().map(|x| x.num).collect(),
            )),
            (true, false) => Some(Rank::Straight(cards_by_num.iter().map(|x| x.num).collect())),
            (false, true) => Some(Rank::Flush(cards_by_num.iter().map(|x| x.num).collect())),
            _ => None,
        }
    }

    fn rank_rest(cards_by_num: &Vec<Card>) -> Rank {
        let pairs = cards_by_num
            .iter()
            .zip(cards_by_num.iter().skip(1))
            .filter(|(x, y)| x.num == y.num)
            .fold(vec![], |mut acc: Vec<Vec<Card>>, (x, y)| {
                match acc
                    .iter_mut()
                    .find(|zs| zs.first().iter().all(|z| z.num == x.num))
                {
                    None => acc.push(vec![*x, *y]),
                    Some(zs) => zs.push(*y),
                }

                acc
            });

        let pair_slices: Vec<&[Card]> = pairs.iter().map(|xs| xs.as_slice()).collect();

        let nums_by_num: Vec<u8> = cards_by_num.iter().map(|x| x.num).collect();

        match pair_slices.as_slice() {
            [[_, _, _, _]] => Rank::FourOfAKind(
                pairs[0][0].num,
                nums_by_num
                    .iter()
                    .filter(|x| **x != pairs[0][0].num)
                    .copied()
                    .collect(),
            ),
            [[_, _], [_, _, _]] => Rank::FullHouse {
                three: pairs[1][0].num,
                two: pairs[0][0].num,
            },
            [[_, _, _], [_, _]] => Rank::FullHouse {
                three: pairs[0][0].num,
                two: pairs[1][0].num,
            },
            [[_, _, _]] => Rank::ThreeOfAKind(
                pairs[0][0].num,
                nums_by_num
                    .iter()
                    .filter(|x| **x != pairs[0][0].num)
                    .copied()
                    .collect(),
            ),
            [[_, _], [_, _]] => Rank::TwoPair {
                big: pairs[0][0].num,
                small: pairs[1][0].num,
                rest: nums_by_num
                    .iter()
                    .filter(|x| **x != pairs[0][0].num && **x != pairs[1][0].num)
                    .copied()
                    .collect(),
            },
            [[_, _]] => Rank::OnePair(
                pairs[0][0].num,
                nums_by_num
                    .iter()
                    .filter(|x| **x != pairs[0][0].num)
                    .copied()
                    .collect(),
            ),
            _ => Rank::HighCard(cards_by_num.iter().map(|x| x.num).collect()),
        }
    }

    fn ace_can_end_a_strait_low(cards: &mut Vec<Card>) {
        // ace
        if cards[0].num == 14 && cards[4].num == 2 && Self::is_straight(&cards[1..]) {
            cards[0].num = 1;
        }

        // resort
        cards.sort_by(|x, y| y.num.cmp(&x.num));
    }

    fn rank(s: &'a str) -> Result<Rank, Error> {
        let mut cards_by_num: Vec<Card> = s.split_whitespace().map(|x| x.parse()).try_collect()?;
        cards_by_num.sort_by(|x, y| y.num.cmp(&x.num));

        Self::ace_can_end_a_strait_low(&mut cards_by_num);

        let mut cards_by_color: Vec<Card> = cards_by_num.clone();
        cards_by_color.sort_by(|x, y| x.color.cmp(&y.color));

        Ok(Self::rank_straight_or_flush(&cards_by_num, &cards_by_color)
            .unwrap_or_else(|| Self::rank_rest(&cards_by_num)))
    }
}

///
/// Note the type signature: this function should return _the same_ reference to
/// the winning hand(s) as were passed in, not reconstructed strings which happen to be equal.
pub fn winning_hands<'a>(hands: &[&'a str]) -> Vec<&'a str> {
    let mut hands: Vec<Hand> = hands.iter().map(|x| Hand::new(*x)).try_collect().unwrap();

    hands.sort_by(|x, y| y.rank.cmp(&x.rank));

    hands
        .iter()
        .skip(1)
        .fold(
            hands.first().into_iter().collect(),
            |mut acc: Vec<&Hand>, x| {
                if let Some(Ordering::Equal) = acc.get(0).map(|y| y.rank.cmp(&x.rank)) {
                    acc.push(x)
                }

                acc
            },
        )
        .iter()
        .map(|x| x.orig)
        .collect()
}
