
public class JavaRobotAlert extends SendUDP {
    private static byte[] commandAlertRead = { 89, 69, 82, 67, 32, 0, 0, 0, 3, 1, 0, 0, 0, 0, 0, 0, 57, 57, 57, 57, 57,
	    57, 57, 57, 112, 0, 1, 0, 0, 1, 0, 0 };
    private static byte[] commandAlertReset = { 89, 69, 82, 67, 32, 0, 4, 0, 3, 1, 0, 0, 0, 0, 0, 0, 57, 57, 57, 57, 57,
	    57, 57, 57, -126, 0, 1, 0, 1, 16, 0, 0, 1, 0, 0, 0 };
    private Boolean alert = new Boolean(false);

    // private constructor JavaRobotAlert Read
    private JavaRobotAlert() {
	super(commandAlertRead);
    }

    private JavaRobotAlert(int i) {
	super(commandAlertReset);
    }

    public static JavaRobotAlert makeAlert(int index){
	switch(index){
	case 1://read alert
	    return new JavaRobotAlert();
	case 2://reset alert
	    return new JavaRobotAlert(1);
	default://reset alert
	    return new JavaRobotAlert(1);
	}
    }

    public Boolean call() {
	alert = false;
	return alert;
    }

}
