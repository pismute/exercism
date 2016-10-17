import java.util.function.IntPredicate;
import java.util.function.IntUnaryOperator;
import java.util.stream.IntStream;

public class Pangrams {
    public static final IntPredicate isLowerAlphabet = i-> 'a' <= i && i <= 'z';

    /*
     * 'a' == 10
     * 'b' == 100
     * 'c' == 1000
     * ... == ...
     * 'z' == ...
     */
    public static final IntUnaryOperator toBitwise = i-> 2 << (i - 'a');

    public static final int ALPHABETS =
            IntStream.rangeClosed('a', 'z').map(toBitwise).sum();

    public static boolean isPangram(String sentence) {
        return sentence.toLowerCase().chars()
                .filter(isLowerAlphabet)
                .map(toBitwise)
                .reduce(0, (a, b)-> a | b) == ALPHABETS;
    }
}

