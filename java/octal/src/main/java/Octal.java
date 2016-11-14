import java.util.OptionalInt;

public class Octal {
    private final String octal;

    public Octal(String octal) {
        this.octal = octal;
    }

    public static OptionalInt validOctal(int i) {
        return 0 <= i && i < 8? OptionalInt.of(i): OptionalInt.empty();
    }

    public int getDecimal() {
        return octal.chars()
                .map(i-> i - '0')
                .mapToObj(Octal::validOctal)
                .peek(System.out::println)
                .reduce(OptionalInt.of(0), (acc, digit)->
                        acc.isPresent() && digit.isPresent()?
                                OptionalInt.of(acc.getAsInt()*8 + digit.getAsInt()):
                                OptionalInt.empty())
                    .orElse(0);
    }
}
