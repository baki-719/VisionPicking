package com.example.lotte.visionpicking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by DEV on 2018-02-03.
 */

public class ListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<ListItem> listItemArrayList;
    private TextView nameView;
    private TextView locationView;
    private TextView countView;

    public ListAdapter(Context context, ArrayList<ListItem> listItemArrayList) {
        this.context = context;
        this.listItemArrayList = listItemArrayList;
    }

    @Override
    public int getCount() {
        return this.listItemArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.listItemArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item, null);
            nameView = convertView.findViewById(R.id.item_name);
            locationView = convertView.findViewById(R.id.item_location);
            countView = convertView.findViewById(R.id.item_count);
        }

        nameView.setText(listItemArrayList.get(position).getName());
        locationView.setText(listItemArrayList.get(position).getLocation());
        // TODO: 2018-02-03 갯수 출력
        return convertView;
    }
}
