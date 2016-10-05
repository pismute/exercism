import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

public class WordCount {
    public static <T> Collector<T, ?, Integer> countingInt() {
        return Collectors.reducing(0, e -> 1, Integer::sum);
    }

    public Map<String, Integer> phrase(String sentences) {
        return asList(sentences.toLowerCase().split("[^\\w]+")).stream()
                .collect(Collectors.groupingBy(Function.identity(), countingInt()));
    }
}
