import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

final class Flattener {
    
    public static Stream<Object> enflat(Object x) {
        if(x == null ) return Stream.empty();
        else if(x instanceof List) {
            return ((List) x).stream()
                    .flatMap(Flattener::enflat);
        }
        else return Stream.of(x);
    }

    public static List<Object> flatten(List<Object> xs) {
        return enflat(xs).collect(Collectors.toList());
    }
}
