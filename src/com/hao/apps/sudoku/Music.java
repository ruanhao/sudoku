package com.hao.apps.sudoku;

import android.content.Context;
import android.media.MediaPlayer;

public class Music {

	private static MediaPlayer mp = null;

	public static void startMusic(Context context) {
		if (Settings.getMusicState(context)) {
			stop(context);
			int r = (int) (Math.random() * 10);
			int choise = 0;
			if (r > 5){
				choise = 1;
			}else {
				choise = 2;
			}
			
			switch (choise) {
			case 1:
				mp = MediaPlayer.create(context, R.raw.a);
				break;
			case 2:
				mp = MediaPlayer.create(context, R.raw.b);
				break;
			default:
				break;
			}
			mp.setLooping(true);
			mp.start();
		}

	}

	public static void stop(Context context) {
		if (Settings.getMusicState(context)) {
			if (mp != null) {
				mp.release();
				mp = null;
			}
		}

	}

}
