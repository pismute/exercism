import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class RomanNumeral {
    private static final List<RomanNumber> arabicToRomans =
            new ArrayList<RomanNumber>() {{
                add(new RomanNumber(1000, "M"));
                add(new RomanNumber(900, "CM"));
                add(new RomanNumber(500, "D"));
                add(new RomanNumber(400, "CD"));
                add(new RomanNumber(100, "C"));
                add(new RomanNumber(90, "XC"));
                add(new RomanNumber(50, "L"));
                add(new RomanNumber(40, "XL"));
                add(new RomanNumber(10, "X"));
                add(new RomanNumber(9, "IX"));
                add(new RomanNumber(5, "V"));
                add(new RomanNumber(4, "IV"));
                add(new RomanNumber(1, "I"));
            }};

    private static Function<Integer, Optional<Unfold<Integer, String>>> largestRomanBy =
            number -> arabicToRomans.stream()
                    .filter(t -> t.getArabic() <= number)
                    .findFirst()
                        .map(t-> Unfold.of(number - t.getArabic(), t.getRoman()));

    private final int arabic;

    public RomanNumeral(int arabic) {
        this.arabic = arabic;
    }

    public String getRomanNumeral() {
        return Unfold.unfold(arabic, largestRomanBy).stream()
                .collect(Collectors.joining());
    }

    public static class RomanNumber {
        private final int arabic;
        private final String roman;

        public RomanNumber(int arabic, String roman) {
            this.arabic = arabic;
            this.roman = roman;
        }

        public int getArabic() {
            return arabic;
        }

        public String getRoman() {
            return roman;
        }
    }

    public static class Unfold<Seed, Ele> {
        private final Seed seed;
        private final Ele ele;

        public Unfold(Seed seed, Ele ele) {
            this.seed = seed;
            this.ele = ele;
        }

        public Seed getSeed() {
            return seed;
        }

        public Ele getEle() {
            return ele;
        }

        public static <A, B> Unfold<A, B> of(A a, B b) {
            return new Unfold<>(a, b);
        }

        public static <A, B> List<B> unfold(A seed, Function<A, Optional<Unfold<A, B>>> f) {
            final List<B> list = new ArrayList<>();

            Optional<Unfold<A, B>> o = f.apply(seed);
            while( o.isPresent() ) {
                o = o.flatMap(t -> {
                    list.add(t.getEle());
                    return f.apply(t.getSeed());
                });
            }

            return list;
        }
    }
}
