package com.company.sum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Sum {

    public static int[] solution(float[] arr, float target) {

        List<Float> copy = new ArrayList<>();

        for (float f : arr)
            copy.add(f);

        Arrays.sort(arr);

        int i = -1;
        int j = -1;

        boolean flagSuccess = false;

        for (float n : arr) {

            if (n > target)
                break;

            for (float m : arr) {

                if (n + m > target) {
                    break;
                }

                if (n + m == target) {
                    i = copy.indexOf(n);
                    j = copy.indexOf(m);
                    flagSuccess = true;
                    break;
                }
            }

            if (flagSuccess)
                break;

        }

        return new int[] { i, j };
    }
}
