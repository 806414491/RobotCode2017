package org.usfirst.frc199.Robot2017.commands;

import org.usfirst.frc199.Robot2017.Robot;
import org.usfirst.frc199.Robot2017.subsystems.DrivetrainInterface;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveBackwardsGearDelivery extends Command {
	
	private Timer tim = new Timer();
	private double duration;
	private DrivetrainInterface drivetrain;
	private double speed;
	
	public DriveBackwardsGearDelivery(double theDuration, double spdInchesPerSec, DrivetrainInterface theDrivetrain) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.drivetrain);
		duration = theDuration;
		drivetrain = theDrivetrain;
		speed = spdInchesPerSec;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		tim.reset();
		tim.start();
		drivetrain.resetEncoder();
		drivetrain.resetGyro();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
//		drivetrain.specialUnevenArcadeDrive(speed);
		drivetrain.unevenArcadeDrive(speed, 0);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return tim.get() > duration;
	}

	// Called once after isFinished returns true
	protected void end() {
		drivetrain.stopDrive();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		drivetrain.stopDrive();
	}
}
