package com.company.sum;

public class SumRequest {

    public static void doRequest() {

        float[] arr = new float[] { 2, 7, 14, 15 };
        float target = 9;

        int[] indexes = Sum.solution(arr, target);

        System.out.println("[ " + indexes[0] + ", " + indexes[1] + " ]");

        System.out.println();

        arr = new float[] { 5, 7, 2, 22, 14, 15, -3, 28, 11 };
        target = 25;

        indexes = Sum.solution(arr, target);

        System.out.println("[ " + indexes[0] + ", " + indexes[1] + " ]");

        System.out.println();
    }
}
