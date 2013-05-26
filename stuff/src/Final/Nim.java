package Final;

import java.util.Scanner;

public class Nim {
	private int[] piles;
	private int maxBinaryLength;
	private Scanner scan;

	public Nim(int rowCount, int maxSticks) {
		piles = new int[rowCount];
		scan = new Scanner(System.in);
		maxBinaryLength = (int) (Math.log(maxSticks) / Math.log(2) + 1);
		for (int i = 0; i < rowCount; i++) {
			piles[i] = (int) (Math.random() * maxSticks + 1);
		}
		int bin = binarySum(piles);
		int dec = decimalSum(piles);
		int ones = numOnes(piles);
		while (!((bin == 0 && ones != dec) || (bin == 1 && ones == dec))) {
			for (int i = 0; i < rowCount; i++)
				piles[i] = (int) (Math.random() * maxSticks + 1);
			bin = binarySum(piles);
			dec = decimalSum(piles);
			ones = numOnes(piles);
		}
	}

	public int[] pile() {
		return piles;
	}

	private int binarySum(int[] board) {
		int[] binary = new int[board.length];
		int binarySum = 0;
		int count;
		for (int a = 0; a < binary.length; a++)
			binary[a] = Integer.parseInt(Integer.toBinaryString(board[a]));
		for (int i = 0; i < maxBinaryLength; i++) {
			count = 0;
			for (int j = 0; j < binary.length; j++) {
				if (binary[j] % 2 == 1)
					count++;
				binary[j] /= 10;
			}
			if (count % 2 == 1)
				binarySum += Math.pow(2, i);
		}
		return binarySum;
	}

	public int decimalSum(int[] board) {
		int sum = 0;
		for (int i = 0; i < board.length; i++)
			sum += board[i];
		return sum;
	}

	private int numOnes(int[] board) {
		int ones = 0;
		for (int i = 0; i < board.length; i++)
			if (board[i] == 1)
				ones++;
		return ones;
	}

	public void consoleTurn() {
		int tester[] = new int[piles.length];
		int bin, dec, ones;
		for (int i = 0; i < piles.length; i++)
			tester[i] = piles[i];
		for (int i = 0; i < tester.length; i++) {
			while (tester[i] > -1) {
				bin = binarySum(tester);
				dec = decimalSum(tester);
				ones = numOnes(tester);
				if ((bin == 0 && ones != dec) || (bin == 1 && ones == dec)) {
					piles = tester;
					System.out.println("Gameboard after my turn:\n"
							+ toString());
					return;
				}
				tester[i]--;
			}
			tester[i] = piles[i];
		}
	}

	public void personTurn() {
		int row, amount;
		try {
			System.out
					.println("Please enter which row you would like to remove from\n");
			row = scan.nextInt();
			System.out
					.println("Please enter how many sticks you would like to remove\n");
			amount = scan.nextInt();
			if (piles[row - 1] - amount < 0 && amount > 0) {
				System.out.println("Error, please start turn over.");
				personTurn();
				return;
			} else {
				piles[row - 1] -= amount;
				System.out.println("Gameboard after your turn:\n"
						+ this.toString());
			}
		} catch (Exception e) {
			System.out.println("Error, please start turn over.");
			personTurn();
		}
	}

	public void personTurn(int row, int amount) {
		piles[row] -= amount;
	}

	public void gameTurn() {
		int tester[] = new int[piles.length];
		int bin, dec, ones;
		for (int i = 0; i < piles.length; i++)
			tester[i] = piles[i];
		for (int i = 0; i < tester.length; i++) {
			while (tester[i] > -1) {
				bin = binarySum(tester);
				dec = decimalSum(tester);
				ones = numOnes(tester);
				if ((bin == 0 && ones != dec) || (bin == 1 && ones == dec)) {
					piles = tester;
					return;
				}
				tester[i]--;
			}
			tester[i] = piles[i];
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