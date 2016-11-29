/*
 * 
 * This function is used to read the data from robot
 * 
 */
public class RobotReadPosition extends SendUDP {
    private static int[] readInt = { 89, 69, 82, 67, 32, 00, 00, 00, 03, 01, 00, 00, 00, 00, 00, 00, 57, 57, 57, 57, 57,
	    57, 57, 57, 117, 00, 101, 00, 00, 01, 00, 00 };

    public RobotReadPosition() throws Exception {
	super(readInt);
	RobotReadPosition r = new RobotReadPosition();
	int[] response = new int[]{};
	response = r.sendint();
    }

}
