package DaoImplement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import DAO.CustomerDAO;
import DTOmodel.Customer;
import DTOmodel.Transactions;
import queryStament.MyStatement;

public class CustomerDaoImpl implements CustomerDAO{
	
	PreparedStatement pstmt = null;
	Connection conn = null;
	
	public CustomerDaoImpl(Connection conn) {   //pass connection from UI module
		this.conn = conn;
	}

	public Customer getCustDetail(String firstName,String lastName) {
		ResultSet rs = null;
		Customer c =new Customer();
		
		try {
			pstmt =  conn.prepareStatement(MyStatement.getCustDetailbyName);
			pstmt.setString(1, firstName+"%");
			pstmt.setString(2, lastName+"%");
			rs = pstmt.executeQuery();
			c = loadCustomerInfo(rs);
		
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		
		return c;
		}
		
	public Customer loadCustomerInfo(ResultSet rs) throws SQLException {
			Customer cust=new Customer();
		if(rs.next()) {
			cust.setFirstName(rs.getString(1));
			cust.setMiddleName(rs.getString(2));
			cust.setLastName(rs.getString(3));
			cust.setSSN(rs.getString(4));
			cust.setCreditCardNo(rs.getString(5));
			cust.setAptNo(rs.getString(6));
			cust.setStreetName(rs.getString(7));
			cust.setCustCity(rs.getString(8));
			cust.setCustState(rs.getString(9));
			cust.setCustCountry(rs.getString(10));
			cust.setCustZip(rs.getInt(11));
			cust.setCustPhone(rs.getString(12));
			cust.setCustEmail(rs.getString(13));
			cust.setLastUpdated(rs.getString(14));
		}
			return cust;
	}	
		
	
	public Customer getCustDetail(int ssn) {
		ResultSet rs = null;
		Customer c =new Customer();
		try {
			pstmt =  conn.prepareStatement(MyStatement.getCustDetailbySSN);
			pstmt.setInt(1, ssn);
			rs = pstmt.executeQuery();
			c = loadCustomerInfo(rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}catch(NullPointerException e2) {
				System.out.println(e2.getMessage());
				System.out.println("Not record found for ssn:" + ssn);
			}
		return c;
	}
	
	
/*	public void setNewCustCCN(String CCN, int ssn) {
		try {
			pstmt =  conn.prepareStatement(MyStatement.setCustCCN);
			pstmt.setString(1, CCN);
			pstmt.setInt(2, ssn);
			//System.out.println(pstmt.toString());
			pstmt.execute();
			System.out.println("Customer's Credit Card info succefully changed.");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}*/
	
	public void setNewCustEmail(String newEmail, int ssn, String ccn) {
		try {
			pstmt =  conn.prepareStatement(MyStatement.setCustEmail);
			pstmt.setString(1, newEmail);
			pstmt.setInt(2, ssn);
			pstmt.setString(3, ccn);
			//System.out.println(pstmt.toString());
			pstmt.execute();
			System.out.println("Customer's Email succefully changed.");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public void setNewCustAddress(String newAptNo, String newStreet,String newCity, String newState, String newCountry, int newZip, int custSSN, String ccn ) {
		try {
			pstmt =  conn.prepareStatement(MyStatement.setCustAddress);
			pstmt.setString(1, newAptNo);
			pstmt.setString(2, newStreet);
			pstmt.setString(3, newCity);
			pstmt.setString(4, newState);
			pstmt.setString(5, newCountry);
			pstmt.setInt(6, newZip);
			pstmt.setInt(7, custSSN);
			pstmt.setString(8, ccn);
			//System.out.println(pstmt.toString());
			pstmt.execute();
			System.out.println("Customer's address succefully changed.");

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
	} // method ends
	
	public void setNewCustPhoneNumber(String newNum, int ssn, String ccn){
		try {
			pstmt =  conn.prepareStatement(MyStatement.setCustPhoneNum);
			pstmt.setString(1, newNum);
			pstmt.setInt(2, ssn);
			pstmt.setString(3, ccn);
			//System.out.println(pstmt.toString());
			pstmt.execute();
			System.out.println("Customer's Phone Number succefully changed.");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
/*	public void setCustLastUpdated(String datetime,int ssn ) {
		
		try {
			pstmt =  conn.prepareStatement(MyStatement.updateDateTime);
			pstmt.setString(1, datetime);
			pstmt.setInt(2, ssn);
			//System.out.println(pstmt.toString());
			pstmt.execute();
			System.out.println("Date time stamped: " + datetime);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}*/

	public Customer generateMonthlyBill(String ccnum, int month, int year) {
		ResultSet rs = null;
		Customer c = new Customer();
		try {
			pstmt =  conn.prepareStatement(MyStatement.monthlyBillSum);
			pstmt.setString(1, ccnum);
			pstmt.setInt(2, month);
			pstmt.setInt(3, year);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
			c.setFirstName(rs.getString(1));
			c.setLastName(rs.getString(2));
			c.setCreditCardNo(rs.getString(3));
			c.setMonthBillAmount(rs.getString(4));
			c.setBillMonth(rs.getString(5));
			c.setBillYear(rs.getString(6));
			}
			
			} catch (SQLException e) {
				e.getMessage();
			}
		return c;
	}
	
	
	public ArrayList<Transactions> generateMonthlyBillDetail(String ccnum, int month, int year) {
		ResultSet rs = null;
		ArrayList<Transactions> translist = new ArrayList<Transactions>();
		
		try {
			pstmt =  conn.prepareStatement(MyStatement.monthlyBillDetail);
			pstmt.setString(1, ccnum);
			pstmt.setInt(2, month);
			pstmt.setInt(3, year);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Transactions tran=new Transactions();
				tran.setTransactionId(rs.getInt("TRANSACTION_ID"));
				tran.setDay(rs.getInt("day"));
				tran.setTransactionValue(rs.getFloat("TRANSACTION_VALUE"));
				tran.setTransactionType(rs.getString("TRANSACTION_TYPE"));
				translist.add(tran);
			}
			
			} catch (SQLException e) {
				e.getMessage();
			}
		return translist;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
