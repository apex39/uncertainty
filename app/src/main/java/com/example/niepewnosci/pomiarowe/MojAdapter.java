package com.example.niepewnosci.pomiarowe;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MojAdapter extends ArrayAdapter<Float>{
	public MojAdapter(Context context, List<Float> data){
		super(context, R.layout.wyglad_listy, data);
	}

    static class ViewHolder{
        int element_position;
		TextView element_text;
		ImageButton bin;
		}

    public View getView(int position, View convertView, ViewGroup parent){
		ViewHolder holder;

		if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.wyglad_listy, parent, false);
            holder = new ViewHolder();
            holder.element_text = (TextView) convertView.findViewById(R.id.element_text);
            holder.bin=(ImageButton) convertView.findViewById(R.id.element_usun);
            holder.bin.setTag(position);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.bin.setTag(position);
        holder.element_text.setText(getItem(position).toString());
		convertView.setTag(holder);
		return convertView;
		}
	}