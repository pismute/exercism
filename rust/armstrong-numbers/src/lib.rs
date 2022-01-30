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

pub fn is_armstrong_number(num: u32) -> bool {
    let len = (num as f64).log10() as u32 + 1;

    let armstrong: u32 = unfoldr(num, |x| if *x < 1 { None } else { Some((x / 10, x % 10)) })
        .map(|x| x.pow(len))
        .sum();

    armstrong == num
}
