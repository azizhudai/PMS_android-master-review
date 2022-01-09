package com.karatascompany.pys3318;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.karatascompany.pys3318.models.UserModel;
import com.karatascompany.pys3318.models.UserTokenModel;
import com.karatascompany.pys3318.remote.ApiUtils;
import com.karatascompany.pys3318.remote.UserService;
import com.karatascompany.pys3318.session.Session;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonSignUp;
    private Button kayitBtn;

    private ProgressDialog progressDialog;

    public static String IpUrl = "";
    public static int userId = 0;
    UserService userService;
    private Session session;
    // private FirebaseAuth mAuth;
    //  FirebaseDatabase mfirebaseDatabase;
    //private FirebaseFirestore mFireStore;

    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Objects.requireNonNull(getSupportActionBar()).hide();

        userService = ApiUtils.getUserService();
        FirebaseApp.initializeApp(this);
        //mFireStore = FirebaseFirestore.getInstance();
        //     mfirebaseDatabase = FirebaseDatabase.getInstance();
        progressDialog = new ProgressDialog(this);

        setTitle("Kullanıcı Giriş");

        //String token_id = FirebaseInstanceId.getInstance().getToken(); // FirebaseInstanceId.getInstance().getToken();//.getToken();

        /////////////
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);

        buttonSignUp = findViewById(R.id.buttonSignUp);
        kayitBtn = findViewById(R.id.kayitBtn);

        editTextEmail.setText("azizhudaikaratas@gmail.com");
        editTextPassword.setText("123456");

        session = new Session(this);
        // mAuth = FirebaseAuth.getInstance();

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String email = editTextEmail.getText().toString();
                    String password = editTextPassword.getText().toString();

                    if (validateLogin(email, password)) {

                        doLogin(email, password);
                    }
                } catch (Exception e) {
                    Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });

        kayitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(LoginActivity.this, UserSignUpActivity.class));

            }
        });


    }

    private boolean validateLogin(String email, String password) throws Exception {

        if (email == null || email.trim().length() == 0) {
            Toast.makeText(this, "Email Giriniz!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password == null || password.trim().length() == 0) {
            Toast.makeText(this, "Şifre Giriniz!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;

    }

    private void doLogin(final String email, final String password) {
        progressDialog.setMessage("Giriş yapılıyor Bekleyin...");
        progressDialog.setIcon(R.drawable.team_work);
        progressDialog.show();

        Call<UserModel> call = userService.login(email, password);
        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if (response.isSuccessful()) {

                    final UserModel userModelObj = response.body();
                    try {
                        //if(userModelObj.getUserEmail().equals(email) && userModelObj.getUserPassword().equals(password)){
                        if (userModelObj != null) {

                     /*       mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
*/

                            //String token_id = FirebaseInstanceId.getInstance().getToken();//.getToken();
                            //String current_id = mAuth.getCurrentUser().getUid();

                            Map<String, Object> tokenMap = new HashMap<>();
                            //tokenMap.put("token_id", token_id);
                                               /* DatabaseReference databaseReference = mfirebaseDatabase.getReference();
                                                databaseReference.child("kullanici")
                                                        .child(current_id).updateChildren(tokenMap);
                                                //   .setValue(tokenMap);*/

                            Intent intent = new Intent(LoginActivity.this, MainFragmentActivity.class);
                            intent.putExtra("email", email);
                            userId = userModelObj.getUserId();
                            intent.putExtra("userId", userModelObj.getUserId());
                            intent.putExtra("IpUrl", IpUrl);
                            session.setUserId(String.valueOf(userId));

                            session.setUserMail(email);

                            //  UpdateTokenId(session.getUserId(),token_id);

                            startActivity(intent);
                            //Toast.makeText(LoginActivity.this,"Giriş Başarılı...",Toast.LENGTH_SHORT).show();

                                    /*    mFireStore.collection("Users").document(current_id).update(tokenMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {

                                            }
                                        });
*/
                                        /*    }else{
                                        Toast.makeText(LoginActivity.this, "HATA: "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                                        progressDialog.dismiss();}
                                }
                            });
*/
                        } else {
                            Toast.makeText(LoginActivity.this, "Giriş Red!!", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();

                    } catch (Exception e) {
                        Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }

                } else {
                    Toast.makeText(LoginActivity.this, "Giriş Red!!" + response.errorBody(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Hatalı! Terar Deneyin." + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        });
    }

    private void UpdateTokenId(String userId, String token_id) {

        UserTokenModel userToken = new UserTokenModel(userId, token_id);
        // userToken.setUserId(userId);
        //userToken.setTokenId(token_id);
        Call<String> call = userService.UpdateTokenId(userToken);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    String result = response.body();
                    if (result.equals("OK")) {
                        Toast.makeText(LoginActivity.this, "Başarılı", Toast.LENGTH_SHORT).show();

                    } else
                        Toast.makeText(LoginActivity.this, response.body(), Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}