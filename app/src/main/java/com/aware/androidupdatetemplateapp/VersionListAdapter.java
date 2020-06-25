/**
 * Author : Dharmang Solanki
 * Description : ArrayAdapterClass to populate the list view with list of
 * versions.
 * Version list is the list of Version class
 */
package com.aware.androidupdatetemplateapp;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.Response;

import org.json.JSONObject;

import java.util.List;

public class VersionListAdapter extends ArrayAdapter<Version> {
    private static final String TAG = "VersionListAdapter";
    private Context mContext;
    int mResource;

    /**
     * Constructor
     * @param context The context in which list will be populated
     * @param resource the layout which we will use to populate list view
     * @param versions List of versions fetched from server
     */
    public VersionListAdapter(Context context, int resource, List<Version> versions) {
        super(context, resource, versions);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // get the versions information
        String appName = getItem(position).getAppName();
        String number = getItem(position).getNumber();
        String location = getItem(position).getLocation();
        String os = getItem(position).getOs();

        Version version = new Version(appName,number,location,os);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource,parent,false);

        TextView version_text = (TextView) convertView.findViewById(R.id.version_label);
        CheckBox version_checkBox = (CheckBox) convertView.findViewById(R.id.version_checkBox);
        version_text.setText(appName);
        return convertView;
    }
}
