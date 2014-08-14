package net.brunovianna.poemapps;

import net.brunovianna.poemapps.doublet.DoubletSplashActivity;
import net.brunovianna.poemapps.pendulum.*;


import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
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
        	case(1): //significante
    	    	intent = new Intent (PoemAppActivity.this, DoubletSplashActivity.class);
    	        startActivity(intent);
    	        break;
        	case(2): //saudeus
		    	intent = new Intent (PoemAppActivity.this, DoubletSplashActivity.class);
		        startActivity(intent);
		        break;
        	case(3): //pendulo
		    	intent = new Intent (PoemAppActivity.this, PoemPendulum.class);
		        startActivity(intent);
		        break;
        	
        	}

          
        }
      });
    }
	
	static final String[] POEMAS = new String[] {"Doublet", "Significante", "Saudades", "Pêndulo"};

}
