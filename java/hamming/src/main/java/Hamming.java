import java.util.stream.IntStream;

public class Hamming {
    public static int compute(String left, String right) {
        if (left.length() != right.length()) throw new IllegalArgumentException("lengths are mismatched!");

        return IntStream.range(0, left.length())
                .map(i -> left.charAt(i) ==  right.charAt(i) ? 0 : 1)
                .sum();
    }
}
