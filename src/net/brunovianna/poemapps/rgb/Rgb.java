package net.brunovianna.poemapps.rgb;


import processing.core.*;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;


public class Rgb extends PApplet {

PFont droidFont;
PGraphics r, b;
boolean dragging, draggingRed;
int downX, downY, rectWidth, rectHeight;

public void setup() {
  //size(100,100);
  //droidFont = createFont("DroidSans", 32, true);
  droidFont = loadFont("DroidSans-32.vlw");
  textFont(droidFont);
  textAlign (LEFT, CENTER);
  background(255);
  rectWidth = width/2;
  rectHeight = height/5;
  r = createGraphics(rectWidth,rectHeight);
  r.beginDraw();
  r.background(204,0,0);
  r.endDraw();
  b = createGraphics(rectWidth,rectHeight);
  b.beginDraw();
  b.background(51,255,255);
  b.endDraw();
  image (r, 0,0);
  image (b,width/2,0);
  dragging = false;
  draggingRed = false;
}

public void draw () {
  //blendMode();
  background(255);
  if (!dragging)  {
    image (r, 0,0);
    image (b,width/2,0);
  } else {
    if (draggingRed) {
      println ( "r");
      blend (r,0,0,rectWidth,rectHeight,mouseX-downX+rectWidth,mouseY-downY,rectWidth,rectHeight,SUBTRACT);
      image (r, 0,0);
    } else {
      println ( "b");
      blend (b,0,0,rectWidth,rectHeight,mouseX-downX,mouseY-downY,rectWidth,rectHeight,SUBTRACT);
      image (b,width/2,0);
    }
  } 
  
  
  fill (51,255,255);
  text ("texto azul", width/3,height/3);
  fill (204,0,0);
  text ("texto vermelho", width/3,height/3*2);
  //blendMode(ADD);
  //blend (r,0,0,150,150,mouseX,mouseY,150,150,SUBTRACT);
}

public void mousePressed() {
  
  if (mouseY < rectHeight) { //clicked on the drag area
    dragging = true;
    downX = mouseX;
    downY = mouseY;
    if (mouseX < rectWidth) { //clicked on red
      draggingRed = false;
      println ("< 100");
    } else {
      draggingRed = true;
    }
  } 

  
}



public void mouseReleased() {
  dragging = false;
}
}