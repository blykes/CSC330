package edu.cuny.csi.csc330.lab1;

import java.util.*;
import java.math.*;

// -10 - you have some a issue - ans doesn't look like you're disallowing dupes 
// Otherwise, very good!

public class SweetMillionGame 
{
	//constants for game
	public final static int DEFAULT_GAME_COUNT = 5;
	private final static String game_name = "Sweet Millions";
	private final static int store = 1583;
	private final static int selection_pool_size = 40;
	private final static int selection_count = 6;
	
	//Randomizer declaration
	private Randomizer randomizer;
	
	public SweetMillionGame()
	{
		init();
	}
	
	private void init()
	{
		randomizer = new Randomizer();
	}
	
	public void displayTicket()
	{
		//ticket heading
		displayHeading();
				
		//display numbers
		//displayNnumbers();
		
		//display footer
		displayFooter(); 
		
		return;
		
	}
	
	protected void displayHeading()
	{
		System.out.println("Store number: " + store + "\t"+ game_name);
		System.out.println("2/15/2016");
		
		for(int b=0; b<6; b++)
			{
			int num=randomizer.generateInt(1, 40); 
			System.out.printf("%d ", num );
			}

	}
	
	
	
	protected void displayFooter()
	{
		System.out.println("\nLab 1 CSC 330");
	}
	
	public static void main (String[] args)
	{
		int numberOfGames = 5;
		
		if (args.length >0)
		{
			numberOfGames = Integer.parseInt(args[0]);
		}
		
		SweetMillionGame game = new SweetMillionGame();
		
		//now what
		

		
		
		game.displayTicket();
	}
}//end of package
