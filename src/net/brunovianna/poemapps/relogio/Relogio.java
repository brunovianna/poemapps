package net.brunovianna.poemapps.relogio;

import processing.core.*;


public class Relogio extends PApplet {

	PImage frases[] = new PImage[12];
	PImage ponteiro_horas, ponteiro_minutos, ponteiro_segundos;
	private static final int zero_x = 25; 
	private static final int zero_y = 38; 
	private float proporcao;
	
	public void setup() {
		
		
		for (int i=1; i<13;i++) {
			frases[i-1] = loadImage("relogio-"+i+".png");
		}
		
		
		//frases[0] = loadImage("relogio-3.png");
		
		ponteiro_horas = loadImage("nao.png");
		ponteiro_minutos = loadImage("sim.png");
		ponteiro_segundos = loadImage("talvez.png");
		
		proporcao = (width*0.45f)/ponteiro_segundos.width;
		 
		
	}
	
	public void draw() {

		background(200);
		
		pushMatrix();
		translate (width/2 , height/2 ); 
		scale(proporcao);
		rotate ( second() *  PI / 30 );
		image (ponteiro_segundos, - zero_x,- zero_y);
		rotate ( -second() *  PI / 30 );
		rotate (minute() * PI / 30);
		image (ponteiro_minutos, - zero_x,- zero_y);
		rotate (-minute() * PI / 30);
		
		int hora;
		if (hour() > 11)
			hora = hour() - 12;
		else
			hora = hour();
		
		rotate (hora * PI / 6);
		image (ponteiro_horas, - zero_x,- zero_y);

	
		
		imageMode(CENTER);
		
		int hora_anterior;
		if (hora == 0)
			hora_anterior = 11;
		else 
			hora_anterior = hora - 1;
			
		rotate (-hora * PI / 6);
		image(frases[hora_anterior],0,0);		
		rotate (hora * PI / 6);
		image(frases[hora],0,0);
	
		popMatrix();

		
		
	}
	
}
