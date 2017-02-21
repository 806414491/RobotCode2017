'''
Must contain code to identify peg marks after our initial basic contour-finding process
Must return the centers of both strips of tape
'''

import numpy as np
import cv2
import operator

def findTape(frame, lower, upper):
    hsv = cv2.cvtColor(frame, cv2.COLOR_BGR2HSV)
    mask = cv2.inRange(hsv, lower, upper)
    cnts, hierarchy = cv2.findContours(mask.copy(), cv2.RETR_LIST,
                            cv2.CHAIN_APPROX_SIMPLE)
    
    cnts2 = []

    for c in cnts:
        epsilon = 0.05 * cv2.arcLength(c, True)
        c = cv2.approxPolyDP(c, epsilon, True)

        area = cv2.contourArea(c)

        if (len(c) == 4 and area > 100 and cv2.isContourConvex(c)): 
            cnts2.append((area, c))

    # sorts contours by largest to smallest area
    if cnts2 != None:
        cnts2.sort(key=operator.itemgetter(0), reverse=True)

    for i in range(0, len(cnts2) - 1):
        for j in range(i + 1, len(cnts2)):
            if (cnts2[i][0] / 2) < cnts2[j][0]:
                
                Mi = cv2.moments(np.array(cnts2[i][1]))
                if Mi["m00"] != 0:
                    centerXi = 320 - int(Mi["m10"] / Mi["m00"])
                    centerYi = 180 - int(Mi["m01"] / Mi["m00"])
                else:
                    return -1, -1, -1, -1, False


                Mj = cv2.moments(np.array(cnts2[j][1]))
                if Mj["m00"] != 0:
                    centerXj = 320 - int(Mj["m10"] / Mj["m00"])
                    centerYj = 180 - int(Mj["m01"] / Mj["m00"])
                else:
                    return -1, -1, -1, -1, False

                if centerXi > centerXj:
                    return centerXj, centerYj, centerXi, centerYi, True
                else:
                    return centerXi, centerYi, centerXj, centerYj, True


    return -1, -1, -1, -1, False
