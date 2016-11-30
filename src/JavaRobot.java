
public class JavaRobot extends SendUDP {
    Boolean initial = false;
    Boolean botReady = false;
    RobotAngle targetAngle;
    RobotPosition targetPosition;
    
    private int[] tool = new int[8];

    // Constructor with no value
    public JavaRobot() {
	this.targetAngle = new RobotAngle(0, 0);
	this.setReady(true);
    }

    // Constructor with theta and phi
    public JavaRobot(int theta_in, int phi_in) {
	this.targetAngle = new RobotAngle(theta_in, phi_in);
	this.setReady(true);
    }

    private void setReady(Boolean input) {
	this.botReady = input;
    }
    //Before moveTo or move command, we have to initialize first
    public void init(){
	
    }
    public void moveTo(int theta, int phi) {
	if(initial){
	    //We have to initialize the JavaRobot first
	    
	    RobotReadPosition tg = null;
	    try {
		this.tool = tg.sendint();
	    } catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}
	this.setReady(false);
	targetAngle.setTheta(theta); // yaw
	targetAngle.setPhi(phi); // pitch
	System.out.println("Robot start moving");
	System.out.printf("Robot is moving to yaw = ",targetAngle.getTheta()," and pitch = ",targetAngle.getPhi());
    }
    
    public void move(){
	this.setReady(false);
	System.out.println("Robot start moving! ");
	RobotMove robotmove = new RobotMove(targetAngle.getTheta(),targetAngle.getPhi(),tool);
	System.out.printf("\nRobot is moving to yaw = %d and pitch = %d\n",targetAngle.getTheta(),targetAngle.getPhi());
	robotmove.move();
    }
    public Boolean isCloseTo(RobotAngle otherAngle) {
	return (Math.abs(otherAngle.getTheta() - targetAngle.getTheta()) < 0.01)
		&& (Math.abs(otherAngle.getPhi() - targetAngle.getPhi()) < 0.01);
    }

    // Inner class of RobotAngle
    // contains yaw and pitch
    class RobotAngle {

	private int theta;
	private int phi;

	RobotAngle(int theta, int phi) {
	    this.theta = theta;
	    this.phi = phi;
	}

	// basic void function set and get angle
	public void setTheta(int angle) {
	    this.theta = angle;
	}

	public void setPhi(int angle) {
	    this.phi = angle;
	}

	public int getTheta() {
	    return this.theta;
	}

	public int getPhi() {
	    return this.phi;
	}

    }

    // Inner class of Robot Position
    // contains X, Y and Z
    class RobotPosition {
	private int X;
	private int Y;
	private int Z;
	private int[] robotposi = new int[3];

	RobotPosition(int X, int Y, int Z) {
	    this.X = X;
	    this.Y = Y;
	    this.Z = Z;
	}

	// basic void to set and get position
	public void setX(int position) {
	    this.X = position;
	}

	public void setY(int position) {
	    this.Y = position;
	}

	public void setZ(int position) {
	    this.Z = position;
	}

	public int[] getPosition() {
	    this.robotposi[1] = X;
	    this.robotposi[2] = Y;
	    this.robotposi[3] = Z;
	    return this.robotposi;
	}
    }


}
