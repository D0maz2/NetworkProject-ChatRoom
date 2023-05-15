package mainClasses;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    ServerSocket serverSocket1 = null;
    Socket socket1 = null;
    DataInputStream inputStream = null;
    DataOutputStream outputStream = null;
    //DataOutputStream outputStream = null;

    public Server(int port) {
        try {
            //Mainly starting the connection(communication)
            serverSocket1 = new ServerSocket(port);                        //Listen step for any input in TCP port
            System.out.println("Server Started");
            //Check for every accepted socket and create a client handler with a client socket or it.
            while (true) {
                Socket clientSocket = serverSocket1.accept();
                // Create a new thread to handle the client connection
                clientHandler clientHandler = new clientHandler(clientSocket);
                clientHandler.start();
            }
        }
        catch (IOException i){
            System.out.println(i);
            System.out.println("line 31");
        }
    }

    public static void main(String[] args) {
        Server server = new Server(3333);
    }
}