import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Crypto {
    public static Function<String, Optional<Character>> getOptionWithIndex(int i) {
        return xs -> i < xs.length()? Optional.of(xs.charAt(i)): Optional.empty();
    }

    public static List<String> transposeAll(List<String> square, int size) {
        return IntStream.range(0, size)
                .mapToObj(i -> square.stream()
                        .map(getOptionWithIndex(i))
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .map(c -> c.toString())
                        .collect(Collectors.joining()))
                .collect(Collectors.toList());
    }

    public static List<String> grouped(String cs, int size) {
        return IntStream.rangeClosed(0, (cs.length()-1)/size)
                .map(i-> i*size)
                .mapToObj(i-> cs.substring(i, Math.min(i + size, cs.length())))
                .collect(Collectors.toList());
    }

    private final String plainText;

    public Crypto(String plainText) {
        this.plainText = plainText;
    }

    public String getNormalizedPlaintext() {
        return plainText.toLowerCase().chars()
                .filter(Character::isLetterOrDigit)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();
    }

    public int getSquareSize() {
        return (int)Math.ceil(Math.sqrt(getNormalizedPlaintext().length()));
    }

    public List<String> getPlaintextSegments() {
        return grouped(getNormalizedPlaintext(), getSquareSize());
    }

    public String getCipherText() {
        return transposeAll(getPlaintextSegments(), getSquareSize()).stream()
                .collect(Collectors.joining());
    }

    public String getNormalizedCipherText() {
        return transposeAll(getPlaintextSegments(), getSquareSize()).stream()
                .collect(Collectors.joining(" "));
    }
}
