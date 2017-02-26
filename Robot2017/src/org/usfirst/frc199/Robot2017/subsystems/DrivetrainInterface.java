package org.usfirst.frc199.Robot2017.subsystems;

import org.usfirst.frc199.Robot2017.DashboardInterface;
import org.usfirst.frc199.Robot2017.Robot;
import org.usfirst.frc199.Robot2017.commands.TeleopDrive;
import org.usfirst.frc199.Robot2017.motion.PID;
import org.usfirst.frc199.Robot2017.subsystems.DrivetrainInterface.DriveTypes;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public interface DrivetrainInterface extends DashboardInterface {
	/**
	 * This method initializes the command used in teleop
	 */
	public void initDefaultCommand();
	
	public enum DriveTypes {
		ARCADE, TANK, DRIFT_TANK, DRIFT_ARCADE
	}
	
	/**
	 * Changes the drive type
	 */
	public void toggleDriveType();
	
	/**
	 * 
	 * @return either DriveTypes.ARCADE, DriveTypes.TANK or DriveTypes.DRIFT_TANK
	 */
	public DriveTypes getDriveType();
	
	/**
	 * Allows toggling between arcade and tank teleop driving
	 */
	public void drive();

	public void arcadeDrive(double speed, double turn);
	
	/**
	 * Accounts for drift when in arcade drive
	 * Sets each motor's respective target speed based on speed to joystick ratios
	 * */
	public void unevenArcadeDrive(double speedJoy, double turnJoy);
	
	/**
	 * Accounts for drift when in tank drive
	 * Sets each motor's respective target speed based on speed to joystick ratios
	 * */
	public void unevenTankDrive(double leftJoy, double rightJoy);

	/**
	 * Forces the robot's turn and move speed to change at a max of 5% each
	 * iteration
	 */
	public void gradualDrive();

	/**
	 * For autonomous period, drives to angle given and then to distance given.
	 */
	public void autoDrive();

	/**
	 * Just stops the robot, setting its motors to zero. Usually called after a
	 * command finishes.
	 */
	public void stopDrive();

	/**
	 * @return the distance that the robot moved relative to the encoders' last
	 *         reset
	 */
	public double getDistance();
	
	/**
	 * Gets the distancePID
	 * Only used in tests
	 * @return the distancePID
	 * */
	public PID getDistancePID();
	
	/**
	 * Sets the distance for PID target
	 * 
	 * @param targetDistance
	 *            - the target distance being set to PID
	 */
	public void setDistanceTarget(double targetDistance);

	/** 
	 * Updates and tests distancePID
	 */
	public void updateDistancePID();

	/**
	 * Checks to see if the distance PID has reached the target
	 * 
	 * @return Whether distance target has been reached
	 */
	public boolean distanceReachedTarget();
	
	/**
	 * @return the angle that the robot turned relative to the gyro's last reset
	 */
	public double getAngle();
	
	/**
	 * Gets the anglePID
	 * Only used in tests
	 * @return the anglePID
	 * */
	public PID getAnglePID();

	/**
	 * Sets the angle for PID target
	 * 
	 * @param targetAngle
	 *            - the target angle in being set to PID
	 */
	public void setAngleTarget(double targetAngle);
	
	/**
	 * Updates and tests anglePID
	 */
	public void updateAnglePID();

	/**
	 * Checks to see if the angle PID has reached the target
	 * 
	 * @return Whether angle target has been reached
	 */
	public boolean angleReachedTarget();
	
	/**
	 * @return the speed of the left side of the robot at the current time
	 */
	public double getLeftSpeed();
	
	/**
	 * Gets the leftSpeedPID
	 * Only used in tests
	 * @return the leftSpeedPID
	 * */
	public PID getLeftSpeedPID();
	
	/**
	 * Sets the left speed for PID target
	 * 
	 * @param targetSpeed
	 *            - the target left speed being set to PID
	 */
	public void setLeftSpeedTarget(double targetSpeed);

	/** 
	 * Updates and tests/runs leftDriveSpeedPID
	 */
	public void updateLeftSpeedPID();

	/**
	 * Checks to see if the left speed PID has reached the target
	 * 
	 * @return Whether left speed target has been reached
	 */
	public boolean leftSpeedReachedTarget();
	
	/**
	 * @return the speed of the right side of the robot at the current time
	 */
	public double getRightSpeed();
	
	/**
	 * Gets the rightSpeedPID
	 * Only used in tests
	 * @return the rightSpeedPID
	 * */
	public PID getRightSpeedPID();
	
	/**
	 * Sets the right speed for PID target
	 * 
	 * @param targetSpeed
	 *            - the target right speed being set to PID
	 */
	public void setRightSpeedTarget(double targetSpeed);

	/** 
	 * Updates and tests/runs rightDriveSpeedPID
	 */
	public void updateRightSpeedPID();

	/**
	 * Checks to see if the right speed PID has reached the target
	 * 
	 * @return Whether right speed target has been reached
	 */
	public boolean rightSpeedReachedTarget();

	/**
	 * @return the average speed of the two sides of the robot at the current time
	 */
	public double getVelocity();
	
	/**
	 * Sets targets for tracking velocity of robot for motion profiling
	 * 
	 * @param linVelTarget
	 * @param angVelTarget
	 */
	public void setVelocityTarget(double linVelTarget, double angVelTarget);
	
	/**
	 * Updates linear and angular velocity PIDs for motion profiling
	 */
	public void updateVelocityPIDs();
	
	/**
	 * Checks to see if the speed PID has reached the target
	 * 
	 * @return Whether speed target has been reached
	 */
	public boolean velocityReachedTarget();
	
	/**
	 * Gets the angular acceleration
	 * @return the angular acceleration
	 * */
	public double getAngularAcceleration();

	/**
	 * @return the angular velocity
	 */
	public double getAngularVelocity();
	
	/**
	 * Checks to see if the angular velocity PID has reached the target
	 *  
	 * @return Whether angular velocity PID has reached the target
	 */
	public boolean angularVelocityPIDReachedTarget();
	
	/**
	 * Uses PID to reach target velocity
	 * 
	 * @param v
	 *            - linear velocity in inches/second
	 * @param w
	 *            - angular velocity in degrees/second
	 * @param a
	 *            - acceleration in inches/second/second
	 * @param alpha
	 *            - angular acceleration in degrees/second/second
	 */
	public void followTrajectory(double v, double w, double a, double alpha);
	
	/**
	 * Converts volts from ultrasonic sensors into inches based on equation derived from testing.
	 * @return distance in inches
	 * */
	public double convertVoltsToInches(double volts);
	
	/**
	 * Gets the voltage from one of two ultrasonic sensors.
	 * @param side tells which ultrasonic sensor to pull data from (left or right)
	 * 	if side true, gets left voltage
	 * 	if side false, gets right voltage
	 * */
	public double getUSVoltage(boolean side);
	
	/**
	 * Calculates the distance the robot needs to drive to reach target distance from wall
	 * 	based on the average of the current US sensor readings.
	 * Uses the average of the readings because this is measured before the robot auto truns
	 * 	but is used to execute auto drive after the robot auto turns. The average distance from
	 * 	the wall will not change in and ideal scenario because the distance from the robot center
	 * 	will not change. Henceforth, average is used.
	 * @return the distance to be antered into a drive PID 
	 * */
	public double getUSDistToDrive();
	
	/**
	 * Calculates the angle needed to turn to to approach the wall head on.
	 * @return the angle to turn to
	 * */
	public double getUSTargetAngle();

	/**
	 * Shifts gears to whatever state they are not in
	 */
	public void shiftGears();
	
	/**
	 * Sets the shifter piston to neutral
	 * */
	public void setShifterNeutral();

	/**
	 * Monitors current draw of drivetrain
	 * 
	 * @return whether the robot should shift to low gear
	 */
	public boolean currentControl();

	/**
	 * Resets the encoders to return zero at that point
	 */
	public void resetEncoder();

	/**
	 * Resets the gyro to return zero at that point
	 */
	public void resetGyro();
}
