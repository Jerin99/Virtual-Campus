package com.campus.vcamp;

import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class insert_student_marks extends Fragment {
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    EditText studentAdmissionIdInInsertingStudentMarks;
    Button studentAdmissionIdInInsertingStudentMarksButton, insertMarks, semCheckingButton;
    TextView nameOfTheStudentInInsertingMarks, subjectsNameInInsertingMarks, MarksInInsertingMarks;
    EditText semNoOfInsertingMarks;
    EditText subject1MarkTeacher, subject2MarkTeacher, subject3MarkTeacher, subject4MarkTeacher, subject5MarkTeacher, subject6MarkTeacher,
            subject1Teacher, subject2Teacher, subject3Teacher, subject4Teacher, subject5Teacher, subject6Teacher;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_insert_student_marks, container, false);
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        studentAdmissionIdInInsertingStudentMarks = view.findViewById(R.id.studentAdmissionIdInInsertingStudentMarks);
        studentAdmissionIdInInsertingStudentMarksButton = view.findViewById(R.id.studentAdmissionIdInInsertingStudentMarksButton);
        nameOfTheStudentInInsertingMarks = view.findViewById(R.id.nameOfTheStudentInInsertingMarks);
        insertMarks = view.findViewById(R.id.insertMarks);
        semNoOfInsertingMarks = view.findViewById(R.id.semNoOfInsertingMarks);

        subjectsNameInInsertingMarks = view.findViewById(R.id.subjectsNameInInsertingMarks);
        MarksInInsertingMarks = view.findViewById(R.id.MarksInInsertingMarks);
        semCheckingButton = view.findViewById(R.id.semCheckingButton);

        subject1Teacher = view.findViewById(R.id.subject1Teacher);
        subject2Teacher = view.findViewById(R.id.subject2Teacher);
        subject3Teacher = view.findViewById(R.id.subject3Teacher);
        subject4Teacher = view.findViewById(R.id.subject4Teacher);
        subject5Teacher = view.findViewById(R.id.subject5Teacher);
        subject6Teacher = view.findViewById(R.id.subject6Teacher);

        subject1MarkTeacher = view.findViewById(R.id.subject1MarkTeacher);
        subject2MarkTeacher = view.findViewById(R.id.subject2MarkTeacher);
        subject3MarkTeacher = view.findViewById(R.id.subject3MarkTeacher);
        subject4MarkTeacher = view.findViewById(R.id.subject4MarkTeacher);
        subject5MarkTeacher = view.findViewById(R.id.subject5MarkTeacher);
        subject6MarkTeacher = view.findViewById(R.id.subject6MarkTeacher);

        semNoOfInsertingMarks.setVisibility(View.INVISIBLE);
        subject1Teacher.setVisibility(View.INVISIBLE);
        subject2Teacher.setVisibility(View.INVISIBLE);
        subject3Teacher.setVisibility(View.INVISIBLE);
        subject4Teacher.setVisibility(View.INVISIBLE);
        subject5Teacher.setVisibility(View.INVISIBLE);
        subject6Teacher.setVisibility(View.INVISIBLE);
        subject1MarkTeacher.setVisibility(View.INVISIBLE);
        subject2MarkTeacher.setVisibility(View.INVISIBLE);
        subject3MarkTeacher.setVisibility(View.INVISIBLE);
        subject4MarkTeacher.setVisibility(View.INVISIBLE);
        subject5MarkTeacher.setVisibility(View.INVISIBLE);
        subject6MarkTeacher.setVisibility(View.INVISIBLE);
        subjectsNameInInsertingMarks.setVisibility(View.INVISIBLE);
        MarksInInsertingMarks.setVisibility(View.INVISIBLE);
        insertMarks.setVisibility(View.INVISIBLE);
        semCheckingButton.setVisibility(View.INVISIBLE);


        studentAdmissionIdInInsertingStudentMarksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String AdmissionId = studentAdmissionIdInInsertingStudentMarks.getText().toString();
                if(AdmissionId.isEmpty()){
                    studentAdmissionIdInInsertingStudentMarks.setError("This field can't be empty");
                    studentAdmissionIdInInsertingStudentMarks.requestFocus();
                }else{
                    DocumentReference documentReference = fStore.collection("Users").document(fAuth.getUid());
                    documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            String TeacherType = documentSnapshot.getString("TypeOfTeacher");
                            String Year = TeacherType.substring(0,1);
                            String Dept = TeacherType.substring(1);

                            DocumentReference checkTeacherEligibility = fStore.collection("studentAboutInfo").document(""+AdmissionId);
                            checkTeacherEligibility.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    String yearOfStudent = documentSnapshot.getString("Year");
                                    String deptOfStudent = documentSnapshot.getString("Department");

                                    if (Year.equals(yearOfStudent) && Dept.equals(deptOfStudent)){
                                        Query nameRetrievalFromAdmissionID = fStore.collection("Users").whereEqualTo("Admission", AdmissionId);
                                        nameRetrievalFromAdmissionID.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                            @Override
                                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                                if(!queryDocumentSnapshots.isEmpty()){
                                                    String Name;
                                                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                                        Name = documentSnapshot.getString("FullName");
                                                        nameOfTheStudentInInsertingMarks.setText(Name);

                                                        semNoOfInsertingMarks.setVisibility(View.VISIBLE);
                                                        semCheckingButton.setVisibility(View.VISIBLE);

                                                        semCheckingButton.setOnClickListener(new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View v) {
                                                                String checkSem = semNoOfInsertingMarks.getText().toString();
//************************************************************BCA Semester wise Subjects************************************************************
                                                                if(Year.equals("1") && (Dept.equals("BCA"))) {
                                                                    if (checkSem.equals("1") || (checkSem.equals("2"))) {
                                                                        getVisible();
                                                                        if (checkSem.equals("1")) {
                                                                            int SemNumber = 1;
                                                                            String Semester = "First Semester Result";
                                                                            String sem1sub1 = "English I";
                                                                            String sem1sub2 = "Mathematics";
                                                                            String sem1sub3 = "Statistics";
                                                                            String sem1sub4 = "COA";
                                                                            String sem1sub5 = "C";
                                                                            String sem1sub6 = "LAB I";
                                                                            subject1Teacher.setText(sem1sub1);
                                                                            subject2Teacher.setText(sem1sub2);
                                                                            subject3Teacher.setText(sem1sub3);
                                                                            subject4Teacher.setText(sem1sub4);
                                                                            subject5Teacher.setText(sem1sub5);
                                                                            subject6Teacher.setText(sem1sub6);

                                                                            insertMarks.setOnClickListener(new View.OnClickListener() {
                                                                                @Override
                                                                                public void onClick(View v) {
                                                                                    String sem1mark1, sem1mark2, sem1mark3, sem1mark4, sem1mark5, sem1mark6;
                                                                                    sem1mark1 = subject1MarkTeacher.getText().toString();
                                                                                    sem1mark2 = subject2MarkTeacher.getText().toString();
                                                                                    sem1mark3 = subject3MarkTeacher.getText().toString();
                                                                                    sem1mark4 = subject4MarkTeacher.getText().toString();
                                                                                    sem1mark5 = subject5MarkTeacher.getText().toString();
                                                                                    sem1mark6 = subject6MarkTeacher.getText().toString();
                                                                                    if(!sem1mark1.isEmpty() && !sem1mark2.isEmpty() && !sem1mark3.isEmpty() && !sem1mark4.isEmpty() && !sem1mark5.isEmpty() && !sem1mark6.isEmpty()) {
                                                                                        int total = Integer.parseInt(sem1mark1)+Integer.parseInt(sem1mark2)+Integer.parseInt(sem1mark3)+Integer.parseInt(sem1mark4)+Integer.parseInt(sem1mark5)+Integer.parseInt(sem1mark6);
                                                                                        int average = total/6;
                                                                                        String grade = "";
                                                                                        if(Integer.parseInt(sem1mark1)>100 || Integer.parseInt(sem1mark2)>100 || Integer.parseInt(sem1mark3)>100 || Integer.parseInt(sem1mark4)>100 || Integer.parseInt(sem1mark5)>100 || Integer.parseInt(sem1mark6)>100){
                                                                                            Toast.makeText(getContext(), "Marks should be less than or equal to 100", Toast.LENGTH_SHORT).show();
                                                                                        }else{
                                                                                            if(average<101 && average>90){
                                                                                                grade = "A";
                                                                                            }else if(average<91 && average>80){
                                                                                                grade = "B";
                                                                                            }else if(average<81 && average>70){
                                                                                                grade = "C";
                                                                                            }else if(average<71 && average>60){
                                                                                                grade = "D";
                                                                                            }else{
                                                                                                grade = "Failed";
                                                                                            }

                                                                                            DocumentReference insertSem1Mark = fStore.collection("studentAboutInfo")
                                                                                                    .document(""+AdmissionId)
                                                                                                    .collection("Student_Marks")
                                                                                                    .document("Sem1");
                                                                                            Map<String, Object>insertMarks = new HashMap<>();
                                                                                            insertMarks.put("sub1", sem1sub1);
                                                                                            insertMarks.put("sub2", sem1sub2);
                                                                                            insertMarks.put("sub3", sem1sub3);
                                                                                            insertMarks.put("sub4", sem1sub4);
                                                                                            insertMarks.put("sub5", sem1sub5);
                                                                                            insertMarks.put("sub6", sem1sub6);
                                                                                            insertMarks.put("mark1", sem1mark1);
                                                                                            insertMarks.put("mark2", sem1mark2);
                                                                                            insertMarks.put("mark3", sem1mark3);
                                                                                            insertMarks.put("mark4", sem1mark4);
                                                                                            insertMarks.put("mark5", sem1mark5);
                                                                                            insertMarks.put("mark6", sem1mark6);
                                                                                            insertMarks.put("total", Integer.toString(total));
                                                                                            insertMarks.put("grade", grade);
                                                                                            insertMarks.put("SemOrder", SemNumber);
                                                                                            insertMarks.put("Semester", Semester);

                                                                                            insertSem1Mark.set(insertMarks).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                                @Override
                                                                                                public void onSuccess(Void unused) {
                                                                                                    Toast.makeText(getContext(), "Marks inserted successfully", Toast.LENGTH_SHORT).show();
                                                                                                }
                                                                                            }).addOnFailureListener(new OnFailureListener() {
                                                                                                @Override
                                                                                                public void onFailure(@NonNull @NotNull Exception e) {
                                                                                                    Toast.makeText(getContext(), "Failed to insert marks", Toast.LENGTH_SHORT).show();
                                                                                                }
                                                                                            });

                                                                                        }
                                                                                    }else{
                                                                                        Toast.makeText(getContext(), "Please fill all the fields", Toast.LENGTH_SHORT).show();
                                                                                    }
                                                                                }
                                                                            });

                                                                        } else if (checkSem.equals("2")) {
                                                                            int SemNumber = 2;
                                                                            String Semester = "Second Semester Result";
                                                                            String sem2sub1 = "English II";
                                                                            String sem2sub2 = "Mathematics";
                                                                            String sem2sub3 = "DBMS";
                                                                            String sem2sub4 = "Computer";
                                                                            String sem2sub5 = "C++";
                                                                            String sem2sub6 = "LAB II";
                                                                            subject1Teacher.setText(sem2sub1);
                                                                            subject2Teacher.setText(sem2sub2);
                                                                            subject3Teacher.setText(sem2sub3);
                                                                            subject4Teacher.setText(sem2sub4);
                                                                            subject5Teacher.setText(sem2sub5);
                                                                            subject6Teacher.setText(sem2sub6);

                                                                            insertMarks.setOnClickListener(new View.OnClickListener() {
                                                                                @Override
                                                                                public void onClick(View v) {
                                                                                    String sem2mark1, sem2mark2, sem2mark3, sem2mark4, sem2mark5, sem2mark6;
                                                                                    sem2mark1 = subject1MarkTeacher.getText().toString();
                                                                                    sem2mark2 = subject2MarkTeacher.getText().toString();
                                                                                    sem2mark3 = subject3MarkTeacher.getText().toString();
                                                                                    sem2mark4 = subject4MarkTeacher.getText().toString();
                                                                                    sem2mark5 = subject5MarkTeacher.getText().toString();
                                                                                    sem2mark6 = subject6MarkTeacher.getText().toString();
                                                                                    if(!sem2mark1.isEmpty() && !sem2mark2.isEmpty() && !sem2mark3.isEmpty() && !sem2mark4.isEmpty() && !sem2mark5.isEmpty() && !sem2mark6.isEmpty()) {
                                                                                        int total = Integer.parseInt(sem2mark1)+Integer.parseInt(sem2mark2)+Integer.parseInt(sem2mark3)+Integer.parseInt(sem2mark4)+Integer.parseInt(sem2mark5)+Integer.parseInt(sem2mark6);
                                                                                        int average = total/6;
                                                                                        String grade = "";
                                                                                        if(Integer.parseInt(sem2mark1)>100 || Integer.parseInt(sem2mark2)>100 || Integer.parseInt(sem2mark3)>100 || Integer.parseInt(sem2mark4)>100 || Integer.parseInt(sem2mark5)>100 || Integer.parseInt(sem2mark6)>100){
                                                                                            Toast.makeText(getContext(), "Marks should be less than or equal to 100", Toast.LENGTH_SHORT).show();
                                                                                        }else{
                                                                                            if(average<101 && average>90){
                                                                                                grade = "A";
                                                                                            }else if(average<91 && average>80){
                                                                                                grade = "B";
                                                                                            }else if(average<81 && average>70){
                                                                                                grade = "C";
                                                                                            }else if(average<71 && average>60){
                                                                                                grade = "D";
                                                                                            }else{
                                                                                                grade = "Failed";
                                                                                            }

                                                                                            DocumentReference insertSem1Mark = fStore.collection("studentAboutInfo")
                                                                                                    .document(""+AdmissionId)
                                                                                                    .collection("Student_Marks")
                                                                                                    .document("Sem2");
                                                                                            Map<String, Object>insertMarks = new HashMap<>();
                                                                                            insertMarks.put("sub1", sem2sub1);
                                                                                            insertMarks.put("sub2", sem2sub2);
                                                                                            insertMarks.put("sub3", sem2sub3);
                                                                                            insertMarks.put("sub4", sem2sub4);
                                                                                            insertMarks.put("sub5", sem2sub5);
                                                                                            insertMarks.put("sub6", sem2sub6);
                                                                                            insertMarks.put("mark1", sem2mark1);
                                                                                            insertMarks.put("mark2", sem2mark2);
                                                                                            insertMarks.put("mark3", sem2mark3);
                                                                                            insertMarks.put("mark4", sem2mark4);
                                                                                            insertMarks.put("mark5", sem2mark5);
                                                                                            insertMarks.put("mark6", sem2mark6);
                                                                                            insertMarks.put("total", Integer.toString(total));
                                                                                            insertMarks.put("grade", grade);
                                                                                            insertMarks.put("SemOrder", SemNumber);
                                                                                            insertMarks.put("Semester", Semester);

                                                                                            insertSem1Mark.set(insertMarks).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                                @Override
                                                                                                public void onSuccess(Void unused) {
                                                                                                    Toast.makeText(getContext(), "Marks inserted successfully", Toast.LENGTH_SHORT).show();
                                                                                                }
                                                                                            }).addOnFailureListener(new OnFailureListener() {
                                                                                                @Override
                                                                                                public void onFailure(@NonNull @NotNull Exception e) {
                                                                                                    Toast.makeText(getContext(), "Failed to insert marks", Toast.LENGTH_SHORT).show();
                                                                                                }
                                                                                            });

                                                                                        }
                                                                                    }else{
                                                                                        Toast.makeText(getContext(), "Please fill all the fields", Toast.LENGTH_SHORT).show();
                                                                                    }
                                                                                }
                                                                            });
                                                                        }
                                                                    }else{
                                                                        getInvisible();
                                                                        Toast.makeText(getContext(), "You can't access this", Toast.LENGTH_SHORT).show();
                                                                    }
                                                                }else if(Year.equals("2") && (Dept.equals("BCA"))) {
                                                                    if (checkSem.equals("3") || (checkSem.equals("4"))) {
                                                                        getVisible();
                                                                        if (checkSem.equals("3")) {
                                                                            int SemNumber = 3;
                                                                            String Semester = "Third Semester Result";
                                                                            String sem3sub1 = "Statistics";
                                                                            String sem3sub2 = "CG";
                                                                            String sem3sub3 = "Microprocessor";
                                                                            String sem3sub4 = "OS";
                                                                            String sem3sub5 = "DS";
                                                                            String sem3sub6 = "LAB III";
                                                                            subject1Teacher.setText(sem3sub1);
                                                                            subject2Teacher.setText(sem3sub2);
                                                                            subject3Teacher.setText(sem3sub3);
                                                                            subject4Teacher.setText(sem3sub4);
                                                                            subject5Teacher.setText(sem3sub5);
                                                                            subject6Teacher.setText(sem3sub6);

                                                                            insertMarks.setOnClickListener(new View.OnClickListener() {
                                                                                @Override
                                                                                public void onClick(View v) {
                                                                                    String sem3mark1, sem3mark2, sem3mark3, sem3mark4, sem3mark5, sem3mark6;
                                                                                    sem3mark1 = subject1MarkTeacher.getText().toString();
                                                                                    sem3mark2 = subject2MarkTeacher.getText().toString();
                                                                                    sem3mark3 = subject3MarkTeacher.getText().toString();
                                                                                    sem3mark4 = subject4MarkTeacher.getText().toString();
                                                                                    sem3mark5 = subject5MarkTeacher.getText().toString();
                                                                                    sem3mark6 = subject6MarkTeacher.getText().toString();
                                                                                    if(!sem3mark1.isEmpty() && !sem3mark2.isEmpty() && !sem3mark3.isEmpty() && !sem3mark4.isEmpty() && !sem3mark5.isEmpty() && !sem3mark6.isEmpty()) {
                                                                                        int total = Integer.parseInt(sem3mark1)+Integer.parseInt(sem3mark2)+Integer.parseInt(sem3mark3)+Integer.parseInt(sem3mark4)+Integer.parseInt(sem3mark5)+Integer.parseInt(sem3mark6);
                                                                                        int average = total/6;
                                                                                        String grade = "";
                                                                                        if(Integer.parseInt(sem3mark1)>100 || Integer.parseInt(sem3mark2)>100 || Integer.parseInt(sem3mark3)>100 || Integer.parseInt(sem3mark4)>100 || Integer.parseInt(sem3mark5)>100 || Integer.parseInt(sem3mark6)>100){
                                                                                            Toast.makeText(getContext(), "Marks should be less than or equal to 100", Toast.LENGTH_SHORT).show();
                                                                                        }else{
                                                                                            if(average<101 && average>90){
                                                                                                grade = "A";
                                                                                            }else if(average<91 && average>80){
                                                                                                grade = "B";
                                                                                            }else if(average<81 && average>70){
                                                                                                grade = "C";
                                                                                            }else if(average<71 && average>60){
                                                                                                grade = "D";
                                                                                            }else{
                                                                                                grade = "Failed";
                                                                                            }

                                                                                            DocumentReference insertSem3Mark = fStore.collection("studentAboutInfo")
                                                                                                    .document(""+AdmissionId)
                                                                                                    .collection("Student_Marks")
                                                                                                    .document("Sem3");
                                                                                            Map<String, Object>insertMarks = new HashMap<>();
                                                                                            insertMarks.put("sub1", sem3sub1);
                                                                                            insertMarks.put("sub2", sem3sub2);
                                                                                            insertMarks.put("sub3", sem3sub3);
                                                                                            insertMarks.put("sub4", sem3sub4);
                                                                                            insertMarks.put("sub5", sem3sub5);
                                                                                            insertMarks.put("sub6", sem3sub6);
                                                                                            insertMarks.put("mark1", sem3mark1);
                                                                                            insertMarks.put("mark2", sem3mark2);
                                                                                            insertMarks.put("mark3", sem3mark3);
                                                                                            insertMarks.put("mark4", sem3mark4);
                                                                                            insertMarks.put("mark5", sem3mark5);
                                                                                            insertMarks.put("mark6", sem3mark6);
                                                                                            insertMarks.put("total", Integer.toString(total));
                                                                                            insertMarks.put("grade", grade);
                                                                                            insertMarks.put("SemOrder", SemNumber);
                                                                                            insertMarks.put("Semester", Semester);

                                                                                            insertSem3Mark.set(insertMarks).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                                @Override
                                                                                                public void onSuccess(Void unused) {
                                                                                                    Toast.makeText(getContext(), "Marks inserted successfully", Toast.LENGTH_SHORT).show();
                                                                                                }
                                                                                            }).addOnFailureListener(new OnFailureListener() {
                                                                                                @Override
                                                                                                public void onFailure(@NonNull @NotNull Exception e) {
                                                                                                    Toast.makeText(getContext(), "Failed to insert marks", Toast.LENGTH_SHORT).show();
                                                                                                }
                                                                                            });

                                                                                        }
                                                                                    }else{
                                                                                        Toast.makeText(getContext(), "Please fill all the fields", Toast.LENGTH_SHORT).show();
                                                                                    }
                                                                                }
                                                                            });
                                                                            
                                                                        } else if (checkSem.equals("4")) {
                                                                            int SemNumber = 4;
                                                                            String Semester = "Fourth Semester Result";
                                                                            String sem4sub1 = "OR";
                                                                            String sem4sub2 = "Algorithm";
                                                                            String sem4sub3 = "Linux";
                                                                            String sem4sub4 = "Web Prog";
                                                                            String sem4sub5 = "SA & SE";
                                                                            String sem4sub6 = "LAB IV";
                                                                            subject1Teacher.setText(sem4sub1);
                                                                            subject2Teacher.setText(sem4sub2);
                                                                            subject3Teacher.setText(sem4sub3);
                                                                            subject4Teacher.setText(sem4sub4);
                                                                            subject5Teacher.setText(sem4sub5);
                                                                            subject6Teacher.setText(sem4sub6);

                                                                            insertMarks.setOnClickListener(new View.OnClickListener() {
                                                                                @Override
                                                                                public void onClick(View v) {
                                                                                    String sem4mark1, sem4mark2, sem4mark3, sem4mark4, sem4mark5, sem4mark6;
                                                                                    sem4mark1 = subject1MarkTeacher.getText().toString();
                                                                                    sem4mark2 = subject2MarkTeacher.getText().toString();
                                                                                    sem4mark3 = subject3MarkTeacher.getText().toString();
                                                                                    sem4mark4 = subject4MarkTeacher.getText().toString();
                                                                                    sem4mark5 = subject5MarkTeacher.getText().toString();
                                                                                    sem4mark6 = subject6MarkTeacher.getText().toString();
                                                                                    if(!sem4mark1.isEmpty() && !sem4mark2.isEmpty() && !sem4mark3.isEmpty() && !sem4mark4.isEmpty() && !sem4mark5.isEmpty() && !sem4mark6.isEmpty()) {
                                                                                        int total = Integer.parseInt(sem4mark1)+Integer.parseInt(sem4mark2)+Integer.parseInt(sem4mark3)+Integer.parseInt(sem4mark4)+Integer.parseInt(sem4mark5)+Integer.parseInt(sem4mark6);
                                                                                        int average = total/6;
                                                                                        String grade = "";
                                                                                        if(Integer.parseInt(sem4mark1)>100 || Integer.parseInt(sem4mark2)>100 || Integer.parseInt(sem4mark3)>100 || Integer.parseInt(sem4mark4)>100 || Integer.parseInt(sem4mark5)>100 || Integer.parseInt(sem4mark6)>100){
                                                                                            Toast.makeText(getContext(), "Marks should be less than or equal to 100", Toast.LENGTH_SHORT).show();
                                                                                        }else{
                                                                                            if(average<101 && average>90){
                                                                                                grade = "A";
                                                                                            }else if(average<91 && average>80){
                                                                                                grade = "B";
                                                                                            }else if(average<81 && average>70){
                                                                                                grade = "C";
                                                                                            }else if(average<71 && average>60){
                                                                                                grade = "D";
                                                                                            }else{
                                                                                                grade = "Failed";
                                                                                            }

                                                                                            DocumentReference insertSem4Mark = fStore.collection("studentAboutInfo")
                                                                                                    .document(""+AdmissionId)
                                                                                                    .collection("Student_Marks")
                                                                                                    .document("Sem4");
                                                                                            Map<String, Object>insertMarks = new HashMap<>();
                                                                                            insertMarks.put("sub1", sem4sub1);
                                                                                            insertMarks.put("sub2", sem4sub2);
                                                                                            insertMarks.put("sub3", sem4sub3);
                                                                                            insertMarks.put("sub4", sem4sub4);
                                                                                            insertMarks.put("sub5", sem4sub5);
                                                                                            insertMarks.put("sub6", sem4sub6);
                                                                                            insertMarks.put("mark1", sem4mark1);
                                                                                            insertMarks.put("mark2", sem4mark2);
                                                                                            insertMarks.put("mark3", sem4mark3);
                                                                                            insertMarks.put("mark4", sem4mark4);
                                                                                            insertMarks.put("mark5", sem4mark5);
                                                                                            insertMarks.put("mark6", sem4mark6);
                                                                                            insertMarks.put("total", Integer.toString(total));
                                                                                            insertMarks.put("grade", grade);
                                                                                            insertMarks.put("SemOrder", SemNumber);
                                                                                            insertMarks.put("Semester", Semester);

                                                                                            insertSem4Mark.set(insertMarks).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                                @Override
                                                                                                public void onSuccess(Void unused) {
                                                                                                    Toast.makeText(getContext(), "Marks inserted successfully", Toast.LENGTH_SHORT).show();
                                                                                                }
                                                                                            }).addOnFailureListener(new OnFailureListener() {
                                                                                                @Override
                                                                                                public void onFailure(@NonNull @NotNull Exception e) {
                                                                                                    Toast.makeText(getContext(), "Failed to insert marks", Toast.LENGTH_SHORT).show();
                                                                                                }
                                                                                            });

                                                                                        }
                                                                                    }else{
                                                                                        Toast.makeText(getContext(), "Please fill all the fields", Toast.LENGTH_SHORT).show();
                                                                                    }
                                                                                }
                                                                            });
                                                                            
                                                                        }
                                                                    }else{
                                                                        Toast.makeText(getContext(), "You can't access this", Toast.LENGTH_SHORT).show();
                                                                    }
                                                                }else if(Year.equals("3") && (Dept.equals("BCA"))) {
                                                                    if (checkSem.equals("5") || (checkSem.equals("6"))) {
                                                                        getVisible();
                                                                        if (checkSem.equals("5")) {
                                                                            int SemNumber = 5;
                                                                            String Semester = "Fifth Semester Result";
                                                                            String sem5sub1 = "CN";
                                                                            String sem5sub2 = "IT";
                                                                            String sem5sub3 = "JAVA";
                                                                            String sem5sub4 = "Open Course";
                                                                            String sem5sub5 = "Mini Project";
                                                                            String sem5sub6 = "LAB v";
                                                                            subject1Teacher.setText(sem5sub1);
                                                                            subject2Teacher.setText(sem5sub2);
                                                                            subject3Teacher.setText(sem5sub3);
                                                                            subject4Teacher.setText(sem5sub4);
                                                                            subject5Teacher.setText(sem5sub5);
                                                                            subject6Teacher.setText(sem5sub6);

                                                                            insertMarks.setOnClickListener(new View.OnClickListener() {
                                                                                @Override
                                                                                public void onClick(View v) {
                                                                                    String sem5mark1, sem5mark2, sem5mark3, sem5mark4, sem5mark5, sem5mark6;
                                                                                    sem5mark1 = subject1MarkTeacher.getText().toString();
                                                                                    sem5mark2 = subject2MarkTeacher.getText().toString();
                                                                                    sem5mark3 = subject3MarkTeacher.getText().toString();
                                                                                    sem5mark4 = subject4MarkTeacher.getText().toString();
                                                                                    sem5mark5 = subject5MarkTeacher.getText().toString();
                                                                                    sem5mark6 = subject6MarkTeacher.getText().toString();
                                                                                    if(!sem5mark1.isEmpty() && !sem5mark2.isEmpty() && !sem5mark3.isEmpty() && !sem5mark4.isEmpty() && !sem5mark5.isEmpty() && !sem5mark6.isEmpty()) {
                                                                                        int total = Integer.parseInt(sem5mark1)+Integer.parseInt(sem5mark2)+Integer.parseInt(sem5mark3)+Integer.parseInt(sem5mark4)+Integer.parseInt(sem5mark5)+Integer.parseInt(sem5mark6);
                                                                                        int average = total/6;
                                                                                        String grade = "";
                                                                                        if(Integer.parseInt(sem5mark1)>100 || Integer.parseInt(sem5mark2)>100 || Integer.parseInt(sem5mark3)>100 || Integer.parseInt(sem5mark4)>100 || Integer.parseInt(sem5mark5)>100 || Integer.parseInt(sem5mark6)>100){
                                                                                            Toast.makeText(getContext(), "Marks should be less than or equal to 100", Toast.LENGTH_SHORT).show();
                                                                                        }else{
                                                                                            if(average<101 && average>90){
                                                                                                grade = "A";
                                                                                            }else if(average<91 && average>80){
                                                                                                grade = "B";
                                                                                            }else if(average<81 && average>70){
                                                                                                grade = "C";
                                                                                            }else if(average<71 && average>60){
                                                                                                grade = "D";
                                                                                            }else{
                                                                                                grade = "Failed";
                                                                                            }

                                                                                            DocumentReference insertSem5Mark = fStore.collection("studentAboutInfo")
                                                                                                    .document(""+AdmissionId)
                                                                                                    .collection("Student_Marks")
                                                                                                    .document("Sem5");
                                                                                            Map<String, Object>insertMarks = new HashMap<>();
                                                                                            insertMarks.put("sub1", sem5sub1);
                                                                                            insertMarks.put("sub2", sem5sub2);
                                                                                            insertMarks.put("sub3", sem5sub3);
                                                                                            insertMarks.put("sub4", sem5sub4);
                                                                                            insertMarks.put("sub5", sem5sub5);
                                                                                            insertMarks.put("sub6", sem5sub6);
                                                                                            insertMarks.put("mark1", sem5mark1);
                                                                                            insertMarks.put("mark2", sem5mark2);
                                                                                            insertMarks.put("mark3", sem5mark3);
                                                                                            insertMarks.put("mark4", sem5mark4);
                                                                                            insertMarks.put("mark5", sem5mark5);
                                                                                            insertMarks.put("mark6", sem5mark6);
                                                                                            insertMarks.put("total", Integer.toString(total));
                                                                                            insertMarks.put("grade", grade);
                                                                                            insertMarks.put("SemOrder", SemNumber);
                                                                                            insertMarks.put("Semester", Semester);

                                                                                            insertSem5Mark.set(insertMarks).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                                @Override
                                                                                                public void onSuccess(Void unused) {
                                                                                                    Toast.makeText(getContext(), "Marks inserted successfully", Toast.LENGTH_SHORT).show();
                                                                                                }
                                                                                            }).addOnFailureListener(new OnFailureListener() {
                                                                                                @Override
                                                                                                public void onFailure(@NonNull @NotNull Exception e) {
                                                                                                    Toast.makeText(getContext(), "Failed to insert marks", Toast.LENGTH_SHORT).show();
                                                                                                }
                                                                                            });

                                                                                        }
                                                                                    }else{
                                                                                        Toast.makeText(getContext(), "Please fill all the fields", Toast.LENGTH_SHORT).show();
                                                                                    }
                                                                                }
                                                                            });
                                                                            
                                                                        } else if (checkSem.equals("6")) {
                                                                            int SemNumber = 6;
                                                                            String Semester = "Sixth Semester Result";
                                                                            String sem6sub1 = "Cloud Computing";
                                                                            String sem6sub2 = "Android";
                                                                            String sem6sub3 = "Data Mining";
                                                                            String sem6sub4 = "Main Project";
                                                                            String sem6sub5 = "Seminar";
                                                                            String sem6sub6 = "LAB VI";
                                                                            subject1Teacher.setText(sem6sub1);
                                                                            subject2Teacher.setText(sem6sub2);
                                                                            subject3Teacher.setText(sem6sub3);
                                                                            subject4Teacher.setText(sem6sub4);
                                                                            subject5Teacher.setText(sem6sub5);
                                                                            subject6Teacher.setText(sem6sub6);

                                                                            insertMarks.setOnClickListener(new View.OnClickListener() {
                                                                                @Override
                                                                                public void onClick(View v) {
                                                                                    String sem6mark1, sem6mark2, sem6mark3, sem6mark4, sem6mark5, sem6mark6;
                                                                                    sem6mark1 = subject1MarkTeacher.getText().toString();
                                                                                    sem6mark2 = subject2MarkTeacher.getText().toString();
                                                                                    sem6mark3 = subject3MarkTeacher.getText().toString();
                                                                                    sem6mark4 = subject4MarkTeacher.getText().toString();
                                                                                    sem6mark5 = subject5MarkTeacher.getText().toString();
                                                                                    sem6mark6 = subject6MarkTeacher.getText().toString();
                                                                                    if(!sem6mark1.isEmpty() && !sem6mark2.isEmpty() && !sem6mark3.isEmpty() && !sem6mark4.isEmpty() && !sem6mark5.isEmpty() && !sem6mark6.isEmpty()) {
                                                                                        int total = Integer.parseInt(sem6mark1)+Integer.parseInt(sem6mark2)+Integer.parseInt(sem6mark3)+Integer.parseInt(sem6mark4)+Integer.parseInt(sem6mark5)+Integer.parseInt(sem6mark6);
                                                                                        int average = total/6;
                                                                                        String grade = "";
                                                                                        if(Integer.parseInt(sem6mark1)>100 || Integer.parseInt(sem6mark2)>100 || Integer.parseInt(sem6mark3)>100 || Integer.parseInt(sem6mark4)>100 || Integer.parseInt(sem6mark5)>100 || Integer.parseInt(sem6mark6)>100){
                                                                                            Toast.makeText(getContext(), "Marks should be less than or equal to 100", Toast.LENGTH_SHORT).show();
                                                                                        }else{
                                                                                            if(average<101 && average>90){
                                                                                                grade = "A";
                                                                                            }else if(average<91 && average>80){
                                                                                                grade = "B";
                                                                                            }else if(average<81 && average>70){
                                                                                                grade = "C";
                                                                                            }else if(average<71 && average>60){
                                                                                                grade = "D";
                                                                                            }else{
                                                                                                grade = "Failed";
                                                                                            }

                                                                                            DocumentReference insertSem6Mark = fStore.collection("studentAboutInfo")
                                                                                                    .document(""+AdmissionId)
                                                                                                    .collection("Student_Marks")
                                                                                                    .document("Sem6");
                                                                                            Map<String, Object>insertMarks = new HashMap<>();
                                                                                            insertMarks.put("sub1", sem6sub1);
                                                                                            insertMarks.put("sub2", sem6sub2);
                                                                                            insertMarks.put("sub3", sem6sub3);
                                                                                            insertMarks.put("sub4", sem6sub4);
                                                                                            insertMarks.put("sub5", sem6sub5);
                                                                                            insertMarks.put("sub6", sem6sub6);
                                                                                            insertMarks.put("mark1", sem6mark1);
                                                                                            insertMarks.put("mark2", sem6mark2);
                                                                                            insertMarks.put("mark3", sem6mark3);
                                                                                            insertMarks.put("mark4", sem6mark4);
                                                                                            insertMarks.put("mark5", sem6mark5);
                                                                                            insertMarks.put("mark6", sem6mark6);
                                                                                            insertMarks.put("total", Integer.toString(total));
                                                                                            insertMarks.put("grade", grade);
                                                                                            insertMarks.put("SemOrder", SemNumber);
                                                                                            insertMarks.put("Semester", Semester);

                                                                                            insertSem6Mark.set(insertMarks).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                                @Override
                                                                                                public void onSuccess(Void unused) {
                                                                                                    Toast.makeText(getContext(), "Marks inserted successfully", Toast.LENGTH_SHORT).show();
                                                                                                }
                                                                                            }).addOnFailureListener(new OnFailureListener() {
                                                                                                @Override
                                                                                                public void onFailure(@NonNull @NotNull Exception e) {
                                                                                                    Toast.makeText(getContext(), "Failed to insert marks", Toast.LENGTH_SHORT).show();
                                                                                                }
                                                                                            });

                                                                                        }
                                                                                    }else{
                                                                                        Toast.makeText(getContext(), "Please fill all the fields", Toast.LENGTH_SHORT).show();
                                                                                    }
                                                                                }
                                                                            });
                                                                            
                                                                        }
                                                                    }else{
                                                                        Toast.makeText(getContext(), "You can't access this", Toast.LENGTH_SHORT).show();
                                                                    }
                                                                }
                                                                else{
                                                                    Toast.makeText(getContext(), "You can't add marks to this sem", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }

                                                            private void getVisible() {
                                                                subject1Teacher.setVisibility(View.VISIBLE);
                                                                subject2Teacher.setVisibility(View.VISIBLE);
                                                                subject3Teacher.setVisibility(View.VISIBLE);
                                                                subject4Teacher.setVisibility(View.VISIBLE);
                                                                subject5Teacher.setVisibility(View.VISIBLE);
                                                                subject6Teacher.setVisibility(View.VISIBLE);
                                                                subject1MarkTeacher.setVisibility(View.VISIBLE);
                                                                subject2MarkTeacher.setVisibility(View.VISIBLE);
                                                                subject3MarkTeacher.setVisibility(View.VISIBLE);
                                                                subject4MarkTeacher.setVisibility(View.VISIBLE);
                                                                subject5MarkTeacher.setVisibility(View.VISIBLE);
                                                                subject6MarkTeacher.setVisibility(View.VISIBLE);
                                                                subjectsNameInInsertingMarks.setVisibility(View.VISIBLE);
                                                                MarksInInsertingMarks.setVisibility(View.VISIBLE);
                                                                insertMarks.setVisibility(View.VISIBLE);
                                                            }
                                                            private void getInvisible() {
                                                                subject1Teacher.setVisibility(View.INVISIBLE);
                                                                subject2Teacher.setVisibility(View.INVISIBLE);
                                                                subject3Teacher.setVisibility(View.INVISIBLE);
                                                                subject4Teacher.setVisibility(View.INVISIBLE);
                                                                subject5Teacher.setVisibility(View.INVISIBLE);
                                                                subject6Teacher.setVisibility(View.INVISIBLE);
                                                                subject1MarkTeacher.setVisibility(View.INVISIBLE);
                                                                subject2MarkTeacher.setVisibility(View.INVISIBLE);
                                                                subject3MarkTeacher.setVisibility(View.INVISIBLE);
                                                                subject4MarkTeacher.setVisibility(View.INVISIBLE);
                                                                subject5MarkTeacher.setVisibility(View.INVISIBLE);
                                                                subject6MarkTeacher.setVisibility(View.INVISIBLE);
                                                                subjectsNameInInsertingMarks.setVisibility(View.INVISIBLE);
                                                                MarksInInsertingMarks.setVisibility(View.INVISIBLE);
                                                                insertMarks.setVisibility(View.INVISIBLE);
                                                            }
                                                        });
                                                    }
                                                }else{
                                                    Toast.makeText(getContext(), "The admission ID which you provided doesn't exist", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    }else{
                                        Toast.makeText(getContext(), "You are not allowed to add marks to this student", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    });
                }
            }
        });

//        insertMarks.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DocumentReference documentReference = fStore.collection("Users").document(fAuth.getUid());
//                documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                    @Override
//                    public void onSuccess(DocumentSnapshot documentSnapshot) {
//                        String TeacherType = documentSnapshot.getString("TypeOfTeacher");
//                        String Year = TeacherType.substring(0,1);
//                        String Dept = TeacherType.substring(1);
//
//                    }
//                });
//            }
//        });

        return view;
    }
}
