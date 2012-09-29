package com.android.vtbustracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

public class BusActivity extends Activity {

	Button bLookUp, bFav;
	AutoCompleteTextView auto;
	String[] data = { "Hethwood", "CRC", "Hokie Express",
			"University Mall Shuttle", "Progress Street", "Two Town Trolley",
			"Main Street North", "Main Street South", "University City Boulevard", "Harding Avenue",
			"Patrick Henry", "Toms Creek" };

	String chosenStop = "";
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bus);

		auto = (AutoCompleteTextView) findViewById(R.id.textTitle);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, data);
		
		

		bLookUp = (Button) findViewById(R.id.bLookUp);
		
		bFav = (Button) findViewById(R.id.button1);
		
		bFav.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent favIntent = new Intent(v.getContext(), FavoritesList.class);
				startActivity(favIntent);
				
			}
			
		});

		adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

		auto.setAdapter(adapter);

		bLookUp.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				chosenStop = auto.getText().toString();
				
				if (chosenStop.equalsIgnoreCase("HETHWOOD"))
				{
					chosenStop = "HWD";
				}
				
				if (chosenStop.equalsIgnoreCase("CRC"))
				{
					chosenStop = "CRC";
				}
				
				if (chosenStop.equalsIgnoreCase("HOKIE EXPRESS"))
				{
					chosenStop = "HXP";
				}
				
				if (chosenStop.equalsIgnoreCase("UNIVERSITY MALL SHUTTLE"))
				{
					chosenStop = "UMS";
				}
				
				if (chosenStop.equalsIgnoreCase("PROGRESS STREET"))
				{
					chosenStop = "PRG";
				}
					
				if (chosenStop.equalsIgnoreCase("TWO TOWN TROLLEY"))
				{
					chosenStop = "TTT";
				}
				
				if (chosenStop.equalsIgnoreCase("MAIN STREET NORTH"))
				{
					chosenStop = "MSN";
				}
				
				if (chosenStop.equalsIgnoreCase("MAIN STREET SOUTH"))
				{
					chosenStop = "MSS";
				}
				
				if (chosenStop.equalsIgnoreCase("UNIVERSITY CITY BOULEVARD"))
				{
					chosenStop = "UCB";
				}
				
				if (chosenStop.equalsIgnoreCase("HARDING AVENEUE"))
				{
					chosenStop = "HDG";
				}
				
				if (chosenStop.equalsIgnoreCase("PATRICK HENRY"))
				{
					chosenStop = "PH";
				}
				
				if (chosenStop.equalsIgnoreCase("TOMS CREEK"))
				{
					chosenStop = "TC";
				}
				
				
				Intent stopCodeIntent = new Intent(v.getContext(), StopCodeActivity.class);
				Bundle passData = new Bundle();
				passData.putString("stop", chosenStop);
				stopCodeIntent.putExtras(passData);
				startActivity(stopCodeIntent);
			}

		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_bus, menu);
		return true;
	}
}
