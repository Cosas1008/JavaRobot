
public class JavaRobotHold extends SendUDP {
    
	private Boolean status = new Boolean(false);
	//Constructor for JavaRobotHold
	public JavaRobotHold(int i) {
		super((i+5));// Hold 6: ON / 7: OFF
	}

	public Boolean makeHold(int index) {
		int[] response = null;
		JavaRobotHold js = new JavaRobotHold(index);
		try {
			response = js.sendint();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(response[6] == 0){
			status = true;
		}else{
			status = false;
		}
		
		return status;
	}
	
}
