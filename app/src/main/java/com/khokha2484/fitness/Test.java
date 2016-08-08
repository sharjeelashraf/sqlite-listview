package com.khokha2484.fitness;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.AdView;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.ads.InterstitialAd;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Test extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener{
        EditText mText;
        Button mAdd;
        ListView mList;
        TextView text;
        MyDbHelper mHelper;
        SQLiteDatabase mDb;
        Cursor mCursor;
        SimpleCursorAdapter mAdapter; private InterstitialAd interAd;

        @Override
        public void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.main);

              //  mText = (EditText)findViewById(R.id.name);
                //mAdd = (Button)findViewById(R.id.add);
              //  mAdd.setOnClickListener(this);

                interAd = new InterstitialAd(this);

                interAd.setAdUnitId(getString(R.string.interstitial_ad));
                //Google ad unit
                //interAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");

                ///Create Ad
                requestad();

                interAd.setAdListener(new AdListener() {
                        //public void onAdLoaded() {
                        //displayInterstitial();
                        //}
                        public void onAdClosed() {
                                // When user closes ad end this activity (go back to first activity)
                                requestad();

                        }
                });

                mList = (ListView)findViewById(R.id.list);
                mList.setOnItemClickListener(this);
               // text = (TextView)findViewById(R.id.text1);
                mHelper = new MyDbHelper(this);
        }
        public void requestad()
        {

                AdView mAdView = (AdView) findViewById(R.id.adView);
                AdRequest adRequest = new AdRequest.Builder().build();

                interAd.loadAd(adRequest);
                mAdView.loadAd(adRequest);

        }

        public void displayInterstitial()
        {
                if(interAd.isLoaded())
                {
                        interAd.show();
                }
        }
        @Override
        public void onResume() {
                super.onResume();
                displayInterstitial();
                mDb = mHelper.getWritableDatabase();
                String[] columns = new String[] {"_id", MyDbHelper.COL_NAME, MyDbHelper.COL_DATE};
                mCursor = mDb.query(MyDbHelper.TABLE_NAME, columns, null, null, null, null, null, null);
                String[] headers = new String[] {MyDbHelper.COL_NAME};
                mAdapter = new SimpleCursorAdapter(this, R.layout.imagetwoline,
                        mCursor, headers, new int[]{ R.id.text1});
                mList.setAdapter(mAdapter);
        }

        @Override
        public void onPause() {
                super.onPause();
                mDb.close();
                mCursor.close();
        }

//This was used for text input through button input in DB
      @Override
        public void onClick(View v) {
                ContentValues cv = new ContentValues(2);
                cv.put(MyDbHelper.COL_NAME, mText.getText().toString());
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                cv.put(MyDbHelper.COL_DATE, mText.getText().toString()); //Insert 'now' as the date
                mDb.insert(MyDbHelper.TABLE_NAME, null, cv);
                mCursor.requery();
                mAdapter.notifyDataSetChanged();
                mText.setText(null);
      }

        @Override
        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                mCursor.moveToPosition(position);
                displayInterstitial();
                String rowId = mCursor.getString(0);
                String rowHead = mCursor.getString(1); //Column 0 of the cursor is the id
                String rowDesc = mCursor.getString(2); //Column 0 of the cursor is the id
                String pos = String.valueOf(position);
                //  text.setText(rowId);
                Intent intent = new Intent(Test.this,xyz.class);
                intent.putExtra("RowId", pos);
                intent.putExtra("RowHead", rowHead);
                intent.putExtra("RowDesc", rowDesc);
                startActivity(intent);
        }


}