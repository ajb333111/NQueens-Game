package edu.miamioh.birdaj;

// Alec Bird
// CSE 271 Section B

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * 
 * Describes a board for the NQueens game
 *
 */
public class ChessBoard extends JFrame {

	public static final int FRAME_WIDTH = 575;
	public static final int FRAME_HEIGHT = 575;

	private JPanel board;
	private JPanel buttonPanel;
	private JPanel labelPanel;
	private SquareButton button;
	private PartialTestButton check;
	private Tip tip;
	private SmartTip smartTip;
	private JLabel label;
	private JLabel label1;
	private JMenuBar bar;
	private JMenu menu;
	private JMenuItem item;
	private int Qcounter = 0;
	private Queen[] queens;
	private boolean[][] test = new boolean[8][8];
	private SquareButton[] buttonArray;

	/**
	 * Creates a new board
	 */
	public ChessBoard() {
		super();
		setSize(FRAME_WIDTH, FRAME_HEIGHT);

		createMenu();
		createPartialButton();
		createTipButtons();
		createSafeLabel();
		createButtonPanel();
		createBoard();
		createSpaces();

	}

	/**
	 * Creates the chess board
	 */
	public void createBoard() {
		board = new JPanel();
		board.setLayout(new GridLayout(8, 8));

	}

	/**
	 * Creates the individual squares on the board
	 */
	public void createSpaces() {
		// Makes an array of 64 spaces
		buttonArray = new SquareButton[64];
		int i = 1;
		int row = 1;
		int column = 1;
		boolean flag = true;
		while (i <= 64) {
			// Start in the top left at coordinates (0,0)
			button = new SquareButton(row - 1, column - 1);
			ActionListener listener = new ClickListener();
			button.addActionListener(listener);
			buttonArray[i - 1] = button;
			// Sets the color of each square
			if (i % 2 != 0 && flag == true) {
				button.setBackground(Color.BLACK);
			} else if (i % 2 == 0 && flag == true) {
				button.setBackground(Color.WHITE);
			} else if (i % 2 != 0 && flag == false) {
				button.setBackground(Color.WHITE);
			} else if (i % 2 == 0 && flag == false) {
				button.setBackground(Color.BLACK);
			}
			if (i % 8 == 0 && flag == true) {
				flag = false;
				i++;
				column = 0;
				row++;
			} else if (i % 8 == 0 && flag == false) {
				flag = true;
				i++;
				column = 0;
				row++;
			} else {
				i++;
			}
			column++;
			board.add(button);

		}
		add(board, BorderLayout.CENTER);
	}

	/**
	 * 
	 * Describes an ActionListener for the board
	 *
	 */
	class ClickListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			if (e.getSource() instanceof SquareButton) {
				SquareButton temp = (SquareButton) e.getSource();

				// Makes sure when you click on a square it makes the color black or white if it wasn't 
				changeColor(temp, temp.getRow(), temp.getColumn());

				// If a square is yellow from pressing the tip button and the user clicks
				// on a different square, change it back to black or white
				for (SquareButton ele : buttonArray) {
					if (ele.getBackground() == Color.YELLOW) {
						changeColor(ele, ele.getRow(), ele.getColumn());
					}
				}

				Queen queen = new Queen(temp.getRow(), temp.getColumn());
				// Add a queen to the board if the space is empty
				if (temp.getIcon() == null && Qcounter != 8) {
					temp.setIcon(queen.createImage());
					test[queen.getRow()][queen.getCol()] = true;
					Qcounter++;

				// Display a message if the user tries to put more than 8 queens on the board
				} else if (Qcounter == 8 && temp.getIcon() == null) {
					JFrame frame = new JFrame();
					JOptionPane.showMessageDialog(frame, "Cannot have more than 8 queens on the board at once.");
					
				// Remove the queen from the board if there is one present
				} else {
					temp.setIcon(null);
					test[queen.getRow()][queen.getCol()] = false;
					Qcounter--;
				// If there are 8 queens on the board, hide the tip and smart tip buttons
				}
				if (Qcounter == 8) {
					tip.setEnabled(false);
					smartTip.setEnabled(false);
				} else {
					tip.setEnabled(true);
					smartTip.setEnabled(true);
				}

			} else if (e.getSource() instanceof PartialTestButton) {
				PartialTestButton check = (PartialTestButton) e.getSource();
				queens = check.changeArray(test, Qcounter);
				int num = check.examine(queens);
				
				// If the partial solution is not good, then highlight the unsafe queens in red
				if (num == 2) {
					ArrayList<Queen> notValid = check.getNotSafe(queens);
					for (int i = 0; i < notValid.size(); i++) {
						int row = notValid.get(i).getRow();
						int col = notValid.get(i).getCol();
						SquareButton space = (SquareButton) board.getComponent(getLocation(row, col));
						space.setBackground(Color.RED);
					}
				// If the queens are safe, change the colors back to normal when clicked
				} else {
					for (SquareButton ele : buttonArray)
						if (ele.getBackground() != Color.YELLOW) {
							changeColor(ele, ele.getRow(), ele.getColumn());
						}
				}
				// Print whether the queens are safe or not
				label.setText(check.checkIfSafe(num));

			} else if (e.getSource() instanceof Tip) {
				Tip sol = (Tip) e.getSource();
				queens = sol.changeArray(test, Qcounter);
				SquareButton spot = sol.testSol(queens);
				int row = spot.getRow();
				int col = spot.getColumn();
				if (row >= 0 && col >= 0) {
					// Highlight the square that is a safe location in yellow
					int com = getLocation(row, col);
					SquareButton but = (SquareButton) board.getComponent(com);
					but.setBackground(Color.YELLOW);
					label1.setText("");
				// If there are no safe spots, let the user know
				} else
					label1.setText("No safe spot.");

			// Shows the rules of the game if the user clicks the help menu item
			} else if (e.getSource() instanceof JMenuItem) {
				JFrame helpFrame = new JFrame();
				JOptionPane.showMessageDialog(helpFrame, createRules());
			}
		}

	}

	/**
	 * Creates an ActionListener to be added to a button
	 * @param button the button 
	 */
	public void addAction(JButton button) {
		ActionListener listener = new ClickListener();
		button.addActionListener(listener);
	}

	/**
	 * Creates a PartialTestButton 
	 */
	public void createPartialButton() {
		check = new PartialTestButton("Check");
		addAction(check);
	}

	/**
	 * Creates a tip button and smart tip button
	 */
	public void createTipButtons() {
		tip = new Tip("Tip");
		addAction(tip);
		
		// Does not do anything when clicked because did not implement this feature
		smartTip = new SmartTip("Smart Tip");
		addAction(smartTip);

	}

	/**
	 * Creates a safe label and tip label
	 */
	public void createSafeLabel() {
		label = new JLabel("Place Queens on the board!");
		label1 = new JLabel("");

	}

	/**
	 * Creates the menu bar, menu, and menu item
	 */
	public void createMenu() {
		bar = new JMenuBar();
		menu = new JMenu("Help");
		item = new JMenuItem("How to play");
		menu.add(item);
		bar.add(menu);
		setJMenuBar(bar);
		ActionListener list = new ClickListener();
		item.addActionListener(list);
	}

	/**
	 * Creates panels to be added to the board
	 */
	public void createButtonPanel() {
		JPanel top = new JPanel(new BorderLayout());
		buttonPanel = new JPanel();
		labelPanel = new JPanel();
		labelPanel.add(label);
		labelPanel.add(label1);
		buttonPanel.add(check);
		buttonPanel.add(tip);
		buttonPanel.add(smartTip);
		top.add(buttonPanel, BorderLayout.CENTER);
		top.add(labelPanel, BorderLayout.SOUTH);
		add(top, BorderLayout.NORTH);
	}

	/**
	 * Gets the location of a space on the board
	 * @param row the row
	 * @param col the column
	 * @return the space number
	 */
	public int getLocation(int row, int col) {
		return (row * 8) + col;
	}

	/**
	 * Changes the color of a square on the board to black or white
	 * @param button the square to be changed
	 * @param row the row of the square
	 * @param col the column of the square
	 */
	public void changeColor(SquareButton button, int row, int col) {
		if (row % 2 == 0 && col % 2 == 0) {
			button.setBackground(Color.BLACK);
		} else if (row % 2 == 0 && col % 2 != 0) {
			button.setBackground(Color.WHITE);
		} else if (row % 2 != 0 && col % 2 == 0) {
			button.setBackground(Color.WHITE);
		} else
			button.setBackground(Color.BLACK);
	}

	/**
	 * Creates a paragraph of the rules of the game
	 * @return the rules 
	 */
	public String createRules() {
		String rules = "The objective of this game is to place 8 queens on the board in a way that they do NOT attack each other.\n"
				+ "Two queens attack each other if they are in the same row, column, or diagonal.\n"
				+ "Press the \"Check\" button to see if the queens currently on the board are safe. Unsafe queens will be highlighted in red.\n"
				+ "Press the \"Tip\" button if you are stuck. A safe location will be highlighted in yellow.\n"
				+ "(NOTE: Continually pressing the \"Tip\" button will not lead to a solution.)";

		return rules;
	}

}
