package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int choose = -1;

        while(true)
        {
           System.out.println("Choose a figure:\n");

           System.out.println("1 -> Triangle");
           System.out.println("2 -> Square");
           System.out.println("3 -> Circle\n");

           choose = scanner.nextInt();

           if(choose < 1 || choose > 3){
               System.out.println("Choice error. Choose a number between 1 and 3!!!\n");
           }else{
               Figure figure = InsertData.insertData(choose);

               System.out.print("Exit? (Y/N) ");

               scanner.nextLine();

               String exitProgram = scanner.nextLine();

               if(exitProgram.toUpperCase().equals("Y"))
                   break;

               System.out.println();
           }
        }

        scanner.close();
    }
}
