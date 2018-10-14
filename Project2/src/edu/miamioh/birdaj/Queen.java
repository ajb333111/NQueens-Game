package edu.miamioh.birdaj;

// Alec Bird
// CSE 271 Section B

import java.awt.Image; 
import javax.swing.ImageIcon;

/**
 * 
 * Describes a queen piece 
 *
 */
public class Queen {
	
	private int row; 
	private int col;

	/**
	 * Creates a queen
	 * @param row the row the queen is in
	 * @param col the column the queen is in
	 */
	public Queen(int row, int col){
		super();		
		this.row = row;
		this.col = col;
	}
	
	/**
	 * Creates an image of the queen
	 * @return the queen icon
	 */
	public ImageIcon createImage(){
		ImageIcon icon = new ImageIcon(getClass().getResource("/ress/crownQueen.jpg"));
		Image image = icon.getImage();
		Image newImage = image.getScaledInstance(55, 55, java.awt.Image.SCALE_SMOOTH); //used Image API to find image scaling algorithm
		ImageIcon newQ = new ImageIcon(newImage);
		return newQ;
	}
	
	// Used Dr. Stephan's code from his Powerpoint in class for this method
	/**
	 * Checks whether a queen attacks another queen
	 * @param other the other queen
	 * @return true if the queens attack each other, false if not
	 */
	public boolean attacks(Queen other){
		return row == other.row
				|| col == other.col
				|| Math.abs(row - other.row) == Math.abs(col - other.col);
				
	}

	/**
	 * Gets the row of the queen
	 * @return the row
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Gets the column of the queen
	 * @return the column
	 */
	public int getCol() {
		return col;
	}

	/**
	 * Creates a string for a queen
	 */
	public String toString(){
		return row + "," + col;
	}
	
	
}
