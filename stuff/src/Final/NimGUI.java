package Final;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.util.Scanner;

public class NimGUI extends JFrame {
	private JButton sticks[][];
	private JButton finishTurn;
	private GridBagConstraints c;
	private Nim nim;
	private Event e;
	private Scanner scan;

	private int rows, maxSize;

	public NimGUI() {
		super("Beat Me");
		e = new Event();
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		scan = new Scanner(System.in);
		nim=null;
		 boolean valid=false;
		 while (!valid) {
				try {
					System.out.println("How many rows would you like the board to have?");
					rows = scan.nextInt();
					System.out.println("How many sticks should each row be able to hold?");
					maxSize = scan.nextInt();
					if((rows%2==0 && maxSize==1) || (rows%2==1 && maxSize==2)){
						System.out.println("Please choose different dimensions.");
					}
					else{
					 nim = new Nim(rows, maxSize);
					 valid=true;}
				} catch (Exception e) {
					System.out.println("Error, please enter valid data.");
				}
		 }
		setLayout(new GridBagLayout());
		c = new GridBagConstraints();
		sticks = new JButton[rows][maxSize];
		for(int r=0; r < rows; r++){
			makeRow(r,nim.pile()[r]);
			addToGrid(r, maxSize+1,new JLabel("Row "+(r+1)));
		}
		finishTurn = new JButton("Finish Turn?");
		addToGrid(rows,maxSize+1,finishTurn);
		pack();
		 }

	public void makeRow(int rowNum, int rowWidth) {
		for (int x = 0; x < rowWidth; x++) {
			sticks[rowNum][x] = new JButton("|");
			addToGrid(rowNum, x, sticks[rowNum][x]);
		}
	}

	public void addToGrid(int row, int x, JButton button){
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = x;
		c.gridy = row;
		button.addActionListener(e);
		add(button, c);
	}

	public void addToGrid(int row, int x, JLabel label){
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = x;
		c.gridy = row;
		add(label, c);
	}

	public static void main(String[] args) {
		new NimGUI();
	}
	public class Event implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (arg0.getSource()==finishTurn){
				if(finishTurn.getText().equals("GGWP")){
					setVisible(false);
					new NimGUI();
				}	
				if(!isLegal())
					finishTurn.setText("Try Again");
				else{
					playerInput();
					if(nim.decimalSum(nim.pile())==0){
						finishTurn.setText("GGWP");
					System.out.println("Press the GGWP button to play again.");
					}
					else{
					gameUpdate();
					finishTurn.setText("Finish Turn?");
					}	
				}
			}
			stickListener(arg0.getSource());
			pack();
		}
	}

	public void stickListener(Object source){
		for(int r=0; r < rows; r++){
			for(int x = 0; x < maxSize; x++){
				JButton stick = sticks[r][x];
				if(stick != null && source == stick)
					if(stick.getText().equals("|"))
						stick.setText("X");
					else
						stick.setText("|");
			}
		}
	}

	public int rowCount(int row){
		int count = 0;
		for(int x = 0; x < maxSize; x++){
			JButton stick = sticks[row][x];
			if(stick != null && stick.getText().equals("|"))
				count++;
		}
		return count;
	}

	public int rowChange(int row){
		return Math.abs(nim.pile()[row] - rowCount(row));
	}

	public boolean isLegal(){
		int count = 0;
		for(int r = 0; r < rows; r++)
			if(count > 1)
				return false;
			else if(rowChange(r) != 0)
				count++;
		return count == 1;
	}

	public void playerInput(){
		int targetRow = -1;
		for(int r = 0; r < rows; r++)
			if(rowChange(r) != 0){
				targetRow = r;
				break;
			}
		nim.personTurn(targetRow, rowChange(targetRow));
		for(JButton[] row: sticks)
			for(JButton stick: row)
				if(stick != null && stick.getText().equals("X")){
					remove(stick);
					stick = null;
				}
	}
	public void gameUpdate(){
		nim.gameTurn();
	int tr = -1;
	for(int r = 0; r < rows; r++)
		if(rowChange(r) != 0){
			tr = r;
				break;
			}
		for(int x = 0; rowChange(tr) != 0; x++){
			if(sticks[tr][x] != null){
				remove(sticks[tr][x]);
				sticks[tr][x]= null;
			}
		}
	}

}