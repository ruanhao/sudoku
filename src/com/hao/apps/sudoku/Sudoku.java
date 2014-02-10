package com.hao.apps.sudoku;

import com.hao.apps.sudoku.R;

import android.app.Activity;
import android.app.AlertDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import android.widget.Button;

public class Sudoku extends Activity implements View.OnClickListener {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		findViews();
		
		setButtonListeners();
		
		/*GradientDrawable gradient = new GradientDrawable(
				Orientation.TL_BR,
				new int[] {Color.argb(50, 251, 183, 166), Color.argb(50, 177, 239, 191)});*/
		
		GradientDrawable gradient = new GradientDrawable(
				Orientation.TL_BR,
				new int[] {Color.argb(200, 147, 209, 131), Color.argb(200, 240, 182, 176)});
		
		//Sudoku.this.getWindow().setBackgroundDrawable(gradient);
		
		
		Drawable back = getResources().getDrawable(R.drawable.dada);
		
		Sudoku.this.getWindow().setBackgroundDrawable(back);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu);
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		super.onOptionsItemSelected(item);
		switch (item.getItemId()) {
		case R.id.settings:
			Intent intent = new Intent();
			intent.setClass(Sudoku.this, Settings.class);
			startActivity(intent);
			return true;

		default:
			return false;
		}
	}

	private void setButtonListeners() {
		// TODO Auto-generated method stub
		aboutButton.setOnClickListener(this);
		newButton.setOnClickListener(this);
		
		exitButton.setOnClickListener(this);
		
		continueButton.setOnClickListener(this);
	}

	private void findViews() {
		// TODO Auto-generated method stub
		aboutButton = (Button) findViewById(R.id.about_button);
		newButton = (Button) findViewById(R.id.new_button);
		
		exitButton = (Button) findViewById(R.id.exit_button);
		
		continueButton = (Button) findViewById(R.id.continue_button);
		
	}

	Button aboutButton = null;
	Button newButton = null;
	Button continueButton = null;
	Button exitButton = null;
	private static final String TAG = "Sudoku";
	

	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.about_button:
			Intent intent = new Intent();
			intent.setClass(Sudoku.this, About.class);
			startActivity(intent);
			break;

		case R.id.new_button:
			openNewGameDialog();
			break;

		case R.id.exit_button:
			finish();
			break;
		case R.id.continue_button:
			if (getSharedPreferences("PREF", MODE_PRIVATE)
							.getInt("flag", 3) != 3){
				Intent intentContinue = new Intent(Sudoku.this, Game.class);
				intentContinue.putExtra(Game.KEY_DIFFICULTY, Game.CONTINUE_KEY);
				startActivity(intentContinue);
			}
			

		default:
			break;
		}
	}

	private void openNewGameDialog() {
		// TODO Auto-generated method stub
		/*new AlertDialog.Builder(Sudoku.this).setTitle("开始新游戏！！！！").setItems(
				R.array.diffculty, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						startGame(which);
					}
				}).show();*/
		
		String which = Settings.getDiffState(getApplicationContext());
		Integer i = Integer.parseInt(which);
		startGame(i);
		
	}

	private void startGame(int i) {
		Log.d(TAG, "click on " + i);
		Intent intent = new Intent(Sudoku.this, Game.class);
		intent.putExtra(Game.KEY_DIFFICULTY, i);
		startActivity(intent);
	}
}