package com.example.news;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.news.Presenters.LogInActivityPresenter;
import com.example.news.Presenters.LogInTestInterface;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class LogInActivity extends AppCompatActivity implements LogInTestInterface {

    private EditText email, password;
    private Button logInBtn  , signUpFromLoginBtn;
    private FirebaseAuth auth;
    private FirebaseFirestore firebaseFirestore;
    private CollectionReference collectionReferenceUsers;
    LogInActivityPresenter logInActivityPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        auth = FirebaseAuth.getInstance();
        logInActivityPresenter = new LogInActivityPresenter(this);
        firebaseFirestore = FirebaseFirestore.getInstance();
        collectionReferenceUsers = firebaseFirestore.collection("Users");

        email = findViewById(R.id.emailLogInEdt);
        password = findViewById(R.id.passwordLogInEdt);
        logInBtn = findViewById(R.id.loginInBtn);
        signUpFromLoginBtn = findViewById(R.id.signUpFromLoginBtn);
        signUpFromLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LogInActivity.this , SignUpAvtivity.class);
                startActivity(intent);
            }
        });


        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                logInActivityPresenter.checkFromData(email.getText().toString(),password.getText().toString() );


            }
        });

    }
    @Override
    public void sendRequestLogin() {
        final ProgressDialog progressDialog;
        progressDialog = ProgressDialog.show(LogInActivity.this, null, "Loading...");
        auth.signInWithEmailAndPassword(email.getText().toString() , password.getText().toString()).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LogInActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        }).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Intent view = new Intent(LogInActivity.this, MainActivity.class);
                startActivity(view);
            }
        });
    }

    @Override
    public void showErrorMessage() {
        Toast.makeText(LogInActivity.this, "Please Enter All Fields", Toast.LENGTH_SHORT).show();
    }
}
