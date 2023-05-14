package mainClasses;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    DataInputStream inputStream = null;
    DataInputStream inputStream2 = null;
    DataOutputStream outputStream = null;
    Socket socket1 = null;
    public Client(String host,int port) {
        try {
            socket1 = new Socket(host, port);           //you should type the ip of the server in this case it's on the same machine so I can type a string named "localhost" which shall have the same results & the port written here is the one written in the server class and is the one to be accessed by this client
            if(socket1.isConnected()) {
                System.out.println("Connection successful");
            }
            inputStream = new DataInputStream(System.in);     //take input stream from client (not sure!)
            outputStream = new DataOutputStream(socket1.getOutputStream()); //send output stream from server (not sure!)
            inputStream2 = new DataInputStream(socket1.getInputStream());
        }
        catch (UnknownHostException u){
            System.out.println(u);
        }
        catch (IOException i){
            System.out.println(i);
        }
            //Here is where the communication happens anything you want for teh client and the server to interact together for
        String msg = "";
//        String txt = "";
        while(!msg.equals("!q")){
            try{
                msg = inputStream.readLine();
                outputStream.writeUTF(msg);
//                txt = inputStream2.readUTF();
//                System.out.println(txt);
            }
            catch (IOException i){
                System.out.println(i);
            }
        }
            //Close everything after finishing all Communication(interactions) between client and server
        try {
            inputStream.close();
            outputStream.close();
            socket1.close();
        }
        catch (IOException i){
            System.out.println(i);
        }
    }

    public static void main(String[] args) {
        Client client = new Client("192.168.1.12",3333);
    }
}
