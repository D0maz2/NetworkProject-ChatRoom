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
    //DataOutputStream outputStream = null;

    public Server(int port) {
        try{
            //Mainly starting the connection(communication)
            serverSocket1 = new ServerSocket(port);                        //Listen step for any input in TCP port 3333
            System.out.println("Server Started");
            socket1 = serverSocket1.accept();                                         //Accept the socket with the correct port
            System.out.println("Client Accepted!");
            inputStream = new DataInputStream(new BufferedInputStream(socket1.getInputStream()));     //take input stream from client (not sure!)
            //outputStream = new DataOutputStream(socket1.getOutputStream()); //send output stream from server (not sure!)

            //Here is where the communication happens anything you want for teh client and the server to interact together for

            String msg = "";
            while(!msg.equals("!q")){
                try {
                    msg = inputStream.readUTF();
                    System.out.println(msg);
                }
                catch (IOException i){
                    System.out.println(i);
                }
            }

            //Close everything after finishing all Communication(interactions) between client and server
            System.out.println("Closing connection");
            inputStream.close();
            //outputStream.close();
            socket1.close();
            serverSocket1.close();
        }
        catch(IOException i){
            System.out.println(i);
        }

    }

    public static void main(String[] args) {
        Server server = new Server(3333);
    }
}
