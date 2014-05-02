package hillbotgui;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class InfoGetter { 
	private String serverHostname;
	private DatagramSocket clientSocket;
	private InetAddress IPAddress;
	private int port;

	private static final int DEFAULT_PORT = 7877;

	public InfoGetter(String serverHostname, int port){
		this.serverHostname = serverHostname;
		this.port = port;
	}

	public InfoGetter(String serverHostname){
		this(serverHostname, DEFAULT_PORT);
	}

	public void connect() throws SocketException, UnknownHostException{
		clientSocket = new DatagramSocket(port);
		IPAddress = InetAddress.getByName(serverHostname);

		System.out.println ("Setting host as " + IPAddress + 
				" at UDP port " + port);
	}
	
	public String fakeInfo(){
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "{ser:" + Math.random() + "," + Math.random() + "," + Math.random() + "," + Math.random() + "};";
		//return "{ser:1,.5,.75,1,1,1,1,1,1};";
	}

	public String getInfo() throws IOException{
		/*byte[] receiveData = new byte[1024];

		DatagramPacket receivePacket = 
				new DatagramPacket(receiveData, receiveData.length);

		clientSocket.setSoTimeout(10000);

		String info = null;

		try {
			clientSocket.receive(receivePacket); 

			info = new String(receivePacket.getData()); 
		}
		catch (SocketTimeoutException ste)
		{
			System.out.println ("Timeout Occurred: Packet assumed lost");
		}

		return info;*/
		return fakeInfo();
	}

	public void close(){
		clientSocket.close();
	}
} 