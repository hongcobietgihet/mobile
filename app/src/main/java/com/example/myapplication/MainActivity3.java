package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;

import model.LoginResponse;
import model.User;
import model.UpdateProfileRequest;
import model.UpdateProfileResponse;
import model.ApiService;
import model.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity3 extends AppCompatActivity {

    TextView tvName;
    ImageView imgAvatar;
    TextInputEditText edtName;
    TextInputEditText edtEmail;
    TextInputEditText edtPhone;
    TextInputEditText edtAddress;
    TextInputEditText edtAvatar;
    TextInputEditText edtDesc;

    Button btnSave;
    Button btnFriend;
    Button btnLogout;
    User currUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main3);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        tvName = findViewById(R.id.tvName);
        imgAvatar = findViewById(R.id.imgAvatar);

        imgAvatar.setOnClickListener(v -> {

            if (currUser == null) {
                Toast.makeText(
                        this,
                        "User chưa được tải",
                        Toast.LENGTH_SHORT
                ).show();
                return;
            }

            Intent intent =
                    new Intent(
                            MainActivity3.this,
                            MainActivity4.class
                    );

            intent.putExtra(
                    "user_id",
                    currUser.getId()
            );

            startActivity(intent);

        });

        tvName.setOnClickListener(v -> {
            if (currUser == null) {
                Toast.makeText(
                        this,
                        "User chưa được tải",
                        Toast.LENGTH_SHORT
                ).show();
                return;
            }

            Intent intent =
                    new Intent(
                            MainActivity3.this,
                            MainActivity4.class
                    );

            intent.putExtra(
                    "user_id",
                    currUser.getId()
            );

            startActivity(intent);

        });

        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPhone = findViewById(R.id.edtPhone);
        edtAddress = findViewById(R.id.edtAddress);
        edtAvatar = findViewById(R.id.edtAvatar);
        edtDesc = findViewById(R.id.edtDesc);

        btnSave = findViewById(R.id.btnSave);
        btnFriend = findViewById(R.id.btnFriend);
        btnLogout = findViewById(R.id.btnLogout);

        int user_id =
                getIntent().getIntExtra("user_id", -1);

        ApiService service = RetrofitClient
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

                            tvName.setText(
                                    currUser.getName()
                            );

                            edtName.setText(
                                    currUser.getName()
                            );

                            edtEmail.setText(
                                    currUser.getEmail()
                            );

                            edtPhone.setText(
                                    currUser.getPhone()
                            );

                            edtAddress.setText(
                                    currUser.getAddress()
                            );

                            edtAvatar.setText(
                                    currUser.getAvatar()
                            );

                            edtDesc.setText(
                                    currUser.getDescription()
                            );

                            Glide
                                    .with(MainActivity3.this)
                                    .load(currUser.getAvatar())
                                    .placeholder(R.drawable.ic_launcher_foreground)
                                    .error(R.drawable.ic_launcher_foreground)
                                    .into(imgAvatar);
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<LoginResponse> call,
                            Throwable t
                    ) {
                        Toast.makeText(MainActivity3.this,
                                t.getMessage(),
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                });


        edtAvatar.addTextChangedListener(
                new android.text.TextWatcher() {

                    @Override
                    public void beforeTextChanged(
                            CharSequence s,
                            int start,
                            int count,
                            int after
                    ) {}

                    @Override
                    public void onTextChanged(
                            CharSequence s,
                            int start,
                            int before,
                            int count
                    ) {

                        String url = s.toString();

                        if (!url.isEmpty()) {

                            Glide
                                    .with(MainActivity3.this)
                                    .load(url)
                                    .into(imgAvatar);

                        }

                    }

                    @Override
                    public void afterTextChanged(
                            android.text.Editable s
                    ) {}

                }
        );

        btnSave.setOnClickListener(v -> {

                    String name =
                            edtName.getText().toString();

                    String email =
                            edtEmail.getText().toString();

                    String phone =
                            edtPhone.getText().toString();

                    String address =
                            edtAddress.getText().toString();

                    String avatar =
                            edtAvatar.getText().toString();

                    String desc =
                            edtDesc.getText().toString();

                    if (name.isEmpty() ||
                            email.isEmpty()) {

                        Toast.makeText(this, "Không được để trống",
                                Toast.LENGTH_SHORT).show();

                        return;
                    }

            UpdateProfileRequest request =
                    new UpdateProfileRequest(
                            name,
                            address,
                            avatar,
                            desc,
                            phone
                    );

            service.updateProfile(user_id, request)
                    .enqueue(
                            new Callback<UpdateProfileResponse>() {

                                @Override
                                public void onResponse(
                                        Call<UpdateProfileResponse> call,
                                        Response<UpdateProfileResponse> response
                                ) {

                                    if(response.isSuccessful()
                                            && response.body() != null){

                                        currUser =
                                                response
                                                        .body()
                                                        .getUser();

                                        tvName.setText(
                                                currUser.getName()
                                        );

                                        Glide
                                                .with(MainActivity3.this)
                                                .load(
                                                        currUser.getAvatar()
                                                )
                                                .into(imgAvatar);

                                        Toast.makeText(
                                                MainActivity3.this,
                                                "Đã lưu thành công",
                                                Toast.LENGTH_SHORT
                                        ).show();

                                    } else {

                                        Toast.makeText(
                                                MainActivity3.this,
                                                "Cập nhật không thành công",
                                                Toast.LENGTH_SHORT
                                        ).show();
                                    }
                                }

                                @Override
                                public void onFailure(
                                        Call<UpdateProfileResponse> call,
                                        Throwable t
                                ) {

                                    Toast.makeText(
                                            MainActivity3.this,
                                            t.getMessage(),
                                            Toast.LENGTH_SHORT
                                    ).show();
                                }
                            }
                    );


        });


        btnFriend.setOnClickListener(v -> {
            Intent intent =
                    new Intent(
                            MainActivity3.this,
                            MainActivity5.class);

            intent.putExtra("user_id", currUser.getId());

            startActivity(intent);
        });

        btnLogout.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            MainActivity3.this,
                            MainActivity2.class
                    );

            startActivity(intent);

            finish();
        });

    }
}