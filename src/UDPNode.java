import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

public class UDPNode {
    String host = "192.168.1.24"; // Robot IPAddress
    private int port = 7000; // Computer's Port
    private int robotPort = 10040; // Robot's Port
    private int timeOut = 1000; // Default 1 second
    private final int MAX_BUFFER_LENGTH = 256;
    private byte[] robotCommand = new byte[MAX_BUFFER_LENGTH]; // Sending command
    private byte[] receiveData = new byte[MAX_BUFFER_LENGTH]; // Received buffer package of data
    private boolean flag = false;
    InetAddress robotAddress;

    // constructor
    public UDPNode(int port, int timeOut, byte[] robotCommand) {
	this.robotPort = port;
	this.timeOut = timeOut;
	this.robotCommand = robotCommand;
	try {
	    robotAddress = InetAddress.getByName(host);
	    // System.out.println("Robot IP address : " +
	    // robotAddress.getHostAddress());

	} catch (UnknownHostException e) {
	    e.printStackTrace();
	}
    }

    public UDPNode(byte[] robotCommand) {
	this.robotCommand = robotCommand;
	try {
	    robotAddress = InetAddress.getByName(host);
	    // System.out.println("Robot IP address : " +
	    // robotAddress.getHostAddress());

	} catch (UnknownHostException e) {
	    e.printStackTrace();
	}
    }

    public UDPNode() {
	// No command send
	try {
	    robotAddress = InetAddress.getByName(host);
	    // System.out.println("Robot IP address : " +
	    // robotAddress.getHostAddress());

	} catch (UnknownHostException e) {
	    e.printStackTrace();
	}
    }

    public byte[] submit() throws IOException {
	int count = 0;
	try {
	    DatagramSocket socket = new DatagramSocket(port); // Set // UDP // Socket.
	    socket.setSoTimeout(timeOut); // Millisecond
	    DatagramPacket request = new DatagramPacket(robotCommand, robotCommand.length, robotAddress, robotPort);
	    socket.send(request);
	    System.out.println("From Local IP address is : " + InetAddress.getLocalHost().getHostAddress());
	    System.out.println("Local Port is :¡@" + socket.getLocalPort());
	    System.out.println("Set Timeout : " + socket.getSoTimeout());
	    String command = new String(request.getData());
	    System.out.println("Send command : " + command);
	    DatagramPacket response = new DatagramPacket(receiveData, receiveData.length);
	    byte[] byteData = new byte[MAX_BUFFER_LENGTH];
	    while(count <= 3){
        	    try{
        		socket.receive(response);
        		this.flag = true;
        	    }catch (SocketTimeoutException e) {
        		socket.send(request); //resend
        		System.out.println("Resending the" +count +"-th time");
        		count++;
        	    }
	    }
	    if(!(flag)){
		System.out.println("Robot doesn't response");
		socket.close();
		byteData = null;
	    }else{
		// debug part
		    System.out.println("Message SocketAddress is : " + response.getSocketAddress());
		    System.out.println("Message Port is : " + response.getPort());
		    // System.out.println("");
		    // String message = new String(response.getData(),
		    // response.getOffset(), response.getLength());
		    // debug part
		    int num = ByteBuffer.wrap(response.getData()).getInt();
		    System.out.println("The response integral is: " + num + "has the length " + response.getLength());
		    byteData = response.getData();
		    socket.close();
	    }
	    
	    return byteData;
	} catch (UnknownHostException e) {
	    e.printStackTrace();
	    System.out.println("UnknownHostException, Please contact Y.W. Chen");
	    return null;
	} 
    }
}
