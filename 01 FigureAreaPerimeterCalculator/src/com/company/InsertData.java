package com.company;

import java.security.InvalidParameterException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class InsertData {

    public static Figure insertData(int choose) {

        Scanner scanner = new Scanner(System.in);

        Figure figure = null;

        try {

            System.out.println("\nEnter data:\n");

            switch (choose) {
                case 1:
                    System.out.print("Enter all sides of the triangle? (Y/N) ");

                    String allSides = scanner.nextLine();
                    System.out.println();

                    if (allSides.toUpperCase().equals("Y")) {
                        System.out.print("Enter a: ");
                        double a = scanner.nextDouble();
                        System.out.print("Enter b: ");
                        double b = scanner.nextDouble();
                        System.out.print("Enter c: ");
                        double c = scanner.nextDouble();
                        System.out.println();
                        figure = new Triangle(a, b, c);

                        ((Triangle) figure).print();

                    } else {
                        System.out.print("Enter p: ");
                        double p = scanner.nextDouble();
                        System.out.print("Enter h: ");
                        double h = scanner.nextDouble();
                        System.out.println();

                        figure = new Triangle(p, h);
                        ((Triangle) figure).print();

                    }
                    break;

                case 2:
                    System.out.print("Enter a: ");
                    double a = scanner.nextDouble();
                    System.out.println();

                    figure = new Square(a);
                    ((Square) figure).print();

                    break;

                case 3:
                    System.out.print("Enter r: ");
                    double r = scanner.nextDouble();
                    System.out.println();

                    figure = new Circle(r);
                    ((Circle) figure).print();
                    break;
            }
        } catch (InputMismatchException | InvalidParameterException e) {
            System.out.println(e + "\n");
        }
        
        scanner.close();

        return figure;
    }
}
