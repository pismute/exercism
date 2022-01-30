struct Unfoldr<A, F> {
    seed: A,
    f: F,
}

impl<A, B, F> Iterator for Unfoldr<A, F>
where
    F: FnMut(&A) -> Option<(A, B)>,
{
    type Item = B;

    #[inline]
    fn next(&mut self) -> Option<Self::Item> {
        let (seed, item) = (self.f)(&self.seed)?;
        self.seed = seed;
        Some(item)
    }
}

fn unfoldr<A, B, F>(seed: A, f: F) -> Unfoldr<A, F>
where
    F: FnMut(&A) -> Option<(A, B)>,
{
    Unfoldr { seed, f }
}

pub fn find<A: Ord, B: AsRef<[A]>>(array: B, key: A) -> Option<usize> {
    let arr = array.as_ref();

    unfoldr((0, arr.len()), |(start, end)| {
        let i = start + (end - start) / 2;

        if start == end {
            None
        } else {
            arr.get(i).map(|x| {
                if *x == key {
                    ((0, 0), Some(i))
                } else if *x < key {
                    ((i + 1, *end), None)
                } else {
                    ((*start, i), None)
                }
            })
        }
    })
    .last()
    .flatten()
}
