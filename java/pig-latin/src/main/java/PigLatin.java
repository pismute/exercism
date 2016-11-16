import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class PigLatin {
    public static final Pattern NOT_WORD = Pattern.compile("\\W+");

    public static final List<String> VOWELS =
            Arrays.asList("a", "e", "i", "o", "u", "yt", "xr");

    public static final List<String> CONSONANTS =
            Arrays.asList("ch", "qu", "thr", "th", "sch");

    public static final String XQU = "qu";

    public static Optional<String> validVowel(String input) {
        return VOWELS.stream()
                .filter(input::startsWith)
                .findAny()
                    .map(x-> input);
    }

    public static Optional<String> validEdgeConsonants(String input) {
        return CONSONANTS.stream()
                .filter(input::startsWith)
                .findAny()
                    .map(x-> input.substring(x.length()) + x);
    }

    public static Optional<String> validXQU(String input) {
        return XQU.equals(input.substring(1, 3))?
                Optional.of(input.substring(3) +  input.substring(0, 3)): Optional.empty();
    }

    public static String translateWord(String word) {
        return validVowel(word)
                .orElseGet(()-> {
                    return validEdgeConsonants(word).orElseGet(() -> {
                        return validXQU(word).orElseGet(() -> {
                            return word.substring(1) + word.substring(0, 1);
                        });
                    });
                }) + "ay";
    }
    public static String translate(String input) {
        return NOT_WORD.splitAsStream(input)
                .map(PigLatin::translateWord)
                .collect(Collectors.joining(" "));
    }
}
