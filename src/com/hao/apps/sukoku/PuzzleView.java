package com.hao.apps.sukoku;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
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

		selRect = new Rect(0, 0, (int) tileWidth, (int) tileHeight);

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		System.out.println("onTouchEvent()");

		super.onTouchEvent(event);
		
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			System.out.println(tileWidth + "" + tileHeight);
			invalidate();
			return true;
			

		default:
			return true;
		}
		
	}

	
	
	@Override
	public void invalidate(Rect dirty) {
		// TODO Auto-generated method stub
		System.out.println("before invalidate");
		super.invalidate(dirty);
		System.out.println("after invalidate");
		
	}

	private void select(int x, int y) {
		System.out.println("invalidate old");
		//invalidate(selRect);
		XOfSelection = Math.min(Math.max(x, 0), 8);
		YOfSelection = Math.min(Math.max(y, 0), 8);
		getRect(XOfSelection, YOfSelection, selRect);
		System.out.println("invalidate new");
		invalidate(selRect);
	}

	private void getRect(int x, int y, Rect rect) {
		rect.set((int) (x * tileWidth), (int) (y * tileHeight), (int) (x
				* tileWidth + tileWidth), (int) (y * tileHeight + tileHeight));
		
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		System.out.println("onDraw()");
		super.onDraw(canvas);

		drawBackground(canvas);
		drawGrids(canvas);

		Paint numberPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

		numberPaint
				.setColor(getResources().getColor(R.color.puzzle_foreground));
		// numberPaint.setStyle(Style.FILL);
		numberPaint.setTextSize(tileHeight * 0.75f);
		numberPaint.setTextScaleX(tileWidth / tileHeight);
		numberPaint.setTextAlign(Paint.Align.CENTER);

		FontMetrics fm = numberPaint.getFontMetrics();
		float x = tileWidth / 2;
		float y = tileHeight / 2 - (fm.ascent + fm.descent) / 2;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				canvas.drawText(
						PuzzleView.this.gameReference.getTileString(i, j), i
								* tileWidth + x, j * tileHeight + y,
						numberPaint);
			}
		}
		
		System.out.println("onDraw() finished");
		
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

}
