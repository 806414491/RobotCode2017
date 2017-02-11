package org.usfirst.frc199.Robot2017.commands;

import org.usfirst.frc199.Robot2017.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class AutoAlignGear extends CommandGroup {

	/**
	 * @param shoot	whether to shoot or not
	 */
    public AutoAlignGear(boolean shoot) {
        while(SmartDashboard.getBoolean("Vision/gearVisionRunning", false)) {
        	addSequential(new AutoDelay(-1, Robot.intake));
        }
        addSequential(new AutoDelay(0.25, Robot.intake));
        if (SmartDashboard.getBoolean("Vision/OH-YEAH", false)) {
        	addSequential(new AutoDrive(Robot.vision.getDistanceToGear(), Robot.vision.getAngleToGear(), Robot.drivetrain));
        	if(shoot) {
        		//TODO: deal with not being able to see boiler
        		addParallel(new AutoShoot(Robot.vision.getDistanceToBoiler(), 7, Robot.shooter));
        	}
        	addSequential(new AutoDelay(0, Robot.intake));
        	addSequential(new RunShooter(0,Robot.shooter));
        }
    }

}
