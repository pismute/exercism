import java.util.*;
import java.util.function.Function;
import java.util.stream.IntStream;

public class Raindrops {
    public static final Map<Integer, String> PLIAONG = new HashMap<Integer, String>() {{
        put(3, "Pling");
        put(5, "Plang");
        put(7, "Plong");
    }};

    public static String convert(int nr) {
        return factors(nr).stream()
                .distinct()
                .filter(PLIAONG::containsKey)
                .map(PLIAONG::get)
                .reduce((l, r)-> l + r)
                    .orElse(String.valueOf(nr));
    }

    public static final List<Integer> factors(int nr) {
        return unfoldLeft(nr, (seed)->
                seed.intValue() < 2 ? Optional.empty():
                        IntStream.iterate(2, i-> i+1)
                                .filter(i -> seed.intValue()%i == 0)
                                .boxed()
                                .findFirst()
                                    .map(i -> Pair.of(i, seed.intValue()/i)));
    }

    public static <Left, Right> List<Left> unfoldLeft(Right seed, Function<Right, Optional<Pair<Left, Right>>> f) {
        final List<Left> list = new ArrayList<>();

        Optional<Pair<Left, Right>> o = f.apply(seed);
        while(o.isPresent()) {
            o = o.flatMap(pair -> {
                list.add(pair.getLeft());
                return f.apply(pair.getRight());
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
            return new Pair(a, b);
        }
    }
}
