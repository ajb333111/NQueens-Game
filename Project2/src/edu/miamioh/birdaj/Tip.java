package edu.miamioh.birdaj;

// Alec Bird
// CSE 271 Section B

import java.util.Arrays; 
import javax.swing.JButton;

/**
 * 
 * Describes the function of the tip button
 * Tells the player a safe spot to put their queen
 */
public class Tip extends JButton{

	private String text;
	
	/**
	 * Creates a tip button
	 * @param text the text on the button
	 */
	public Tip(String text){
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
	
	/**
	 * Looks through all the queens on the board to find a safe spot to put a new one
	 * @param queens the current list of Queens on the board
	 * @return the square where a new queen could go 
	 */
	public SquareButton testSol(Queen[] queens){
		SquareButton button;
		int i,j,k;
			for(i = 0; i< 8; i++){
				for(j = 0; j< 8; j++){
					int counter = 0;
					for(k = 0; k< queens.length; k++){
					Queen sub = new Queen(i,j);
					if(!(sub.attacks(queens[k]))){
					counter++;
				}
					if(counter == queens.length){
						button = new SquareButton(i,j);
						return button;
					}
				}
			}
		}
		// If there are no queens on the board, place one randomly
		if(queens.length == 0){
			int row = (int)(Math.random() * 8);
			int col = (int)(Math.random() * 8);
			button = new SquareButton(row,col);
			return button;
		}
		// If there are no safe spots, return a button not on the board
		button = new SquareButton(-1,-1);
		return button;
	}	
}
