package com.campus.vcamp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class student_assignment_upload_fragment extends Fragment {

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    Button studentAssignmentUploadButton;
    StorageReference storageReference;
    ProgressBar progressBarForAssignmentUpload;
    String Admission, Department, Year, Name;
    String Semester, Subject, Teacher;
    EditText studentAssignmentSemInsert, studentAssignmentSubjectInsert, studentAssignmentTeacherName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_student_assignment_upload_fragment, container, false);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        studentAssignmentUploadButton = view.findViewById(R.id.studentAssignmentUploadButton);
        progressBarForAssignmentUpload = view.findViewById(R.id.progressBarForAssignmentUpload);
        studentAssignmentSemInsert = view.findViewById(R.id.studentAssignmentSemInsert);
        studentAssignmentSubjectInsert = view.findViewById(R.id.studentAssignmentSubjectInsert);
        studentAssignmentTeacherName = view.findViewById(R.id.studentAssignmentTeacherName);

        Admission = getArguments().getString("admission");
        Name = getArguments().getString("name");
        Year = getArguments().getString("year");
        Department = getArguments().getString("dept");
        Toast.makeText(getContext(), ""+Admission+" "+Year+" "+Department+" "+Name, Toast.LENGTH_SHORT).show();

        studentAssignmentUploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String sem, sub, teacher;
                sem = studentAssignmentSemInsert.getText().toString();
                sub = studentAssignmentSubjectInsert.getText().toString();
                teacher = studentAssignmentTeacherName.getText().toString();

                Semester = sem.replaceAll("\\s+", "");
                Subject = sub.replaceAll("\\s", "_");
                Teacher = teacher.replaceAll("\\s", "_");

                if(sem.isEmpty()){
                    studentAssignmentSemInsert.setError("Please fill this field");
                    studentAssignmentSemInsert.requestFocus();
                }else if(sub.isEmpty()){
                    studentAssignmentSubjectInsert.setError("Please fill this field");
                    studentAssignmentSubjectInsert.requestFocus();
                }else if(teacher.isEmpty()){
                    studentAssignmentTeacherName.setError("Please fill this field");
                    studentAssignmentTeacherName.requestFocus();
                }else{
                    Intent openPDF = new Intent();
                    openPDF.setType("application/pdf");
                    openPDF.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(openPDF, "Select PDF"), 12);
                }
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 12){
            Uri pdfURI = data.getData();
            uploadAssignment(pdfURI);
        }
    }

    private void uploadAssignment(Uri profileUri) {
        //upload image to firebase

        StorageReference fileRef = storageReference.child("Assignments/"+Teacher+"/"+Year+"/"+Semester+"/"+Admission+"__"+Department+"__"+Subject+".pdf");
        fileRef.putFile(profileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String url = uri.toString();
                        Date date = new Date();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                        String CurrentDate = simpleDateFormat.format(date).toString();
                        long random = Calendar.getInstance().getTimeInMillis();
                        String randomTime = random+"";
                        DocumentReference insertAssignmentToStudent = fStore.collection("studentAboutInfo")
                                .document(""+Admission)
                                .collection("Assignments")
                                .document(""+randomTime);
                        Map<String, Object> insertAssignmentToStudentMap = new HashMap<>();
                        insertAssignmentToStudentMap.put("Subject", Subject);
                        insertAssignmentToStudentMap.put("Semester", Semester);
                        insertAssignmentToStudentMap.put("Date", CurrentDate);
                        insertAssignmentToStudentMap.put("Url", url);
                        insertAssignmentToStudentMap.put("Teacher", Teacher);
                        insertAssignmentToStudentMap.put("Time", randomTime);
                        insertAssignmentToStudentMap.put("Status", "0");
                        insertAssignmentToStudent.set(insertAssignmentToStudentMap);

                        DocumentReference insertAssignmentToTeacher = fStore.collection("Assignments")
                                .document(""+randomTime);
                        Map<String, Object> insertAssignmentToTeacherMap = new HashMap<>();
                        insertAssignmentToTeacherMap.put("Semester", Semester);
                        insertAssignmentToTeacherMap.put("AdmissionID", Admission);
                        insertAssignmentToTeacherMap.put("Teacher", Teacher);
                        insertAssignmentToTeacherMap.put("Name", Name);
                        insertAssignmentToTeacherMap.put("Date", CurrentDate);
                        insertAssignmentToTeacherMap.put("Semester", Semester);
                        insertAssignmentToTeacherMap.put("Subject", Subject);
                        insertAssignmentToTeacherMap.put("Department", Department);
                        insertAssignmentToTeacherMap.put("PDF", url);
                        insertAssignmentToTeacher.set(insertAssignmentToTeacherMap);

                        Toast.makeText(getContext(), "Assignment uploaded successfully", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull @NotNull UploadTask.TaskSnapshot snapshot) {
                double progress = (100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                progressBarForAssignmentUpload.setVisibility(View.VISIBLE);
                int currentProgress = (int)progress;
                progressBarForAssignmentUpload.setProgress(currentProgress);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "Failed to upload", Toast.LENGTH_SHORT).show();
            }
        });
    }

}