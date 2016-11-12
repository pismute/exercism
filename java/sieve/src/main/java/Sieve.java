import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;

public class Sieve {
    private final int number;

    public Sieve(int number) {
        this.number = number;
    }

    public OptionalInt findFirst(IntPredicate sieve) {
        return IntStream.rangeClosed(2, number)
                .filter(sieve)
                .findFirst();
    }

    public List<Integer> getPrimes() {
        List<Integer> acc = new ArrayList<>();
        IntPredicate sieve = (i)-> true;

        OptionalInt o = findFirst(sieve);
        while(o.isPresent()) {
            final int next = o.getAsInt();
            acc.add(next);
            sieve = sieve.and(i-> i % next != 0);
            o = findFirst(sieve);
        }

        return acc;
    }
}
