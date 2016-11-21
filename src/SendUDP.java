
public abstract class SendUDP {
    private int port = 9999;
    private int timeOut = 1000;
    private byte[] command;
    private static int[] readInt = { 89, 69, 82, 67, 32, 00, 00, 00, 03, 01, 00, 00, 00, 00, 00, 00, 57, 57, 57, 57, 57,
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
	this(readInt);
    }

    public void send() throws Exception {
	UDPNode Command = new UDPNode(port, timeOut, command);
	Command.submit();
    }

    public int[] byteArrayToInt(byte[] b) {
	int[] transfered = new int[(b.length) / 4];
	for (int j = 0; j < (b.length / 4); j++) {
	    transfered[j] = b[(j * 4) + 3] & 0xFF | (b[(j * 4) + 2] & 0xFF) << 8 | (b[(j * 4) + 1] & 0xFF) << 16
		    | (b[(j * 4) + 0] & 0xFF) << 24;
	}
	return transfered;
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
