import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by pismute on 09/10/2016.
 */
public class Pangrams {
    public static final Supplier<Stream<Integer>> ALPHABETS =
            ()-> IntStream.rangeClosed((int) 'a', (int) 'z').mapToObj(Integer::new);

    public static boolean isPangram(String sentence) {
        final Set<Integer> lowercases =
                sentence.toLowerCase().chars()
                        .distinct()
                        .filter(Character::isLowerCase)
                        .mapToObj(Integer::new)
                        .collect(Collectors.toSet());

        return ALPHABETS.get().allMatch(lowercases::contains);
    }
}
