package com.example.myapplication;
import model.RegisterRequest;
import model.RegisterResponse;
import model.ApiService;
import model.RetrofitClient;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.util.List;


import model.User;


public class MainActivity extends AppCompatActivity {
    TextInputEditText edtName;
    TextInputEditText edtEmail;
    TextInputEditText edtPhone;
    TextInputEditText edtPw;
    TextInputEditText edtCPw;
    Button btnCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPhone = findViewById(R.id.edtPhone);
        edtPw = findViewById(R.id.edtPw);
        edtCPw = findViewById(R.id.edtCPw);

        btnCreate = findViewById(R.id.btnCreate);
        btnCreate.setOnClickListener(v -> {
            String name = edtName.getText().toString().trim();
            String email = edtEmail.getText().toString().trim();
            String phone = edtPhone.getText().toString().trim();
            String pw = edtPw.getText().toString().trim();
            String cpw = edtCPw.getText().toString().trim();
            phone = phone.replaceAll("[\\s\\-\\(\\)]", "");

            if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || pw.isEmpty() || cpw.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            if (!pw.equals(cpw)) {
                Toast.makeText(this, "Mật khẩu không khớp",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            ApiService service = RetrofitClient
                    .getRetrofit()
                    .create(ApiService.class);

            RegisterRequest request = new RegisterRequest(name, email, phone, pw);
            service.register(request).enqueue(new Callback<RegisterResponse>() {

                        @Override
                        public void onResponse(Call<RegisterResponse> call,
                                Response<RegisterResponse> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "Đăng ký thành công",
                                        Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(
                                                MainActivity.this,
                                                MainActivity2.class
                                        );
                                startActivity(intent);

                                finish();
                            } else {
                                Toast.makeText(MainActivity.this, "Đăng ký không thành công",
                                        Toast.LENGTH_SHORT
                                ).show();
                            }
                        }

                        @Override
                        public void onFailure(
                                Call<RegisterResponse> call,
                                Throwable t) {

                            Toast.makeText(
                                    MainActivity.this,
                                    t.getMessage(),
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    });
        });
    }
}