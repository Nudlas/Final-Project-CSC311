# Final-Project-CSC311

## Java Console ATM Project
This project is a beginner-friendly **console ATM simulation** written in Java.

### Features
- Log in with account number and PIN
- View available and total balances
- Withdraw cash
- Deposit funds
- Transfer funds to another account (extra feature)
- Exit

### Project files
Each class is in its own file:
- `ATMCaseStudy.java` (contains `main`, starts the app)
- `ATM.java`
- `Screen.java`
- `Keypad.java`
- `CashDispenser.java`
- `DepositSlot.java`
- `Account.java`
- `BankDatabase.java`
- `Transaction.java`
- `BalanceInquiry.java`
- `Withdrawal.java`
- `Deposit.java`
- `Transfer.java` (new feature)

## How to run
### 1) Make sure Java is installed
Check:
```bash
java -version
javac -version
```

### 2) Compile the project
From the project folder:
```bash
javac *.java
```

### 3) Run the program
```bash
java ATMCaseStudy
```

## Test accounts
You can log in with either of these:
1. Account: `12345`, PIN: `54321`
2. Account: `98765`, PIN: `56789`

## Main menu
After login, you will see:
1. View my balance
2. Withdraw cash
3. Deposit funds
4. Transfer funds
5. Exit

## Transfer feature notes
`Transfer.java` is the required extra feature.
- Enter target account number (or `0` to cancel)
- Enter transfer amount in cents (or `0` to cancel)
- If target exists and funds are enough, transfer succeeds

