package com.campus.vcamp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

public class LoginActivity extends AppCompatActivity {

    EditText loginName, loginPwd;
    Button CreateStudent, CreateStaff, login;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    TextView ForgotPassword;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginName = findViewById(R.id.loginUsername);
        ForgotPassword = findViewById(R.id.forgotPasswordText);
        loginPwd = findViewById(R.id.loginPassword);
        login = findViewById(R.id.loginButton);
        CreateStaff = findViewById(R.id.createStaff);
        CreateStudent = findViewById(R.id.createStudent);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        dialog = new Dialog(LoginActivity.this);

        ForgotPassword.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View v) {

                dialog.setContentView(R.layout.alert_dialog_for_reset_password);
                dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.reset_password_background));
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().getAttributes().windowAnimations = R.style.animationForResetPassword;
                Button cancel, sentLink;
                ImageView passwordResetIcon;
                EditText passwordResetEmail;
                passwordResetIcon = dialog.findViewById(R.id.passwordResetIcon);
                passwordResetEmail = dialog.findViewById(R.id.passwordResetEmail);
                cancel = dialog.findViewById(R.id.cancelResetPassword);
                sentLink = dialog.findViewById(R.id.sentResetPassword);

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

                sentLink.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String eMail = passwordResetEmail.getText().toString();
                        fAuth.sendPasswordResetEmail(eMail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(getApplicationContext(), "The link to reset your password has been sent to your mail", Toast.LENGTH_SHORT).show();
                                passwordResetIcon.setImageResource(R.drawable.ic_tick);
                                passwordResetEmail.setText("");
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull @NotNull Exception e) {
                                Toast.makeText(getApplicationContext(), "Failed to send reset link "+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

                dialog.show();

            }
        });

        CreateStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, CreateStaff.class));
            }
        });
        CreateStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CreateStudent.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uName, passwd;
                uName = loginName.getText().toString();
                passwd = loginPwd.getText().toString();

                if(uName.isEmpty()){
                    loginName.setError("Please fill this field");
                    loginName.requestFocus();
                }else if(passwd.isEmpty()){
                    loginPwd.setError("Please fill this field");
                    loginPwd.requestFocus();
                }else if(!uName.isEmpty() && !passwd.isEmpty()){
                    fAuth.signInWithEmailAndPassword(uName, passwd).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Toast.makeText(LoginActivity.this, "Welcome to vCamp", Toast.LENGTH_SHORT).show();
                            checkAccessLevel(authResult.getUser().getUid());
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(LoginActivity.this, "Failed to log in", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

    }

    private void checkAccessLevel(String uid) {
        DocumentReference df = fStore.collection("Users").document(uid);
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.getString("isTeacher") != null){
                    startActivity(new Intent(getApplicationContext(), TeacherHomeActivity.class));
                    finish();
                }
                if(documentSnapshot.getString("isStudent") != null){
                    startActivity(new Intent(getApplicationContext(), StudentHomeActivity.class));
                    finish();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
            if(FirebaseAuth.getInstance().getCurrentUser() != null){

                DocumentReference df = fStore.collection("Users").document(fAuth.getUid());
                df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.getString("isTeacher") != null){
                            startActivity(new Intent(getApplicationContext(), TeacherHomeActivity.class));
                            finish();
                        }
                        if(documentSnapshot.getString("isStudent") != null){
                            startActivity(new Intent(getApplicationContext(), StudentHomeActivity.class));
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        fAuth.signOut();
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        finish();
                    }
                });
            }

    }

}