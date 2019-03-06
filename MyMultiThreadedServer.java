import java.net.*;
import java.util.Vector;

import org.omg.CORBA.PUBLIC_MEMBER;

import java.io.*;

/* MyClient - Demo of client / server network communication
	by: Michael Floeser
*/

public class MyMultiThreadedServer {
    public static void main(String[] args) {
        new MyMultiThreadedServer();
    }

    Vector<ThreadServer> clientList = new Vector<ThreadServer>();

    public MyMultiThreadedServer() {
        ServerSocket ss = null;

        try {
            System.out.println("getLocalHost: " + InetAddress.getLocalHost());
            System.out.println("getByName:    " + InetAddress.getByName("localhost"));

            ss = new ServerSocket(16789);
            Socket cs = null;
            while (true) { // run forever once up

                // try{
                cs = ss.accept(); // wait for connection
                String usernameString = "John";
                ThreadServer ths = new ThreadServer(cs, usernameString );
                clientList.add(ths);
                ths.start();
            } // end while
        } catch (BindException be) {
            System.out.println("Server already running on this computer, stopping.");
        } catch (IOException ioe) {
            System.out.println("IO Error");
            ioe.printStackTrace();
        }

    } // end main

    class ThreadServer extends Thread {
        Socket cs;
        private boolean newUser = true;
        BufferedReader br;
        PrintWriter opw;
        String clientMsg;
        String userName;

        public ThreadServer(Socket cs, String username) {
            
            this.cs = cs;
            this.userName = username;
        }

        public void run() {

            try {
                br = new BufferedReader(new InputStreamReader(cs.getInputStream()));
                opw = new PrintWriter(new OutputStreamWriter(cs.getOutputStream()));

                System.out.println(newUser);
                if (newUser == true) {
                    newUser = false;
                    opw.println("Welcome to the server");

                }

                Thread testThread = new Thread() {
                    public void run() {
                        //while (true) {

                            try {
                                clientMsg = br.readLine();
                                System.out.println("Server read: " + clientMsg);
                            } catch (IOException e) {
                                e.printStackTrace();
                            } // from client

                            for (int i = 0; i < clientList.size(); i++) {
                                System.out.println("Sent message to client number " + i);
                                clientList.get(i).opw.println(userName + ": " + clientMsg);
                                opw.flush();
                            }
                        //}
                    }

                };
                testThread.start();

                //opw.println(clientMsg.toUpperCase()); // to client
            } catch (IOException e) {
                System.out.println("Inside catch");
                e.printStackTrace();
            }
        } // end while

    } // end class ThreadServer
} // end MyMultiThreadedServer