import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Atbash {
    public static int encodeOrDecodeChar(int ch) {
        return Character.isLetter(ch)? 'a' + 'z' - ch: ch;
    }

    public static String encodeOrDecode(String text) {
        return text.toLowerCase().chars()
                .filter(Character::isLetterOrDigit)
                .map(Atbash::encodeOrDecodeChar)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    public static Stream<String> groupedStream(String string, int size) {
        final int len = string.length();
        return IntStream.rangeClosed(0, len/size + (len%size==0? -1: 0))
                .map(i-> i*size)
                .mapToObj(i-> string.substring(i, Math.min(i+size, len)));
    }

    public static String encode(String plain) {
        return groupedStream(encodeOrDecode(plain), 5)
                .collect(Collectors.joining(" "));
    }

    public static String decode(String cipher) {
        return encodeOrDecode(cipher);
    }
}
