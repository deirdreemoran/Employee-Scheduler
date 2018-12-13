import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.util.Arrays;


     
public class Scheduler {
    public static void main(String[] args) {
        //query database for student schedules, store values in global variables
        ArrayList<String[]> myStudents = new ArrayList<String[]>();
        String[] myStudentsNames = new String[20];
        int[] myStudentIds = new int[20]; 
        int maxShiftLength = 4;
        int scheduleLength = 112;
        String[] daysOfWeek = {" ", "MON-->", "TUE-->", "WED-->", "THUR-->", "FRI-->", "SAT-->", " "}; 
        String[] schedule1 = new String[scheduleLength];
        String[] schedule2 = new String[scheduleLength];
        String[] scheduleInits1 = new String[scheduleLength];
        String[] scheduleInits2 = new String[scheduleLength];
        getSchedules(myStudents, myStudentsNames, myStudentIds);
        initializeResultSchedules(scheduleInits1, scheduleInits2, schedule1, schedule2, scheduleLength);	
		scheduleStudents(myStudents, myStudentsNames, myStudentIds, maxShiftLength, scheduleLength, schedule1, schedule2, scheduleInits1, scheduleInits2);
		saveSchedules(scheduleInits1, scheduleInits2);
        printSchedules(daysOfWeek, scheduleInits1, scheduleInits2);
	}

    public static void getSchedules(ArrayList<String[]> myStudents, String[] myStudentsNames, int[] myStudentIds) {
        Connection c = null;
        try
        {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:db.sqlite3");
            String sql = "SELECT id, name, schedule FROM myprojapp_schedule";
            Statement stmt  = c.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            int count = 0;
            
            // loop through the result set and get schedules, names and ids
            while (rs.next()) {
                String str = rs.getString("schedule");
                String[] aStr = str.split("");
                myStudents.add(aStr);
                myStudentsNames[count] = rs.getString("name");
                myStudentIds[count] = rs.getInt("id");
                count++;
            }
        }
        catch ( Exception e )
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public static void saveSchedules(String[] scheduleInits1, String[] scheduleInits2){
        String mystring1 = String.join(",", scheduleInits1);
        String mystring2 = String.join(",", scheduleInits2);
        System.out.println(mystring1);
        Connection c2 = null;
        try
        {
            Class.forName("org.sqlite.JDBC");
            c2 = DriverManager.getConnection("jdbc:sqlite:db.sqlite3");
            String sql = "UPDATE myprojapp_scheduler SET schedule1 = ?, schedule2 = ? WHERE id = 1;";
            PreparedStatement stmt  = c2.prepareStatement(sql);
            stmt.setString(1, mystring1);
            stmt.setString(2, mystring2);
            stmt.executeUpdate();

        }
        catch ( Exception e )
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

    }

    public static void initializeResultSchedules(String[] scheduleInits1, String[] scheduleInits2, String[] schedule1, String[] schedule2, int scheduleLength){
        for(int i = 0; i < scheduleLength; i++){
            scheduleInits1[i] = "  ";
            scheduleInits2[i] = "  ";
            schedule1[i] = "0";
            schedule2[i] = "0";
        }
    }

    public static void scheduleStudents(ArrayList<String[]> myStudents, String[] myStudentsNames, int[]myStudentIds, int maxShiftLength, int scheduleLength, String[] schedule1, String[] schedule2, String[] scheduleInits1, String[] scheduleInits2){
        for(int i = 0; i < 20; i++){
            int position = 0;
            for(int j = 0; j < myStudents.size(); j++){
                String[] aStudent = myStudents.get(j);
                for(int k = 0; k < aStudent.length; k++){
                    int slot = k;
                    if(aStudent[k].equals("1")){
                        if(schedule1[k].equals("0")){
                            for(int r = 0; r < maxShiftLength; r++){
                                if(slot != scheduleLength){
                                    if(aStudent[slot].equals("1") && schedule1[slot].equals("0")){
                                        schedule1[slot] = "1";
                                        aStudent[slot] = "0";
                                        scheduleInits1[slot] = myStudentsNames[position];
                                        slot++;
                                    }
                                    else{
                                        break;
                                    }
                                }
                                else{
                                    break;
                                }
                            }
                            break;
                        }
                        else if(schedule2[k].equals("0")){
                            for(int hour = 0; hour < maxShiftLength; hour++){
                                if(slot != scheduleLength){
                                    if(aStudent[slot].equals("1") && schedule2[slot].equals("0")){
                                        schedule2[slot] = "1";
                                        aStudent[slot] = "0";
                                        scheduleInits2[slot] = myStudentsNames[position];
                                        slot++;
                                    }
                                    else{
                                        break;
                                    }
                                }
                            }
                            break;
                        }
                    }
                }
                position++;
            }
        }
    }

    public static void printSchedules(String[] daysOfWeek, String[] scheduleInits1, String[] scheduleInits2){
        System.out.println("McHenry:");
        System.out.println("\t8am\t9am\t10am\t11am\t12pm\t1pm\t2pm\t3pm\t4pm\t5pm\t6pm\t7pm\t8pm\t9pm\t10pm\t11pm-close \n");
        String sunday = "SUN-->";
        int i = 0;
        System.out.print(sunday);
        System.out.print("\t");
        int day = 1;
        for (int j = 0; j < scheduleInits1.length; j++){
            if (!scheduleInits1[j].equals("  ")){
                System.out.print(scheduleInits1[j]);
                System.out.print("\t");
                i++;
                if (i == 16){
                    i = 0;
                    System.out.println();
                    System.out.print(daysOfWeek[day]);
                    System.out.print("\t");
                    day++;
                }
            }
            else{
                String blank = "---";
                System.out.print(blank);
                System.out.print("\t");
                i++;
                if (i == 16){
                    i = 0;
                    System.out.println();
                    System.out.print(daysOfWeek[day]);
                    System.out.print("\t");
                    day++;
                }
            }
        }
        System.out.println();
        System.out.println("S&E:");
        System.out.println("\t8am\t9am\t10am\t11am\t12pm\t1pm\t2pm\t3pm\t4pm\t5pm\t6pm\t7pm\t8pm\t9pm\t10pm\t11pm-close \n");
        i = 0;
        System.out.print(sunday);
        System.out.print("\t");
        day = 1;
        for (int j = 0; j < scheduleInits2.length; j++){
            if (!scheduleInits2[j].equals("  ")){
                System.out.print(scheduleInits2[j]);
                System.out.print("\t");
                i++;
                if (i == 16){
                    i = 0;
                    System.out.println();
                    System.out.print(daysOfWeek[day]);
                    System.out.print("\t");
                    day++;
                }
            }
            else{
                String blank = "---";
                System.out.print(blank);
                System.out.print("\t");
                i++;
                if (i == 16){
                    i = 0;
                    System.out.println();
                    System.out.print(daysOfWeek[day]);
                    System.out.print("\t");
                    day++;
                }
            }
        }
    }
}