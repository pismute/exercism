#![feature(if_let_guard)]

use std::collections::HashMap;

pub type Value = i32;
pub type Result = std::result::Result<(), Error>;

type Stack = Vec<Value>;
type Words = HashMap<String, Vec<String>>;

pub struct Forth {
    words: Words,
    stack: Stack,
}

#[derive(Debug, PartialEq)]
pub enum Error {
    DivisionByZero,
    StackUnderflow,
    UnknownWord,
    InvalidWord,
}

impl Forth {
    pub fn new() -> Forth {
        Forth {
            words: HashMap::new(),
            stack: vec![],
        }
    }

    pub fn stack(&self) -> &[Value] {
        &self.stack
    }

    fn binary<F: FnOnce(Value, Value, &mut Stack) -> Result>(stack: &mut Stack, f: F) -> Result {
        if stack.len() < 2 {
            // to keep values in stack on error;
            Err(Error::StackUnderflow)
        } else {
            stack
                .pop()
                .zip(stack.pop())
                .ok_or_else(|| Error::StackUnderflow) // won't be error
                .and_then(|(x, y)| f(y, x, stack))
        }
    }

    fn unary<F: FnOnce(Value, &mut Stack) -> Result>(stack: &mut Stack, f: F) -> Result {
        if stack.len() < 1 {
            // to keep values in stack on error;
            Err(Error::StackUnderflow)
        } else {
            stack
                .pop()
                .ok_or_else(|| Error::StackUnderflow)
                .and_then(|x| f(x, stack))
        }
    }

    fn parse_word(
        words: &mut Words,
        token_stack: &mut Vec<String>,
    ) -> std::result::Result<(String, Vec<String>), Error> {
        let word = token_stack
            .pop()
            .filter(|x| x.parse::<Value>().is_err()) // number is not allowed
            .ok_or_else(|| Error::InvalidWord)?;
        let mut defs: Vec<String> = vec![];
        let mut done = false;
        while let Some(word_token) = token_stack.pop() {
            match word_token.as_str() {
                ";" => {
                    done = true;
                    break;
                }
                _ => match words.get(&word_token) {
                    Some(xs) => defs.extend_from_slice(xs.as_slice()),
                    None => defs.push(word_token),
                },
            }
        }

        if done {
            Ok((word.to_string(), defs))
        } else {
            Err(Error::InvalidWord)
        }
    }

    fn eval_tokens(words: &mut Words, stack: &mut Stack, token_stack: &mut Vec<String>) -> Result {
        while let Some(token) = token_stack.pop() {
            let result = match token.parse::<Value>() {
                Ok(v) => Ok(stack.push(v)),
                Err(_) => match token.as_str() {
                    ":" => {
                        let (word, defs) = Self::parse_word(words, token_stack)?;

                        // eagerly evaluate word to pass alloc-attack
                        let mut new_stack: Stack = vec![];
                        let mut new_token_stack: Vec<String> = defs.into_iter().rev().collect();

                        // this recursion is unsafe when expression has very deep words.
                        match Self::eval_tokens(words, new_stack.as_mut(), &mut new_token_stack) {
                            Ok(_) => { // fully evaluated
                                let new_defs: Vec<String> = new_stack
                                    .iter()
                                    .map(|x| x.to_string())
                                    .collect();
                                words.insert(word, new_defs);
                                Ok(())
                            },
                            Err(Error::StackUnderflow) => { // partially evaluated
                                let new_defs: Vec<String> = new_stack
                                    .iter()
                                    .map(|x| x.to_string())
                                    .chain(new_token_stack.into_iter().rev())
                                    .collect();

                                words.insert(word, new_defs);
                                Ok(())
                            },
                            Err(x) => Err(x),
                        }
                    },
                    _ if let Some(xs) = words.get(&token) => {
                        Ok(token_stack.extend(xs.iter().rev().cloned()))
                    },
                    "/" => Self::binary(stack, |x, y, stack| {
                        if y != 0 {
                            Ok(stack.push(x / y))
                        } else {
                            Err(Error::DivisionByZero)
                        }
                    }),
                    "+" => Self::binary(stack, |x, y, stack| Ok(stack.push(x + y))),
                    "*" => Self::binary(stack, |x, y, stack| Ok(stack.push(x * y))),
                    "-" => Self::binary(stack, |x, y, stack| Ok(stack.push(x - y))),
                    "dup" => Self::unary(stack, |x, stack| {
                        Ok(stack.extend_from_slice(&[x, x]))
                    }),
                    "drop" => Self::unary(stack, |_, _| Ok(())),
                    "swap" => Self::binary(stack, |x, y, stack| {
                        Ok(stack.extend_from_slice(&[y, x]))
                    }),
                    "over" => Self::binary(stack, |x, y, stack| {
                        Ok(stack.extend_from_slice(&[x, y, x]))
                    }),
                    _ => Err(Error::UnknownWord),
                },
            };

            // On error, push the last token back to the stack to build word.
            if let Err(_) = result {
                token_stack.push(token);
            }

            result?;
        }

        Ok(())
    }

    pub fn eval(&mut self, input: &str) -> Result {
        let mut token_stack: Vec<String> = input
            .split_whitespace()
            .rev()
            .map(|x| x.to_lowercase())
            .collect();

        Self::eval_tokens(&mut self.words, &mut self.stack, &mut token_stack)
    }
}
