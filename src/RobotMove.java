import java.util.ArrayList;

public class RobotMove extends SendUDP {
    // Parameters
    private boolean bangular = false;
    private final int MAX_BUFFER_LENGTH = 256;
    private static int[] Head = { 89, 69, 82, 67, 32, 00, 104, 00, 03, 01, 00, 00, 00, 00, 00, 00, 57, 57, 57, 57, 57,
	    57, 57, 57 };// Header part
    private static int[] Suh = { 138, 00, 03, 00, 01, 02, 00, 00 };// Sub-header
								   // part
    // Setting( Speed Coordinate)
    private int speed = 10;
    private int[] Setting_rect = { 01, 00, 01, speed * 10, 17 };// Setting of
								// Speed to 1
								// mm/s and
								// Cartesian
								// Robot
								// coordinate(17)
    private int[] Setting_ang = { 01, 00, 02, speed * 10, 19 };// Setting of
							       // Speed to 1
							       // degree/s and
							       // Cartesian Tool
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

    // constructor
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
	if(bangular){//This function is now move angular only
	    this.x = 0;
	    this.y = 0;
	    this.z = 0;
	    this.yaw = tool[5];
	    this.pitch = tool[6];
	    this.zr = tool[7];
	}else{//Move rectangular only
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
	int[] command = new int[] {};
	command = generateCommand(bangular);
	System.out.println("moving.......");

	// System.out.println("ArrayLsit is : " + arrlist);//deBug ArrayList

    }

    private int[] generateCommand(boolean arg){
	ArrayList<Integer> arrlist = new ArrayList<Integer>(MAX_BUFFER_LENGTH);
	
	//Head
	for(int i : Head){
	    arrlist.add(i);
	}
	//Suh
	for(int i : Suh){
	    arrlist.add(i);
	}
	//Setting
	if (arg){ //if True => angle
	    for(int i : Setting_ang)
		arrlist.add(i);
	}else{//if False => rectangle
	    for(int i : Setting_rect)
		arrlist.add(i);
	}
	arrlist.add(coor);		//Axis
	arrlist.add(Reserv);		//Reserve
	arrlist.add(typeNumber);	//type
	arrlist.add(Reserv);		//Extension1
	arrlist.add(toolNumber);	//Tool number
	for(int i : coordinate){	//Coordinate of rectangle
	    arrlist.add(i);
	}
	for(int i : angle){		//Coordinate of angular
	    arrlist.add(i);
	}
	for(int i : extension){		//Extension2
	    arrlist.add(i);
	}
	System.out.println("ArrayLsit is : " + arrlist);//deBug ArrayList
	
	
	//Change arraylist type to int[] type
	int[] ints = new int[arrlist.size()];
	for(int i=0, len = arrlist.size(); i < len; i++){
	    ints[i] = arrlist.get(i);
	}
	
	
	return ints;
    }

}