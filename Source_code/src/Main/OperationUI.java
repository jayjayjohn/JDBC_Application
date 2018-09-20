package Main;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

import DaoImplement.CustomerDaoImpl;
import DaoImplement.TransactionsDaoImpl;
import exceptions.noSuchThingException;
import DTOmodel.Customer;
import DTOmodel.Transactions;

public class OperationUI {
	Scanner scan = new Scanner(System.in); 
	Connection connection = null;
	
	CustomerDaoImpl custImpl;
	TransactionsDaoImpl tranImpl;
	
	public OperationUI() {
		this.connection = getConnection();
		
		custImpl = new CustomerDaoImpl(this.connection);
		tranImpl = new TransactionsDaoImpl(this.connection);
	}
	
	public Connection getConnection() {
		Connection connection=null;
		try {
			FileReader file = new FileReader("db.login");
			Properties login = new Properties();
			login.load(file);
			System.out.println("Connecting to database \'cdw_sapp\' using "+login.getProperty("username")+"...");
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(login.getProperty("url"), login.getProperty("username"), login.getProperty("password"));
			//System.out.println("Connection successfully established.");
			
		} catch (ClassNotFoundException e1) {
			System.out.println(e1.getMessage());
		} catch (SQLException e2) {
			System.out.println(e2.getMessage());
			System.out.println("Please double check the login infomation in dblogin file.");
		} catch (IOException e3) {
			System.out.println(e3.getMessage());
		} 
		
		return connection;
		}//method ends
	
//**************************showTranbyZipMonthYear****************************************//
	
	public void showTranbyZipMonthYear() {
		ArrayList<Transactions> transList = new ArrayList<Transactions>(); 
		int zipCode;
		int month;
		int year;
			
		while(true) {
			try {
				System.out.print("Enter zipcode: ");
				zipCode = Integer.parseInt(scan.next()); //nextInt would cause a infinite loop in this case & parseInt would trigger exception if user enter non number
				if(zipCode <= 0 | zipCode >=999999 )  //ensure user input < 6 digit zip code
				{ 
					throw new noSuchThingException("");
				}
			}catch(NumberFormatException | noSuchThingException e) {
				System.out.println("Invalid zipcode, please enter number only.");
				continue;
				}
				break;
			}
		
		while(true) {
			try {
				System.out.print("Enter Month: ");
				month = Integer.parseInt(scan.next());
				if( month <=0 | month >12 ) { throw new noSuchThingException("");} 
			}catch(NumberFormatException | noSuchThingException e) {
				System.out.println("Invalid month, try again.");
				continue;
			}
				break;
			}
	
		
		while(true) {
			try {
				System.out.print("Enter Year: ");
				year = Integer.parseInt(scan.next());
				if( year <= 0 ) 
				{ throw new noSuchThingException("");
				} 
			}catch(NumberFormatException | noSuchThingException e) {
				System.out.println("Invalid year, try again.");
				continue;
			}
				break;
			}
	
		System.out.print("\n");	
		
		transList = tranImpl.getTransByZipMonthyear(zipCode,month,year);
		
		if(transList.isEmpty()) {
			System.out.println("No transaction record found for " + month + "/" + year  + " at " + zipCode );
			return;
		}
		
		System.out.println("Transactions detail for " + month+"/"+year+" from Zipcode "+ zipCode+"=============================================================");
		
		drawLine("=",110);
		
		String format = "%-10s %-10s %-15s %-10s %-10s %-20s %-20s %-15s %-10s %n";
		System.out.printf(format,"First Name","Last Name","Transaction ID","Value","Day","Type","Cust CCN","Customer SSN","Branch Code");
			
		for(Transactions tran:transList) {
			System.out.printf(format, tran.getCustomer().getFirstName(),tran.getCustomer().getLastName(), tran.getTransactionId(),  tran.getTransactionValue(),  tran.getDay(),  tran.getTransactionType() , tran.getCreditCardNum(),  tran.getCustSSN(), tran.getBranchCode());
			}
		
		System.out.println("Save result to txt file ? (Y/N)");
		
		char saveTxt = scan.next().charAt(0);
		
		if(saveTxt == 'Y' | saveTxt == 'y') {
			
			try {
				PrintWriter pWriter = new PrintWriter(new FileWriter("transDetail_"+month+year+"_"+zipCode+".txt"));
				pWriter.printf(format,"First Name","Last Name","Transaction ID","Value","Day","Type","Cust CCN","Customer SSN","Branch Code");
				
				for(Transactions tran:transList) {
					pWriter.printf(format,tran.getCustomer().getFirstName(),tran.getCustomer().getLastName(), tran.getTransactionId(),  tran.getTransactionValue(),  tran.getDay(),  tran.getTransactionType() , tran.getCreditCardNum(),  tran.getCustSSN(), tran.getBranchCode());
					}
				pWriter.close();
				System.out.println("");
				System.out.println("Result set wrote to \'transDetail_"+month+year+"_"+zipCode+".txt\' under project directory");
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}	
			}
		
	}// method ends
	
//**************************showTransCountByType****************************************//	
	
	public void showTransCountByType() {
		System.out.print("\n");
		
		ArrayList<String> typeList = tranImpl.getTypeList();
		System.out.println("Here is the list of type you may search for:");
		
		for(String type:typeList) {
			System.out.println("\t"+type);
			}
		
		drawLine("-", 108);
		
		System.out.print("Enter Transaction Type:");
		String type0 = scan.next();		
		System.out.print("\n");
		
		// check if user input valid type
		String type = type0.substring(0, 1).toUpperCase() + type0.substring(1).toLowerCase(); // ensure capitalization of first letter 
		
		System.out.println("type");
		if(!typeList.contains(type)) {
			System.out.println("Type " + type + " no found in the database.");
			System.out.println("Return to main menu.");
			return;
			}
		
		Transactions tran= tranImpl.getTransCountByType(type);	
		System.out.println("Type\tTotal#\tTotal Value");
		System.out.println(type+"\t"+tran.getCount()+"\t"+tran.getSum());
		
		
		System.out.println("");
		System.out.println("Save result to txt file ? (Y/N)");
		char saveTxt = scan.next().charAt(0);
		
		if(saveTxt == 'Y' | saveTxt == 'y') {
		try {
			FileWriter fw = new FileWriter("transCountSum_"+type+".txt");
			fw.write("Type\tTotal#\tTotal Value\n");
			fw.write(type+"\t"+tran.getCount()+"\t"+tran.getSum()+"\n");
			fw.close();
			System.out.println("Result set wrote to \'transCountSum_"+type+".txt\' under project directory");

		} catch (IOException e) {
			e.printStackTrace();
			}
		}
		
	}

	//**************************showTransCountByState****************************************//	
	public void showTransCountByState() {
		drawLine("=", 108);
		ArrayList<String> stateList = tranImpl.getStateList();
		System.out.println("Here is the list of states you may search for:");
		System.out.println("Returning to main menu.");
		for(String state:stateList) {
			System.out.print(state+" ");
		}
		
		System.out.print("\n");
		System.out.print("Enter State:");
		String state = scan.next().toUpperCase();
		System.out.print("\n");
		
		if(!stateList.contains(state)) {
			System.out.println("State " + state + " does not exist in the database.");
			
			return;
		}
		
		Transactions tran= tranImpl.getTransCountByState(state);
		System.out.println("State\t# of trans\tTotal Value");
		System.out.println(state+ "\t" +tran.getCount()+"     \t" +tran.getSum());
		
		
		System.out.println("");
		System.out.println("Save result to txt file ? (Y/N)");
		char saveTxt = scan.next().charAt(0);
		
		if(saveTxt == 'Y' | saveTxt == 'y') {
		
		try {
			FileWriter fw = new FileWriter("transCountSum_"+state+".txt");
			fw.write("State\tTotal#\tTotal Value\n");
			fw.write(state+"\t"+tran.getCount()+"\t"+tran.getSum()+"\n");
			fw.close();
			System.out.println("Result set wrote to \'transCountSum_"+state+".txt\' under project directory");

		} catch (IOException e) {
			e.printStackTrace();
			}
		}
	}
	
//**************************showCustInfo*************************************************//	 
	public void showCustDetail() {
		
		drawLine("=", 108);
		int ssn;
		String first = "";
		String last= "";
		
		Customer c = new Customer();
		System.out.println("Select search method:");
		System.out.println("A\t Search by first and last name");
		System.out.println("B\t Search by SSN");
		
		char ab = scan.next().charAt(0);
	
		if(ab == 'B' | ab == 'b') {
			
			while(true) {
			try {
			System.out.print("Enter Customer SSN: ");
			ssn = Integer.parseInt(scan.next());
			if( ssn<=0 | ssn>=999999999) {throw new noSuchThingException("");} // check for 9 digit input
				}catch(NumberFormatException | noSuchThingException e1) {
				System.out.println("Invalid. Please enter 9 digit SSN without any symbol.");
				continue; // allow user to enter again
				}
				break; // break if not exception occurs
				}
			
			c = custImpl.getCustDetail(ssn);
		
			}
		else if (ab == 'a' | ab == 'A') {
			
			while(true) {
				try {
				
					System.out.print("Enter Customer first name: ");
					String first0 = scan.next();
					first=first0.substring(0, 1).toUpperCase() + first0.substring(1).toLowerCase();
					if(!first.matches("[a-zA-Z.']+")) {throw new noSuchThingException("");} // check for valid name
				}catch(noSuchThingException e1) {
					System.out.println("Name should not contain any number or symbol");
					continue; // allow user to enter again
				}
					break; // break if not exception occurs
				}
				
			while(true) {
			try {
				System.out.print("Enter Customer last name: ");
				String last0 = scan.next();
				last=last0.substring(0, 1).toUpperCase() + last0.substring(1).toLowerCase();
				if(!last.matches("[a-zA-Z.']+")) {throw new noSuchThingException("");} // check for valid name
			}catch(noSuchThingException e1) {
				System.out.println("Name should not contain any number or symbol");
				continue; // allow user to enter again
			}
				break; // break if not exception occurs
			}
			
			c = custImpl.getCustDetail(first,last);	
			
			}//else if statement ends
		
			drawLine("=", 108);
			String format = "%-15s %-25s %-25s %-60s %-15s %-25s %-15s %n";
			
			System.out.printf(format,"SSN","CCN","CustomerName", "Address","PhoneNo","Email","LastUpdated" );	
			
			try {
			System.out.printf(format,c.getSSN(), c.getCreditCardNo(),   
							c.getFirstName()+" "+c.getMiddleName().charAt(0)+". "+
							c.getLastName(), c.getAptNo()+" "+c.getStreetName()+","+
							c.getCustCity()+","+c.getCustState()+","+ c.getCustZip() , 
							c.getCustPhone() , c.getCustEmail() , c.getLastUpdated());
			
			}catch(NullPointerException e2) {
				System.out.println("Record not found, please check your input and try again!");
				return;
			}
			
			System.out.println("");
			System.out.println("Save result to txt file ? (Y/N)");
			char saveTxt = scan.next().charAt(0);
			
			if(saveTxt == 'Y' | saveTxt == 'y') {
			try {
				PrintWriter pw = new PrintWriter(new FileWriter("AccountInfo_"+c.getFirstName()+"_"+c.getLastName()+".txt"));
				pw.printf(format,c.getSSN() , c.getCreditCardNo()  ,   
							c.getFirstName()+" "+c.getMiddleName().charAt(0)+". "+
							c.getLastName(), c.getAptNo()+" "+c.getStreetName()+","+
							c.getCustCity()+","+c.getCustState()+","+ c.getCustZip() , 
							c.getCustPhone() , c.getCustEmail() , c.getLastUpdated()	
							);
				pw.close();
				System.out.println("Result set wrote to \'AccountInfo_"+c.getFirstName()+"_"+c.getLastName()+".txt\' under project directory");	
			
			} catch (IOException e) {
				e.printStackTrace();
			} 
			}
				
	}// end of method get cust detail

//**************************ModifyCustInfo*************************************************//		
	public void modifyCustDetail() {
		Customer c = new Customer();
	
		int ssnum;
		String ccnum;
		while(true) {
			try {	
			System.out.print("Enter Customer SSN: ");
			ssnum = Integer.parseInt(scan.next());
			if( ssnum<=0 | ssnum>=999999999) {throw new noSuchThingException("");} // SSN int(9)
			
			}catch(NumberFormatException | noSuchThingException e1) {
				System.out.println("Invalid. Please enter 9 digit SSN without any symbol.");
				continue; // allow user to enter again
				}
				break; // break if not exception occurs
			}
		c = custImpl.getCustDetail(ssnum);
		
		drawLine("=", 108);
		
		try {
		ccnum=c.getCreditCardNo();	
		System.out.println("Customer: " + c.getFirstName() +" "+ c.getMiddleName() +" "+ c.getLastName() + "      xxxxxx" + c.getSSN().substring(c.getSSN().length()-4,c.getSSN().length()));
		}catch(NullPointerException e) {
			System.out.println("No customer with SSN " + ssnum + " found.");
			return;
		}
		
		System.out.println("1) Change Address				2) Change Phone Number" );
		System.out.println("3) Change Email Address" );
		System.out.println("5) Go back to main menu" );
		System.out.println(); 
		System.out.println("Which item would you like to modify?");
			
		char modifyOption = scan.next().charAt(0);
				
		switch (modifyOption) {
		case '1':
				System.out.println("Please enter new address for the customer;");
				System.out.print("Apt no: ");
				scan.nextLine();  //move cursor to nextline 
				String newAptNo = scan.nextLine();
				
				System.out.print("Street Name: ");
				String newStreet = scan.nextLine();   //nextline allow user to input street separated by space
				
				System.out.print("City: ");
				String newCity = scan.nextLine();
				
				System.out.print("State: ");
				String newState = scan.nextLine();
				//scan.nextLine(); //move cursor to next line
				
				System.out.print("Country: ");
				String newCountry = scan.nextLine();
		
				int newZip;
				
				while(true) {
					try {
						System.out.print("Zipcode: ");
						newZip = Integer.parseInt(scan.next()); 
						if(newZip <= 0 | newZip >=9999999 ) { throw new noSuchThingException("");} //zipcode VARCHAR(7)
					}catch(NumberFormatException | noSuchThingException e) {
						System.out.println("Invalid zipcode, try again.");
						continue;
					}
						break;
					}
				
				
				System.out.println("New Address: " + newAptNo +" " + newStreet + ","+ newCity + "," + newState + ", " + newCountry+","+ newZip);
			
				System.out.println("Make change? (Y/N)");
				char confirm = scan.next().charAt(0);
					if (confirm == 'y' || confirm == 'Y' ) {
						custImpl.setNewCustAddress(newAptNo, newStreet, newCity, newState, newCountry, newZip, ssnum,ccnum);
				
						break;
					}else {
						System.out.println("Abort. Customer's info unchanged");;
						break;
					}
				 	
				case '2':
					String newPhoneNum="";
					while(true) {
					
						System.out.println("Please enter new phone number for the customer:");
					
						try {
						newPhoneNum = scan.next();
						if(!newPhoneNum.matches("[0-9]+") | newPhoneNum.length() > 7 ) {
							throw new noSuchThingException("");
						}
						}catch (noSuchThingException e) {
							System.out.println("Invalid! Phone number should be integer only and max. of 10 digit");
							continue;
						}

						custImpl.setNewCustPhoneNumber(newPhoneNum,ssnum,ccnum);
					
						break;
					}
					break;
					
				
				case '3':
					System.out.println("Please enter new Email for the customer:");
					String newEmail = scan.next();
					custImpl.setNewCustEmail(newEmail, ssnum, ccnum);
					//stampDateTime(ssnum);
					break;	
				default:
					break;
				}	
		
	}// method end
	
	
//**************************Monthly Bill*************************************************//
	
	public void showMonthlyBill() {
		String ccnum;
		int month;
		int year;
		
		while(true) {
			System.out.println("Enter Credit Card Number:");
			try {
				ccnum= scan.next();
				if(!ccnum.matches("[0-9]+") | ccnum.length() != 16 ) {
					throw new noSuchThingException("");
				}
			}catch (noSuchThingException e) {
				System.out.println("Please enter 16 digit without any symbol.");
				continue;
				}
			break;	
			}
	
		while(true) {
			try {
				System.out.print("Enter billing month: ");
				month = Integer.parseInt(scan.next());
				if( month <= 0 | month >12 ) { throw new noSuchThingException("");} //   
			}catch(NumberFormatException | noSuchThingException e) {
				System.out.println("Invalid month, try again.");
				continue;
				}
				break;
			}

		while(true) {
			try {
				System.out.print("Enter billing year: ");
				year = Integer.parseInt(scan.next());
				if( year <= 1998 | year >2118 ) { throw new noSuchThingException("");} //
			}catch(NumberFormatException | noSuchThingException e) {
				System.out.println("Invalid year, try again.");
				continue;
			}
				break;
			}
		
		Customer c = custImpl.generateMonthlyBill(ccnum, month, year);
		if(c.getFirstName()==null) {
			System.out.println("");
			System.out.println("No record found for credit card number: " + ccnum + " for the month " + month + "-" + year);
			return;
		}
		
		System.out.println("");
		System.out.println("Card Holder\t\tCredit Card Number\tSpending\tMonth\tYear");
	
		System.out.println(c.getFirstName()+" "+c.getLastName()+"\t\t"+c.getCreditCardNo()+"\t"+c.getMonthBillAmount()+"\t\t"+c.getBillMonth()+"\t"+c.getBillYear());
		System.out.println("");
		
		System.out.println("Show bill details ? (Y/N)");
		char showDetails = scan.next().charAt(0);
		
		if(showDetails == 'Y' | showDetails == 'y') {
			
			ArrayList<Transactions> tlist = custImpl.generateMonthlyBillDetail(ccnum, month, year);
			System.out.println("TransId\t\tValue\tDay\t\tType");
			
			for(Transactions t:tlist) {
				System.out.println(t.getTransactionId()+"\t\t"+t.getTransactionValue()+"\t"+t.getDay()+"\t\t"+t.getTransactionType());
			}
			
			
			
			System.out.println("Save result to txt file ? (Y/N)");
			char saveTxt = scan.next().charAt(0);
			if(saveTxt == 'Y' | saveTxt == 'y') {
		
				try {
					FileWriter fw= new FileWriter("Bill_"+month+"-"+year+"_"+c.getLastName()+c.getCreditCardNo().substring(12)+".txt");
					fw.write("Card Holder\t\tCredit Card Number\tSpending\tMonth\tYear\n");
					fw.write(c.getFirstName()+" "+c.getLastName()+"\t\t"+c.getCreditCardNo()+"\t\t"+c.getMonthBillAmount()+"\t\t"+c.getBillMonth()+"\t"+c.getBillYear());
					fw.write("\n\n");
					fw.write("TransId\t\tValue\tDay\t\tType\n");
					
					for(Transactions t:tlist) {
					fw.write(t.getTransactionId()+"\t\t"+t.getTransactionValue()+"\t"+t.getDay()+"\t\t"+t.getTransactionType()+"\n");
					}
					
					fw.close();
					System.out.println("Result set wrote to \'Bill_"+month+"-"+year+"_"+c.getLastName()+c.getCreditCardNo().substring(12)+".txt\' under project directory");	
					} catch (IOException e) {
						e.getMessage();
					}
		
				}
		}
		
	}//method ends
	
//**************************Transaction Between Two Dates*************************************************//	
	public void showTransBtwTwodate() {

		int ssn;
		String startDate="";
		String endDate="";
		while(true) {
			try {	
			System.out.print("Enter Customer SSN: ");
			ssn = Integer.parseInt(scan.next());
			if( ssn<=0 | ssn>=999999999) {
				throw new noSuchThingException("");
				} // SSN int(9)
			}catch(NumberFormatException | noSuchThingException e1) {
				System.out.println("Invalid. Please enter 9 digit SSN without any symbol.");
				continue; // allow user to enter again
				}
				break; // break if not exception occurs
			}
		
		while(true) {
			System.out.print("\n");
			System.out.print("Enter starting date (yyyy-mm-dd):");
			try {
			startDate = scan.next();
			if(!startDate.matches("^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])$")) {
				throw new noSuchThingException("");
				}
			}catch(noSuchThingException e) {
				System.out.println("Invalid.  Please input date as yyyy-mm-dd.");
				continue;
			}
			break;
		}
		
		
		while(true) {
			System.out.print("\n");
			System.out.print("Enter ending date (yyyy-mm-dd):");
			try {
			endDate = scan.next();
			if(!endDate.matches("^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])$")) {
				throw new noSuchThingException("");
				}
			}catch(noSuchThingException e) {
				System.out.println("Invalid.  Please input date as yyyy-mm-dd.");
				continue;
			}
			break;
		}	
		
		ArrayList<Transactions> tlist = tranImpl.getTranBtwTwoDates(startDate, endDate, ssn);
		Customer c = custImpl.getCustDetail(ssn);
		String last4ssn= c.getSSN().substring(5);
		
		if(tlist.isEmpty()) {
			System.out.println("No transaction found for " + c.getFirstName()+" "+c.getLastName() + " between " 
					+ startDate + " and " + endDate + "   xxxxxx"+last4ssn);
			return;
		}
		
		
		drawLine("=", 108);
		
		System.out.println("Here is the transaction made by " + c.getFirstName()+" "+c.getLastName() + " between "
							+ startDate + " and " + endDate + "   xxxxxx"+last4ssn);
		System.out.println("Date\tTransactionValue\tType\tBranchCode");
		
		for(Transactions t:tlist) {
			System.out.println(t.getMonth()+"-"+t.getDay()+"-"+t.getYear()+"\t" + t.getTransactionValue()+"\t\t"+t.getTransactionType());
	 		}
		
		System.out.println("");
		System.out.println("Save result to txt file ? (Y/N)");
		char saveTxt = scan.next().charAt(0);
		
		if(saveTxt == 'Y' | saveTxt == 'y') {
		
 		try {
 		FileWriter fw= new FileWriter("Trans_"+c.getLastName()+last4ssn+"_"+startDate+"_to_"+endDate+".txt");
 		fw.write("Date\t\tValue\tType\t\tBranchCode\n" );
		for(Transactions t:tlist) {
			
		fw.write(t.getMonth()+"-"+t.getDay()+"-"+t.getYear()+"\t" + t.getTransactionValue()+"\t\t"+t.getTransactionType()+"\n");
 		
 		}
		System.out.println("Result set wrote to \'Trans_"+c.getLastName()+last4ssn+"_"+startDate+"_to_"+endDate+".txt\' under project directory");	
 		fw.close();
 		}catch(IOException e) {
 		
 		}
		
		}
		
		
	}
	
/////////////////////////////Suplementary methods/////////////////////////////
	
	public void drawLine(String symbol, int number) {
		for(int i = 0 ; i<number;i++) { System.out.print(symbol);} 
		System.out.print("\n");
	}
	
/*	public void stampDateTime(int ssnum) {
		String dateTimeNow = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
		custImpl.setCustLastUpdated(dateTimeNow, ssnum);
		
	}*/
	
}// class ends
