package com.company.matrix;

public class IntegerAddition implements Addition<Integer>
{
    public IntegerAddition(){}

    public Integer add(Integer value1, Integer value2)
    {
        return value1 + value2;
    }
}
