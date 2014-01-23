package com.hao.apps.sudoku;

import com.hao.apps.sukoku.R;

import android.app.Activity;
import android.app.AlertDialog;

import android.content.DialogInterface;
import android.content.Intent;
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
		default:
			break;
		}
	}

	private void openNewGameDialog() {
		// TODO Auto-generated method stub
		new AlertDialog.Builder(Sudoku.this).setTitle("new game").setItems(
				R.array.diffculty, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						startGame(which);
					}
				}).show();
	}

	private void startGame(int i) {
		Log.d(TAG, "click on " + i);
		Intent intent = new Intent(Sudoku.this, Game.class);
		intent.putExtra(Game.KEY_DIFFICULTY, i);
		startActivity(intent);
	}
}