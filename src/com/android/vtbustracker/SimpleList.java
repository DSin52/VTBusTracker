package com.android.vtbustracker;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class SimpleList extends Activity {
	ArrayAdapter<String> ad;
	ListView listFav;
	Button done;
	Bundle bundle;
	ArrayList<String> pass;
	private static final String PREFS_NAME = "favorites";
	private SharedPreferences settings;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.busfav);
		pass = new ArrayList<String>();
		final String[] stringList = { "Hethwood", "CRC", "Hokie Express",
				"University Mall Shuttle", "Progress Street",
				"Two Town Trolley", "Main Street North", "Main Street South",
				"University City Boulevard", "Harding Avenue", "Patrick Henry",
				"Toms Creek" };
		bundle = new Bundle();
		settings = getSharedPreferences(PREFS_NAME, 0);
		listFav = (ListView) findViewById(R.id.listFav);

		done = (Button) findViewById(R.id.bDone);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				SimpleList.this, R.layout.customlist, stringList);
		listFav.setAdapter(adapter);

		listFav.setOnItemClickListener(new OnItemClickListener() {
			int i = 0;
			public void onItemClick(AdapterView<?> l, View v, int position,
					long id) {
				Toast.makeText(v.getContext(), "Added: " + stringList[position], 2000).show();
//				pass.add(stringList[position]);
//				bundle.putStringArrayList("favorites", pass);
				SharedPreferences.Editor editor = settings.edit();
				editor.putString("favorites " + i, stringList[position]);
				editor.commit();
				i ++;
				
			}
		});
		
		done.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(SimpleList.this, FavoritesList.class);
				intent.putExtras(bundle);
				startActivity(intent);
			}
			
		});

	}

}
