package org.jsp.bank.DAO;

import org.jsp.bank.model.Bank;

public interface BankDAO {
    void userRegistration(Bank bank);
    void credit();
    void debit(String accountNumber,String password);
    void changingThePassword();
    void mobileToMobileTransaction(String mobileNumber);
    void checkBalance();
}
