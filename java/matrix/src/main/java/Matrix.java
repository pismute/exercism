import java.util.Arrays;
import java.util.stream.IntStream;

public class Matrix {
    private final int[][] rows;
    private final int[][] cols;

    public Matrix(String xss) {
        final int[][] matrix = parseLines(xss);
        this.rows = matrix;
        this.cols = transpose(matrix);
    }

    public int getRowsCount() {
        return rows.length;
    }

    public int getColumnsCount() {
        return cols.length;
    }

    public int[] getRow(int i) {
        return rows[i];
    }

    public int[] getColumn(int i) {
        return cols[i];
    }

    public static int[][] parseLines(String xss) {
        return Arrays.stream(xss.split("\n"))
                .map(Matrix::parseLine)
                .toArray(int[][]::new);
    }

    public static int[] parseLine(String xs) {
        return Arrays.stream(xs.split(" "))
                .mapToInt(x -> x.charAt(0) - '0')
                .toArray();
    }

    public static int[][] transpose(int[][] xss) {
        return IntStream.range(0, xss[0].length)
                .mapToObj(i -> transposeColumn(xss, i))
                .toArray(int[][]::new);
    }

    public static int[] transposeColumn(int[][] xss, int i) {
        return IntStream.range(0, xss.length)
                .map(j -> xss[j][i])
                .toArray();
    }

}
