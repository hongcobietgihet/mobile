package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class postAdapter
        extends ArrayAdapter<post> {

    public postAdapter(
            Context context,
            List<post> posts
    ) {
        super(context, 0, posts);
    }

    @Override
    public View getView(
            int position,
            View convertView,
            ViewGroup parent
    ) {

        if (convertView == null) {

            convertView = LayoutInflater.from(getContext()).inflate(
                                    R.layout.post_item,
                                    parent,
                                    false
                            );
        }

        post post = getItem(position);

        ImageView imgAvatar = convertView.findViewById(R.id.imgAvatar);

        TextView txtName = convertView.findViewById(R.id.txtName);

        TextView txtTime = convertView.findViewById(R.id.txtTime);

        TextView txtContent =convertView.findViewById(R.id.txtContent);

        txtName.setText(post.getName());

        txtTime.setText(post.getTime());

        txtContent.setText(post.getContent());

        Glide
                .with(getContext())
                .load(post.getAvatar())
                .into(imgAvatar);

        return convertView;
    }
}
