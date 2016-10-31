import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

public class Triangle {
    private final TriangleKind kind;

    public Triangle(double x, double y, double z) throws TriangleException {
        final Supplier<DoubleStream> stream = ()-> DoubleStream.of(x, y, z);

        final List<Double> sorted = stream.get().sorted().boxed().collect(Collectors.toList());
        final double first = sorted.get(0);
        final double second = sorted.get(1);
        final double third = sorted.get(2);

        if(first <= 0 || first + second <= third) throw new TriangleException();


        this.kind = (first == second && second== third) ? TriangleKind.EQUILATERAL:
                (first == second || second == third) ? TriangleKind.ISOSCELES:
                        TriangleKind.SCALENE;
    }


    public TriangleKind getKind() {
        return kind;
    }
}
