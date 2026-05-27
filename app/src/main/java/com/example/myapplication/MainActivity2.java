package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.google.android.material.textfield.TextInputEditText;
import java.util.List;

import model.LoginResponse;
import model.LoginRequest;
import model.User;
import model.RegisterRequest;
import model.RegisterResponse;
import model.ApiService;
import model.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity2 extends AppCompatActivity {

    TextInputEditText edtEmail;
    TextInputEditText edtPw;
    Button btnLogin;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        edtEmail = findViewById(R.id.edtEmail);
        edtPw = findViewById(R.id.edtPw);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        ActivityResultLauncher<Intent> resgisterLauncher =
                registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                        result -> {if(result.getResultCode() == RESULT_OK){
                            Toast.makeText(this, "Đăng ký thành công",
                                    Toast.LENGTH_SHORT).show();
                        }});

        btnLogin.setOnClickListener(v -> {
            String email = edtEmail.getText().toString().trim();
            String pw = edtPw.getText().toString().trim();
            if(email.isEmpty() || pw.isEmpty()){
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin",
                        Toast.LENGTH_SHORT).show();
                return;
            }


            ApiService service = RetrofitClient
                            .getRetrofit()
                            .create(ApiService.class);

            LoginRequest request =
                    new LoginRequest(email, pw);

            service.login(request).enqueue(new Callback<LoginResponse>() {

                        @Override
                        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                            if(response.isSuccessful() && response.body() != null){
                                LoginResponse loginResponse =
                                        response.body();

                                Intent intent = new Intent(
                                                MainActivity2.this,
                                                MainActivity3.class);

                                if (loginResponse.getUser() != null) {
                                    intent.putExtra("user_id", loginResponse.getUser().getId());
                                } else {
                                    intent.putExtra("user_id", -1);
                                }

                                startActivity(intent);

                            } else {
                                Toast.makeText(MainActivity2.this, "Sai email hoặc password",
                                        Toast.LENGTH_SHORT
                                ).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<LoginResponse> call, Throwable t) {
                            Toast.makeText(MainActivity2.this, t.getMessage(),
                                    Toast.LENGTH_SHORT
                            ).show();

                        }
                    });


        });

        btnRegister.setOnClickListener(v ->{
            Intent intent = new Intent(MainActivity2.this, MainActivity.class);

            resgisterLauncher.launch(intent);
        });

    }
}