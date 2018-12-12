import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.util.Arrays;

public class Students {

	public ArrayList<String[]> myStudents = new ArrayList<String[]>();
	public String[] myStudentsNames = new String[20];
	public int[] myStudentIds = new int[20]; 
	public static final int maxShiftLength = 4;
	public static final int scheduleLength = 112;
	public static final String[] daysOfWeek = {" ", "MON-->", "TUE-->", "WED-->", "THUR-->", "FRI-->", "SAT-->", " "}; 
	public String[] schedule1 = new String[scheduleLength];
	public String[] schedule2 = new String[scheduleLength];
	public String[] scheduleInits1 = new String[scheduleLength];
	public String[] scheduleInits2 = new String[scheduleLength];
	 
	public void getSchedules() {
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

    public void saveSchedules(){
        String mystring1 = String.join(",", scheduleInits1);
        String mystring2 = String.join(",", scheduleInits2);
        Connection c2 = null;
        try
        {
            Class.forName("org.sqlite.JDBC");
            c2 = DriverManager.getConnection("jdbc:sqlite:db.sqlite3");
            String sql = "INSERT INTO myprojapp_studentSchedule ( studentSchedule1, studentSchedule2 ) VALUES ( ?, ? );";
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

    public void initializeResultSchedules(){
        for(int i = 0; i < scheduleLength; i++){
            scheduleInits1[i] = "  ";
            scheduleInits2[i] = "  ";
            schedule1[i] = "0";
            schedule2[i] = "0";
        }
    }

    public void scheduleStudents(){
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

    public void printSchedules(){
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