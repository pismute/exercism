import java.util.Objects;

public final class BoardCoordinate {
    public static final int WIDTH = 8;
    public static final int HEIGHT = 8;
    private final int rank;
    private final int file;

    public BoardCoordinate(int rank, int file) {
        require(rank >= 0, "Coordinate must have positive rank.");
        require(rank < WIDTH, "Coordinate must have rank <= 7.");
        require(file >= 0, "Coordinate must have positive file.");
        require(file < HEIGHT, "Coordinate must have file <= 7.");

        this.rank = rank;
        this.file = file;
    }

    public boolean canAttack(BoardCoordinate that) {
        return rank == that.rank ||
                file == that.file ||
                rank - file == that.rank - that.file ||
                rank + file == that.rank + that.file;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoardCoordinate that = (BoardCoordinate) o;
        return rank == that.rank &&
                file == that.file;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rank, file);
    }

    public static void require(boolean condition, String message){
        if(!condition) throw new IllegalArgumentException(message);
    }
}
