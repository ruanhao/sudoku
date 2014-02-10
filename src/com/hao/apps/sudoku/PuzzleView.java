package com.hao.apps.sudoku;

import com.hao.apps.sudoku.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;


import android.graphics.Rect;

import android.view.MotionEvent;
import android.view.View;

public class PuzzleView extends View {

	public PuzzleView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub

		gameReference = (Game) context;
		
		

		
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO Auto-generated method stub
		super.onSizeChanged(w, h, oldw, oldh);

		tileWidth = w / 9f;
		tileHeight = h / 9f;

		selRect = new Rect();

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		super.onTouchEvent(event);

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			int x = (int) (event.getX() / tileWidth);
			int y = (int) (event.getY() / tileHeight);
			select(x, y);
			if (gameReference.getIsPenUsedFlag() || (gameReference.PEN_STATE_BEFORE_INT == 1)){
				gameReference.setTestPen(x, y, 1);
			}
			gameReference.showKeypad(x, y);
			break;

		default:
			break;
		}

		return true;
	}

	public void setSelectedTile(int i) {
		gameReference.setTile(XOfSelection, YOfSelection, i);
		invalidate();
	}

	private void select(int x, int y) {
		XOfSelection = Math.min(Math.max(x, 0), 8);
		YOfSelection = Math.min(Math.max(y, 0), 8);
		getRect(XOfSelection, YOfSelection, selRect);
		invalidate();
	}

	private void getRect(int x, int y, Rect rect) {
		rect.set((int) (x * tileWidth), (int) (y * tileHeight), (int) (x
				* tileWidth + tileWidth), (int) (y * tileHeight + tileHeight));

	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub

		super.onDraw(canvas);

		drawBackground(canvas);
		drawGrids(canvas);
		drawNumbers(canvas);
		drawFocusRect(canvas);
		
		judgeIfSucceed();

	}

	private void judgeIfSucceed() {
		// TODO Auto-generated method stub
		
		if (gameReference.isSucceed()){
			
			new AlertDialog.Builder(gameReference)
				.setTitle("congradulations")
				.setPositiveButton("ok", new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						gameReference.finish();
					}
				})
				.show();
		}
	}

	private void drawFocusRect(Canvas canvas) {
		// TODO Auto-generated method stub
		Paint paintFocus = new Paint();
		paintFocus.setColor(getResources().getColor(R.color.puzzle_selected));
		canvas.drawRect(selRect, paintFocus);
	}

	public void drawNumbers(Canvas canvas) {
		Paint numberPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		Paint preNumberPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		
		
		preNumberPaint.setColor(Color.BLUE);
		numberPaint.setStyle(Style.FILL);
		numberPaint.setTextSize(tileHeight * 0.75f);
		preNumberPaint.setTextSize(tileHeight * 0.75f);
		numberPaint.setTextScaleX(tileWidth / tileHeight);
		preNumberPaint.setTextScaleX(tileWidth / tileHeight);
		numberPaint.setTextAlign(Paint.Align.CENTER);
		preNumberPaint.setTextAlign(Paint.Align.CENTER);
		
		

		FontMetrics fm = numberPaint.getFontMetrics();
		float x = tileWidth / 2;
		float y = tileHeight / 2 - (fm.ascent + fm.descent) / 2;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (PuzzleView.this.gameReference.isInPreDefinedValues(j * 9
						+ i)) {
					canvas.drawText(PuzzleView.this.gameReference
							.getTileString(i, j), i * tileWidth + x, j
							* tileHeight + y, preNumberPaint);
				} else {
					/*
					 * 下面的if-else语句如果将else中的内容放至FontMetrics fm之前，会有bug在中等难度中出现，请测试
					 */
					//System.out.println(gameReference.getIsPenUsedFlag() && (gameReference.getTestPen(i, j) == 1));
					//System.out.println(i + ", " + j + ": " + gameReference.getTestPen(i, j));
					if(gameReference.getTestPen(i, j) == 1){
						numberPaint.setColor(Color.RED);
						
					}else{
						numberPaint
						.setColor(getResources().getColor(R.color.puzzle_foreground));
					}
					
					canvas.drawText(PuzzleView.this.gameReference
							.getTileString(i, j), i * tileWidth + x, j
							* tileHeight + y, numberPaint);
				}

			}
		}
	}

	private void drawGrids(Canvas canvas) {
		Paint darkPaint = new Paint();
		darkPaint.setColor(getResources().getColor(R.color.puzzle_dark));
		Paint hilitePaint = new Paint();
		hilitePaint.setColor(getResources().getColor(R.color.puzzle_hilite));
		Paint lightPaint = new Paint();
		lightPaint.setColor(getResources().getColor(R.color.puzzle_light));

		for (int i = 0; i < 9; i++) {
			canvas.drawLine(0, i * tileHeight, getWidth(), i * tileHeight,
					lightPaint);
			canvas.drawLine(0, i * tileHeight + 1, getWidth(), i * tileHeight
					+ 1, hilitePaint);
			canvas.drawLine(i * tileWidth, 0, i * tileWidth, getHeight(),
					lightPaint);
			canvas.drawLine(i * tileWidth + 1, 0, i * tileWidth + 1,
					getHeight(), hilitePaint);
		}
		for (int i = 0; i < 9; i++) {
			if (i % 3 != 0)
				continue;
			canvas.drawLine(0, i * tileHeight, getWidth(), i * tileHeight,
					darkPaint);
			canvas.drawLine(0, i * tileHeight + 1, getWidth(), i * tileHeight
					+ 1, hilitePaint);
			canvas.drawLine(i * tileWidth, 0, i * tileWidth, getHeight(),
					darkPaint);
			canvas.drawLine(i * tileWidth + 1, 0, i * tileWidth + 1,
					getHeight(), hilitePaint);
		}
	}

	private void drawBackground(Canvas canvas) {
		Paint backgroundPaint = new Paint();
		backgroundPaint.setColor(getResources().getColor(
				R.color.puzzle_background));
		canvas.drawRect(0, 0, getWidth(), getHeight(), backgroundPaint);
	}

	private float tileWidth = 0f;
	private float tileHeight = 0f;
	private int XOfSelection = 0;
	private int YOfSelection = 0;
	private Game gameReference = null;
	private Rect selRect = null;
	
	private static final String TAG = "PuzzleView.java";
	
	

}
