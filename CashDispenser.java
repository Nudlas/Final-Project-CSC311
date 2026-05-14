public class CashDispenser {
    private static final int INITIAL_COUNT = 500;
    private int count;

    public CashDispenser() {
        count = INITIAL_COUNT;
    }

    public void dispenseCash(int amount) {
        int billsRequired = amount / 20;
        count -= billsRequired;
        System.out.println("Please take your cash from the cash dispenser.");
    }

    public boolean isSufficientCashAvailable(int amount) {
        int billsRequired = amount / 20;
        return count >= billsRequired;
    }
}
