import java.util.AbstractMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Hexadecimal {
    public static final int BASE = 16;
    public static final String HEXA_DIGITS = "0123456789abcdef";
    public static final Map<Character, Integer> HEXA_TO_DECIMAL =
            IntStream.range(0, BASE)
                    .mapToObj(i-> new AbstractMap.SimpleEntry<>(HEXA_DIGITS.charAt(i), i))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    public static Optional<Integer> hexaToDecimal(Character ch) {
        return Optional.ofNullable(HEXA_TO_DECIMAL.get(ch));
    }

    public static int toDecimal(String hexa) {
        return hexa.chars()
                .mapToObj(i-> (char)i)
                .map(Hexadecimal::hexaToDecimal)
                .reduce(Optional.of(0), (l, r) -> l.flatMap(ll-> r.map(rr-> ll*BASE + rr)))
                    .orElse(0);
    }
}
