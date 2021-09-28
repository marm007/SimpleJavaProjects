package com.company.matrix;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

public class Matrix<T> implements Iterable {

    private int rows;
    private int columns;
    private T matrix[][];

    public Matrix(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;

        @SuppressWarnings("unchecked")
        T[][] array = (T[][]) new Object[rows][columns];
        this.matrix = array;

    }

    public Matrix(T[][] matrix) {
        this.matrix = matrix;

        this.rows = matrix.length;
        this.columns = matrix[0].length;
    }

    public static <T> Matrix<T> add(Matrix<? extends T> A, Matrix<? extends T> B, Addition<T> addition) {

        if ((A.rows != B.rows) || (A.columns != B.columns))
            throw new RuntimeException("Unequal rows and columns.");

        @SuppressWarnings("unchecked")
        Matrix<T> C = new Matrix<T>(A.rows, A.columns);

        for (int r = 0; r < A.rows; r++) {
            for (int c = 0; c < A.columns; c++) {
                C.matrix[r][c] = addition.add(A.matrix[r][c], B.matrix[r][c]);
            }
        }
        return C;
    }

    public void show() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++)
                System.out.print(matrix[i][i] + " ");
            System.out.println();
        }

        System.out.println();
    }

    @Override
    public Iterator iterator() {
        return null;
    }

    @Override
    public void forEach(Consumer action) {

    }

    @Override
    public Spliterator spliterator() {
        return null;
    }
}
