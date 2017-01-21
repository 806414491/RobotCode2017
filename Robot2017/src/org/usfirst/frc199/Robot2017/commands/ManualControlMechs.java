package org.usfirst.frc199.Robot2017.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc199.Robot2017.Robot;

/**
 *
 */
public class ManualControlMechs extends Command {
	public ManualControlMechs() {
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	
	
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	String system = SmartDashboard.getString("Manual Control Mech");
    	switch (system.toLowerCase()){
	    	case "intake": Robot.intake.runIntake(Robot.oi.manipulator.getThrottle());
	    		break;
	    	case "feeder": Robot.shooter.runFeederMotor(Robot.oi.manipulator.getThrottle());
	    		break;
	    	case "climber": Robot.climber.runClimber(Robot.oi.manipulator.getThrottle());
	    		break;
	    	case "shooter": Robot.shooter.runShootMotor(Robot.oi.manipulator.getThrottle());
	    		break;
	    	case "turret": Robot.shooter.runTurretMotor(Robot.oi.manipulator.getThrottle());
	    		break;
	    	case "hood": Robot.shooter.runHoodMotor(Robot.oi.manipulator.getThrottle());
	    		break;
    	}
    }

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		String system = SmartDashboard.getString("Manual Control Mech");
		
    	switch (system.toLowerCase()){
	    	case "intake": Robot.intake.runIntake(0);
	    		break;
	    	case "feeder": Robot.shooter.runFeederMotor(0);
	    		break;
	    	case "climber": Robot.climber.runClimber(0);
	    		break;
	    	case "shooter": Robot.shooter.runShootMotor(0);
	    		break;
	    	case "turret": Robot.shooter.runTurretMotor(0);
    			break;
	    	case "hood": Robot.shooter.runHoodMotor(0);
	    		break;
    	}
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
