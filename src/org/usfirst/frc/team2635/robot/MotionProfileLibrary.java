package org.usfirst.frc.team2635.robot;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

public class MotionProfileLibrary
{
	public Map<String, MotionProfile> Profiles;
	
	public MotionProfileLibrary()
	{
		Profiles = new HashMap<String, MotionProfile>();
	}

	public static RotationParameters getRotationParameters(double targetAngle, 
														   double wheelRadiusInches,
														   double turnRadiusInches, 
														   double wheelSeparationInches,  
														   double rpm, 
														   boolean Clockwise, 
														   boolean rotateCenter)
	{
		double inchesPerRotation = wheelRadiusInches * 2 * Math.PI;
		
		double arcLengthInner;
		double archLengthOuter;
		double innerWheelRotations;
		double outerWheelRotations;
		
		if (rotateCenter)
		{			
			//To rotate around center.
			double radius = wheelSeparationInches/2.0;
			//radius is 1/2 of wheelSeparationInches
			//ArcLengh = radius * angle in radians
			
			arcLengthInner = radius *  (2*Math.PI)/360.0 * targetAngle;
			archLengthOuter = arcLengthInner;
			innerWheelRotations = arcLengthInner/inchesPerRotation;
			outerWheelRotations = archLengthOuter/inchesPerRotation;

		}
		else
		{	
			arcLengthInner = turnRadiusInches *  (2*Math.PI)/360.0 * targetAngle;
			archLengthOuter = (turnRadiusInches + wheelSeparationInches)  *  (2*Math.PI)/360.0 * targetAngle;
			innerWheelRotations = arcLengthInner/inchesPerRotation;
			outerWheelRotations = -archLengthOuter/inchesPerRotation;
		}
		
		
		double velocityRatio = Math.abs(outerWheelRotations/innerWheelRotations);
		
		double innerVelocity = rpm;
		double outerVelocity = rpm * velocityRatio;
		
		double innerAcceleration = 2 * innerVelocity;
		double outerAcceleration = 2 * outerVelocity;
		
		
		if (!Clockwise && !rotateCenter)
		{
			double tmpRotation = innerWheelRotations;
			innerWheelRotations = outerWheelRotations;
			outerWheelRotations = tmpRotation;
			
			double tmpAcceleration = innerAcceleration;
			innerAcceleration = outerAcceleration;
			outerAcceleration = tmpAcceleration;
			
			double tmpVelocity = innerVelocity;
			innerVelocity = outerVelocity;
			outerVelocity = tmpVelocity;
		}
		else if (!Clockwise && rotateCenter)
		{
			innerWheelRotations = -innerWheelRotations;
			outerWheelRotations = -outerWheelRotations;
		}
		
		
		RotationParameters rotationParams = new RotationParameters();
		rotationParams.innerAcceleration = innerAcceleration;
		rotationParams.outerAcceleration = outerAcceleration;
		rotationParams.innerVelocity     = innerVelocity;
		rotationParams.outerVelocity     = outerVelocity;
		rotationParams.innerWheelRotations = innerWheelRotations;
		rotationParams.outerWheelRotations = outerWheelRotations;

		return rotationParams;

//		System.out.println("innerVelocity:" + innerVelocity);
//		System.out.println("outerVelocity:" + outerVelocity);
//		
//		System.out.println("innerAcceleration:" + innerAcceleration);
//		System.out.println("outerAcceleration:" + outerAcceleration);
//		 
//		System.out.println("outerWheelRotations:" + outerWheelRotations);
//		System.out.println("innerWheelRotations:" + innerWheelRotations);
		

	}
	
	public static DriveParameters getDriveParameters(double wheelRadiusInches, double distanceInches, double rpm, boolean reverse)
	{
		double inchesPerRotation = wheelRadiusInches * 2 * Math.PI;
		
		//double arcLengthInner;
		//double archLengthOuter;
		double velocity = rpm;
		double acceleration = rpm * 2;
		double leftWheelRotations = -distanceInches/inchesPerRotation;
		double rightWheelRotations = distanceInches/inchesPerRotation;
		
		if (reverse)
		{			
			leftWheelRotations = -leftWheelRotations;
			rightWheelRotations = -rightWheelRotations;
		}
		
		DriveParameters driveParams = new DriveParameters();
		driveParams.maxAcceleration = acceleration;
		driveParams.leftWheelRotations = leftWheelRotations;
		driveParams.rightWheelRotations = rightWheelRotations;
		driveParams.maxVelocity     = velocity;
		
		return driveParams;

	}
	
	
	public static ArrayList<DriveMotionOperation> SimpleOperationTest()
	{
		
//		//go forrward
		ArrayList<DriveMotionOperation> operationList = new ArrayList<DriveMotionOperation>();
		DriveMotionOperation rotateOperation;
		DriveMotionOperation driveOperation;
		
		//rotateOperation = new DriveMotionOperation();
//		//90.0, 1.45,  0.0, 19, 400,clockWise, centerRotate 
//		rotateOperation.targetAngle = 90.0;
//		rotateOperation.driveOperationType = DriveOperationType.Rotation;
//		rotateOperation.rpm = 400;
//		rotateOperation.turnRadiusInches = 0;
//		rotateOperation.rotateCenter = true;
//		rotateOperation.Clockwise = false;
//		rotateOperation.OperationFinished = false;
//		rotateOperation.OperationStarted = false;
//		rotateOperation.OperationName = "Rotate1";
//		operationList.add(rotateOperation);
//
//		
		//driveOperation = new DriveMotionOperation();
//		//90.0, 1.45,  0.0, 19, 400,clockWise, centerRotate 
//		//driveOperation.targetAngle = 90.0;
//		driveOperation.driveOperationType = DriveOperationType.Drive;
//		driveOperation.rpm = 400;
//		driveOperation.driveDistanceInches = 36;
//		driveOperation.rotateCenter = false;
//		driveOperation.Clockwise = false;
//		driveOperation.OperationFinished = false;
//		driveOperation.OperationStarted = false;
//		driveOperation.OperationName = "Drive1";
//		driveOperation.Reverse = false;
//		operationList.add(driveOperation);
//
//		
//		
//		rotateOperation = new DriveMotionOperation();
//		//90.0, 1.45,  0.0, 19, 400,clockWise, centerRotate 
//		rotateOperation.targetAngle = 90.0;
//		rotateOperation.driveOperationType = DriveOperationType.Rotation;
//		rotateOperation.rpm = 400;
//		rotateOperation.turnRadiusInches = 0;
//		rotateOperation.rotateCenter = true;
//		rotateOperation.Clockwise = false;
//		rotateOperation.OperationName = "Rotate2";
//		rotateOperation.OperationFinished = false;
//		rotateOperation.OperationStarted = false;
//		operationList.add(rotateOperation);
//		
//		driveOperation = new DriveMotionOperation();
//		//90.0, 1.45,  0.0, 19, 400,clockWise, centerRotate 
//		//driveOperation.targetAngle = 90.0;
//		driveOperation.driveOperationType = DriveOperationType.Drive;
//		driveOperation.rpm = 400;
//		driveOperation.driveDistanceInches = 36;
//		driveOperation.rotateCenter = false;
//		driveOperation.Clockwise = false;
//		driveOperation.OperationFinished = false;
//		driveOperation.OperationStarted = false;
//		driveOperation.OperationName = "Drive1";
//		driveOperation.Reverse = false;
//		operationList.add(driveOperation);
//
//		
//		rotateOperation = new DriveMotionOperation();
//		//90.0, 1.45,  0.0, 19, 400,clockWise, centerRotate 
//		rotateOperation.targetAngle = 90.0;
//		rotateOperation.driveOperationType = DriveOperationType.Rotation;
//		rotateOperation.rpm = 400;
//		rotateOperation.turnRadiusInches = 0;
//		rotateOperation.rotateCenter = true;
//		rotateOperation.Clockwise = false;
//		rotateOperation.OperationName = "Rotate2";
//		rotateOperation.OperationFinished = false;
//		rotateOperation.OperationStarted = false;
//		operationList.add(rotateOperation);
//		
//		
//		driveOperation = new DriveMotionOperation();
//		//90.0, 1.45,  0.0, 19, 400,clockWise, centerRotate 
//		//driveOperation.targetAngle = 90.0;
//		driveOperation.driveOperationType = DriveOperationType.Drive;
//		driveOperation.rpm = 400;
//		driveOperation.driveDistanceInches = 36;
//		driveOperation.rotateCenter = false;
//		driveOperation.Clockwise = false;
//		driveOperation.OperationFinished = false;
//		driveOperation.OperationStarted = false;
//		driveOperation.OperationName = "Drive1";
//		driveOperation.Reverse = false;
//		operationList.add(driveOperation);
//		
//		rotateOperation = new DriveMotionOperation();
//		//90.0, 1.45,  0.0, 19, 400,clockWise, centerRotate 
//		rotateOperation.targetAngle = 90.0;
//		rotateOperation.driveOperationType = DriveOperationType.Rotation;
//		rotateOperation.rpm = 400;
//		rotateOperation.turnRadiusInches = 0;
//		rotateOperation.rotateCenter = true;
//		rotateOperation.Clockwise = false;
//		rotateOperation.OperationName = "Rotate2";
//		rotateOperation.OperationFinished = false;
//		rotateOperation.OperationStarted = false;
//		operationList.add(rotateOperation);
		
		driveOperation = new DriveMotionOperation();
		//90.0, 1.45,  0.0, 19, 400,clockWise, centerRotate 
		//driveOperation.targetAngle = 90.0;
		driveOperation.driveOperationType = DriveOperationType.Drive;
		driveOperation.rpm = 400;
		driveOperation.driveDistanceInches = 36;
		driveOperation.rotateCenter = false;
		driveOperation.Clockwise = false;
		driveOperation.OperationFinished = false;
		driveOperation.OperationStarted = false;
		driveOperation.OperationName = "Drive1";
		driveOperation.Reverse = true;
		operationList.add(driveOperation);
		
		driveOperation = new DriveMotionOperation();
		//90.0, 1.45,  0.0, 19, 400,clockWise, centerRotate 
		//driveOperation.targetAngle = 90.0;
		driveOperation.driveOperationType = DriveOperationType.Drive;
		driveOperation.rpm = 400;
		driveOperation.driveDistanceInches = 36;
		driveOperation.rotateCenter = false;
		driveOperation.Clockwise = false;
		driveOperation.OperationFinished = false;
		driveOperation.OperationStarted = false;
		driveOperation.OperationName = "Drive1";
		driveOperation.Reverse = false;
		operationList.add(driveOperation);
		
		//go reverse
		
		
//		rotateOperation = new DriveMotionOperation();
//		//90.0, 1.45,  0.0, 19, 400,clockWise, centerRotate 
//		rotateOperation.targetAngle = 90.0;
//		rotateOperation.driveOperationType = DriveOperationType.Rotation;
//		rotateOperation.rpm = 400;
//		rotateOperation.turnRadiusInches = 0;
//		rotateOperation.rotateCenter = true;
//		rotateOperation.Clockwise = true;
//		rotateOperation.OperationName = "Rotate2";
//		rotateOperation.OperationFinished = false;
//		rotateOperation.OperationStarted = false;
//		operationList.add(rotateOperation);
//		
//		driveOperation = new DriveMotionOperation();
//		//90.0, 1.45,  0.0, 19, 400,clockWise, centerRotate 
//		//driveOperation.targetAngle = 90.0;
//		driveOperation.driveOperationType = DriveOperationType.Drive;
//		driveOperation.rpm = 400;
//		driveOperation.driveDistanceInches = 36;
//		driveOperation.rotateCenter = false;
//		driveOperation.Clockwise = false;
//		driveOperation.OperationFinished = false;
//		driveOperation.OperationStarted = false;
//		driveOperation.OperationName = "Drive1";
//		driveOperation.Reverse = true;
//		operationList.add(driveOperation);
//		
//		rotateOperation = new DriveMotionOperation();
//		//90.0, 1.45,  0.0, 19, 400,clockWise, centerRotate 
//		rotateOperation.targetAngle = 90.0;
//		rotateOperation.driveOperationType = DriveOperationType.Rotation;
//		rotateOperation.rpm = 400;
//		rotateOperation.turnRadiusInches = 0;
//		rotateOperation.rotateCenter = true;
//		rotateOperation.Clockwise = true;
//		rotateOperation.OperationName = "Rotate2";
//		rotateOperation.OperationFinished = false;
//		rotateOperation.OperationStarted = false;
//		operationList.add(rotateOperation);
//		
//		driveOperation = new DriveMotionOperation();
//		//90.0, 1.45,  0.0, 19, 400,clockWise, centerRotate 
//		//driveOperation.targetAngle = 90.0;
//		driveOperation.driveOperationType = DriveOperationType.Drive;
//		driveOperation.rpm = 400;
//		driveOperation.driveDistanceInches = 36;
//		driveOperation.rotateCenter = false;
//		driveOperation.Clockwise = false;
//		driveOperation.OperationFinished = false;
//		driveOperation.OperationStarted = false;
//		driveOperation.OperationName = "Drive1";
//		driveOperation.Reverse = true;
//		operationList.add(driveOperation);
//		
//		rotateOperation = new DriveMotionOperation();
//		//90.0, 1.45,  0.0, 19, 400,clockWise, centerRotate 
//		rotateOperation.targetAngle = 90.0;
//		rotateOperation.driveOperationType = DriveOperationType.Rotation;
//		rotateOperation.rpm = 400;
//		rotateOperation.turnRadiusInches = 0;
//		rotateOperation.rotateCenter = true;
//		rotateOperation.Clockwise = true;
//		rotateOperation.OperationName = "Rotate2";
//		rotateOperation.OperationFinished = false;
//		rotateOperation.OperationStarted = false;
//		operationList.add(rotateOperation);
//		
//		driveOperation = new DriveMotionOperation();
//		//90.0, 1.45,  0.0, 19, 400,clockWise, centerRotate 
//		//driveOperation.targetAngle = 90.0;
//		driveOperation.driveOperationType = DriveOperationType.Drive;
//		driveOperation.rpm = 400;
//		driveOperation.driveDistanceInches = 36;
//		driveOperation.rotateCenter = false;
//		driveOperation.Clockwise = false;
//		driveOperation.OperationFinished = false;
//		driveOperation.OperationStarted = false;
//		driveOperation.OperationName = "Drive1";
//		driveOperation.Reverse = true;
//		operationList.add(driveOperation);
		
		return operationList;
	}
	
	
	
	
	
	public double[][] ArrayListToPoints(ArrayList<ProfilePoint> pointList)
	{
		double[][] Points = new double[pointList.size()][3];
		for (int i=0; i < pointList.size(); i++)
		{
			Points[i][0] = pointList.get(i).RotationalPosition;
			Points[i][1] = pointList.get(i).Velocity;
			Points[i][2] = (double)pointList.get(i).Duration;
		}
		
		return Points;
	}
	
	
	
	
	
	


	
	

		
	
	
		
}
	
	

