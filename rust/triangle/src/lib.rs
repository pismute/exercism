use std::{fmt::Debug, ops::Add};

#[derive(PartialEq)]
pub enum Triangle {
    Equilateral,
    Isosceles,
    Scalene,
}

impl Triangle {
    pub fn build<A: PartialEq + PartialOrd + Copy + Default + Add<Output = A> + Debug>(
        sides: [A; 3],
    ) -> Option<Triangle> {
        // assume sides doesn't have negative number
        // what is the best way to identiy positive numbers within generics?
        let default: A = Default::default();
        let mut sorted = sides.clone();

        // A must not be uncomparable value like NaN.
        sorted.sort_unstable_by(|x, y| x.partial_cmp(y).unwrap_or(std::cmp::Ordering::Equal));

        match sorted {
            [x, y, z] if sides.iter().any(|x| *x == default) || x + y < z => None,
            [x, y, z] if x == y && y == z => Some(Triangle::Equilateral),
            [x, y, z] if x == y || y == z => Some(Triangle::Isosceles),
            _ => Some(Triangle::Scalene),
        }
    }

    pub fn is_equilateral(&self) -> bool {
        *self == Triangle::Equilateral
    }

    pub fn is_scalene(&self) -> bool {
        *self == Triangle::Scalene
    }

    pub fn is_isosceles(&self) -> bool {
        *self == Triangle::Isosceles
    }
}
