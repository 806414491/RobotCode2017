package org.usfirst.frc199.Robot2017.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc199.Robot2017.Robot;
import org.usfirst.frc199.Robot2017.subsystems.DrivetrainInterface;

/**
 *
 */
public class AutoDriveForGear extends Command {

	DrivetrainInterface drivetrain;
	boolean angleDone = false;
	Timer tim = new Timer();
	double noResetPeriod = Robot.getPref("AlignGear vision update period", 0.02);

	public AutoDriveForGear(DrivetrainInterface drivetrain) {
		requires(Robot.drivetrain);
		this.drivetrain = drivetrain;
	}

	// Called just before this Command runs the first time
	public void initialize() {
		drivetrain.resetEncoder();
		drivetrain.resetGyro();
		drivetrain.setDistanceTarget(Robot.vision.getDistanceToGear());
		drivetrain.setAngleTarget( Robot.vision.getAngleToGear());
		tim.start();
		tim.reset();

	}

	// Called repeatedly when this Command is scheduled to run
	public void execute() {
		//update targets with new/current vision values
		//reset encoders/gyro w/o reseting totalError
		if(!angleDone){
			angleDone = drivetrain.angleReachedTarget();
			drivetrain.updateAnglePID();
			drivetrain.resetEncoder();
		} else if(!drivetrain.distanceReachedTarget()){
			drivetrain.updateDistancePID();
		}
		
		if(tim.get() > noResetPeriod) {
			drivetrain.getAnglePID().setTargetNotTotError(Robot.vision.getAngleToGear());
			drivetrain.resetGyro();
			drivetrain.getDistancePID().setTargetNotTotError(Robot.vision.getDistanceToGear());
			drivetrain.resetEncoder();
		}
		
//		if(!drivetrain.angleReachedTarget())
//			drivetrain.updateAnglePID();
//		else if(!drivetrain.distanceReachedTarget())
//			drivetrain.updateDistancePID();
		
		if (drivetrain.currentControl()) {
			drivetrain.shiftGears();
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	public boolean isFinished() {
		//don't need to check if angle reached target bc dist will only be reached once angle is
		return drivetrain.distanceReachedTarget();
	}

	// Called once after isFinished returns true
	public void end() {
		drivetrain.stopDrive();
		tim.reset();
		tim.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
