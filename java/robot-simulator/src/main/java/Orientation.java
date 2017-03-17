enum Orientation {
    NORTH, EAST, SOUTH, WEST;

    public Orientation prev() {
        return this == NORTH ? WEST:
               this == EAST ? NORTH:
               this == SOUTH ? EAST: SOUTH;
    }

    public Orientation next() {
        return this == NORTH ? EAST:
               this == EAST ? SOUTH:
               this == SOUTH ? WEST: NORTH;
    }
}
