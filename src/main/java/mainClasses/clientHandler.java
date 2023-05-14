package mainClasses;

import java.io.*;
import java.net.Socket;

public class clientHandler extends Thread{
    private Socket clientSocket;

    public clientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    public void run() {
        try {
            DataOutputStream outputStream = new DataOutputStream(clientSocket.getOutputStream());
            DataInputStream inputStream = new DataInputStream(clientSocket.getInputStream());

            String msg = "";
            while(!msg.equals("!q")){
                try{
                    msg = inputStream.readUTF();
                    System.out.println(msg);
                }
                catch (IOException i){
                    System.out.println(i);
                }
            }

            inputStream.close();
            outputStream.close();
            clientSocket.close();
        } catch (IOException e) {
            System.err.println("Error handling client connection: " + e.getMessage());
        }
    }
}
