import java.util.ArrayList;
import java.util.List;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;

public class Sieve {
    private final int number;

    public Sieve(int number) {
        this.number = number;
    }

    public IntStream streamWith(List<IntPredicate> filters) {
        IntStream stream = IntStream.rangeClosed(2, number);

        for(IntPredicate filter: filters) {
            stream = stream.filter(filter);
        }

        return stream;
    }

    public List<Integer> sieves(List<IntPredicate> filters, List<Integer> acc) {
        streamWith(filters).findFirst()
                .ifPresent((i) -> {
                    filters.add((ii)-> ii % i != 0);
                    acc.add(i);
                    sieves(filters, acc);
                });

        return acc;
    }

    public List<Integer> getPrimes() {
        return sieves(new ArrayList<>(), new ArrayList<>());
    }
}
