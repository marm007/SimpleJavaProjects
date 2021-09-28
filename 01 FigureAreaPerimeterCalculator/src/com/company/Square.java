package com.company;

public class Square extends Figure implements Print {

    private double a;

    public Square(double a) {
        this.a = a;
    }

    @Override
    public double calculateArea() {
        return Math.pow(this.a, 2);
    }

    @Override
    public double calculatePerimeter() {
        return this.a*4;
    }

    @Override
    public void print() {
        System.out.println("This is square... a = " + this.a + " area = " + calculateArea() + " perimeter = " + calculatePerimeter() + "\n");
    }
}
