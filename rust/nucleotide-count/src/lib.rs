use std::collections::HashMap;

const NUCLEOTIDES: &str = "ACGT";

pub fn count(nucleotide: char, dna: &str) -> Result<usize, char> {
    if NUCLEOTIDES.contains(nucleotide) {
        let counts = nucleotide_counts(dna)?;
        counts.get(&nucleotide).cloned().ok_or_else(|| nucleotide)
    } else {
        Err(nucleotide)
    }
}

pub fn nucleotide_counts(dna: &str) -> Result<HashMap<char, usize>, char> {
    dna.chars().try_fold(
        NUCLEOTIDES.chars().map(|x| (x, 0)).collect(),
        |mut acc: HashMap<char, usize>, x| match acc.get_mut(&x) {
            Some(v) => {
                *v += 1;
                Ok(acc)
            }
            None => Err(x),
        },
    )
}
