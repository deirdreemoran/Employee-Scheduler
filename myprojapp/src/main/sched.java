import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class sched {
    public static void main(String[] args) {
		String[][] myStudents = readFile();
		//for(int i = 0; i < myStudents.length; i++){
		//	System.out.println(myStudents[i]);
		//}//
		for(int qq = 0; qq < 15; qq++){
	    	for (int rr = 0; rr < myStudents[qq].length; rr++){
	    		System.out.print(myStudents[qq][rr]);
	    	}
	    	System.out.println();
	    }
	    String[] schedule1 = new String[112];
		String[] schedule2 = new String[112];
		int[] scheduleId1 = new int[112];
		int[] scheduleId2 = new int[112];
		String[] scheduleInits1 = new String[112];
		String[] scheduleInits2 = new String[112];
		
		String[] myStudentsNames= {"Allie", "Beth", "Carol", "Daniel", "Elena", "Fread", "Greg", "Hannah", "Ike", "Josh", "Kathy", "Liz", "Matt", "Nina", "Oliver" };
		for(int i = 0; i < 112; i++){
			scheduleInits1[i] = "  ";
			scheduleInits2[i] = "  ";
			scheduleId1[i] = 99;
			scheduleId2[i] = 99;
			schedule1[i] = "0";
			schedule2[i] = "0";
		}		
		
		int[] myStudentIds ={0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14 };
		String[] daysOfWeek = {" ", "TUE-->", "WED-->", "THUR-->", "FRI-->", "SAT-->", "SUN-->", " "}; 
					System.out.println(myStudents[0][0]);
					System.out.println(myStudents[0][1]);

		for(int i = 0; i < 20; i++){
			int position = 0;
			for(int j = 0; j < myStudents.length; j++){
				System.out.print("Position" + position);
				for(int k = 0; k < 112; k++){
					if(myStudents[j][k].equals("1")){
						if(schedule1[k].equals("0")){
							System.out.print("before");
							System.out.println(myStudents[j][k]);

							schedule1[k] = "1";
							schedule1[k+1] = "1";
							schedule1[k+2] = "1";
							schedule1[k+3] = "1";
							myStudents[j][k] = "0";
							myStudents[j][k + 1] = "0";
							myStudents[j][k + 2] = "0";
							myStudents[j][k + 3] = "0";
							scheduleInits1[k] = myStudentsNames[position];
							scheduleInits1[k + 1] = myStudentsNames[position];
							scheduleInits1[k + 2] = myStudentsNames[position];
							scheduleInits1[k + 3] = myStudentsNames[position];
							scheduleId1[k] = myStudentIds[position];
							scheduleId1[k + 1] = myStudentIds[position];
							scheduleId1[k + 2] = myStudentIds[position];
							scheduleId1[k + 3] = myStudentIds[position];
							System.out.print("afetr");
							System.out.println(myStudents[j][k]);
							break;
						}
						else if(schedule2[k].equals("0")){
							schedule2[k] = "1";
							schedule2[k+1] = "1";
							schedule2[k+2] = "1";
							schedule2[k+3] = "1";
							myStudents[j][k] = "0";
							myStudents[j][k + 1] = "0";
							myStudents[j][k + 2] = "0";
							myStudents[j][k + 3] = "0";
							scheduleInits2[k] = myStudentsNames[position];
							scheduleInits2[k + 1] = myStudentsNames[position];
							scheduleInits2[k + 2] = myStudentsNames[position];
							scheduleInits2[k + 3] = myStudentsNames[position];
							scheduleId2[k] = myStudentIds[position];
							scheduleId2[k + 1 ] = myStudentIds[position];
							scheduleId2[k + 2] = myStudentIds[position];
							scheduleId2[k + 3] = myStudentIds[position];
							break;
						}
					}
				}
				position++;
			}
		}

		for(int gg = 0; gg < scheduleInits2.length; gg++){
			System.out.println(scheduleInits2[gg]);
		}
		for(int gg = 0; gg < scheduleInits2.length; gg++){
			System.out.println(scheduleInits1[gg]);
		}
		System.out.println("McHenry:");
		System.out.println("\t8am\t9am\t10am\t11am\t12pm\t1pm\t2pm\t3pm\t4pm\t5pm\t6pm\t7pm\t8pm\t9pm\t10pm\t11pm-close \n");
		String monday = "MON-->";
		int i = 0;
		System.out.print(monday);
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
System.out.println();

System.out.println("S&E:");
		System.out.println("\t8am\t9am\t10am\t11am\t12pm\t1pm\t2pm\t3pm\t4pm\t5pm\t6pm\t7pm\t8pm\t9pm\t10pm\t11pm-close \n");
		i = 0;
		System.out.print(monday);
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

		for(int qq = 0; qq < 15; qq++){
	    	for (int rr = 0; rr < myStudents[qq].length; rr++){
	    		System.out.print(myStudents[qq][rr]);
	    	}
	    	System.out.println();
	    }
	}


    private static String[][] readFile()
	{
		//String filename = "./main/resources/schedules.csv";
		String filename = "data.csv";
		String[] arrays = new String[15];
		String[][] someA = new String[15][];
		int i = 0;
	    try
	   	{
	    	BufferedReader reader = new BufferedReader(new FileReader(filename));
	    	String line;

	    	while ((line = reader.readLine()) != null)
	    	{
	    		String strArray[] = line.split(",");
	    		//System.out.print("ASDFASDFSADF");
	    		//for(int q = 0; q < strArray.length; q++){
	    		//	System.out.print(strArray[q]);
	    		//}
	    		//System.out.println();
	    		someA[i] = strArray;
	      		arrays[i] = line;
	      		i++;
	      		
	    	}
	    	//for(int qq = 0; qq < 15; qq++){
	    	//	for (int rr = 0; rr < someA[qq].length; rr++){
	    	//		System.out.print(someA[qq][rr]);
	    	//	}
	    	//	System.out.println();
	    	//}
	   		reader.close();
	    	return someA;
	  	}
	  	catch (Exception e)
	  	{
	    	System.err.format("Exception occurred trying to read '%s'.", filename);
	    	e.printStackTrace();
	    	return null;
	  	}
	}
}