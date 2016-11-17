import java.util.stream.IntStream;

public class Prime {
    public static boolean isPrime(int n) {
        return IntStream.rangeClosed(2, (int)Math.sqrt(n))
                .allMatch(i-> n % i != 0);
    }

    public static int nth(int n) {
        return IntStream.iterate(2, i-> i+1)
                .filter(Prime::isPrime)
                .skip(n-1)
                .findFirst()
                    .getAsInt();
    }
}
