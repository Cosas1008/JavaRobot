import java.util.Scanner;

public class JavaRobotExample {
    public static void main(String[] args) {
	System.out.println("Enter the angle of yaw :");
	Scanner sc = new Scanner(System.in);
	int yaw = sc.nextInt();
	System.out.println("Enter the angle of pitch :");
	int pitch = sc.nextInt();
	JavaRobot robot = new JavaRobot(yaw, pitch);
	System.out.println("You have set the robot angle to (yaw, pitch) = ( " + yaw + " , " + pitch + " )");
	robot.move();
    }
}
