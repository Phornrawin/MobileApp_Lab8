package com.example.phornrawin.mobileapp_lab8;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Phornrawin on 27/10/2560.
 */

public class PersonAdapter extends ArrayAdapter<Person> {
    public PersonAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Person> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_row, null);

            Person p = getItem(position);
            TextView textViewName = (TextView) convertView.findViewById(R.id.tv_name);
            textViewName.setText(p.getFirstname());
            TextView textViewLastName = (TextView) convertView.findViewById(R.id.tv_lastname);
            textViewLastName.setText(p.getLastname());
            TextView textViewNickname = (TextView) convertView.findViewById(R.id.tv_nickname);
            textViewNickname.setText(p.getNickname());

        }
        return convertView;
    }

}
