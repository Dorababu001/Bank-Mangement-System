package org.jsp.bank.DAO;

public class BankCoustmerDesk {
	public static BankDAO  coustmerHelpDesk() {
		BankDAO bankDAO=new BankDaoImp();
		return bankDAO;
	}

}
