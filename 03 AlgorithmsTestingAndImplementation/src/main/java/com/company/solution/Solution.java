package com.company.solution;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Solution {

    public static void solution(List<Integer> a) {
        if (a != null && a.size() != 0 && a.size() <= 1 * Math.pow(10, 15)) {

            System.out.print("[");
            for (int i = 0; i < a.size(); i++) {
                if (i != a.size() - 1)
                    System.out.print(a.get(i) + ", ");
                else
                    System.out.print(a.get(i));
            }
            System.out.print("]");

            a.sort(Integer::compare);

            a = a.stream().filter(o -> o > 0).collect(Collectors.toList());

            int result = 1;

            if (a.size() > 0) {
                int previous = a.get(0);

                for (int i = 1; i < a.size(); i++) {
                    if (a.get(i) - previous > 1) {
                        result = previous + 1;
                        break;
                    }

                    previous = a.get(i);
                }

                if (previous == a.get(a.size() - 1))
                    result = a.get(a.size() - 1) + 1;
            }

            System.out.println(" solution = " + result);
            System.out.println();

        } else
            System.err.println("Lista jest pusta lub niezainicjalizowana lub ma wiecej niz 15E elementow!!!");

    }
}
