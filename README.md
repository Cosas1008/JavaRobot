"# JavaRobot" 
This is the function which can directly send commands through UDP to # YASKAWA-MH12 # Robotic Arm, with controller DX200.

There are 3 versions, with only the 3rd is only maintainable, in this repository, feel free to use this for research purpose, all the right reserved 
This is the final Repository of JavaRobot version 1-3 and the brief Matlab calling code demonstrate here.  

Initialize a JavaRobot object with JavaRobotRead on matlab, you may get three variable,  
JavaRobot is the object created in **Java**.  
Tool store the information you need to move the robot.  
RefPosRMatrix will output an array 3x3 of rotational matrix.  

#### Update
JavaRobot_1.1.jar run at jre1.8.0_121 release at 2017 Aug  
JavaRobot_1.3.jar run at jre1.8.0_151 release at 2018 Jan  



##### version release note:

In the end of 2018 will release the ros package with open source Motoplus and ros.industrial support 

```matlab

%***********************IMPORTANT**************************%
%This function will executed once and for all position you are about to use
[JavaRobot, Tool, RefPosRMatrix] = JavaRobotRead();

```

Use the matlab to call JavaRobotMove with the parameter you need and call another Matlab function call JavaRobotMove( 2 is for moving both angular and rectangular purpose)  

```matlab

X = 0;  
Y = 0;  
Z = 0;  
Roll = 0;  
Pitch = 0;  
Yaw = 0;  
speed = 100;  
mThread = JavaRobotMove(Roll, Pitch, Yaw, speed,   JavaRobot, Tool, RefPosRMatrix);  %Only move angles  
mThread2 = JavaRobotMove2(X,Y,Z,Roll, Pitch, Yaw, speed,   JavaRobot, Tool, RefPosRMatrix); %Move angles and   position  

```

In JavaRobot, if you know the function what I've created, then you can use it directly without hesitation. The function in Matlab is only a kind of API using JavaRobot. However, you still need a guide line  just before you went through all the code yourself.  

There are three layer of classes you may see in jar file (or you may go through `src` directory on Gitbut instead) 


1. package rfvlsi
2. package rfvlsi.Robot
3. package rfvlsi.Robot.RobotCommand 

For the first layer, you can only find a file called JavaRobotExample.java. The code is for tutorial part of how to create the main object `JavaRobot` and the way to send command to robot with `RobotMoveThread`  

```java

public class JavaRobot {
	private UDPNode robotNode = new UDPNode();

	private RobotPosition refPosition;
	// the robot should always start at refPosition;

	public RobotPosition getRefPosition() {
		return refPosition;
	}

	public UDPNode getUDPNode() {
		return this.robotNode;
	}

	// Constructor with no value
	public JavaRobot() throws Exception {
		refPosition = getCurrentPosition();
		System.out.println("refPosition is : " + refPosition.toString());
	}

	public synchronized RobotPosition getCurrentPosition() throws Exception {
		// Note; need to use "synchronized" modifier; only one thread can run
		// this method.
		try {
			CommandReadPosition cmdReadPos = new CommandReadPosition();
			return (RobotPosition) cmdReadPos.sendTo(this, (int) (Math.random() * 150) + 10050);
		} catch (NullPointerException e) {
			System.out.println("Can not get RobotPosition.");
			return null;
		}
	}

	public RobotMoveThread moveTo(RobotPosition target, int speed) {
		RobotMoveThread moveThread = new RobotMoveThread(this, target, speed);
		moveThread.start(); // start the new thread to run the run() function.
		return moveThread;
	}
}
```

You may have notice the JavaRobot call two main function in package *rfvlsi.Robot*. one is `getCurrentPosition` and the other is `RobotMoveThread`, you can know the function in its own code. The data structure `RobotPosition` resemble `Tool` in Matlab, storing the information of original position (or so called reference position) and used almost every parameter used in the functions.  

in UtilConvert are functions to deal with byte, int8, int32 and String array, the function is quite straightforward referring to the name of function.  
```java

package rfvlsi.Robot;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class UtilConvert {
	/*
	 * Below here are functions to deal with byte, int8, int32 and String array
	 * transformation
	 */
	public static int[] byteArrayToInt(byte[] b);

	public static byte[] InttoByteArray(int[] inputIntArray);

	public static byte[] InttoByteArraySingle(int input);

	public static byte[] InttoByteArrayMove(int[] inputIntArray);

	public static byte[] swap(byte[] ibytes);

	public static byte[] hexStringToByteArray(String s); 
	public static int[] byteToint32;

	public static int[] stringTointArray;

	public static byte[] swapString(byte[] ibytes);

	public static String convert(byte[] data);
}
```
in package rfvlsi.Robot.RobotCommand, all the class inheritate from the abstract class RobotCommand. The RobotCommand is the abstract class for sending the command to robotic arm, here demonstrate the *design pattern* in RobotCommand.
```java
package rfvlsi.Robot.RobotCommand;

import rfvlsi.Robot.JavaRobot;

public abstract class RobotCommand {
	// return the datagram in byte[] to send to Robot
	protected abstract byte[] getDatagram();

	public Object sendTo(JavaRobot robot, int port) throws Exception {
		byte[] response = robot.getUDPNode().submit(this.getDatagram(), port);
		return this.parseResult(response);
	};

	// parse the result, and return true for success.
	protected abstract Object parseResult(byte[] response) throws Exception;

}

```

Last Update 7/26/2017 9:39:04 AM 