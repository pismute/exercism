import java.util.List;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Series {
    private final String string;

    public Series(String string) {
        this.string = string;
    }

    public List<Integer> getDigits() {
        return string.chars()
                .map(Character::getNumericValue)
                .mapToObj(Integer::valueOf)
                .collect(Collectors.toList());
    }

    public List<Integer> getRange(int start, int limit) {
        return getDigits().stream()
                .skip(start)
                .limit(limit)
                .collect(Collectors.toList());
    }

    public List<List<Integer>> slices(int i) {
        final int n = string.length();

        if( n < i) throw new IllegalArgumentException("slice is too big: + " + n);

        final List<Integer> digits = getDigits();

        final IntFunction<List<Integer>> f = start -> getRange(start, i);

        return IntStream.rangeClosed(0, n - i)
                .mapToObj(f)
                .collect(Collectors.toList());
    }

}
