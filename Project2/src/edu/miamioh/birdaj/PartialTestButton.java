package edu.miamioh.birdaj;

// Alec Bird
// CSE 271 Section B

import java.util.ArrayList;

import javax.swing.JButton;

/**
 * 
 * Describes what a partial test button does
 *
 */
public class PartialTestButton extends JButton {

	private String text;

	private static final int NQUEENS = 8;
	private static final int ACCEPT = 1;
	private static final int ABANDON = 2;
	private static final int CONTINUE = 3;
	private ArrayList<Queen> list;

	public PartialTestButton(String text) {
		super(text);

	}

	/**
	 * Creates an array of Queens from an array of boolean values
	 * @param array the array of true and false
	 * @param size the size of the Queen array
	 * @return the Queen array
	 */
	public Queen[] changeArray(boolean[][] array, int size) {
		Queen[] temp = new Queen[size];
		int index = 0;
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[i].length; j++) {
				if (array[i][j] == true) {
					temp[index] = new Queen(i, j);
					index++;
				}
				if (index == size)
					return temp;
			}
		}
		return temp;
	}

	// Used Dr. Stephan's code from his Powerpoint in class for this method
	/**
	 * Examines whether two queens on the board attack each other
	 * @param queens the Queen array to be checked through
	 * @return either 1,2,3 if the partial solution is a full solution, bad, or good
	 */
	public int examine(Queen[] queens) {
		for (int i = 0; i < queens.length; i++) {

			for (int j = i + 1; j < queens.length; j++) {

				if (queens[i].attacks(queens[j])) {
					return ABANDON;

				}
			}
		}
		if (queens.length == NQUEENS) {
			return ACCEPT;
		} else {
			return CONTINUE;
		}
	}

	/**
	 * Creates a message based off the current solution
	 * @param num the number that tells if the solution is good
	 * @return the message
	 */
	public String checkIfSafe(int num) {
		if (num == PartialTestButton.CONTINUE) {
			return "Queens are safe.";
		} else if (num == PartialTestButton.ABANDON) {
			return "Queens are not safe.";
		} else {
			return "Solution found!";
		}
	}

	/**
	 * Checks which queens on the board are not safe and adds them to a list
	 * @param queens the array of Queens
	 * @return an ArrayList of unsafe queens
	 */
	public ArrayList<Queen> getNotSafe(Queen[] queens) {
		list = new ArrayList<Queen>();
		for (int i = 0; i < queens.length; i++) {

			for (int j = i + 1; j < queens.length; j++) {

				if (queens[i].attacks(queens[j])) {
					list.add(queens[i]);
					list.add(queens[j]);
				}
			}
		}
		return list;
	}

	
	

}
