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
		
		translate (width/2 , height/2 ); 
		scale(proporcao);
		rotate (second() *  PI / 30 - HALF_PI);
		image (ponteiro_segundos, - zero_x,- zero_y);
		
		
		rotate ( -second() *  PI / 30 + HALF_PI );
		
		rotate (minute() * PI / 30 - HALF_PI);
		image (ponteiro_minutos, - zero_x,- zero_y);
		
		rotate (-minute() * PI / 30 - HALF_PI);
	
		
		int hora;
		if (hour() > 11)
			hora = hour() - 12;
		else
			hora = hour();
		
		rotate (hora * PI / 6 +HALF_PI);
		image (ponteiro_horas, - zero_x,- zero_y);
		rotate (-hora * PI / 6 -HALF_PI);

		
		int hora_anterior;
		if (hora == 0)
			hora_anterior = 11;
		else 
			hora_anterior = hora - 1;
			
		image(frases[hora_anterior],-frases[hora_anterior].width/2 , -frases[hora_anterior].height/2);		
		rotate (hora * PI / 6 + PI);
		image(frases[hora],-frases[hora].width/2,-frases[hora].height/2);
		

		
		
	}
	
}
