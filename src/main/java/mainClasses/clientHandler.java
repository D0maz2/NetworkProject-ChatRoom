package mainClasses;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class clientHandler extends Thread{
    private Socket clientSocket;
    public static ArrayList<clientHandler> clients = new ArrayList<>();
    private String name;
    DataInputStream inputStream = null;
    //DataOutputStream outputStream = null;
    DataInputStream inputStream2 = null;
    DataOutputStream outputStream2 = null;

    public clientHandler(Socket socket) {
        this.clientSocket = socket;
        clients.add(this);
        try {
            this.name=inputStream.readUTF();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        broadcast("[Server] "+name+" has joined the lobby.");
    }

    public void run() {
        try {
            //outputStream = new DataOutputStream(clientSocket.getOutputStream());
            inputStream = new DataInputStream(clientSocket.getInputStream());
            outputStream2 = new DataOutputStream(clientSocket.getOutputStream());
            inputStream2 = new DataInputStream(System.in);

            String msg = "";
            while(!msg.equals("!q")){
                try{
//                    msg = inputStream.readUTF();
//                    System.out.println(msg);
                    listenerThread();
                    msg = inputStream2.readLine();
                    outputStream2.writeUTF("[Server] "+msg);
                    outputStream2.flush();
                }
                catch (IOException i){
                    System.out.println(i);
                }
            }
            broadcast(name+" has left the lobby.");
            inputStream.close();
            inputStream2.close();
            outputStream2.close();
            clientSocket.close();
        } catch (IOException e) {
            System.err.println("Error handling client connection: " + e.getMessage());
        }
    }

    public void broadcast(String msg) {
        for (clientHandler clients : clients) {
            try {
                if (!clients.name.equals(name)) {
                    clients.outputStream2.writeUTF(msg);
                    clients.outputStream2.flush();

                }
            } catch (IOException i) {
                System.out.println(i);
            }
        }
    }
    public void listenerThread(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (clientSocket.isConnected()){
                    try {
                        String msg = "";
                        msg = inputStream.readUTF();
                        System.out.println(name+" "+msg);
                    } catch (IOException i){
                        System.out.println(i);
                    }
                }
            }
        }).start();
    }
}
