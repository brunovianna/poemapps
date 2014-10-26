package net.brunovianna.poemapps.fatorial;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import processing.core.*;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;


public class Fatorial extends PApplet {

	ArrayList<Bolavra> bolavraList;
	Bolavra centro, bolavra;
	SensorManager myManager;
	private List<Sensor> sensors;
	private Sensor accSensor;
	private long shakeTime, lastUpdate = -1;
	private float acc_x, acc_y, acc_z;
	private float acc_last_x, acc_last_y, acc_last_z;
	private static final int SHAKE_THRESHOLD = 400;


	
	public void setup() {
		int fontsize = (int)(width/33.75f);
		
		bolavraList = new ArrayList<Bolavra>();
		
		
		
		centro = new Bolavra (0,0,0,"de",fontsize);
		centro.setCentro(true);
		
		bolavra = new Bolavra (121,139,0,"nuca",fontsize);
		bolavra.setPosition(0);
		bolavraList.add(bolavra);
		
		bolavra = new Bolavra (0,64,107,"sorte",fontsize);
		bolavra.setPosition(1);
		bolavraList.add(bolavra);
		
		bolavra = new Bolavra (246,128,133,"jogo",fontsize);
		bolavra.setPosition(2);
		bolavraList.add(bolavra);
		
		bolavra = new Bolavra (208,15,0,"si",fontsize);
		bolavra.setPosition(3);
		bolavraList.add(bolavra);
		
		bolavra = new Bolavra (247,189,0,"arrepio",fontsize);
		bolavra.setPosition(4);
		bolavraList.add(bolavra);
		
		bolavra = new Bolavra (7,101,65,"beijo",fontsize);
		bolavra.setPosition(5);
		bolavraList.add(bolavra);
	
		
		// Set Sensor + Manager
		myManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
		sensors = myManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
		if(sensors.size() > 0)
		{
			accSensor = sensors.get(0);
		}
		
		myManager.registerListener(mySensorListener, accSensor, SensorManager.SENSOR_DELAY_GAME); 	

		pushMatrix();
		float largura = width*0.9f;
		//float comeco = width*0.05f;
		float altura = largura * 0.03f;
		rectMode(CENTER);
		translate (width/2,height/2);
		rect (0,0,largura,altura);
		rotate(PI/3.0f);
		rect (0,0,largura,altura);
		rotate(PI/3.0f);
		rect (0,0,largura,altura);
		rotate(PI/3.0f);
		popMatrix();
		
		centro.draw();
		
		
		
		for (int i=0;i<bolavraList.size();i++) {
			bolavraList.get(i).draw();
			
		}

		desenha_bolas();
	}

	public void draw() {
		
	}
	
	class Bolavra  {
		
		String m_palavra;
		boolean m_centro;
		int m_cor, m_pos;
		double m_angle;
		float m_x, m_y;
		float m_diam, m_fontsize;
		PGraphics m_graphics;
		
		Bolavra (int r, int g, int b, String palavra, float fontsize) {
		
			m_fontsize = fontsize;
			m_palavra = palavra;
			m_diam = width / 6.0f;
			m_graphics = createGraphics((int)m_diam,(int)m_diam,JAVA2D);
			m_graphics.beginDraw();
			//m_graphics.background(255);
			m_graphics.imageMode(CENTER);
			m_graphics.noStroke();
			m_graphics.fill(r,g,b);
			m_graphics.ellipseMode(CENTER);
			m_graphics.ellipse(m_diam/2,m_diam/2,m_diam,m_diam);
			m_graphics.fill(220);
			m_graphics.textSize(fontsize);
			m_graphics.textAlign(CENTER,CENTER);
			m_graphics.text(palavra, m_diam/2,m_diam/2);
			m_graphics.endDraw();
		}
		
		public void setCentro (boolean centro) {
			m_centro = centro;
		}
		
		public void setPosition (int pos) {
			m_pos = pos;
			m_angle = Math.PI / 6.0f + pos *  Math.PI / 3.0f; 
			float raio = (width-m_diam)/2.1f;
			m_x = width/2 + (float) (raio * Math.sin(m_angle)); 
			m_y = height/2 + (float) (raio * Math.cos(m_angle)); 
		}
		
		public int getPosition() {
			return m_pos;
		}
		
		public void draw() {
			
			if (m_centro) {
				
				imageMode(CENTER);
				image(m_graphics, width/2, height/2); 
				
			} else {
				
				imageMode(CENTER);
				image(m_graphics, m_x, m_y); 
				
			}
		}
		
		
	}
	
	private final SensorEventListener mySensorListener = new SensorEventListener()
	{

		public void onAccuracyChanged(Sensor sensor, int accuracy) {}

		@Override
		public void onSensorChanged(SensorEvent event) {
			Random random = new Random();
			long curTime = System.currentTimeMillis();
			// only allow one update every 100ms.
			if ((curTime - lastUpdate) > 100) {
				long diffTime = (curTime - lastUpdate);
				lastUpdate = curTime;

				acc_x = event.values[SensorManager.DATA_X];
				acc_y = event.values[SensorManager.DATA_Y];
				acc_z = event.values[SensorManager.DATA_Z];

				float speed = Math.abs(acc_x+acc_y+acc_z - acc_last_x - acc_last_y - acc_last_z) / diffTime * 10000;
				if ((speed > SHAKE_THRESHOLD)&&(curTime-shakeTime > 1000)) {
					// yes, this is a shake action! Do something about it!
					shakeTime = curTime;

					
					int nova_pos = (int)(random(6));
					int velha_pos = (int)(random(6));
					
					while (nova_pos==velha_pos) 
						velha_pos = (int)(random(6));
						
					int i=0;
					while (bolavraList.get(i).getPosition() != nova_pos)
						i++;
					
					int j=0;
					while (bolavraList.get(j).getPosition() != velha_pos)
						j++;
					
					bolavraList.get(i).setPosition(velha_pos);
					bolavraList.get(j).setPosition(nova_pos);
					
					desenha_bolas();
					
					
					}
					
				}
				acc_last_x = acc_x;
				acc_last_y = acc_y;
				acc_last_z = acc_z;
			}
		
	};


	public void desenha_bolas() {
		pushMatrix();
		float largura = width*0.9f;
		//float comeco = width*0.05f;
		float altura = largura * 0.03f;
		rectMode(CENTER);
		translate (width/2,height/2);
		rect (0,0,largura,altura);
		rotate(PI/3.0f);
		rect (0,0,largura,altura);
		rotate(PI/3.0f);
		rect (0,0,largura,altura);
		rotate(PI/3.0f);
		popMatrix();
		
		centro.draw();
		
		
		
		for (int i=0;i<bolavraList.size();i++) {
			bolavraList.get(i).draw();
			
		}

	}
	
}


