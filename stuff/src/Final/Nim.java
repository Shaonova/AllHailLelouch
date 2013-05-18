package Final;
import java.util.Scanner;
import java.util.Arrays;
public class Nim {
	private int[] piles;
	public Nim(int rowCount, int maxSticks) {
		piles = new int[rowCount];
		for (int i = 0; i < rowCount; i++) {
			piles[i] = (int) (Math.random() * (maxSticks - 1) + 1);
		}
		while (binarySum(piles) != 0) {
			for (int i = 0; i < rowCount; i++) {
				piles[i] = (int) (Math.random() * (maxSticks - 1) + 1);
			}
		}
	}
	public int[] pile() {
		return piles;
	}
	public int toBinary(int dec) {
		return Integer.parseInt(Integer.toBinaryString(dec));
	}
	public int binarySum(int[] board) {
		int firstSum = 0;
		int finalSum = 0;
		for (int i = 0; i < board.length; i++)
			firstSum += toBinary(board[i]);
		int lim = (int) (Math.log10((double) firstSum) + 1);
		for (int i = 0; i < lim; i++) {
			if (firstSum % 2 == 1)
				finalSum += Math.pow(2, i);
			firstSum /= 10;
		}
		return finalSum;
	}
	public int decimalSum(int[] board) {
		int sum = 0;
		for (int i = 0; i < board.length; i++)
			sum += board[i];
		return sum;
	}
	public int numOnes(int[] board) {
		int ones = 0;
		for (int i = 0; i < board.length; i++)
			if (board[i] == 1)
				ones++;
		return ones;
	}
	public void gameTurn() {
		int tester[] = new int[piles.length];
		for(int i=0;i<piles.length;i++)
			tester[i]=piles[i];
		int bin;
		int dec;
		int ones;
		for (int i = 0; i < tester.length; i++) {
			while (tester[i] > -1) {
				bin = binarySum(tester);
				dec = decimalSum(tester);
				ones = numOnes(tester);
				if ((bin == 0 && ones != dec) || (bin == 1 && ones == dec)) {
					piles = tester;
					System.out.println("Gameboard after my turn:\n"	+ toString());
					return;
				}
				tester[i]--;
			}
			tester[i] = piles[i];
		}
	}
	public void personTurn() {
		Scanner scan = new Scanner(System.in);
		System.out.println(toString());
		try {
			System.out.println("Please enter which row you would like to remove from\n");
			int row = scan.nextInt();

			System.out.println("Please enter how many sticks you would like to remove\n");
			int amount = scan.nextInt();
			if (piles[row - 1] - amount < 0 && amount > 0) {
				System.out.println("Error, please start turn over.");
				personTurn();
				return;
			} else {
				piles[row - 1] -= amount;
				System.out.println("Gameboard after your turn:\n"+ this.toString());
			}
		} catch (Exception e) {
			System.out.println("Error, please start turn over.");
			personTurn();
		}
	}
	public String toString() {
		String output = "";
		for (int j = 0; j < piles.length; j++) {
			output = output + "row " + (j + 1) + ":";
			for (int i = 0; i < piles[j]; i++)
				output += "|";
			output += "\n";
		}
		return output;
	}
}