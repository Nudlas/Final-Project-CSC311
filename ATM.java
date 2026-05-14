public class ATM {
    private boolean userAuthenticated;
    private int currentAccountNumber;

    private final Screen screen;
    private final Keypad keypad;
    private final CashDispenser cashDispenser;
    private final DepositSlot depositSlot;
    private final BankDatabase bankDatabase;

    private static final int BALANCE_INQUIRY = 1;
    private static final int WITHDRAWAL = 2;
    private static final int DEPOSIT = 3;
    private static final int TRANSFER = 4;
    private static final int EXIT = 5;

    public ATM() {
        userAuthenticated = false;
        currentAccountNumber = 0;
        screen = new Screen();
        keypad = new Keypad();
        cashDispenser = new CashDispenser();
        depositSlot = new DepositSlot();
        bankDatabase = new BankDatabase();
    }

    public void run() {
        screen.displayMessageLine("Welcome!");

        while (true) {
            while (!userAuthenticated) {
                authenticateUser();
            }

            performTransactions();
            userAuthenticated = false;
            currentAccountNumber = 0;
            screen.displayMessageLine("\nThank you! Goodbye!\n");
        }
    }

    private void authenticateUser() {
        screen.displayMessage("\nPlease enter your account number: ");
        int accountNumber = keypad.getInput();
        screen.displayMessage("Enter your PIN: ");
        int pin = keypad.getInput();

        userAuthenticated = bankDatabase.authenticateUser(accountNumber, pin);

        if (userAuthenticated) {
            currentAccountNumber = accountNumber;
        } else {
            screen.displayMessageLine("Invalid account number or PIN. Please try again.");
        }
    }

    private void performTransactions() {
        boolean userExited = false;

        while (!userExited) {
            int mainMenuSelection = displayMainMenu();
            Transaction currentTransaction = null;

            switch (mainMenuSelection) {
                case BALANCE_INQUIRY:
                    currentTransaction = new BalanceInquiry(currentAccountNumber, screen, bankDatabase);
                    break;
                case WITHDRAWAL:
                    currentTransaction = new Withdrawal(currentAccountNumber, screen, bankDatabase, keypad, cashDispenser);
                    break;
                case DEPOSIT:
                    currentTransaction = new Deposit(currentAccountNumber, screen, bankDatabase, keypad, depositSlot);
                    break;
                case TRANSFER:
                    currentTransaction = new Transfer(currentAccountNumber, screen, bankDatabase, keypad);
                    break;
                case EXIT:
                    screen.displayMessageLine("Exiting the system...");
                    userExited = true;
                    break;
                default:
                    screen.displayMessageLine("You did not enter a valid selection. Try again.");
                    break;
            }

            if (currentTransaction != null) {
                currentTransaction.execute();
            }
        }
    }

    private int displayMainMenu() {
        screen.displayMessageLine("\nMain menu:");
        screen.displayMessageLine("1 - View my balance");
        screen.displayMessageLine("2 - Withdraw cash");
        screen.displayMessageLine("3 - Deposit funds");
        screen.displayMessageLine("4 - Transfer funds");
        screen.displayMessageLine("5 - Exit");
        screen.displayMessage("Enter a choice: ");
        return keypad.getInput();
    }
}
