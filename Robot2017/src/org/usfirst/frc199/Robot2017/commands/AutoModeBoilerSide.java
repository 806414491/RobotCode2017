package org.usfirst.frc199.Robot2017.commands;

import org.usfirst.frc199.Robot2017.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * This is to be used if starting on the boiler side,
 * with back left corner of robot at the inside (closest to center-field) 
 * corner of tape
 * 
 * 
 * Summary:
 * The robot drives forward, turns 60 degrees to be parallel with the
 * desired lift, drives forward to the lift, auto-aligns, and shoots
 * at the boiler while the gear is taken
 **/
public class AutoModeBoilerSide extends CommandGroup {

	private boolean alliance;
	
	/***
	 * Commands for autonomous starting at left
	 * @param alliance true for red, false for blue 
	 */
    public AutoModeBoilerSide(boolean alliance) {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    	
    	this.alliance = alliance;
    	
    	final double ROBOT_LENGTH = 36.875; //in.
    	final double ROBOT_WIDTH = 34.75; //in
    	final double LEFT = -1;
    	final double RIGHT = 1;
    	final double LENGTH_1 = 95.742; //in. from front end of robot to point on field
    	final double LENGTH_2 = 23.5; //in. from front of robot to lift (after pivot)
    	
    	//Drives to airlift
    	addSequential(new AutoDrive(LENGTH_1, 0, Robot.drivetrain));
    	
    	//Turns toward lift
    	if(alliance)
    	{
    		addSequential(new AutoDrive(0,LEFT*60, Robot.drivetrain));
    	}
    	else
    	{
    		addSequential(new AutoDrive(0,RIGHT*60, Robot.drivetrain));
    	}
    	
    	//drives up to lift and aligns
    	addSequential(new AutoDrive(LENGTH_2, 0, Robot.drivetrain));
     	addSequential(new AutoAlignGear());
     	
     	//Aims and shoots
    	addParallel(new VisionAssistedShoot(0,0));
    	addSequential(new AutoDelay(5));
    	
    	
    	
    }
}
