package org.usfirst.frc.team2635.robot;

public class MotionProfile
{		
	// Position (rotations)	Velocity (RPM)	Duration (ms)
	public  double [][]leftPoints;
	public  double [][]rightPoints;
	
	public boolean leftReverse;
	public boolean rightReverse;
	public boolean profileCompleted;
	public String profileName;
	public boolean  isLastProfile;
	
	public MotionProfile()
	{
		
	}
}
