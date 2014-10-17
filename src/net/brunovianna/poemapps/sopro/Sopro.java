package net.brunovianna.poemapps.sopro;

import net.brunovianna.poemapps.R;

import android.content.res.Resources;




import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;


import fisica.*;
import geomerative.*;
import org.apache.batik.svggen.font.table.*;
import org.apache.batik.svggen.font.*;

import java.util.*;

import processing.core.*;
import processing.data.StringList;

public class Sopro extends PApplet {

	//Resources res;
	
	FWorld world;
	double sopro = 0;
	RFont rFont;
	PFont pFont;
	
	StringList frase;
	int[] fases;
	int fase;
	
	
	//String frase = "frase para soprar um pouco mais longa até";
	
	
	
	public static final int SAMPLE_RATE = 16000;
	private AudioRecord mRecorder;
	private short[] mBuffer;
	
	public void setup() {
		

		
	  smooth();
	
	  frameRate(60);
	
	initRecorder();

	  
	  Fisica.init(this);
	  Fisica.setScale(4);
	  RG.init(this);
	
	  RG.setPolygonizer(RG.ADAPTATIVE);
	
	  world = new FWorld();
	  world.setGravity(0, 0);
	  //world.setEdges(this, color(255));
	  //world.remove(world.top);
	  
	  
	  
	  Resources res = getResources();
	  CharSequence[] sopro_texto = res.getTextArray(R.array.sopro_texto);
      CharSequence[] sopro_fases = res.getTextArray(R.array.sopro_fases);
	  
	  frase = new StringList();
	  
	  for (int i=0; i<sopro_texto.length;i++) {
		  frase.append ((String) sopro_texto[i]);		  
	  }
	  
	  
	
	 String stringFont = "LiberationSerif-Bold";
	
	  rFont = RG.loadFont(stringFont+".ttf");
	  pFont = loadFont(stringFont+".vlw");
	  textFont (pFont);
	
	  //posicionando o texto
	  int maxWidth = 0;
	  for (int i=0;i<frase.size();i++) {
	    if (frase.get(i).length()>maxWidth)
	      maxWidth = frase.get(i).length();
	  }
	
	  
	  int fsize = (int)(width*0.8f/maxWidth);
	  textSize(fsize);
	  
	  int pos_Y = (int) (height*0.2f);
	  float stepY = textAscent() + textDescent();
	
	  
	
	  for (int i=0; i<frase.size();i++) {
	    int start_X = (int)(width/2 - textWidth(frase.get(i)));
	    for (int j=0;j<frase.get(i).length();j++) {
	      if (frase.get(i).charAt(j) != ' ') {
	    	int fase = 0;
	    	nf (fase, sopro_fases[i].charAt(j));
	        //FChar chr = new FChar(frase.get(i).charAt(j), fsize, fase);
	    	FChar chr = new FChar('o', fsize, fase);
	          println (frase.get(i).charAt(j));
	
	        if (chr.bodyCreated()) {
	          float pos_X =  textWidth(frase.get(i).substring( 0, j));
	          chr.setPosition(start_X+pos_X, pos_Y);
	          
	          world.add(chr);
	        }
	      }
	    }
        pos_Y += stepY;

	  } 
	  
	  
	
		mRecorder.startRecording();
		//mRecording = getFile("raw");
		startBufferedWrite();

	}
	
	public void draw() {
	  background(50);
	  fill(0);
	  stroke(0);

	  
	  world.draw(this);
	  world.step();
	  
	  //println (sopro);
	  
	  if (sopro > 1.0f) {
		  Iterator itr = world.getBodies().iterator();
	      while (itr.hasNext()) {
	          //vetor unitario
	          FBody body = (FBody) itr.next();
	          
	          // sopro vem do meio de baixo
	          float ux = body.getX() - width/2;
	          float uy = body.getY() - height;
	        
	          float uvector = dist (0,0,ux,uy);
	                
	          body.setForce((float)((ux/uvector)*sopro),(float)((uy/uvector)*sopro));
	      }
	  }//println ("lopp");
	  
	  /*
	  
	  
	  //verifica se faz mudança de fase
	  boolean changeFase = true;
	  
	  Iterator itr = world.getBodies().iterator();
	  
	  
      while (itr.hasNext()) {
          //vetor unitario
          FChar body = (FChar) itr.next();
          
          if (body.m_fase == fase) {
        	  if ((body.getX()<width)&&(body.getX()>0.0f)&&(body.getY()>0.0f)) {
        		changeFase = false;
        		break;
        	  }
        	  
          }
          
          
      }
      
	  
      if (changeFase) {
    	  fase++;
    	  if (fase == 5) {
    		  //fim
    	  } else {
    		  itr = world.getBodies().iterator();
    		  while (itr.hasNext()) {
    	          //vetor unitario
    	          FChar body = (FChar) itr.next();
    	          if (body.m_fase == fase) {
    	        	  body.setStatic(false);
    	          }
    	          
    		  }
    	  }
    	  
      }
    	  
	  */
	  
	}
	
	public void keyPressed() {  
	
	  if (key == CODED) {
	    if (keyCode == UP) {
	      sopro += 5.0f;
	      println (sopro);
	      Iterator itr = world.getBodies().iterator();
	      while (itr.hasNext()) {
	          //vetor unitario
	          FBody body = (FBody) itr.next();
	          
	          // sopro vem do meio de baixo
	          float ux = body.getX() - width/2;;
	          float uy = body.getY() - height;
	        
	          float uvector = dist (0,0,ux,uy);
	                
	          body.setForce((float)((ux/uvector)*sopro),(float)((uy/uvector)*sopro));
	        
	        
	      }
	    } else if (keyCode == DOWN) {
	      sopro -= 5.0f;
	      Iterator itr = world.getBodies().iterator();
	      while (itr.hasNext()) {
	        ((FBody)itr.next()).setForce(0.0f,(float)sopro);
	      }
	    }
	  }
	}

	class FChar extends FPoly {
		  RShape m_shape;
		  RShape m_poly;
		  boolean m_bodyCreated;
		  public int m_fase;
		    
		  FChar(char chr, int fsize, int p_fase){
		    super();
		    
		    String txt = "";
		    txt += chr;
		    m_fase = p_fase;
		    
		    RG.textFont(rFont, fsize);
		    m_shape = RG.getText(txt);
		    m_poly = RG.polygonize(m_shape);
		    
		    if (m_poly.countChildren() < 1) return;
		    m_poly = m_poly.children[0];    
		    
		    // Find the longest contour of our letter
		    float maxLength = 0.0f;
		    int maxIndex = -1;
		    for (int i = 0; i < m_poly.countPaths(); i++) {
		      float currentLength = m_poly.paths[i].getCurveLength();
		      if (currentLength > maxLength) {
		        maxLength = currentLength;
		        maxIndex = i;
		      }
		    }
		  
		    if (maxIndex == -1) return;
		    
		    RPoint[] points = m_poly.paths[maxIndex].getPoints();

		    for (int i=0; i<points.length; i++) {
		      this.vertex(points[i].x, points[i].y);
		    }

		    this.setFill(235, 235, 235);
		    this.setNoStroke();
		    
		    this.setDamping( 0.1f);
		    this.setFriction(0.9f);
		    //this.setRestitution(0.5);
		    this.setSensor(true);
		    //this.setPosition(posX+10, height/5);
		    if (m_fase != 0)
		    	this.setStatic(true); 
		    
		    //posX = (posX + m_poly.getWidth()) % (width-100);
		  
		    m_bodyCreated = true;
		  }
		  
		  boolean bodyCreated(){
		    return m_bodyCreated;  
		  }
		  
		  public void draw(PGraphics applet){
		    preDraw(applet);
		    m_shape.draw(applet);
		    postDraw(applet);
		  }
		}

	private void initRecorder() {
		int bufferSize = AudioRecord.getMinBufferSize(SAMPLE_RATE, AudioFormat.CHANNEL_IN_MONO,
				AudioFormat.ENCODING_PCM_16BIT);
		mBuffer = new short[bufferSize];
		mRecorder = new AudioRecord(MediaRecorder.AudioSource.MIC, SAMPLE_RATE, AudioFormat.CHANNEL_IN_MONO,
				AudioFormat.ENCODING_PCM_16BIT, bufferSize);
	}

	private void startBufferedWrite() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				//DataOutputStream output = null;
				try {
					//output = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
					while (true) {
						double sum = 0;
						int readSize = mRecorder.read(mBuffer, 0, mBuffer.length);
						for (int i = 0; i < readSize; i++) {
							//output.writeShort(mBuffer[i]);
							sum += mBuffer[i] * mBuffer[i];
						}
						if (readSize > 0) {
							//tá aqui
							
							sopro = sum / (readSize * 1000.0f) ;
							println (sopro);
						}
					}
				} finally {
				}
			}
		}).start();
	}
	
}



/*
public class Sopro extends Activity {

	public static final int SAMPLE_RATE = 16000;

	private AudioRecord mRecorder;
	private short[] mBuffer;
	private final String startRecordingLabel = "Start recording";
	private final String stopRecordingLabel = "Stop recording";
	private boolean mIsRecording = false;
	private ProgressBar mProgressBar;
	private double amplitude;

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sopro);

		initRecorder();

		mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

		final Button button = (Button) findViewById(R.id.button);
		button.setText(startRecordingLabel);

		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(final View v) {
				if (!mIsRecording) {
					button.setText(stopRecordingLabel);
					mIsRecording = true;
					mRecorder.startRecording();
					//mRecording = getFile("raw");
					startBufferedWrite();
				}
				else {
					button.setText(startRecordingLabel);
					mIsRecording = false;
					mRecorder.stop();
				}
			}
		});
	}

	@Override
	public void onDestroy() {
		mRecorder.release();
		super.onDestroy();
	}

	private void initRecorder() {
		int bufferSize = AudioRecord.getMinBufferSize(SAMPLE_RATE, AudioFormat.CHANNEL_IN_MONO,
				AudioFormat.ENCODING_PCM_16BIT);
		mBuffer = new short[bufferSize];
		mRecorder = new AudioRecord(MediaRecorder.AudioSource.MIC, SAMPLE_RATE, AudioFormat.CHANNEL_IN_MONO,
				AudioFormat.ENCODING_PCM_16BIT, bufferSize);
	}

	private void startBufferedWrite() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				//DataOutputStream output = null;
				try {
					//output = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
					while (mIsRecording) {
						double sum = 0;
						int readSize = mRecorder.read(mBuffer, 0, mBuffer.length);
						for (int i = 0; i < readSize; i++) {
							//output.writeShort(mBuffer[i]);
							sum += mBuffer[i] * mBuffer[i];
						}
						if (readSize > 0) {
							//tá aqui
							
							amplitude = sum / readSize;
							mProgressBar.setProgress((int) Math.sqrt(amplitude));
						}
					}
				} finally {
					mProgressBar.setProgress(0);
				}
			}
		}).start();
	}


}
*/