import java.util.Optional;

public class Trinary {
    public static Optional<Integer> validDigit(int d) {
        return '0' <= d && d < '3'? Optional.of(d - '0') : Optional.empty();
    }

    public static int toDecimal(String string) {
        return string.chars()
                .mapToObj(Trinary::validDigit)
                .reduce(Optional.of(0), (l, r) -> l.flatMap(ll-> r.map(rr -> ll*3 + rr)))
                    .orElse(0);
    }
}
