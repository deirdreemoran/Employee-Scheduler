# Scheduler-Java-Python
Using the Django web-framework, this application allows employers to generate weekly schedules based on submitted employee availability times. 
Employees can login securely and create a schedule based on their available times.  Schedules are stored in a sqlite3 database and 
can be saved and adjusted the logged in user.  Admins can generate a master schedule based on all employee submitted availabilities.  
The master schedule is generated using Java and stored in a sqlite3 database.  

This project was originally desgined to schedule ~20 library assistants on 2 public service desks for 16 hours a day, 7 days a week. (2x16x7 = total of 224 hours per week)
