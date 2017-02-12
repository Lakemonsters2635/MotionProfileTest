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

	public static RotationParameters SetRotationProfile(double targetAngle, double wheelRadiusInches, double turnRadiusInches, double wheelSeparationInches,  double rpm, boolean Clockwise, boolean rotateCenter)
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
	
	
	public static ArrayList<DriveMotionOperation> SimpleOperationTest()
	{
		ArrayList<DriveMotionOperation> operationList = new ArrayList<DriveMotionOperation>();
		DriveMotionOperation rotateOperation = new DriveMotionOperation();
		//90.0, 1.45,  0.0, 19, 400,clockWise, centerRotate 
		rotateOperation.targetAngle = 90.0;
		rotateOperation.driveOperationType = DriveOperationType.Rotation;
		rotateOperation.rpm = 400;
		rotateOperation.turnRadiusInches = 0;
		rotateOperation.rotateCenter = true;
		rotateOperation.Clockwise = false;
		rotateOperation.wheelRadiusInches = 1.45;
		rotateOperation.wheelSeparationInches = 19.0;
		rotateOperation.OperationFinished = false;
		rotateOperation.OperationStarted = false;
		rotateOperation.OperationName = "Operation1";
		
		operationList.add(rotateOperation);
		
		
		DriveMotionOperation rotateOperation2 = new DriveMotionOperation();
		
		//90.0, 1.45,  0.0, 19, 400,clockWise, centerRotate 
		rotateOperation2.targetAngle = 90.0;
		rotateOperation2.driveOperationType = DriveOperationType.Rotation;
		rotateOperation2.rpm = 400;
		rotateOperation2.turnRadiusInches = 0;
		rotateOperation2.rotateCenter = true;
		rotateOperation2.Clockwise = false;
		rotateOperation2.wheelRadiusInches = 1.45;
		rotateOperation2.wheelSeparationInches = 19.0;
		rotateOperation2.OperationName = "Operation2";
		rotateOperation2.OperationFinished = false;
		rotateOperation2.OperationStarted = false;
		
		operationList.add(rotateOperation2);
//		
//		rotateOperation = new DriveMotionOperation();
//		//90.0, 1.45,  0.0, 19, 400,clockWise, centerRotate 
//		rotateOperation.targetAngle = 90.0;
//		rotateOperation.driveOperationType = DriveOperationType.Rotation;
//		rotateOperation.rpm = 400;
//		rotateOperation.turnRadiusInches = 0;
//		rotateOperation.rotateCenter = true;
//		rotateOperation.Clockwise = false;
//		rotateOperation.wheelRadiusInches = 1.45;
//		rotateOperation.wheelSeparationInches = 19.0;
//		
//		operationList.add(rotateOperation);
//		
//		rotateOperation = new DriveMotionOperation();
//		//90.0, 1.45,  0.0, 19, 400,clockWise, centerRotate 
//		rotateOperation.targetAngle = 90.0;
//		rotateOperation.driveOperationType = DriveOperationType.Rotation;
//		rotateOperation.rpm = 400;
//		rotateOperation.turnRadiusInches = 0;
//		rotateOperation.rotateCenter = true;
//		rotateOperation.Clockwise = false;
//		rotateOperation.wheelRadiusInches = 1.45;
//		rotateOperation.wheelSeparationInches = 19.0;
		
		//operationList.add(rotateOperation);
		
		return operationList;
	}
	
	
	
	public 	ArrayList<ProfilePoint> RampUp480()
	{
		// Position (rotations)	Velocity (RPM)	Duration (ms)
		double [][]Points = new double[][]{		
		{0.0167272727272727,	61.09090909	,10},
		{0.0290909090909091,	87.27272727	,10},
		{0.0461818181818182,	117.8181818	,10},
		{0.0687272727272727,	152.7272727	,10},
		{0.0974545454545455,	192	,10},
		{0.133090909090909,	235.6363636	,10},
		{0.176363636363636,	283.6363636	,10},
		{0.226909090909091,	322.9090909	,10},
		{0.283636363636364,	357.8181818	,10},
		{0.345818181818182,	388.3636364	,10},
		{0.412727272727273,	414.5454545	,10},
		{0.483636363636364,	436.3636364	,10},
		{0.557818181818182,	453.8181818	,10},
		{0.634545454545455,	466.9090909	,10},
		{0.713090909090909,	475.6363636	,10},
		{0.792727272727273,	480	,10}};
		
		ArrayList<ProfilePoint> resultPoints = new ArrayList<ProfilePoint>();
		for (int i=0; i < Points.length; i++)
		{
			ProfilePoint point = new ProfilePoint( Points[i][0], Points[i][1], (int)Points[i][2]);
			resultPoints.add(point);
		}
		
		return resultPoints;
		
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
	
	public ArrayList<ProfilePoint> RampDown480(double RampdownStart)
	{
		// Position (rotations)	Velocity (RPM)	Duration (ms)
		double [][]Points = new double[][]{	
		{RampdownStart +	0.07927272727,	471.2727273  ,10},  //FHE  470 Fudged
		{RampdownStart +	0.1567272727,	458.1818182	,10},
		{RampdownStart +	0.2316363636,	440.7272727	,10},
		{RampdownStart +	0.3032727273,	418.9090909	,10},
		{RampdownStart +	0.3709090909,	392.7272727	,10},
		{RampdownStart +	0.4338181818,	362.1818182	,10},
		{RampdownStart +	0.4912727273,	327.2727273 ,10},  //FHE 349.0909092 Fudged
		{RampdownStart +	0.5425454545,	288	        ,10},
		{RampdownStart +	0.5869090909,	244.3636364	,10},
		{RampdownStart +	0.6236363636,	196.3636364	,10},
		{RampdownStart +	0.6530909091,	157.0909091	,10},
		{RampdownStart +	0.6763636364,	122.1818182	,10},
		{RampdownStart +	0.6941818182,	91.63636364	,10},
		{RampdownStart +	0.7072727273,	65.45454545	,10},
		{RampdownStart +	0.7163636364,	43.63636364	,10},
		{RampdownStart +	0.7221818182,	26.18181818	,10},
		{RampdownStart +	0.7254545455,	13.09090909	,10},
		{RampdownStart +	0.7269090909,	4.363636364	,10},
		{RampdownStart +	0.7272727273,	0	        ,10},
		{RampdownStart +	0.7272727273,	0	        ,10}};
		
		ArrayList<ProfilePoint> resultPoints = new ArrayList<ProfilePoint>();
		for (int i=0; i < Points.length; i++)
		{

			ProfilePoint point = new ProfilePoint( Points[i][0], Points[i][1], (int)Points[i][2]);
			resultPoints.add(point);	

		}
		
		return resultPoints;
	}
	
	public ArrayList<ProfilePoint> Drive480(double distanceInches,  double wheelRadiusInches)
	{
		ArrayList<ProfilePoint> TotalPointsList = RampUp480();
		
		double RampUpEndPosition = TotalPointsList.get(TotalPointsList.size()-1).RotationalPosition;

		
		double distancePerRotation = wheelRadiusInches * 2 * Math.PI;		
		double requiredRotations = distanceInches/distancePerRotation;
		
		double Time = (requiredRotations / 480) * 60;
		double numberOfIntervals = Time * 100;
		double positionDelta = (480 * .01)/60;
		ProfilePoint point = new ProfilePoint(RampUpEndPosition + positionDelta, 480,10);
		TotalPointsList.add(point);
		
		for (int i=1; i < (int)numberOfIntervals; i++)
		{
			point = new ProfilePoint(TotalPointsList.get(i-1).RotationalPosition + positionDelta, 480,10);
			TotalPointsList.add(point);
		}
		
		ArrayList<ProfilePoint> rampDownPoints = RampDown480(TotalPointsList.get(TotalPointsList.size()-1).RotationalPosition);

		TotalPointsList.addAll(rampDownPoints);
		
		
		return TotalPointsList;
	}
	
	
	
//	public double[][] GenerateRampUpProfile(double maxRotationsPerSec, double numberRotations, int T1Ms, int T2Ms, int timeIntervalMs)
//	{
//		
//
//		double T4 = (numberRotations/maxRotationsPerSec) * 1000.0;
//		//double FLV = ROUNDUP((T1Ms/timeIntervalMs),0);
//		double FL1 = Math.ceil(T1Ms/timeIntervalMs);
//		double FL2 = Math.ceil(T2Ms/timeIntervalMs);
//		double N = T4/timeIntervalMs;
//		
//		double [][]Points;
//		//And then, a miracle happens.
//		
//		return Points;
//	}
	

	
	

		
	
	
		
}
	
	

