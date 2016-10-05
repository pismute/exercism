import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.reducing;

public class DNA {
    private final String dna;

    private final Map<Character, Integer> _nucleotideCounts =
            new HashMap<Character, Integer>() {{
                put('A', 0);
                put('C', 0);
                put('G', 0);
                put('T', 0);
            }};

    public static <T> Collector<T, ?, Integer> countingInt() {
        return reducing(0, e -> 1, Integer::sum);
    }

    public DNA(String dna) {
        this.dna = dna;

        _nucleotideCounts.putAll(
            dna.chars()
                    .mapToObj(i-> new Character((char)i))
                    .collect(Collectors.groupingBy(Function.identity(), countingInt())));
    }

    public Map<Character, Integer> nucleotideCounts() {
        return _nucleotideCounts;
    }

    public int count(char character) {
        Integer value = _nucleotideCounts.get(character);

        if(value == null)
            throw new IllegalArgumentException(character + " is not a nucleotide");

        return value;
    }
}
