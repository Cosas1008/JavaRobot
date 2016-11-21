
public class RobotGetAngle extends SendUDP {
    	
	static byte[] command = {};
	
	
	public RobotGetAngle(){
	    super(command);	    
	}
	public static void main(String[] args) {
	    	RobotGetAngle get = new RobotGetAngle();
    		try {
    		    get.send();
    		} catch (Exception e) {
        	    // TODO Auto-generated catch block
        	    e.printStackTrace();
        	}
	}

}
