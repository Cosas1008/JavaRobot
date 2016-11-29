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
	    private int[] response = new int[]{};
	    private int[] tool = new int[8];
    public RobotReadPosition() throws Exception {
		super(readInt);
		RobotReadPosition r = new RobotReadPosition();
		this.response = r.sendint();
    }

    private static void main(String[] args){
    	//The response store data form we have to modified to tool
    	for(int i :response){

    	}
    }

}
