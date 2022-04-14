use std::collections::{HashMap, HashSet};

pub struct CodonsInfo<'a> {
    dict: HashMap<&'a str, &'a str>,
    stops: HashSet<&'a str>,
}

impl<'a> CodonsInfo<'a> {
    pub fn name_for(&self, codon: &str) -> Option<&'a str> {
        self.dict.get(codon).map(|x| *x)
    }

    pub fn of_rna(&self, rna: &str) -> Option<Vec<&'a str>> {
        rna.as_bytes()
            .chunks(3)
            .filter_map(|x| std::str::from_utf8(x).ok())
            .take_while(|y| !self.stops.contains(*y))
            .try_fold(vec![], |mut acc, x| match x.len() {
                3 => self.name_for(x).map(|y| {
                    acc.push(y);
                    acc
                }),
                _ => None,
            })
    }
}

pub fn parse<'a>(pairs: Vec<(&'a str, &'a str)>) -> CodonsInfo<'a> {
    CodonsInfo {
        dict: pairs.into_iter().collect(),
        stops: vec!["UAA", "UAG", "UGA"].into_iter().collect(),
    }
}
