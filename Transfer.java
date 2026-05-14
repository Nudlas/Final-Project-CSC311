public class Transfer extends Transaction {
    private final Keypad keypad;
    private static final int CANCELED = 0;

    public Transfer(int userAccountNumber, Screen atmScreen, BankDatabase atmBankDatabase, Keypad atmKeypad) {
        super(userAccountNumber, atmScreen, atmBankDatabase);
        keypad = atmKeypad;
    }

    @Override
    public void execute() {
        Screen screen = getScreen();
        BankDatabase bankDatabase = getBankDatabase();

        // New feature: transfer money from the current user account to another account.
        screen.displayMessage("Enter target account number (or 0 to cancel): ");
        int targetAccountNumber = keypad.getInput();

        if (targetAccountNumber == CANCELED) {
            screen.displayMessageLine("Canceling transaction...");
            return;
        }

        if (!bankDatabase.accountExists(targetAccountNumber)) {
            screen.displayMessageLine("Target account not found.");
            return;
        }

        if (targetAccountNumber == getAccountNumber()) {
            screen.displayMessageLine("You cannot transfer to the same account.");
            return;
        }

        screen.displayMessage("Enter transfer amount in CENTS (or 0 to cancel): ");
        int amountInCents = keypad.getInput();

        if (amountInCents == CANCELED) {
            screen.displayMessageLine("Canceling transaction...");
            return;
        }

        double amount = amountInCents / 100.0;
        double availableBalance = bankDatabase.getAvailableBalance(getAccountNumber());

        if (amount <= 0) {
            screen.displayMessageLine("Invalid transfer amount.");
            return;
        }

        if (availableBalance >= amount) {
            bankDatabase.debit(getAccountNumber(), amount);
            bankDatabase.credit(targetAccountNumber, amount);
            screen.displayMessageLine("Transfer successful.");
        } else {
            screen.displayMessageLine("Insufficient funds.");
        }
    }
}
