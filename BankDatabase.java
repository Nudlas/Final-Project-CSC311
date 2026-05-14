import java.util.ArrayList;
import java.util.List;

public class BankDatabase {
    private final List<Account> accounts;

    public BankDatabase() {
        accounts = new ArrayList<>();

        accounts.add(new Account(12345, 54321, 1000.00, 1200.00));
        accounts.add(new Account(98765, 56789, 200.00, 200.00));
    }

    private Account getAccount(int accountNumber) {
        for (Account currentAccount : accounts) {
            if (currentAccount.getAccountNumber() == accountNumber) {
                return currentAccount;
            }
        }
        return null;
    }

    public boolean authenticateUser(int userAccountNumber, int userPIN) {
        Account userAccount = getAccount(userAccountNumber);
        return userAccount != null && userAccount.validatePIN(userPIN);
    }

    public double getAvailableBalance(int userAccountNumber) {
        return getAccount(userAccountNumber).getAvailableBalance();
    }

    public double getTotalBalance(int userAccountNumber) {
        return getAccount(userAccountNumber).getTotalBalance();
    }

    public void credit(int userAccountNumber, double amount) {
        getAccount(userAccountNumber).credit(amount);
    }

    public void debit(int userAccountNumber, double amount) {
        getAccount(userAccountNumber).debit(amount);
    }

    public boolean accountExists(int accountNumber) {
        return getAccount(accountNumber) != null;
    }
}
