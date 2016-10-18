import java.util.stream.IntStream;

public class Bob {
    public static final String WHATEVER = "Whatever.";
    public static final String SURE = "Sure.";
    public static final String WHOA = "Whoa, chill out!";
    public static final String FINE = "Fine. Be that way!";

    public static final char last(String s) {
        return s.charAt(s.length()-1);
    }

    public static final boolean isSure(String s){
        return last(s) == '?';
    }

    public static final boolean isWhoa(String s) {
        return s.chars().noneMatch(Character::isLowerCase) &&
                s.chars().anyMatch(Character::isUpperCase);
    }

    public static final boolean isStreamEmpty(IntStream stream) {
        return !stream.findFirst().isPresent();
    }

    public static String hey(String msg) {
        final String trimmed = msg.trim();
        return trimmed.isEmpty() ? FINE :
                isWhoa(trimmed) ? WHOA :
                        isSure(trimmed) ? SURE :
                                WHATEVER;
    }
}