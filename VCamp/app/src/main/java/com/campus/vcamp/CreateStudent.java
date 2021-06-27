package com.campus.vcamp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateStudent extends AppCompatActivity {

    EditText studentEmail, studentFullName, studentPhoneNumber, studentPasswd, admissionID, revoveryText;
    Button createStudent;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    DatabaseReference databaseReference;
    CollectionReference collectionReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_student);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        studentEmail = findViewById(R.id.studentEmail);
        studentFullName = findViewById(R.id.studentFullName);
        studentPhoneNumber = findViewById(R.id.studentPhoneNumber);
        studentPasswd = findViewById(R.id.studentPasswd);
        admissionID = findViewById(R.id.admissionID);
        revoveryText = findViewById(R.id.recoveryKeyword);
        createStudent = findViewById(R.id.createStudent);

        createStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email, FullName, PhoneNumber, Passwd, Admission, Recovery;
                Email = studentEmail.getText().toString();
                FullName = studentFullName.getText().toString();
                PhoneNumber = studentPhoneNumber.getText().toString();
                Passwd = studentPasswd.getText().toString();
                Admission = admissionID.getText().toString();
                Recovery = revoveryText.getText().toString();

                final String pass = "^(?=.*[0-9])"
                        + "(?=.*[a-z])(?=.*[A-Z])"
                        + "(?=.*[@#$%^&+=])"
                        + "(?=\\S+$).{8,20}$";
                Pattern p = Pattern.compile(pass);
                Matcher m = p.matcher(Passwd);

                if(Email.isEmpty()){
                    studentEmail.setError("Please fill this field");
                    studentEmail.requestFocus();
                }else if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
                    studentEmail.setError("Please enter a valid email address");
                    studentEmail.requestFocus();
                }
                else if(FullName.isEmpty()){
                    studentFullName.setError("Please fill this field");
                    studentFullName.requestFocus();
                }else if(PhoneNumber.isEmpty()){
                    studentPhoneNumber.setError("Please fill this field");
                    studentPhoneNumber.requestFocus();
                }else if(!PhoneNumber.matches("[0-9]{10}")){
                    studentPhoneNumber.setError("Please enter a valid mobile number");
                    studentPhoneNumber.requestFocus();
                }
                else if(Passwd.isEmpty()){
                    studentPasswd.setError("Please fill this field");
                    studentPasswd.requestFocus();
                }else if(!m.matches()){
                    studentPasswd.setError("Please use atleast one Uppercase, Lowercase, digit and a special character");
                    studentPasswd.requestFocus();
                }
                else if(Admission.isEmpty()){
                    admissionID.setError("Please fill this field");
                    admissionID.requestFocus();
                }else if(!Admission.matches("[0-9]{4}")){
                    admissionID.setError("please enter a valid admission ID");
                    admissionID.requestFocus();
                }else if(Recovery.isEmpty()){
                    revoveryText.setError("Please fill this field");
                    revoveryText.requestFocus();
                }
                else{

                    Query admissionIDExist = fStore.collection("Users").whereEqualTo("Admission", Admission);
                    admissionIDExist.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            if(!queryDocumentSnapshots.isEmpty()){
                                admissionID.setError("This admission ID already exists");
                                admissionID.requestFocus();
                            }else{
                                fAuth.createUserWithEmailAndPassword(Email, Passwd).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(AuthResult authResult) {
                                        FirebaseUser user = fAuth.getCurrentUser();
                                        Toast.makeText(CreateStudent.this, "Account Created Successfully", Toast.LENGTH_SHORT).show();
                                        assert user != null;
                                        DocumentReference df = fStore.collection("Users").document(user.getUid());
                                        Map<String, Object> userInfo = new HashMap<>();
                                        userInfo.put("FullName", FullName);
                                        userInfo.put("Email", Email);
                                        userInfo.put("Phone", PhoneNumber);
                                        userInfo.put("Password", Passwd);
                                        userInfo.put("Admission", Admission);
                                        userInfo.put("RecoveryKey", Recovery);

                                        userInfo.put("isStudent", "1");

                                        df.set(userInfo);
                                        startActivity(new Intent(CreateStudent.this, LoginActivity.class));
                                        finish();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(CreateStudent.this, "Failed to create your account", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    });

                    /**/


                    /*fAuth.createUserWithEmailAndPassword(Email, Passwd).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            FirebaseUser user = fAuth.getCurrentUser();
                            Toast.makeText(CreateStudent.this, "Account Created Successfully", Toast.LENGTH_SHORT).show();
                            assert user != null;
                            DocumentReference df = fStore.collection("Users").document(user.getUid());
                            Map<String, Object> userInfo = new HashMap<>();
                            userInfo.put("FullName", FullName);
                            userInfo.put("Email", Email);
                            userInfo.put("Phone", PhoneNumber);
                            userInfo.put("Password", Passwd);
                            userInfo.put("Admission", Admission);
                            userInfo.put("RecoveryKey", Recovery);

                            userInfo.put("isStudent", "1");

                            df.set(userInfo);
                            startActivity(new Intent(CreateStudent.this, LoginActivity.class));
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(CreateStudent.this, "Failed to create your account", Toast.LENGTH_SHORT).show();
                        }
                    });*/
                }

            }
        });

    }
}