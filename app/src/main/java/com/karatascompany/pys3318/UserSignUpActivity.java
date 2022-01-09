package com.karatascompany.pys3318;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.protobuf.Empty;
import com.karatascompany.pys3318.poco.User;
import com.karatascompany.pys3318.remote.ApiUtils;
import com.karatascompany.pys3318.remote.UserService;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserSignUpActivity extends AppCompatActivity {

    TextView editTextEmail, editTextUserName, editTextUserSurname, editTextPassword, editTextPasswordAgain;
    Button buttonUserSignUp;
    ProgressBar progress_signup;

    private UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_sign_up);

        Objects.requireNonNull(getSupportActionBar()).hide();

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextUserName = findViewById(R.id.editTextUserName);
        editTextUserSurname = findViewById(R.id.editTextUserSurname);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextPasswordAgain = findViewById(R.id.editTextPasswordAgain);
        progress_signup = findViewById(R.id.progress_signup);

        buttonUserSignUp = findViewById(R.id.buttonUserSignUp);

        userService = ApiUtils.getUserService();

        buttonUserSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString();
                String userName = editTextUserName.getText().toString();
                String userSurname = editTextUserSurname.getText().toString();
                String pass = editTextPassword.getText().toString();
                String againPass = editTextPasswordAgain.getText().toString();

                int statu = CheckEmptyControl(email, userName, userSurname, pass, againPass);

                if (statu == 1) {
                    User user = new User(email, userName, userSurname, pass);
                    Call<String> call = userService.InsertUserInf(user);

                    progress_signup.setVisibility(View.VISIBLE);

                    call.enqueue(new Callback<String>() {

                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            try {
                                if (response.body().equals("1")) {
                                    Toast.makeText(UserSignUpActivity.this, "Kayıt Başarılı...", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(UserSignUpActivity.this, LoginActivity.class));
                                } else if (response.body().equals("-1"))
                                    Toast.makeText(UserSignUpActivity.this, "Başka Bir Mail Adresi Deneyiniz!", Toast.LENGTH_SHORT).show();
                                else
                                    Toast.makeText(UserSignUpActivity.this, "Hata Oluştu!", Toast.LENGTH_SHORT).show();

                                progress_signup.setVisibility(View.GONE);

                            } catch (Exception e) {
                                progress_signup.setVisibility(View.GONE);
                                Toast.makeText(UserSignUpActivity.this, "Hata Oluştu!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            progress_signup.setVisibility(View.GONE);
                            Toast.makeText(UserSignUpActivity.this, "Hata Oluştu!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });


    }

    public int CheckEmptyControl(String email, String userName, String userSurname, String pass, String againPass) {

        String Statu = "";
        if (email.isEmpty()) Statu += " Email Alanı Zorunludur...";
        if (userName.isEmpty()) Statu += " Kullanıcı Adı Alanı Zorunludur...";
        if (userSurname.isEmpty()) Statu += " Kullanıcı Soyadı Alanı Zorunludur...";
        if (pass.isEmpty()) Statu += " Şifre Alanı Zorunludur...";
        if (againPass.isEmpty()) Statu += " Şifre Tekrar Alanı Zorunludur...";

        if (!Statu.isEmpty()) {
            Toast.makeText(this, Statu, Toast.LENGTH_SHORT).show();
            return 0;
        } else {
            int isTruePass = CheckPasswords(pass, againPass);
            if (isTruePass == 1) {
                return 1;
            } else {
                return 0;
            }
        }

    }

    public int CheckPasswords(String pass, String againPass) {

        if (pass.equals(againPass)) {
            if (pass.length() < 8) {
                Toast.makeText(this, "Şifre En Az 8 Hane Olmalı!", Toast.LENGTH_SHORT).show();
                return 0;
            } else return 1;
        } else {
            Toast.makeText(this, "Şifreler Eşleşmiyor!", Toast.LENGTH_SHORT).show();
            return 0;
        }

    }

}
