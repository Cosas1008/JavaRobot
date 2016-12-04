import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public abstract class SendUDP {
    private final int MAX_BUFFER_LENGTH = 256;
    private byte[] command = new byte[MAX_BUFFER_LENGTH];
    private int port;
    private int timeOut;
    private static byte[] readInt = { 89, 69, 82, 67, 32, 00, 00, 00, 03, 01, 00, 00, 00, 00, 00, 00, 57, 57, 57, 57, 57,
	    57, 57, 57, 117, 00, 101, 00, 00, 01, 00, 00 };// Read Position

    // static String[] toolPosition = null;// Tool number ,Form ,X ,Y ,Z ,Xr ,Yr
    // ,Zr
    public SendUDP(int iport, int itimeOut, byte[] robotCommand) {
	this.port = iport;
	this.timeOut = itimeOut;
	this.command = robotCommand;
    }

    public SendUDP(byte[] byteCommand) {
	this(9999, 1000, byteCommand);
    }

    public SendUDP(int[] IntCommand) {
	byte[] byteComm = null;
	try {
	    byteComm = InttoByteArray(IntCommand);
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	this.command = byteComm;
    }

    public SendUDP() {
	this.command = readInt;
    }

    public byte[] send() throws Exception {
	UDPNode Command = new UDPNode(command);
	byte[] response = new byte[]{};
	try{
	    response = Command.submit();
	    return response;
	}catch(SocketTimeoutException e){
	    System.out.println("Cannot get response");
	    return null;
	}
	
    }
    public int[] sendint() throws Exception {
	UDPNode Command = new UDPNode(command);
	byte[] response = new byte[]{};
	try{
	    response = Command.submit();
	      
	}catch(SocketTimeoutException e){
	    System.out.println("Cannot get response");
	    return null;
	}
	return  byteArrayToInt(response);  
    }

    public int[] byteArrayToInt(byte[] b) {
	if(b != null){
	int[] transfered = new int[(b.length) / 4];
	for (int j = 0; j < (b.length / 4); j++) {
	    transfered[j] = b[(j * 4) + 3] & 0xFF | (b[(j * 4) + 2] & 0xFF) << 8 | (b[(j * 4) + 1] & 0xFF) << 16
		    | (b[(j * 4) + 0] & 0xFF) << 24;
	    
	}
	return transfered;}
	else{
	    return null;
	}
    }
/*
    public byte[] InttoByteArray(int[] inputIntArray) {
	byte[] transfered = new byte[(inputIntArray.length * 4)];
	for (int j = 0; j < inputIntArray.length; j++) {
	    transfered[(j * 4)] = (byte) (inputIntArray[j] >> 24);
	    transfered[(j * 4) + 1] = (byte) (inputIntArray[j] >> 24);
	    transfered[(j * 4) + 2] = (byte) (inputIntArray[j] >> 24);
	    transfered[(j * 4) + 3] = (byte) (inputIntArray[j] >> 24);
	}
	return transfered;
    }
    */
    public byte[] InttoByteArray(int[] inputIntArray) throws Exception {
	boolean isEmpty = true;
	byte[] transfered = new byte[(inputIntArray.length * 4)];
	
	ByteBuffer byteBuffer = ByteBuffer.allocate(inputIntArray.length *4);
	IntBuffer intBuffer = byteBuffer.asIntBuffer();
	intBuffer.put(inputIntArray);
	transfered = byteBuffer.array();
	
	for (byte b : transfered) {
	    if (b != 0) {
	        isEmpty = false;
	    }
	}
	if(isEmpty){
	    return new byte[]{(byte) 99,(byte) 99,(byte) 99};
	}else{
	    return transfered;
	}
    }
    
}
