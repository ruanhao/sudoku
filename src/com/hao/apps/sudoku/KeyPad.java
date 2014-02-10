package com.hao.apps.sudoku;

import com.hao.apps.sudoku.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.TableLayout;

public class KeyPad extends Dialog implements View.OnClickListener {
	
	private int[] used = null;
	private PuzzleView puzzleView = null;
	Button button1 = null;
	Button button2 = null;
	Button button3 = null;
	Button button4 = null;
	Button button5 = null;
	Button button6 = null;
	Button button7 = null;
	Button button8 = null;
	Button button9 = null;
	TableLayout tl = null;

	public KeyPad(Context context, int[] used, PuzzleView puzzleView) {
		super(context);
		// TODO Auto-generated constructor stub
		
		this.used = used;
		this.puzzleView = puzzleView;
		setTitle(R.string.keypad_title);
		
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.keypad);
		findViews();
		setListeners();
		deleteButtons(used);
	}

	private void deleteButtons(int[] used) {
		// TODO Auto-generated method stub
		for(int i = 0; i < used.length; i++){
			delete(used[i]);
		}
	}

	private void delete(int i) {
		// TODO Auto-generated method stub
		switch (i) {
		case 1:
			button1.setVisibility(View.INVISIBLE);
			break;
		case 2:
			button2.setVisibility(View.INVISIBLE);
			break;
		case 3:
			button3.setVisibility(View.INVISIBLE);
			break;
		case 4:
			button4.setVisibility(View.INVISIBLE);
			break;
		case 5:
			button5.setVisibility(View.INVISIBLE);
			break;
		case 6:
			button6.setVisibility(View.INVISIBLE);
			break;
		case 7:
			button7.setVisibility(View.INVISIBLE);
			break;
		case 8:
			button8.setVisibility(View.INVISIBLE);
			break;
		case 9:
			button9.setVisibility(View.INVISIBLE);
			break;
		default:
			break;
		}
	}

	private void setListeners() {
		// TODO Auto-generated method stub
		button1.setOnClickListener(this);
		button2.setOnClickListener(this);
		button3.setOnClickListener(this);
		button4.setOnClickListener(this);
		button5.setOnClickListener(this);
		button6.setOnClickListener(this);
		button7.setOnClickListener(this);
		button8.setOnClickListener(this);
		button9.setOnClickListener(this);
		tl.setOnClickListener(this);
	}

	private void findViews() {
		// TODO Auto-generated method stub
		button1 = (Button) findViewById(R.id.key_1);
		button2 = (Button) findViewById(R.id.key_2);
		button3 = (Button) findViewById(R.id.key_3);
		button4 = (Button) findViewById(R.id.key_4);
		button5 = (Button) findViewById(R.id.key_5);
		button6 = (Button) findViewById(R.id.key_6);
		button7 = (Button) findViewById(R.id.key_7);
		button8 = (Button) findViewById(R.id.key_8);
		button9 = (Button) findViewById(R.id.key_9);
		tl = (TableLayout) findViewById(R.id.keypad);
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.key_1:
			returnResult(1);
			break;
		case R.id.key_2:
			returnResult(2);
			break;
		case R.id.key_3:
			returnResult(3);
			break;
		case R.id.key_4:
			returnResult(4);
			break;
		case R.id.key_5:
			returnResult(5);
			break;
		case R.id.key_6:
			returnResult(6);
			break;
		case R.id.key_7:
			returnResult(7);
			break;
		case R.id.key_8:
			returnResult(8);
			break;
		case R.id.key_9:
			returnResult(9);
			break;
		case R.id.keypad:
			returnResult(0);
			break;
		default:
			break;
		}
	}

	private void returnResult(int i) {
		// TODO Auto-generated method stub
		puzzleView.setSelectedTile(i);
		dismiss();
	}
	
	

}
