public class Withdrawal extends Transaction {
    private int amount;
    private final Keypad keypad;
    private final CashDispenser cashDispenser;

    private static final int CANCELED = 6;

    public Withdrawal(int userAccountNumber, Screen atmScreen, BankDatabase atmBankDatabase,
                      Keypad atmKeypad, CashDispenser atmCashDispenser) {
        super(userAccountNumber, atmScreen, atmBankDatabase);
        keypad = atmKeypad;
        cashDispenser = atmCashDispenser;
    }

    @Override
    public void execute() {
        BankDatabase bankDatabase = getBankDatabase();
        Screen screen = getScreen();

        boolean cashDispensed = false;

        do {
            amount = displayMenuOfAmounts();

            if (amount != CANCELED) {
                double availableBalance = bankDatabase.getAvailableBalance(getAccountNumber());

                if (amount <= availableBalance) {
                    if (cashDispenser.isSufficientCashAvailable(amount)) {
                        bankDatabase.debit(getAccountNumber(), amount);
                        cashDispenser.dispenseCash(amount);
                        cashDispensed = true;
                    } else {
                        screen.displayMessageLine("Insufficient cash available in the ATM.");
                    }
                } else {
                    screen.displayMessageLine("Insufficient funds in your account.");
                }
            } else {
                screen.displayMessageLine("Canceling transaction...");
                return;
            }
        } while (!cashDispensed);
    }

    private int displayMenuOfAmounts() {
        int[] amounts = {0, 20, 40, 60, 100, 200};

        Screen screen = getScreen();
        screen.displayMessageLine("\nWithdrawal menu:");
        screen.displayMessageLine("1 - $20");
        screen.displayMessageLine("2 - $40");
        screen.displayMessageLine("3 - $60");
        screen.displayMessageLine("4 - $100");
        screen.displayMessageLine("5 - $200");
        screen.displayMessageLine("6 - Cancel transaction");
        screen.displayMessage("Choose a withdrawal amount: ");

        int input = keypad.getInput();

        if (input >= 1 && input <= 5) {
            return amounts[input];
        }
        if (input == CANCELED) {
            return CANCELED;
        }

        screen.displayMessageLine("Invalid selection. Try again.");
        return displayMenuOfAmounts();
    }
}
