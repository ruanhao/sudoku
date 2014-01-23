package com.hao.apps.sudoku;

import android.app.Activity;
import android.os.Bundle;

public class Game extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		puzzleView = new PuzzleView(Game.this);
		setContentView(puzzleView);
	}

	
	public String getTileString(int i, int j){
		int r = (int) (Math.random() * 10);
		return String.valueOf(r);
	}
	
	
	private static final String TAG = "Sudoku";
	public static final String KEY_DIFFICULTY = "difficulty";
	public static final int DIFFICULTY_EASY = 0;
	public static final int DIFFICULTY_MEDIUM = 1;
	public static final int DIFFICULTY_HARD = 2;
	
	private PuzzleView puzzleView = null;
	
}
