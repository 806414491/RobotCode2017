package org.usfirst.frc199.Robot2017.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc199.Robot2017.Robot;
import org.usfirst.frc199.Robot2017.RobotMap;
import org.usfirst.frc199.Robot2017.subsystems.ShooterInterface;

/**
 * 
 */
public class AutoShoot extends Command {
	double target;
	double angle;
	Timer tim = new Timer();
	double duration;
	ShooterInterface shooter;

	/**
	 * Figures out speed at which to run motors (with static hood) for provided
	 * distance to boiler. Uses ShooterPID to run motor at that speed for given
	 * duration. Runs feeder motors while the shooter motor is with in 5% of the
	 * target speed.
	 * 
	 * @param targetDistance
	 *            - distance to the boiler
	 * @param runTime
	 *            - duration to run the shooter motor
	 */

	public AutoShoot(double targetDistance, double runTime, ShooterInterface shooter) {
		this.shooter = shooter;
		target = this.shooter.convertDistanceToTargetVelocity(targetDistance);
		angle = this.shooter.convertDistanceToTargetAngle(targetDistance);
		duration = runTime;
	}

	// Called just before this Command runs the first time
	public void initialize() {
		tim.start();
		shooter.setShooterPIDTarget(target);
		shooter.setHoodPIDTarget(angle);
	}

	// Called repeatedly when this Command is scheduled to run
	public void execute() {
		shooter.updateShooterPID(shooter.getShootEncoderRate());
		if (!shooter.shooterMotorStalled()) {
			shooter.runShootMotor(shooter.getShooterPIDOutput());
		}
		if (Math.abs(shooter.getShootEncoderRate() - target) <= Robot.getPref("speedErrorConstant", .05)
				* target) {
			shooter.runFeederMotor(1);
		}
		shooter.updateHoodPID(shooter.getHoodEncoder());
		shooter.runHoodMotor(shooter.getHoodPIDOutput());
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return (tim.get() >= duration);
	}

	// Called once after isFinished returns true
	public void end() {
		shooter.stopShootMotor();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
