/**
 * Brian Lykes Lab 2A - Numeric Analyzer

 */

package edu.cuny.csi.csc330.lab2;

import java.util.*;

public class NumericAnalyzer 
{
	
	public NumericAnalyzer()
	{
		init();
	}
	
	public void init()
	{
		
	}
	
	public void Display(int args[])
	{
		//display the input numbers
		System.out.print("Unsorted array: ");
		for(int i = 0; i < args.length;i++)
			System.out.print(args[i] + " ");
		
		//sorted numbers
		Arrays.sort(args);
		System.out.print("\nSorted array: ");				
		for(int i = 0 ; i < args.length ; ++i) 
		{
			System.out.print(args[i] + " ");
		}
		
		//how long is the array
		System.out.println("\n\nThe size of the array is: " + args.length);
	}
	
	public void Calculate(int args[])
	{
		//the min
		int min = args[0];
		for(int i = 1; i < args.length;i++)
		{
			if (args[i] < min)
				min = args[i];
		}
		System.out.print("The sallest number is: " +min);
		
		//the max
		int max = args[0];
		for(int i = 1; i < args.length;i++)
		{
			if (args[i] > min)
				max = args[i];
		}
		System.out.print("\nThe largest number is: " +max);
		
		//find the range
		int range = 0;
		range = max - min;
		System.out.print("\nThe range value is: " + range);
		
		//finds the sum
		int sum = 0;
		for(int i = 0; i < args.length;i++)
			sum += args[i];
		System.out.println("\nThe sum is: " + sum);
		
		//Finds the average value
		int average = 0;
		for(int i = 0; i < args.length; i++)
			average = sum/args.length;
		System.out.println( "The mean or average value is: " + average);
	}
	public static void main(String[] args) 
	{
		//instance of Numeric Analyzer
		NumericAnalyzer analyzer = new NumericAnalyzer();
		
		//Error check
		if(args.length == 0 ) 
		{
			// Display error message … 
			System.err.println("You didnt enter any numbers Dave");

			System.exit(1); 
					
		}
		
		//create an array
		int [] numbers = new int[args.length];
		for(int i = 0 ; i < args.length ; ++i) 
		{
		numbers[i] = Integer.parseInt(args[i]);
		}
		
		
				
		
		analyzer.Display(numbers);
		analyzer.Calculate(numbers);
	}

}
