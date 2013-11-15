package com.prodning.turtlesim.android.kernel_extensions;

import android.widget.ProgressBar;

import com.prodning.turtlesim.android.activity.MainActivity;
import com.prodning.turtlesim.kernel.combat.CombatSimulation;
import com.prodning.turtlesim.kernel.combat.data.FleetCombatUnit;
import com.prodning.turtlesim.kernel.combat.data.MacroCombatResult;
import com.prodning.turtlesim.kernel.combat.data.SimulationResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dby0jyq on 11/15/13.
 */
public class SimulateCombatRunnable implements Runnable {
    int numberOfSimulations;


    public SimulateCombatRunnable(String progressBarId, int numberOfSimulations, List<FleetCombatUnit> fleetCombatUnits) {

    }

    @Override
    public void run() {
        SimulationResult result = new SimulationResult();

        MainActivity.this.runOnUiThread(new Runnable() {

        });

        for(int i = 0; i < numberOfSimulations; i++) {
            ArrayList<FleetCombatUnit> newFleetCombatUnits = new ArrayList<FleetCombatUnit>();

            for(FleetCombatUnit fcu : fleetCombatUnits) {
                newFleetCombatUnits.add(fcu.deepClone());
            }

            pb.setProgress(i);

            MacroCombatResult mcr = CombatSimulation.fleetCombat(newFleetCombatUnits);

            result.addCombatResult(mcr);
        }
    }
}
