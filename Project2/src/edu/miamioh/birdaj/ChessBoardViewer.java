package edu.miamioh.birdaj;

// Alec Bird
// CSE 271 Section B Spring 2017

import javax.swing.JFrame;

/**
 * 
 * Runs the NQueens game
 *
 */
public class ChessBoardViewer {

	public static void main(String[] args) {
		
		// Creates a chess board and a title for the frame
		JFrame frame = new ChessBoard();
		frame.setTitle("NQueens Solver");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

	}

}
