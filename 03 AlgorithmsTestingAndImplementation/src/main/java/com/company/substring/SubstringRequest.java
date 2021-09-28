package com.company.substring;

public class SubstringRequest {

    public static void doRequest() {
        String a = "abcd";
        String b = "cdabcdab";

        System.out.println("How many times repat a to fit b? " + Substring.substring(a, b));

        a = "ab";
        b = "bababababababa";

        System.out.println("How many times repat a to fit b? " + Substring.substring(a, b));

        System.out.println();
    }
}
