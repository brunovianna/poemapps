package net.brunovianna.poemapps;

import net.brunovianna.poemapps.pelacidade.*;
import net.brunovianna.poemapps.doublet.DoubletSplashActivity;
import net.brunovianna.poemapps.pelacidade.*;
import net.brunovianna.poemapps.rgb.*;
import net.brunovianna.poemapps.bussola.*;
import net.brunovianna.poemapps.sopro.*;


import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.view.View;

public class PoemAppActivity extends ListActivity {
    /** Called when the activity is first created. */
 
	public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

      setListAdapter(new ArrayAdapter<String>(this, R.layout.poemapp_layout, POEMAS));

      ListView lv = getListView();
      lv.setTextFilterEnabled(true);

      lv.setOnItemClickListener(new OnItemClickListener() {
        public void onItemClick(AdapterView<?> parent, View view,
            int position, long id) {
        	Intent intent;
        	switch(position) {

        	case(0): //ampulheta
    	    	intent = new Intent (PoemAppActivity.this, DoubletSplashActivity.class);
    	        startActivity(intent);
    	        break;
        	case(1): //cidade
    	    	intent = new Intent (PoemAppActivity.this, PelacidadeActivity.class);
    	        startActivity(intent);
    	        break;
        	case(2): //rgb
		    	intent = new Intent (PoemAppActivity.this, Rgb.class);
		        startActivity(intent);
		        break;
        	case(3): //oriente
		    	intent = new Intent (PoemAppActivity.this, Bussola.class);
		        startActivity(intent);
		        break;
        	case(6): //sopro
		    	intent = new Intent (PoemAppActivity.this, Sopro.class);
		        startActivity(intent);
		        break;
        	
        	}

          
        }
      });
    }
	
	static final String[] POEMAS = new String[] {"Doublets", "Pela cidade", "Poema em RGB", "Oriente", "Sinuca", "Réu Lógico", "Sopro"};

}
