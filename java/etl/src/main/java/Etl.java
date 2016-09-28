import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Etl {
    // from Minborg's
    // http://minborgsjavapot.blogspot.com/2014/12/java-8-initializing-maps-in-smartest-way.html
    public static <K, V> Map.Entry<K, V> entry(K key, V value) {
        return new AbstractMap.SimpleEntry<>(key, value);
    }

    public Map<String, Integer> transform(Map<Integer, List<String>> old) {
        return old.entrySet().stream()
                .flatMap((e) ->
                    e.getValue().stream()
                            .map((c) -> entry(c.toLowerCase(), e.getKey()))
                )
                .collect(Collectors.toMap((e)-> e.getKey(), (e) -> e.getValue()));
    }
}
