import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LargestSeriesProductCalculator {
    private final String digits;

    public LargestSeriesProductCalculator(String digits) {
        if(digits == null) {
            throw new IllegalArgumentException("String to search must be non-null.");
        }

        this.digits =
                validDigits(digits)
                        .orElseThrow(()->
                                new IllegalArgumentException("String to search may only contains digits."));

    }

    public Optional<String> validDigits(String digits) {
        return digits.chars().anyMatch(i-> i < '0' || '9' < i)?
                Optional.empty():
                Optional.of(digits);
    }

    public long calculateLargestProductForSeriesLength(int n) {
        if(n > digits.length()) {
            throw new IllegalArgumentException("Series length must be less than or equal to the length of the string to search.");
        }else if(n < 0) {
            throw new IllegalArgumentException("Series length must be non-negative.");
        }

        return sliding(digits, n).stream()
                    .mapToLong(LargestSeriesProductCalculator::product)
                    .max()
                        .getAsLong();
    }

    public static List<String> sliding(String string, int n) {
        return IntStream.range(0, string.length() - n + 1)
                .mapToObj(i-> string.substring(i, i + n))
                .collect(Collectors.toList());
    }

    public static long product(String string) {
        return string.chars()
                .map(i-> i - '0')
                .asLongStream()
                .reduce(1L, (a, b)-> a * b);
    }
}
