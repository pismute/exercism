import java.util.Arrays;

public class Matrix {
    private final int[][] matrix;

    public Matrix(String xss) {
        this.matrix = parseLines(xss);
    }

    public static int[][] parseLines(String xss) {
        String[] xs = xss.split("\n");
        final int[][] matrix = new int[xs.length][];

        for(int i = 0; i < xs.length; i++ ) {
            matrix[i] = parseLine(xs[i]);
        }

        return matrix;
    }

    public static int[] parseLine(String xs) {
        return Arrays.stream(xs.split(" "))
                .mapToInt(x -> x.charAt(0) - '0')
                .toArray();
    }


    public int getRowsCount() {
        return matrix.length;
    }

    public int getColumnsCount() {
        return matrix[0].length;
    }

    public int[] getRow(int i) {
        return Arrays.copyOf(matrix[i], matrix[i].length);
    }

    public int[] getColumn(int i) {
        final int nRow = matrix.length;
        final int[] column = new int[nRow];

        for(int j = 0; j < nRow; j++) {
            column[j] = matrix[j][i];
        }

        return column;
    }
}
