package edu.cuny.csi.csc330.project;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


//import edu.cuny.csi.csc330.examples.Student;

public class Registration {
	
	//initial collection sizes
			private static final int INITIAL_COLLECTION_SIZE = 25; 
			private static final int INITIAL_MAP_SIZE = 89;
			//read in the users to a file
			public static List<String> readAllLines;
			
			//collection of users to be read in
			private String userName;
			private String password;
			private List users;
			//store something
			private Set set;

	public Registration() throws IOException {
		// TODO Auto-generated constructor stub
		init();	
	}
	
	public String getuserName()
	{
		return userName;
	}
	
	public void setuserName(String userName)
	{
		this.userName = userName;
	}
	
	public void User(String userName)
	{
		
	}
	
	public String getpassword()
	{
		return password;
	}
	
	public void setpassword(String password)
	{
		this.password = password;
	}
	
	
	private void init() throws IOException
	{
		//list = new Userlist(INITIAL_COLLECTION_SIZE);
		//read in the users to a file
		readAllLines = Files.readAllLines(Paths.get("/users/brianlykes/Documents/workspace/CSC330/src/ProjectUserPass.txt"));
		//set = new Userset(INITIAL_MAP_SIZE);
		System.out.println(readAllLines);
		populate();
	}
	
	public void populate()
	{
		
		
		/*
		// populate list with Integers 
		for(int i = 1 ; i <= INITIAL_COLLECTION_SIZE * 2 ; ++i ) {
			list.add(new Integer(i * 100)); 
		}
		System.out.println("Size of List instance: " + list.size()); 
		*/
/*
		// populate the map 
		Registration student1 = new Registration("Brian"); 
		Student student2 = new Student("Venisha"); 
		Student student3 = new Student("Jonathan");

		map.put(student1.getuser(), student1); 
		map.put(student2.getLastName(), student2); 
		System.out.println("Size of Map instance: " + map.keySet().size()); 

		// populate the set 
		set.add("one"); 
		set.add("two"); 
		set.add("three"); 
		set.add("one"); 
		set.add("two");

		//  HOW MANY ELEMENTS DO WE EXPECT TO BE IN THIS Set INSTANCE?   WHY?  
		System.out.println("Size of Set instance: " + set.size()); 

		if( set.contains("two") ) {
			System.out.println("Found it!"); 
		}
		else {
			System.err.println("Not found ;-("); 
		} 
*/
	}


	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Registration registration = new Registration();
	}

}
