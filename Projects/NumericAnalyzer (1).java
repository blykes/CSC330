package edu.cuny.csi.csc330.lab2;

import java.util.*;

public class NumericAnalyzer 
{
	//variables and array for analyzer
	public final int ARRAY = 10; //the amount of numbers the user will be limited to
	public int[] digits = new int[ARRAY]; //the actual array of user input digits
	public int numbers;
	//scanner object for input
	Scanner keyboard  = new Scanner(System.in); //sets up keyboard inputs

/*
	public NumericAnalyzer()
	{
		init();
	}*/
	
	
	//lets get those digits
	public void getDigits()
	{
		//for loop for input
		System.out.println("Please enter your digits now\n");
		System.out.println("The first digit is:");
		numbers = keyboard.nextInt();
		for (int i = 0; i < ARRAY; i++)
		{
			System.out.println("The next digit is: ");
			numbers = keyboard.nextInt();
		}
			 
			System.out.println("Lets check that array: ");
			for(int i = 0 ; i < ARRAY ; ++i) {
				System.out.printf("[%d] = %d\n",  i,  digits[i] );
			}
	}
	
	//sort from end of randomizer from first lab and place here
	/*Arrays.sort(digits);
	
	for(int i = 0 ; i < digits ; ++i) {
		System.out.printf("[%d] = %d\n",  i,  digits[i] );
	}*/
	
	public static void main(String[] args) 
	{
		NumericAnalyzer check = new NumericAnalyzer();
		
		check.getDigits();
		
		if(args.length == 0 ) 
		{
			// Display some error message … 
			System.out.println("You didnt tell me how many numbers Dave");

			System.exit(1); 
			
		}
			

	}

}
