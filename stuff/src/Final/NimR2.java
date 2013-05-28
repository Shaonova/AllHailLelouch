package Final;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class NimR2 extends JFrame {
	private JTextField rows, max;
	private JButton[][] sticks;
	private JLabel label1, label2, playAgain;
	private JLabel[] labels, ruleList;
	private JButton button, finishTurn, ruleDisplay;
	private GridBagConstraints c, d;
	private Event e;
	private Nim nim;
	private int row, maxSize;
	private JFrame rules;

	public NimR2() {
		super("Nim");
		e = new Event();
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridBagLayout());
		c = new GridBagConstraints();
		playAgain = new JLabel("Press GGWP to play again.");
		rulePrep();
		dimensionInput();
		pack();
	}

	public void rulePrep() {
		d = new GridBagConstraints();
		ruleList = new JLabel[3];
		ruleList[0] = new JLabel("1) Each of us takes turns taking sticks.");
		ruleList[1] = new JLabel(
				"2) You may take as many sticks as you like from one row per turn.");
		ruleList[2] = new JLabel("3) The last player to remove a stick loses.");
		rules = new JFrame("Rules");
		rules.setLayout(new GridBagLayout());
		d.fill = GridBagConstraints.HORIZONTAL;
		d.gridx = 0;
		for (int i = 0; i < 3; i++) {
			d.gridy = i;
			rules.add(ruleList[i], d);
		}
		rules.pack();
	}

	public void dimensionInput() {
		label1 = new JLabel("How many rows?");
		addToGrid(0, 0, label1);
		label2 = new JLabel("MaximumSticks/row?");
		addToGrid(1, 0, label2);
		rows = new JTextField(10);
		addToGrid(0, 1, rows);
		max = new JTextField(10);
		addToGrid(1, 1, max);
		button = new JButton("Input Complete");
		addButton(2, 0, button);
		ruleDisplay = new JButton("Rules");
		addButton(2, 1, ruleDisplay);
	}

	public void nimMaker() {
		nim = new Nim(row, maxSize);
		rows.setText("");
		max.setText("");
		remove(rows);
		remove(max);
		remove(button);
		remove(label1);
		remove(label2);
		remove(ruleDisplay);
		ruleDisplay = new JButton("Rules");
		addButton(row + 1, maxSize + 1, ruleDisplay);
		sticks = new JButton[row][maxSize];
		labels = new JLabel[row];
		for (int a = 0; a < row; a++) {
			makeRow(a, nim.pile()[a]);
			labels[a] = new JLabel("Row " + (a + 1));
			addToGrid(a, maxSize + 1, labels[a]);
		}
		finishTurn = new JButton("Finish Turn");
		addButton(row, maxSize + 1, finishTurn);
	}

	public void addButton(int row, int x, JButton button) {
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = x;
		c.gridy = row;
		button.addActionListener(e);
		add(button, c);
	}

	public void addToGrid(int row, int x, Component comp) {
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = x;
		c.gridy = row;
		add(comp, c);
	}

	private class Event implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			if (arg0.getSource() == ruleDisplay)
				rules.setVisible(true);
			else {
				if (arg0.getSource() == button) {
					try {
						row = Integer.parseInt(rows.getText());
						maxSize = Integer.parseInt(max.getText());
						if ((row % 2 == 0 && maxSize == 1) || maxSize < 1
								|| row < 1) {
							button.setText("Nice try, but no.");
							return;
						} else {
							nimMaker();
						}
					} catch (Exception e) {
						button.setText("Invalid input.");
					}
				} else {
					if (arg0.getSource() == finishTurn) {
						if (finishTurn.getText().equals("GGWP")) {
							remove(playAgain);
							remove(finishTurn);
							dimensionInput();
							finishTurn.setText("Finish Turn");
						} else {
							if (!isLegal())
								finishTurn.setText("Try Again");
							else {
								playerInput();
								if (nim.decimalSum(nim.pile()) == 0) {
									finishTurn.setText("GGWP");
									remove(ruleDisplay);
									for (int i = 0; i < row; i++)
										remove(labels[i]);
									addToGrid(row, 0, playAgain);
								} else {
									gameUpdate();
									finishTurn.setText("Finish Turn");
								}
							}
						}
					}
					stickListener(arg0.getSource());
				}
				pack();
			}
		}
	}

	public void stickListener(Object source) {
		JButton stick;
		for (int r = 0; r < row; r++) {
			for (int x = 0; x < maxSize; x++) {
				stick = sticks[r][x];
				if (stick != null && source == stick)
					if (stick.getText().equals("|"))
						stick.setText("X");
					else
						stick.setText("|");
			}
		}
	}

	public int rowCount(int row) {
		int count = 0;
		JButton stick;
		for (int x = 0; x < maxSize; x++) {
			stick = sticks[row][x];
			if (stick != null && stick.getText().equals("|"))
				count++;
		}
		return count;
	}

	public int rowChange(int row) {
		return Math.abs(nim.pile()[row] - rowCount(row));
	}

	public boolean isLegal() {
		int count = 0;
		for (int r = 0; r < row; r++)
			if (count > 1)
				return false;
			else if (rowChange(r) != 0)
				count++;
		return count == 1;
	}

	public void playerInput() {
		int targetRow = -1;
		for (int r = 0; r < row; r++)
			if (rowChange(r) != 0) {
				targetRow = r;
				break;
			}
		nim.personTurn(targetRow, rowChange(targetRow));
		for (JButton[] row : sticks)
			for (JButton stick : row)
				if (stick != null && stick.getText().equals("X")) {
					remove(stick);
					stick = null;
				}
	}

	public void gameUpdate() {
		nim.gameTurn();
		int tr = -1;
		for (int r = 0; r < row; r++)
			if (rowChange(r) != 0) {
				tr = r;
				break;
			}
		for (int x = 0; rowChange(tr) != 0; x++) {
			if (sticks[tr][x] != null) {
				remove(sticks[tr][x]);
				sticks[tr][x] = null;
			}
		}
	}

	public void makeRow(int rowNum, int rowWidth) {
		for (int x = 0; x < rowWidth; x++) {
			sticks[rowNum][x] = new JButton("|");
			addButton(rowNum, x, sticks[rowNum][x]);
		}
	}

	public static void main(String[] args) {
		new NimR2();
	}
}