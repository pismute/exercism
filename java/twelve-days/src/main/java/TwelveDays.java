import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TwelveDays {
    public static final String[] NUMBERS = {
            "a",
            "two",
            "three",
            "four",
            "five",
            "six",
            "seven",
            "eight",
            "nine",
            "ten",
            "eleven",
            "twelve"};

    public static final Optional<String>[] ORDINALS = new Optional[]{
            Optional.of("first"),
            Optional.of("second"),
            Optional.of("third"),
            Optional.empty(),
            Optional.of("fifth"),
            Optional.empty(),
            Optional.empty(),
            Optional.of("eighth"),
            Optional.of("ninth"),
            Optional.empty(),
            Optional.empty(),
            Optional.of("twelfth")};

    public static String ordinal(int i) {
        return ORDINALS[i].orElse(NUMBERS[i] + "th");
    }

    public static final String[] PHRASES = {
            "Partridge in a Pear Tree",
            "Turtle Doves",
            "French Hens",
            "Calling Birds",
            "Gold Rings",
            "Geese-a-Laying",
            "Swans-a-Swimming",
            "Maids-a-Milking",
            "Ladies Dancing",
            "Lords-a-Leaping",
            "Pipers Piping",
            "Drummers Drumming"};

    public static StringBuilder phrase(int x) {
        return new StringBuilder(NUMBERS[x]).append(" ").append(PHRASES[x]);
    }

    public static StringBuilder conjunction(int i) {
        return new StringBuilder(i == 0 ? "and " : "");
    }

    public static StringBuilder phrases(int i) {
        if( i == 0 ) return phrase(i);
        else return IntStream.rangeClosed(0, i)
                .mapToObj(x -> conjunction(x).append(phrase(x)))
                .reduce((x, y) -> y.append(", ").append(x))
                    .get();
    }

    public static String verse(int i) {
        final int j = i - 1;

        return new StringBuilder()
                .append("On the ")
                .append(ordinal(j))
                .append(" day of Christmas my true love gave to me, ")
                .append(phrases(j))
                .append(".\n")
                .toString();
    }

    public static String verses(int i, int j) {
        return IntStream.rangeClosed(i, j)
                .mapToObj(TwelveDays::verse)
                .collect(Collectors.joining("\n"));
    }


    public static String sing() {
        return verses(1, 12);
    }
}
