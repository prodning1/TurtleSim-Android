package com.prodning.turtlesim.android.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.prodning.turtlesim.test.android.R;

public class FlightTimeActivity extends Activity {

    public static final String TAG = "TurtleSim-Android_FlightTimeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_time);

        if (savedInstanceState == null) {

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_combat:
                Intent intent = new Intent(FlightTimeActivity.this, CombatSimulationMainActivity.class);
                startActivity(intent);
                break;
            case R.id.action_trade:
                Intent intent_action_trade = new Intent(FlightTimeActivity.this, TradeRatioActivity.class);
                startActivity(intent_action_trade);
                break;
            case R.id.action_flight_time:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void calculateButtonOnClick(View view) {
        final EditText speedEditText = (EditText) findViewById(R.id.edit_speed);
        final EditText distanceEditText = (EditText) findViewById(R.id.edit_distance);
        final EditText resultEditText = (EditText) findViewById(R.id.resultsEditText);

        try {
            int speed = Integer.parseInt(speedEditText.getText().toString());
            int distance = Integer.parseInt(distanceEditText.getText().toString());
            int result = (int) (10.0 + 3500.0 * Math.sqrt((10 * (double) distance) / (double) speed));

            String resultString = Integer.toString(result) + " seconds";

            resultEditText.setText(resultString.toCharArray(), 0, resultString.length());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
