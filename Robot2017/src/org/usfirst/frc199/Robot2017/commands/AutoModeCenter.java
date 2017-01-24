package org.usfirst.frc199.Robot2017.commands;

import org.usfirst.frc199.Robot2017.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * This is to be used if starting at center of alliance wall
 * 
 * Summary:
 * The robot drives to the lift, loads gear and shoots at boiler,
 * backs out and drives away from the boiler, turns toward the 
 * center of the field and drives forward
 * 
 */
public class AutoModeCenter extends CommandGroup {
	
	/***
	 * Commands for autonomous starting at center
	 * @param alliance true for red, false for blue 
	 */
    public AutoModeCenter(boolean alliance) {
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
    	
    	final double ROBOT_LENGTH = 36.875; //in.
    	final double ROBOT_WIDTH = 34.75; //in
    	final double DIST_TO_LIFT = 114.3; //in. from alliance wall to lift (approx.)
    	final double DIVIDER_DEPTH = 21.5; //in. dividers protrude from the airship toward alliance wall (approx.)
    	final double AIRSHIP_DIAGONAL = 80.07;//in. from corner to corner of airship;
    	final double LEFT = -1;
    	final double RIGHT = 1;
    
		//Drives to lift and aligns
    	addSequential(new AutoDrive(DIST_TO_LIFT - ROBOT_LENGTH, 0, Robot.drivetrain));
    	addSequential(new AutoAlignGear());
    	
    	//Aims and shoots
    	addParallel(new VisionAssistedShoot(0,0));
    	addSequential(new AutoDelay(5));
    	
    	//Backs out of dividers, giving 6 inches of extra space for the pivot
    	addSequential(new AutoDrive(0-(DIVIDER_DEPTH + 6),0, Robot.drivetrain));
    	
    	//Turns away from boiler
    	if(alliance)
    	{
    		addSequential(new AutoDrive(0,LEFT*90, Robot.drivetrain));
    	}
    	else
    	{
    		addSequential(new AutoDrive(0,RIGHT*90, Robot.drivetrain));
    	}
    	
    	//Drives past airship
    	addSequential(new AutoDrive((AIRSHIP_DIAGONAL / 2) + 36, 0, Robot.drivetrain));
    	
    	//Turns toward center of field
    	if(alliance)
    	{
    		addSequential(new AutoDrive(0,RIGHT*90, Robot.drivetrain));
    	}
    	else
    	{
    		addSequential(new AutoDrive(0,LEFT*90, Robot.drivetrain));
    	}
    	
    	//Passes baseline
    	addSequential(new AutoDrive(DIVIDER_DEPTH + 24,0, Robot.drivetrain));
	
    		
    }
    	
    
    	
    	
    
}
