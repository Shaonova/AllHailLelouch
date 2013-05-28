package Final;

import java.util.Scanner;

public class Runner {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		boolean valid = false, con = true;
		Nim nim = null;
		String cont;
		int rowCount, maxSticks;
		while (con) {
			while (!valid) {
				try {
					System.out
							.println("How many rows would you like the board to have?");
					rowCount = scan.nextInt();
					System.out
							.println("How many sticks should each row be able to hold?");
					maxSticks = scan.nextInt();
					if ((rowCount % 2 == 0 && maxSticks == 1) || maxSticks < 1
							|| rowCount < 1) {
						System.out
								.println("Please enter different dimensions.");
					} else {
						nim = new Nim(rowCount, maxSticks);
						valid = true;
					}
				} catch (Exception e) {
					System.out.println("Error, please enter valid data.");
				}
			}
			if (nim.decimalSum(nim.pile()) != 0)
				System.out.println(nim.toString());
			while (nim.decimalSum(nim.pile()) != 0) {
				nim.personTurn();
				if (nim.decimalSum(nim.pile()) == 0) {
					System.out.println("GGWP");
					break;
				}
				nim.consoleTurn();
			}
			try {
				System.out.println("Play Again? Y/N");
				cont = scan.next();
				if (cont.equalsIgnoreCase("n"))
					con = false;
				else if (cont.equalsIgnoreCase("y"))
					valid = false;
				else
					throw new Exception();
			} catch (Exception e) {
				System.out.println("Invalid data. Please re-enter choice.");
			}
		}
	}
}