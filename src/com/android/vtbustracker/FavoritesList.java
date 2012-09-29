package com.android.vtbustracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

public class FavoritesList extends Activity{

	Button addFav;
	ListView favList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.favorites);
		
		addFav = (Button) findViewById(R.id.addFav);
		favList = (ListView) findViewById(R.id.favList);
		
		addFav.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent favIntent= new Intent(v.getContext(), SecondStopActivity.class);
				startActivity(favIntent);
				
			}
			
		});
	}

	
	
}
