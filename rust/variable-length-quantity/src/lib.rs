#![feature(let_chains)]
#![feature(int_roundings)]

#[derive(Debug, PartialEq)]
pub enum Error {
    IncompleteNumber,
    Overflow,
}

const MSB: u8 = 0x80;

/// Convert a list of numbers to a stream of bytes encoded with variable length encoding.
fn value_to_bytes(value: u32) -> impl Iterator<Item = u8> {
    (0_u8..5)
        .rev()
        .map(move |i| {
            let pos = i * 7;
            let byte = (value >> pos) as u8;

            if i == 0 {
                byte & !MSB
            } else {
                byte | MSB
            }
        })
        .skip_while(|x| *x == MSB)
}

pub fn to_bytes(values: &[u32]) -> Vec<u8> {
    values
        .into_iter()
        .flat_map(|x| value_to_bytes(*x))
        .collect()
}

/// Given a stream of bytes, extract all numbers which are encoded in there.
pub fn from_bytes(bytes: &[u8]) -> Result<Vec<u32>, Error> {
    bytes
        .iter()
        .try_fold((true, 0, vec![]), |(_, num, mut acc), x| {
            if num >> 25 == 0 {
                if *x & MSB == MSB {
                    let x_no_msb = *x & !MSB;

                    let new_num = (num << 7) | x_no_msb as u32;

                    Ok((false, new_num, acc))
                } else {
                    let x_msb = *x & !MSB;

                    let new_num = (num << 7) | x_msb as u32;

                    acc.push(new_num);

                    Ok((true, 0, acc))
                }
            } else {
                Err(Error::Overflow)
            }
        })
        .and_then(|(is_complete, _, acc)| {
            if is_complete {
                Ok(acc)
            } else {
                Err(Error::IncompleteNumber)
            }
        })
}
