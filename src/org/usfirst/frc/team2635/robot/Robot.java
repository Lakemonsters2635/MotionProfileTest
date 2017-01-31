
package org.usfirst.frc.team2635.robot;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
public class Robot extends IterativeRobot {

	/** The Talon we want to motion profile. */
	CANTalon _talon = new CANTalon(9);
	CANTalon _talon2 = new CANTalon(4);

	/** some example logic on how one can manage an MP */
	MotionProfileExample _example = new MotionProfileExample(_talon, "Left", true);
	MotionProfileExample _example2 = new MotionProfileExample(_talon2, "Right", false);
	
	/** joystick for testing */
	Joystick _joy= new Joystick(0);

	/** cache last buttons so we can detect press events.  In a command-based project you can leverage the on-press event
	 * but for this simple example, lets just do quick compares to prev-btn-states */
	boolean [] _btnsLast = {false,false,false,false,false,false,false,false,false,false};


	public Robot() { // could also use RobotInit()
		
		
		_talon.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		_talon.configEncoderCodesPerRev(250);
		_talon2.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		_talon2.configEncoderCodesPerRev(250);
		//_talon2.setInverted(true);
		
		_talon.setF(5.0);
	    _talon.setP(0.05);
	    _talon.setI(0); 
	    _talon.setD(0);
	    
	    _talon2.setF(5.0);
	    _talon2.setP(0.05);
	    _talon2.setI(0); 
	    _talon2.setD(0);
	}
	/**  function is called periodically during operator control */
    public void teleopPeriodic() {
		/* get buttons */
		boolean [] btns= new boolean [_btnsLast.length];
		for(int i=1;i<_btnsLast.length;++i)
			btns[i] = _joy.getRawButton(i);

		/* get the left joystick axis on Logitech Gampead */
		double leftYjoystick = -1 * _joy.getY(); /* multiple by -1 so joystick forward is positive */

		/* call this periodically, and catch the output.  Only apply it if user wants to run MP. */
		_example.control();
		_example2.control();
		
//		if(_joy.getRawButton(6)) {
//			_talon.changeControlMode(TalonControlMode.Voltage);
//			_talon.set(10);
//			System.out.println("Hi");
//		}
		
		if (btns[5] == false) { /* Check button 5 (top left shoulder on the logitech gamead). */
			/*
			 * If it's not being pressed, just do a simple drive.  This
			 * could be a RobotDrive class or custom drivetrain logic.
			 * The point is we want the switch in and out of MP Control mode.*/
		
			/* button5 is off so straight drive */
			_talon.changeControlMode(TalonControlMode.Voltage);
			_talon2.changeControlMode(TalonControlMode.Voltage);
			
	
			_talon.set(12.0 * leftYjoystick);
			_talon2.set(12.0 * leftYjoystick);

			_example.reset();
			_example2.reset();
		} else {
			/* Button5 is held down so switch to motion profile control mode => This is done in MotionProfileControl.
			 * When we transition from no-press to press,
			 * pass a "true" once to MotionProfileControl.
			 */
			
			_talon.changeControlMode(TalonControlMode.MotionProfile);
			_talon2.changeControlMode(TalonControlMode.MotionProfile);

			CANTalon.SetValueMotionProfile setOutput = _example.getSetValue();
			CANTalon.SetValueMotionProfile setOutput2 = _example2.getSetValue();
			//_talon2.setInverted(true);
					
		
			_talon.set(setOutput.value);
			_talon2.set(setOutput2.value);

			/* if btn is pressed and was not pressed last time,
			 * In other words we just detected the on-press event.
			 * This will signal the robot to start a MP */
			if( (btns[6] == true) && (_btnsLast[6] == false) ) {
				/* user just tapped button 6 */
				//_talon2.setInverted(true);				

				_example.startMotionProfile();
				_example2.startMotionProfile();
				

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
		_example.reset();
		_example2.reset();
	}
}
