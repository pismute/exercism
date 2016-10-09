import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class WordCount {
    public static final Pattern WORD_PATTERN = Pattern.compile("\\W+");

    public static <T> Collector<T, ?, Integer> countingInt() {
        return Collectors.summingInt(e->1);
    }

    public Map<String, Integer> phrase(String sentences) {
        return WORD_PATTERN.splitAsStream(sentences.toLowerCase())
                .collect(Collectors.groupingBy(Function.identity(), countingInt()));
    }
}
