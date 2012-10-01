package com.android.vtbustracker;

import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
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
public class SecondStopActivity extends ListActivity {
	private ArrayList<String>[] stringList;
	ArrayAdapter<String> ad;
	AsyncRun run;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		run = new AsyncRun();
		try {
			stringList = run.execute().get();
		} catch (Exception e) {
			e.printStackTrace();
		}

		setListAdapter(new ArrayAdapter<String>(SecondStopActivity.this,
				R.layout.busfav, stringList[0]));
		getListView().setBackgroundColor(Color.rgb(128, 0, 0));

	}

	private class AsyncRun extends AsyncTask<String, Void, ArrayList<String>[]> {
		ArrayList<String> list = new ArrayList<String>();
		ArrayList<String> list2 = new ArrayList<String>();
		ArrayList<String> list3 = new ArrayList<String>();

		@Override
		protected ArrayList<String>[] doInBackground(String... params) {
			HttpClient client = new DefaultHttpClient();
			HttpGet get = new HttpGet(
					"http://www.bt4u.org/BT4U_Webservice.asmx/"
							+ "GetCurrentBusInfo?");

			SAXParserFactory sxf = SAXParserFactory.newInstance();
			try {
				HttpResponse response = client.execute(get);

				HttpEntity entity = response.getEntity();
				SAXParser sp = sxf.newSAXParser();

				DefaultHandler handler = new DefaultHandler() {
					boolean route = false;
					boolean code = false;
					String test = "";

					public void startElement(String uri, String localName,
							String qName, Attributes attributes)
							throws SAXException {

						if (qName.equals("RouteShortName")) {
							route = true;
						}

						if (qName.equals("TripPointName")) {
							code = true;
						}

					}

					public void endElement(String uri, String localName,
							String qName) throws SAXException {

					}

					public void characters(char ch[], int start, int length)
							throws SAXException {

						if (route) {
							test = new String(ch, start, length);
							if (!test.equals("")) {
								list2.add(test);
							}

							route = false;

						}

						if (code) {
							String test2 = new String(ch, start, length);
							if (!test.equals("")) {
								list3.add(test2);
							}

							code = false;
						}

					}

				};

				InputStream stream = entity.getContent();
				sp.parse(stream, handler);
				stream.close();
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

			for (int i = 0; i < list3.size(); i++) {
				String add = list3.get(i) + " " + list2.get(i);
				list.add(add);
			}
			@SuppressWarnings("unchecked")
			ArrayList<String>[] arrayList = new ArrayList[3];

			arrayList[0] = list;
			arrayList[1] = list2;
			arrayList[2] = list3;
			return arrayList;
		}

	}

}