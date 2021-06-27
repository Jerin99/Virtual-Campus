package com.campus.vcamp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.xml.namespace.QName;

public class teacher_accept_leave extends Fragment {

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    TextView LeaveApplierName, DateOfLeave, LeaveReason;
    Button leaveAllowButton, leaveDenyButton;
    RelativeLayout relativeLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_teacher_accept_leave, container, false);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        LeaveApplierName = view.findViewById(R.id.leaveApplierName);
        DateOfLeave = view.findViewById(R.id.leaveDate);
        LeaveReason = view.findViewById(R.id.leaveReason);
        leaveAllowButton = view.findViewById(R.id.leaveAllowButton);
        leaveDenyButton = view.findViewById(R.id.leaveDenyButton);
        relativeLayout = view.findViewById(R.id.leaveRelativeLayout);

        DocumentReference documentReference = fStore.collection("Users").document(fAuth.getUid());
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String Teacher, typeOfTeacher, deptOfTeacher;
                Teacher = documentSnapshot.getString("TypeOfTeacher");
                typeOfTeacher = Teacher.substring(0, 1);
                deptOfTeacher = Teacher.substring(1);

                CollectionReference collectionReference = fStore.collection("Leave");
                Task<QuerySnapshot> query = collectionReference.whereEqualTo("Department", ""+deptOfTeacher).whereEqualTo("Year", ""+typeOfTeacher).
                        get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(!queryDocumentSnapshots.isEmpty()){
                            String Reason, LeaveDate, Department, Year, AdmissionID, Name;
                            for(DocumentSnapshot documentSnapshot: queryDocumentSnapshots.getDocuments()){

                                LeaveDate = documentSnapshot.getString("Date");
                                Department = documentSnapshot.getString("Department");
                                Year = documentSnapshot.getString("Year");
                                AdmissionID = documentSnapshot.getString("Admission");
                                Reason = documentSnapshot.getString("Reason");
                                Name = documentSnapshot.getString("Name");

                                LeaveApplierName.setText(Name);
                                DateOfLeave.setText(LeaveDate);
                                LeaveReason.setText(Reason);

                                String finalAdmissionID = AdmissionID;
                                String finalLeaveDate = LeaveDate;
                                String finalName = Name;
                                String finalAdmissionID1 = AdmissionID;
                                String finalAdmissionID2 = AdmissionID;
                                String finalLeaveDate1 = LeaveDate;
                                leaveAllowButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy___HH:mm", Locale.getDefault());
                                        String currentTime = simpleDateFormat.format(new Date());

                                        CollectionReference grantLeave = fStore.collection("studentAboutInfo");
                                        Task<QuerySnapshot> query = grantLeave.whereEqualTo("AdmissionID", ""+finalAdmissionID).
                                                get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                            @Override
                                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                                String uniqueID = finalLeaveDate1.concat("ID");
                                                Toast.makeText(getContext(), ""+uniqueID, Toast.LENGTH_SHORT).show();
                                                if(!queryDocumentSnapshots.isEmpty()){
                                                    DocumentReference insertGrantedLeave = fStore.collection("studentAboutInfo").document(""+finalAdmissionID).
                                                            collection("Leave").document(""+uniqueID);
                                                    Map<String, Object> granted = new HashMap<>();
                                                    granted.put("GrantedLeave", "1");
                                                    granted.put("LeaveGrantedTime", currentTime);
                                                    granted.put("LeaveGrantedFor", finalLeaveDate);
                                                    insertGrantedLeave.set(granted).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void unused) {
                                                            Toast.makeText(getContext(), "Granted leave for "+finalName, Toast.LENGTH_SHORT).show();
                                                            fStore.collection("Leave").document(""+finalAdmissionID).
                                                                    delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void unused) {
                                                                }
                                                            });
                                                        }
                                                    });
                                                }
                                            }
                                        });

                                    }
                                });

//Deny Button for leave
                                leaveDenyButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy___HH:mm", Locale.getDefault());
                                        String currentTime = simpleDateFormat.format(new Date());

                                        CollectionReference grantLeave = fStore.collection("studentAboutInfo");
                                        Task<QuerySnapshot> query = grantLeave.whereEqualTo("AdmissionID", ""+finalAdmissionID).
                                                get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                            @Override
                                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                                String uniqueID = finalLeaveDate.concat("ID");
                                                if(!queryDocumentSnapshots.isEmpty()){
                                                    DocumentReference insertGrantedLeave = fStore.collection("studentAboutInfo").document(""+finalAdmissionID).
                                                            collection("Leave").document(""+uniqueID);
                                                    Map<String, Object> granted = new HashMap<>();
                                                    granted.put("GrantedLeave", "0");
                                                    granted.put("LeaveGrantedTime", currentTime);
                                                    granted.put("LeaveGrantedFor", finalLeaveDate);
                                                    insertGrantedLeave.set(granted).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void unused) {
                                                            Toast.makeText(getContext(), "Denied leave for "+finalName, Toast.LENGTH_SHORT).show();
                                                            fStore.collection("Leave").document(""+finalAdmissionID).
                                                                    delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void unused) {
                                                                }
                                                            });
                                                        }
                                                    });
                                                }
                                            }
                                        });

                                    }
                                });

                            }
                        }else{
                            relativeLayout.setVisibility(View.INVISIBLE);
                            Toast.makeText(getContext(), "No attendance", Toast.LENGTH_LONG).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        /*CollectionReference collectionReference = fStore.collection("Leave");
        Task<QuerySnapshot> query = collectionReference.whereEqualTo("Department", "BCA").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(!queryDocumentSnapshots.isEmpty()){
                    String Reason, LeaveDate, Department, Year, AdmissionID, Name;
                    for(DocumentSnapshot documentSnapshot: queryDocumentSnapshots.getDocuments()){
                        LeaveDate = documentSnapshot.getString("Date");
                        Department = documentSnapshot.getString("Department");
                        Year = documentSnapshot.getString("Year");
                        AdmissionID = documentSnapshot.getString("Admission");
                        Reason = documentSnapshot.getString("Reason");
                        Name = documentSnapshot.getString("Name");

                        LeaveApplierName.setText(Name);
                        DateOfLeave.setText(LeaveDate);
                        LeaveReason.setText(Reason);

                    }

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });*/

        return view;
    }
}