/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author abhat
 */
import java.util.Random;
import java.util.Scanner;

public class NewClass {

    public static void main(String[] args) {

        //Calling object of scanner class to read inputs
        Scanner reader = new Scanner(System.in);

        //Calling a constructor of the class to get access to other functions
        NewClass obj = new NewClass();

        //Reading Input from User
        System.out.println("Enter number of throws");
        int numThrows = reader.nextInt();

        //Approximating Value of PI 
        double PI = ((obj.Ans(numThrows) / numThrows) * 4);
        System.out.println(PI);
    }

    //'Method()' to calculate the number of points in the circle
    double Ans(int numThrows) {
        int hits = 0;
        for (int i = 0; i < numThrows; i++) {

            //Assigning Random points on the first quarter of the square-circle diagram
            double xpos = Math.random();
            double ypos = Math.random();

            //If the point is within the circle, we increment 'hits'
            if (Math.sqrt((xpos * xpos) + (ypos * ypos)) < 1) {
                System.out.println(hits);
                hits++;
            }
        }
        return hits;
    }

}
