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

public class FavoritesList extends Activity {

	Button addFav, homeButton;
	ListView favList;
	private static final String PREFS_NAME = "favorites";
	ArrayList<String> stringList;
	String chosenStop;
	private SharedPreferences settings;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.favorites);

		addFav = (Button) findViewById(R.id.addFav);
		homeButton = (Button) findViewById(R.id.homeButton);
		favList = (ListView) findViewById(R.id.favList);
		chosenStop = "";
		addFav.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent favIntent = new Intent(v.getContext(), SimpleList.class);
				startActivity(favIntent);

			}

		});
		settings = getSharedPreferences(PREFS_NAME, 0);
		stringList = new ArrayList<String>();
//		try {
//			stringList = getIntent().getExtras()
//					.getStringArrayList("favorites");
//		} catch (Exception e) {
//			System.out.println("hold");
//		}
		for(int i = 0; i <20; i ++)
		{
			if (!settings.getString("favorites " + i, "").equals(""))
			{
				stringList.add(settings.getString("favorites " + i, ""));
			}
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				FavoritesList.this, R.layout.customlist, stringList);
		favList.setAdapter(adapter);

		favList.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> l, View v, int position,
					long id) {
				chosenStop = stringList.get(position);
				if (chosenStop.equalsIgnoreCase("HETHWOOD")) {
					chosenStop = "HWD";
				}

				if (chosenStop.equalsIgnoreCase("CRC")) {
					chosenStop = "CRC";
				}

				if (chosenStop.equalsIgnoreCase("HOKIE EXPRESS")) {
					chosenStop = "HXP";
				}

				if (chosenStop.equalsIgnoreCase("UNIVERSITY MALL SHUTTLE")) {
					chosenStop = "UMS";
				}

				if (chosenStop.equalsIgnoreCase("PROGRESS STREET")) {
					chosenStop = "PRG";
				}

				if (chosenStop.equalsIgnoreCase("TWO TOWN TROLLEY")) {
					chosenStop = "TTT";
				}

				if (chosenStop.equalsIgnoreCase("MAIN STREET NORTH")) {
					chosenStop = "MSN";
				}

				if (chosenStop.equalsIgnoreCase("MAIN STREET SOUTH")) {
					chosenStop = "MSS";
				}

				if (chosenStop.equalsIgnoreCase("UNIVERSITY CITY BOULEVARD")) {
					chosenStop = "UCB";
				}

				if (chosenStop.equalsIgnoreCase("HARDING AVENEUE")) {
					chosenStop = "HDG";
				}

				if (chosenStop.equalsIgnoreCase("PATRICK HENRY")) {
					chosenStop = "PH";
				}

				if (chosenStop.equalsIgnoreCase("TOMS CREEK")) {
					chosenStop = "TC";
				}
				Intent stopCodeIntent = new Intent(v.getContext(),
						StopCodeActivity.class);
				Bundle passData = new Bundle();
				passData.putString("stop", chosenStop);
				stopCodeIntent.putExtras(passData);
				startActivity(stopCodeIntent);
			}

		});

		homeButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent home = new Intent(FavoritesList.this, BusActivity.class);
				startActivity(home);
			}
		});

	}

}
