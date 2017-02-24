import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

final class HandshakeCalculator {

    public static Predicate<Signal> isMatched(int x) {
        return signal -> signal.getCode() == (x & signal.getCode());
    }

    public static Function<List<Signal>, List<Signal>> toAction(Signal signal) {
        return xs -> {
            if(signal == Signal.REVERSE) Collections.reverse(xs);
            else xs.add(signal);

            return xs;
        };
    }

    public List<Signal> calculateHandshake(int x) {
        return EnumSet.allOf(Signal.class).stream()
                .filter(HandshakeCalculator.isMatched(x))
                .map(HandshakeCalculator::toAction)
                .reduce(Function.identity(), Function::andThen)
                    .apply(new ArrayList<>());
    }
}
