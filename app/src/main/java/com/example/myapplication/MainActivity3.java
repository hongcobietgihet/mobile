package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

import H.File;
import H.User;

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
    List<User> users;
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
                    "email",
                    currUser.getEmail()
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
                    "email",
                    currUser.getEmail()
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

        String email = getIntent().getStringExtra("email");
        users = File.readUsers(this);
        for(User u : users){
            if(u.getEmail().equals(email)){
                currUser = u;
                break;
            }
        }

        if(currUser != null){
            tvName.setText(currUser.getName());
            edtName.setText(currUser.getName());
            edtEmail.setText(currUser.getEmail());
            edtPhone.setText(currUser.getPhone());
            edtAddress.setText(currUser.getAddress());
            edtAvatar.setText(currUser.getAvatar());
            edtDesc.setText(currUser.getDescription());

            Glide
                    .with(this)
                    .load(currUser.getAvatar())
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .error(R.drawable.ic_launcher_foreground)
                    .into(imgAvatar);
        }

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

            String emailNew =
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
                    emailNew.isEmpty()) {

                Toast.makeText(this, "Không được để trống",
                        Toast.LENGTH_SHORT).show();

                return;
            }

            currUser.setName(name);
            currUser.setEmail(emailNew);
            currUser.setPhone(phone);
            currUser.setAddress(address);
            currUser.setAvatar(avatar);
            currUser.setDescription(desc);
            File.saveUsers(this, users);
            tvName.setText(name);

            Glide
                    .with(this)
                    .load(avatar)
                    .into(imgAvatar);

            Toast.makeText(
                    this,
                    "Đã lưu thành công",
                    Toast.LENGTH_SHORT
            ).show();
        });

        btnFriend.setOnClickListener(v -> {
            Intent intent =
                    new Intent(
                            MainActivity3.this,
                            MainActivity5.class);

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