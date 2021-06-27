package com.campus.vcamp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateStaff extends AppCompatActivity {

    EditText staffEmail, staffFullName, staffPhoneNumber, staffPasswd, staffKey;
    Button CreateAccount;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staff_create);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        CreateAccount = findViewById(R.id.createStaff);
        staffEmail = findViewById(R.id.staffEmail);
        staffFullName = findViewById(R.id.staffFullName);
        staffPhoneNumber = findViewById(R.id.staffPhoneNumber);
        staffPasswd = findViewById(R.id.staffPasswd);
        staffKey = findViewById(R.id.staffKey);

        CreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email, FullName, PhoneNumber, Passwd, Key;
                Email = staffEmail.getText().toString();
                FullName = staffFullName.getText().toString();
                PhoneNumber = staffPhoneNumber.getText().toString();
                Passwd = staffPasswd.getText().toString();
                Key = staffKey.getText().toString();

                final String pass = "^(?=.*[0-9])"
                        + "(?=.*[a-z])(?=.*[A-Z])"
                        + "(?=.*[@#$%^&+=])"
                        + "(?=\\S+$).{8,20}$";
                Pattern p = Pattern.compile(pass);
                Matcher m = p.matcher(Passwd);
                final String KeyValue = "12345";

                if(Email.isEmpty()){
                    staffEmail.setError("Please fill this field");
                    staffEmail.requestFocus();
                }else if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
                    staffEmail.setError("Please enter a valid email address");
                    staffEmail.requestFocus();
                }
                else if(FullName.isEmpty()){
                    staffFullName.setError("Please fill this field");
                    staffFullName.requestFocus();
                }else if(PhoneNumber.isEmpty()){
                    staffPhoneNumber.setError("Please fill this field");
                    staffPhoneNumber.requestFocus();
                }else if(!PhoneNumber.matches("[0-9]{10}")){
                    staffPhoneNumber.setError("Please enter a valid mobile number");
                    staffPhoneNumber.requestFocus();
                }
                else if(Passwd.isEmpty()){
                    staffPasswd.setError("Please fill this field");
                    staffPasswd.requestFocus();
                }else if(!m.matches()){
                    staffPasswd.setError("Please use atleast on Uppercase, Lowercase and a digit");
                    staffPasswd.requestFocus();
                }
                else if(Key.isEmpty()){
                    staffKey.setError("Please fill this field");
                    staffKey.requestFocus();
                }else if(!Key.matches(KeyValue)){
                    staffKey.setError("You are not authorised to create staff account");
                    staffKey.requestFocus();
                }
                else{
                    fAuth.createUserWithEmailAndPassword(Email, Passwd).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            FirebaseUser user = fAuth.getCurrentUser();
                            Toast.makeText(CreateStaff.this, "Account Created Successfully", Toast.LENGTH_SHORT).show();
                            assert user != null;
                            DocumentReference df = fStore.collection("Users").document(user.getUid());
                            Map<String, Object> userInfo = new HashMap<>();
                            userInfo.put("FullName", FullName);
                            userInfo.put("Email", Email);
                            userInfo.put("Phone", PhoneNumber);
                            userInfo.put("Password", Passwd);
                            userInfo.put("TypeOfTeacher", "");
                            userInfo.put("isTeacher", "1");

                            df.set(userInfo);
                            startActivity(new Intent(CreateStaff.this, LoginActivity.class));
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(CreateStaff.this, "Failed to create your account", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });

    }
}