package com.example.myapplication;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import H.User;

public class requestAdapter  extends ArrayAdapter<User> {
    Activity context;
    ArrayList<User> list;
    ArrayList<User> friendList;

    public requestAdapter(Activity context, ArrayList<User> list, ArrayList<User> friendList) {
        super(context, R.layout.request_item, list);
        this.context = context;
        this.list = list;
        this.friendList = friendList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View row = inflater.inflate(R.layout.request_item, null);

        ImageView imgAvatar = row.findViewById(R.id.imgAvatar);
        TextView txtName = row.findViewById(R.id.txtName);
        Button btnAcp = row.findViewById(R.id.btnAccept);
        Button btnDcl = row.findViewById(R.id.btnDecline);

        User u = list.get(position);
        txtName.setText(u.getName());

        btnAcp.setOnClickListener(v ->{
            u.setIsFriend(true);

            friendList.add(u);
            list.remove(position);
            notifyDataSetChanged();
        });

        btnDcl.setOnClickListener(v ->{
            list.remove(position);
            notifyDataSetChanged();
        });

        return row;
    }
}
