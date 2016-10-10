import java.util.stream.IntStream;

public class Hamming {
    public static int compute(String l, String r) {
        if (l.length() != r.length()) throw new IllegalArgumentException("lengths are mismatched!");
        return IntStream.range(0, l.length())
                .map(i -> l.charAt(i) ==  r.charAt(i) ? 0 : 1)
                .sum();
    }
}
