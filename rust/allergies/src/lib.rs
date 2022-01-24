pub struct Allergies(u32);

#[derive(Debug, PartialEq, Clone, Copy)]
#[repr(u32)]
pub enum Allergen {
    Eggs = 1,
    Peanuts = 2,
    Shellfish = 4,
    Strawberries = 8,
    Tomatoes = 16,
    Chocolate = 32,
    Pollen = 64,
    Cats = 128,
}

impl Allergen {
    const VALUES: &'static [Allergen] = &[
        Allergen::Eggs,
        Allergen::Peanuts,
        Allergen::Shellfish,
        Allergen::Strawberries,
        Allergen::Tomatoes,
        Allergen::Chocolate,
        Allergen::Pollen,
        Allergen::Cats,
    ];
}

impl Allergies {
    pub fn new(score: u32) -> Self {
        Self(score)
    }

    pub fn is_allergic_to(&self, allergen: &Allergen) -> bool {
        let v = *allergen as u32;

        self.0 & v == v
    }

    pub fn allergies(&self) -> Vec<Allergen> {
        Allergen::VALUES
            .iter()
            .filter(|x| self.is_allergic_to(x))
            .cloned()
            .collect()
    }
}
