package com.company.matrix;

public class MatrixRequest {

    public static void doRequest() {
        int sizeX = 10;
        int sizeY = 10;

        String tab[][] = new String[sizeY][sizeX];

        for (int i = 0; i < sizeY; i++)
            for (int j = 0; j < sizeX; j++)
                tab[i][j] = String.valueOf(i * j);

        Matrix<String> matrix = new Matrix<>(tab);

        matrix.show();

        Integer tab1[][] = new Integer[sizeY][sizeX];

        for (int i = 0; i < sizeY; i++)
            for (int j = 0; j < sizeX; j++)
                tab1[i][j] = i * j;

        Matrix<Integer> matrix1 = new Matrix<>(tab1);
        matrix1.show();

        Matrix.add(matrix, matrix, new StringAddition()).show();

        Matrix.add(matrix1, matrix1, new IntegerAddition()).show();
    }
}
