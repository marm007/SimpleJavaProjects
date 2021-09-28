package com.company.substring;

public class Substring {

    public static int substring(String a, String b) {
        int numberOfTimes = -1;
        String copyA = a;

        while (copyA.length() <= 2 * b.length()) {

            numberOfTimes++;
            copyA += a;

            if (copyA.matches("(.*)" + b + "(.*)"))
                break;

            System.out.println(copyA);
        }

        if (numberOfTimes != -1)
            numberOfTimes += 2;

        return numberOfTimes;
    }
}
