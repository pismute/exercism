import java.util.Arrays;
import java.util.stream.IntStream;

public class PascalsTriangle {
    public static int[] level(int[] base) {
        int len = base.length;
        return IntStream.rangeClosed(0, len)
                .map(i-> i == 0 || i == len? 1: base[i-1] + base[i])
                .toArray();
    }

    public static int[][] computeTriangle(int n) {
        if(n < 0) throw new IllegalArgumentException("must n >=0");

        int[] base = {};
        int[][] triangle = new int[n][];

        for(int i=0; i<n; i++) {
            base = triangle[i] = level(base);
        }

        return triangle;
    }

    public static boolean isTriangle(int[][] input) {
        int[] base = {};
        int n = input.length;

        for(int i=0; i<n; i++) {
            if( Arrays.equals(level(base), input[i]) == false)
                return false;
            base = input[i];
        }

        return true;
    }
}
