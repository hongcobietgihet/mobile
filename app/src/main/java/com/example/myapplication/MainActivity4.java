package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
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

import model.LoginResponse;
import model.User;
import model.CreatePostRequest;
import model.CreatePostResponse;
import model.DeletePostResponse;
import model.GetPostsResponse;
import model.User;

import model.ApiService;
import model.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity4
        extends AppCompatActivity {

    EditText edtContent;
    Button btnPost;
    ListView listView;

    ArrayList<post> postList;
    postAdapter adapter;
    ApiService service;

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


        int user_id = getIntent().getIntExtra("user_id", -1);

        service =
                RetrofitClient
                        .getRetrofit()
                        .create(ApiService.class);
        service.getProfile(user_id)
                .enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(
                            Call<LoginResponse> call,
                            Response<LoginResponse> response
                    ) {

                        if(response.isSuccessful()
                                && response.body() != null){

                            currUser = response.body().getUser();
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<LoginResponse> call,
                            Throwable t
                    ) {

                        Toast.makeText(
                                MainActivity4.this,
                                t.getMessage(),
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                });


        postList = new ArrayList<>();

        adapter = new postAdapter(this, postList);
        service =
                RetrofitClient
                        .getRetrofit()
                        .create(ApiService.class);
        service.getAllPosts()
                .enqueue(new Callback<GetPostsResponse>() {
                    @Override
                    public void onResponse(
                            Call<GetPostsResponse> call,
                            Response<GetPostsResponse> response
                    ) {

                        if(response.isSuccessful()
                                && response.body() != null){

                            postList.clear();

                            postList.addAll(
                                    response
                                            .body()
                                            .getData()
                            );
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<GetPostsResponse> call,
                            Throwable t
                    ) {

                        Toast.makeText(
                                MainActivity4.this,
                                t.getMessage(),
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                });
        listView.setAdapter(adapter);

        btnPost.setOnClickListener(v -> {

            String content = edtContent.getText().toString().trim();

            if (content.isEmpty()) {
                Toast.makeText(this, "Nhập nội dung", Toast.LENGTH_SHORT).show();
                return;
            }

            if(user_id == -1){
                Toast.makeText(this, "User ID lỗi", Toast.LENGTH_SHORT).show();
                return;
            }

            CreatePostRequest request =
                    new CreatePostRequest(user_id, content);
            service.createPost(request)
                    .enqueue(
                            new Callback<CreatePostResponse>() {
                                @Override
                                public void onResponse(
                                        Call<CreatePostResponse> call,
                                        Response<CreatePostResponse> response
                                ) {

                                    if(response.isSuccessful()
                                            && response.body() != null){

                                        post newPost =
                                                response
                                                        .body()
                                                        .getData();
                                        postList.add(0, newPost);
                                        adapter.notifyDataSetChanged();

                                        edtContent.setText("");

                                        Toast.makeText(
                                                MainActivity4.this,
                                                "Đăng bài thành công",
                                                Toast.LENGTH_SHORT
                                        ).show();

                                    } else {

                                        Toast.makeText(
                                                MainActivity4.this,
                                                "Đăng bài không thành công",
                                                Toast.LENGTH_SHORT
                                        ).show();
                                    }
                                }

                                @Override
                                public void onFailure(
                                        Call<CreatePostResponse> call,
                                        Throwable t
                                ) {

                                    Toast.makeText(
                                            MainActivity4.this,
                                            t.getMessage(),
                                            Toast.LENGTH_SHORT
                                    ).show();
                                }
                            }
                    );

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
                    final post p = postList.get(position);
                    service.deletePost(p.getId())
                            .enqueue(
                                    new Callback<DeletePostResponse>() {

                                        @Override
                                        public void onResponse(
                                                Call<DeletePostResponse> call,
                                                Response<DeletePostResponse> response
                                        ) {

                                            if(response.isSuccessful()){
                                                postList.remove(p);

                                                adapter.notifyDataSetChanged();

                                                Toast.makeText(
                                                        MainActivity4.this,
                                                        "Đã xoá bài viết",
                                                        Toast.LENGTH_SHORT
                                                ).show();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<DeletePostResponse> call, Throwable t) {
                                            Toast.makeText(
                                                    MainActivity4.this,
                                                    t.getMessage(),
                                                    Toast.LENGTH_SHORT
                                            ).show();
                                        }
                                    }
                            );
                }

                return true;
            });

            popup.show();
        });
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
            intent.putExtra("user_id", currUser.getId());
            startActivity(intent);
            return true;
        }
        else if(id == R.id.option2){
            Collections.sort(postList, new Comparator<post>() {
                @Override
                public int compare(post o1, post o2) {
                    return o2.getCreated_at().compareTo(o1.getCreated_at());
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
                    return o2.getAuthor().getName().compareTo(o1.getAuthor().getName());
                }
            });
            adapter.notifyDataSetChanged();
            listView.invalidateViews();
        }
        return super.onOptionsItemSelected(item);
    }

}