package org.usfirst.frc199.Robot2017.commands;

import org.usfirst.frc199.Robot2017.Robot;
import org.usfirst.frc199.Robot2017.subsystems.IntakeInterface;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class RunGearRollerOut extends Command {

	IntakeInterface intake;
	Timer tim;
	
    public RunGearRollerOut(IntakeInterface intake) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	this.intake = intake;
//    	requires(Robot.intake);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	tim = new Timer();
    	tim.reset();
    	tim.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
//    	if(!intake.intakeIsDown()) intake.lowerIntake();
    	intake.runRoller(-0.2/* *Robot.getPref("RollerOutMultiplier", 1)*/);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return tim.get() > 1;
    }

    // Called once after isFinished returns true
    protected void end() {
    	intake.runRoller(0);
    	SmartDashboard.putBoolean("iCanHazGear", false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	// Removed to avoid interference from ToggleIntake
    	intake.runRoller(0);
    }
}
