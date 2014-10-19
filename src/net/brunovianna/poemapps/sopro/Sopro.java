package net.brunovianna.poemapps.sopro;

import net.brunovianna.poemapps.R;

import android.content.res.Resources;




import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;

import java.util.*;

import processing.core.*;
import processing.data.StringList;

public class Sopro extends PApplet {

	//Resources res;

	ArrayList<FChar> world;
	double sopro = 0;

	StringList frase;
	int[] fases;
	int fase;


	//String frase = "frase para soprar um pouco mais longa até";



	public static final int SAMPLE_RATE = 16000;
	private AudioRecord mRecorder;
	private short[] mBuffer;
	int sound_counter = 0;
	int sound_sum;
	boolean measuring_sound = true;
	float sound_threshold = 0;
	int millis;
	
	public void setup() {
		smooth();
		fill(200);
		
		world = new ArrayList<FChar> ();
				
		Resources res = getResources();
		CharSequence[] sopro_texto = res.getTextArray(R.array.sopro_texto);
		CharSequence[] sopro_fases = res.getTextArray(R.array.sopro_fases);
		
		frase = new StringList();
		
		for (int i=0; i<sopro_texto.length;i++) {
			frase.append (sopro_texto[i].toString());
		}
		
		//frase.append("guelra palavra");
		/*frase.append("dentro dela o ar");
		  frase.append("entrando e saindo");
		  frase.append("expirar inspirar");
		  frase.append("colocar uma palavra fora d’agua");
		  frase.append("nas entre le / r / tras");
		  
		  frase.append("a palavra respira os sentidos que");
		  frase.append("ainda vêm");
		  frase.append("não é uma guerra");
		  frase.append("mas uma outra espécie de paz");
		  frase.append("a palavra erra");
		  frase.append("quando diz mais");
		
		  frase = new StringList();
		frase.append("guelra palavra");
		frase.append("dentro dela o ar");
		  frase.append("entrando e saindo");
		  frase.append("expirar inspirar");
		  frase.append("colocar uma palavra fora d’agua");
		  frase.append("nas entre le / r / tras");
		  frase.append("a palavra respira os sentidos que");
		  frase.append("ainda vêm");
		  frase.append("não é uma guerra");
		  frase.append("mas uma outra espécie de paz");
		  frase.append("a palavra erra");
		  frase.append("quando diz mais");*/

		String stringFont = "LiberationSerif-Bold";

		PFont pFont = loadFont(stringFont+".vlw");
		textFont (pFont);

		//posicionando o texto
		int maxWidth = 0;
		for (int i=0;i<frase.size();i++) {
			if (frase.get(i).length()>maxWidth)
				maxWidth = frase.get(i).length();
		}


		int fsize = (int)(width*2.0f/maxWidth);
		textSize(fsize);

		int pos_Y = (int) (height*0.2f);
		float stepY = textAscent() + textDescent();



		for (int i=0; i<frase.size();i++) {
			int start_X = (int)(width/2 - textWidth(frase.get(i))/2);
			for (int j=0;j<frase.get(i).length();j++) {
				if (frase.get(i).charAt(j) != ' ') {
					float pos_X =  textWidth(frase.get(i).substring( 0, j));
					float mass = 1.0f + random (100)/1000.0f;
					float friction = 0.9f;
					FChar chr = new FChar(frase.get(i).charAt(j), fsize, start_X+ pos_X, pos_Y, mass, friction);
					println (frase.get(i).charAt(j));


					world.add(chr);
				}
			}
			pos_Y += stepY;

		}


		initRecorder();

		mRecorder.startRecording();
		//mRecording = getFile("raw");
		startBufferedWrite();

		millis = millis();
	}

	public	void draw() {
		
		if (measuring_sound) {
			sound_counter++;
			sound_sum += sopro;
			if (millis + 1000 < millis()) {
				sound_threshold = (sound_sum/sound_counter) * 2.0f;
				if (sound_threshold > 100000.0f)
					sound_threshold = 100000.0f;
				measuring_sound = false;
			}
				
		} else {
		
		
		background(50);
		fill(200);
		stroke(0);

		
		Iterator itr = world.iterator();
		while (itr.hasNext()) {
			FChar chr = (FChar) itr.next();
			if (sopro > sound_threshold) {
				//vetor unitario
				
				// sopro vem do meio de baixo
				float ux = chr.m_X - width/2;
				float uy = chr.m_Y - height;

				float uvector = dist (0,0,ux,uy);

				chr.setForce((float)((ux/uvector)*(sopro/sound_threshold)*0.00001f),(float)((uy/uvector)*(sopro/sound_threshold)*0.00001f));
			}
			
			chr.draw();
		}
		}

	}

	public void keyPressed() {  

		/*
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
		  */
	}

	class FChar {

		public int m_fase;
		public char m_char;
		float m_friction, m_accelX, m_accelY;
		float m_X, m_Y, m_velX, m_velY, m_forceX, m_forceY, m_mass;
		boolean m_static;
		
		FChar(char chr, int p_fase, float x, float y, float m, float f){
		
			m_char = chr;
			m_fase = p_fase;
			
			m_mass = m;
			m_friction = f;
			m_X = x;
			m_Y = y;
			
			m_accelX = 0.0f;
			m_accelY = 0.0f;
			
		}
		
		public void setFriction (float f) {
			m_friction = f;
		}

		public void setForce (float x, float y) {
			
			m_forceX = x;
			m_forceY = y;
		}
		
		public void draw(){
			
			m_accelX = m_accelX + ( m_forceX / m_mass ) *  m_friction;
			m_velX = m_velX + m_accelX;
			m_X = m_X + m_velX;
			
			m_accelY = m_accelY + ( m_forceY / m_mass ) *  m_friction;
			m_velY = m_velY + m_accelY;
			m_Y = m_Y + m_velY;
			
			text (m_char, m_X, m_Y);

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