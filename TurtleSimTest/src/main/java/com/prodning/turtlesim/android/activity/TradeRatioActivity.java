package com.prodning.turtlesim.android.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
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
                try {
                    EditText metalRatioEditText = (EditText) findViewById(R.id.edit_metal);
                    EditText crystalRatioEditText = (EditText) findViewById(R.id.edit_crystal);
                    EditText deuteriumRatioEditText = (EditText) findViewById(R.id.edit_deuterium);

                    double metalRatio = Double.parseDouble(metalRatioEditText.getText().toString());
                    double crystalRatio = Double.parseDouble(crystalRatioEditText.getText().toString());
                    double deuteriumRatio = Double.parseDouble(deuteriumRatioEditText.getText().toString());

                    EditText currentMetalEditText = (EditText) findViewById(R.id.io_metal);
                    EditText currentCrystalEditText = (EditText) findViewById(R.id.io_crystal);
                    EditText currentDeuteriumEditText = (EditText) findViewById(R.id.io_deuterium);

                    int currentMetal = Integer.parseInt(currentMetalEditText.getText().toString());
                    int currentCrystal = Integer.parseInt(currentCrystalEditText.getText().toString());
                    int currentDeuterium = Integer.parseInt(currentDeuteriumEditText.getText().toString());

                    int finalMetal;
                    int finalCrystal;
                    int finalDeuterium;

                    switch(view.getId()) {
                        case R.id.button_metal_calc:
                            finalMetal = currentMetal;
                            finalCrystal = (int) (currentMetal*crystalRatio/metalRatio);
                            finalDeuterium = (int) (currentMetal*deuteriumRatio/metalRatio);
                            break;
                        case R.id.button_crystal_calc:
                            finalMetal = (int) (currentCrystal*metalRatio/crystalRatio);
                            finalCrystal = currentCrystal;
                            finalDeuterium = (int) (currentCrystal*deuteriumRatio/crystalRatio);
                            break;
                        case R.id.button_deuterium_calc:
                            finalMetal = (int) (currentDeuterium*metalRatio/deuteriumRatio);
                            finalCrystal = (int) (currentDeuterium*crystalRatio/deuteriumRatio);
                            finalDeuterium = currentDeuterium;
                            break;
                        default:
                            finalMetal = currentMetal;
                            finalCrystal = currentCrystal;
                            finalDeuterium = currentDeuterium;
                    }

                    Log.i(TAG, Integer.toString(finalMetal));

                    currentMetalEditText.setText(Integer.toString(finalMetal).toCharArray(), 0,
                                                 Integer.toString(finalMetal).length());
                    currentCrystalEditText.setText(Integer.toString(finalCrystal).toCharArray(), 0,
                                                 Integer.toString(finalCrystal).length());
                    currentDeuteriumEditText.setText(Integer.toString(finalDeuterium).toCharArray(), 0,
                                                 Integer.toString(finalDeuterium).length());

                } catch (NumberFormatException e) {
                    Log.e(TAG, "Error in parsing ratios: ");
                }
            }
        };


        Button metalCalcButton = (Button) findViewById(R.id.button_metal_calc);
        Button crystalCalcButton = (Button) findViewById(R.id.button_crystal_calc);
        Button deuteriumCalcButton = (Button) findViewById(R.id.button_deuterium_calc);

        metalCalcButton.setOnClickListener(onClick);
        crystalCalcButton.setOnClickListener(onClick);
        deuteriumCalcButton.setOnClickListener(onClick);
    }
}