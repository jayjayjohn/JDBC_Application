package DaoImplement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import DAO.TransactionDAO;
import DTOmodel.Customer;
import DTOmodel.Transactions;
import queryStament.MyStatement;

public class TransactionsDaoImpl implements TransactionDAO{


	PreparedStatement pstmt = null;
	Connection conn = null;
	
	public TransactionsDaoImpl(Connection conn) {
		this.conn=conn;
	}


	@Override
	public ArrayList<Transactions> getTransByZipMonthyear(int zip, int month, int year) {
		
		ArrayList<Transactions> tranList = new ArrayList<Transactions>();
		ResultSet theRS = null;
		try {
			pstmt =  conn.prepareStatement(MyStatement.getTransByZipMonthYear);
			pstmt.setInt(1, zip);
			pstmt.setInt(2, month);
			pstmt.setInt(3, year);
			theRS = pstmt.executeQuery();	
			if(theRS.next()) {
			tranList = loadTransList(theRS);
			}
		}catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return tranList;
	}

	
	
	public ArrayList<Transactions> loadTransList(ResultSet rs) {    // load resultset to transaction object and add the list
		ArrayList<Transactions> translist= new ArrayList<Transactions>();
		try {
		while(rs.next()) {
			Transactions tran=new Transactions();
			
			//tran.setCustName(rs.getString("custName"));
			tran.setTransactionId(rs.getInt("T.TRANSACTION_ID"));
			tran.setDay(rs.getInt("t.day"));
			tran.setMonth(rs.getInt("t.Month"));
			tran.setYear(rs.getInt("t.Year"));
			tran.setCreditCardNum(rs.getString("t.CREDIT_CARD_NO"));
			tran.setCustSSN(rs.getString("T.CUST_SSN"));
			tran.setBranchCode(rs.getInt("t.BRANCH_CODE"));
			tran.setTransactionValue(rs.getFloat("t.TRANSACTION_VALUE"));
			tran.setTransactionType(rs.getString("T.TRANSACTION_TYPE"));
			
			Customer cust = new Customer();    //avoid causing a error when loading tran between two dates.
			cust.setFirstName(rs.getString("c.FIRST_NAME"));    //try to do it in a different way when you have time
			cust.setLastName(rs.getString("c.LAST_NAME"));
			tran.setCustomer(cust);
			
			
			translist.add(tran);
		}
		}catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return translist;
	}
	
	
	
	
	
	@Override
	public Transactions getTransCountByType(String type) {
		
		ResultSet rs = null;
		Transactions tran = new Transactions();
		try {
			pstmt =  conn.prepareStatement(MyStatement.getTransByType);
			pstmt.setString(1, type);
			rs = pstmt.executeQuery();
			if(rs.next()) {
			tran.setCount(rs.getInt(1));
			tran.setSum(rs.getFloat(2));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return tran;
	}

	@Override
	public Transactions getTransCountByState(String state) {
		Transactions tran = new Transactions();
		ResultSet rs = null;
		try {
			pstmt =  conn.prepareStatement(MyStatement.getTransByState);
			pstmt.setString(1, state);
			rs = pstmt.executeQuery();
			rs.first();
			tran.setCount(rs.getInt(1));
			tran.setSum(rs.getFloat(2));	
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return tran;
	}

	
	public ArrayList<Transactions> transBtwTwoDates(String startDate, String endDate, int ssn ){
		return null;
		
	}
	
	
	public ArrayList<Transactions> getTranBtwTwoDates(String startDate, String endDate, int ssn){
		
		ArrayList<Transactions> tranList1 = new ArrayList<Transactions>();
		
		ResultSet rs = null;
		try {
			pstmt =  conn.prepareStatement(MyStatement.getTranBtwTwoDate);
			pstmt.setString(2, startDate);
			pstmt.setString(3, endDate);
			pstmt.setInt(1, ssn);
			rs = pstmt.executeQuery();	
			
			while(rs.next()) {
				Transactions tran=new Transactions();
				tran.setTransactionId(rs.getInt("T.TRANSACTION_ID"));
				tran.setDay(rs.getInt("t.day"));
				tran.setMonth(rs.getInt("t.Month"));
				tran.setYear(rs.getInt("t.Year"));
				tran.setCreditCardNum(rs.getString("t.CREDIT_CARD_NO"));
				tran.setCustSSN(rs.getString("T.CUST_SSN"));
				tran.setBranchCode(rs.getInt("t.BRANCH_CODE"));
				tran.setTransactionValue(rs.getFloat("t.TRANSACTION_VALUE"));
				tran.setTransactionType(rs.getString("T.TRANSACTION_TYPE"));
				tranList1.add(tran);
			}
		
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tranList1;
	}
	
	
	
	
	//===============================Additional methods===============================//
	public ArrayList<String> getTypeList() {
		ResultSet rs=null;
		ArrayList<String> typeList = new ArrayList<String>();
		try {
			pstmt =  conn.prepareStatement("SELECT DISTINCT TRANSACTION_TYPE FROM cdw_sapp_creditcard;");
			rs = pstmt.executeQuery();
		
			while(rs.next()) {
				typeList.add(rs.getString(1));
			}	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return typeList;
	}
	public ArrayList<String> getStateList() {
		ResultSet rs=null;
		ArrayList<String> stateList = new ArrayList<String>();
		try {
			pstmt =  conn.prepareStatement("SELECT DISTINCT BRANCH_STATE FROM cdw_sapp_branch;");
			rs = pstmt.executeQuery();
			while(rs.next()) {
				stateList.add(rs.getString(1));
			}	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return stateList;
	}
	
	

	
	
}// class ends
