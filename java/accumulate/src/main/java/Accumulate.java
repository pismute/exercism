import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

public class Accumulate {
    public static <T> List<T> accumulate(List<T> xs, UnaryOperator<T> f) {
        return xs.stream()
                .collect(ArrayList::new,
                        (acc, x) -> acc.add(f.apply(x)),
                        ArrayList::addAll);
    }
}
