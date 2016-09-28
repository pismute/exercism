import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Etl {
    public static Function<String, Map.Entry<String, Integer>> entryWithValue(Integer v) {
        return k -> new AbstractMap.SimpleEntry<>(k, v);
    }

    public Map<String, Integer> transform(Map<Integer, List<String>> old) {
        return old.entrySet().stream()
            .flatMap(entry ->
                entry.getValue().stream()
                        .map(String::toLowerCase)
                        .map(entryWithValue(entry.getKey()))
            )
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
