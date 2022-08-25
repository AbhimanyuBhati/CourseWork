/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/*
Name: Abhimanyu Bhati
SID:55991421
*/

package b2122.hw2;

import b2122.hw2.TicTacToe.Player;
import static b2122.hw2.TicTacToe.Player.O;
import static b2122.hw2.TicTacToe.Player.X;

/**
 *
 * @author abhat
 */
public class TicTacToe55991421 implements TicTacToe {

    int[][] board; //tic tac toe board on which user plays (NxN)
    Player player, winner; //to track the current player and winner


    TicTacToe55991421() {
        player = X; //First turn is given to X by default
    }

    @Override
    public void init(int size) throws IllegalArgumentException {

        if (size < 3) {
            throw new IllegalArgumentException();
        }

        board = new int[size][size]; //initialising the tic-tac-toe board to the dimension mentioned
        int count = 1;
        //loop to initialise the elements of the board with their position numbers
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = count;
                count++;
            }
        }
    }

    @Override
    public boolean hasNext() {
        if (hasWinner() == true) {
            return false;//if the game has a winner already then the next turn is redundant hence returning false
        }

        int count = 0;

//loop checks if at least one element is left unmarked for the game to continue
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] != -1 && board[i][j] != 0) { // here -1 corresponds to "X" and 0 corresponds to "O"
                    return true;
                }
            }
        }
        return false; //if all positions have been accessed, there are no more possible turns, hence return false
    }

    @Override
    public Player getTurn() {
        return player; //informs us which player's turn it is presently
    }

    @Override
    public void mark(int pos) throws IllegalArgumentException { 
//Determining which player is playing
        if (pos <= 0 || pos > (board.length * board.length)) {//checking whether entered position is within the board
            throw new IllegalArgumentException();
        }

        int inp = -1; // By default we set inp to -1 as it is X which starts the game by default. Again -1 corresponds to "X"

        if (getTurn() == O) { //Change inp to 0 if its O's turn
            inp = 0;
        }

//boolean flag to determine if a given position is still unmarked in the grid
        boolean isPresent = false;

//loop to search for element and replace it with player symbol
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == pos) {
                    board[i][j] = inp;
                    isPresent = true;
                }
            }
        }
        if (isPresent == false) { //if position has already been marked previously and any of the the user tries to mark it again, we throw exception
            throw new IllegalArgumentException();
        }

//Changing turn
        if (getTurn() == X) {
            player = O;
        } else {
            player = X;
        }

    }

    @Override
    public void print() {
        int temp = board.length * board.length; //temp variable used for spacing and aligning the output. It is the maximum numerical value of the positions in the board 
        int digits = 0;//stores the number of digits of the biggest possible element in the board (which is temp)

        while (temp != 0) { // to calculate number of digits for spacing
            temp = temp / 10;
            digits++;
        }

        String spacing = "%" + digits + "s"; // to dynamically determine the output formatting

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                switch (board[i][j]) {
                    case -1: {

                        System.out.printf(spacing, "X");
                        break;
                    }
                    case 0: {
                        System.out.printf(spacing, "O");
                        break;
                    }
                    default: {
                        System.out.printf(spacing, String.valueOf(board[i][j]));
                        break;
                    }
                }
                if (j < board.length - 1) {
                    System.out.print("|");
                }
            }
            System.out.println("");
        }
    }

    @Override
    public boolean hasWinner() {
        /*Checking diagonal elements only as for a player to win, the diagonal element must be occupied by the player.
and if occupied, we check if the corresponding row, column has been filled by the player as well*/
        for (int i = 0; i < board.length; i++) {
            if (board[i][i] != -1 && board[i][i] != 0) {
                //skip to next iteration
            } else if (checkRow(i) || checkColumn(i)) {
                return true;
            }
        }
//checking if diagonal positions are occupied by a single player
        if (checkMajorDiagonal() || checkMinorDiagonal()) {
            return true;
        }
        return false;
    }

    @Override
    public Player getWinner() throws IllegalStateException {
        if (hasWinner() == false) {
            throw new IllegalStateException();
        } else if (getTurn() == X) {
            winner = O;
        } else {
            winner = X;
        }
        return winner;

    }

    /*This function checks if the corresponding board positions' row is filled by only one player*/
    public boolean checkRow(int pos) {
        int inp = 0;

        if (getTurn() == O) {
            inp = -1; //Since 'mark' changes the turn of the player after marking the position
        }

        for (int i = 0; i < board.length; i++) {
            if (board[pos][i] == inp) {
                //skip to next iteration
            } else {
                return false;
            }
        }
        return true;
    }

    /*This function checks if the corresponding board positions' column is filled by only one player*/
    public boolean checkColumn(int pos) {
        int inp = 0;
        if (getTurn() == O) {
            inp = -1;
        }
        for (int i = 0; i < board.length; i++) {
            if (board[i][pos] == inp) {
                //skip to next iteration
            } else {
                return false;
            }
        }
        return true;
    }

    /*This function checks if the corresponding board positions' major diagonal is filled by only one player*/
    public boolean checkMajorDiagonal() {
        int inp = 0;
        int temp = board.length - 1;
        if (getTurn() == O) {
            inp = -1;
        }
        for (int i = 0; i < board.length; i++) {
            if (board[i][i] == inp) {//major diagonal elements
                //skip to next iteration
            } else {
                return false;
            }
        }

        return true;
    }

    /*This function checks if the corresponding board positions' minor diagonal is filled by only one player*/
    public boolean checkMinorDiagonal() {
        int inp = 0;
        int temp = board.length - 1;
        if (getTurn() == O) {
            inp = -1;
        }
        for (int i = 0; i < board.length; i++) {
            if (board[i][temp - i] == inp) {//minor diagonal elements
                //skip iteration
            } else {
                return false;
            }
        }
        return true;
    }

}
