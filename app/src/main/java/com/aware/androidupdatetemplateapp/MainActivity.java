package com.aware.androidupdatetemplateapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RequestQueue queue;
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        queue = Volley.newRequestQueue(this);
        // Check for updates and if found show it in notifications

        JsonObjectRequest checkUpdateString =
                new JsonObjectRequest(Request.Method.GET, GlobalConfig.HOST + "/latest-version?appName="+GlobalConfig.APP, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                // Check if any new version is compatible
                                if(!MyUpdateManager.checkCompatibility())
                                {
                                    Log.d(TAG, "onResponse: The device is incompatible");
                                    return;
                                }

                                // Further check the version number
                                GlobalConfig configObj = GlobalConfig.getInstance();
                                try {
                                    configObj.setNEWVERSION(response.getString("latest_version"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                if(MyUpdateManager.checkVersion())
                                {
                                    // TODO: Show the Notification for update
                                    Log.d(TAG, "DUMMY NOTIFICATION: new version "+configObj.getNEWVERSION() + " is available.");
                                }

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e(TAG, "onErrorResponse: Error in /versions api for fetching updates" );
                            }});
        queue.add(checkUpdateString);


    }

    public void checkForUpdates(View view) {
        // Using AppUpdateManager to check updates

        final TextView currentVersionView = (TextView) findViewById(R.id.current_version_view);
        final TextView latestVersionView = (TextView) findViewById(R.id.latest_version_view);

        final ListView versionListView = (ListView) findViewById(R.id.version_list);
        final Context mContext = this;

        JsonObjectRequest stringRequest =
                new JsonObjectRequest(Request.Method.GET, GlobalConfig.HOST+"/", null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                ArrayList<Version> versionList;
                                VersionListAdapter vla;
                                JSONArray json_list;
                                try {
                                    Log.d(TAG, "onResponse: "+ response.toString());
                                    json_list = response.getJSONArray("items");
                                    versionList = new ArrayList<Version>();

                                    for (int i=0;i<json_list.length();i++)
                                    {
                                        JSONObject version_obj = json_list.getJSONObject(i);
                                        String appName = version_obj.getString("app_name");
                                        String version = version_obj.getString("version");
                                        String location = version_obj.getString("location");
                                        String os = version_obj.getString("os");
                                        versionList.add(new Version(
                                                appName,
                                                version,
                                                location,
                                                os));
                                    }

                                    vla = new VersionListAdapter(mContext, R.layout.list_view_layout,versionList);
                                    versionListView.setAdapter(vla);
                                } catch (JSONException e) {
                                    Log.e(TAG, "onResponse: Some error occurred in converting the response body." );
                                    e.printStackTrace();
                                }
                                GlobalConfig globalConfig = GlobalConfig.getInstance();
                                MyUpdateManager myUpdateManager = new MyUpdateManager();
                                currentVersionView.setText("Currently Installed Version: "+globalConfig.getVERSION());
                                latestVersionView.setText("Available Update: Version " + globalConfig.getNEWVERSION());
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        currentVersionView.setText("That didn't work!: "+ error.getMessage());
                        Log.e(TAG, "onResponse: "+ error.getMessage());
                    }
                });


        queue.add(stringRequest);
    }




}