package org.usfirst.frc.team2635.robot;
import java.util.ArrayList;
import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.kauailabs.navx.frc.AHRS;

public class Robot extends IterativeRobot {

	
	public AHRS navx;
	
	/** The Talon we want to motion profile. */
	CANTalon _talon = new CANTalon(9);
	CANTalon _talon2 = new CANTalon(4);
	CANTalon _talon3 = new CANTalon(6);
	CANTalon _talon4 = new CANTalon(12);
	
	public double wheelRadiusInches = 1.45;
	public double wheelSeparationInches = 19.0;
	


	//MotionProfileLibrary profileLibrary = new MotionProfileLibrary();
	//ArrayList<ProfilePoint> pointList = profileLibrary.Drive480(120, 2.5);
	//double[][] profilePoints = profileLibrary.ArrayListToPoints(pointList);
	//double[][] profilePoints = profileLibrary.Profiles.get("Profile").profilePoints;
	//MotionProfile leftProfile = new MotionProfile(profilePoints, true);
	//MotionProfile rightProfile = new MotionProfile(profilePoints, false);

	ArrayList<DriveMotionOperation> driveList = MotionProfileLibrary.SimpleOperationTest();
	/** some example logic on how one can manage an MP */
	//MotionProfileLibrary MotionProfiles = new MotionProfileLibrary();
    
    
	//MotionProfileExample _example = new MotionProfileExample(_talon, leftProfile);
	//MotionProfileExample _example2 = new MotionProfileExample(_talon2, rightProfile);
	
	/** joystick for testing */
	Joystick _joy= new Joystick(0);
	

	
	/** cache last buttons so we can detect press events.  In a command-based project you can leverage the on-press event
	 * but for this simple example, lets just do quick compares to prev-btn-states */
	boolean [] _btnsLast = {false,false,false,false,false,false,false,false,false,false};

	double errorThreshold = 0.01;
	public String currentOperationName = "";
	

	public Robot() { // could also use RobotInit()
		
		
//		ArrayList<ProfilePoint> Points = profileLibrary.Drive480(24,  2.5);
//		for (int i=0; i < Points.size(); i++)
//		{
//			System.out.println("{" + Points.get(i).RotationalPosition + "," + Points.get(i).Velocity+ "," + Points.get(i).Duration + "},");
//		}
		
		//navx = new AHRS(SerialPort.Port.kMXP);
		navx = new AHRS(SPI.Port.kMXP); 
		
		_talon.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		_talon.configEncoderCodesPerRev(250);
		_talon2.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		_talon2.configEncoderCodesPerRev(250);
		_talon2.setInverted(true);
		
		_talon3.changeControlMode(CANTalon.TalonControlMode.Follower);
		_talon4.changeControlMode(CANTalon.TalonControlMode.Follower);
		
		
		_talon.setF(1.5345);
	    _talon.setP(10);
	    _talon.setI(.1); 
	    _talon.setD(0);
	    _talon.setMotionMagicCruiseVelocity(200.0);
	    _talon.setMotionMagicAcceleration(400.0);
	    
	    _talon2.setF(1.5345);
	    _talon2.setP(10);
	    _talon2.setI(.1); 
	    _talon2.setD(0);
	    _talon2.setMotionMagicCruiseVelocity(200.0);
	    _talon2.setMotionMagicAcceleration(400.0);
	    
		_talon3.set(_talon.getDeviceID());
		_talon4.set(_talon2.getDeviceID());
		
		


	}
	

	
	/**  function is called periodically during operator control */
    public void teleopPeriodic() {
		/* get buttons */
		boolean [] btns= new boolean [_btnsLast.length];
		for(int i=1;i<_btnsLast.length;++i)
			btns[i] = _joy.getRawButton(i);

		/* get the left joystick axis on Logitech Gampead */
		double leftYjoystick = -1 * _joy.getY(); /* multiple by -1 so joystick forward is positive */
		double rightYjoystick = -1 * _joy.getRawAxis(5) ; /* multiple by -1 so joystick forward is positive */

		/* call this periodically, and catch the output.  Only apply it if user wants to run MP. */
		//_example.control();
		//_example2.control();
		
//		if(_joy.getRawButton(6)) {
//			_talon.changeControlMode(TalonControlMode.Voltage);
//			_talon.set(10);
//			System.out.println("Hi");
//		}
		if (btns[4] == true)
		{
			_talon.setPosition(0.0);
			_talon2.setPosition(0.0);
			driveList.clear();
			driveList = MotionProfileLibrary.SimpleOperationTest();
			//navx.reset();
		}
		
		if (btns[1])
		{
			if (_talon.getControlMode() !=  TalonControlMode.MotionMagic)
			{
				_talon.changeControlMode(TalonControlMode.MotionMagic);
				_talon2.changeControlMode(TalonControlMode.MotionMagic);
			}

			
			
			double navxAngle = navx.getAngle();
			//
//			_talon.setMotionMagicCruiseVelocity(200.0);
//			_talon2.setMotionMagicCruiseVelocity(263.3333332);
//			
//			_talon.setMotionMagicAcceleration(400.0);
//			_talon2.setMotionMagicAcceleration(526.6666664);
			//SetRotationProfile(90.0, 1.7,  60.0, 19, true, 200 );
			
			//boolean clockWise = false;
			//boolean centerRotate = true;
			// currentOperation = null;
			DriveMotionOperation currentOperation = null;
			for (int i=0; i < driveList.size(); i++)
			{
				if (driveList.get(i).OperationFinished == false)
				{
					if (currentOperationName != driveList.get(i).OperationName)
					{
						_talon.setPosition(0.0);
						_talon2.setPosition(0.0);
						System.out.println("Operation Has Changed. PreviousOperation:" +  currentOperationName);
						System.out.println("NewOperation:" +  driveList.get(i));
						currentOperationName = driveList.get(i).OperationName;
						System.out.println("Raw Angle:" +  navx.getAngle());
						System.out.println("-------------------");
						//SmartDashboard.putNumber("Raw Angle", navx.getAngle());
					}
					
					currentOperation = driveList.get(i);
					break;
				}
			}
			


			if (currentOperation != null)
			{
				SmartDashboard.putString("CurrentOperationIsNULL", "FALSE");
				double talon1Error = 0.0;
				double talon2Error = 0.0;
				if (currentOperation.driveOperationType == DriveOperationType.Rotation)
				{
					RotationParameters rotationParams = MotionProfileLibrary.getRotationParameters(currentOperation.targetAngle, 
																								   wheelRadiusInches,  
																								   currentOperation.turnRadiusInches,
																								   wheelSeparationInches, 
																								   currentOperation.rpm, 
																								   currentOperation.Clockwise,
																								   currentOperation.rotateCenter );
					_talon.setMotionMagicCruiseVelocity(rotationParams.innerVelocity);
					_talon2.setMotionMagicCruiseVelocity(rotationParams.outerVelocity);
					
					_talon.setMotionMagicAcceleration(rotationParams.innerAcceleration);
					_talon2.setMotionMagicAcceleration(rotationParams.outerAcceleration);
					
					_talon.set(rotationParams.innerWheelRotations);
					_talon2.set(rotationParams.outerWheelRotations);
					talon1Error = Math.abs(rotationParams.innerWheelRotations - _talon.getPosition());
					talon2Error = Math.abs(rotationParams.outerWheelRotations - _talon2.getPosition());
				}
				else if (currentOperation.driveOperationType == DriveOperationType.Drive)
				{
					DriveParameters driveParams = MotionProfileLibrary.getDriveParameters(wheelRadiusInches, 
																					      currentOperation.driveDistanceInches, 
																					      currentOperation.rpm, 
																					      currentOperation.Reverse);
					
					_talon.setMotionMagicCruiseVelocity(driveParams.maxVelocity);
					_talon2.setMotionMagicCruiseVelocity(driveParams.maxVelocity);
					
					_talon.setMotionMagicAcceleration(driveParams.maxAcceleration);
					_talon2.setMotionMagicAcceleration(driveParams.maxAcceleration);
					
					_talon.set(driveParams.leftWheelRotations);
					_talon2.set(driveParams.rightWheelRotations);
					
					talon1Error = Math.abs(driveParams.leftWheelRotations - _talon.getPosition());
					talon2Error = Math.abs(driveParams.rightWheelRotations - _talon2.getPosition());
				}
			   

				//int talon1CloseLoopError = Math.abs(_talon.getClosedLoopError());
				//int talon2CloseLoopError = Math.abs(_talon2.getClosedLoopError());
				
				  //SmartDashboard.putNumber("talon:ClosedLoopError:", talon1CloseLoopError);
				  //SmartDashboard.putNumber("talon2:ClosedLoopError:", talon2CloseLoopError);
				  SmartDashboard.putNumber("talon1Error:", talon1Error);
				  SmartDashboard.putNumber("talon2Error:", talon2Error);


				
				//TODO: Case where closedLoopError is already zero, even when nothing has been done.
				if (!currentOperation.OperationStarted && (talon1Error > 0 || talon2Error > 0))
				{
					currentOperation.OperationStarted = true;
					System.out.println("currentOperation.OperationName:" + currentOperation.OperationName);
				}				
				
				//System.out.println("currentOperation.OperationStarted:" + currentOperation.OperationStarted + "    talon1CloseLoopError:" + talon1CloseLoopError + "     talon2CloseLoopError:" + talon2CloseLoopError +   "     errorThreshold:" + errorThreshold);
				//System.out.println("currentOperation.OperationStarted:" + currentOperation.OperationStarted + "    talon1Error:" + talon1Error + "     talon2Error:" + talon2Error +   "     errorThreshold:" + errorThreshold);
				
				if (currentOperation.OperationStarted && talon1Error < errorThreshold && talon2Error < errorThreshold)
				{
					currentOperation.OperationFinished = true;
					System.out.println("currentOperation.OperationFinished:" + currentOperation.OperationFinished);
					//_talon.setPosition(0.0);
					//_talon2.setPosition(0.0);
				}
				
				SmartDashboard.putBoolean("currentOperation.OperationStarted:", currentOperation.OperationStarted);
				SmartDashboard.putBoolean("currentOperation.OperationFinished:", currentOperation.OperationFinished);
				
			}
			else if (currentOperation == null)
			{
				SmartDashboard.putString("CurrentOperationIsNULL", "TRUE");
				//_talon.setPosition(0.0);
				//_talon2.setPosition(0.0);
			}

			

			
//			_talon.getClosedLoopError();
//			_talon.getCloseLoopRampRate();
//			_talon.getEncVelocity();
//			_talon.getError();
//			_talon.getExpiration();
//			_talon.getMotionProfileStatus(motionProfileStatus);
//			_talon.getMotionProfileTopLevelBufferCount();
			

			
			
			//_talon.set(8.267);
			//_talon2.set(-10.885);
			

			//SmartDashboard.putInt(key, value);
			
			

			
		}		
		else if (btns[5] == false) { /* Check button 5 (top left shoulder on the logitech gamead). */
			/*
			 * If it's not being pressed, just do a simple drive.  This
			 * could be a RobotDrive class or custom drivetrain logic.
			 * The point is we want the switch in and out of MP Control mode.*/
		
			/* button5 is off so straight drive */
			_talon.changeControlMode(TalonControlMode.Voltage);
			_talon2.changeControlMode(TalonControlMode.Voltage);
			
	
			_talon.set(12.0 * leftYjoystick);
			_talon2.set(12.0 * rightYjoystick);
			
			

			//_example.reset();
			//_example2.reset();
		} else {
			/* Button5 is held down so switch to motion profile control mode => This is done in MotionProfileControl.
			 * When we transition from no-press to press,
			 * pass a "true" once to MotionProfileControl.
			 */
			
			
				_talon.changeControlMode(TalonControlMode.MotionMagic);
				_talon2.changeControlMode(TalonControlMode.MotionMagic);
			
			

			/* if btn is pressed and was not pressed last time,
			 * In other words we just detected the on-press event.
			 * This will signal the robot to start a MP */
			if( (btns[6] == true) && (_btnsLast[6] == false) )
			{
				/* user just tapped button 6 */
				//_talon2.setInverted(true);				

				
				_talon.set(0.0);
				_talon2.set(0.0);
	
							

			}
			//_talon.changeControlMode(TalonControlMode.Voltage);
			//_talon2.set(10.0);
		}

		/* save buttons states for on-press detection */
		for(int i=1;i<10;++i)
			_btnsLast[i] = btns[i];

	}
	/**  function is called periodically during disable */
	public void disabledPeriodic() {
		/* it's generally a good idea to put motor controllers back
		 * into a known state when robot is disabled.  That way when you
		 * enable the robot doesn't just continue doing what it was doing before.
		 * BUT if that's what the application/testing requires than modify this accordingly */
		_talon.changeControlMode(TalonControlMode.PercentVbus);
		_talon.set( 0 );
		_talon2.changeControlMode(TalonControlMode.PercentVbus);
		_talon2.set( 0 );

		/* clear our buffer and put everything into a known state */
		//_example.reset();
		//_example2.reset();
	}
}
