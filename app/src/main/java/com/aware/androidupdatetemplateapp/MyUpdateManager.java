package com.aware.androidupdatetemplateapp;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

public class MyUpdateManager {

    private static final String TAG = "UpdateManager";


    public MyUpdateManager(){}
    /**
     * This method will be called during start up or during installing new version
     * If the version is incompatible the notification will not be displayed to the user
     */
    public static boolean checkCompatibility(){
        // Check the compatibility
        // currently assuming that the device is compatible
        return true;
    }

    /**
     *
     *  @return Return true if the new version is greater than older one and false if not
     */
    public static boolean checkVersion()
    {
        // The format of version may differ in future and accordingly this logic must be updated
        // Currently, the version number is just a floating number
        try{
            GlobalConfig configObj = GlobalConfig.getInstance();
            if(Float.parseFloat(configObj.getNEWVERSION()) > Float.parseFloat(configObj.getVERSION())){
                Log.d(TAG, "checkVersion: New Version available!");
                return true;
            }
            else {
                Log.d(TAG, "checkVersion: No Updates available! The App is up to date!");
                return false;
            }
        }
        catch (NumberFormatException nfe)
        {
            Log.e(TAG, "checkVersion: Error in converting the version number" );
            return false;
        }

    }

}
