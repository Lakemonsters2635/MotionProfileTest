package org.usfirst.frc.team2635.robot;

public class ProfilePoint {
	
	public double RotationalPosition;
	public double Velocity; 
	public int Duration; 
	
	public ProfilePoint(double position, double velocity, int duration)
	{
		this.RotationalPosition = position;
		this.Velocity = velocity;
		this.Duration = duration;
	}

}
