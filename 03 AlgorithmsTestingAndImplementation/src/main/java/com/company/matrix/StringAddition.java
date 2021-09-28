package com.company.matrix;

public class StringAddition implements Addition<String> {
    public String add(String value1, String value2) {
        // use auto unboxing and auto boxing
        return String.valueOf(Integer.parseInt(value1) + Integer.parseInt(value2));
    }
}
