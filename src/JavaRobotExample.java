 //import java.util.Scanner;

public class JavaRobotExample {
	private static Boolean flag = new Boolean(false);

	public static void main(String[] args) throws Exception {
		// System.out.println("Enter the angle of yaw :");
		// Scanner sc = new Scanner(System.in);
		// int yaw = sc.nextInt();
		// System.out.println("Enter the angle of pitch :");
		// int pitch = sc.nextInt();
		int yaw = 10;
		int pitch = 23;
		JavaRobot robot = new JavaRobot(yaw, pitch);
		System.out.println("You have set the robot angle to (yaw, pitch) = ( " + yaw + " , " + pitch + " )");
		robot.test();
		flag = JavaRobot.Hold(1);	//Hold ON
		System.out.println("Hold : " + flag.toString());
		flag = JavaRobot.Servo(1);	//Servo ON
		System.out.println("Servo : " + flag.toString());
		flag = JavaRobot.Hold(2);	//Hold OFF
		System.out.println("Hold : " + flag.toString());
		flag = JavaRobot.Servo(2);	//Serbo OFF
		System.out.println("Servo : " + flag.toString());
	}
}
