package Final;
import java.util.Scanner;
public class Runner {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		boolean valid = false;
		Nim nim=null;
		while (!valid) {
			try {
				System.out.println("How many rows would you like the board to have?");
				int rowCount = scan.nextInt();
				System.out.println("How many sticks should each row be able to hold?");
				int maxSticks = scan.nextInt();
				 nim = new Nim(rowCount, maxSticks);
				 valid=true;
			} catch (Exception e) {
				System.out.println("Error, please enter valid data.");
			}
		}
		while (nim.decimalSum(nim.pile()) != 0) {
			nim.personTurn();
			if (nim.decimalSum(nim.pile()) == 0) {
				System.out.println("GGWP");
				break;
			}
			nim.gameTurn();
			if (nim.decimalSum(nim.pile()) == 0) {
				System.out.println("You have won somehow. Time to look at the code again.");
				break;
			}
		}
	}
}