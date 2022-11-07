package a2223.hw1.student;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Multi-threaded server program that multiplies matrices using fork-join
 * framework.
 *
 * @author vanting
 */
public class Server55991421 implements Runnable {

    public static final int DEFAULT_PORT = 42210;
    private Socket socket;

    public Server55991421() {
    }

    public Server55991421(Socket socket) {
        this.socket = socket;
    }

    /**
     * Driver function. Start this server at the default port.
     */
    public static void main(String[] args) throws IOException {
        start(DEFAULT_PORT);
    }

    /**
     * Start matrix server at the specified port. It should accept and handle
     * multiple client requests concurrently.
     *
     * @param port port number listened by the server
     */
    public static void start(int port) throws IOException {

        // your implementation here
        boolean listening = true;
        ServerSocket s = new ServerSocket(port);
        while (listening) {
            Socket s1 = s.accept();
            Server55991421 server = new Server55991421(s1);
            System.out.println("Hello");
            Thread thread = new Thread(server);
            thread.start();
//          ObjectInputStream in = new ObjectInputStream(s1.getInputStream());

            //ObjectInputStream in = new ObjectInputStream(s1.getInputStream());
//
//        while (listening) {
//            Socket s1 = s.accept();
//            Server55849809 server = new Server55849809(s1);
//            Thread thread = new Thread(server);
//            thread.start();
//        }
        }

        // 1. accept a new connection from client
        // 2. create a task with the socket
        // 3. submit the task to a thread pool to execute
//        ParallelMultiply task = new ParallelMultiply(s1);
    }

    /**
     * Handle a matrix client request. It reads two matrices from socket,
     * compute their product, and then send the product matrix back to the
     * client.
     */
    @Override
    public void run() {
        try {
            //logic of the task
            // your implementation here
            // 1. read two matrices from the socket
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            Object ReadA = in.readObject();
            Object ReadB = in.readObject();
            Matrix A = (Matrix) ReadA;
            Matrix B = (Matrix) ReadB;

            // 2. call multiThreadMultiply() to compute their product
            Matrix product = multiThreadMultiply(A, B);
//            System.out.println(product);

            // 3. send back the product matrix 
            out.writeObject(product);

        } catch (IOException ex) {
            Logger.getLogger(Server55991421.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Server55991421.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//    public void run() {
//        try {
//            //logic of the task
//
//            // your implementation here
//            // 1. read two matrices from the socket
//            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
//            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
//
//            Object ReadA = in.readObject();
//            Object ReadB = in.readObject();
//            Matrix A = (Matrix) ReadA;
//            Matrix B = (Matrix) ReadB;
//
//            // 2. call multiThreadMultiply() to compute their product
//            Matrix product = multiThreadMultiply(A, B);
//
//            // 3. send back the product matrix 
//            out.writeObject(product);
//
//        } catch (IOException ex) {
//            Logger.getLogger(Server55907774.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(Server55907774.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }

//    public void run() {
//        // your implementation here
//        Matrix m1 = null, m2 = null, product;
//
//        try {
//
//            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
//
//            Object ReadA = in.readObject();
//            Object ReadB = in.readObject();
//            Matrix A = (Matrix) ReadA;
//            Matrix B = (Matrix) ReadB;
//            PrintWriter out;
//            out = new PrintWriter(socket.getOutputStream(), true);
//
////            Object[] result = (Object[]) in.readObject();
////            for(Object obj : result){
////                m1 = (Matrix) obj;
////                
////            }
////            while (in.readObject() != null) {
//            m1 = (Matrix) in.readObject();
//            m2 = (Matrix) in.readObject();
////            }
//            product = multiThreadMultiply(m1, m2);
//            out.println(product);
//            
//        } catch (IOException ex) {
//            Logger.getLogger(Server55991421.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(Server55991421.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        // your implementation here
//        // 1. read two matrices from the socket
//        // 2. call multiThreadMultiply() to compute their product
//        // 3. send back the product matrix 
//    }
    /**
     * Compute A x B using fork-join framework.
     *
     * @param matA matrix A
     * @param matB matrix B
     * @return the matrix product of AxB
     */
    public static Matrix multiThreadMultiply(Matrix matA, Matrix matB) {

        Matrix product = null;

        // your implementation here
        // 1. create a fork-join task (parallelMultiply)
        // 2. submit the task to a fork-join pool
        ForkJoinPool pool = new ForkJoinPool();
        ParallelMultiply task;
        task = new ParallelMultiply(matA, matB);
        pool.invoke(task);
        product = task.CopyProduct();

        return product;

    }
}

/**
 * Design a recursive and resultless ForkJoinTask. It splits the matrix
 * multiplication into multiple tasks to be executed in parallel.
 *
 */
class ParallelMultiply extends RecursiveAction {

    private static int THRESHOLD = 1;
    Matrix product;
    Matrix m1, m2, newM;
    //no. of rows in m1 = 1 is base case
    // your implementation here

    ParallelMultiply(Matrix m1, Matrix m2) {
        this.m1 = m1;
        this.m2 = m2;
    }

    public Matrix CopyProduct() {
        return this.product;
    }

    @Override
    protected void compute() {

        if (m1.row() == THRESHOLD && m1.col() == m2.row()) {
            this.product = m1.multiply(m2);
        } else {
            List<ParallelMultiply> tasks = new ArrayList<>();

            for (int i = 0; i < m1.row(); i++) {

                long[][] elementsOfRow = new long[1][m1.col()];

                for (int j = 0; j < m1.col(); j++) {
                    elementsOfRow[0][j] = m1.at(i, j);
                }
                newM = new Matrix(elementsOfRow);
                tasks.add(new ParallelMultiply(newM, m2));

            }
            invokeAll(tasks);
//            for (ParallelMultiply task : tasks) {
//                task.fork();
//            }
//            for (ParallelMultiply task : tasks) {
//                task.join();
//            }
            long[][] result = new long[this.m1.row()][this.m2.col()];

            int row = 0;
            for (ParallelMultiply task : tasks) {
                for (int column = 0; column < task.product.col(); ++column) {
                    result[row][column] = task.product.at(0, column);
                }
                row++;
            }

            this.product = new Matrix(result);
        }

    }
}

//
//class ParallelMultiply extends RecursiveAction {
//
//    Matrix A;
//    Matrix B;
//    Matrix Product;
//
//    public ParallelMultiply(Matrix A, Matrix B) {
//
//        this.A = A;
//        this.B = B;
////        this.Product = Product;
//       
//    }
//
//    // your implementation here
//    @Override
//    protected void compute() {
//
//        if (A.row() == 1 || A.col() == B.row()) {
//
//            this.Product = A.multiply(B);
//
//        } else {
//
//            List<ParallelMultiply> list = new ArrayList<>();
//
//            for (int i = 0; i < A.row(); ++i) {
//                long[][] n = null;
//                for (int j = 0; j < A.col(); ++j) {
//                    n[0][j] = A.at(i, j);
//                }
//                Matrix newRow = new Matrix(n);
//                list.add(new ParallelMultiply(newRow, B, null));
//            }
//
//            invokeAll(list);
//            long[][] result = new long[this.A.row()][this.B.col()];
//
//
//            int row = 0;
//            for (ParallelMultiply task : list) {
//                for (int column = 0; column < task.Product.col(); ++column) {
//                    result[row][column] = task.Product.at(0, column);
//                }
//                row++;
//            }
//            
//            this.Product = new Matrix(result);
//        }
//
//    }
//
//}
//    private static int THRESHOLD = 1;
//
//    //no. of rows in m1 = 1 is base case
//    // your implementation here
//    ParallelMultiply(Matrix MatA, Matrix matB) {
//
//    }
//
//    Matrix m1, m2;
//    //Matrix[] smallM;
//
//    @Override
//    protected void compute() {
//        if (m1.row() == THRESHOLD) {
//
//        } else {
//            int numRow = m1.row();
//            for (int i = 0; i < m1.row(); i++) {
//                for (int j = 0; j < m1.col(); j++) {
//                    m1 = new Matrix(i, j);
//                }
//            }
//
//        }
//    }

