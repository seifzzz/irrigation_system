# Description 
As a irrigation system which helps the automatic irrigation of agricultural lands without human intervention, system has to
be designed to fulfil the requirement of maintaining and configuring the plots of land by the irrigation time slots and the
amount of water required for each irrigation period.
The irrigation system should have integration interface with a sensor device to direct letting the sensor to irrigate based on
the configured time slots/amount of water.


# Plot Controller  
it's used to create,configure,edit and show list of plot.
1. Create plot : Using Uri  : /plots (post), put jsontext in the request  like this {name: "" , size : "" ,crop : ""} , Id is already generated when the Plot is created,
every plot has a slot so when the Plot created , his slot is generated depends on land's size(ft) and land's crop (I made three types : fruits,vegetables,rice).
2. Delete plot : Using Uri : /plots/{id} (Delete), deleting land and it's slot from database (if it exist)
3. Update Plot : Using Uri : /plots/{id} (Patch), updating land and it's slot (if it exist)
4. Find Plot : Using Uri : /plots/{id} (Get) , Showing details of this land
5. Find All lands : Using Uri : /plots (Get) , Showing details of all lands
6. Find All slots : Using Uri : /plots/slots (Get) , Showing details of all slots

# Irrigation System Controller 
it's used to start,stop,know system's status and irrigate just one land.
1.Status : Using Uri : /irrigation/status (Get) , it shows system's status.
2.Start : Using Uri : /irrigation/start (Post) , it starts the system , irrigates every land existing on database , if there another plot created , when the system is 
running , and using the Uri : /irrigation/irrigate/{id} to irrigate , system will try 3 times to irrigate it every 30 seconed , if it can't make it, 
alerting system will work to tell who use the system , the system can't make this request now, then the failed process will be added to retry Queue , it will  
start after another lands are finished , and it will start to irrigate.
3.Stop : Using Uri : /irrigation/stop , it makes the system stop.
4.Irrigate just one land : /irrigation/irrigate/{id} , it irrigate just one land, if the system is working and the sensor is available.


# Irrigation System Service  
it used to check if the system is working or not.

# Sensor Service  
it's used to check ,if the sensor is available to irrigate land or not , if it's not available ,it will retry 3 times in every 30 seconed.

# Alerting System Service  
it's used when the system can't irrigate , just to alert the client ,if he can't start it now and inform him it will start after the 
system is available

# Before Running server  
change application properties, to connect your local database using mysql (url , usernaem and password)

