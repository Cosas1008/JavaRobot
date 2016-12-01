/*
 * Modified on November 29th, 2016
 * 
 * Attention
 * 
 * This function is used to read the data from robot
 * This function is used only for initialize the tool variable
 * That is, we will assign the "integer array" named "tool" for the later on work
 */
public class RobotReadPosition extends SendUDP {
    private static int[] readInt = { 89, 69, 82, 67, 32, 00, 00, 00, 03, 01, 00, 00, 00, 00, 00, 00, 57, 57, 57, 57, 57,
	    57, 57, 57, 117, 00, 101, 00, 00, 01, 00, 00 };
    private int[] response = new int[] {};
    private int[] tool = new int[8];

    public RobotReadPosition() {
	super(readInt);
    }

    public int[] read() throws NullPointerException {
	System.out.println("Called");
	RobotReadPosition robotread = new RobotReadPosition();
	try {
	    this.response = robotread.sendint();
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	    this.response = null;
	}
	return response;
    }
}
