package com.prodning.turtlesim.android.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.prodning.turtlesim.kernel.parse.EntityFileParser;
import com.prodning.turtlesim.kernel.test.gui.FleetCombatTestGUI;
import com.prodning.turtlesim.test.android.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class CombatSimulationMainActivity extends Activity {
    public static final String TAG = "TurtleSim-Android_MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combat_simulation_main);

        if (savedInstanceState == null) {
            //Get file handles for the resource XML files and pass them to the EntityFileParser class
            File fleetFile = resToFile(R.raw.fleets, "fleets.xml");
            File entitiesFile = resToFile(R.raw.entities, "entities.xml");

            try {
                EntityFileParser.setFleetFile(fleetFile);
                EntityFileParser.setEntitiesFile(entitiesFile);
            } catch (Exception e) {
                final TextView resultsTextView = (TextView) findViewById(R.id.resultsTextView);
                String resultString = e.getMessage();
                resultsTextView.setText(resultString.toCharArray(), 0, resultString.length());
            }


            //Initialize fleet picker spinners
            final Spinner attackingFleetSpinner = (Spinner) findViewById(R.id.spinner_attacking_fleet);
            final Spinner defendingFleetSpinner = (Spinner) findViewById(R.id.spinner_defending_fleet);

            ArrayList<String> fleetArrayList = new ArrayList<String>();
            fleetArrayList.add("ERROR");

            try {
                fleetArrayList = EntityFileParser.getListOfFleetIds();

                fleetArrayList.add(0,getResources().getString(R.string.select_one));

                String[] fleets = fleetArrayList.toArray(new String[0]);

                ArrayAdapter<String> fleetArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, fleets);


                attackingFleetSpinner.setAdapter(fleetArrayAdapter);
                defendingFleetSpinner.setAdapter(fleetArrayAdapter);
            } catch (Exception e) {
                Log.e(TAG, "Error in fleet spinner");
                Log.e(TAG, e.getMessage());
                Log.v(TAG, fleetArrayList.toString());
                System.exit(1);
            }

            //initially set the simulate button to disabled (enable it when 2 valid fleets are selected)
            final Button simulateButton = (Button) findViewById(R.id.button_simulate);

            simulateButton.setEnabled(false);

            //set spinner listener
            attackingFleetSpinner.setOnItemSelectedListener(fleetSpinnerListener);
            defendingFleetSpinner.setOnItemSelectedListener(fleetSpinnerListener);
        }
    }

    public AdapterView.OnItemSelectedListener fleetSpinnerListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            checkSimulateButtonEnable();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            checkSimulateButtonEnable();
        }
    };

    private void checkSimulateButtonEnable() {
        final Spinner attackingFleetSpinner = (Spinner) findViewById(R.id.spinner_attacking_fleet);
        final Spinner defendingFleetSpinner = (Spinner) findViewById(R.id.spinner_defending_fleet);

        final Button simulateButton = (Button) findViewById(R.id.button_simulate);

        String flt1 = (String) ((Spinner) findViewById(R.id.spinner_attacking_fleet)).getSelectedItem();
        String flt2 = (String) ((Spinner) findViewById(R.id.spinner_defending_fleet)).getSelectedItem();

        String selectOne = getResources().getString(R.string.select_one);

        if(flt1.equals(selectOne) || flt2.equals(selectOne)) {
            simulateButton.setEnabled(false);
        } else {
            simulateButton.setEnabled(true);
        }
    }

    public void simulateButtonOnClick(View view) {
        final TextView resultsTextView = (TextView) findViewById(R.id.resultsTextView);

        String flt1 = (String) ((Spinner) findViewById(R.id.spinner_attacking_fleet)).getSelectedItem();
        String flt2 = (String) ((Spinner) findViewById(R.id.spinner_defending_fleet)).getSelectedItem();

        String resultString = "[error in simulation]";

        try {
            resultString = FleetCombatTestGUI.getResults(flt1, flt2);
        } catch (Exception e) {
            resultString = e.getMessage();
        }

        resultsTextView.setText(resultString.toCharArray(), 0, resultString.length());
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
                return true;
            case R.id.action_trade:
                Intent intent = new Intent(CombatSimulationMainActivity.this, TradeRatioActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

//    /**
//     * A placeholder fragment containing a simple view.
//     */
//    public static class PlaceholderFragment extends Fragment {
//
//        public PlaceholderFragment() {
//        }
//
//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                Bundle savedInstanceState) {
//            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
//            return rootView;
//        }
//    }

    private File resToFile(int resourceID, String filename) {
        File file = getApplicationContext().getFileStreamPath(filename);
        if(file.exists()) {
            return file;
        }

        InputStream is;
        FileOutputStream fos;
        try {
            is = getResources().openRawResource(resourceID);
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            fos = openFileOutput(filename, MODE_PRIVATE);
            fos.write(buffer);
            fos.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file;
    }
}
