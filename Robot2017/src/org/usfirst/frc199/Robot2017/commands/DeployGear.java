package org.usfirst.frc199.Robot2017.commands;

import org.usfirst.frc199.Robot2017.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DeployGear extends CommandGroup {

	public DeployGear(double targetDist, boolean shoot) {

		SmartDashboard.putBoolean("Gear has been lifted", false);

		addSequential(new AutoAlignGear(shoot));
		addSequential(new AutoDrive(targetDist, 0, Robot.drivetrain));

		// don't be fooled, this makes the robot wait until gearLifted()
		addSequential(new AutoDelay(0, Robot.intake));

		SmartDashboard.putBoolean("Gear has been lifted", true);
	}
}
