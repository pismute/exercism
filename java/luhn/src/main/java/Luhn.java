import java.util.Arrays;
import java.util.stream.IntStream;

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

        return doubleEverySecond(digits, len%2);
    }

    public int getCheckSum() {
        return stream(getAddends()).sum();
    }

    public boolean isValid() {
        return getCheckSum()%10 == 0;
    }

    public static int[] toDigits(int number) {
        final int[] reversed =
                IntStream.iterate(number, nr-> nr / 10)
                        .limit((long)Math.log10(number) + 1)
                        .map(nr-> nr % 10)
                        .toArray();

        return reverseArray(reversed);
    }

    public static int[] doubleEverySecond(int[] is, int evenOdd) {
        return IntStream.range(0, is.length)
                .map(i-> i%2==evenOdd? is[i]*2: is[i])
                .map(i-> i > 9? i-9: i)
                .toArray();
    }

    public static long create(int number) {
        final int[] digits = toDigits(number);
        final int len = digits.length;

        final int[] doubled = doubleEverySecond(digits, 1 - len%2);

        final long checksum = (10 - (Arrays.stream(doubled).sum() % 10)) % 10;

        return (long)number * 10 + checksum;
    }

    public static int checksum(int[] is) {
        return stream(is).sum();
    }

    public static int[] reverseArray(int[] array) {
        final int len = array.length;
        return IntStream.rangeClosed(1, len)
                .map(i-> array[len-i])
                .toArray();
    }
}
