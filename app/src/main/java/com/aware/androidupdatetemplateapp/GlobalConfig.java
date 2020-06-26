package com.aware.androidupdatetemplateapp;

import android.app.Application;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class GlobalConfig extends Application {
    public static String HOST = "http://9126354bec1c.ngrok.io";
    public static String APP = "Application1";
    private String VERSION = "1.0";
    private String NEWVERSION = "1.0";
    private static  GlobalConfig globalConfigSingtonObj;
    private RequestQueue queue;
    private GlobalConfig() { }
    private static final String TAG = "GlobalConfig";
    /**
     * This is the getInstance method which implements the singleton design pattern
     * @return global object of class GlobalConfig
     */
    public static GlobalConfig getInstance()
    {
        if(globalConfigSingtonObj == null){

            synchronized(GlobalConfig.class)
            {
                if(globalConfigSingtonObj == null){
                    globalConfigSingtonObj = new GlobalConfig();
                }
            }
        }
        return globalConfigSingtonObj;
    }

    public String getVERSION() {
        return VERSION;
    }

    public void setVERSION(String VERSION) {
        this.VERSION = VERSION;
    }

    public String getNEWVERSION() {
        return NEWVERSION;
    }

    public void setNEWVERSION(String NEWVERSION) {
        this.NEWVERSION = NEWVERSION;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        queue = Volley.newRequestQueue(this);
        // check if new version is available
        JsonObjectRequest checkUpdateString =
                new JsonObjectRequest(Request.Method.GET, GlobalConfig.HOST + "/latest-version?appName=" + GlobalConfig.APP, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                Log.d(TAG, "onCreate: latest version "+response.toString() );
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e(TAG, "onCreate : Error in fetching latest version");
                            }
                        });
        queue.add(checkUpdateString);

    }
}
