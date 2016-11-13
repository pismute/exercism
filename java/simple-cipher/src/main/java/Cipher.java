import java.util.Random;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;

public class Cipher {
    private final String key;

    private static final IntPredicate tureP = (i)-> true;

    public Cipher(String key) {
        if(key.isEmpty() ||
                key.chars().anyMatch((i) -> !Character.isLowerCase(i))) {
            throw new IllegalArgumentException("Incorrect Key: " + key);
        }

        this.key = key;
    }

    public Cipher() {
        this.key = randomString(100);
    }


    public static String randomString(int length) {
        final Random rand = new Random();
        return rand.ints('a', 'z' + 1)
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();
    }

    public String getKey() {
        return key;
    }


    public static int normalize(int ch) {
        return ('a' <= ch && ch <= 'z')? ch:
                (ch < 'a')? normalize(ch + 26): normalize(ch - 26);
    }

    public String encode(String plain) {
        return IntStream.range(0, plain.length())
                .map(i-> plain.charAt(i) + (key.charAt(i) - 'a'))
                .map(Cipher::normalize)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();
    }

    public String decode(String cipher) {
        return IntStream.range(0, cipher.length())
                .map(i-> cipher.charAt(i) - (key.charAt(i) - 'a'))
                .map(Cipher::normalize)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();
    }
}
