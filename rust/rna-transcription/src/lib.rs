use lazy_static::lazy_static;
use std::collections::{HashMap, HashSet};

lazy_static! {
    static ref TRANS: HashMap<char, char> = vec![('G', 'C'), ('C', 'G'), ('T', 'A'), ('A', 'U')]
        .into_iter()
        .collect();
    static ref DNA: HashSet<char> = TRANS.keys().cloned().collect();
    static ref RNA: HashSet<char> = TRANS.values().cloned().collect();
}

#[derive(Debug, PartialEq)]
pub struct Dna(String);

#[derive(Debug, PartialEq)]
pub struct Rna(String);

impl Dna {
    pub fn new(dna: &str) -> Result<Dna, usize> {
        dna.chars()
            .position(|x| !DNA.contains(&x))
            .map(|i| Err(i))
            .unwrap_or_else(|| Ok(Dna(dna.to_string())))
    }

    pub fn into_rna(self) -> Rna {
        Rna(self
            .0
            .chars()
            .flat_map(|x| TRANS.get(&x).into_iter())
            .collect())
    }
}

impl Rna {
    pub fn new(rna: &str) -> Result<Rna, usize> {
        rna.chars()
            .position(|x| !RNA.contains(&x))
            .map(|i| Err(i))
            .unwrap_or_else(|| Ok(Rna(rna.to_string())))
    }
}
