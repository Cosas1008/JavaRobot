import java.util.Scanner;

public class JavaRobotExample {
    public static void main(String[] args){
	System.out.println("Enter the angle of yaw :¡@\n");
	Scanner sc = new Scanner(System.in);
	int yaw =  sc.nextInt();
	System.out.println("Enter the angle of pitch :¡@\n");
	int pitch = sc.nextInt();
	JavaRobot robot = new JavaRobot(yaw,pitch);
    }
}
