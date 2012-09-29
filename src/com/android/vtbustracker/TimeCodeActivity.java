package com.android.vtbustracker;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.annotation.TargetApi;
import android.app.ListActivity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;

@TargetApi(12)
public class TimeCodeActivity extends ListActivity {
	String stopadjTime; 
	String stopName;
	ArrayList<String> timings;
	String currentBus;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//stopadjTime = getIntent().getExtras().getString("stopNum", "fail");
		stopadjTime = getIntent().getExtras().getString("stopNum");

		System.out.println("ADJTIME" + stopadjTime);
		 
		//currentBus = getIntent().getExtras().getString("current", "fail");
		currentBus = getIntent().getExtras().getString("current");

		//stopName = getIntent().getExtras().getString("stopName", "fail");
		stopName = getIntent().getExtras().getString("stopName");

		Departures depart = new Departures();
		try {
			 timings = depart.execute().get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(timings.size());
		getListView().setBackgroundColor(Color.rgb(128, 0, 0));
		setListAdapter(new ArrayAdapter<String>(TimeCodeActivity.this,
				R.layout.customlist, timings));

	}

	private class Departures extends AsyncTask<String, Void, ArrayList<String>> {
		ArrayList<String> list = new ArrayList<String>();
		ArrayList<String> list2 = new ArrayList<String>();
		ArrayList<String> finish = new ArrayList<String>();

		@Override
		protected ArrayList<String> doInBackground(String... params) {

			HttpClient client = new DefaultHttpClient();

			HttpGet get = new HttpGet(
					"http://www.bt4u.org/BT4U_Webservice.asmx/"
							+ "GetNextDepartures?routeShortName="
							+ currentBus + "&stopCode=" + stopadjTime);

			try {
				list.clear();
				list2.clear();
				HttpResponse response = client.execute(get);
				HttpEntity entity = response.getEntity();

				SAXParserFactory sxf = SAXParserFactory.newInstance();
				SAXParser sp = sxf.newSAXParser();
				DefaultHandler handler = new DefaultHandler() {
					boolean patternPoint = false;
					boolean adjTime = false;

					String test = "";

					public void startElement(String uri, String localName,
							String qName, Attributes attributes)
							throws SAXException {

						if (qName.equals("PatternPointName")) {
							patternPoint = true;
						}

						if (qName.equals("AdjustedDepartureTime")) {
							adjTime = true;
						}

					}

					public void endElement(String uri, String localName,
							String qName) throws SAXException {

					}

					public void characters(char ch[], int start, int length)
							throws SAXException {

						if (patternPoint) {
							test = new String(ch, start, length);
							if (!test.equals("")) {
								list.add(test);
								System.out.println(test);
							}

							patternPoint = false;

						}

						if (adjTime) {
							String test2 = new String(ch, start, length);
							if (!test.equals("")) {
								list2.add(test2);
							}

							adjTime = false;
						}

					}

				};

				InputStream stream = entity.getContent();
				sp.parse(stream, handler);
				stream.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
			for (int i = 0; i < list.size(); i++)
			{
				String add = list.get(i) + " " + list2.get(i);
				finish.add(add);
				
			}
			System.out.println(finish.size());

			return finish;
		}

	}
}
