import java.util.Scanner;

public class Keypad {
    private final Scanner input;

    public Keypad() {
        input = new Scanner(System.in);
    }

    public int getInput() {
        while (!input.hasNextInt()) {
            input.next();
            System.out.print("Please enter a whole number: ");
        }
        return input.nextInt();
    }
}
