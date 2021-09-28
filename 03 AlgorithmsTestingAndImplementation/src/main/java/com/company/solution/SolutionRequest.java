package com.company.solution;

import java.util.LinkedList;
import java.util.List;

public class SolutionRequest {

    public static void doRequest() {

        List<Integer> a = new LinkedList<>();

        a.add(1);
        a.add(3);
        a.add(6);
        a.add(4);
        a.add(1);
        a.add(2);

        Solution.solution(a);

        List<Integer> b = new LinkedList<>();

        b.add(1);
        b.add(2);
        b.add(3);

        Solution.solution(b);

        List<Integer> c = new LinkedList<>();

        c.add(-1);
        c.add(-3);

        Solution.solution(c);
    }
}
