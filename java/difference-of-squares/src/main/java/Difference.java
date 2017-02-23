import java.util.stream.IntStream;

public final class Difference {

    public static int square(int x) {
        return x * x;
    }

    public static int computeSquareOfSumTo(int n) {
        return square(IntStream.rangeClosed(1, n).sum());
    }

    public static int computeSumOfSquaresTo(int n) {
        return IntStream.rangeClosed(1, n)
                .map(Difference::square)
                .sum();
    }

    public static int betweenSquareOfSumAndSumOfSquaresTo(int n) {
        return computeSquareOfSumTo(n) - computeSumOfSquaresTo(n);
    }
}
