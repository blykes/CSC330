package edu.cuny.csi.csc330.lab2b;

import java.util.*;


/**
 *********************************************************************************************************************************************** 
 * @author ______  

AS SHOWN AT     http://web.eecs.umich.edu/~bartlett/credit_card_number.html   

2. LUHN Formula (Mod 10) for Validation of Primary Account Number

The following steps are required to validate the primary account number:
Step 1: Double the value of alternate digits of the primary account number 
beginning with the second digit from the right (the first right--hand digit is the check digit.)

Step 2: Add the individual digits comprising the products obtained in Step 1 to each of the unaffected digits in the original number.

Step 3: The total obtained in Step 2 must be a number ending in zero (30, 40, 50, etc.) for the account number to be validated.

For example, to validate the primary account number 4 9 9 2 7 3 9 8 7 1 6:
 9 2 3 8 1
 
 2
 1
 6
 6
 4
 1
 8 
 ---
 28 

4 
9 
7 
9 
7 
6
--- 
42

42 + 28 = 70  

 *************************************************************************************************************************************************/

public class PaymentCardValidator {
	
	private String accountNumber;  
	private boolean valid; 
	private int checkSum;  
	
	
	/**
	 * @param accountNumber
	 */
	public PaymentCardValidator(String accountNumber) 
	{
		this.accountNumber = accountNumber;
		
				
	}

	public int validate()   
	{
		checkSum =-1;
		
		//number check
		if (accountNumber.length()<1)
		{
			System.out.println("Invalid");
			valid = false;
			return -1;
		}
		
		checkSum = 0;
		int temp = 0;
		
		//scans account number and multiply by 2 
		for (int i = accountNumber.length()-2; i > 0; i-=2)
		{
			temp = Character.getNumericValue(accountNumber.charAt(i))*2;
			if (temp > 9) //if multiple is over 9
				temp -=9; //subtract 9 to add 
			checkSum+=temp;
		}
		
		//totals for skipped ints
		for (int i = accountNumber.length()-1; i >=0; i-=2)
		{
			checkSum += Character.getNumericValue(accountNumber.charAt(i));
		}
		
		valid = (checkSum%10 == 0);
		return checkSum;  
	}
	
	public void displayDetails()
	{
		//print the numbers 
		System.out.print("The credit card digits are: [");
		for(int i = 0; i< accountNumber.length();i++)
		{
		System.out.print(accountNumber.charAt(i) + " ");	
		}
		System.out.println("]");
		
		//Print the multiplied numbers
		System.out.print("The multiplied nunmbers are: [");
		for (int i = accountNumber.length()-2; i > 0; i-=2)
		{
			System.out.print(accountNumber.charAt(i) + " ");
		}
		System.out.println("]");
		
		//Print the unmultiplied numbers
		System.out.print("The unused numbers are: [");
		for (int i = accountNumber.length()-1; i >=0; i-=2)	
		{
			 System.out.print(accountNumber.charAt(i) + " ");
		}
		System.out.println("]");
				
		System.out.println(this);
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
		
	}


	@Override
	public String toString() {
		return "PaymentCardValidator [accountNumber=" + accountNumber
				+ ", valid=" + valid + ", checkSum=" + checkSum + "]\n";
	}

	/**
	 * @param args 
	 */
	public static void main(String[] args) {
		
		String accountNumber = "49927398716";
		
		if(args.length == 1) 
			accountNumber = args[0];
		
		PaymentCardValidator validator = new PaymentCardValidator(accountNumber);
		validator.validate(); 
		validator.displayDetails();
		
		//Invalid test
		validator.setAccountNumber("");
		validator.validate(); 
		validator.displayDetails(); 
		
		validator.setAccountNumber("30569309025904");
		validator.validate(); 
		validator.displayDetails();
		

		validator.setAccountNumber("378282246310005");
		validator.validate(); 
		validator.displayDetails(); 
		
		validator.setAccountNumber("4012888888881881");
		validator.validate(); 
		validator.displayDetails(); 
		
	}
}
