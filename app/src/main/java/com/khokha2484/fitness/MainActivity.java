package com.khokha2484.fitness;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.app.Activity;


public class MainActivity extends Activity {
	ListView list;
	String[] web = {
		"Google Plus",
			"Twitter",
			"Windows",
			"Bing",
			"Itunes",
			"Wordpress",
			"Drupal"
	} ;
	Integer[] imageId = {
			R.mipmap.ic_launcher,
			R.mipmap.ic_launcher,
			R.mipmap.ic_launcher,
			R.mipmap.ic_launcher,
			R.mipmap.ic_launcher,
			R.mipmap.ic_launcher,
			R.mipmap.ic_launcher

			
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		CustomList adapter = new
				CustomList(MainActivity.this, web, imageId);
		list=(ListView)findViewById(R.id.list);
				list.setAdapter(adapter);
				list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

		            @Override
		            public void onItemClick(AdapterView<?> parent, View view,
		                                    int position, long id) {
						int Nposition = position;
		                Toast.makeText(MainActivity.this, "You Clicked at " +web[+ position], Toast.LENGTH_SHORT).show();


						if(position == 1) {
							//code specific to first list item
							Intent myIntent = new Intent(view.getContext(), xyz.class);
							int Npos= position;
							myIntent.putExtra("position", Npos);
							startActivityForResult(myIntent, 0);
						}

						if(position == 2) {
							//code specific to 2nd list item
							Intent myIntent = new Intent(view.getContext(), xyz.class);
							startActivityForResult(myIntent, 0);
						}

					}
		        });

				
				
				
	}
			
}
