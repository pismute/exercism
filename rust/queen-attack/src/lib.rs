#[derive(Debug)]
pub struct ChessPosition(i32, i32);

#[derive(Debug)]
pub struct Queen(ChessPosition);

impl ChessPosition {
    pub fn new(rank: i32, file: i32) -> Option<Self> {
        match (rank, file) {
            ((0..=7), (0..=7)) => Some(ChessPosition(rank, file)),
            _ => None,
        }
    }
}

impl Queen {
    pub fn new(position: ChessPosition) -> Self {
        Queen(position)
    }

    pub fn can_attack(&self, other: &Queen) -> bool {
        let dx = self.0 .0 - other.0 .0;
        let dy = self.0 .1 - other.0 .1;
        dx == 0 || dy == 0 || (dx / dy).abs() == 1
    }
}
