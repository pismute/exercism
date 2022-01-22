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

fn unfold<A, B, F>(seed: B, f: F) -> Unfold<B, F>
where
    F: FnMut(&B) -> Option<(A, B)>,
{
    Unfold { seed, f }
}

pub fn is_armstrong_number(num: u32) -> bool {
    let len = (num as f64).log10() as u32 + 1;

    let armstrong: u32 = unfold(num, |x| if *x < 1 { None } else { Some((x % 10, x / 10)) })
        .map(|x| x.pow(len))
        .sum();

    armstrong == num
}
