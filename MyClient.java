import java.net.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import javax.swing.JFrame;

import java.io.*;

/* SHOULD ONLY COMMIT TO GUI
*/

public class MyClient extends Thread implements ActionListener
{
	private static Socket s;
	private JTextArea jtaMessage;
	private JTextField jtfMessageInput;
	private static String ip;
	private static boolean ipSet = false;
	
	public MyClient()
	{
		try
		{	
			JFrame mainFrame = new JFrame();
			JPanel jpSouth = new JPanel();
			JButton jbSend = new JButton("SEND");
			jtaMessage = new JTextArea("Please enter sever IP in message box below.");
			JScrollPane jsaScroll = new JScrollPane(jtaMessage);
			jtfMessageInput = new JTextField();
			//jtfMessageInput.setSize(10, 100);
			jpSouth.setLayout(new BorderLayout());
			mainFrame.add(jpSouth, BorderLayout.SOUTH);
			mainFrame.add(jsaScroll, BorderLayout.CENTER);
			jpSouth.add(jbSend, BorderLayout.CENTER);
			jpSouth.add(jtfMessageInput, BorderLayout.NORTH);
			jtaMessage.setSize(300, 300);
			jtaMessage.setEditable(false);
			jsaScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
			jsaScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			mainFrame.setSize(500, 500);
			mainFrame.setVisible(true);
			mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			jbSend.addActionListener(this);
			
			s = new Socket("192.168.0.125", 16789);
			System.out.println("Connected to " + s.getInetAddress());
		} 
		catch (UnknownHostException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String [] args)
	{
		JFrame initFrame = new JFrame("Please enter server IP");
		
		
			initFrame.add(new JLabel("Enter the Server IP:"), BorderLayout.WEST);
			JButton jbIp = new JButton("Enter");
			final JTextField jtIP = new JTextField("localhost");
			initFrame.add(jtIP, BorderLayout.CENTER);
			initFrame.add(jbIp, BorderLayout.SOUTH);
			initFrame.pack();
			initFrame.setVisible(true);
			initFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	   	 	
			while(ipSet == false)
			{
	   	 	jbIp.addActionListener(new ActionListener() 
			{
		         public void actionPerformed(ActionEvent ae) 
		         {
		        	 final String ipStr = jtIP.getText();
		        	 if(ipStr.equals(""))
		        	 {
		        		 ip = "localhost";
		        		 ipSet = true;
		        	 }
		        	 else
		        	 {
		        		 ip = ipStr;
		        		 ipSet = true;
		        	 }
		         }
			});
		}
   	 	initFrame.setVisible(false);
		
		try{
			s = new Socket( ip, 16789);
			new MyClient();
			System.out.println("getLocalHost: "+InetAddress.getLocalHost() );
			System.out.println("getByName:    "+InetAddress.getByName("localhost") );
			
			
			
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
		thServ serverLoop = new thServ();
		serverLoop.start();
	}
	
	public synchronized void actionPerformed(ActionEvent ae)
	{
		try
		{

			OutputStream out = s.getOutputStream();
			String temp = jtfMessageInput.getText();
			PrintWriter pout = new PrintWriter(out);
			pout.println(temp);		// Writes some String to server
			jtaMessage.setText(jtaMessage.getText()+ "\n" + temp);
			pout.flush(); 					// forces the data through to server
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public class thServ extends Thread
	{
		public thServ()
		{
			
		}
		public void run()
		{
			try
			{
				InputStream in = s.getInputStream();
				BufferedReader bin = new BufferedReader(new InputStreamReader(in));
				
				while(true)
				{
					jtaMessage.setText(jtaMessage.getText() + "\n" + bin.readLine());
				}
				
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}