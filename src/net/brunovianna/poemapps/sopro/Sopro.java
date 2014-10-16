package net.brunovianna.poemapps.sopro;

import net.brunovianna.poemapps.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;


import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Environment;
import android.text.format.Time;
import android.widget.ProgressBar;
import android.widget.Toast;

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