#![feature(if_let_guard)]

use std::collections::HashMap;

pub type Value = i32;
pub type Result = std::result::Result<(), Error>;

type Stack = Vec<Value>;
type Words = HashMap<String, Vec<Term>>;
type TermResult = std::result::Result<(), (Term, Error)>;

#[derive(Clone, Debug)]
enum Term {
    Var(Box<Word>),
    Val(Value),
    Op(String),
    InvalidWord,
}

#[derive(Clone, Debug)]
struct Word {
    name: String,
    terms: Vec<Term>,
}

mod parser {
    use super::{Term, Word};
    use nom::{
        bytes::complete::*, character::complete::*, multi::*, sequence::*, AsChar, IResult, Parser,
    };

    fn value(i: &str) -> IResult<&str, Term> {
        i32.map(Term::Val).parse(i)
    }

    fn op_name(i: &str) -> IResult<&str, &str> {
        take_while1(|x: char| x.is_alpha() || x == '+' || x == '*' || x == '/' || x == '-').parse(i)
    }

    fn op(i: &str) -> IResult<&str, Term> {
        tag("+")
            .or(tag("-"))
            .or(tag("*"))
            .or(tag("/"))
            .or(op_name)
            .map(|x: &str| Term::Op(x.to_lowercase()))
            .parse(i)
    }

    fn word(i: &str) -> IResult<&str, Term> {
        let open = pair(tag(":"), space1);
        let name_terms = separated_pair(op_name, space1, separated_list1(space1, value.or(op)));
        let close = pair(space1, tag(";"));

        delimited(open, name_terms, close)
            .map(|(name, terms)| {
                let word = Word {
                    name: name.to_lowercase(),
                    terms,
                };

                Term::Var(Box::new(word))
            })
            .parse(i)
    }

    fn undefined(i: &str) -> IResult<&str, Term> {
        Ok((i, Term::InvalidWord))
    }

    pub(super) fn term(i: &str) -> IResult<&str, Term> {
        value.or(op).or(word).or(undefined).parse(i)
    }

    // complete combinators are used, because streaming combinators return 'Incomplete(Size(1))'.
    // streaming combinators need a complete maker need in input.
    // a input "1 + 2 + 3" would return 'Incomplete(Size(1)) in streaming combinators.
    // a input "1 + 2 + 3;" can be complete with 'many_until' combinator in steaming combinators.
    pub(super) fn terms(i: &str) -> IResult<&str, Vec<Term>> {
        separated_list0(space1, term).parse(i)
    }

    // note: 'iterator' combinator.
    // It is a great combinator to parse lazily, but it is not enough.
    //
    // 1. this combinator is applicable only input has repetition of one pattern.
    // ex) '1_2_3_', iterator(input, pair(number, char('_'))), This is repetition of 'number_'.
    //
    // 2. To build a complete lazy parser, it seems to need many iterators like 'separated_iter', 'iter_until'... and so on.
}

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
    pub fn new() -> Self {
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

    fn eval_term(words: &mut Words, stack: &mut Stack, term: Term) -> TermResult {
        fn with_term(t: Term) -> impl FnOnce(Error) -> (Term, Error) {
            |err| (t, err)
        }

        match term {
            Term::Var(word) => {
                let mut new_stack: Stack = vec![];
                let mut terms_iter = word.terms.into_iter();
                match Self::eval_iter(words, &mut new_stack, &mut terms_iter) {
                    Ok(_) => {
                        // fully evaluated
                        let terms: Vec<Term> = new_stack.into_iter().map(Term::Val).collect();
                        words.insert(word.name, terms);
                        Ok(())
                    }
                    Err((last, Error::StackUnderflow)) => {
                        // partially evaluated
                        let terms: Vec<Term> = new_stack
                            .into_iter()
                            .map(Term::Val)
                            .chain(std::iter::once(last))
                            .chain(terms_iter)
                            .collect();

                        words.insert(word.name, terms);
                        Ok(())
                    }
                    Err(x) => Err(x),
                }
            }
            Term::Val(x) => Ok(stack.push(x)),
            Term::Op(ref op) =>  match op.as_str() {
                _ if let Some(ys) = words.get(op) => {
                    let mut iter = ys.clone().into_iter();
                    Self::eval_iter(words, stack, &mut iter)
                },
                "/" => Self::binary(stack, |x, y, stack| {
                    if y != 0 {
                        Ok(stack.push(x / y))
                    } else {
                        Err(Error::DivisionByZero)
                    }
                }).map_err(with_term(term.clone())),
                "+" => Self::binary(stack, |x, y, stack| Ok(stack.push(x + y))).map_err(with_term(term)),
                "*" => Self::binary(stack, |x, y, stack| Ok(stack.push(x * y))).map_err(with_term(term)),
                "-" => Self::binary(stack, |x, y, stack| Ok(stack.push(x - y))).map_err(with_term(term)),
                "dup" => Self::unary(stack, |x, stack| Ok(stack.extend_from_slice(&[x, x]))).map_err(with_term(term)),
                "drop" => Self::unary(stack, |_, _| Ok(())).map_err(with_term(term)),
                "swap" => Self::binary(stack, |x, y, stack| Ok(stack.extend_from_slice(&[y, x]))).map_err(with_term(term)),
                "over" => Self::binary(stack, |x, y, stack| Ok(stack.extend_from_slice(&[x, y, x]))).map_err(with_term(term)),
                _ => Err((term, Error::UnknownWord)),
            },
            Term::InvalidWord => Err((term, Error::InvalidWord)),
        }
    }

    // don't consume iterator if it failed to eagerly evaluate word.
    fn eval_iter(
        words: &mut Words,
        stack: &mut Stack,
        terms: &mut impl Iterator<Item = Term>,
    ) -> TermResult {
        terms.try_for_each(|x| Self::eval_term(words, stack, x))
    }

    pub fn eval(&mut self, input: &str) -> Result {
        let (_, terms) = parser::terms(input).unwrap();

        let iter = terms.into_iter();

        Self::eval_iter(&mut self.words, &mut self.stack, &mut iter.into_iter()).map_err(|(_, x)| x)
    }
}
