# Status: Works. Tool.
# Plain ol' camera. No alteration; just for testing cameras and choosing ports.

import numpy as np
import cv2

cap = cv2.VideoCapture(0)
ret = cap.set(3,1280)
ret = cap.set(4,720)
while(True):
    # Capture frame-by-frame
    ret, frame = cap.read()
    
    cv2.imshow('frame',frame)
    if cv2.waitKey(1) & 0xFF == 27:
        break

# When everything done, release the capture
cap.release()
cv2.destroyAllWindows()