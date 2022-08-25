package b2122.hw2;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * EE3206 Assignment 2
 * 
 * Your are required to implement the game interface TicTacToe below. There are 
 * seven abstract methods. In addition to these public methods, you may define 
 * other private fields and methods for your own use.
 *
 * Your class name must be TicTacToeXXXXXXXX where the X-string is your student ID.
 *
 * Your class must be in the same package of this interface (i.e. b2122.hw2)
 *
 * Your class must provide a no-arg constructor.
 *
 * You can run the main method in this interface to test your implementation.
 */

/**
 * A simple strategy board game – Tic Tac Toe. The game has two players, X and O, and starts 
 * with X. They take turns marking the spaces in a grid (usually 3x3 but can be NxN in this 
 * assignment). The player who succeeds in placing N respective marks in a horizontal, vertical, 
 * or diagonal row wins the game.
 * 
 * @author vanting
 */

public interface TicTacToe {

    // The mark for two game players
    public enum Player {X, O}
    
    /**
     * This method is used to initialize the size of the grid. When it is called, the grid 
     * is re-initialized and all marks currently on the grid are erased. 
     * 
     * @param size                          the number of spaces in a row (e.g. 3 means 3x3)
     * @throws IllegalArgumentException     if the input size is less than 3
     */
    public void init(int size) throws IllegalArgumentException;
    
    /**
     * This method returns true if the game can continue. It returns false if a winner has
     * been determined or all grid positions have been marked.
     */

    public boolean hasNext();
    
    /**
     * This method returns the player of the current turn. 
     */
    public Player getTurn();

    /**
     * This method puts the current player mark in the specified position.
     * 
     * @param pos                           a number indicating a particular grid position
     * @throws IllegalArgumentException     if the input position cannot be marked
     */
    public void mark(int pos) throws IllegalArgumentException;

    /**
     * This method prints the grid to console (i.e. System.out). The grid spaces are either
     * printed with a player mark or a position number (in row-major order). 
     * 
     * For example, an initial look of a 3x3 grid with no player marks:
     * 1|2|3
     * 4|5|6
     * 7|8|9
     * 
     * Another example with 3 Xs and 2 Os.
     * X|2|O	
     * 4|O|6	
     * X|8|X
     */
    public void print();

    /**
     * This method returns true if the game has a winner. It returns false when the game
     * is still in progress or there is no winner.
     */
    public boolean hasWinner();
    
    /**
     * This method returns the winner of the game.
     * 
     * @return                              the winner of the game
     * @throws IllegalStateException        if the game is in progress or has no winner
     */
    public Player getWinner() throws IllegalStateException;
    
    //====================================================================================
    // DO NOT MODIFY THE METHODS BELOW
    //====================================================================================
    
    /**
     * Load your TicTacToe implementation class and create a new instance with 
     * your no-arg constructor.
     * 
     * @param sid   your student ID
     * @return      an instance of your TicTacToe implementation
     * 
     */
    public static TicTacToe newInstance(String sid) {
        
        TicTacToe ttt = null;
        try {
            Class clazz = Class.forName("b2122.hw2.TicTacToe" + sid);
            ttt = (TicTacToe) clazz.getDeclaredConstructor().newInstance();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TicTacToe.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return ttt;
        }
    }
    
    /**
     * Enter your 8-digit SID to start the game.
     */
    public static void main(String[] args) {
        
        Scanner sin = new Scanner(System.in);  
        
        System.out.print("Enter your SID: ");
        TicTacToe game = newInstance(sin.next());
        System.out.println("");
        
        System.out.print("Enter the size of the grid: ");
        game.init(sin.nextInt());
        System.out.println("");
        
        game.print();
        while(game.hasNext()) {
            while(true) {
                try {
                    System.out.print("Mark " + game.getTurn() + " at: ");
                    game.mark(sin.nextInt());
                    break;
                } catch(IllegalArgumentException e) {
                    System.out.println("Wrong position.");
                } catch(InputMismatchException e) {
                    System.out.println("Wrong input.");
                    sin.nextLine();
                }
            }
            
            System.out.println("");
            game.print();
        }
       
        if(game.hasWinner())
            System.out.println("The winner is " + game.getWinner() + " !!!");
        else
            System.out.println("DRAW game!");
        
    }

}


