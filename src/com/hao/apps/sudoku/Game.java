package com.hao.apps.sudoku;

import com.hao.apps.sudoku.R;
import com.hao.apps.sudoku.algorithm.SudokuUtils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

public class Game extends Activity {

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		this.pm = (PowerManager) getSystemService(POWER_SERVICE);
		this.wl = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK
				| PowerManager.ON_AFTER_RELEASE, "Sudoku");
		setPowermanagerOn();
		
		Music.startMusic(getApplicationContext());
		
		super.onResume();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		super.onCreate(savedInstanceState);
		
		SharedPreferences sp = getSharedPreferences("PREF", Context.MODE_PRIVATE);
		sp.edit().putInt("flag", 1).commit();
		
		for (int i = 0; i < testPenArr.length; i++){
			testPenArr[i] = 0;
		}
		
		
		int diff = getIntent().getIntExtra(Game.KEY_DIFFICULTY, 0);
		puzzle = getPuzzle(diff);
		if (diff == CONTINUE_KEY){
			String pre = getPreferences(Context.MODE_PRIVATE).getString(
					CONTINUE_PUZZLE_PREDEFINE, "");
			
				String[] splitedArray = pre.split(",");

				int[] temp = new int[splitedArray.length];
				for (int i = 0; i < splitedArray.length; i++) {
					temp[i] = Integer.parseInt(splitedArray[i]);
				}
				preDefinedValuesIndex = temp;

				String testPenPreferenceString = getPreferences(
						Context.MODE_PRIVATE).getString(TEST_PEN, "");
				testPenArr = fromPuzzleString(testPenPreferenceString);

				PEN_STATE_BEFORE_INT = getPreferences(Context.MODE_PRIVATE)
						.getInt(PEN_STATE_BEFORE, 0);
			
			
			
			
			
		}else{
			getPreDefinedValueIndex();
		}
		
		
		
		calculateUsedTiles();
		
		
		
		
		puzzleView = new PuzzleView(Game.this);
		setContentView(puzzleView);
		
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		getPreferences(Context.MODE_PRIVATE).edit().putString(CONTINUE_PUZZLE, puzzleToString()).commit();
		getPreferences(Context.MODE_PRIVATE).edit().putString(CONTINUE_PUZZLE_PREDEFINE, preDefinedIndexToString()).commit();
		if (isPenUsed){
			getPreferences(Context.MODE_PRIVATE).edit().putInt(PEN_STATE_BEFORE, 1).commit();
		}else{
			getPreferences(Context.MODE_PRIVATE).edit().putInt(PEN_STATE_BEFORE, 0).commit();

		}
		getPreferences(Context.MODE_PRIVATE).edit().putString(TEST_PEN, testPenArrayToString()).commit();
		setPowermanagerOff();
		Music.stop(getApplicationContext());
		super.onPause();
	}

	

	private String testPenArrayToString() {
		// TODO Auto-generated method stub
		String temp = "";
		for (int i = 0; i < testPenArr.length; i++){
			temp = temp + testPenArr[i];
		}
		return temp;
	}

	private String preDefinedIndexToString() {
		// TODO Auto-generated method stub
		String temp = "";
		for (int i = 0; i < preDefinedValuesIndex.length; i++){
			if (i == preDefinedValuesIndex.length - 1){
				temp = temp + preDefinedValuesIndex[i];
			}else{
				
				temp = temp + preDefinedValuesIndex[i] + ",";
			}
			
		}
		return temp;
	}

	private String puzzleToString() {
		// TODO Auto-generated method stub
		String temp = "";
		for (int i = 0; i < puzzle.length; i++){
			temp = temp + puzzle[i];
		}
		return temp;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		
		MenuInflater mi = getMenuInflater();
		mi.inflate(R.menu.gamemenu, menu);
		
		return super.onCreateOptionsMenu(menu);
	}

	private int[] getPuzzle(int diff) {
		// TODO Auto-generated method stub
		String temp;
		switch (diff) {
		case Game.DIFFICULTY_EASY:
			temp = easyPuzzle;
			break;
		case Game.DIFFICULTY_MEDIUM:
			temp = mediumPuzzle;
			break;
		case Game.DIFFICULTY_HARD:
			temp = SudokuUtils.drillPuzzle();
			break;
		case Game.CONTINUE_KEY:
			
			temp = getPreferences(Context.MODE_PRIVATE).getString(CONTINUE_PUZZLE, easyPuzzle);
			break;
		default:
			temp = easyPuzzle;
			break;
		}
		return fromPuzzleString(temp);
	}

	private int[] fromPuzzleString(String temp) {
		// TODO Auto-generated method stub
		int[] intArr = new int[temp.length()];
		for (int i = 0; i < temp.length(); i++) {
			intArr[i] = temp.charAt(i) - '0';
		}
		return intArr;
	}

	public String getTileString(int i, int j) {
		if (puzzle[9 * j + i] != 0) {
			return String.valueOf(puzzle[9 * j + i]);
		} else {
			return "";
		}
	}

	public void showKeypad(int x, int y) {

		if (isInPreDefinedValues(y * 9 + x)) {
			puzzleView.setAnimation(AnimationUtils.loadAnimation(Game.this,
					R.anim.shake));
			switch (wrongClickNumber) {
			case 0:
				Toast.makeText(Game.this, R.string.wrong, Toast.LENGTH_SHORT)
						.show();
				break;
			case 1:
				Toast.makeText(Game.this, R.string.wrong_1, Toast.LENGTH_SHORT)
						.show();
				break;
			case 2:
				Toast.makeText(Game.this, R.string.wrong_2, Toast.LENGTH_SHORT)
						.show();
				break;
			case 3:
				Toast.makeText(Game.this, R.string.wrong_3, Toast.LENGTH_SHORT)
						.show();
				break;
			default:
				break;
			}
			wrongClickNumber++;
		} else {
			Dialog keypad = new KeyPad(Game.this, used[x][y], puzzleView);
			keypad.show();
		}

	}

	public void calculateUsedTiles() {

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				used[i][j] = calculateUsedTiles(i, j);
			}
		}

	}

	
	
	
	/*
	 * public int[] calculateUsedTiles(int i, int j) { // TODO Auto-generated
	 * method stub int[] arr1 = new int[9]; int[] arr2 = new int[9]; int index =
	 * i + j * 9; for(int k = index - i; k < index + (9 - i - 1); k++){ if(k !=
	 * index && puzzle[k] > 0){ arr1[k - index + i] = puzzle[k]; } } for(int m =
	 * index - 9 * j; m < index + (9 - 1 - j) * 9; m = m + 9){ if(m != index &&
	 * puzzle[m] > 0){ arr2[(m - index + 9 * j) / 9] = puzzle[m]; } } int count1
	 * = 0; int count2 = 0; for(int n = 0; n < arr1.length; n++){ if(arr1[n] >
	 * 0) count1++; } int[] arr1Compress = new int[count1]; int begin = 0;
	 * for(int c = 0; c < arr1.length; c++){ if(arr1[c] > 0){
	 * arr1Compress[begin] = arr1[c]; begin++; } } int[] arr2Compress = new
	 * int[count2]; begin = 0; for(int d = 0; d < arr2.length; d++){ if(arr2[d]
	 * > 0){ arr2Compress[begin] = arr2[d]; begin++; } }
	 * 
	 * int overlap = 0; for(int a = 0; a < arr1.length; a++){ for(int b = 0; b <
	 * arr2.length; b++){ if(arr1[a] == arr2[b]) overlap++; } }
	 * 
	 * int finalLength = arr1.length + arr2.length - overlap; int[] output = new
	 * int[finalLength]; for(int e = 0; e < arr1.length; e++){ output[e] =
	 * arr1[e]; } begin = arr1.length; for(int g = 0; g < arr2.length; g++){
	 * for(int k = 0; k < arr1.length; k++){ if(arr2[g] == arr1[k]){ break;
	 * }else if(k == arr1.length -1){ output[begin] = arr2[k]; begin++; } } }
	 * 
	 * return output; }
	 */

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		switch (item.getItemId()) {
		case R.id.test_pen:
			this.isPenUsed = true;
			break;
		case R.id.confirm_pen:
			this.isPenUsed = false;
			this.PEN_STATE_BEFORE_INT = 0;
			for (int i = 0; i < this.testPenArr.length; i++){
				testPenArr[i] = 0;
			}
			puzzleView.invalidate();
			break;
		case R.id.discard_pen:
			this.PEN_STATE_BEFORE_INT = 0;
			this.isPenUsed = false;
			for (int i = 0; i < testPenArr.length; i++){
				if (testPenArr[i] == 1){
					testPenArr[i] = 0;
					puzzle[i] = 0;
				}
			}
			calculateUsedTiles();
			puzzleView.invalidate();
			break;
		default:
			break;
		}
		
		return super.onOptionsItemSelected(item);
	}

	private int[] calculateUsedTiles(int x, int y) {

		int[] c = new int[9];

		for (int i = 0; i < 9; i++) {
			if (i == y)
				continue;
			int t = getTile(x, i);
			if (t != 0)
				c[t - 1] = t;
		}

		for (int i = 0; i < 9; i++) {
			if (i == x) {
				continue;
			}
			int t = getTile(i, y);
			if (t != 0) {
				c[t - 1] = t;
			}
		}

		int startX = (x / 3) * 3;
		int startY = (y / 3) * 3;
		for (int i = startX; i < startX + 3; i++) {
			for (int j = startY; j < startY + 3; j++) {
				if (i == x && j == y)
					continue;
				int t = getTile(i, j);
				if (t != 0)
					c[t - 1] = t;
			}
		}

		int nused = 0;
		for (int t : c) {
			if (t != 0)
				nused++;
		}
		int[] c1 = new int[nused];
		nused = 0;
		for (int t : c) {
			if (t != 0)
				c1[nused++] = t;
		}

		return c1;
	}

	public void setTile(int x, int y, int value) {
		puzzle[9 * y + x] = value;
		calculateUsedTiles();
	}

	public int getTile(int x, int y) {
		// TODO Auto-generated method stub
		return puzzle[9 * y + x];
	}

	public int getPreDefinedValueNumber(int[] puzzle) {
		int count = 0;
		for (int i = 0; i < puzzle.length; i++) {
			if (puzzle[i] > 0)
				count++;
		}
		return count;
	}

	public void setPowermanagerOff() {
		this.wl.release();
	}
	
	public void setPowermanagerOn() {
		this.wl.acquire();
	}

	public void getPreDefinedValueIndex() {
		preDefinedValuesIndex = new int[getPreDefinedValueNumber(puzzle)];
		int begin = 0;
		for (int i = 0; i < puzzle.length; i++) {
			if (puzzle[i] > 0)
				preDefinedValuesIndex[begin++] = i;
		}
		
	}
	
	public boolean isSucceed(){
		
		for (int i = 0; i < puzzle.length; i++){
			if (puzzle[i] == 0){
				return false;
			}
		}
		return true;
	}
	
	public boolean getIsPenUsedFlag(){
		return this.isPenUsed;
	}

	public boolean isInPreDefinedValues(int aValue) {
		for (int i = 0; i < preDefinedValuesIndex.length; i++) {
			if (aValue == preDefinedValuesIndex[i])
				return true;
		}
		return false;
	}
	
	public void setTestPen(int x, int y, int value){
		this.testPenArr[9 * y + x] = value;
	}
	
	public boolean isNeededToContinue(){
		String temp = getPreferences(Context.MODE_PRIVATE).getString(CONTINUE_PUZZLE_PREDEFINE, "");
		if (temp.equals("")){
			return false;
		}else {
			return true;
		}
	}
	
	public int getTestPen(int x, int y){
		return this.testPenArr[y * 9 + x];
	}

	private int[][][] used = new int[9][9][];

	
	public static final String KEY_DIFFICULTY = "difficulty";
	public static final int DIFFICULTY_EASY = 0;
	public static final int DIFFICULTY_MEDIUM = 1;
	public static final int DIFFICULTY_HARD = 2;

	private PuzzleView puzzleView = null;

	private final String easyPuzzle = "360000000" + "004230800" + "000004200"
			+ "070460003" + "820000014" + "500013020" + "001900000"
			+ "007048300" + "000000045";

	private final String mediumPuzzle = "650000070" + "000506000" + "014000005"
			+ "007009000" + "002314700" + "000700800" + "500000630"
			+ "000201000" + "030000097";

	private final String hardPuzzle = "009000000" + "080605020" + "501078000"
			+ "000000700" + "706040102" + "004000000" + "000720903"
			+ "090301080" + "000000600";

	private int[] puzzle = new int[81];

	private int[] preDefinedValuesIndex = null;

	private int wrongClickNumber = 0;

	PowerManager pm = null;
	PowerManager.WakeLock wl = null;
	
	private boolean isPenUsed = false;
	
	private int[] testPenArr = new int[9 * 9];
	
	
	public static final int CONTINUE_KEY = 100;
	public static final String CONTINUE_PUZZLE = "CONTINUE_PUZZLE";
	public static final String CONTINUE_PUZZLE_PREDEFINE = "CONTINUE_PUZZLE_PREDEFINE";
	public static final String TEST_PEN	 = "TEST_PEN";
	
	public static final String PEN_STATE_BEFORE = "PEN_STATE_BEFORE";
	public int PEN_STATE_BEFORE_INT = 0;
	
	
}
