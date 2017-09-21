package org.usfirst.frc199.Robot2017.subsystems;

import org.usfirst.frc199.Robot2017.DashboardInterface;
import org.usfirst.frc199.Robot2017.Robot;
import org.usfirst.frc199.Robot2017.RobotMap;
import org.usfirst.frc199.Robot2017.commands.*;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Intake extends Subsystem implements IntakeInterface {

	private final DoubleSolenoid pivotPiston = RobotMap.intakePivotPiston;
	private final DoubleSolenoid flipperFlapper = RobotMap.flipperFlapper;
	private final SpeedController intakeMotor = RobotMap.intakeIntakeMotor;

	private final AnalogInput AI = RobotMap.driverAI;

	private final PowerDistributionPanel pdp = RobotMap.pdp;
	private boolean AItriggered = false;
	private Timer tim = new Timer();
	private Timer gearStartupTimer = new Timer();
	private boolean intakeIsDown = false;
	private boolean flipperIsUp = false;
	private final SpeedController gearRoller = RobotMap.gearRoller;
	private final DigitalInput gearIntakeSwitch = RobotMap.gearIntakeSwitch;
	private final DoubleSolenoid flashLED = RobotMap.flashingGearLED;
	private double maxCurrent = 0;

	public Intake() {
		super();
		putString("~TYPE~", "Intake");
	}

	public void initDefaultCommand() {
		setDefaultCommand(new FlashLED(Robot.intake));
	}

	public void LEDOn() {
		if (!haveGear())
			flashLED.set(DoubleSolenoid.Value.kOff);
		else
			LEDOff();
	}

	public void LEDOff() {
		flashLED.set(DoubleSolenoid.Value.kForward);
	}

	/**
	 * Returns the current value of the intakeMotor
	 */
	public double getIntake() {
		return intakeMotor.get();
	}

	/**
	 * Rruns the intake motor at a set speed
	 * 
	 * @param speed - the speed you want the intake motor to run at -1 -> 1
	 */
	public void runIntake(double speed) {
		intakeMotor.set(speed);
		
	}

	/**
	 * Runs intake at a speed based on drive speed
	 */
	public void controlledIntake(boolean isBackwards) {
		if (isBackwards) {
			intakeMotor.set(-1);
		} else {
			intakeMotor.set((Robot.getPref("intakeDirection", 1)) * (0.5 * Robot.drivetrain.getAverageMotors() + 0.8));
		}
	}

	/**
	 * Returns whether or not the pdp detects the intake drawing more current
	 * than allowed
	 */
	public boolean intakeCurrentOverflow() {
		int channel = (int) (Robot.getPref("Intake PDP channel", 1));
		double current = pdp.getCurrent(channel);
		if (current >= Robot.getPref("maxIntakeCurrent", 40))
			return true;
		return false;
	}

	/**
	 * Stops the intake motor
	 */
	public void stopIntake() {
		intakeMotor.set(0);
	}

	/**
	 * Runs the gearRoller a certain direction
	 * 
	 * @param speed -1 for kreverse, 0 for koff, and 1 for kforward
	 */

	public void runRoller(double speed) {
		gearStartupTimer.reset();
		gearStartupTimer.start();
		if(Math.abs(speed) > 0.1) maxCurrent = 0;
		gearRoller.set(speed);
	}

	/**
	 * Gets if the gear is in the gear intake, pushing either switch
	 * 
	 * @return if the gear intake limit switches are pushed
	 */
	public boolean getSwitch() {
		return gearIntakeSwitch.get();
	}

	/**
	 * Moves the intake up if it is down, and vice versa, or moves intake in
	 * specified direction
	 * 
	 * @param giveDirection - Is intake direction given? If not, just toggle.
	 * @param down - If giveDirection, should the intake go down? If not
	 *            giveDirection, doesn't matter
	 */
	public void toggleIntake(boolean giveDirection, boolean down) {
		if (giveDirection) {
			if (down) {
				pivotPiston.set(DoubleSolenoid.Value.kReverse);
			} else {
				pivotPiston.set(DoubleSolenoid.Value.kForward);
			}
			intakeIsDown = down;
		} else { 
			if (intakeIsDown) {
				pivotPiston.set(DoubleSolenoid.Value.kForward);
			} else {
				pivotPiston.set(DoubleSolenoid.Value.kReverse);
			}
			intakeIsDown = !intakeIsDown;
		}
	}
	
	public void toggleIntake() {
		toggleIntake(false, false);
//		if (!intakeIsDown) {
//			pivotPiston.set(DoubleSolenoid.Value.kReverse);
//		} else {
//			pivotPiston.set(DoubleSolenoid.Value.kForward);
//		}
//		intakeIsDown = !intakeIsDown;
	}

	public void raiseIntake() {
		pivotPiston.set(DoubleSolenoid.Value.kForward);
		intakeIsDown = false;
	}

	public void lowerIntake() {
		pivotPiston.set(DoubleSolenoid.Value.kReverse);
		intakeIsDown = true;
	}

	/**
	 * Sets the pivot piston to neutral
	 */
	public void setIntakePistonNeutral() {
		pivotPiston.set(DoubleSolenoid.Value.kOff);
	}

	/**
	 * @return if the intake is up or not
	 */
	public boolean intakeIsDown() {
		return intakeIsDown;
	}

	/**
	 * Sets flipperFlapper to forward unless it already is, then sets to
	 * backwards
	 */
	public void toggleFlipperFlapper() {
		if (!flipperIsUp) {
			// shifts flipper to down
			flipperFlapper.set(DoubleSolenoid.Value.kReverse);
			flipperIsUp = true;
		} else {
			// shifts flipper to up
			flipperFlapper.set(DoubleSolenoid.Value.kForward);
			flipperIsUp = false;
		}
	}
	
	public void raiseFlipperFlapper() {
		//extend dead stop
		flipperFlapper.set(DoubleSolenoid.Value.kReverse);
		flipperIsUp = true;
	}

	public void lowerFlipperFlapper() {
		//retract dead stop
		flipperFlapper.set(DoubleSolenoid.Value.kForward);
		flipperIsUp = false;
	}

	/**
	 * Sets the flipperFlapper to neutral
	 */
	public void setFlipperFlapperNeutral() {
		flipperFlapper.set(DoubleSolenoid.Value.kOff);
	}

	/**
	 * @return if the gear has been lifted or not
	 */
	public boolean gearLifted(boolean isTriggered) {
		// return if gear lifted or not
		if (AI.getVoltage() > 0.15) {
			tim.reset();
			tim.start();
		}
		AItriggered = (tim.get() > 2);
		if (isTriggered) {
			return AItriggered;
		} else {
			return (AI.getVoltage() > 0.15);
		}
		// if (AI.getVoltage() > 0.15) {
		// AItriggered = true;
		// tim.reset();
		// tim.start();
		// return true;
		// } else {
		// if(tim.get() > 5) AItriggered = false;
		// return false;
		// }
	}

	/*
	 * Resets the light sensor trigger value
	 */
	public void resetAITrigger() {
		AItriggered = false;
	}

	@Override
	/**
	 * Displays data to SmartDashboard
	 */
	public void displayData() {
		if(pdp.getCurrent((int) Robot.getPref("Intake PDP channel", 1)) > Robot.getPref("gearInCurrent", 38)) {
			putBoolean("iCanHazGear", true);
		}

		putString("Flap piston status", flipperFlapper.get().toString());
		putNumber("Intake current draw", pdp.getCurrent((int) Robot.getPref("Intake PDP channel", 2)));
//		putNumber("Sending to intake", getIntake());
		putString("Intake piston status", pivotPiston.get().toString());
		putBoolean("Intake is down", intakeIsDown);
		putBoolean("Gear has been lifted", gearLifted(false));
		putBoolean("Gear current test", haveGear());
		putNumber("Peg sensor reading", AI.getVoltage());
		putBoolean("Peg sensor has triggered", AItriggered);
		putNumber("Intake current draw", pdp.getCurrent(2));
		if(pdp.getCurrent((int) Robot.getPref("Intake PDP channel", 1)) > maxCurrent && gearStartupTimer.get() > 0.75) 
			maxCurrent = pdp.getCurrent((int) Robot.getPref("Intake PDP channel", 1));
		putNumber("Intake max current", maxCurrent);
		putNumber("Gear timer", gearStartupTimer.get());
		putNumber("Roller output", gearRoller.get());
	}

	@Override
	public boolean haveGear() {
		return pdp.getCurrent((int) Robot.getPref("Intake PDP channel", 1)) > Robot.getPref("gearInCurrent", 38) 
				&& gearStartupTimer.get() > 0.75;
	}
}
