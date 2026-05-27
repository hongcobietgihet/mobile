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

import model.User;

public class suggestAdapter extends ArrayAdapter<User> {
    Activity context;
    ArrayList<User> list;
    ArrayList<User> requestList;

    public suggestAdapter(Activity context, ArrayList<User> list, ArrayList<User> requestList){
        super(context, R.layout.friend_item, list);
        this.context = context;
        this.list = list;
        this.requestList = requestList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View row = inflater.inflate(R.layout.friend_item, null);

        ImageView imgAvatar = row.findViewById(R.id.imgAvatar);
        TextView txtName = row.findViewById(R.id.txtName);
        Button btnAdd = row.findViewById(R.id.btnAddFriend);

        User u = list.get(position);
        txtName.setText(u.getName());

        btnAdd.setOnClickListener(v ->{
            u.setIsFriend(true);

            requestList.add(u);
            list.remove(u);
            notifyDataSetChanged();
        });
        return row;

    }
}
