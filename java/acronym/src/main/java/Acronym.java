import java.util.function.Function;
import java.util.regex.Pattern;

public class Acronym {
    public static final Pattern PUNCTUATION = Pattern.compile("[:]");
    public static final Pattern NOT_WORD = Pattern.compile("\\W+");

    public static Function<String, String> capitalize =
            word-> word.isEmpty()? word: Character.toUpperCase(word.charAt(0)) + word.substring(1);

    public static String generate(String phrase) {
        return PUNCTUATION.splitAsStream(phrase)
                .limit(1)
                .flatMap(NOT_WORD::splitAsStream)
                .map(capitalize)
                .flatMapToInt(word-> word.chars().filter(Character::isUpperCase))
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();
    }
}
