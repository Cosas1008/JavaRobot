
public class JavaRobotAlert extends SendUDP {
    private static byte[] commandAlertRead = { 89, 69, 82, 67, 32, 0, 0, 0, 3, 1, 0, 0, 0, 0, 0, 0, 57, 57, 57, 57, 57,
	    57, 57, 57, 112, 0, 1, 0, 0, 1, 0, 0 };
    private static byte[] commandAlertReset = { 89, 69, 82, 67, 32, 0, 4, 0, 3, 1, 0, 0, 0, 0, 0, 0, 57, 57, 57, 57, 57,
	    57, 57, 57, -126, 0, 1, 0, 1, 16, 0, 0, 1, 0, 0, 0 };
    private Boolean alert = new Boolean(false);

    public JavaRobotAlert() {
	// TODO Auto-generated constructor stub

    }

    public Boolean call() {
	alert = false;
	return alert;
    }

}
