public class Scheduler {
    public static void main(String[] args) {
        //query database for student schedules, store values in global variables
        Students myStudents = new Students();
        myStudents.getSchedules();
   	    myStudents.initializeResultSchedules();	
		myStudents.scheduleStudents();
		myStudents.saveSchedules();
        myStudents.printSchedules();
	}

    
}