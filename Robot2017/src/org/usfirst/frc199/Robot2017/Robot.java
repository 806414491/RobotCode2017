package org.usfirst.frc199.Robot2017;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.ArrayList;

import org.usfirst.frc199.Robot2017.DashboardInterface;
import org.usfirst.frc199.Robot2017.commands.*;
import org.usfirst.frc199.Robot2017.subsystems.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	Command autonomousCommand;

	public static OI oi;
	public static ArrayList<DashboardInterface> subsystems = new ArrayList<>();
	public static Drivetrain drivetrain;
	public static Intake intake;
	public static Shooter shooter;
	public static Climber climber;
	public static Vision vision;
//	private DisplayDashboardData display;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		RobotMap.init();
		drivetrain = new Drivetrain();
		intake = new Intake();
		shooter = new Shooter();
		climber = new Climber();
		vision = new Vision();
//		display = new DisplayDashboardData();

		// OI must be constructed after subsystems. If the OI creates Commands
		// (which it very likely will), subsystems are not guaranteed to be
		// constructed yet. Thus, their requires() statements may grab null
		// pointers. Bad news. Don't move it.

		subsystems.add(drivetrain);
		subsystems.add(intake);
		subsystems.add(shooter);
		subsystems.add(climber);
		subsystems.add(vision);
		oi = new OI();
		for(DashboardInterface s: Robot.subsystems) {
    		if(!s.getKey("").substring(0, 4).equals("PID/")) {
    			s.putString("~TYPE~", "SubSystem");
    		}
    	}
        SmartDashboard.putData(Scheduler.getInstance());
	}

	/**
	 * This function is called when the disabled button is hit. You can use it
	 * to reset subsystems before shutting down.
	 */
	public void disabledInit() {

	}

	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	public void autonomousInit() {
		// Schedule the autonomous command based on the widget
		boolean blueAlliance = Robot.getPref("Blue", true);
		switch (Robot.getPref("Auto location", "Dead reckoning")) {
		case "Left":
			if (blueAlliance) {
				autonomousCommand = new AutoModeBoilerSide(blueAlliance);
			} else {
				autonomousCommand = new AutoModeLoadSide(!blueAlliance);
			}
			break;
		case "Right":
			if (blueAlliance) {
				autonomousCommand = new AutoModeLoadSide(blueAlliance);
			} else {
				autonomousCommand = new AutoModeBoilerSide(!blueAlliance);
			}
			break;
		case "Center":
			autonomousCommand = new AutoModeCenter(blueAlliance);
			break;
		case "Dead reckoning":
			autonomousCommand = new AutoModeBasic(Robot.getPref("Dead Reckoning Duration", 2.5));
			break;
		default:
			autonomousCommand = null;
			break;
		}

		// Start the specified autonomous command
		if (autonomousCommand != null)
			autonomousCommand.start();

		// Update all subsystem SmartDashboard values during autonomous
		new DisplayDashboardData().start();
	}

	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null)
			autonomousCommand.cancel();

		// Update all subsystem SmartDashboard values during teleop
		new DisplayDashboardData().start();
	}

	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		
		SmartDashboard.putBoolean("button 5 (forward)", oi.outputButton.get());
		SmartDashboard.putBoolean("button 7 (backward)", oi.intakeButton.get());
		SmartDashboard.putNumber("Sending to intake", intake.getIntake());
	}

	public void testPeriodic() {
		LiveWindow.run();
	}

	/**
	 * Gets a preference or creates one if it does not yet exist
	 * 
	 * @param key - The name of the preference
	 * @param defaultValue - Backup value if the preference is not found
	 * @return The value of the preference
	 */
	public static double getPref(String key, double defaultValue) {
		if (!Preferences.getInstance().containsKey(key)) {
			Preferences.getInstance().putDouble(key, defaultValue);
		}
		return Preferences.getInstance().getDouble(key, defaultValue);
	}

	public static boolean getPref(String key, boolean defaultValue) {
		if (!Preferences.getInstance().containsKey(key)) {
			Preferences.getInstance().putBoolean(key, defaultValue);
		}
		return Preferences.getInstance().getBoolean(key, defaultValue);
	}

	public static String getPref(String key, String defaultValue) {
		if (!Preferences.getInstance().containsKey(key)) {
			Preferences.getInstance().putString(key, defaultValue);
		}
		return Preferences.getInstance().getString(key, defaultValue);
	}
}
