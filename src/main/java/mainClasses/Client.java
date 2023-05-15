package mainClasses;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
    Scanner input = new Scanner(System.in);
    DataInputStream inputStream2 = null;
    DataOutputStream outputStream = null;
    Socket socket1 = null;
    String name;

    public Client(String host, int port) throws IOException {
        //wait for name to be input and start teh connection
        try {
            System.out.println("Enter the name that will be shown in chat : ");
            this.name = input.nextLine();
            socket1 = new Socket(host, port);
            if (socket1.isConnected()) {
                System.out.println("Connection successful");
            }
            inputStream2 = new DataInputStream(socket1.getInputStream());
            outputStream = new DataOutputStream(socket1.getOutputStream());

            outputStream.writeUTF(name);
        } catch (UnknownHostException u) {
            System.out.println(u);
        } catch (IOException i) {
            System.out.println(i);
        }

        // Start the listener thread
        listenerThread();

        // Send messages to the server
        try {
            while (true) {
                String msg = input.nextLine();
                if (msg.equals("!q")) {
                    break;
                }

                if (msg.contains("add")) {
                    add(msg);
                }
                if (msg.contains("subtract")) {
                    subtract(msg);
                }
                if (msg.contains("multiply")) {
                    multiply(msg);
                }
                if (msg.contains("divide")) {
                    divide(msg);
                }
                outputStream.writeUTF(msg);
                outputStream.flush();
            }
        } catch (IOException i) {
            System.out.println(i);
        } finally {
            try {
                if (inputStream2 != null) {
                    inputStream2.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
                if (socket1 != null) {
                    socket1.close();
                }
            } catch (IOException i) {
                System.out.println(i);
            }
        }
    }

    //Calculator methods
    public void add(String msg) {
        int sum =0;
        for (int i = 0; i < msg.length(); i++) {
            char character = msg.charAt(i);
            if (Character.isDigit(character)) {
                sum += Integer.parseInt(String.valueOf(character));
            }
        }
        System.out.println("The sum = " + sum);
            }

    public void subtract(String msg) {
        int subtraction = 0;
        for (int i = 0; i < msg.length(); i++) {
            char character = msg.charAt(i);
            if (Character.isDigit(character)) {
                if(subtraction == 0) {
                    subtraction = Integer.parseInt(String.valueOf(character));
                }
                else{
                    subtraction -= Integer.parseInt(String.valueOf(character));
                }
            }
        }
        System.out.println("The subtraction = " + subtraction);

    }

    public void multiply(String msg) {
        int product = 1;
        for (int i = 0; i < msg.length(); i++) {
            char character = msg.charAt(i);
            if (Character.isDigit(character)) {
                product *= Integer.parseInt(String.valueOf(character));
            }
        }
        System.out.println("The product = " + product);

    }

    public void divide(String msg) {
        int division = 1;
        for (int i = 0; i < msg.length(); i++) {
            char character = msg.charAt(i);
            if (Character.isDigit(character)) {
                division = Integer.parseInt(String.valueOf(character))/division;
            }
        }
        System.out.println("The sum = " + division);
    }

    public void listenerThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (socket1.isConnected()) {
                    try {
                        String msg = inputStream2.readUTF();
                        System.out.println(msg);
                    } catch (IOException i) {
                        System.out.println(i);
                    }
                }
            }
        }).start();
    }

    public static void main(String[] args) throws IOException {
        Client client = new Client("localhost", 3333);
    }
}