import java.net.*;
import java.awt.*;
import javax.swing.*;

import javax.swing.JFrame;

import java.io.*;

/* SHOULD ONLY COMMIT TO GUI
*/

public class MyClient
{
	MyClient() {
		JFrame mainFrame = new JFrame();
		JPanel JPCenter = new JPanel();
		mainFrame.setSize(500, 500);
		mainFrame.setVisible(true);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public static void main(String [] args)
	{
		try{
			System.out.println("getLocalHost: "+InetAddress.getLocalHost() );
			System.out.println("getByName:    "+InetAddress.getByName("localhost") );
			//test 123 Jake
			Socket s = new Socket( args[0], 16789);
			InputStream in = s.getInputStream();
			OutputStream out = s.getOutputStream();
			
			PrintWriter pout = new PrintWriter(out);
			pout.println(args[1]);		// Writes some String to server
			pout.flush(); 					// forces the data through to server
			
			BufferedReader bin = new BufferedReader(new InputStreamReader(in));
			System.out.println(args[1]+" <=returned-as=> " + bin.readLine());

			out.close();
			pout.close();
			s.close();
		}
		catch(UnknownHostException uhe) {
			System.out.println("no host");
			uhe.printStackTrace();
		}
		catch(IOException ioe)
		{
			System.out.println("IO error");
			ioe.printStackTrace();
		}
	   catch( ArrayIndexOutOfBoundsException aioobe ) {
	 		System.out.println("\nUsage: java Day10Server hostname some-word");
	 	}
	}
}