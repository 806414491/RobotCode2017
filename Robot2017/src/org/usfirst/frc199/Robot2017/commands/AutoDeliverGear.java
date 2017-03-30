package org.usfirst.frc199.Robot2017.commands;

import org.usfirst.frc199.Robot2017.Robot;
import org.usfirst.frc199.Robot2017.subsystems.DrivetrainInterface;
import org.usfirst.frc199.Robot2017.subsystems.IntakeInterface;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoDeliverGear extends CommandGroup {

    public AutoDeliverGear(DrivetrainInterface drivetrain, IntakeInterface intake) {
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
    	
    	WaypointAndHeading w = new WaypointAndHeading();
    	
//    	addSequential(new ToggleIntake(true, false, intake));
//
//    	addSequential(new ToggleIntake(true, true, Robot.intake));
    	
    	//finds waypoint
    	addSequential(new SetWaypointAndHeadingToAlignWithPeg(w));
    	
    	//turns to waypoint
    	addSequential(new TurnToWaypoint(w,drivetrain));
    	
    	//drives to waypoint
    	addSequential(new DriveToWaypoint(w,drivetrain));
    	
    	//turns to heading
    	addSequential(new TurnToHeading(w,drivetrain));
    	
    	//finds peg
//    	addSequential(new SetWaypointToPeg(w));
    	
    	//turns towards the peg
    	//addSequential(new TurnToWaypoint(w,drivetrain));
    	
    	//drives to the peg
//    	addSequential(new DriveToWaypoint(w,drivetrain));
    	addSequential(new AutoDrive(Robot.getPref("StandoffDistance", 44), 0, Robot.drivetrain));
    	
    	//waits for gear to be removed
    	addSequential(new AutoWaitForGearRemoval(intake));
    	
    	//TODO: add functionality for shooting
    }
}
