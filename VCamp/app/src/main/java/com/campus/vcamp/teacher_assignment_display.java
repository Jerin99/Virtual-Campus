package com.campus.vcamp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class teacher_assignment_display extends Fragment {

    FirebaseFirestore fStore;
    Button firstSemAssignmentSearchWithID, teacherAssignmentApproved, teacherAssignmentDenied;
    EditText firstSemStudentAdmissionID;
    RelativeLayout teacherAssignmentLayout;
    TextView teacherAssignmentName, teacherAssignmentSubject, teacherAssignmentDate, teacherAssignmentDepartment, teacherAssignmentSemester;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_teacher_first_sem_assignment_display, container, false);

        String Semester = getArguments().getString("Semester");
        String Name = getArguments().getString("Name");

        fStore = FirebaseFirestore.getInstance();
        firstSemStudentAdmissionID = view.findViewById(R.id.firstSemStudentAdmissionID);
        teacherAssignmentLayout = view.findViewById(R.id.teacherAssignmentLayout);
        firstSemAssignmentSearchWithID = view.findViewById(R.id.firstSemAssignmentSearchWithID);
        teacherAssignmentName = view.findViewById(R.id.teacherAssignmentName);
        teacherAssignmentSubject = view.findViewById(R.id.teacherAssignmentSubject);
        teacherAssignmentDate = view.findViewById(R.id.teacherAssignmentDate);
        teacherAssignmentDepartment = view.findViewById(R.id.teacherAssignmentDepartment);
        teacherAssignmentSemester = view.findViewById(R.id.teacherAssignmentSemester);
        teacherAssignmentApproved = view.findViewById(R.id.teacherAssignmentApproved);
        teacherAssignmentDenied = view.findViewById(R.id.teacherAssignmentDenied);

        firstSemAssignmentSearchWithID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Admission = firstSemStudentAdmissionID.getText().toString();
                Query query = fStore.collection("Assignments").whereEqualTo("AdmissionID", Admission)
                        .whereEqualTo("Semester", Semester)
                        .whereEqualTo("Teacher", ""+Name);
                query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(queryDocumentSnapshots.isEmpty()){
                            Toast.makeText(getContext(), "There are no assignments of "+Admission, Toast.LENGTH_SHORT).show();
                        }else{
                            query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                                    if(task.isSuccessful()){
                                        teacherAssignmentLayout.setVisibility(View.VISIBLE);
                                        for(QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                            String Admission, Date, Department, Semester, Subject, Assignment, NameofStudent;
                                            Admission = documentSnapshot.getString("AdmissionID");
                                            Date = documentSnapshot.getString("Date");
                                            Department = documentSnapshot.getString("Department");
                                            Semester = documentSnapshot.getString("Semester");
                                            Subject = documentSnapshot.getString("Subject");
                                            NameofStudent = documentSnapshot.getString("Name");
                                            Assignment = documentSnapshot.getString("PDF");

                                            teacherAssignmentName.setText(NameofStudent);
                                            teacherAssignmentSubject.setText(Subject);
                                            teacherAssignmentDate.setText(Date);
                                            teacherAssignmentDepartment.setText(Department);
                                            teacherAssignmentSemester.setText(Semester);


                                            teacherAssignmentApproved.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    Query query1 = fStore.collection("studentAboutInfo")
                                                            .document(""+Admission)
                                                            .collection("Assignments")
                                                            .whereEqualTo("Teacher", Name)
                                                            .whereEqualTo("Subject", Subject)
                                                            .whereEqualTo("Semester", Semester);
                                                    query1.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                        @Override
                                                        public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                                                            if(task.isSuccessful()){
                                                                for(QueryDocumentSnapshot documentSnapshot1 : task.getResult()){
                                                                    String time = documentSnapshot1.getString("Time");
                                                                    DocumentReference approved = fStore.collection("studentAboutInfo")
                                                                            .document(""+Admission)
                                                                            .collection("Assignments")
                                                                            .document(""+time);
                                                                    approved.update("Status", "1");
                                                                    Toast.makeText(getContext(), "Approved assignment for "+Name, Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        }
                                                    });

                                                }
                                            });

                                            teacherAssignmentDenied.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    Query query1 = fStore.collection("studentAboutInfo")
                                                            .document(""+Admission)
                                                            .collection("Assignments")
                                                            .whereEqualTo("Teacher", Name)
                                                            .whereEqualTo("Subject", Subject)
                                                            .whereEqualTo("Semester", Semester);
                                                    query1.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                        @Override
                                                        public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                                                            if(task.isSuccessful()){
                                                                for(QueryDocumentSnapshot documentSnapshot1 : task.getResult()){
                                                                    String time = documentSnapshot1.getString("Time");
                                                                    DocumentReference approved = fStore.collection("studentAboutInfo")
                                                                            .document(""+Admission)
                                                                            .collection("Assignments")
                                                                            .document(""+time);
                                                                    approved.update("Status", "2");
                                                                    Toast.makeText(getContext(), "Denied assignment for "+Name, Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        }
                                                    });
                                                }
                                            });

                                        }
                                    }
                                }
                            });
                        }
                    }
                });
            }
        });

        return view;
    }
}
