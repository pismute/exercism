import java.util.Random;
import java.util.stream.Collectors;

public class Robot {
    private String name;
    private final Random random = new Random();

    public Robot() {
        reset();
    }

    public String getName() {
        return name;
    }

    public void reset() {
        name = twoLetters() + threeDigits();
    }

    private String twoLetters() {
        return random.ints('A', 'Z'+1)
                .limit(2)
                .mapToObj(i -> String.valueOf((char)i))
                  .collect(Collectors.joining());
    }

    private String threeDigits() {
        return random.ints(0, 10)
                .limit(3)
                .mapToObj(String::valueOf)
                  .collect(Collectors.joining());
    }
}
