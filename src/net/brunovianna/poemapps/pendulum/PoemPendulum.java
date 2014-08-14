package net.brunovianna.poemapps.pendulum;

import processing.core.*; 

import android.view.MotionEvent; 
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import android.os.Bundle;
import android.util.Log;

public class PoemPendulum extends PApplet {

	/**
	 * Words. 
	 * 
	 * The text() function is used for writing words to the screen. 
	 */


	int x = 30;
	PFont fontA;
	String[] fontList;
	ImagePendulum ip;
	PImage textoBranco, textoPreto;

	private SensorManager mSensorManager;
	private Sensor mAccelerometer;
	private Sensor mMagnetic;
	private MySensorEventListener accSensorEventListener;
	private MySensorEventListener magSensorEventListener;
	private float[] acc_values;
	private float[] mag_values;

	private long mSensorTimeStamp;
	private long mCpuTimeStamp;
	public long mLastT;
	public float mLastDeltaT;


	public void setup() 
	{
		//  size(200, 200);
		background(102);

		// Load the font. Fonts must be placed within the data 
		// directory of your sketch. Use Tools > Create Font 
		// to create a distributable bitmap font. 
		// For vector fonts, use the createFont() function. 

		//fontList = PFont.list();

		fontA = createFont("SansSerif", 20);

		// Set the font and its size (in units of pixels)
		textFont(fontA);

		textoBranco = loadImage("oscila-branco.png");
		textoPreto = loadImage("oscila-preto.png");

		int percent = (int) (displayHeight * 0.8f);

		textoBranco.resize(percent, 0);
		textoPreto.resize(percent, 0);

		smooth();
		
		orientation(PORTRAIT);

		// Make a new Pendulum with an origin location and armlength
		// p = new Pendulum(new PVector(width/2,height/2),75.0f);
		// tp = new TextPendulum(new PVector(width/2,height/2), "hello poetry", fontA);
		ip = new ImagePendulum(new PVector(width/2,height/10), textoPreto, textoBranco);
	}

	public void draw() {
		//p.go();
		ip.go();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Get an instance of the SensorManager
		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

		mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);


		if (mAccelerometer != null) {
			accSensorEventListener = new MySensorEventListener();



			mSensorManager.registerListener(accSensorEventListener, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
		}
	}

	protected void onResume() {
		super.onResume();

		if (mAccelerometer!=null)
			mSensorManager.registerListener(accSensorEventListener, mAccelerometer, SensorManager.SENSOR_DELAY_UI);

	}

	protected void onPause() {
		super.onPause();
		if (mAccelerometer!=null)
			mSensorManager.unregisterListener(accSensorEventListener);

	}


	// Setup our SensorEventListener
	class MySensorEventListener implements SensorEventListener {
		public void onSensorChanged(SensorEvent event) {
			int eventType = event.sensor.getType();
			if(eventType == Sensor.TYPE_ACCELEROMETER) {
				acc_values = event.values;
			}
			else if(eventType == Sensor.TYPE_MAGNETIC_FIELD) {
				mag_values = event.values;
			}

			mSensorTimeStamp = event.timestamp;
			mCpuTimeStamp = System.nanoTime();

		}
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
		}
	}


	public boolean surfaceTouchEvent(MotionEvent event) {
		// your code here

		switch (event.getAction()) {

		case (MotionEvent.ACTION_MOVE):
		case (MotionEvent.ACTION_DOWN): { 

			mouseX = (int)event.getX();
			mouseY = (int)event.getY();
			ip.clicked((int)event.getX(), (int)event.getY());


		} break;
		case (MotionEvent.ACTION_UP): { 

			ip.stopDragging();
		}


		}

		// if you want the variables for motionX/motionY, mouseX/mouseY etc.
		// to work properly, you'll need to call super.surfaceTouchEvent().
		return super.surfaceTouchEvent(event);
	}

	//Pendulum
	//Daniel Shiffman <http://www.shiffman.net>

	//A Simple Pendulum Class
	//Includes functionality for user can click and drag the pendulum

	//Created 2 May 2005

	class Pendulum  {

		PVector loc;      // Location of pendulum ball
		PVector origin;   // Location of arm origin
		float r;           // Length of arm
		float theta;       // Pendulum arm angle
		float theta_vel;   // Angle velocity
		float theta_acc;   // Angle acceleration

		float ballr;       // Ball radius
		float damping;     // Arbitary damping amount

		boolean dragging = false;

		public Pendulum() {} //why??


		// This constructor could be improved to allow a greater variety of pendulums
		Pendulum(PVector origin_, float r_) {
			// Fill all variables
			origin = origin_.get();
			r = r_;
			theta = 0.0f;

			//calculate the location of the ball using polar to cartesian conversion
			float x = r * sin(theta);
			float y = r * cos(theta);
			loc = new PVector(origin.x + x, origin.y + y);
			theta_vel = 0.0f;
			theta_acc = 0.0f;
			damping = 0.995f;   // Arbitrary damping
			ballr = 16.0f;      // Arbitrary ball radius
		}

		void go() {
			update();
			drag();    //for user interaction
			render();
		}

		// Function to update location
		void update() {
			// As long as we aren't dragging the pendulum, let it swing!
			if (!dragging) {
				float G = 0.4f;                              // Arbitrary universal gravitational constant
				theta_acc = (-1f * G / (float)r) * sin(theta);      // Calculate acceleration (see: http://www.myphysicslab.com/pendulum1.html)
				theta_vel += theta_acc;                     // Increment velocity
				theta_vel *= damping;                       // Arbitrary damping
				theta += theta_vel;                         // Increment theta
			}
			loc.set(r*sin(theta),r*cos(theta),0);         // Polar to cartesian conversion
			loc.add(origin);                              // Make sure the location is relative to the pendulum's origin
		}

		void render() {
			stroke(0);
			// Draw the arm
			line(origin.x,origin.y,loc.x,loc.y);
			ellipseMode(CENTER);
			fill(175);
			if (dragging) fill(0);
			// Draw the ball
			ellipse(loc.x,loc.y,ballr,ballr);
		}

		// The methods below are for mouse interaction

		// This checks to see if we clicked on the pendulum ball
		void clicked(int mx, int my) {


			float d = dist(mx,my,loc.x,loc.y);
			if (d < ballr) {
				dragging = true;
			}
		}

		// This tells us we are not longer clicking on the ball
		void stopDragging() {
			dragging = false;
		}

		void drag() {
			// If we are draging the ball, we calculate the angle between the 
			// pendulum origin and mouse location
			// we assign that angle to the pendulum
			if (dragging) {
				PVector diff = PVector.sub(origin,new PVector(mouseX,mouseY));   // Difference between 2 points
				theta = atan2(-1*diff.y,diff.x) - radians(90);                      // Angle relative to vertical axis
			}
		}

	}

	class TextPendulum extends Pendulum {

		String pendulumText;
		PFont font;

		public TextPendulum (PVector origin_, String text_, PFont font_) {
			// Fill all variables
			origin = origin_.get();
			pendulumText = text_;
			font = font_;
			theta = 0.0f;

			r = textWidth(pendulumText);

			//calculate the location of the ball using polar to cartesian conversion
			float x = r * sin(theta);
			float y = r * cos(theta);
			loc = new PVector(origin.x + x, origin.y + y);
			theta_vel = 0.0f;
			theta_acc = 0.0f;
			damping = 0.995f;   // Arbitrary damping
			ballr = 16.0f;      // Arbitrary ball radius
		}


		void render() {
			stroke(0);
			//			ellipseMode(CENTER);
			fill(175);
			//			ellipse(loc.x,loc.y,ballr,ballr);
			// Draw the arm
			//line(origin.x,origin.y,loc.x,loc.y);
			translate(origin.x,origin.y);
			rotate (-theta + PI/2);
			text(pendulumText,0,0);
		}

	}

	class ImagePendulum extends Pendulum {

		PImage pendulumImageWhite, pendulumImageBlack;
		int textHeight, startX, startY;

		public ImagePendulum (PVector origin_,  PImage imageblack_, PImage image_) {
			// Fill all variables
			origin = origin_.get();
			pendulumImageWhite = image_;
			pendulumImageBlack = imageblack_;
			theta = 0.0f;

			r = pendulumImageWhite.width;
			textHeight = pendulumImageWhite.height;

			//calculate the location of the ball using polar to cartesian conversion
			float x = r * sin(theta);
			float y = r * cos(theta);
			loc = new PVector( x,  y);
			theta_vel = 0.0f;
			theta_acc = 0.0f;
			damping = 0.995f;   // Arbitrary damping
			ballr = 16.0f;      // Arbitrary ball radius


		}

		// This checks to see if we clicked on the pendulum ball
		void clicked(int mx, int my) {

			startX = mx;
			startY = my;
			dragging = true;
		}
		// Function to update location
		void update() {
			// As long as we aren't dragging the pendulum, let it swing!
			if (!dragging) {

				float G = 0.4f;                              // Arbitrary universal gravitational constant

				if (acc_values!=null) {
					final float sx = acc_values[0] * 0.05f;
					final float sy = acc_values[1] * 0.05f;
					//Log.d("ANGLE", "degrees: "+degrees(atan2(-1*sy,sx)));
					final float phi = atan2(-1*sy,sx)+radians(90);
					//final float phi = radians(0);
					//theta_acc = (-1f * G / (float)r) * (sin(theta)+ sin(phi)*cos(theta)+cos(phi)*sin(theta));
					theta_acc = (-1f * G / (float)r) * sin(theta+phi);      // Calculate acceleration (see: http://www.myphysicslab.com/pendulum1.html)
				} else {
					theta_acc = (-1f * G / (float)r) * sin(theta);      // Calculate acceleration (see: http://www.myphysicslab.com/pendulum1.html)
				}
				theta_vel += theta_acc;                     // Increment velocity
				theta_vel *= damping;                       // Arbitrary damping
				theta += theta_vel;                         // Increment theta
			}
			loc.set(r*sin(theta),r*cos(theta),0);         // Polar to cartesian conversion
			//loc.add(origin);                              // Make sure the location is relative to the pendulum's origin
		}

		void drag() {
			// If we are draging the ball, we calculate the angle between the 
			// pendulum origin and mouse location
			// we assign that angle to the pendulum
			if (dragging) {
				PVector diff = PVector.sub(origin,new PVector(mouseX,mouseY));   // Difference between 2 points
				theta = atan2(-1*diff.y,diff.x) - radians(90);                      // Angle relative to vertical axis
				//Log.d("ANGLE", "theta: "+degrees(theta));
			}
		}


		void render() {

			int grey;

			stroke(0);
			fill(175);

			if (theta < -1.0f*PI)
				theta = theta + 2.0f*PI;
			else if (theta > PI)
				theta = theta - 2.0f*PI;
			
			
			if (theta < -1*PI/2) {
				grey=255;
			} else if (theta > PI/2) {
				grey = 0;
			} else {
				grey = 128 - (int)(theta * 128 / (PI / 2));
			}

			
			//background(constrain(grey, 0, 255));
			background(grey);

			translate(origin.x,origin.y);

			rotate (-theta + PI/2);

			if (theta>0) {
				image(pendulumImageWhite,0,0);
			} else {
				image(pendulumImageBlack,0,0);
			}
			Log.d("ANGLE", "degrees: "+degrees(theta));

		}

	}

}