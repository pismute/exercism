struct Unfold<B, F> {
    seed: B,
    f: F,
}

impl<A, B, F> Iterator for Unfold<B, F>
where
    F: FnMut(&B) -> Option<(A, B)>,
{
    type Item = A;

    #[inline]
    fn next(&mut self) -> Option<Self::Item> {
        let (item, seed) = (self.f)(&self.seed)?;
        self.seed = seed;
        Some(item)
    }
}

/// std::iter::successors is not the best.
fn unfold<A, B, F>(seed: B, f: F) -> Unfold<B, F>
where
    F: FnMut(&B) -> Option<(A, B)>,
{
    Unfold { seed, f }
}

pub fn find<A: Ord, B: AsRef<[A]>>(array: B, key: A) -> Option<usize> {
    let arr = array.as_ref();

    unfold((0, arr.len()), |(start, end)| {
        let i = start + (end - start) / 2;

        if start == end {
            None
        } else {
            arr.get(i).map(|x| {
                if *x == key {
                    (Some(i), (0, 0))
                } else if *x < key {
                    (None, (i + 1, *end))
                } else {
                    (None, (*start, i))
                }
            })
        }
    })
    .last()
    .flatten()
}
