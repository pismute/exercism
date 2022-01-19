const EARTH_SECONDS: f64 = 31557600.0;

#[derive(Debug)]
pub struct Duration(f64);

impl From<u64> for Duration {
    fn from(s: u64) -> Self {
        Self(s as f64)
    }
}

pub trait Planet {
    const EARTH_YEARS: f64;

    fn years_during(d: &Duration) -> f64 {
        d.0 / (EARTH_SECONDS * Self::EARTH_YEARS)
    }
}

macro_rules! planet {
    ($name: ident, $earth_years: expr) => {
        pub struct $name;
        impl Planet for $name {
            const EARTH_YEARS: f64 = $earth_years;
        }
    };
}

planet!(Mercury, 0.2408467);
planet!(Venus, 0.61519726);
planet!(Earth, 1.0);
planet!(Mars, 1.8808158);
planet!(Jupiter, 11.862615);
planet!(Saturn, 29.447498);
planet!(Uranus, 84.016846);
planet!(Neptune, 164.79132);
