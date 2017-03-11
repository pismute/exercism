import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

final class BracketChecker {
    public static final Map<Character, Character> BRAKET_PAIRS =
            new HashMap<Character, Character>(){{
                put('}', '{');
                put(']', '[');
                put(')', '(');
            }};

    public static final Set<Character> BRAKETS = BRAKET_PAIRS.entrySet().stream()
            .flatMap(e -> Stream.of(e.getKey(), e.getValue()))
            .collect(Collectors.toSet());

    private final String input;

    public BracketChecker(String s) {
        this.input = s;
    }

    public boolean isBalanced(Collection<Character> xs) {
        final Stack<Character> stack = new Stack<>();

        for(final Character x : xs) {
            final Character open = BRAKET_PAIRS.get(x);

            if(open == null) stack.push(x);
            else { // when x is close
                if(stack.isEmpty()) return false;
                else {
                    final Character top = stack.pop();

                    if(!open.equals(top)) return false;
                }
            }
        }

        return stack.isEmpty();
    }

    public boolean areBracketsMatchedAndNestedCorrectly() {
        final List<Character> brackets =
                input.chars()
                    .mapToObj(x -> new Character((char)x))
                    .filter(BRAKETS::contains)
                    .collect(Collectors.toList());

        return isBalanced(brackets);
    }

}
