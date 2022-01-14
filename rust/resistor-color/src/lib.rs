use enum_iterator::IntoEnumIterator;
use int_enum::IntEnum;
use std::fmt;

#[repr(usize)]
#[derive(Debug, PartialEq, Clone, Copy, IntEnum, IntoEnumIterator)]
pub enum ResistorColor {
    Black = 0,
    Brown = 1,
    Red = 2,
    Orange = 3,
    Yellow = 4,
    Green = 5,
    Blue = 6,
    Violet = 7,
    Grey = 8,
    White = 9,
}

impl fmt::Display for ResistorColor {
    fn fmt(&self, f: &mut fmt::Formatter) -> fmt::Result {
        use ResistorColor::*;

        match *self {
            Black => write!(f, "Black"),
            Blue => write!(f, "Blue"),
            Brown => write!(f, "Brown"),
            Green => write!(f, "Green"),
            Grey => write!(f, "Grey"),
            Orange => write!(f, "Orange"),
            Red => write!(f, "Red"),
            Violet => write!(f, "Violet"),
            White => write!(f, "White"),
            Yellow => write!(f, "Yellow"),
        }
    }
}

pub fn color_to_value(_color: ResistorColor) -> usize {
    _color.int_value()
}

pub fn value_to_color_string(value: usize) -> String {
    match ResistorColor::from_int(value) {
        Ok(v) => v.to_string(),
        _ => String::from("value out of range"),
    }
}

pub fn colors() -> Vec<ResistorColor> {
    Vec::from_iter(ResistorColor::into_enum_iter())
}
