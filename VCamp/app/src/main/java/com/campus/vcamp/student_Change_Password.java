package com.campus.vcamp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class student_Change_Password extends Fragment {
    EditText recoveryKey, newPasswd;
    Button changePass;
    FirebaseAuth fAuth;
    FirebaseUser userID;
    String passwordRecoveryKey = null;
    FirebaseFirestore fStore;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_change_password, container, false);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser();

        recoveryKey = view.findViewById(R.id.RecoveryKey);
        newPasswd = view.findViewById(R.id.newPassword);
        changePass = view.findViewById(R.id.changePass);

        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentReference documentReference = fStore.collection("Users").document(fAuth.getUid());
                documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        passwordRecoveryKey = documentSnapshot.getString("RecoveryKey");

                        String recovery = recoveryKey.getText().toString();
                        String password = newPasswd.getText().toString();

                        final String pass = "^(?=.*[0-9])"
                                + "(?=.*[a-z])(?=.*[A-Z])"
                                + "(?=.*[@#$%^&+=])"
                                + "(?=\\S+$).{8,20}$";
                        Pattern p = Pattern.compile(pass);
                        Matcher m = p.matcher(password);

                        if(recovery.isEmpty()){
                            recoveryKey.setError("Please fill this");
                            recoveryKey.requestFocus();
                        }else if(!recovery.equals(passwordRecoveryKey)){
                            recoveryKey.setError("Your password key is not matching");
                            recoveryKey.requestFocus();
                        }else if(password.isEmpty()){
                            newPasswd.setError("Please fill this");
                            newPasswd.requestFocus();
                        }else if(!m.matches()){
                            newPasswd.setError("Please use atleast one Uppercase, Lowercase digit, a special character and length must be between 8 and 20");
                            newPasswd.requestFocus();
                        }
                        else{
                            userID.updatePassword(password).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(getContext(), "Password updated successfully", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull
                                                              Exception e) {
                                    Toast.makeText(getContext(), "Error in updating password", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Error in connecting with database", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        return view;
    }
}