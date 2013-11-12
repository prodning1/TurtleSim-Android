package com.prodning.turtlesim.test.android;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.prodning.turtlesim.kernel.test.gui.FleetCombatTestGUI;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            final Button simulateButton = (Button) findViewById(R.id.simulateButton);

            simulateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final TextView resultsTextView = (TextView) findViewById(R.id.resultsTextView);

                    String flt1 = ((EditText) findViewById(R.id.attackingFleetEditText)).getText().toString();
                    String flt2 = ((EditText) findViewById(R.id.defendingFleetEditText)).getText().toString();

                    String resultString = "[error in simulation]";

                    try {
                        resultString = FleetCombatTestGUI.getResults(flt1, flt2);
                    } catch (Exception e) {
                        resultString = e.getMessage();
                    }

                    resultsTextView.setText(resultString.toCharArray(), 0, resultString.length());
                }
            });
        }
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }

}
