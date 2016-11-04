import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.LongStream;

public class PrimeFactors {
    public static List<Long> getForNumber(long input) {
        return unfoldRight(input, seed -> {
            final long s = seed;
            return s == 1L? Optional.empty():
                    LongStream.iterate(2, i -> i+1)
                            .filter(i -> s % i == 0)
                            .boxed()
                            .findFirst()
                                .map(primeFactor -> Pair.of(primeFactor, s/primeFactor));
        });
    }

    public static <A, B> List<A> unfoldRight(B seed, Function<B, Optional<Pair<A, B>>> f) {
        final List<A> list = new ArrayList<>();

        Optional<Pair<A, B>> o = f.apply(seed);
        while( o.isPresent() ) {
            o = o.flatMap(t -> {
                list.add(t.getLeft());
                return f.apply(t.getRight());
            });
        }

        return list;
    }

    public static class Pair<Left, Right> {
        private final Left left;
        private final Right right;

        public Pair(Left left, Right right) {
            this.left = left;
            this.right = right;
        }

        public Left getLeft() {
            return left;
        }

        public Right getRight() {
            return right;
        }

        public static <A, B> Pair<A, B> of(A a, B b) {
            return new Pair<>(a, b);
        }
    }
}
