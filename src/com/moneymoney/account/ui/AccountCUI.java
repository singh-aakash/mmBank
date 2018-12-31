package com.moneymoney.account.ui;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.moneymoney.account.SavingsAccount;
import com.moneymoney.account.service.SavingsAccountService;
import com.moneymoney.account.service.SavingsAccountServiceImpl;
import com.moneymoney.account.util.DBUtil;
import com.moneymoney.exception.AccountNotFoundException;
import com.moneymoney.exception.InsufficientFundsException;
import com.moneymoney.exception.InvalidInputException;

public class AccountCUI {
	private static Scanner scanner = new Scanner(System.in);
	private static SavingsAccountService savingsAccountService = new SavingsAccountServiceImpl();

	public static void start() {

		do {
			System.out.println("****** Welcome to Money Money Bank********");
			System.out.println("1. Open New Savings Account");
			System.out.println("2. Update Account");
			System.out.println("3. Close Account");
			System.out.println("4. Search Account");
			System.out.println("5. Withdraw");
			System.out.println("6. Deposit");
			System.out.println("7. FundTransfer");
			System.out.println("8. Check Current Balance");
			System.out.println("9. Get All Savings Account Details");
			System.out.println("10. Sort Accounts");
			System.out.println("11. Exit");
			System.out.println();
			System.out.println("Make your choice: ");

			int choice = scanner.nextInt();

			performOperation(choice);

		} while (true);
	}

	private static void performOperation(int choice) {
		switch (choice) {
		case 1:
			acceptInput("SA");
			break;
		case 2:
			updateAccount();
			break;
		case 3:
			deleteAccount();
			break;
		case 4:
			searchAccounts();
			break;
		case 5:
			withdraw();
			break;
		case 6:
			deposit();
			break;
		case 7:
			fundTransfer();
			break;
		case 8:
			currentBalance();
			break;
		case 9:
			showAllAccounts();
			break;
		case 10:
			sortAccounts();
		case 11:
			try {
				DBUtil.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.exit(0);
			break;
		default:
			System.err.println("Invalid Choice!");
			break;
		}

	}

<<<<<<< HEAD
	private static void sortAccounts() {
		System.out
				.println("Select the criteria on which you want to sort the accounts:");
		System.out.println("1.Account Names\n2.Range of account balance\n");
		int choiceSort = scanner.nextInt();

		switch (choiceSort) {
		case 1:
			List<SavingsAccount> savingsAccounts;
			try {
				savingsAccounts = savingsAccountService
						.getAllSavingsAccountsSortedByNames();
				for (SavingsAccount savingsAccount : savingsAccounts) {
					System.out.println(savingsAccount);
				}
				DBUtil.commit();
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			break;

		case 2:
			List<SavingsAccount> savingsAccountsOrderedByRange;
			System.out.println("Enter the range :\nenter the maximum amount :");
			double maximum = scanner.nextDouble();
			System.out.println("enter the minimum amount :");
			double minimum = scanner.nextDouble();

			try {
				savingsAccountsOrderedByRange = savingsAccountService
						.getAllSavingsAccountsSortedByRange(minimum, maximum);
				for (SavingsAccount savingsAccount : savingsAccountsOrderedByRange) {
					System.out.println(savingsAccount);
				}
				DBUtil.commit();
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			break;
		}
	}

	private static void searchAccounts() {

		System.out.println("Enter the account number to find it:");
		int accountNumber = scanner.nextInt();
		SavingsAccount savingsAccount;
		try {
			savingsAccount = savingsAccountService
					.getAccountById(accountNumber);
			String result = savingsAccount.getBankAccount().toString();
			System.out.println(result);
		} catch (ClassNotFoundException | SQLException
				| AccountNotFoundException e) {
			e.printStackTrace();
		}
	}

	private static void updateAccount() {
		System.out.println("Enter account number to update");
		int accountNumber = scanner.nextInt();
		SavingsAccount savingsAccount = null;
		try {
			savingsAccount = savingsAccountService
					.getAccountById(accountNumber);
			System.out
					.println("1. update Account holderName \n 2. Update SalaryType \n 3. Update both HolderName and SalaryType ");
			int choice = scanner.nextInt();

			switch (choice) {
			case 1:
				System.out.println("Enter name to change account holder name");
				String accountHolderName = scanner.nextLine();
				accountHolderName = scanner.nextLine();

				try {
					savingsAccount = savingsAccountService
							.getAccountById(accountNumber);
					savingsAccount.getBankAccount().setAccountHolderName(
							accountHolderName);
					System.out.println("function calling");
				} catch (ClassNotFoundException | SQLException
						| AccountNotFoundException e1) {
					e1.printStackTrace();
				}

				break;
			case 2:
				System.out
						.println("Enter salary type to change account salarytype");
				boolean salary = scanner.nextBoolean();
				try {
					savingsAccount = savingsAccountService
							.getAccountById(accountNumber);
					savingsAccount.setSalary(salary);
					savingsAccountService.updateAccount(savingsAccount);
				} catch (ClassNotFoundException | SQLException
						| AccountNotFoundException e1) {
					e1.printStackTrace();
				}

				break;
			case 3:
				System.out.println("enter the accountHolderName");
				accountHolderName = scanner.nextLine();
				accountHolderName = scanner.nextLine();
				System.out.println("enter the salary type");
				salary = scanner.nextBoolean();
				try {
					savingsAccount = savingsAccountService
							.getAccountById(accountNumber);
					savingsAccount.setSalary(salary);
					savingsAccount.getBankAccount().setAccountHolderName(
							accountHolderName);
					savingsAccountService.updateAccount(savingsAccount);
				} catch (ClassNotFoundException | SQLException
						| AccountNotFoundException e1) {

					e1.printStackTrace();
				}
=======
	private static void updateAccount() {
		System.out.println("Enter account number to update");
		int accountNumber = scanner.nextInt();
		SavingsAccount savingsAccount;
		try{
			savingsAccount = savingsAccountService.getAccountById(accountNumber);	
			int choice = scanner.nextInt();
			System.out.println("enter 1. update Account holderName \n 2. Update SalaryType \n 3. Update both HolderName and SalaryType ");
			switch(choice)
			{
			case 1:
				System.out.println("Enter name to change account holder name");
				String accountHolderName = scanner.nextLine();
				try {
					savingsAccount = savingsAccountService.getAccountById(accountNumber);
					savingsAccount.getBankAccount().setAccountHolderName(accountHolderName);
					savingsAccountService.updateAccount(savingsAccount);
				} catch (ClassNotFoundException| SQLException| AccountNotFoundException e1) {
					e1.printStackTrace();
				} 
				
				
				break;
			case 2:
				System.out.println("Enter salarytype to change account salarytype");
				boolean salary = scanner.nextBoolean();
				try {
					savingsAccount = savingsAccountService.getAccountById(accountNumber);
					savingsAccount.setSalary(salary);
					savingsAccountService.updateAccount(savingsAccount);
				} catch (ClassNotFoundException| SQLException| AccountNotFoundException  e1) {
					e1.printStackTrace();
				} 
				
				break;
			case 3:
				System.out.println("enter the salary type and accountHolderName");
				accountHolderName = scanner.nextLine();
				salary = scanner.nextBoolean();
				try {
					savingsAccount = savingsAccountService.getAccountById(accountNumber);
					savingsAccount.setSalary(salary);
					savingsAccount.getBankAccount().setAccountHolderName(accountHolderName);
					savingsAccountService.updateAccount(savingsAccount);
				} catch (ClassNotFoundException| SQLException| AccountNotFoundException e1) {
			
					e1.printStackTrace();
				} 
>>>>>>> 3053768e58fdf059d7b8833ed024defd4dbf2d5c
				break;
			default:
				System.err.println("Invalid Choice!");
				break;
<<<<<<< HEAD

			}
		} catch (ClassNotFoundException | SQLException
				| AccountNotFoundException e1) {
			e1.printStackTrace();
		}
=======
				
			}
		}catch (ClassNotFoundException | SQLException | AccountNotFoundException e1) {
			e1.printStackTrace();
		}		
>>>>>>> 3053768e58fdf059d7b8833ed024defd4dbf2d5c
	}

	private static void currentBalance() {

		System.out.println("Enter account number to search");
		int accountNumber = scanner.nextInt();
		try {
			savingsAccountService.checkAccountBalance(accountNumber);
			System.out.println("current balance"
					+ savingsAccountService.checkAccountBalance(accountNumber));
			DBUtil.commit();
<<<<<<< HEAD
		} catch (ClassNotFoundException | SQLException | InvalidInputException e) {
=======
		} 
		catch (ClassNotFoundException | SQLException | InvalidInputException e) {
>>>>>>> 3053768e58fdf059d7b8833ed024defd4dbf2d5c
			e.printStackTrace();
		}

	}

	private static void deleteAccount() {
		System.out.println("Enter account number to delete");
		int deleteaccountNumber = scanner.nextInt();
		try {
			savingsAccountService.deleteAccount(deleteaccountNumber);
			DBUtil.commit();
		} catch (ClassNotFoundException | SQLException | InvalidInputException e) {
			e.printStackTrace();
		}

	}

	private static void fundTransfer() {
		System.out.println("Enter Account Sender's Number: ");
		int senderAccountNumber = scanner.nextInt();
		System.out.println("Enter Account Receiver's Number: ");
		int receiverAccountNumber = scanner.nextInt();
		System.out.println("Enter Amount: ");
		double amount = scanner.nextDouble();
		try {
<<<<<<< HEAD
			SavingsAccount senderSavingsAccount = savingsAccountService
					.getAccountById(senderAccountNumber);
			SavingsAccount receiverSavingsAccount = savingsAccountService
					.getAccountById(receiverAccountNumber);
			savingsAccountService.fundTransfer(senderSavingsAccount,
					receiverSavingsAccount, amount);
		} catch (ClassNotFoundException | SQLException | InvalidInputException
				| AccountNotFoundException | InsufficientFundsException e) {
=======
			SavingsAccount senderSavingsAccount = savingsAccountService.getAccountById(senderAccountNumber);
			SavingsAccount receiverSavingsAccount = savingsAccountService.getAccountById(receiverAccountNumber);
			savingsAccountService.fundTransfer(senderSavingsAccount, receiverSavingsAccount, amount);
		} catch (ClassNotFoundException | SQLException | InvalidInputException| AccountNotFoundException e) {
>>>>>>> 3053768e58fdf059d7b8833ed024defd4dbf2d5c
			e.printStackTrace();
		}
	}

	private static void deposit() {
		System.out.println("Enter Account Number: ");
		int accountNumber = scanner.nextInt();
		System.out.println("Enter Amount: ");
		double amount = scanner.nextDouble();
		SavingsAccount savingsAccount = null;
		try {
			savingsAccount = savingsAccountService
					.getAccountById(accountNumber);
			savingsAccountService.deposit(savingsAccount, amount);
			DBUtil.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			try {
				DBUtil.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} catch (Exception e) {
			try {
				DBUtil.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	private static void withdraw() {
		System.out.println("Enter Account Number: ");
		int accountNumber = scanner.nextInt();
		System.out.println("Enter Amount: ");
		double amount = scanner.nextDouble();
		SavingsAccount savingsAccount = null;
		try {
			savingsAccount = savingsAccountService
					.getAccountById(accountNumber);
			savingsAccountService.withdraw(savingsAccount, amount);
			DBUtil.commit();
		} catch (ClassNotFoundException | SQLException
				| AccountNotFoundException e) {
			try {
				DBUtil.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} catch (Exception e) {
			try {
				DBUtil.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	private static void sortMenu(String sortWay) {
		do {
			System.out.println("+++++Ways of Sorting+++++++");
			System.out.println("1. Account Number");
			System.out.println("2. Account Holder Name");
			System.out.println("3. Account Balance");
			System.out.println("4. Exit from Sorting");

			int choice = scanner.nextInt();

		} while (true);

	}

	private static void showAllAccounts() {
		List<SavingsAccount> savingsAccounts;
		try {
			savingsAccounts = savingsAccountService.getAllSavingsAccount();
			for (SavingsAccount savingsAccount : savingsAccounts) {
				System.out.println(savingsAccount);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

	}

	private static void acceptInput(String type) {
		if (type.equalsIgnoreCase("SA")) {
			System.out.println("Enter your Full Name: ");
			String accountHolderName = scanner.nextLine();
			accountHolderName = scanner.nextLine();
			System.out
					.println("Enter Initial Balance(type na for Zero Balance): ");
			String accountBalanceStr = scanner.next();
			double accountBalance = 0.0;
			if (!accountBalanceStr.equalsIgnoreCase("na")) {
				accountBalance = Double.parseDouble(accountBalanceStr);
			}
			System.out.println("Salaried?(y/n): ");
			boolean salary = scanner.next().equalsIgnoreCase("n") ? false
					: true;
			createSavingsAccount(accountHolderName, accountBalance, salary);
		}
	}

	private static void createSavingsAccount(String accountHolderName,
			double accountBalance, boolean salary) {
		try {
			savingsAccountService.createNewAccount(accountHolderName,
					accountBalance, salary);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
