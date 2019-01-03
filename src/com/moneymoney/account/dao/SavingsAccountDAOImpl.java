package com.moneymoney.account.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.moneymoney.account.SavingsAccount;
import com.moneymoney.account.util.DBUtil;
import com.moneymoney.exception.AccountNotFoundException;
import com.moneymoney.exception.InvalidInputException;

public class SavingsAccountDAOImpl implements SavingsAccountDAO {

	public SavingsAccount createNewAccount(SavingsAccount account) throws ClassNotFoundException, SQLException {
		Connection connection = DBUtil.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO ACCOUNT VALUES(?,?,?,?,?,?)");
		preparedStatement.setInt(1, account.getBankAccount().getAccountNumber());
		preparedStatement.setString(2, account.getBankAccount().getAccountHolderName());
		preparedStatement.setDouble(3, account.getBankAccount().getAccountBalance());
		preparedStatement.setBoolean(4, account.isSalary());
		preparedStatement.setObject(5, null);
		preparedStatement.setString(6, "SA");
		preparedStatement.executeUpdate();
		preparedStatement.close();
		DBUtil.commit();
		return account;
	}

	public List<SavingsAccount> getAllSavingsAccount() throws ClassNotFoundException, SQLException {
		List<SavingsAccount> savingsAccounts = new ArrayList<>();
		Connection connection = DBUtil.getConnection();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT * FROM ACCOUNT");
		while (resultSet.next()) {// Check if row(s) is present in table
			int accountNumber = resultSet.getInt(1);
			String accountHolderName = resultSet.getString("account_hn");
			double accountBalance = resultSet.getDouble(3);
			boolean salary = resultSet.getBoolean("salary");
			SavingsAccount savingsAccount = new SavingsAccount(accountNumber, accountHolderName, accountBalance,
					salary);
			savingsAccounts.add(savingsAccount);
		}
		DBUtil.commit();
		return savingsAccounts;
	}
	@Override
	public void updateBalance(int accountNumber, double currentBalance) throws ClassNotFoundException, SQLException {
		Connection connection = DBUtil.getConnection();
		connection.setAutoCommit(false);
		PreparedStatement preparedStatement = connection.prepareStatement
				("UPDATE ACCOUNT SET account_bal=? where account_id=?");
		preparedStatement.setDouble(1, currentBalance);
		preparedStatement.setInt(2, accountNumber);
		preparedStatement.executeUpdate();
	}
	
	@Override
	public SavingsAccount getAccountById(int accountNumber) throws ClassNotFoundException, SQLException, AccountNotFoundException {
		Connection connection = DBUtil.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement
				("SELECT * FROM account where account_id=?");
		preparedStatement.setInt(1, accountNumber);
		ResultSet resultSet = preparedStatement.executeQuery();
		SavingsAccount savingsAccount = null;
		if(resultSet.next()) {
			String accountHolderName = resultSet.getString("account_hn");
			double accountBalance = resultSet.getDouble(3);
			boolean salary = resultSet.getBoolean("salary");
			savingsAccount = new SavingsAccount(accountNumber, accountHolderName, accountBalance,
					salary);
			return savingsAccount;
		}
		throw new AccountNotFoundException("Account with account number "+accountNumber+" does not exist.");
	}
	
	@Override
	public SavingsAccount deleteAccount(int accountNumber) throws SQLException, ClassNotFoundException, InvalidInputException {
		Connection connection = DBUtil.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM account WHERE account_id = ?");
		preparedStatement.setInt(1, accountNumber);
		int rowsAffected = preparedStatement.executeUpdate();
	
		if(rowsAffected == 0)
			throw new InvalidInputException("Account with account number "+accountNumber+" does not exist.");
		
		return null;
	}

	@Override
	public void commit() throws SQLException {
		DBUtil.commit();
	}

	@Override
	public double checkAccountBalance(int accountNumber) throws SQLException, ClassNotFoundException, InvalidInputException {
		Connection connection = DBUtil.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement("select account_bal from account where account_id = ?");
		preparedStatement.setInt(1, accountNumber);
		ResultSet resultSet = preparedStatement.executeQuery();
		if(resultSet.next()){
			double accountBalnce = resultSet.getDouble(1);
			return accountBalnce;
		}
		throw new InvalidInputException("Account with account number "+accountNumber+" does not exist.");
	}

	@Override
	public SavingsAccount updateAccount(SavingsAccount account) throws SQLException, ClassNotFoundException {
		
		Connection connection = DBUtil.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement("UPDATE account SET  account_hn = ?,salary =? WHERE account_id = ?");
		preparedStatement.setString(1, account.getBankAccount().getAccountHolderName());
		preparedStatement.setBoolean(2, account.isSalary());
		preparedStatement.setInt(3, account.getBankAccount().getAccountNumber());
		int rowsAffected = preparedStatement.executeUpdate();

		DBUtil.commit();
		if(rowsAffected >= 1)
		if(rowsAffected == 1)
			System.out.println("Account Updated");
		return null;
	}

	@Override
	public List<SavingsAccount> getAllAccountsSortedByNames() throws ClassNotFoundException, SQLException {
		List<SavingsAccount> savingsAccounts = new ArrayList<>();
		Connection connection = DBUtil.getConnection();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT * FROM ACCOUNT ORDER BY account_hn;");
		while (resultSet.next()) {// Check if row(s) is present in table
			int accountNumber = resultSet.getInt(1);
			String accountHolderName = resultSet.getString("account_hn");
			double accountBalance = resultSet.getDouble(3);
			boolean salary = resultSet.getBoolean("salary");
			SavingsAccount savingsAccount = new SavingsAccount(accountNumber, accountHolderName, accountBalance,
					salary);
			savingsAccounts.add(savingsAccount);
		}
		DBUtil.commit();
		return savingsAccounts;
		
	}

	@Override
	public List<SavingsAccount> getAllSavingsAccountsSortedByRange(
			double minimum, double maximum) throws ClassNotFoundException, SQLException {
		List<SavingsAccount> savingsAccounts = new ArrayList<>();
		Connection connection = DBUtil.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM ACCOUNT WHERE account_bal BETWEEN ? AND ?;");
		preparedStatement.setDouble(1, minimum);
		preparedStatement.setDouble(2, maximum);
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {// Check if row(s) is present in table
			int accountNumber = resultSet.getInt(1);
			String accountHolderName = resultSet.getString("account_hn");
			double accountBalance = resultSet.getDouble(3);
			boolean salary = resultSet.getBoolean("salary");
			SavingsAccount savingsAccount = new SavingsAccount(accountNumber, accountHolderName, accountBalance,
					salary);
			savingsAccounts.add(savingsAccount);
		}
		DBUtil.commit();
		return savingsAccounts;
	}

	@Override
	public List<SavingsAccount> getAccountByBalanceRange(double minimumBalance, double highestBalance)
			throws ClassNotFoundException, SQLException, AccountNotFoundException {
		Connection connection = DBUtil.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM account where account_balance BETWEEN ? AND ?");
		preparedStatement.setDouble(1,minimumBalance);
		preparedStatement.setDouble(2, highestBalance);
		ResultSet resultSet = preparedStatement.executeQuery();
		
		List<SavingsAccount> listOfAccounts = new ArrayList();
		while(resultSet.next())
		{
			int accountNumber = resultSet.getInt("account_id");
			String accountHolderName = resultSet.getString("account_hn");
			double accountBalance = resultSet.getDouble(3);
			boolean salary = resultSet.getBoolean("account_isSalary");
			String accountType = resultSet.getString("account_type");
			SavingsAccount savingsAccount = new SavingsAccount(accountNumber,accountHolderName, accountBalance, salary);
			listOfAccounts.add(savingsAccount);
		}
		return listOfAccounts;
	}

	@Override
	public SavingsAccount getAccountByName(String accountToSearch)
			throws ClassNotFoundException, SQLException, AccountNotFoundException {
		Connection connection = DBUtil.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement
				("SELECT * FROM account where account_hn=?");
		preparedStatement.setString(1, accountToSearch);
		ResultSet resultSet = preparedStatement.executeQuery();
		SavingsAccount savingsAccount = null;
		if(resultSet.next()) {
			int accountNumber = resultSet.getInt("account_id");
			double accountBalance = resultSet.getDouble(3);
			boolean salary = resultSet.getBoolean("account_isSalary");
			savingsAccount = new SavingsAccount(accountNumber, accountToSearch, accountBalance, salary);
			return savingsAccount;
		}
		throw new AccountNotFoundException("Account with account holder name "+ accountToSearch +" does not exist.");
	}

	@Override
	public List<SavingsAccount> sort(int choice) throws ClassNotFoundException, SQLException {
		Connection connection = DBUtil.getConnection();
		List<SavingsAccount> sortedAccountList = new ArrayList();
		if(choice == 1)
		{
			Statement stmt = connection.createStatement();
			ResultSet resultSet = stmt.executeQuery("SELECT * FROM account ORDER BY account_id");
			while(resultSet.next())
			{

				int accountNumber = resultSet.getInt("account_id");
				String accountHolderName = resultSet.getString("account_hn");
				double accountBalance = resultSet.getDouble(3);
				boolean salary = resultSet.getBoolean("account_isSalary");
				String accountType = resultSet.getString("account_type");
				SavingsAccount savingsAccount = new SavingsAccount(accountNumber,accountHolderName, accountBalance, salary);
				sortedAccountList.add(savingsAccount);
			}
		
		}
		return sortedAccountList;
	}

}
