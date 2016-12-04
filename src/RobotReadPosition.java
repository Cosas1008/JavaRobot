/*
 * Modified on November 29th, 2016
 * 
 * Attention
 * 
 * This function is used to read the data from robot
 * This function is used only for initialize the tool variable
 * That is, we will assign the "integer array" named "tool" for the later on work
 */
public class RobotReadPosition extends SendUDP{
    private static int[] readInt = { 1497715267 , 536870912 , 50397184 , 0, 960051513 , 960051513 ,1962960128 , 65536 };
    private int[] response = new int[]{};
    private int[] tool = new int[8];

    public RobotReadPosition() {
	super(readInt);
    }

    public int[] read(){
	System.out.println("Called");
	RobotReadPosition robotread = new RobotReadPosition();
	try {
	    this.response = robotread.sendint();
	    if(this.response == null){
		return null;
	    }else{
		System.out.println("respnse = " + this.response.toString());
		System.out.println("Return has length " + this.response.length);
	    }
	} catch (NullPointerException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	    this.tool = null;
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} 
	return tool;
    }

    public byte[] swap(byte[] ibytes) {
	byte[] obytes = new byte[ibytes.length];
	for (int i = 0; i < ibytes.length; i++) {
	    if ((i + 1) % 4 == 0 && i != 0) {
		byte[] first = { ibytes[i - 3], ibytes[i - 2] };
		byte[] second = { ibytes[i - 1], ibytes[i] };
		obytes[i - 3] = second[1];
		obytes[i - 2] = second[0];
		obytes[i - 1] = first[1];
		obytes[i] = first[0];
		System.out.println("OBYTES" + i + " : " + obytes[i - 3] + " " + obytes[i - 2] + " " + obytes[i - 1]
			+ " " + obytes[i]);
	    }
	}

	return obytes;
    }

    public int[] stringTointArray(String[] commandByt) {
	RobotReadPosition robotPosition = new RobotReadPosition();
	byte[] commandbyteArray = new byte[commandByt.length];
	for (int i = 0; i < commandByt.length; i++) {
	    byte[] e = robotPosition.hexStringToByteArray(commandByt[i]);
	    commandbyteArray[i] = e[0];
	}
	byte[] changedCommandbyteArray = robotPosition.swap(commandbyteArray);
	/*Debug purpose
	Byte[] commandByteArray = byteString.toObjects(changedCommandbyteArray);
	List<Byte> byteList2 = Arrays.asList(commandByteArray);
	System.out.println(byteList2);
	//another swap function : Collections.reverse(byteList2);
	System.out.println(Arrays.toString(commandByteArray));
	 * 
	 */
	// byte[] into int32
	int[] haha = robotPosition.byteToint32(changedCommandbyteArray);
	for (int i : haha) {
	    System.out.println(i);
	}
	return haha;
    }

    public byte[] hexStringToByteArray(String s) {
	int len = s.length();
	byte[] data = new byte[len / 2];
	for (int i = 0; i < len; i += 2) {
	    data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
	}
	return data;
    }

    public int[] byteToint32(byte[] inputByteArray) {
	int[] x = new int[(inputByteArray.length) / 4];
	// System.out.println(x);
	for (int i = 1; i < (inputByteArray.length); i = i + 4) {
	    int offSet = (i - 1);
	    int length = 4;
	    int a = Math.floorDiv(i, 4);
	    // public static ByteBuffer wrap(byte[] array, int offset, int
	    // length) Wraps a byte array into a buffer.
	    x[a] = java.nio.ByteBuffer.wrap(inputByteArray, offSet, length).getInt();
	}
	return x;
    }

}
