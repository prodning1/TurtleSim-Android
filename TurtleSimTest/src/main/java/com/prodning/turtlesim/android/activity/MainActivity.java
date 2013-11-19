package com.prodning.turtlesim.android.activity;

import android.app.Activity;
import android.content.Entity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.prodning.turtlesim.kernel.exception.TS_DuplicateIDException;
import com.prodning.turtlesim.kernel.exception.TS_GenericParseException;
import com.prodning.turtlesim.kernel.exception.TS_IDNotFoundException;
import com.prodning.turtlesim.kernel.parse.EntityFileParser;
import com.prodning.turtlesim.kernel.test.gui.FleetCombatTestGUI;
import com.prodning.turtlesim.test.android.R;

import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;

public class MainActivity extends Activity {
    public static final String TAG = "TurtleSim-Android_MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
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

            final Spinner attackingFleetSpinner = (Spinner) findViewById(R.id.spinner_attacking_fleet);
            final Spinner defendingFleetSpinner = (Spinner) findViewById(R.id.spinner_defending_fleet);

            try {
                String[] fleets = (String[]) EntityFileParser.getListOfFleetIds().toArray();

                ArrayAdapter<String> fleetArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, fleets);


                attackingFleetSpinner.setAdapter(fleetArrayAdapter);
                defendingFleetSpinner.setAdapter(fleetArrayAdapter);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "Error in fleet spinner");
                System.exit(1);
            }
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
            case R.id.action_settings:
                return true;
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
