package com.campus.vcamp;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class student_attendance extends Fragment {

    TextView dateLeave;
    EditText reasonForLeave;
    Button applyLeave;
    DatePickerDialog.OnDateSetListener datePickerDialog;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_attendance, container, false);

        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        applyLeave = view.findViewById(R.id.applyForAttendance);
        dateLeave = view.findViewById(R.id.dateLeave);
        reasonForLeave = view.findViewById(R.id.reasonForLeave);


        DocumentReference retrieveAdmissionID = fStore.collection("Users").document(fAuth.getUid());
        retrieveAdmissionID.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                final String admissionID = documentSnapshot.getString("Admission");
                final String fullName = documentSnapshot.getString("FullName");

                DocumentReference retrieveStudentInformtion = fStore.collection("studentAboutInfo").document(""+admissionID);
                retrieveStudentInformtion.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        final String department = documentSnapshot.getString("Department");
                        final String DeptYear = documentSnapshot.getString("Year");

                        dateLeave.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Calendar calendar = Calendar.getInstance();
                                int year = calendar.get(Calendar.YEAR);
                                int month = calendar.get(Calendar.MONTH);
                                int day = calendar.get(Calendar.DAY_OF_MONTH);

                                DatePickerDialog dialog = new DatePickerDialog(getContext(),
                                        android.R.style.Theme_Holo_Dialog_MinWidth,
                                        datePickerDialog,
                                        year, month, day);
                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                dialog.show();
                            }
                        });

                        datePickerDialog = new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                boolean dateChecker = true;
                                month = month + 1;
                                Calendar calendar = Calendar.getInstance();
                                int current_year = calendar.get(Calendar.YEAR);
                                int current_month = calendar.get(Calendar.MONTH)+1;
                                int current_day = calendar.get(Calendar.DAY_OF_MONTH);

                                if(year<current_year){
                                    dateLeave.setError("Enter a valid date");
                                    dateLeave.requestFocus();
                                    dateLeave.setText("");
                                    dateChecker = false;
                                }else if(month<current_month) {
                                    dateLeave.setError("Enter a valid date");
                                    dateLeave.requestFocus();
                                    dateLeave.setText("");
                                    dateChecker = false;
                                }else if(dayOfMonth<current_day){
                                    dateLeave.setError("Enter a valid date");
                                    dateLeave.requestFocus();
                                    dateLeave.setText("");
                                    dateChecker = false;
                                }
                                if(dateChecker){
                                    String date = month + "-" + dayOfMonth + "-" + year;
                                    dateLeave.setText(date);
                                    applyLeave.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            String reason = reasonForLeave.getText().toString();
                                            if(reason.isEmpty()){
                                                reasonForLeave.setError("Please fill this box");
                                                reasonForLeave.requestFocus();
                                            }else{
                                                DocumentReference documentReference = fStore.collection("Leave").document(admissionID);
                                                Map<String, Object> Leave = new HashMap<>();
                                                Leave.put("Admission", admissionID);
                                                Leave.put("Name", fullName);
                                                Leave.put("Department", department);
                                                Leave.put("Year", DeptYear);
                                                Leave.put("Date", date);
                                                Leave.put("Reason", reason);
                                                documentReference.set(Leave).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        Toast.makeText(getContext(), "Leave applied successfully", Toast.LENGTH_SHORT).show();
                                                        dateLeave.setText("");
                                                        reasonForLeave.setText("");
                                                        dateLeave.requestFocus();
                                                        int a = 6;
                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull @NotNull Exception e) {
                                                        Toast.makeText(getActivity(), "Failed to apply for leave", Toast.LENGTH_SHORT).show();
                                                    }
                                                });

                                            }
                                        }
                                    });
                                }
                            }

                        };
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {

            }
        });

        return view;
    }

}

/*
dateLeave.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(getContext(),
        android.R.style.Theme_Holo_Dialog_MinWidth,
        datePickerDialog,
        year, month, day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        }
        });

        datePickerDialog = new DatePickerDialog.OnDateSetListener() {
@Override
public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        month = month + 1;
        Calendar calendar = Calendar.getInstance();
        int current_year = calendar.get(Calendar.YEAR);
        int current_month = calendar.get(Calendar.MONTH);
        int current_day = calendar.get(Calendar.DAY_OF_MONTH);
        if((year<current_year) || (month<current_month) || (dayOfMonth<current_day)){
        dateLeave.setError("Enter a valid date");
        dateLeave.requestFocus();
        }else{
        String date = month + "/" + dayOfMonth + "/" + year;
        dateLeave.setText(date);
        applyLeave.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
        String reason = reasonForLeave.getText().toString();
        if(reason.isEmpty()){
        reasonForLeave.setError("Please fill this box");
        reasonForLeave.requestFocus();
        }else{

        DocumentReference documentReference = fStore.collection("Leave").document(admissionID);
        Map<String, Object> Leave = new HashMap<>();
        Leave.put("Admission", admissionID);
        Leave.put("Date", date);
        Leave.put("Reason", reason);
        documentReference.set(Leave).addOnSuccessListener(new OnSuccessListener<Void>() {
@Override
public void onSuccess(Void unused) {
        Toast.makeText(getContext(), "Leave applied successfully", Toast.LENGTH_SHORT).show();
        }
        }).addOnFailureListener(new OnFailureListener() {
@Override
public void onFailure(@NonNull @NotNull Exception e) {
        Toast.makeText(getActivity(), "Failed to apply for leave", Toast.LENGTH_SHORT).show();
        }
        });

        }
        }
        });
        }
        }

        };*/
