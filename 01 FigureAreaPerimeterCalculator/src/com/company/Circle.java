package com.company;

public class Circle extends Figure implements Print {

    double r;

    public Circle(double r) {
        this.r = r;
    }

    @Override
    public double calculateArea() {
        return Math.PI * Math.pow(this.r, 2);
    }

    @Override
    public double calculatePerimeter() {
        return 2 * Math.PI * r;
    }

    @Override
    public void print() {
        System.out.println("This is circle... r = " + this.r + " area = " + calculateArea() + " perimeter = " + calculatePerimeter() + "\n");
    }
}
