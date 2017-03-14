'''
Must:
Run script to set camera exposure
Run boiler identification on loop and put to SmartDashboard if shooter command running
Run gear mark identification only ~three times and put to SmartDashboard if gear command run
Set the right ports for cams
'''

""" Imports and Inits """
from access_nt import NTClient  # have similar lines to import the boiler identifier and gear identifier class
import cv2
import subprocess
import numpy as np
import boiler_identify
import lift_marks_identify

nt = NTClient()

# cameras
shooterCap = cv2.VideoCapture(0)
gearCap = cv2.VideoCapture(1)

log = open("/tmp/vision.log", 'w')

# Gear Tape values
gearFailCounter = 0

""" Exposure script """
# shooter camera
subprocess.call("uvcdynctrl -d video0 -s \"Exposure, Auto\" 1", shell=True)
subprocess.call("uvcdynctrl -d video0 -s \"Exposure (Absolute)\" 5", shell=True)

# gear camera
subprocess.call("uvcdynctrl -d video1 -s \"Exposure, Auto\" 1", shell=True)
subprocess.call("uvcdynctrl -d video1 -s \"Exposure (Absolute)\" 5", shell=True)

# alt shooter camera
subprocess.call("uvcdynctrl -d video2 -s \"Exposure, Auto\" 1", shell=True)
subprocess.call("uvcdynctrl -d video2 -s \"Exposure (Absolute)\" 5", shell=True)

# alt gear camera
subprocess.call("uvcdynctrl -d video3 -s \"Exposure, Auto\" 1", shell=True)
subprocess.call("uvcdynctrl -d video3 -s \"Exposure (Absolute)\" 5", shell=True)

log.write("log works.\n")
nt.write("Vision", "OH-YEAH", False)
nt.write("Vision", "gearRunning", False)
nt.write("Vision", "shooterRunning", False)

""" Main Loop """
while(True):

	""" boiler tape identification code """
	if nt.getShooter():
		log.write("shooter running\n")
		shooterCap.open(0)
		shooterCap.set(3, 640)
		shooterCap.set(4, 360)
		ret, shooterFrame = shooterCap.read()
		# Run boiler identification script
		centers = boiler_identify.findBoiler(shooterFrame, np.array([48, 175, 100]), np.array([100, 255, 200]))

		nt.write("Vision", "boilerFound", centers[0] != -1)
		if (centers[0] != -1):
			nt.write("Vision", "boilerX", centers[0])
			nt.write("Vision", "boilerY", centers[1])
	else:
		shooterCap.release()

	""" gear tape identification code """
	if nt.getGear():
		log.write("gear running\n")
		gearCap.open(1)
		gearCap.set(3, 640)
		gearCap.set(4, 360)

		ret, gearFrame = gearCap.read()
		# Run gear mark identification
		lx, ly, rx, ry, lb, lt, rb, rt, success = lift_marks_identify.findTape(
			gearFrame, np.array([65, 175, 70]), np.array([100, 255, 200]))

		nt.write("Vision", "leftGearCenterX", lx)
		nt.write("Vision", "leftGearCenterY", ly)
		nt.write("Vision", "rightGearCenterX", rx)
		nt.write("Vision", "rightGearCenterY", ry)

		nt.write("Vision", "leftGearBottomY", lb)
		nt.write("Vision", "leftGearTopY", lt)
		nt.write("Vision", "rightGearBottomY", rb)
		nt.write("Vision", "rightGearTopY", rt)

		nt.write("Vision", "OH-YEAH", lx != -1)
	else:
		gearCap.release()
