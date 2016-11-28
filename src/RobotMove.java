import java.util.ArrayList;

public class RobotMove {
    // Parameters
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
	toolNumber = tool[0];
	typeNumber = tool[1];
	coor = tool[2];
	coordinate[0] = tool[3];
	coordinate[1] = tool[4];
	coordinate[2] = tool[5];
	angle[0] = tool[6];
	angle[1] = tool[7];
	angle[2] = tool[8];
    }

    public RobotMove(int theta, int phi, int[] tool) {
	// TODO Auto-generated constructor stub
	
    }

    public void move() {
	int[] command;
	ArrayList<Integer> arrlist = new ArrayList<Integer>(MAX_BUFFER_LENGTH);

	// Use add() method to add elements in the list with condition and
	// switch cases

    }
}
