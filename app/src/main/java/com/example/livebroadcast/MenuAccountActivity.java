package com.example.livebroadcast;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MenuAccountActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_account);


        final TextView tv = (TextView)findViewById(R.id.tvtest);
        Button bt = (Button)findViewById(R.id.bttest);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText("nnnnnnnnnnnn");
            }
        });

        Log.d("MenuAccountActivity","onCreate");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_menu_account, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public  void onStart(){
        super.onStart();
        Log.d("MenuAccountActivity","onStart");
    }

    @Override
    public void onRestart(){
        super.onRestart();
        Log.d("MenuAccountActivity", "onRestart");
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d("MenuAccountActivity", "onResume");
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.d("MenuAccountActivity", "onPause");
    }

    @Override
    public void onStop(){
        super.onStop();
        Log.d("MenuAccountActivity", "onStop");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d("MenuAccountActivity", "onDestroy");
    }
}
