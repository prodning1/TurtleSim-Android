package com.prodning.turtlesim.android.resource_utility;

import android.app.Activity;
import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by dby0jyq on 11/15/13.
 */
public class ResourceFileManagement {

    public static void initializeInternalStorage() {

    }

    public static File resToFile(Activity parentActivity, int resourceID, String filename) {
        File file = parentActivity.getApplicationContext().getFileStreamPath(filename);
        if(file.exists()) {
            return file;
        }

        InputStream is;
        FileOutputStream fos;
        try {
            is = parentActivity.getResources().openRawResource(resourceID);
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            fos = parentActivity.openFileOutput(filename, Context.MODE_PRIVATE);
            fos.write(buffer);
            fos.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
}
