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
Scanner reader = new Scanner(System.in);    
    NewClass obj = new NewClass();
        System.out.println("Enter number of throws");
        int numThrows = reader.nextInt();
        double PI=((obj.Ans(numThrows)/numThrows)*4);
System.out.println(PI);

    }

    double Ans(int numThrows) {
        int hits = 0;
        for (int i = 0; i < numThrows; i++) {
            double xpos = Math.random();
            double ypos = Math.random();

            if (Math.sqrt((xpos * xpos) + (ypos * ypos)) < 1) {
                System.out.println(hits);
                hits++;
            }
        }
        return hits;
    }

}
