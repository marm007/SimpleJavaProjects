package com.company.sum;

import org.junit.Assert;
import org.junit.Test;

public class SumTest {

    @Test
    public void solution_ZeroOneIndexes() {
        float[] arr = new float[] { 2, 7, 14, 15 };
        float target = 9;

        int[] indexes = Sum.solution(arr, target);

        Assert.assertEquals(0, indexes[0]);
        Assert.assertEquals(1, indexes[1]);
    }

    @Test
    public void solution_OnlyPositiveValuesAndGiveNegative_MinusOneIndexes() {
        float[] arr = new float[] { 22, 1, 12, 33, 4 };
        float target = 9;

        int[] indexes = Sum.solution(arr, target);

        Assert.assertEquals(-1, indexes[0]);
        Assert.assertEquals(-1, indexes[1]);
    }

    @Test
    public void solution_OnlyNegativeValuesAndGivePositive_MinusOneIndexes() {
        float[] arr = new float[] { -1, -2, -33, -3, -10 };
        float target = 9;

        int[] indexes = Sum.solution(arr, target);

        Assert.assertEquals(-1, indexes[0]);
        Assert.assertEquals(-1, indexes[1]);
    }
}