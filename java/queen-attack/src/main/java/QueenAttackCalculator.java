public final class QueenAttackCalculator {
    private final BoardCoordinate white;
    private final BoardCoordinate black;

    public QueenAttackCalculator(BoardCoordinate white, BoardCoordinate black) {
        BoardCoordinate.require(white != null && black != null,
                "You must supply valid board coordinates for both Queens.");
        BoardCoordinate.require(!white.equals(black),
                "Queens may not occupy the same board coordinate.");

        this.white = white;
        this.black = black;
    }

    public boolean canQueensAttackOneAnother() {
        return white.canAttack(black);
    }
}
