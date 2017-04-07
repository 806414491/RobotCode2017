package org.usfirst.frc199.Robot2017.subsystems;

import org.usfirst.frc199.Robot2017.DashboardInterface;
import org.usfirst.frc199.Robot2017.Log;
import org.usfirst.frc199.Robot2017.Robot;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Vision extends Subsystem implements DashboardInterface {
	// inches between the centers of both reflectors (horizontal)
	private final double REFLECTOR_DIST_GEAR = 8.5; //8.5
	//degrees of offset of gear cam if not facing straight forward
	private final double GEAR_CAM_OFFSET = Robot.getPref("Gear cam angle offset", 0);
	// inches off the ground (~21.67)
	private final double SHOOTER_CAM_HEIGHT = Robot.getPref("Camera Height", 0); 
	// in from camera bottom to top of boiler tape
	private final double BOILER_HEIGHT = 88 - SHOOTER_CAM_HEIGHT; 
	// radians from north that you can see in both directions
	private final double THETA = Math.toRadians(29.85);
	// radians of the camera's vertical field of view
	private final double SHOOTER_CAM_ANGLE = Math.toRadians(35.78);
	// pixel width of image
	private final double RESOLUTION_WIDTH = 640; 
	// pixel height of image
	private final double RESOLUTION_HEIGHT = 360;
	private double SCREEN_DEPTH = RESOLUTION_HEIGHT / Math.tan(SHOOTER_CAM_ANGLE);
	private final double SCREEN_CENTER_X = RESOLUTION_WIDTH / 2;
	private final double SCREEN_CENTER_Y = RESOLUTION_HEIGHT / 2;

 	private final double PIVOT_TO_FRONT_DISTANCE = Robot.getPref("Distance from pivot point to front of robot", 0);
 	
 	public double[] leftMarkCoords = new double[2];
 	public double[] rightMarkCoords = new double[2];
 	public double[] centerMarkCoords = new double[2];


 	private final String[] gearVisionKeys = {
 		"leftGearCenterX", "leftGearCenterY", "rightGearCenterX", "rightGearCenterY", 
 		"leftGearBottomY", "leftGearTopY", "rightGearBottomY", "rightGearTopY"
 	};

 	private double[] defaultGearValues = {-1,-1,-1,-1,-1,-1,-1,-1,-1};

	public Vision() {
		super();
		putString("~TYPE~", "Vision");
	}

	public void initDefaultCommand() {

	}

	/**
 	 * @return distance from camera to the plane perpendicular to its line of sight, on which the gear lift lies
 	 */
 	public double getCameraDistanceToGearPlane() {
 		double[] gearValues = getNumArray("gearValues", defaultGearValues);

  		if (gearValues[8] == 1) {
  			double leftGearCenterX = gearValues[0];
  			double leftGearCenterY = gearValues[1];
  			double rightGearCenterX = gearValues[2];
 			double rightGearCenterY = gearValues[3];
			double pixelDist = Math.sqrt(Math.pow(rightGearCenterX - leftGearCenterX, 2) + Math.pow(rightGearCenterY - leftGearCenterY, 2));
  			double fieldOfView = (REFLECTOR_DIST_GEAR * RESOLUTION_WIDTH) / pixelDist;
  			double distanceToGear = (fieldOfView / 2) / (Math.tan(THETA));
 			return distanceToGear; 
 		} else {
 			return 0;
 		}
 	}
 	
 	public double getCamDistToGearPlane2() {
 		if (getBoolean("OH-YEAH", true)) {
  			double leftGearCenterX = getNumber("leftGearCenterX", 0); //use leftgear topx
  			double rightGearCenterX = getNumber("rightGearCenterX", 0); //use rightgear bottomx
  			double leftGearCenterY = getNumber("leftGearCenterY", 0); //use leftgear topy
 			double rightGearCenterY = getNumber("rightGearCenterY", 0); //use rightgear bottomy
			double pixelDist = Math.sqrt(Math.pow(rightGearCenterX - leftGearCenterX, 2) + Math.pow(rightGearCenterY - leftGearCenterY, 2));
  			double fieldOfView = (Math.sqrt(61) * RESOLUTION_WIDTH) / pixelDist;
  			double distanceToGear = (fieldOfView / 2) / (Math.tan(THETA));
 			return distanceToGear; 
 		} else {
 			return 0;
 		}
 	}
 	
 	/**
 	 * Accounts for camera tilt in order to calculate distance or angle to gear lift
 	 * @return distance in pixel's from relative center of screen
 	 */
 	public double getPixelDistanceToGear() {
 		if (getBoolean("OH-YEAH", true)) {
 			double pegX = (getNumber("leftGearCenterX", 0) + getNumber("rightGearCenterX", 0)) / 2;
 			double pegY = (getNumber("leftGearCenterY", 0) + getNumber("rightGearCenterY", 0)) / 2;
  			double m = (getNumber("rightGearCenterY", 0) - getNumber("leftGearCenterY", 0)) / 
  					(getNumber("rightGearCenterX", 0) - getNumber("leftGearCenterX", 0));
  	
			return ( pegX + m*pegY - m*SCREEN_CENTER_Y + SCREEN_CENTER_X ) / (m*m + 1);
  		} else {
  			return 0;
  		}
 	}
 
 	/**
 	 * @return calculated angle from camera's line of sight to gear lift
 	 */
 	public double getCameraAngleToGear() {
 
 		double pegX = (getNumber("leftGearCenterX", 0) + getNumber("rightGearCenterX", 0)) / 2;
 		double pixelDisplacement = pegX - SCREEN_CENTER_X;
 		double abstractDepth = (RESOLUTION_WIDTH / 2) / Math.tan(THETA);
 		
 		return Math.atan(pixelDisplacement / abstractDepth) +  Math.toRadians(GEAR_CAM_OFFSET);
 		
// 		double leftCamDist = getCamDistToGearMark(getNumber("leftGearBottomY", 0), getNumber("leftGearTopY", 0)); 
//		double leftCamAng = getCamAngToGearMark(getNumber("leftGearCenterX", 0));
// 		double rightCamDist = getCamDistToGearMark(getNumber("rightGearBottomY", 0), getNumber("rightGearTopY", 0)); 
//		double rightCamAng = getCamAngToGearMark(getNumber("rightGearCenterX", 0));
// 		
// 		double camX1 = leftCamDist * Math.sin(leftCamAng);
// 		double camY1 = leftCamDist * Math.cos(leftCamAng);
//
// 		double camX2 = rightCamDist * Math.sin(rightCamAng);
// 		double camY2 = rightCamDist * Math.cos(rightCamAng);
// 		
// 		return Math.atan( (camX1 + camX2)/2 / ((camY1 + camY2)/2) );
 	}
 	
 	public double getDirectionToGear() {
 		double pegX = (getNumber("leftGearCenterX", 0) + getNumber("rightGearCenterX", 0)) / 2;
 		return Integer.signum((int)(pegX - SCREEN_CENTER_X));
 	}
 	
 	/**
 	 * @return distance from front of robot to gear lift in inches
 	 */
 	public double getDistanceToGear() {

 		if (getBoolean("OH-YEAH", true)) {
//	 		double l = getCameraDistanceToGearPlane();
//	 		double theta = getCameraAngleToGear();
//	 		double x = Robot.getPref("Gear cam x distance from pivot", 0);
//	 		double y = Robot.getPref("Gear cam y distance from pivot", 0);
//	 		double r = Math.sqrt( x*x + y*y);
//	 		double psi = Math.atan(x/y);
//	 		double d = l / Math.cos(theta);
//	 		double beta = Math.PI - theta - psi;
//	 		
//	 		return Math.sqrt(r*r + d*d - 2*r*d*Math.cos(beta)) - PIVOT_TO_FRONT_DISTANCE;
 			double xGear = getXFromPivotPoint(getCameraDistanceToGearPlane(), getCameraAngleToGear());
 			double yGear = getYFromPivotPoint(getCameraDistanceToGearPlane(), getCameraAngleToGear());
 			putNumber("xGear", xGear);
 			putNumber("yGear",yGear);
// 			yGear -= PIVOT_TO_FRONT_DISTANCE;
 			return Math.sqrt(xGear*xGear + yGear*yGear) - PIVOT_TO_FRONT_DISTANCE;

  		} else {
  			return 0;
  		}
 	}
 	
 	/**
 	 * @return robot's angle to gear lift in degrees
 	 */
 	public double getAngleToGear() {
 		if (getBoolean("OH-YEAH", true)) {
	 		double l = getCameraDistanceToGearPlane();
	 		double theta = getCameraAngleToGear();
	 		double x = Robot.getPref("Gear cam x distance from pivot", 0);
	 		double y = Robot.getPref("Gear cam y distance from pivot", 0);
	 		double r = Math.sqrt( x*x + y*y);
	 		double psi = Math.atan(x/y);
	 		double d = l / Math.cos(theta);
	 		double beta = Math.PI - theta - psi;
	 		double D = Math.sqrt( r*r + d*d - 2*r*d*Math.cos(beta));  
	 		return Math.toDegrees(Math.asin(d*Math.sin(beta)/D) - psi);
// 			double xGear = getXFromPivotPoint(getCameraDistanceToGearPlane(), getCameraAngleToGear());
// 			double yGear = getYFromPivotPoint(getCameraDistanceToGearPlane(), getCameraAngleToGear());
// 			return Math.toDegrees(Math.atan(xGear/yGear));

		} else {
			return 0;
		}
 	}
 	
 	public double getXFromPivotPoint(double camDist, double camAng) {
 		double camX = camDist * Math.sin(camAng);
 		return camX + Robot.getPref("Gear cam x distance from pivot", 0);
 	}
 	
 	public double getYFromPivotPoint(double camDist, double camAng) {
 		double camY = camDist * Math.cos(camAng);
 		double camFromPivot =  Robot.getPref("Gear cam y distance from pivot", 0);
// 		Log.debug("cam from pivot y: " + camFromPivot);
 		return camY + Robot.getPref("Gear cam y distance from pivot", 0);
 	}
	
	public double getParallacticDistanceToGear() {
		double d1 = getCamDistToGearMark(getNumber("rightGearBottomY", 0), getNumber("rightGearTopY", 0) );
		double d2 = getCamDistToGearMark(getNumber("leftGearBottomY", 0), getNumber("leftGearTopY", 0) );
		putNumber("d1", d1);
		putNumber("d2", d2);
		double dBetween = REFLECTOR_DIST_GEAR;
		// From https://en.wikipedia.org/wiki/Median_(geometry)#Formulas_involving_the_medians.27_lengths
		return Math.sqrt(2*d1*d1+2*d2*d2-dBetween*dBetween)/2;
	}
	
	
	public double getCamDistToGearMark(double yLow, double yHigh) {
		double pixelDist = Math.abs(yLow - yHigh);
		double fieldOfView = (5 * RESOLUTION_WIDTH) / pixelDist;
		double distanceToMark = (fieldOfView / 2) / (Math.tan(THETA));
		return distanceToMark;
	}
	
	public double getCamAngToGearMark(double xPos) {
 		double pixelDisplacement = xPos - SCREEN_CENTER_X;
 		double abstractDepth = SCREEN_CENTER_X / Math.tan(THETA);
 		return (Math.atan(pixelDisplacement / abstractDepth) +  Math.toRadians(GEAR_CAM_OFFSET));
	}
	
	public boolean alignedButHorizontallyOffset() {
		return ( Math.abs(getAngleToGear()) < 0.6 ) && 
				(Math.abs(getParallacticDistanceToGear()) < Math.abs(getCameraDistanceToGearPlane() - 1) );
	}
	
	public double angleToTurnIfHorizontallyOffset() {
		double parDist = getParallacticDistanceToGear();
		double camDist = getCameraDistanceToGearPlane();
		
		double theta =  Math.atan(parDist / (2*Math.sqrt( camDist*camDist- parDist*parDist)));
		return Math.toDegrees(directionToTurnIfHorizontallyOffset() * (Math.PI/2 - theta));
	}
	
	public double angleToTurnBackIfHorizontallyOffset() {
		double parDist = getParallacticDistanceToGear();
		double camDist = getCameraDistanceToGearPlane();
		double theta =  Math.toRadians( Math.atan(parDist / ( 2*Math.sqrt(parDist*parDist - camDist*camDist) )) );
		double angle = -directionToTurnIfHorizontallyOffset() * (theta + 
				Math.acos(getCameraDistanceToGearPlane()/getParallacticDistanceToGear()));
		
		return Math.toDegrees(angle);
	}
	
	public double distanceToTravelIfHorizontallyOffset() {
		double parDist = getParallacticDistanceToGear();
		double camDist = getCameraDistanceToGearPlane();
		
		double theta =  Math.atan(parDist / (2*Math.sqrt(parDist*parDist - camDist*camDist)));
		return getParallacticDistanceToGear() / (2*Math.sin(theta));
	}
	
	public double directionToTurnIfHorizontallyOffset() {
		double d1 = getCamDistToGearMark(getNumber("rightGearBottomY", 0), getNumber("rightGearTopY", 0) );
		double d2 = getCamDistToGearMark(getNumber("leftGearBottomY", 0), getNumber("leftGearTopY", 0) );
		
		if (d1 > d2) return 1;
		else return -1;
	}
	

	public double getDistanceToBoiler() {
		if (getBoolean("boilerFound", true)) {
			double pixelHeight = getNumber("boilerY", 0);
			double distanceToBoiler = (SCREEN_DEPTH * BOILER_HEIGHT) / pixelHeight;
			return distanceToBoiler;
		} else {
			return 0;
		}
	}

	public double getAngleToBoiler() {
		if (getBoolean("boilerFound", true)) {
			double tapeCenterX = getNumber("boilerX", 0);
			double pixelDisplacement = tapeCenterX - SCREEN_CENTER_X;
			double abstractDepth = (RESOLUTION_WIDTH / 2) / Math.tan(THETA);

			double angle = (Math.atan(pixelDisplacement / abstractDepth) * 180) / Math.PI;

			return angle;
		} else {
			return 0;
		}
	}

	public void updateGearCoordinates() {
		double[] gearValues = getNumArray("gearValues", defaultGearValues);
		
		leftMarkCoords[1] = getCameraDistanceToGearPlane() + Robot.getPref("Gear cam y distance from pivot", 0);
		rightMarkCoords[1] = getCameraDistanceToGearPlane() + Robot.getPref("Gear cam y distance from pivot", 0);
		leftMarkCoords[0] = Math.tan(getAngleToGear())*leftMarkCoords[1];
		rightMarkCoords[0] = Math.tan(getAngleToGear())*rightMarkCoords[1];
		
		putNumber("leftX", leftMarkCoords[0]);
		putNumber("leftY", leftMarkCoords[1]);
		putNumber("rightX", rightMarkCoords[0]);
		putNumber("rightY", rightMarkCoords[1]);
	}
	
//	public void updateGearCoordinates() {
//		double[] gearValues = getNumArray("gearValues", defaultGearValues);
//
//		leftMarkCoords[0] = getXFromPivotPoint(getCamDistToGearMark(gearValues[4], gearValues[5]), 
//				getCamAngToGearMark(gearValues[0]));
//		rightMarkCoords[0] = getXFromPivotPoint(getCamDistToGearMark(gearValues[6], gearValues[7]), 
//				getCamAngToGearMark(gearValues[2]));
//		leftMarkCoords[1] = getCameraDistanceToGearPlane() + Robot.getPref("Gear cam y distance from pivot", 0);
//		rightMarkCoords[1] = getCameraDistanceToGearPlane() + Robot.getPref("Gear cam y distance from pivot", 0);
//		
//		putNumber("leftX", leftMarkCoords[0]);
//		putNumber("leftY", leftMarkCoords[1]);
//		putNumber("rightX", rightMarkCoords[0]);
//		putNumber("rightY", rightMarkCoords[1]);
//		
//	}
	
//switch back to this one if the above doesn't work
//	public void updateGearCoordinates() {
//		leftMarkCoords[0] = getXFromPivotPoint(getCamDistToGearMark(getNumber("leftGearBottomY", 0), getNumber("leftGearTopY", 0) ), 
//				getCamAngToGearMark(getNumber("leftGearCenterX", 0)));
//		rightMarkCoords[0] = getXFromPivotPoint(getCamDistToGearMark(getNumber("rightGearBottomY", 0), getNumber("rightGearTopY", 0) ), 
//				getCamAngToGearMark(getNumber("rightGearCenterX", 0)));
////		leftMarkCoords[1] = getYFromPivotPoint(getCamDistToGearMark(getNumber("leftGearBottomY", 0), getNumber("leftGearTopY", 0) ), 
////				getCamAngToGearMark(getNumber("leftGearCenterX", 0)));
////		rightMarkCoords[1] = getYFromPivotPoint(getCamDistToGearMark(getNumber("rightGearBottomY", 0), getNumber("rightGearTopY", 0) ), 
////				getCamAngToGearMark(getNumber("rightGearCenterX", 0)));
////		double leftAng = getCamAngToGearMark(getNumber("leftGearCenterX", 0));
////		double rightAng = getCamAngToGearMark(getNumber("rightGearCenterX", 0));
//		leftMarkCoords[1] = getCameraDistanceToGearPlane() + Robot.getPref("Gear cam y distance from pivot", 0);
//		rightMarkCoords[1] = getCameraDistanceToGearPlane() + Robot.getPref("Gear cam y distance from pivot", 0);
//		
//		putNumber("leftX", leftMarkCoords[0]);
//		putNumber("leftY", leftMarkCoords[1]);
//		putNumber("rightX", rightMarkCoords[0]);
//		putNumber("rightY", rightMarkCoords[1]);
//		
//	}
	
	@Override
	public void displayData() {
		putNumber("Distance to Gear", getDistanceToGear());
		putNumber("Angle to Gear", getAngleToGear());
		putNumber("Distance to Boiler", getDistanceToBoiler());
		putNumber("Angle to Boiler", getAngleToBoiler());
		putNumber("Gear camera angle to target", Math.toDegrees(getCameraAngleToGear()));
		putBoolean("Is aligned but horizontally offset", alignedButHorizontallyOffset());
		putNumber("Angle to turn if horizontally offset", angleToTurnIfHorizontallyOffset());
		putNumber("Par distance to gear", getParallacticDistanceToGear());
		putNumber("Cam ang to left gear mark", getCamAngToGearMark(getNumber("leftGearCenterX", 0)));
		putNumber("Cam ang to right gear mark", getCamAngToGearMark(getNumber("rightGearCenterX", 0)));
//		updateGearCoordinates();
		
		
	}
}
