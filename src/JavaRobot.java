
public class JavaRobot extends SendUDP {
    private Boolean botinitial = false;
    private Boolean botReady = false;
    private RobotAngle targetAngle;
    private RobotAngle otherAngle;
    private int[] tool = new int[8];
    private int[] intTool = new int[8];

    // Constructor with no value
    public JavaRobot() {
	this.setInitial();
	this.setReady(false);
    }

    // Constructor with theta and phi
    public JavaRobot(int theta_in, int phi_in) {
	this.setReady(false);
	this.targetAngle = new RobotAngle(theta_in, phi_in);
    }

    private void setReady(Boolean input) {
	this.botReady = input;
	if(input){
	    JavaRobot.servoOn();
	    JavaRobot.holdOn();
	}
    }

    // initialize boolean function
    private void setInitial() {
	RobotReadPosition read = new RobotReadPosition();
	int[] tool = read.read();
	System.out.println("Form is " + tool[0]);
	System.out.println("Tool is " + tool[1]);
	System.out.println("X is " + tool[2]);
	System.out.println("Y is " + tool[3]);
	System.out.println("Z is " + tool[4]);
	System.out.println("Yaw is " + tool[5]);
	System.out.println("Pitch is " + tool[6]);
	System.out.println("Zr is " + tool[7]);
	this.botinitial = true;
    }

    private boolean getReady() {
	return botReady;
    }

    private boolean getInitial() {
	return botinitial;
    }
    //i have to finish read the position and calculate the diffence in angles and position here
    private boolean robotReady(){
	RobotReadPosition tg = new RobotReadPosition();
	
	return botReady;
    }
    
    
    
    
    public void moveTo(int theta, int phi) {
	if(!(getInitial())){
	    System.out.println("You didn't initialize the JavaRobot, try to use JavaRobot() or JavaRobot(int theta_in, int phi_in)");
	    return;
	} else {
	    int count =1;
	    setReady(false);
	    if (!(getReady())) {
		try {
		    while (getReady()) {
			System.out.println("Move to tool point.");
			setReady(robotReady());
			Thread.sleep(100 / count);// pause
			count ++;
		    }
		} catch (Exception e) {
		    e.printStackTrace();
		}
	    } else {
		this.setReady(false);
		targetAngle.setTheta(theta); // yaw
		targetAngle.setPhi(phi); // pitch
		System.out.println("Robot start moving");
		System.out.printf("Robot is moving to yaw = ", targetAngle.getTheta(), " and pitch = ",
			targetAngle.getPhi());
	    }
	}
    }

    public void move() throws Exception {
	this.setReady(false);
	System.out.println("Robot start moving! ");
	int speed = 10;
	RobotMove robotmove = new RobotMove(targetAngle.getTheta(), targetAngle.getPhi(), intTool, speed);
	System.out.printf("\nRobot is moving to yaw = %d and pitch = %d\n", targetAngle.getTheta(),
		targetAngle.getPhi());
	robotmove.move();
    }

    public int[] read() {
	RobotReadPosition robotread = new RobotReadPosition();
	int[] toolout = robotread.read(); // To store the outcome of Tool
	return toolout;
    }

    public Boolean isCloseTo(RobotAngle otherAngle) {
	return (Math.abs(otherAngle.getTheta() - targetAngle.getTheta()) < 0.01)
		&& (Math.abs(otherAngle.getPhi() - targetAngle.getPhi()) < 0.01);
    }

    public Boolean isCloseTo() {
	return (Math.abs(otherAngle.getTheta() - targetAngle.getTheta()) < 0.01)
		&& (Math.abs(otherAngle.getPhi() - targetAngle.getPhi()) < 0.01);
    }

    // Inner class of Tool store information
    class Tool {
	private int toolnumber;
	private int formnumber;
	private int tZ;
	private RobotPosition rectAngular;
	private RobotAngle angular;

	public Tool(int[] toolin) {
	    this.formnumber = toolin[0];
	    this.toolnumber = toolin[1];
	    rectAngular = new RobotPosition(toolin[2], toolin[3], toolin[4]);
	    angular = new RobotAngle(toolin[5], toolin[6]);

	    this.tZ = toolin[7];
	}

	public Tool() {

	}

	public void setrX(int input) {
	    rectAngular.setX(input);
	}

	public void setrY(int input) {
	    rectAngular.setY(input);
	}

	public void setrZ(int input) {
	    rectAngular.setZ(input);
	}

	public void settX(int input) {
	    angular.setPhi(input);
	}

	public void settY(int input) {
	    angular.setTheta(input);
	}

	public int[] getSetting() {
	    int[] setting = { formnumber, toolnumber };
	    return setting;
	}
	
	public int[] getPosition() {
	    return rectAngular.getPosition();
	}

	public int getTheta() {
	    return angular.getTheta();
	}

	public int getPhi() {
	    return angular.getPhi();
	}
	
	public int getZr(){
	    return this.tZ;
	}
    }

    // Inner class of RobotAngle contains yaw and pitch
    class RobotAngle {

	private int theta;
	private int phi;

	public RobotAngle(int theta, int phi) {
	    this.theta = theta;
	    this.phi = phi;
	}

	public RobotAngle() {
	    // Initialize the RobotAngle with (0,0)
	    this.theta = 0;
	    this.phi = 0;
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

    // Inner class of Robot Position contains X, Y and Z
    public class RobotPosition {
	private int X;
	private int Y;
	private int Z;
	private int[] robotposi = new int[3];

	public RobotPosition(int X, int Y, int Z) {
	    this.X = X;
	    this.Y = Y;
	    this.Z = Z;
	}

	public RobotPosition() {
	    this.X = 0;
	    this.Y = 0;
	    this.Z = 0;
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

    public static void servoOn() {
	JavaRobotServo serbo = new JavaRobotServo(1);//Servo ON 
	serbo.makeServo(1);
	Boolean sevo = serbo.makeServo(1);
	if (sevo) {
	    System.out.println("Servo Start!");
	}
    }
    
    
    public Boolean servo(int i) {
	JavaRobotServo serbo = new JavaRobotServo(i);// 1 : Servo ON / 2: Servo
						     // OFF
	Boolean sevo = serbo.makeServo(1);
	if (sevo && i == 1) {
	    System.out.println("Servo Start!");
	} else if (!(sevo) && i == 2) {
	    System.out.println("Servo Off!");
	} else {
	    System.out.println("Servo failure");
	}
	return sevo;
    }

    public Boolean alert(int i) {
	JavaRobotAlert robotAlert = new JavaRobotAlert(i);// 1 : Read Alert / 2
							  // : Reset Alert
	String alert = robotAlert.makeAlert(i);
	Boolean returnBoolean = new Boolean(true);
	if ((!"".equals(alert))) {
	    returnBoolean = true;// Not any Alert happened
	} else {
	    returnBoolean = false;
	}
	return returnBoolean;
    }
    
    public static void holdOn() {
	JavaRobotHold hold = new JavaRobotHold(1);// 1 : Hold ON / 2 : Hold OFF
	Boolean holdd = hold.makeHold(1);
	if (holdd) {
	    System.out.println("Hold ON! Yoo-hoo-hoo!");
	}
    }
    
    public Boolean hold(int i) {
	JavaRobotHold hold = new JavaRobotHold(i);// 1 : Hold ON / 2 : Hold OFF
	Boolean sevo = hold.makeHold(i);
	if (sevo && i == 1) {
	    System.out.println("Hold ON! Yoo-hoo-hoo!");
	} else if (!(sevo) && i == 2) {
	    System.out.println("Hold Off!");
	} else {
	    System.out.println("Hold failure");
	}
	return sevo;
    }
}
