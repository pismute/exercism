#[derive(Debug)]
pub struct Player {
    pub health: u32,
    pub mana: Option<u32>,
    pub level: u32,
}

impl Player {
    const HEALTH: u32 = 100;
    const MANA: Option<u32> = Some(100);
    const MIN_LEVEL_FOR_MANA: u32 = 10;

    fn new(lev: u32) -> Self {
        Self {
            health: Self::HEALTH,
            mana: if lev >= Self::MIN_LEVEL_FOR_MANA {
                Self::MANA
            } else {
                None
            },
            level: lev,
        }
    }

    pub fn revive(&self) -> Option<Player> {
        match self.health {
            0 => Some(Self::new(self.level)),
            _ => None,
        }
    }

    pub fn cast_spell(&mut self, mana_cost: u32) -> u32 {
        match self.mana {
            None => {
                self.health = self.health - std::cmp::min(self.health, mana_cost);
                0
            }
            Some(x) if x >= mana_cost => {
                self.mana = Some(x - mana_cost);
                mana_cost * 2
            }
            _ => 0,
        }
    }
}
