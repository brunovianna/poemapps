package net.brunovianna.poemapps.doublet;

import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.BitmapFactory.Options;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;

public class DoubletView extends View {

	private SensorManager mSensorManager;
	private WindowManager mWindowManager;
	private Display mDisplay;
	
	public DoubletView(Context context, AttributeSet attrs) {
		super(context, attrs);
		}
	

	public void setWindowManager(WindowManager wm) {

		DisplayMetrics metrics = new DisplayMetrics();

		mWindowManager = wm;
		mDisplay = wm.getDefaultDisplay();
		mDisplay.getMetrics(metrics);


		Options opts = new Options();
		opts.inDither = true;
		opts.inPreferredConfig = Bitmap.Config.RGB_565;

		//the font size, type and color
		Paint p = new Paint();
		p.setColor(Color.BLACK);
		p.setTextSize((int)(metrics.widthPixels/22));// trying to find a letter size that fits
		p.setFakeBoldText(true);
		p.setAntiAlias(true);
		Rect r = new Rect();
		p.getTextBounds("m", 0, 1, r);
		//letterMWidth = r.right;
		

	}

} 