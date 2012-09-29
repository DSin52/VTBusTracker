package com.android.vtbustracker;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

@TargetApi(12)
public class SecondStopActivity extends ListActivity {
	private ArrayList<String>[] stringList;
	ArrayAdapter<String> ad;
	String test = "";
	String currentBus = "";
	RunThread newRun;
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		

	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//setCurrentBus(getIntent().getExtras().getString("stop", "fail"));
		setCurrentBus(getIntent().getExtras().getString("stop"));

		newRun = new RunThread();
		try {
			stringList = newRun.execute().get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
			
		    
			setListAdapter(new ArrayAdapter<String>(SecondStopActivity.this,
					R.layout.customlist, stringList[0]));
			getListView().setBackgroundColor(Color.rgb(128, 0, 0));

	}


	private class RunThread extends AsyncTask<String, Void, ArrayList<String>[]> {
		ArrayList<String> list = new ArrayList<String>();
		ArrayList<String> list2 = new ArrayList<String>();
		ArrayList<String> list3 = new ArrayList<String>();


		@Override
		protected ArrayList<String>[] doInBackground(String... params) {
			HttpClient client = new DefaultHttpClient();

			HttpGet getRequest = new HttpGet(
					"http://www.bt4u.org/BT4U_Webservice.asmx/"
							+ "GetScheduledStopNames?routeShortName=" + getCurrentBus());
			try {
				
				list.clear();
				list2.clear();
				list3.clear();
				HttpResponse response = client.execute(getRequest);

				HttpEntity entity = response.getEntity();

				SAXParserFactory sxf = SAXParserFactory.newInstance();

				SAXParser sp = sxf.newSAXParser();

				DefaultHandler handler = new DefaultHandler() {
					boolean route = false;
					boolean code = false;
					String test = "";
					public void startElement(String uri, String localName,
							String qName, Attributes attributes)
							throws SAXException {

						if (qName.equals("StopName")) {
							route = true;
						}
						
						if(qName.equals("StopCode"))
						{
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

			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("LIST SIZE: " + list2.size());
			System.out.println("LIST SIZE: " + list3.size());
			System.out.println(list3.get(0) + " " + list2.get(0));
			for (int i = 0; i < list3.size(); i ++)
			{
				String add = list3.get(i) + " " + list2.get(i);
				list.add(add);
			}
			System.out.println(list.get(3));
			@SuppressWarnings("unchecked")
			ArrayList<String>[] arrayList = new ArrayList[3];
			
			Collections.sort(list, new Comparator<String>() {
			    public int compare(String a, String b) {
			        return Integer.signum(fixString(a) - fixString(b));
			    }
			    private int fixString(String in) {
			        return Integer.parseInt(in.substring(0, 4));
			    }
			});
			Collections.sort(list3, new Comparator<String>() {
			    public int compare(String a, String b) {
			        return Integer.signum(fixString(a) - fixString(b));
			    }
			    private int fixString(String in) {
			        return Integer.parseInt(in.substring(0, 4));
			    }
			});
			
			arrayList[0] = list;
			arrayList[1] = list2;
			arrayList[2] = list3;
			return arrayList;
		}
		
	}
	
	public void setCurrentBus(String bus)
	{
		currentBus = bus;
	}
	public String getCurrentBus()
	{
		return currentBus;
	}
}