import java.util.function.Consumer;

final class Robot {
    public static final char Advance = 'A';
    public static final char Left = 'L';
    public static final char Right = 'R';

    private GridPosition gridPosition;
    private Orientation orientation;

    public Robot(GridPosition gridPosition, Orientation orientation) {
        this.gridPosition = gridPosition;
        this.orientation = orientation;
    }

    public GridPosition getGridPosition() {
        return gridPosition;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void turnRight() {
        orientation = orientation.next();
    }

    public void turnLeft() {
        orientation = orientation.prev();
    }

    public void advance() {
        final GridPosition gp = gridPosition;

        final int x =
                orientation == Orientation.WEST ? gp.x - 1:
                orientation == Orientation.EAST ? gp.x + 1: gp.x;

        final int y =
                orientation == Orientation.NORTH ? gp.y + 1:
                orientation == Orientation.SOUTH ? gp.y - 1: gp.y;

        gridPosition = new GridPosition(x, y);
    }

    public void stay() {

    }

    public static Consumer<Robot> move(int c) {
       return Advance == c ? Robot::advance:
              Left == c ? Robot::turnLeft:
              Right == c ? Robot::turnRight: Robot::stay;

    }

    public void simulate(String letter) {
        letter.chars()
                .mapToObj(Robot::move)
                .forEach(x -> x.accept(this));
    }
}
