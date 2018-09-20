package DAO;

import DTOmodel.Customer;

public interface CustomerDAO{
	

	public Customer getCustDetail(String firstName,String lastName);
	public Customer getCustDetail(int ssn);
	
	//public void setNewCustCCN(String CCN, int ssn);
	public void setNewCustEmail(String newEmail, int ssn,String ccn);
	
	public void setNewCustAddress(String newAptNo, String newStreet,String newCity, String newState, String newCountry, int newZip, int custSSN, String ccn);
	public void setNewCustPhoneNumber(String newNum, int ssn, String ccn);
	
	//public void setCustLastUpdated(String currentDateTime,int ssn );
	
	public Customer generateMonthlyBill(String ccnum, int month, int year);
	

	
}
