import java.util.Scanner;
import java.sql.*;

public class Registrar 
{
	public static void main(String[] args) 
	{
	    final String DB_URL = "jdbc:postgresql://localhost:5432/Registrar";
		Connection conn;
		
	      try
	      {
	         // Create a connection to the database
	         conn = DriverManager.getConnection(DB_URL, "postgres", "student");

	         char choice;
	         Scanner keyboard = new Scanner(System.in);
	         System.out.println("Welcome to the Registrar " +
	                            "of the small College Database Manager!");
	         do
	         {
	            printMenu();
	            choice = keyboard.nextLine().charAt(0);
	            switch(choice)
	            {
	               case '0':
	                  // Close the connection
	                  conn.close();
	                  break;
	               case '1':
	                  //viewStudent(conn);
	                  break;
	               case '2':
	                  //viewSchedule(conn);
	                  break;
	               case '3':
	                  //viewTuition(conn);
	                  break;
	               case '4':
	                  //viewCourses(conn);
	                  break;
	               case '5':
		              addStudent(conn);
	                  break;
	               case '6':
	            	   addSchedule(conn);
	                  break;
	               case '7':
		              addTuition(conn);
	                  break;
	               case '8':
		              addCourse(conn);
	                  break;
	               case '9':
	                  beginNewSemester(conn);
	                  break;
	            }
	         } while(choice != '0');
	      }

	      catch(Exception ex)
	      {
	         System.out.println("ERROR: " + ex.getMessage());
	      }
	   }
	/**
    The printMenu method displays the menu choices
    for the user to work with the database.
	 */

	public static void printMenu()
	{
	    System.out.println();
	    System.out.println("Select from the following " +
	                       "options:");
	    System.out.println("1. View student personal informations");
	    System.out.println("2. View the schedule of a student");
	    System.out.println("3. View Tuition information of student");
	    System.out.println("4. View the offered courses");
	    System.out.println("5. Add a student");
	    System.out.println("6. Add a class to student's schedule");
	    System.out.println("7. Add student tuition information");
	    System.out.println("8. Add an offered course");
	    System.out.println("9. Begin New Semester");
	    System.out.println("0. Exit the program");
	}

	
 /**
    The beginNewSemester method is a utility method that
    removes the tables and allows the user to reset the
    database for a new semester.
    @param conn A connection to the database.
 */

	public static void beginNewSemester(Connection conn)
	{
	    try
	    {
	       Statement stmt = conn.createStatement();
	
	       // Remove the tables if they already exist
	       // Throws an exception if the tables do not exist
	       stmt.execute("DROP TABLE Courses");
   	       stmt.execute("DROP TABLE Schedule");
	       stmt.execute("DROP TABLE Tuition");
	       stmt.execute("DROP TABLE Student");
	       
	       // Once the tables have been removed, call the
	       // method to create and initialize the tables
	       System.out.println("Reinitializing database.");
	       createRegistrarDB(conn);
	    }

	    catch(Exception ex)
	    {
	       // Create the tables if they do not exist
	       System.out.println("Creating database.");
	       createRegistrarDB(conn);
	    }
	}

 /**
    The createRegistrarDB method is a utility method that
    creates the tables and initializes the database
    with teams and games.
    @param conn A connection to the database.
 */

	public static void createRegistrarDB(Connection conn)
	{
	    try
	    {
	       Statement stmt = conn.createStatement();
	
	       // Create the table of Students
	       stmt.execute("CREATE TABLE Student (" 
	    		   		+ "EMPLID CHAR(8) NOT NULL PRIMARY KEY, " 
	                    + "FirstName CHAR(15) NOT NULL, " 
	                    + "LastName CHAR(15) NOT NULL,"
	                    + "Email CHAR(50),"
	                    + "Phone CHAR(10),"
	                    + "Address CHAR(60) NOT NULL"
	                    + ")");
	
	       // Add some students
	       stmt.executeUpdate("INSERT INTO Student " 
	                          + "(EMPLID, FirstName,"
	                          + "LastName, Email, Phone, Address)"
	                          + "VALUES ('12345678', 'Mario', 'Brother',"
	                          + "'mario@noa.nintendo.com', '3477185678', "
	                          + "'30 Mushroom Kingdom st. Brooklyn. NY. 11223')");
	
	       stmt.executeUpdate("INSERT INTO Student " 
	           				  + "(EMPLID, FirstName,"
	           				  + "LastName, Email, Phone, Address)"
	           				  + "VALUES ('87654321', 'Tom', 'Riddle',"
	           				  + "'Tommy@hogwarts.com', '7183569754', "
	            			  + "'3810  Bicetown Road. New York. NY. 10016')");
	       
	       stmt.executeUpdate("INSERT INTO Student " 
					  		  + "(EMPLID, FirstName,"
					  		  + "LastName, Email, Phone, Address)"
					  		  + "VALUES ('10111213', 'The', 'Doctor',"
					  		  + "'DoctorWho@whovians.com', '3178267951', "
					  		  + "'299 Pinewood Avenue. Brooklyn. NY. 11201')");
	       
	       stmt.executeUpdate("INSERT INTO Student " 
	    		   			  + "(EMPLID, FirstName,"
	    		   			  + "LastName, Email, Phone, Address)"
	    		   			  + "VALUES ('96483128', 'Sam', 'Winchester',"
	    		   			  + "'SWinchester@WinBros.com', '2523478698', "
	    		   			  + "'666 Heaven blvd.  Lawrence. Kansas. 66044')");
	
	       // Create table for Tuition information
	       stmt.execute("CREATE TABLE Tuition (" 
	                    + "EMPLID CHAR(8) NOT NULL PRIMARY KEY " 
	                    + "REFERENCES Student (EMPLID),"
	                    + "TuitionPaid decimal (6,2) NOT NULL, " 
	                    + "TuitionOwed decimal (6,2) NOT NULL, " 
	                    + "ReceivesAid CHAR(2) NOT NULL " 
	                    + ")");
	       
	       //Add Tuition information for students
	       stmt.executeUpdate("INSERT INTO Tuition " 
	                          + "(EMPLID, TuitionPaid, " 
	                          + "TuitionOwed, ReceivesAid) " 
	                          + "VALUES ('12345678','3000.00', " 
	                          + "'3000', 'Y')");
	
	       stmt.executeUpdate("INSERT INTO Tuition " 
	               			  + "(EMPLID, TuitionPaid, " 
	               			  + "TuitionOwed, ReceivesAid) " 
	               			  + "VALUES ('87654321','5000.00', " 
	    		   			  + "'1000', 'N')");
	       
	       stmt.executeUpdate("INSERT INTO Tuition " 
	               			  + "(EMPLID, TuitionPaid, " 
	               			  + "TuitionOwed, ReceivesAid) " 
	               			  + "VALUES ('10111213','0.00', " 
	               			  + "'6000.00', 'N')");
	       
	       stmt.executeUpdate("INSERT INTO Tuition " 
	    		   			  + "(EMPLID, TuitionPaid, " 
	    		   			  +"TuitionOwed, ReceivesAid) " 
	    		   			  +"VALUES ('96483128','6000.00', " 
	    		   			  +"'0.00', 'Y')");
	       
	       stmt.execute("CREATE TABLE courses (" 
     				+ "CourseID INT NOT NULL PRIMARY KEY, " 
     				+ "Instructor CHAR(20) NOT NULL, " 
     				+ "Department CHAR(20) NOT NULL " 
     				+ ")");
      
	       //Add some courses
	       stmt.executeUpdate("INSERT INTO Courses " 
		  		  		  	 + "(CourseID, Instructor, Department) " 
		  		  		  	 + "VALUES (5678, 'John Smith', " 
		  		  		  	 + "'Mathematics')");
      
	       stmt.executeUpdate("INSERT INTO Courses " 
			  		  		 + "(CourseID, Instructor, Department) " 
			  		  		 + "VALUES (5739, 'Peter Capaldi', " 
			  		  		 + "'Mathematics')");
      
	      stmt.executeUpdate("INSERT INTO Courses " 
			  		  		 + "(CourseID, Instructor, Department) " 
			  		  		 + "VALUES (1234, 'Nathan Anderson', " 
			  		  		 + "'English')");
	      
	      stmt.executeUpdate("INSERT INTO Courses " 
    		  				 + "(CourseID, Instructor, Department) " 
			  		  		 + "VALUES (3456, 'Jodie Whittaker', " 
			  		  		 + "'English')");
	      
	      stmt.executeUpdate("INSERT INTO Courses " 
			  		  		 + "(CourseID, Instructor, Department) " 
			  		  		 + "VALUES (9012, 'Matt Damon', " 
			  		  		 + "'Science')");
	      
	     stmt.executeUpdate("INSERT INTO Courses " 
	   		   			     + "(CourseID, Instructor, Department) " 
	   		   			     + "VALUES (2852, 'David Tennant', " 
	   		   			     + "'Science')");
	             
	      stmt.executeUpdate("INSERT INTO Courses " 
	   		   			     + "(CourseID, Instructor, Department) " 
	   		   			     + "VALUES (4089, 'Matt Smith', " 
	   		   			     + "'History')");
	      
	      stmt.executeUpdate("INSERT INTO Courses " 
	   		   			     + "(CourseID, Instructor, Department) " 
	   		   			     + "VALUES (6325, 'Bradley Walsh', " 
	   		   			     + "'History')");
	       
	       // Create table for Schedule information
	       stmt.execute("CREATE TABLE Schedule (" 
	               		+ "EMPLID CHAR(8) NOT NULL PRIMARY KEY " 
	               		+ "REFERENCES Student (EMPLID),"
	               		+ "CourseOneID INT " 
	               		+ "REFERENCES Courses (CourseID),"
	               		+ "CourseTwoID INT " 
	               		+ "REFERENCES Courses (CourseID),"
	               		+ "CourseThreeID INT " 
	               		+ "REFERENCES Courses (CourseID),"
	               		+ "CourseFourID INT " 
	               		+ "REFERENCES Courses (CourseID)"
	               		+ ")");
	       
	       //Create the schedule for the students
	       stmt.executeUpdate("INSERT INTO Schedule " 
		   			  		  + "(EMPLID, CourseOneID, " 
		   			  		  + "CourseTwoID, CourseThreeID, CourseFourID) " 
		   			  		  + "VALUES ('12345678', 1234, " 
		   			  		  + "5678, 9012, 3456)");
	       
	       stmt.executeUpdate("INSERT INTO Schedule " 
		   			  		  + "(EMPLID, CourseOneID, " 
		   			  		  + "CourseTwoID, CourseThreeID, CourseFourID) " 
		   			  		  + "VALUES ('87654321', 5739, " 
		   			  		  + "2852, 1234, 3456)");
	       
	       stmt.executeUpdate("INSERT INTO Schedule " 
		   			  		  + "(EMPLID, CourseOneID, " 
		   			  		  + "CourseTwoID, CourseThreeID, CourseFourID) " 
		   			  		  + "VALUES ('10111213',4089, " 
		   			  		  + "6325, 5739, 5678)");
	       
	       stmt.executeUpdate("INSERT INTO Schedule " 
		   			  		  + "(EMPLID, CourseOneID, " 
		   			  		  + "CourseTwoID, CourseThreeID, CourseFourID) " 
		   			  		  + "VALUES ('96483128', 9012, " 
		   			  		  + "2852, 6325, 4089)");
	       
	       //Create the courses table
	       
	       
	    }
	
	    catch (Exception ex)
	    {
	       System.out.println("ERROR: " + ex.getMessage());
	    }
	}

	
	/**
	
	@param conn A connection to the database.
	 */
	public static void addStudent(Connection conn)
	{
	    Scanner keyboard = new Scanner(System.in);
	
	    try
	    {
	       char ans;
	       String emplid, studentFirstName, studentlastName, email, phone, address;
	       Statement stmt = conn.createStatement();
	
	       do
	       {
	    	    System.out.println("Enter the student EMPLID: ");
	   			emplid = keyboard.nextLine();
	    	   
	    	    System.out.println("Enter the student first name: ");
		   		studentFirstName = keyboard.nextLine();
		   		
		   		System.out.println("Enter the student last name: ");
		   		studentlastName = keyboard.nextLine();
		   		
		   		System.out.println("Enter the student Email: ");
		   		email = keyboard.nextLine();
		   		
		   		System.out.println("Enter the student Phone number: ");
		   		phone = keyboard.nextLine();
		   		
		   		System.out.println("Enter the student Full Address: ");
		   		address = keyboard.nextLine();
	
		   		stmt.executeUpdate("INSERT INTO Student " 
	                + "(EMPLID, FirstName,"
	                + "LastName, Email, Phone, Address)"
	                + "VALUES ('" + emplid + "', ''" 
	                + studentFirstName + "', '" 
	                + studentlastName + "', '" + email + "','"
	                + phone + "', '" + address + "')");
		   		
		   		
		   		System.out.print("Do you want to add " +
	                           "another student: ");
		   		ans = keyboard.nextLine().charAt(0);
	           
	       } while(ans == 'Y'|| ans == 'y');
	    }
	    catch(Exception ex)
	    {
	       System.out.println("ERROR: " + ex.getMessage());
	    }
	    keyboard.close();
	}
	/**
	    
	    @param conn A connection to the database.
	 */
	
	public static void addSchedule(Connection conn)
	 {
	    Scanner keyboard = new Scanner (System.in);
	
	    try
	    {
	       char ans;
	       String EMPLID;
	       int C1, C2, C3, C4;
	
	       Statement stmt = conn.createStatement();
	
	       do
	       {
	          System.out.print("Enter the EMPLID of student: ");
	          EMPLID = keyboard.nextLine();
	
	          System.out.print("Enter the first course number");
	          C1 = keyboard.nextInt();
	          
	          System.out.print("Enter the second course number");
	          C2 = keyboard.nextInt();

	          System.out.print("Enter the third course number");
	          C3 = keyboard.nextInt();
	          
	          System.out.print("Enter the fourth course number");
	          C4 = keyboard.nextInt();
	
	          stmt.executeUpdate("INSERT INTO Schedule " +
	                             "(EMPLID, CourseOneID, " +
	                             "CourseTwoID, CourseThreeID, CourseFourID) " 
	                             + "VALUES ('" + EMPLID 
	                             + "', " + C1 + ", " 
	                             + C2 + ", " + C3 + ", " + C4 + ")");
	          
  	          System.out.print("Do you want to enter " +
	                           "another student's schedule: ");
	          ans = keyboard.nextLine().charAt(0);
	
	       } while(ans == 'Y'|| ans == 'y');
	    }
	
	    catch(Exception ex)
	    {
	       System.out.println("ERROR: " + ex.getMessage());
	    }
	    keyboard.close();

	 }
	/**
    
    @param conn A connection to the database.
	*/
	public static void addTuition (Connection conn)
	{
		Scanner keyboard = new Scanner (System.in);
		
	    try
	    {
	       char ans, finAid;
	       String emplid;
	       double paid, owed;
	
	       Statement stmt = conn.createStatement();
	
	       do
	       {
	          System.out.print("Enter the EMPLID of student: ");
	          emplid = keyboard.nextLine();
	          keyboard.nextLine();
	
	          System.out.print("Enter the amount the student owes");
	          owed = keyboard.nextDouble();
	          keyboard.nextLine();
	          
	          System.out.print("Enter the amount the student paid");
	          paid = keyboard.nextDouble();
	          keyboard.nextLine();

	          System.out.print("Does the student receive financial aid? Y/N");
	          finAid = keyboard.nextLine().charAt(0);
	
	          stmt.executeUpdate("INSERT INTO Tuition " 
	                  + "(EMPLID, TuitionPaid, " 
	                  + "TuitionOwed, ReceivesAid) " 
	                  + "VALUES ('"+ emplid +"','"+ paid 
	                  + "', '" + owed + "', '" + finAid + "')");
	          
  	          System.out.print("Do you want to enter " +
	                           "another student's tuition info: ");
	          ans = keyboard.nextLine().charAt(0);
	
	       } while(ans == 'Y'|| ans == 'y');
	    }
	
	    catch(Exception ex)
	    {
	       System.out.println("ERROR: " + ex.getMessage());
	    }
	    keyboard.close();

	}
	
	
	/**
    
    @param conn A connection to the database.
	*/
	public static void addCourse (Connection conn)
	{
		Scanner keyboard = new Scanner (System.in);
		
	    try
	    {
	       char ans;
	       String instructor, department;
	       int courseId;
	
	       Statement stmt = conn.createStatement();
	
	       do
	       {
	          System.out.println("Enter the course ID: ");
	          courseId = keyboard.nextInt();
	          keyboard.nextLine();

	
	          System.out.println("Enter the Instructor's name: ");
	          instructor = keyboard.nextLine();
	          keyboard.nextLine();

	          
	          System.out.println("Enter the department of the course: ");
	          department = keyboard.nextLine();
	          keyboard.nextLine();

	
	          stmt.executeUpdate("INSERT INTO Courses " 
	    	  		  + "(CourseID, Instructor, Department) " 
	    	  		  + "VALUES ("+ courseId+ ", '" + instructor 
	    	  		  + "', '" + department +"')");
	          
  	          System.out.print("Do you want to add " +
	                           "another course: ");
	          ans = keyboard.nextLine().charAt(0);
	
	       } while(ans == 'Y'|| ans == 'y');
	    }
	
	    catch(Exception ex)
	    {
	       System.out.println("ERROR: " + ex.getMessage());
	    }
	    keyboard.close();

	}
}