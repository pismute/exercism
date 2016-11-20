import java.util.stream.IntStream;

public final class Difference {

    public static int computeSquareOfSumTo(int n) {
        final int sum = IntStream.rangeClosed(1, n).sum();
        return sum * sum;
    }

    public static int computeSumOfSquaresTo(int n) {
        return IntStream.rangeClosed(1, n)
                .map(i-> i*i)
                .sum();
    }

    public static int betweenSquareOfSumAndSumOfSquaresTo(int n) {
        return computeSquareOfSumTo(n) - computeSumOfSquaresTo(n);
    }
}
