package net.brunovianna.poemapps.doublet;


import java.util.ArrayList;

import net.brunovianna.poemapps.R;
import net.brunovianna.poemapps.doublet.DoubletView;

import android.app.Activity;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;



public class DoubletActivity extends Activity {

	private DoubletView mDoubletView;
	private WindowManager mWindowManager;
	private Display mDisplay;
	private WakeLock mWakeLock;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


		// Get an instance of the WindowManager
		mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
		mDisplay = mWindowManager.getDefaultDisplay();


		//first this so the view is instantiated
		//setContentView(R.layout.ampulheta);
		mDoubletView = new DoubletView(getBaseContext(), null);
		
		mDoubletView.setWindowManager(mWindowManager);

	}

	@Override
	protected void onResume() {
		super.onResume();
		/*
		 * when the activity is resumed, we acquire a wake-lock so that the
		 * screen stays on, since the user will likely not be fiddling with the
		 * screen or buttons.
		 */
		mWakeLock.acquire();

	}

	@Override
	protected void onPause() {
		super.onPause();
		/*
		 * When the activity is paused, we make sure to stop the simulation,
		 * release our sensor resources and wake locks
		 */


		// and release our wake-lock
		mWakeLock.release();
	}


	/*	String primeira;
	ArrayList<String> dicionario = new ArrayList<String> ();
	ArrayList<String> possiveis = new ArrayList<String> ();
	ArrayList<String> palavras = new ArrayList<String> ();
	String abecedario = "abcdefghijklmnopqrstuvxyzáéóúãõç";
	PFont monoFont;
	float letterWidth, letterHeight;
	int indexLinha = 0;
	int indexPalavra = 0;
	int indexPossivel = 0;
	int ultimaLetra = -1;
	int ultimaLinha = -1;


	public void setup() {

		String words[] = loadStrings("br.txt");

		for (int i=0;i<words.length;i++)
			dicionario.add(words[i]);

		palavras.add("mente");



		monoFont = createFont("DroidSansMono.ttf", displayWidth/5, true);

		textFont(monoFont);

		letterWidth = textWidth("teste") / 5;
		letterHeight = textAscent() + textDescent();

	}


	public void draw() {


		int[] linha_e_letra = new int[2];
		background (204);
		orientation(PORTRAIT);

		fill(255,255,255);
		for (int i=0;i<indexLinha+1;i++) {
			text(palavras.get(i),0,(i+1)*letterHeight);

		}

		if (mousePressed) {
			linha_e_letra = locateTouch();
			if (linha_e_letra[0]<indexLinha) {
				indexLinha = linha_e_letra[0];
				for (int i=palavras.size()-1;i>linha_e_letra[0];i--)
					palavras.remove(i);
			}

			fill(255,0,0);
			text(palavras.get(indexLinha).charAt(linha_e_letra[1]),linha_e_letra[1]*letterWidth, (indexLinha+1)*letterHeight);
			if ((ultimaLinha==-1)||(ultimaLinha!=linha_e_letra[0])||(ultimaLetra==-1)||(ultimaLetra!=linha_e_letra[1])) {
				possiveis = buscaPalavras(palavras.get(indexLinha), linha_e_letra[1]);
				indexPossivel = 0;
				ultimaLinha=linha_e_letra[0];
				ultimaLetra=linha_e_letra[1];
			}

			if (possiveis.size() != 0) {
				indexLinha++;
				palavras.add(possiveis.get(indexPossivel));
				if (indexPossivel+1==possiveis.size())
					indexPossivel=0;
				else
					indexPossivel++;
				fill(255,255,255);
				text(palavras.get(indexLinha),0, letterHeight*(indexLinha+1));

			}

	}

}


public ArrayList<String> buscaPalavras(String palavra, int index) {

	ArrayList<String> resultado = new ArrayList<String> (); //palavras com uma letra diferente
	String comeco, fim;
	try {
		comeco = palavra.substring(0,index) ;
	} catch (Exception e) {
		comeco = null;
	}
	try {
		fim = palavra.substring(index+1) ;
	} catch (Exception e) {
		fim = null;
	}
	for (int j=0;j<abecedario.length();j++) {

		if (abecedario.charAt(j)==palavra.charAt(index))
			continue;

		String tentativa = "";

		if (comeco != null)
			tentativa = tentativa.concat(comeco);
		tentativa = tentativa.concat((abecedario.substring(j,j+1)));
		if (fim !=null)
			tentativa = tentativa.concat(fim);

		println(tentativa);

		if ((dicionario.indexOf(tentativa)!=-1) && (palavras.indexOf(tentativa)==-1))
			resultado.add(tentativa);

	}
	return resultado;

}




public ArrayList<ArrayList<String>> buscaTodasPalavras(String palavra) {

	ArrayList<ArrayList<String>> resultado = new ArrayList<ArrayList<String>>();


	for (int i=0;i<palavra.length();i++) {
		ArrayList<String> palavras = new ArrayList<String> (); //palavras com uma letra diferente
		String comeco, fim;
		try {
			comeco = palavra.substring(0,i-1) ;
		} catch (Exception e) {
			comeco = null;
		}
		try {
			fim = palavra.substring(i+1) ;
		} catch (Exception e) {
			fim = null;
		}
		for (int j=0;j<abecedario.length();j++) {

			String tentativa = "";

			if (comeco != null)
				tentativa = tentativa.concat(comeco);
			tentativa = tentativa.concat((abecedario.substring(j,j+1)));
			if (fim !=null)
				tentativa = tentativa.concat(fim);

			println(tentativa);

			if (dicionario.indexOf(tentativa)!=-1) 
				palavras.add(tentativa);

		}
		resultado.add(palavras);

	}



	return resultado;
}

public int[] locateTouch() {

	int[] resultado = new int[2];

	resultado [0] = (int) Math.abs(mouseY / letterHeight);
	resultado [1] = (int) Math.abs(mouseX / letterWidth);

	return resultado;


}
	 */
}