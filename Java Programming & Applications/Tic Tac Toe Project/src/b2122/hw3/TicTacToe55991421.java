/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package b2122.hw3;

/*Name: Abhimanyu Bhati
SID: 55991521*/

import b2122.hw2.TicTacToe;
import java.awt.event.*;
import javax.swing.*;
import static b2122.hw2.TicTacToe.Player.O;
import static b2122.hw2.TicTacToe.Player.X;
import java.awt.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.border.TitledBorder;

/**
 *
 * @author abhati4
 */
public class TicTacToe55991421 extends JFrame implements TicTacToe {

    private JButton[][] board; //tic tac toe board on which user plays (NxN)
    private Player player, winner; //to track the current player and winner
    private JButton enter, reset;
    private JTextField sizeField;
    private JPanel top, middle, bottom, scorePanel, resultPanel;
    private JLabel header, ticker, xScore, oScore, drawScore, winLabel, drawLabel, progressLabel;
    private int xCount = 0, oCount = 0, drawCount = 0, totalCount = 0;

    public TicTacToe55991421() {
        /*Initialising the Frame Components*/

        player = X;
        setLayout(new BorderLayout(30, 20));
        sizeField = new JTextField(10);

        /*Panels*/
        top = new JPanel(new FlowLayout());
        middle = new JPanel(new GridLayout());
        bottom = new JPanel(new FlowLayout());
        scorePanel = new JPanel(new GridLayout(4, 1));
        resultPanel = new JPanel();


        /*Buttons*/
        reset = new JButton("Reset");
        enter = new JButton("Enter");

        /*Labels*/
        ticker = new JLabel("Welcome to Abhimanyu's Tic-Tac-Toe");
        ticker.setFont(new Font("Arial", Font.BOLD, 16));
        header = new JLabel("Enter size to start:");
        header.setFont(new Font("Arial", Font.BOLD, 16));
        xScore = new JLabel("X: ");
        xScore.setFont(new Font("Arial", Font.BOLD, 14));
        oScore = new JLabel("O: ");
        oScore.setFont(new Font("Arial", Font.BOLD, 14));
        drawScore = new JLabel("Draw: ");
        drawScore.setFont(new Font("Arial", Font.BOLD, 14));
        winLabel = new JLabel();
        drawLabel = new JLabel();
        progressLabel = new JLabel();

        /*Condition to only let user input numbers*/
        sizeField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
                    e.consume();  // if it's not a number, ignore the event
                    playMusic("music//errormusic.wav");
                }
            }
        });
        sizeField.setVisible(true);

        ActionListener x = new MyActionListener();

        enter.addActionListener(x);
        reset.addActionListener(x);
        reset.setEnabled(false);

        top.setLayout(new FlowLayout());
        top.add(header);
        top.add(sizeField);
        top.add(enter);
        top.add(reset);

        bottom.setBorder(new TitledBorder("Output:"));
        bottom.add(ticker);
        bottom.setSize(100, 100);

        scorePanel.setBorder(new TitledBorder("Win %:"));
        scorePanel.add(xScore);
        scorePanel.add(oScore);
        scorePanel.add(drawScore);
        scorePanel.add(resultPanel);
        scorePanel.setVisible(false);

        resultPanel.setMinimumSize(new Dimension(60, 60));
        resultPanel.setVisible(true);
        winLabel.setSize(50, 50);
        drawLabel.setSize(50, 50);
        progressLabel.setSize(50, 50);

        /*Loading the Images onto the Labels and scaling them to fit the required size*/
        try {
            URL winIcon = new URL("https://cdn-icons-png.flaticon.com/512/445/445087.png");
            URL drawIcon = new URL("https://cdn1.iconfinder.com/data/icons/chess-bzzricon-color-omission/512/20_Draw-512.png");
            URL progressIcon = new URL("https://listimg.pinclipart.com/picdir/s/363-3630556_hourglass-with-flowing-sand-sand-clock-icon-png.png");
            scaleImage(winIcon, winLabel);
            scaleImage(drawIcon, drawLabel);
            scaleImage(progressIcon, progressLabel);
        } catch (MalformedURLException ex) {
            //Logger.getLogger(TicTacToe55991421.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }

        resultPanel.add(progressLabel);
        resultPanel.add(winLabel);
        resultPanel.add(drawLabel);

        add(top, BorderLayout.NORTH);
        add(bottom, BorderLayout.SOUTH);
        add(scorePanel, BorderLayout.WEST);
    }

    public static void main(String[] args) {
        /* frame Settings */
        TicTacToe55991421 myFrame = new TicTacToe55991421();
        myFrame.setTitle("Abhimanyu's Tic-Tac-Toe Game");
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setSize(550, 550);
        myFrame.setLocationRelativeTo(null);
        myFrame.setVisible(true);

    }

    /*Action Listener Class*/
    private class MyActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            /*Handling 'enter' button's event*/
            if (e.getSource() == enter) {
                if (sizeField.getText().equals("")) {
                    playMusic("music//errormusic.wav");
                    /*if the textfield on the frame is empty and user clicks enter, the user will be notified to enter a number greater than 3*/
                    ticker.setText("Enter a number greater than 3 in the Text Field to begin the game!");
                } else {
                    init(Integer.parseInt(sizeField.getText()));
                }
                /*Handling 'reset' button event */
            } else if (e.getSource() == reset) {

                sizeField.setText("");
                ticker.setText("Welcome to Abhimanyu's Tic-Tac-Toe!");

                middle.setVisible(false);
                reset.setEnabled(false);
                winLabel.setVisible(false);
                drawLabel.setVisible(false);
                enter.setEnabled(true);
                sizeField.setEnabled(true);
                progressLabel.setVisible(true);
                scorePanel.setVisible(true);

                validate();
            }

        }

    }

    /*Handling Board Grid Buttons Event*/
    private class ButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board.length; j++) {
                    if (e.getSource() == board[i][j] && hasNext() && (board[i][j].getText().equals("X") == false) && (board[i][j].getText().equals("O") == false)) {
                        /*if the move is valid, isnt marked previously and further moves can be played, then we mark the button with the player who clicked it*/
                        mark(Integer.parseInt(board[i][j].getText()));
                    } else {

                    }
                }
            }
            /*If no more moves can be played*/
            if (hasNext() == false) {
                sizeField.setEnabled(false);
                progressLabel.setVisible(false);
                for (int i = 0; i < board.length; i++) {
                    for (int j = 0; j < board.length; j++) {
                        board[i][j].setEnabled(false); //disabling buttons since no further move is possible
                    }
                }
                /*Case where winner is determined*/
                if (hasWinner() == true) {
                    ticker.setText(getWinner() + " is the Winner");
                    /*updating scores and displaying appropriate icon*/
                    if (getWinner() == X) {
                        /*Case where X is Winner*/
                        xScore.setText("X: " + String.format("%.2f", (double) (++xCount) * (100) / (++totalCount)) + "%");
                        oScore.setText("O: " + String.format("%.2f", (double) (oCount) * (100) / (totalCount)) + "%");
                        drawScore.setText("Draw: " + String.format("%.2f", (double) (drawCount) * (100) / (totalCount)) + "%");
                    } else {
                        /*Case where O is Winner*/
                        xScore.setText("X: " + String.format("%.2f", (double) (xCount) * (100) / (++totalCount)) + "%");
                        oScore.setText("O: " + String.format("%.2f", (double) (++oCount) * (100) / (totalCount)) + "%");
                        drawScore.setText("Draw: " + String.format("%.2f", (double) (drawCount) * (100) / (totalCount)) + "%");
                    }
                    winLabel.setVisible(true);
                    playMusic("music//winmusic.wav");

                } else if (hasWinner() == false) {
                    /*Case where it is a draw*/
                    ticker.setText("Draw Game!");
                    xScore.setText("X: " + String.format("%.2f", (double) (xCount) * (100) / (++totalCount)) + "%");
                    oScore.setText("O: " + String.format("%.2f", (double) (oCount) * (100) / (totalCount)) + "%");
                    drawScore.setText("Draw: " + String.format("%.2f", (double) (++drawCount) * (100) / (totalCount)) + "%");

                    drawLabel.setVisible(true);
                    playMusic("music//drawmusic.wav");
                }
                validate();
            }
        }

    }

    /*Initialises the button grid 'board[][]' with 'size' number of buttons and adds it to the JPanel 'middle'*/
    @Override
    public void init(int size) {
        if (size < 3) {
            ticker.setText("Enter a number greater than 3");
            playMusic("music//errormusic.wav");
            return;
        }
        enter.setEnabled(false);
        player = X;
        middle.removeAll();
        middle.setVisible(true);
        middle.setSize(new Dimension(300, 300));
        reset.setEnabled(true);
        ticker.setText("A " + size + "x" + size + " game has started. Player X first");

        progressLabel.setVisible(true);
        winLabel.setVisible(false);
        drawLabel.setVisible(false);

        scorePanel.setVisible(true);
        middle.setLayout(new GridLayout(size, size));

        ActionListener buttonListener = new ButtonListener();
        board = new JButton[size][size]; //initialising the tic-tac-toe board to the dimension mentioned

        int count = 1;

        //loop to initialise the buttons of the board with their position numbers
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = new JButton(Integer.toString(count));
                board[i][j].setFont(new Font("Arial", Font.BOLD, 18));
                board[i][j].addActionListener(buttonListener); //Attaching a listener to each button
                board[i][j].setEnabled(true);
                middle.add(board[i][j]);
                count++;
            }
        }

        add(middle, BorderLayout.CENTER);
        validate();
    }

    /*Method to Play music takes musicLocation as parameter (which is the music file location in the directory) as a string*/
    public static void playMusic(String musicLocation) {

        try {
            File musicPath = new File(musicLocation);

            if (musicPath.exists()) {
                AudioInputStream audio = AudioSystem.getAudioInputStream(musicPath);
                Clip clip = AudioSystem.getClip();
                clip.open(audio);
                clip.start();
            } else {
                System.out.println("File not Found");
            }

        } catch (Exception ex) {
            System.out.println("Error in playing music");
            ex.printStackTrace();
        }
    }

    /*Method to scale Image to required size. Takes in URL and JLabel object on which the image is to be drawn as parameters*/
    private void scaleImage(URL location, JLabel label) {
        ImageIcon icon = new ImageIcon(location);
        Image img = icon.getImage();
        Image imgScale = img.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(imgScale);
        label.setIcon(scaledIcon);
    }

    @Override
    /*Method to determine if any further move is possible on the board*/
    public boolean hasNext() {
        if (hasWinner() == true) {
            return false;//if the game has a winner already then the next turn is redundant hence returning false
        }

        //loop checks if at least one button is left unmarked for the game to continue
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j].getText().equals("X") == false && board[i][j].getText().equals("O") == false) { // here -1 corresponds to "X" and 0 corresponds to "O"
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
    public void mark(int pos) {
        //Determining which player is playing
        String inp = "X"; // By default we set inp to X as player X starts the game by default. 

        if (getTurn() == O) { //Change inp to O if its O's turn
            inp = "O";
        }

        //boolean flag to determine if a given button is still unmarked in the button grid
        boolean isPresent = false;

        //loop to search for clicked button and replace it with player symbol
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j].getText().equals(Integer.toString(pos))) {
                    board[i][j].setText(inp);
                    if (inp.equals("X")) {
                        board[i][j].setBackground(Color.yellow);
                        playMusic("music//xbuttonmusic.wav");
                    } else if (inp.equals("O")) {
                        board[i][j].setBackground(Color.orange);
                        playMusic("music//obuttonmusic.wav");
                    }
                    isPresent = true;
                }
            }
        }

        //Changing turn
        if (getTurn()
                == X) {
            player = O;
        } else {
            player = X;
        }

        ticker.setText(
                "Player " + getTurn() + "'s turn.");
        validate();

    }

    @Override
    public void print() {
        /*Commented the print() funtion as it is not being used in this assignment code*/

 /* int temp = board.length * board.length; //temp variable used for spacing and aligning the output. It is the maximum numerical value of the positions in the board 
        int digits = 0;//stores the number of digits of the biggest possible element in the board (which is temp)

        while (temp != 0) { // to calculate number of digits for spacing
            temp = temp / 10;
            digits++;
        }

        String spacing = "%" + digits + "s"; // to dynamically determine the output formatting

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                switch (board[i][j].getText()) {
                    case "X": {

                        System.out.printf(spacing, "X");
                        break;
                    }
                    case "O": {
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
         */
    }

    @Override
    public boolean hasWinner() {

        /*Checking diagonal elements only as for a player to win, the diagonal element must be occupied by the player.
        and if occupied, we check if the corresponding row, column has been filled by the player as well*/
        for (int i = 0; i < board.length; i++) {
            if (board[i][i].getText().equals("X") == false && board[i][i].getText().equals("O") == false) {
                //skip to next iteration
            } else if (checkRow(i) || checkColumn(i)) {
                return true;
            }
        }

        /*checking if diagonal positions are occupied by a single player*/
        if (checkMajorDiagonal() || checkMinorDiagonal()) {
            return true;
        }
        return false;
    }

    @Override
    /*returns the winner of the game. Only called hasWinner() is true*/
    public Player getWinner() {
        if (getTurn() == X) {
            winner = O;
        } else {
            winner = X;
        }
        return winner;

    }


    /*This function checks if the corresponding board positions' row is filled by only one player*/
    public boolean checkRow(int pos) {
        String inp = "O";

        if (getTurn() == O) {
            inp = "X"; //Since 'mark' changes the turn of the player after marking the position
        }

        for (int i = 0; i < board.length; i++) {
            if (board[pos][i].getText().equals(inp)) {
                //skip to next iteration
            } else {
                return false;
            }
        }
        return true;
    }

    /*This function checks if the corresponding board positions' column is filled by only one player*/
    public boolean checkColumn(int pos) {
        String inp = "O";
        if (getTurn() == O) {
            inp = "X";
        }
        for (int i = 0; i < board.length; i++) {
            if (board[i][pos].getText().equals(inp)) {
                //skip to next iteration
            } else {
                return false;
            }
        }
        return true;
    }

    /*This function checks if the corresponding board positions' major diagonal is filled by only one player*/
    public boolean checkMajorDiagonal() {
        String inp = "O";
        //int temp = board.length - 1;
        if (getTurn() == O) {
            inp = "X";
        }
        for (int i = 0; i < board.length; i++) {
            if (board[i][i].getText().equals(inp)) {//major diagonal elements
                //skip to next iteration
            } else {
                return false;
            }
        }

        return true;
    }

    /*This function checks if the corresponding board positions' minor diagonal is filled by only one player*/
    public boolean checkMinorDiagonal() {
        String inp = "O";
        int temp = board.length - 1;
        if (getTurn() == O) {
            inp = "X";
        }
        for (int i = 0; i < board.length; i++) {
            if (board[i][temp - i].getText().equals(inp)) {//minor diagonal elements
                //skip iteration
            } else {
                return false;
            }
        }
        return true;
    }

}
