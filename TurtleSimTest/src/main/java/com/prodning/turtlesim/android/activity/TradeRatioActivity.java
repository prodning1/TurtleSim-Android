package com.prodning.turtlesim.android.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.prodning.turtlesim.test.android.R;

/**
 * Created by phider on 11/18/13.
 */
public class TradeRatioActivity extends Activity {
    public static final String TAG = "TurtleSim-Android_TradeRatioActivity";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_ratio);


        View.OnClickListener onClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText metalRatioEditText = (EditText) findViewById(R.id.edit_metal);
                EditText crystalRatioEditText = (EditText) findViewById(R.id.edit_crystal);
                EditText deuteriumRatioEditText = (EditText) findViewById(R.id.edit_deuterium);

                double metalRatio;
                double crystalRatio;
                double deuteriumRatio;

                try { metalRatio = Double.parseDouble(metalRatioEditText.getText().toString());
                } catch (NumberFormatException e) {
                    Log.e(TAG, "Error in parsing ratios: "); return; }
                try { crystalRatio = Double.parseDouble(crystalRatioEditText.getText().toString());
                } catch (NumberFormatException e) {
                    Log.e(TAG, "Error in parsing ratios: "); return; }
                try { deuteriumRatio = Double.parseDouble(deuteriumRatioEditText.getText().toString());
                } catch (NumberFormatException e) {
                    Log.e(TAG, "Error in parsing ratios: "); return; }

                EditText currentMetalEditText = (EditText) findViewById(R.id.io_metal);
                EditText currentCrystalEditText = (EditText) findViewById(R.id.io_crystal);
                EditText currentDeuteriumEditText = (EditText) findViewById(R.id.io_deuterium);

                int currentMetal;
                int currentCrystal;
                int currentDeuterium;

                try { currentMetal = Integer.parseInt(currentMetalEditText.getText().toString());
                } catch (NumberFormatException e) { currentMetal = 0; }
                try { currentCrystal = Integer.parseInt(currentCrystalEditText.getText().toString());
                } catch (NumberFormatException e) { currentCrystal = 0; }
                try { currentDeuterium = Integer.parseInt(currentDeuteriumEditText.getText().toString());
                } catch (NumberFormatException e) { currentDeuterium = 0; }

                int finalMetal;
                int finalCrystal;
                int finalDeuterium;

                switch (view.getId()) {
                    case R.id.button_metal_calc:
                        finalMetal = currentMetal;
                        finalCrystal = (int) (currentMetal * crystalRatio / metalRatio);
                        finalDeuterium = (int) (currentMetal * deuteriumRatio / metalRatio);
                        break;
                    case R.id.button_crystal_calc:
                        finalMetal = (int) (currentCrystal * metalRatio / crystalRatio);
                        finalCrystal = currentCrystal;
                        finalDeuterium = (int) (currentCrystal * deuteriumRatio / crystalRatio);
                        break;
                    case R.id.button_deuterium_calc:
                        finalMetal = (int) (currentDeuterium * metalRatio / deuteriumRatio);
                        finalCrystal = (int) (currentDeuterium * crystalRatio / deuteriumRatio);
                        finalDeuterium = currentDeuterium;
                        break;
                    default:
                        finalMetal = currentMetal;
                        finalCrystal = currentCrystal;
                        finalDeuterium = currentDeuterium;
                }

                currentMetalEditText.setText(Integer.toString(finalMetal).toCharArray(), 0,
                        Integer.toString(finalMetal).length());
                currentCrystalEditText.setText(Integer.toString(finalCrystal).toCharArray(), 0,
                        Integer.toString(finalCrystal).length());
                currentDeuteriumEditText.setText(Integer.toString(finalDeuterium).toCharArray(), 0,
                        Integer.toString(finalDeuterium).length());
            }
        };


        Button metalCalcButton = (Button) findViewById(R.id.button_metal_calc);
        Button crystalCalcButton = (Button) findViewById(R.id.button_crystal_calc);
        Button deuteriumCalcButton = (Button) findViewById(R.id.button_deuterium_calc);

        metalCalcButton.setOnClickListener(onClick);
        crystalCalcButton.setOnClickListener(onClick);
        deuteriumCalcButton.setOnClickListener(onClick);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_combat:
                Intent intent = new Intent(TradeRatioActivity.this, CombatSimulationMainActivity.class);
                startActivity(intent);
                break;
            case R.id.action_trade:
                return true;
            case R.id.action_flight_time:
                Intent intent_action_flight_time = new Intent(TradeRatioActivity.this, FlightTimeActivity.class);
                startActivity(intent_action_flight_time);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}