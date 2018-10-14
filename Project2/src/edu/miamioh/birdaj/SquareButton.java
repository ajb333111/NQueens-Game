package edu.miamioh.birdaj;

// Alec Bird
// CSE 271 Section B

import javax.swing.JButton;

/**
 * 
 * Describes a square on the board
 *
 */
public class SquareButton extends JButton{
	
	private int row;
	private int column;

	/**
	 * Creates a square button  
	 * @param row the row of the square
	 * @param col the column of the square
	 */
	public SquareButton(int row, int col){
		super();
		this.row = row;
		this.column = col;
	}

	/**
	 * Gets the row of the square button
	 * @return the row
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Gets the column of the square button
	 * @return the column
	 */
	public int getColumn() {
		return column;
	}


	
	
	
	
}
