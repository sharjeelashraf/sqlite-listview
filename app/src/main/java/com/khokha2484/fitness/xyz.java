package com.khokha2484.fitness;

import android.app.ActionBar;
import android.app.Activity;
import android.content.ClipData;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.text.ClipboardManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.AdView;

import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;

public class xyz extends Activity {
TextView text, text1, text2;  String head, desc; String id; ImageView image;int sdk = android.os.Build.VERSION.SDK_INT;InterstitialAd interAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xyz);
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
        displayInterstitial();
        text = (TextView)findViewById(R.id.head);
       text1 = (TextView)findViewById(R.id.desc);
        image = (ImageView)findViewById(R.id.picture);
        //text = (TextView)findViewById(R.id.text1);

        Bundle extras = getIntent().getExtras();


        if (extras != null) {
            head = extras.getString("RowHead");
            desc = extras.getString("RowDesc");
            id = extras.getString("RowId");

            // and get whatever type user account id is
        }
        text.setText(head);
        text1.isTextSelectable();
        text1.setText(desc);
//image.getDrawable();
        try {

            image.setImageResource(getApplicationContext().getResources().getIdentifier("drawable/" + "img"+id,
                    null, getApplicationContext().getPackageName()));

//                mAttacher = new PhotoViewAttacher(v);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Not loaded (Image)", Toast.LENGTH_SHORT).show();
//                Log.e("Here 1", "Here " + e.toString());
//                Log.e("Here 2", "Here2 " + e.getCause());
        }


    }
   // int num;
   // public int getImageResourceId() {return R.mipmap.;}
   //public void share(View v) {}
    public void copy(View v) {
        // TODO Auto-generated method stub
        displayInterstitial();
        String CopyText = text1.getText().toString();
        if(CopyText.length() != 0){
            if(sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {

                android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                clipboard.setText(CopyText);
                Toast.makeText(getApplicationContext(), "Text Copied to Clipboard", Toast.LENGTH_SHORT).show();

            } else {
                android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                android.content.ClipData clip = android.content.ClipData.newPlainText("Clip",CopyText);
                Toast.makeText(getApplicationContext(), "Text Copied to Clipboard", Toast.LENGTH_SHORT).show();
                clipboard.setPrimaryClip(clip);
            }}else{
            Toast.makeText(getApplicationContext(), "Nothing to Copy", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onBackPressed()
    {
        // code here to show dialog
        super.onBackPressed();
        // optional depending on your needs
        displayInterstitial();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:

             //   copyText();
                return true;
            case R.id.help:
             //   startActivity(new Intent(this, Help.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    ActivityInfo info;
    public void share(View view)
    {
       /* ACTION_SEND: Deliver some data to someone else.
        createChooser (Intent target, CharSequence title): Here, target- The Intent that the user will be selecting an activity to perform.
            title- Optional title that will be displayed in the chooser.
        Intent.EXTRA_TEXT: A constant CharSequence that is associated with the Intent, used with ACTION_SEND to supply the literal data to be sent.
        */
        displayInterstitial();
        PackageManager packageManager = this.getPackageManager();
        try {
             info = packageManager.getActivityInfo(this.getComponentName(), 0);
            Log.e("app", "Activity name:" + info.name);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, info.name+"\n Please Download the app" +"\n"+ text.getText().toString()+"\n"+ text1.getText().toString());
        sendIntent.setType("text/plain");
        Intent.createChooser(sendIntent,"Share via");
        startActivity(sendIntent);
    }

}