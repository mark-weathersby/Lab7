import java.net.*;
import java.awt.*;
import javax.swing.*;

import javax.swing.JFrame;

import java.io.*;

/* SHOULD ONLY COMMIT TO GUI
*/

public class MyClient
{
	public MyClient() {
		JFrame mainFrame = new JFrame();
		JPanel jpSouth = new JPanel();
		JButton jbSend = new JButton("SEND");
		JTextArea jtaMessage = new JTextArea("test test test");
		JScrollPane jsaScroll = new JScrollPane(jtaMessage);
		JTextField jtfMessageInput = new JTextField();
		//jtfMessageInput.setSize(10, 100);
		jpSouth.setLayout(new BorderLayout());
		mainFrame.add(jpSouth, BorderLayout.SOUTH);
		mainFrame.add(jsaScroll, BorderLayout.CENTER);
		jpSouth.add(jbSend, BorderLayout.CENTER);
		jpSouth.add(jtfMessageInput, BorderLayout.NORTH);
		jtaMessage.setSize(300, 300);
		jsaScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		jsaScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		mainFrame.setSize(500, 500);
		mainFrame.setVisible(true);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public static void main(String [] args)
	{
		try{
			
			new MyClient();
			System.out.println("getLocalHost: "+InetAddress.getLocalHost() );
			System.out.println("getByName:    "+InetAddress.getByName("localhost") );
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