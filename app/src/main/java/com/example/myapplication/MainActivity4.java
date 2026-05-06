package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import H.File;
import H.User;

public class MainActivity4
        extends AppCompatActivity {

    EditText edtContent;
    Button btnPost;
    ListView listView;

    static ArrayList<post> postList;
    postAdapter adapter;

    User currUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        edtContent = findViewById(R.id.edtContent);
        btnPost = findViewById(R.id.btnPost);
        listView = findViewById(R.id.listViewPosts);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        String email = getIntent().getStringExtra("email");

        List<User> users = File.readUsers(this);

        for (User u : users) {

            if (u.getEmail()
                    .equals(email)) {

                currUser = u;

                break;
            }
        }

        if (postList == null) {
            postList = new ArrayList<>();
        }
        adapter = new postAdapter(this, postList);
        listView.setAdapter(adapter);

        btnPost.setOnClickListener(v -> {

            String content = edtContent.getText().toString().trim();

            if (content.isEmpty()) {
                Toast.makeText(this, "Nhập nội dung", Toast.LENGTH_SHORT).show();
                return;
            }

            if (currUser == null) {
                Toast.makeText(this, "Không tìm thấy user", Toast.LENGTH_SHORT).show();
                return;
            }

            String time = getCurrentTime();

            postList.add(
                    0,
                    new post(currUser.getName(), currUser.getAvatar(), content, time, false)
            );

            adapter.notifyDataSetChanged();
            edtContent.setText("");

        });

       // item menu
        listView.setOnItemClickListener((parent, view, position, id) -> {

            PopupMenu popup = new PopupMenu(MainActivity4.this, view, Gravity.END);
            popup.getMenuInflater().inflate(R.menu.item_menu, popup.getMenu());

            popup.setOnMenuItemClickListener(item -> {

                if (item.getItemId() == R.id.itemmenu1) {
                    post p = postList.get(position);
                    Toast.makeText(MainActivity4.this, p.getContent(), Toast.LENGTH_LONG).show();
                }

                else if (item.getItemId() == R.id.itemmenu2) {
                    postList.remove(position);
                    adapter.notifyDataSetChanged();
                }

                return true;
            });

            popup.show();
        });
    }

    private String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return sdf.format(new Date());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.post_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        int id = item.getItemId();
        if(id == R.id.option1){
            Intent intent = new Intent(this, MainActivity3.class);
            intent.putExtra("email", currUser.getEmail());
            startActivity(intent);
            return true;
        }
        else if(id == R.id.option2){
            Collections.sort(postList, new Comparator<post>() {
                @Override
                public int compare(post o1, post o2) {
                    return o2.getTime().compareTo(o1.getTime());
                }
            });
            adapter.notifyDataSetChanged();
            listView.invalidateViews();
            return true;
        }
        else if (id == R.id.option3) {
            Collections.sort(postList, new Comparator<post>() {
                @Override
                public int compare(post o1, post o2) {
                    return o2.getName().compareTo(o1.getName());
                }
            });
            adapter.notifyDataSetChanged();
            listView.invalidateViews();
        }
        return super.onOptionsItemSelected(item);
    }

}