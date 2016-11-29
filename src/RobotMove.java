import java.util.ArrayList;

public class RobotMove extends UDPNode{
    // Parameters
    private boolean bangular = false;
    private boolean isYaw = false;
    private final int HEAD_PACKAGE_SIZE = 32;
    private static final int[] Head = { 89, 69, 82, 67, 32, 00, 104, 00, 03, 01, 00, 00, 00, 00, 00, 00, 57, 57, 57, 57,
	    57, 57, 57, 57 };// Header part
    private static final int[] Suh = { 138, 00, 03, 00, 01, 02, 00, 00 };// Sub-header
    // part
    // Setting( Speed Coordinate)
    private int speed = 10;
    private int[] Setting_rect = { 01, 00, 01, speed * 10, 17 };// Setting of
								// Speed to 1
								// mm/s and
								// Cartesian
								// coordinate(17)
    private int[] Setting_ang = { 01, 00, 02, speed * 10, 19 };// Setting of
							       // Speed to 1
							       // degree/s and
							       // Cartesian
							       // coordinate(19)
    private int x = 0;
    private int y = 0;
    private int z = 0;
    private int yaw = 0;
    private int pitch = 0;
    private int zr = 0;
    private int Reserv = 0;
    private int toolNumber = 0;
    private int typeNumber = 0;
    private int coor = 1;
    private int[] extension = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
    // Axis = Tool(9:32); % X, Y, Z, TX, TY, TZ
    private int[] coordinate = new int[3];
    private int[] angle = new int[3];
    private byte[] comm = new byte[] {};

    // constructor
    public RobotMove(int xin, int yin, int zin, int theta, int phi, int[] tool) {
	this.toolNumber = tool[0];
	this.typeNumber = tool[1];
	this.coor = tool[2];
	this.x = tool[3];
	this.y = tool[4];
	this.z = tool[5];
	this.yaw = tool[6];
	this.pitch = tool[7];
	this.zr = tool[8];
	for (int i = 0; i < 3; i++) {
	    switch (i) {
	    case 1:
		this.coordinate[i] = this.x - xin;
		this.angle[i] = yaw - theta;
		break;
	    case 2:
		this.coordinate[i] = this.y - yin;
		this.angle[i] = pitch - phi;
		break;
	    case 3:
		this.coordinate[i] = this.z - zin;
		this.angle[i] = zr;
		break;
	    }
	}
    }

    public RobotMove(int[] tool) {
	this.toolNumber = tool[0];
	this.typeNumber = tool[1];
	this.coor = tool[2];
	this.x = tool[3];
	this.y = tool[4];
	this.z = tool[5];
	this.yaw = tool[6];
	this.pitch = tool[7];
	this.zr = tool[8];
	for (int i = 0; i < 3; i++) {
	    switch (i) {
	    case 1:
		this.coordinate[i] = this.x;
		this.angle[i] = yaw;
		break;
	    case 2:
		this.coordinate[i] = this.y;
		this.angle[i] = pitch;
		break;
	    case 3:
		this.coordinate[i] = this.z;
		this.angle[i] = zr;
		break;
	    }
	}

    }

    public RobotMove(int theta, int phi, int[] tool) {
	this.toolNumber = tool[0];
	this.typeNumber = tool[1];
	if (bangular) {// This function is now move angular only
	    this.x = 0;
	    this.y = 0;
	    this.z = 0;
	    this.yaw = tool[5];
	    this.pitch = tool[6];
	    this.zr = tool[7];
	} else {// Move rectangular only
	    this.x = tool[2];
	    this.y = tool[3];
	    this.z = tool[4];
	    this.yaw = 0;
	    this.pitch = 0;
	    this.zr = 0;
	}

	for (int i = 0; i < 3; i++) {
	    switch (i) {
	    case 1:
		this.coordinate[i] = this.x;
		this.angle[i] = theta;
		break;
	    case 2:
		this.coordinate[i] = this.y;
		this.angle[i] = phi;
		break;
	    case 3:
		this.coordinate[i] = this.z;
		this.angle[i] = zr;
		break;
	    }
	}
    }

    public void move() {
	// Check whether there are placement first
	// if there are changes in placement, the function will stop after
	// change the
	// placement rather than move angular, you would have to call the
	// function again
	
	int[] rect = { x, y, z };
	moveRect(rect);// Move to the position first
	movePitch(0);// Set Pitch to 0 first
	moveYaw(yaw);// Yaw set to assigned value
	movePitch(pitch);// Pitch set to assigned value
    }

    private void moveRect(int[] rectint) {
	
	bangular = false;
	comm = generateCommand(bangular, rectint);
	
	
    }

    private void movePitch(int thetain) {
	bangular = true;
	isYaw = false;
	comm = generateCommand(bangular, isYaw, thetain);
    }

    public void moveYaw(int phiin) {
	bangular = true;
	isYaw = true;
	comm = generateCommand(bangular, isYaw, phiin);
    }
    //Command for moving in rectangular
    private byte[] generateCommand(boolean arg, int[] rectint) {
	ArrayList<Integer> arrlist = new ArrayList<Integer>(32);
	for (int i : Head) {// Head
	    arrlist.add(i);
	}
	for (int i : Suh) {// Suh
	    arrlist.add(i);
	}
	// Change arraylist type to int[] type
	int[] ints = new int[arrlist.size()];
	byte[] bytes = new byte[arrlist.size()];
	for (int i = 0, len = arrlist.size(); i < len; i++) {
	    ints[i] = arrlist.get(i);
	    bytes[i] = (byte) ints[i];
	}
	//System.out.println("ArrayLsit is : " + arrlist);// deBug ArrayList
	// Integer to 4 bytes
	// Setting
	ArrayList<Integer> arrlist2 = new ArrayList<Integer>();
	if (arg) { // if True => angle
	    for (int i : Setting_ang)
		arrlist2.add(i);
	} else {// if False => rectangle
	    for (int i : Setting_rect)
		arrlist2.add(i);
	}
	for (int i : coordinate) { // Coordinate of rectangle
	    arrlist2.add(i);
	}
	for (int i : angle) { // Coordinate of angular
	    i = 0; // set angular to 0
	    arrlist2.add(i);
	}
	for (int i : extension) { // Extension2
	    arrlist2.add(i);
	}
	arrlist2.add(coor); // Axis
	arrlist2.add(Reserv); // Reserve
	arrlist2.add(typeNumber); // type
	arrlist2.add(Reserv); // Extension1
	arrlist2.add(toolNumber); // Tool number
	int[] ints2 = new int[arrlist2.size()];
	byte[] bytes2 = new byte[arrlist2.size()];
	for (int i = 0, len = arrlist2.size(); i < len; i++) {
	    ints2[i] = arrlist2.get(i);
	    bytes2[i] = (byte) ints2[i];
	}
	//System.out.println("ArrayLsit 2 is : " + arrlist2);// deBug ArrayList

	byte[] outbyte = new byte[bytes.length + bytes2.length];
	// outbyte = combine(arrlist, arrlist2);
	System.arraycopy(bytes, 0, outbyte, 0, bytes.length);
	System.arraycopy(bytes2, 0, outbyte, bytes.length, bytes2.length);
	System.out.println("The output byte array is " + outbyte);
	return outbyte;
    }
    //Command for moving in angular
    private byte[] generateCommand(boolean arg, boolean isyaw, int anglein) {
	ArrayList<Integer> arrlist = new ArrayList<Integer>(HEAD_PACKAGE_SIZE);
	for (int i : Head) {// Head
	    arrlist.add(i);
	}
	for (int i : Suh) {// Suh
	    arrlist.add(i);
	}
	// Change arraylist type to int[] type
	int[] ints = new int[arrlist.size()];
	byte[] bytes = new byte[arrlist.size()];
	for (int i = 0, len = arrlist.size(); i < len; i++) {
	    ints[i] = arrlist.get(i);
	    bytes[i] = (byte) ints[i];
	}
	//System.out.println("ArrayLsit is : " + arrlist);// deBug ArrayList
	// Integer to 4 bytes
	// Setting
	ArrayList<Integer> arrlist2 = new ArrayList<Integer>();
	if (arg) { // if True => angle
	    for (int i : Setting_ang)
		arrlist2.add(i);
	} else {// if False => rectangle
	    for (int i : Setting_rect)
		arrlist2.add(i);
	}
	for (int i : coordinate) { // Coordinate of rectangle
	    i = 0;
	    arrlist2.add(i);
	}
	if ((isYaw)) {
	    arrlist2.add(0);
	    arrlist2.add(angle[1]);
	    arrlist2.add(0);
	} else {
	    arrlist2.add(angle[0]);
	    arrlist2.add(0);
	    arrlist2.add(0);
	}
	for (int i : extension) { // Extension2
	    arrlist2.add(i);
	}
	arrlist2.add(coor); // Axis
	arrlist2.add(Reserv); // Reserve
	arrlist2.add(typeNumber); // type
	arrlist2.add(Reserv); // Extension1
	arrlist2.add(toolNumber); // Tool number
	int[] ints2 = new int[arrlist2.size()];
	byte[] bytes2 = new byte[arrlist2.size()];
	for (int i = 0, len = arrlist2.size(); i < len; i++) {
	    ints2[i] = arrlist2.get(i);
	    bytes2[i] = (byte) ints2[i];
	}
	//System.out.println("ArrayLsit 2 is : " + arrlist2);// deBug ArrayList

	byte[] outbyte = new byte[bytes.length + bytes2.length];
	// outbyte = combine(arrlist, arrlist2);
	System.arraycopy(bytes, 0, outbyte, 0, bytes.length);
	System.arraycopy(bytes2, 0, outbyte, bytes.length, bytes2.length);
	System.out.println("The output byte array is " + outbyte);
	return outbyte;
    }
/*
    private byte[] combine(ArrayList<Integer> arrlist, ArrayList<Integer> arrlist2) {
	int[] arr1 = convertIntegers(arrlist);
	int[] arr2 = convertIntegers(arrlist2);
	int length = arr1.length + arr2.length;

	byte[] outputbyte = new byte[length];
	System.arraycopy(arr1, 0, outputbyte, 0, arr1.length);
	System.arraycopy(arr2, 0, outputbyte, arr1.length, arr2.length);
	return outputbyte;
    }

    public static int[] convertIntegers(ArrayList<Integer> integers) {
	int[] ret = new int[integers.size()];
	for (int i = 0; i < ret.length; i++) {
	    ret[i] = integers.get(i).intValue();
	}
	return ret;
    }
*/
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