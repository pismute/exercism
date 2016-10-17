import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class WordCount {
    public static final Pattern WORD_PATTERN = Pattern.compile("\\W+");

    public Map<String, Integer> phrase(String sentences) {
        return WORD_PATTERN.splitAsStream(sentences.toLowerCase())
                .collect(Collectors.groupingBy(Function.identity(),
                        Collectors.collectingAndThen(Collectors.counting(), Long::intValue)));
    }
}
