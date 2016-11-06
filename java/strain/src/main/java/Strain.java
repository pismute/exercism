import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Strain {
    public static <T> List<T> keep(List<T> input, Predicate<T> f) {
        return input.stream()
                .collect(ArrayList::new,
                        (acc, t)-> {
                            if(f.test(t)) acc.add(t);
                        },
                        ArrayList::addAll);
    }

    public static <T> List<T> discard(List<T> input, Predicate<T> f) {
        return keep(input, f.negate());
    }
}
