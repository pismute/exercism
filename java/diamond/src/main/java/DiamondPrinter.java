import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

final class DiamondPrinter {
    public static final char FROM = 'A';

    public static String line(int x, int y) {
        final int i = x - FROM;
        return IntStream.rangeClosed(FROM - i, FROM + i)
                .map(j -> FROM + Math.abs(FROM - j) == y? y: ' ')
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();
    }

    public List<String> printToList(char x) {
        return IntStream.concat(
                IntStream.rangeClosed(FROM, x),
                IntStream.iterate(x - 1, y -> y - 1).limit(x - FROM))
                .mapToObj(y -> line(x, y))
                .collect(Collectors.toList());
    }
}
