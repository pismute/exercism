import java.util.function.IntFunction;
import java.util.stream.IntStream;

public class SumOfMultiples {

    public static int sum(int limit, int[] xs) {
        return IntStream.of(xs)
                .flatMap(SumOfMultiples.multiples(limit))
                .distinct()
                .sum();
    }

    public static IntFunction<IntStream> multiples(int limit) {
        return x ->
                IntStream.iterate(x, y -> x + y)
                        .limit( limit % x == 0 ? limit/x - 1: limit/x );
    }
}
