import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Scrabble {
    public static <K, V> Function<K, Map.Entry<K, V>> entryWithValue(V value) {
        return key-> new AbstractMap.SimpleEntry<K, V>(key, value);
    }

    private final Map<Character, Integer> SCORES =
            new HashMap<String, Integer>() {{
                put("AEIOULNRST", 1);
                put("DG", 2);
                put("BCMP", 3);
                put("FHVWY", 4);
                put("K", 5);
                put("JX", 8);
                put("QZ", 10);
            }}.entrySet().stream()
                    .flatMap(entry-> entry.getKey().chars()
                            .mapToObj(i-> (char)i)
                            .map(entryWithValue(entry.getValue())))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    public final String input;

    public Scrabble(String input) {
        this.input = input;
    }

    public int getScore() {
        return Optional.ofNullable(input)
                .map(string->
                        string.chars()
                                .mapToObj(i -> (char)i)
                                .map(Character::toUpperCase)
                                .map(SCORES::get)
                                .map(Optional::ofNullable)
                                .filter(Optional::isPresent)
                                .mapToInt(Optional::get)
                                .sum())
                .orElse(0);
    }
}
