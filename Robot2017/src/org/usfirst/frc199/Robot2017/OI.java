// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

package org.usfirst.frc199.Robot2017;

import org.usfirst.frc199.Robot2017.commands.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.*;
import org.usfirst.frc199.Robot2017.subsystems.*;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	//// CREATING BUTTONS
	// One type of button is a joystick button which is any button on a
	//// joystick.
	// You create one by telling it which joystick it's on and which button
	// number it is.
	// Joystick stick = new Joystick(port);
	// Button button = new JoystickButton(stick, buttonNumber);

	// There are a few additional built in buttons you can use. Additionally,
	// by subclassing Button you can create custom triggers and bind those to
	// commands the same as any other Button.

	//// TRIGGERING COMMANDS WITH BUTTONS
	// Once you have a button, it's trivial to bind it to a button in one of
	// three ways:

	// Start the command when the button is pressed and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenPressed(new ExampleCommand());

	// Run the command while the button is being held down and interrupt it once
	// the button is released.
	// button.whileHeld(new ExampleCommand());

	// Start the command when the button is released and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenReleased(new ExampleCommand());

	// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
	public JoystickButton switchDrive;
	public Joystick leftJoy;
	public JoystickButton shiftGears;
	public JoystickButton driveGradually;
	public JoystickButton alignRobot; // AutoShoot
	public Joystick rightJoy;
	public JoystickButton intakeButton;
	public JoystickButton outputButton;
	public JoystickButton indexerInButton;
	public JoystickButton indexerOutButton;
	public JoystickButton shootButton; // ShootOut
	public JoystickButton winchInButton;
	public JoystickButton winchButton;
	public JoystickButton feedInButton;
	public JoystickButton feedOutButton;
	public JoystickButton toggleIntakeButton;
	public Joystick manipulator;

	// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

	public OI() {
		// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS

		manipulator = new Joystick(2);

		feedOutButton = new JoystickButton(manipulator, 4);
		feedOutButton.whileHeld(new FeederOut());
		feedInButton = new JoystickButton(manipulator, 3);
		feedInButton.whileHeld(new FeederIn());
		winchButton = new JoystickButton(manipulator, 10);
		winchButton.whileHeld(new Climb());
		shootButton = new JoystickButton(manipulator, 2);
		shootButton.whileHeld(new ShootOut());
		indexerOutButton = new JoystickButton(manipulator, 6);
		indexerOutButton.whileHeld(new IndexerOut());
		indexerInButton = new JoystickButton(manipulator, 8);
		indexerInButton.whileHeld(new IndexerIn());
		outputButton = new JoystickButton(manipulator, 5);
		outputButton.whileHeld(new IntakeOut());
		intakeButton = new JoystickButton(manipulator, 7);
		intakeButton.whileHeld(new IntakeIn());
		toggleIntakeButton = new JoystickButton(manipulator, 9);
		toggleIntakeButton.whenPressed(new ToggleIntake());
		rightJoy = new Joystick(1);

		alignRobot = new JoystickButton(rightJoy, 4);
		alignRobot.whileHeld(new AutoShoot());
		driveGradually = new JoystickButton(rightJoy, 1);
		driveGradually.whileHeld(new GradualDrive());
		shiftGears = new JoystickButton(rightJoy, 2);
		shiftGears.whenPressed(new ToggleDrivetrainShift());
		leftJoy = new Joystick(0);

		switchDrive = new JoystickButton(leftJoy, 2);
		switchDrive.whenPressed(new ToggleDriveType());

		// SmartDashboard Buttons
		SmartDashboard.putData("MainAutoMode", new MainAutoMode());
		SmartDashboard.putData("AutoDrive", new AutoDrive(0, 0));
		SmartDashboard.putData("AutoAlignAngle", new AutoAlignAngle());
		SmartDashboard.putData("AutoAlignDistance", new AutoAlignDistance());
		SmartDashboard.putData("AutoShoot", new AutoShoot());
		SmartDashboard.putData("TeleopDrive", new TeleopDrive());
		SmartDashboard.putData("GradualDrive", new GradualDrive());
		SmartDashboard.putData("ToggleDriveType", new ToggleDriveType());
		SmartDashboard.putData("ToggleDrivetrainShift", new ToggleDrivetrainShift());
		SmartDashboard.putData("TestPID", new TestPID());
		SmartDashboard.putData("IntakeIn", new IntakeIn());
		SmartDashboard.putData("IntakeOut", new IntakeOut());
		SmartDashboard.putData("IndexerIn", new IndexerIn());
		SmartDashboard.putData("IndexerOut", new IndexerOut());
		SmartDashboard.putData("FeederIn", new FeederIn());
		SmartDashboard.putData("FeederOut", new FeederOut());
		SmartDashboard.putData("TurnTurret", new TurnTurret());
		SmartDashboard.putData("AdjustHood", new AdjustHood());
		SmartDashboard.putData("ToggleIntake", new ToggleIntake());
		SmartDashboard.putData("Climb", new Climb());
		SmartDashboard.putData("ManualControlMechs", new ManualControlMechs());

		// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
	}

	// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=FUNCTIONS
	public Joystick getLeftJoy() {
		return leftJoy;
	}

	public Joystick getRightJoy() {
		return rightJoy;
	}

	public Joystick getManipulator() {
		return manipulator;
	}

	// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=FUNCTIONS
}
