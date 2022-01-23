#[derive(Debug)]
pub struct HighScores<'a>(&'a [u32]);

impl<'a> HighScores<'a> {
    pub fn new(scores: &'a [u32]) -> Self {
        Self(scores)
    }

    pub fn scores(&self) -> &[u32] {
        self.0
    }

    pub fn latest(&self) -> Option<u32> {
        self.0.last().cloned()
    }

    pub fn personal_best(&self) -> Option<u32> {
        self.0.iter().max().cloned()
    }

    // select( O(n) ) + sort( O(3log3) )
    pub fn personal_top_three(&self) -> Vec<u32> {
        let mut top_three = if self.0.len() > 3 {
            self.0
                .to_vec()
                .select_nth_unstable_by(3, |x, y| y.cmp(x))
                .0
                .to_vec()
        } else {
            self.0.to_vec()
        };

        top_three.sort_by(|x, y| y.cmp(x));
        top_three
    }
}
