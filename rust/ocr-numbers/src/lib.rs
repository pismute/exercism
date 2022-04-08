#![feature(iter_intersperse)]

// The code below is a stub. Just enough to satisfy the compiler.
// In order to pass the tests you can add-to or change any of this code.

use std::str::FromStr;

#[derive(Debug, PartialEq)]
pub enum Error {
    InvalidRowCount(usize),
    InvalidColumnCount(usize),
}

struct Ocr(char);

impl FromStr for Ocr {
    type Err = String;

    fn from_str(s: &str) -> Result<Self, Self::Err> {
        match s {
            "     |  |   " => Ok(Ocr('1')),
            " _  _||_    " => Ok(Ocr('2')),
            " _  _| _|   " => Ok(Ocr('3')),
            "   |_|  |   " => Ok(Ocr('4')),
            " _ |_  _|   " => Ok(Ocr('5')),
            " _ |_ |_|   " => Ok(Ocr('6')),
            " _   |  |   " => Ok(Ocr('7')),
            " _ |_||_|   " => Ok(Ocr('8')),
            " _ |_| _|   " => Ok(Ocr('9')),
            " _ | ||_|   " => Ok(Ocr('0')),
            x if x.len() == 12 => Ok(Ocr('?')),
            x => Err(format!("incorrect size: {}", x)),
        }
    }
}

const SIZE_OF_OCR_ROW: usize = 4;
const SIZE_OF_OCR_COLUMN: usize = 3;
const SIZE_OR_FLATTEN_OCR: usize = 12;

pub fn convert(input: &str) -> Result<String, Error> {
    let lines: Vec<&str> = input.split_terminator('\n').collect();

    if lines.len() % SIZE_OF_OCR_ROW != 0 {
        Err(Error::InvalidRowCount(lines.len()))
    } else {
        let mut result: Vec<String> = vec![];
        for xs in lines.chunks(SIZE_OF_OCR_ROW) {
            let column_length = xs[0].len();
            if column_length % SIZE_OF_OCR_COLUMN != 0 {
                return Err(Error::InvalidColumnCount(column_length));
            } else {
                let nr_of_ocr = column_length / SIZE_OF_OCR_COLUMN;
                let mut acc = vec![Vec::with_capacity(SIZE_OR_FLATTEN_OCR); nr_of_ocr];
                for x in xs.into_iter() {
                    for (i, ys) in x.as_bytes().chunks(SIZE_OF_OCR_COLUMN).enumerate() {
                        acc[i].extend(ys);
                    }
                }

                let numbers: Vec<char> = acc
                    .into_iter()
                    .map(|xs| String::from_utf8(xs))
                    .map(|x| x.unwrap().parse().unwrap()) // unsafe --;
                    .map(|x: Ocr| x.0)
                    .collect();

                result.push(numbers.into_iter().collect());
            }
        }

        Ok(result.join(","))
    }
}
