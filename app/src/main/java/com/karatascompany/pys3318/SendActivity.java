package com.karatascompany.pys3318;

import android.annotation.SuppressLint;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SendActivity extends AppCompatActivity {

    TextView textViewSendFriendUserMail;
    Button btnSend; EditText editTextSend;
    String friendUserId,friendUserMail;

    private FirebaseFirestore mFirestore;
    private String mCurrentId;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);

        mFirestore = FirebaseFirestore.getInstance();
        mCurrentId = FirebaseAuth.getInstance().getUid();

        textViewSendFriendUserMail = findViewById(R.id.textViewSendFriendUserMail);
        btnSend = findViewById(R.id.btnSend);
        editTextSend = findViewById(R.id.editTextSend);

         friendUserId = getIntent().getStringExtra("sendUserId");
        friendUserMail = getIntent().getStringExtra("sendUserMail");
        textViewSendFriendUserMail.setText(friendUserMail);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String send = editTextSend.getText().toString();
                if(!send.isEmpty()){

                    Map<String, Object> notificaitonMessage = new HashMap<>();
                    notificaitonMessage.put("message", send);
                    notificaitonMessage.put("from",mCurrentId);

                    mFirestore.collection("Users/"+friendUserId+ "/Notifications").add(notificaitonMessage).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(SendActivity.this, "Bildirim gönderildi. ", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

    }
}
