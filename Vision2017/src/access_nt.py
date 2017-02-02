from networktables import NetworkTables
import logging
logging.basicConfig(level=logging.DEBUG)

debug = False

def valueChanged(key, value, isnew):
        print("valueChanged: key: '%s'; value: %s; isNew: %s" % (key, value, isnew))

def connectionListener(connected, info):
        print(info, '; Connected=%s' %connected)

class NTClient:
    def __init__(self): 
        NetworkTables.initialize(server='roboRIO-199-FRC.local')
        self.nt = NetworkTables.getTable("SmartDashboard/Vision")
        if debug:
            NetworkTables.addGlobalListener(valueChanged)
            
    def changeSubTable(self, subtable):
        """Specify the subtable the client should now refer to"""
        self.nt = NetworkTables.getTable("SmartDashboard/" + subtable)
    
    
    def write(self, subtable, key, value):
        """Specify a subtable that you want to write a value to, the key of the value, and the value"""
        self.nt = NetworkTables.getTable("SmartDashboard/" + subtable)
        if type(value) == bool:
            self.nt.putBoolean(key, value)
        if type(value) == int or type(value) == float:
            self.nt.putNumber(key, value)
        if type(value) == type(""):
            self.nt.putString(key, value)
        self.nt = NetworkTables.getTable("SmartDashboard/Vision")
                
    def get(self, subtable, key, defaultValue):
        """Specify a subtable that you want to retrieve a value from, the key of the value, and the value"""
        self.nt = NetworkTables.getTable("SmartDashboard/" + subtable)
        if type(defaultValue) == bool:
            self.nt.getBoolean(key, defaultValue)
        if type(defaultValue) == int or type(defaultValue) == float:
            self.nt.getNumber(key, defaultValue)
        if type(defaultValue) == type(""):
            self.nt.getString(key, defaultValue)
        self.nt = NetworkTables.getTable("SmartDashboard/Vision")
            
    
