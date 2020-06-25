package com.aware.androidupdatetemplateapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        queue = Volley.newRequestQueue(this);
        // Check for updates and if found show it in notifications
    }

    public void checkForUpdates(View view) {
        // Using AppUpdateManager to check updates
        final TextView textView = (TextView) findViewById(R.id.text);
        final ListView versionListView = (ListView) findViewById(R.id.version_list);
        String host = "http://aa73baaf2c28.ngrok.io/";
        final Context mContext = this;
        JsonObjectRequest stringRequest =
                new JsonObjectRequest(Request.Method.GET, host, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                ArrayList<Version> versionList;
                                VersionListAdapter vla;
                                JSONArray json_list;
                                try {
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
                                    e.printStackTrace();
                                }

                                textView.setText(response.toString());



                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        textView.setText("That didn't work!: "+ error.getMessage());
                    }
                });

        queue.add(stringRequest);
    }




}