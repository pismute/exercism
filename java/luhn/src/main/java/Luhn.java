import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static java.util.Arrays.stream;

public class Luhn {
    private final int number;

    public Luhn(int number) {
        this.number = number;
    }

    public int getCheckDigit() {
        return number%10;
    }

    public int[] getAddends() {
        final int[] digits = toDigits(number);

        final int len = digits.length;

        return doubleEverySecond(digits, len%2, len);
    }

    public int getCheckSum() {
        return stream(getAddends()).sum();
    }

    public boolean isValid() {
        return getCheckSum()%10 == 0;
    }

    public static int[] toDigits(int number) {
        final List<Integer> unfolded = Luhn.unfoldLeft(number, (Integer seed) -> {
            final int s = seed.intValue();
            return s <= 0? Optional.empty():
                    Optional.of(Pair.of(s / 10, s % 10));
        });

        return unfolded.stream()
                .mapToInt(Integer::intValue)
                .toArray();
    }

    public static int[] doubleEverySecond(int[] is, int start, int until) {
        for(int i = start; i < until; i += 2) {
            final int ii = is[i] * 2;
            is[i] = ii > 9? ii - 9: ii;
        }

        return is;
    }

    public static long create(int number) {
        final int[] digits = toDigits(number);
        final int len = digits.length;

        doubleEverySecond(digits, len%2 == 0? 1: 0, len);

        final long checksum = (10 - (stream(digits).sum() % 10)) % 10;

        return (long)number * 10 + checksum;
    }

    public static int checksum(int[] is) {
        return stream(is).sum();
    }

    public static <A, B> List<B> unfoldLeft(A seed, Function<A, Optional<Pair<A, B>>> f) {
        final List<B> list = new ArrayList<B>();

        Optional<Pair<A, B>> o = f.apply(seed);
        while(o.isPresent()) {
            o = o.flatMap(pair -> {
                list.add(0, pair.getRight());
                return f.apply(pair.getLeft());
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
