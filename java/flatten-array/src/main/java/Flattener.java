import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

final class Flattener {

    public Stream<Object> enflat(Object x) {
        if(x == null ) return Stream.empty();
        else if(x instanceof List) {
            return ((List) x).stream()
                    .flatMap(this::enflat);
        }
        else return Stream.of(x);
    }

    public List<Object> flatten(List<Object> xs) {
        return enflat(xs).collect(Collectors.toList());
    }
}
