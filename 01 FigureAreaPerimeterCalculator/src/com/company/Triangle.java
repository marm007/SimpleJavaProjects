package com.company;

import java.security.InvalidParameterException;

public class Triangle extends Figure implements Print {

    private Double a, b, c, p, h;


    public Triangle(double a, double b, double c) throws InvalidParameterException {

            if(c < a + b && a < b + c && b < c + a) {
                this.a = a;
                this.b = b;
                this.c = c;
            }else{
                throw new InvalidParameterException("Triangle existence condition not fulfilled!");
            }

    }

    public Triangle(double p, double h) throws InvalidParameterException {
        this.p = p;
        this.h = h;
    }

    @Override
    public double calculateArea() {
        double area;

        if(p != null){
            area = p * h / 2;
        }else{
            double pp = calculatePerimeter() / 2;
            area = Math.sqrt(pp*(pp - a)*(pp - b)*(pp - c));
        }
        return area;
    }

    @Override
    public double calculatePerimeter() {
        return a + b + c;
    }

    @Override
    public void print() {

        System.out.print("This is triangle... ");

        if(p == null)
            System.out.println("a = " + this.a + " b = " + this.b + " c = " + this.c + " area = " + calculateArea() + " perimeter = " + calculatePerimeter() + "\n");
        else
            System.out.println("p = " + this.p + " h = " + this.h + " area = " + calculateArea() + "\n");

    }
}
