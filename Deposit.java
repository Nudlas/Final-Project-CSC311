public class Deposit extends Transaction {
    private double amount;
    private final Keypad keypad;
    private final DepositSlot depositSlot;

    private static final int CANCELED = 0;

    public Deposit(int userAccountNumber, Screen atmScreen, BankDatabase atmBankDatabase,
                   Keypad atmKeypad, DepositSlot atmDepositSlot) {
        super(userAccountNumber, atmScreen, atmBankDatabase);
        keypad = atmKeypad;
        depositSlot = atmDepositSlot;
    }

    @Override
    public void execute() {
        BankDatabase bankDatabase = getBankDatabase();
        Screen screen = getScreen();

        amount = promptForDepositAmount();

        if (amount != CANCELED) {
            screen.displayMessageLine("Please insert a deposit envelope containing " +
                    String.format("$%,.2f", amount) + ".");

            if (depositSlot.isEnvelopeReceived()) {
                screen.displayMessageLine("Envelope received. The money will be available after verification.");
                bankDatabase.credit(getAccountNumber(), amount);
            } else {
                screen.displayMessageLine("No envelope detected. Canceling transaction.");
            }
        } else {
            screen.displayMessageLine("Canceling transaction...");
        }
    }

    private double promptForDepositAmount() {
        Screen screen = getScreen();
        screen.displayMessage("Please enter a deposit amount in CENTS (or 0 to cancel): ");
        int input = keypad.getInput();

        if (input == CANCELED) {
            return CANCELED;
        }

        return input / 100.0;
    }
}
