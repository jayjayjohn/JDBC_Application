package Main;

import java.text.SimpleDateFormat;
import java.util.Scanner;
import exceptions.*;

public class Execution {

	static OperationUI myOperation;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
        System.out.println(timeStamp);
        starts();
        
	}
	
	public static void starts() {
		myOperation = new OperationUI();
		
		if(myOperation.connection == null) {
			return;
		}
		
		System.out.println("Connection sucessfully established.");
		System.out.println("");
		printMenu();
		exeSelection();
		
	}
	
	public static void printMenu() {
		for(int i = 0 ; i<50;i++) {
			System.out.print("=");		
			}
		System.out.print(" Menu ");	
		for(int i = 0 ; i<52;i++) {
			System.out.print("=");		
			}
	
		System.out.print("\n");	
		System.out.println("1.\tDisplay the transactions made by customers living in a given zipcode for a given month and year.");
		System.out.println("2.\tDisplay the number and total values of transaction for a given type.");	
		System.out.println("3.\tDisplay the number and total values of transaction for branches in a given state.");	
		System.out.println("4.\tDisplay customer's account details .");	
	
		System.out.println("5.\tChange customer's account details.");
		System.out.println("6.\tGenerate monthly bill for a credit card for a given month and year.");
		System.out.println("7.\tDisplay transaction made by a customer between two dates.");
		System.out.println("");
		System.out.println("0.\tExit");	
		
		drawLine("=",108);
		
	}
	
	public static void exeSelection() {
		int selection=8;
		System.out.print("Enter your selection to continues:");
		
		Scanner scaner = new Scanner(System.in);
		
		do {	
			try{
				selection  = Integer.parseInt(scaner.next());  //scaner.nextIn() might create infinite loop
				if(selection<0 | selection >8)  {
					throw new noSuchThingException("");
				}
			}catch ( NumberFormatException | noSuchThingException e) {
				System.out.println("No such selection, try Again: ");
				continue;
			}
		
			switch (selection) {
			case 1:
				myOperation.showTranbyZipMonthYear();
				break;
			case 2:
				myOperation.showTransCountByType();
				break;
			case 3:
				myOperation.showTransCountByState();
				break;
			case 4:
				myOperation.showCustDetail();
				break;
			case 5:
				myOperation.modifyCustDetail();
				break;
		
			case 6:
				myOperation.showMonthlyBill();
				break;
		
			case 7:
				myOperation.showTransBtwTwodate();
				break;
			case 8:
				printMenu();
				break;
			case 0:
			System.out.print("Exit.");
			break;
			}
			
			
			if(selection!=0) {
				drawLine("=", 108);
				System.out.print("Enter your selection to continues or press 8 to show menu:");
				}
		
		}while (selection != 0);
		
		scaner.close();
		
	}
	
	
	public static void drawLine(String symbol, int number) {
		for(int i = 0 ; i<number;i++) { System.out.print(symbol);} 
		System.out.print("\n");
	}
	
	
	
	
	

}// end class
