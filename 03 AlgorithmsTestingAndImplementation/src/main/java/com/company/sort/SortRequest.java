package com.company.sort;

import java.util.Random;

public class SortRequest {

    public static void doRequest() {

        Random random = new Random();
        int numberOfElements = 100000;

        int[] tab = new int[numberOfElements];

        for (int i = 0; i < numberOfElements; i++) {
            tab[i] = random.nextInt(1000000) - 1000000 / 2;
        }

        int[] tmpTab = tab.clone();

        long tStart = System.currentTimeMillis();

        Sort.bubbleSort(tmpTab);

        long tEnd = System.currentTimeMillis();
        long tDelta = tEnd - tStart;
        double elapsedSeconds = tDelta / 1000.0;

        System.out.println("Buble sort time " + elapsedSeconds);

        tmpTab = tab.clone();

        tStart = System.currentTimeMillis();

        Sort.selectSort(tmpTab);

        tEnd = System.currentTimeMillis();
        tDelta = tEnd - tStart;
        elapsedSeconds = tDelta / 1000.0;

        System.out.println("Select sort time " + elapsedSeconds);

        tmpTab = tab.clone();

        tStart = System.currentTimeMillis();

        Sort.quickSort(tmpTab, 0, tmpTab.length - 1);

        tEnd = System.currentTimeMillis();
        tDelta = tEnd - tStart;
        elapsedSeconds = tDelta / 1000.0;

        System.out.println("Quick sort time " + elapsedSeconds);

        tmpTab = tab.clone();

        tStart = System.currentTimeMillis();

        Sort.insertSort(tmpTab);

        tEnd = System.currentTimeMillis();
        tDelta = tEnd - tStart;
        elapsedSeconds = tDelta / 1000.0;

        System.out.println("Insert sort time " + elapsedSeconds);

        tmpTab = tab.clone();

        tStart = System.currentTimeMillis();

        Sort.mergeSort(tmpTab, 0, tmpTab.length - 1);

        tEnd = System.currentTimeMillis();
        tDelta = tEnd - tStart;
        elapsedSeconds = tDelta / 1000.0;

        System.out.println("Merge sort time " + elapsedSeconds);

    }
}
