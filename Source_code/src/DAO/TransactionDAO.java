package DAO;

import java.util.ArrayList;

import DTOmodel.Transactions;

public interface TransactionDAO {

	//public Connection getConnection(); 
	
	public ArrayList<Transactions> getTransByZipMonthyear(int zip, int month, int year);
	public Transactions getTransCountByType(String type);
	public Transactions getTransCountByState(String state); 
	
	public ArrayList<Transactions> transBtwTwoDates(String startDate, String endDate, int ssn );
	
	
}
