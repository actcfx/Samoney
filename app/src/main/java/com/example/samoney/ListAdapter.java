package com.example.samoney;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ListAdapter extends BaseAdapter {

    private List<Map<String, Object>> item_list;
    private LayoutInflater inflater;

    public ListAdapter(Context context, List<Map<String, Object>> item_list) {
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.item_list = item_list;
    }

    @Override
    public int getCount() {
        return item_list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convert_view, ViewGroup parent) {
        View view = inflater.inflate(R.layout.listlayout, parent, false);

        TextView date_txt = view.findViewById(R.id.date_txt);
        TextView time_txt = view.findViewById(R.id.time_txt);
        ImageView type_icon = view.findViewById(R.id.type_icon);
        TextView name_txt = view.findViewById(R.id.name_txt);
        TextView store_txt = view.findViewById(R.id.store_txt);
        TextView cost_txt = view.findViewById(R.id.cost_txt);

        date_txt.setText(item_list.get(position).get("date") + "");
        time_txt.setText(item_list.get(position).get("time") + "");
        type_icon.setImageResource(Integer.parseInt(item_list.get(position).get("type_icon").toString()));
        name_txt.setText(item_list.get(position).get("name") + "");
        store_txt.setText(item_list.get(position).get("store") + "");
        cost_txt.setText("$" + item_list.get(position).get("cost"));

        return view;
    }
}
