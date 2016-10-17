import java.util.List;
import java.util.stream.Collectors;

public class Anagram {
    private final String given;
    private final String givenSorted;

    public Anagram(String given) {
        this.given = given.toLowerCase();
        this.givenSorted = sort(this.given);
    }

    private static String sort(String s) {
        return s.chars()
                .sorted()
                .mapToObj(i-> String.valueOf((char)i))
                .collect(Collectors.joining());
    }

    public List<String> match(List<String> candidates) {
        return candidates.stream()
                .filter(candidate -> !given.equals(candidate.toLowerCase()))
                .filter(candidate -> givenSorted.equals(sort(candidate.toLowerCase())))
                .collect(Collectors.toList());
    }
}
