package com.campus.vcamp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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

public class teacher_leave_acceptance extends AppCompatActivity {

    private RecyclerView student_leave_acceptance;
    FirebaseFirestore fStore;
    private FirestoreRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_leave_acceptance);

        fStore = FirebaseFirestore.getInstance();
        student_leave_acceptance = findViewById(R.id.student_leave_acceptance);

        String TypeOfTeacher = getIntent().getStringExtra("TeacherType");

        String Year = TypeOfTeacher.substring(0,1);
        String Dept = TypeOfTeacher.substring(1);

        Query query = fStore.collection("Leave").whereEqualTo("Department", ""+Dept).whereEqualTo("Year", ""+Year);
        FirestoreRecyclerOptions<teacher_leave_acceptance_modal> options = new FirestoreRecyclerOptions.Builder<teacher_leave_acceptance_modal>()
                .setQuery(query, teacher_leave_acceptance_modal.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<teacher_leave_acceptance_modal, attendanceViewHolder>(options) {
            @NonNull
            @NotNull
            @Override
            public attendanceViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.teacher_leave_acceptance_recycler, parent, false);
                return new attendanceViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull @NotNull teacher_leave_acceptance.attendanceViewHolder holder, int position, @NonNull @NotNull teacher_leave_acceptance_modal model) {
                String admissionNumber = model.getAdmission();
                String nameOfStudent = model.getName();
                holder.Name.setText(model.getName());
                holder.Date.setText(model.getDate());
                holder.Reason.setText(model.getReason());


                CollectionReference collectionReference = fStore.collection("Leave");
                Task<QuerySnapshot> query = collectionReference.whereEqualTo("Department", ""+Dept).whereEqualTo("Year", ""+Year).
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

                                String finalAdmissionID = AdmissionID;
                                String finalLeaveDate = LeaveDate;
                                String finalName = Name;
                                String finalAdmissionID1 = AdmissionID;
                                String finalAdmissionID2 = AdmissionID;
                                String finalLeaveDate1 = LeaveDate;
                                holder.leaveAllowButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy___HH:mm", Locale.getDefault());
                                        String currentTime = simpleDateFormat.format(new Date());

                                        CollectionReference grantLeave = fStore.collection("studentAboutInfo");
                                        Task<QuerySnapshot> query = grantLeave.whereEqualTo("AdmissionID", ""+admissionNumber).
                                                get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                            @Override
                                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                                                Toast.makeText(teacher_leave_acceptance.this, ""+admissionNumber, Toast.LENGTH_SHORT).show();
                                                String uniqueID = finalLeaveDate1.concat("ID");
//                                                Toast.makeText(teacher_leave_acceptance.this, ""+uniqueID, Toast.LENGTH_SHORT).show();
                                                if(!queryDocumentSnapshots.isEmpty()){
                                                    DocumentReference insertGrantedLeave = fStore.collection("studentAboutInfo").document(""+admissionNumber).
                                                            collection("Leave").document(""+uniqueID);
                                                    Map<String, Object> granted = new HashMap<>();
                                                    granted.put("GrantedLeave", "1");
                                                    granted.put("LeaveGrantedTime", currentTime);
                                                    granted.put("LeaveGrantedFor", finalLeaveDate);
                                                    insertGrantedLeave.set(granted).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void unused) {
                                                            Toast.makeText(teacher_leave_acceptance.this, "Granted leave for "+nameOfStudent, Toast.LENGTH_SHORT).show();
                                                            fStore.collection("Leave").document(""+admissionNumber).
                                                                    delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void unused) {
                                                                }
                                                            });
                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull @NotNull Exception e) {
                                                            Toast.makeText(teacher_leave_acceptance.this, "Failed", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                                }
                                            }
                                        });

                                    }
                                });

//Deny Button for leave
                                holder.leaveDenyButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy___HH:mm", Locale.getDefault());
                                        String currentTime = simpleDateFormat.format(new Date());

                                        CollectionReference grantLeave = fStore.collection("studentAboutInfo");
                                        Task<QuerySnapshot> query = grantLeave.whereEqualTo("AdmissionID", ""+admissionNumber).
                                                get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                            @Override
                                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                                String uniqueID = finalLeaveDate.concat("ID");
                                                if(!queryDocumentSnapshots.isEmpty()){
                                                    DocumentReference insertGrantedLeave = fStore.collection("studentAboutInfo").document(""+admissionNumber).
                                                            collection("Leave").document(""+uniqueID);
                                                    Map<String, Object> granted = new HashMap<>();
                                                    granted.put("GrantedLeave", "0");
                                                    granted.put("LeaveGrantedTime", currentTime);
                                                    granted.put("LeaveGrantedFor", finalLeaveDate);
                                                    insertGrantedLeave.set(granted).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void unused) {
                                                            Toast.makeText(teacher_leave_acceptance.this, "Denied leave for "+nameOfStudent, Toast.LENGTH_SHORT).show();
                                                            fStore.collection("Leave").document(""+admissionNumber).
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
                            holder.relativeLayout.setVisibility(View.INVISIBLE);
                            Toast.makeText(teacher_leave_acceptance.this, "No attendance", Toast.LENGTH_LONG).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Toast.makeText(teacher_leave_acceptance.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        };

        student_leave_acceptance.setHasFixedSize(true);
        student_leave_acceptance.setLayoutManager(new LinearLayoutManager(this));
        student_leave_acceptance.setAdapter(adapter);

    }


    private class attendanceViewHolder extends RecyclerView.ViewHolder {

        private TextView Name, Date, Reason;
        Button leaveAllowButton, leaveDenyButton;
        RelativeLayout relativeLayout;

        public attendanceViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            Name = itemView.findViewById(R.id.nameOfLeaveAppliedStudent);
            Date = itemView.findViewById(R.id.dateOfLeaveAppliedStudent);
            Reason = itemView.findViewById(R.id.ReasonForLeaveAppliedStudent);
            leaveAllowButton = itemView.findViewById(R.id.leaveAllowButton);
            leaveDenyButton = itemView.findViewById(R.id.leaveDenyButton);

            relativeLayout = findViewById(R.id.teacherLeaveAcceptanceLayout);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}