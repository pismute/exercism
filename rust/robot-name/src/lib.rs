use std::{cell::RefCell, collections::HashSet};

use rand::{distributions::Uniform, prelude::Distribution, thread_rng};

const NR_OF_ALPHABET: u32 = 26;

struct UniqueRand {
    dist: Uniform<u32>,
    unique: HashSet<u32>,
}

impl UniqueRand {
    pub fn new() -> UniqueRand {
        UniqueRand {
            dist: Uniform::new(0, NR_OF_ALPHABET * NR_OF_ALPHABET * 1000),
            unique: HashSet::new(),
        }
    }

    fn sample(&mut self) -> u32 {
        let mut rng = thread_rng();
        let mut s = self.dist.sample(&mut rng);
        while self.unique.contains(&s) {
            s = self.dist.sample(&mut rng);
        }
        self.unique.insert(s);
        s
    }
}

thread_local! {
    static UNIQUE: RefCell<UniqueRand> = RefCell::new(UniqueRand::new());
}

pub struct Robot {
    name: String,
}

impl Robot {
    const A: u32 = 'A' as u32;

    fn encode(x: u32) -> (char, char, u16) {
        let letters: u32 = x / 1000;
        let head = (letters / NR_OF_ALPHABET) + Robot::A;
        let tail = (letters % NR_OF_ALPHABET) + Robot::A;
        let num = x % 1000;

        (head as u8 as char, tail as u8 as char, num as u16)
    }

    fn gen_name() -> String {
        let r = UNIQUE.with(|x| x.borrow_mut().sample());
        let (h, t, n) = Robot::encode(r);

        format!("{}{}{:03}", h, t, n)
    }

    pub fn new() -> Self {
        let name = Robot::gen_name();

        Robot { name }
    }

    pub fn name(&self) -> &str {
        self.name.as_str()
    }

    pub fn reset_name(&mut self) {
        self.name = Robot::gen_name();
    }
}
