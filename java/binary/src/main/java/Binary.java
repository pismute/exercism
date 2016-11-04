import java.util.stream.IntStream;

public class Binary {
    private final String binaryString;

    public Binary(String binaryString) {
        this.binaryString = binaryString;
    }

    public int binaryToDecimal(String binaryString) {
        final int untilLen = binaryString.length() - 1;
        return IntStream.rangeClosed(0, untilLen)
                .map(i -> (binaryString.charAt(i) - '0') << -(i - untilLen))
                .sum();
    }

    public boolean isValidBinaryString(String binaryString) {
        return binaryString.chars()
                .allMatch(i-> '0' == i || i == '1');
    }

    public int getDecimal() {
        return isValidBinaryString(binaryString)? binaryToDecimal(binaryString): 0;
    }
}
