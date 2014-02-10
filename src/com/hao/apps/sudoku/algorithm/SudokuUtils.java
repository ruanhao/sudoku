package com.hao.apps.sudoku.algorithm;

public class SudokuUtils {
	

		
		
		
		
	public static String generateSudokuString(){
		SudokuAlgorithm sudoku = new SudokuAlgorithm();
		sudoku.generateRandom(1);
		return sudoku.returnSudokuPuzzleString();
	}
	
	public static String drillPuzzle(){
		
		StringBuffer puzzleString = new StringBuffer(generateSudokuString());
		
		int[] num = new int[81];
		for (int i = 0 ; i < num.length; i++){
			num[i] = i;
		}
		int n = num.length;
		int[] result = new int[n];
		for (int i = 0; i < result.length; i++) {
			int r = (int) (Math.random() * n);
			result[i] = num[r];
			num[r] = num[n - 1];
			n--;
		}
		
		for (int i = 0; i < 56; i++){
			puzzleString.setCharAt(result[i], '0');
		}
		
		return puzzleString.toString();
	}

}
