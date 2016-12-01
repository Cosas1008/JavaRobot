import java.net.SocketTimeoutException;

public abstract class SendUDP {
    private int port = 7000;
    private int timeOut = 1000;
    private byte[] command;
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
	byte[] byteComm = InttoByteArray(IntCommand);
	this.command = byteComm;
    }

    public SendUDP() {
	this.command = readInt;
    }

    public byte[] send() throws Exception {
	UDPNode Command = new UDPNode(port, timeOut, command);
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
	UDPNode Command = new UDPNode(port, timeOut, command);
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
}
