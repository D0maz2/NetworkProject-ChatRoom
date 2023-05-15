package mainClasses;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class clientHandler extends Thread{
    private Socket clientSocket;            //socket to communicate with client
    public static ArrayList<clientHandler> clients = new ArrayList<>();     //list of users
    private String name;                           //Each handler will have the same name as the client connected to it
    DataInputStream inputStream = new DataInputStream(System.in);
    DataInputStream inputStream2 = null;
    DataOutputStream outputStream2 = null;

    //Constructor where the client socket is created and is added to the list also the inputStream receives the name and sets it for teh client socket
    public clientHandler(Socket socket) {
        this.clientSocket = socket;
        clients.add(this);
        try {
            inputStream = new DataInputStream(clientSocket.getInputStream());
            this.name=inputStream.readUTF();
        } catch (IOException e) {
            //Exception not here
            System.out.println(e);
        }

        broadcast("[Server] "+name+" has joined the lobby.");   //Call for broadcast method to show other clients that a certain user has joined
    }

    public void run() {
        try {
            //outputStream = new DataOutputStream(clientSocket.getOutputStream());
            outputStream2 = new DataOutputStream(clientSocket.getOutputStream());
            inputStream2 = new DataInputStream(System.in);

            //Start the texting
            String msg = "";
            while(!msg.equals("!q")){
                try{
                    listenerThread();       //method to check for any messages sent to the client handler
                    msg = inputStream2.readLine();
                    outputStream2.writeUTF("[Server] "+msg);    //writing data to oout stream to be read by the clients
                    outputStream2.flush();      //to make sure all of teh stream is printed and it's empty
                }
                catch (IOException i){
                    System.out.println(i);
                }
            }
        }catch(IOException i){
            System.out.println(i);
        }
        //Closing the connection and showing other clients that the this client has been closed
        finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream2 != null) {
                    outputStream2.close();
                }
                if (clientSocket != null) {
                    clientSocket.close();
                }
                broadcast(name+" has left the lobby.");
            } catch (IOException i) {
                System.out.println(i);
            }
        }
    }

    //method to send sgs to anyone who isn't directly connected to this clientHandler
    public void broadcast(String msg) {
        for (clientHandler client : clients) {
            try {
                if (!client.name.equals(name)) {
                    client.outputStream2.writeUTF(msg);
                    client.outputStream2.flush();

                }
            } catch (IOException i) {
                System.out.println(i);
                System.out.println("71");
            }
        }
    }
    //Method to wait for any incoming messages
    public void listenerThread(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (clientSocket.isConnected()){
                    try {
                        String msg = "";
                        msg = inputStream.readUTF();
                        broadcast(name+": "+msg);
                    } catch (IOException i){
                        System.out.println(i);
                    }
                }
            }
        }).start();
    }
}
