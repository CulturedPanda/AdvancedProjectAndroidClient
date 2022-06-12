package com.example.advancedprojectandroidclient.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.advancedprojectandroidclient.R;

/**
 * This adapter is used to display the list of options.
 * A very simple adapter, it was only needed because I wanted to slightly stylize the options.
 */
public class SettingsListAdapter extends ArrayAdapter<String> {

    LayoutInflater inflater;

    public SettingsListAdapter(Context ctx, String[] userArrayList) {
        super(ctx, R.layout.settings_item, userArrayList);
        this.inflater = LayoutInflater.from(ctx);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.settings_item, parent, false);
        }

        String current = getItem(position);
        if (current != null) {
            TextView tv = convertView.findViewById(R.id.settings_item_tv);
            tv.setText(current);
        }

        return convertView;
    }
}
