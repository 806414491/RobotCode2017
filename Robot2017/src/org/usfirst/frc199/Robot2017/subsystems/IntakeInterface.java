package org.usfirst.frc199.Robot2017.subsystems;

import org.usfirst.frc199.Robot2017.DashboardInterface;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public interface IntakeInterface extends DashboardInterface {
	public void initDefaultCommand();

	public void runIntake(double speed);

	public void stopIntake();

	public void toggleIntake();
	
	public boolean intakeIsUp();

	public void stopIntakeDoubleSolenoid();
	
	public boolean intakeCurrentOverflow();
	
	public boolean gearLifted();
	
	public double getIntake();
	
	public void toggleFlipperFlapper();
	
	public void stopFlipperFlapper();
	
	public void controlledIntake(double speed);
}
