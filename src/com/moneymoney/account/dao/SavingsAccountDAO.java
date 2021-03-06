package com.moneymoney.account.dao;

import java.sql.SQLException;
import java.util.List;

import com.moneymoney.account.SavingsAccount;
import com.moneymoney.exception.AccountNotFoundException;
import com.moneymoney.exception.InvalidInputException;

public interface SavingsAccountDAO {
	
	SavingsAccount createNewAccount(SavingsAccount account) throws ClassNotFoundException, SQLException;
	SavingsAccount updateAccount(SavingsAccount account)throws SQLException, ClassNotFoundException;
	SavingsAccount getAccountById(int accountNumber) throws ClassNotFoundException, SQLException, AccountNotFoundException;
	SavingsAccount deleteAccount(int accountNumber) throws SQLException, ClassNotFoundException, InvalidInputException;
	List<SavingsAccount> getAllSavingsAccount() throws ClassNotFoundException, SQLException;
	void updateBalance(int accountNumber, double currentBalance) throws ClassNotFoundException, SQLException;
	void commit() throws SQLException;
	double checkAccountBalance(int accountNumber) throws SQLException, ClassNotFoundException, InvalidInputException;
	List<SavingsAccount> getAllAccountsSortedByNames() throws ClassNotFoundException, SQLException;
	List<SavingsAccount> getAllSavingsAccountsSortedByRange(double minimum,
			double maximum) throws ClassNotFoundException, SQLException;
	List<SavingsAccount> getAccountByBalanceRange(double minimumBalance, double highestBalance) throws ClassNotFoundException, SQLException, AccountNotFoundException;
	SavingsAccount getAccountByName(String accountToSearch) throws ClassNotFoundException, SQLException, AccountNotFoundException;
	List<SavingsAccount> sort(int choice) throws ClassNotFoundException, SQLException;
}
