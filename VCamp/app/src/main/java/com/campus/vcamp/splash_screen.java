package com.campus.vcamp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class splash_screen extends AppCompatActivity {

    Handler handler;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
            handler=new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

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
                    }else{
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        finish();
                    }

                }
            },2000);
    }

}