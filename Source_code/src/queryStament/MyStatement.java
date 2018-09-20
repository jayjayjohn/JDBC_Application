package queryStament;

public class MyStatement {
	
	public static String getTransByZipMonthYear = "SELECT FIRST_NAME, LAST_NAME, t.* " + 
			"FROM cdw_sapp_customer c JOIN cdw_sapp_creditcard t\r\n" + 
			"USING (CREDIT_CARD_NO)  WHERE c.CUST_ZIP =? AND MONTH = ? AND YEAR = ?  ORDER BY t.DAY DESC,c.LAST_NAME,c.FIRST_NAME;";

	public static String getTransByType = "SELECT COUNT(TRANSACTION_TYPE) , SUM(TRANSACTION_VALUE)\r\n" + 
			"FROM cdw_sapp_creditcard WHERE TRANSACTION_TYPE = ?;";
	
	
	public static String getTransByState = "SELECT count(t.TRANSACTION_ID)'# of Trans',sum(t.TRANSACTION_VALUE) 'Sum'\r\n" + 
			"From cdw_sapp_branch b JOIN cdw_sapp_creditcard t using (BRANCH_CODE) WHERE b.BRANCH_STATE = ? ";
	
	
	public static String getCustDetailbySSN = "Select * from cdw_sapp_customer WHERE SSN = ? ;";
	
	public static String getCustDetailbyName = "Select * from cdw_sapp_customer WHERE first_name like ? AND last_name like ? ;";
	
	public static String getCustBySSN = "Select * from cdw_sapp_customer WHERE SSN = ? " ;
	
	public static String setCustAddress = "UPDATE cdw_sapp_customer Set APT_NO = ? , STREET_NAME = ? , CUST_CITY = ?, CUST_STATE = ?, CUST_COUNTRY = ?, CUST_ZIP = ? WHERE SSN = ? AND CREDIT_CARD_NO = ?;";
	
	public static String setCustPhoneNum = "UPDATE cdw_sapp_customer Set CUST_PHONE = ? WHERE SSN = ? AND CREDIT_CARD_NO = ?;";
	
	public static String setCustEmail = "UPDATE cdw_sapp_customer Set CUST_EMAIL = ? WHERE SSN = ? AND CREDIT_CARD_NO = ?;";
	
	//public static String setCustCCN = "UPDATE cdw_sapp_customer Set CREDIT_CARD_NO = ? WHERE SSN = ?;";
	
	//public static String updateDateTime = "UPDATE cdw_sapp_customer Set LAST_UPDATED = ? WHERE SSN = ?;";
	
	public static String monthlyBillSum = "SELECT C.FIRST_NAME, C.LAST_NAME, T.CREDIT_CARD_NO ,SUM(TRANSACTION_VALUE)'SUM', T.MONTH, T.YEAR FROM cdw_sapp_creditcard T JOIN cdw_sapp_customer C ON C.SSN = T.CUST_SSN\r\n" + 
			"WHERE T.CREDIT_CARD_NO = ? AND MONTH = ? AND YEAR = ?;";
	
	public static String monthlyBillDetail = "SELECT TRANSACTION_ID, DAY, TRANSACTION_TYPE, TRANSACTION_VALUE FROM cdw_sapp_creditcard WHERE CREDIT_CARD_NO = ? AND MONTH = ? AND YEAR = ?;";
	
	
	
	public static String getTranBtwTwoDate = "SELECT str_to_date(concat(t.MONTH,'-',t.DAY,'-',t.YEAR), '%m-%d-%Y') 'DATE', t.* FROM cdw_sapp_creditcard t\r\n" + 
			"WHERE CUST_SSN = ? AND str_to_date(concat(t.MONTH,'-',t.DAY,'-',t.YEAR), '%m-%d-%Y') BETWEEN\r\n" + 
			"? AND ? ORDER BY 4 DESC;";
	
	
}
