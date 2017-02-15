package org.usfirst.frc.team2635.robot;



public class DriveMotionOperation 
{
	//Common Parameters
	//OperationName MUST BE UNIQUE within an orchestration.
	public String OperationName;
	public double rpm;
	public DriveOperationType driveOperationType;
	public boolean Reverse;

	//Rotational Parameters
	public double targetAngle;
	public double turnRadiusInches;
	
	public boolean Clockwise;
	public boolean rotateCenter;
	
	
	
	
	//Linear Drive Parameters
	public double driveDistanceInches;
	
	
	
	//Operation Status
	public boolean OperationStarted;
	public boolean OperationFinished;
	
	
	

}
