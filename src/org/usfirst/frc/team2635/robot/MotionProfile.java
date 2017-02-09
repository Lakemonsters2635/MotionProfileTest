package org.usfirst.frc.team2635.robot;

public class MotionProfile
{		
	// Position (rotations)	Velocity (RPM)	Duration (ms)
	public  double [][]profilePoints;

	
	public boolean Reverse;
	public String profileName;

	
	public MotionProfile(double[][] points, boolean reverse )
	{
		this.Reverse = reverse;
		this.profilePoints = points;
	}
}
