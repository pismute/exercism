use std::iter::{once, successors};

pub struct PascalsTriangle(u32);

impl PascalsTriangle {
    pub fn new(row_count: u32) -> Self {
        PascalsTriangle(row_count)
    }

    pub fn rows(&self) -> Vec<Vec<u32>> {
        successors(Some(vec![1_u32]), |x| {
            Some(
                once(1_u32)
                    .chain(x.windows(2).map(|nums| nums.iter().sum()))
                    .chain(once(1_u32))
                    .collect::<Vec<_>>(),
            )
        })
        .take(self.0 as usize)
        .collect::<Vec<_>>()
    }
}
