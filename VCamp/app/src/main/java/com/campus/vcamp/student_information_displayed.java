package com.campus.vcamp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;


public class student_information_displayed extends Fragment {

    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    TextView StudentsInfoDisplayingAdmission, StudentsInfoDisplayingDepartment, StudentsInfoDisplayingYear, StudentsInfoDisplayingEmail, StudentsInfoDisplayingFullName,
            StudentsInfoDisplayingPhone, StudentsInfoDisplayingGuardianName, StudentsInfoDisplayingGuardianNumber, StudentsInfoDisplayingAddress, StudentsInfoDisplayingsemI,
            StudentsInfoDisplayingsemII, StudentsInfoDisplayingsemIII, StudentsInfoDisplayingsemIV, StudentsInfoDisplayingsemV, StudentsInfoDisplayingsemVI;
    EditText studentAdmissionIDForInformation;
    Button teacherStudentInfoDisplayingButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_student_information_displayed, container, false);

        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();

        StudentsInfoDisplayingAdmission = view.findViewById(R.id.StudentsInfoDisplayingAdmission);
        StudentsInfoDisplayingDepartment = view.findViewById(R.id.StudentsInfoDisplayingDepartment);
        StudentsInfoDisplayingYear = view.findViewById(R.id.StudentsInfoDisplayingYear);
        StudentsInfoDisplayingEmail = view.findViewById(R.id.StudentsInfoDisplayingEmail);
        StudentsInfoDisplayingFullName = view.findViewById(R.id.StudentsInfoDisplayingFullName);
        StudentsInfoDisplayingPhone = view.findViewById(R.id.StudentsInfoDisplayingPhone);
        StudentsInfoDisplayingGuardianName = view.findViewById(R.id.StudentsInfoDisplayingGuardianName);
        StudentsInfoDisplayingGuardianNumber = view.findViewById(R.id.StudentsInfoDisplayingGuardianNumber);
        StudentsInfoDisplayingAddress = view.findViewById(R.id.StudentsInfoDisplayingAddress);
        StudentsInfoDisplayingsemI = view.findViewById(R.id.StudentsInfoDisplayingsemI);
        StudentsInfoDisplayingsemII = view.findViewById(R.id.StudentsInfoDisplayingsemII);
        StudentsInfoDisplayingsemIII = view.findViewById(R.id.StudentsInfoDisplayingsemIII);
        StudentsInfoDisplayingsemIV = view.findViewById(R.id.StudentsInfoDisplayingsemIV);
        StudentsInfoDisplayingsemV = view.findViewById(R.id.StudentsInfoDisplayingsemV);
        StudentsInfoDisplayingsemVI = view.findViewById(R.id.StudentsInfoDisplayingsemVI);

        studentAdmissionIDForInformation = view.findViewById(R.id.studentAdmissionIDForInformation);
        teacherStudentInfoDisplayingButton = view.findViewById(R.id.teacherStudentInfoDisplayingButton);

        teacherStudentInfoDisplayingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String SearchId = studentAdmissionIDForInformation.getText().toString();
                if(SearchId.isEmpty()){
                    studentAdmissionIDForInformation.setError("This Field cant be empty");
                    studentAdmissionIDForInformation.requestFocus();
                }else{
                    DocumentReference documentReference = fStore.collection("Users").document(fAuth.getUid());
                    documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            String TeacherType = documentSnapshot.getString("TypeOfTeacher");
                            String Dept = TeacherType.substring(1);

                            DocumentReference getPrimaryInfo = fStore.collection("studentAboutInfo").document(""+SearchId);
                            getPrimaryInfo.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    if(documentSnapshot.exists()){
                                        String studentDepartment = documentSnapshot.getString("Department");
                                        if(studentDepartment.equalsIgnoreCase(Dept)){
                                            String Address = documentSnapshot.getString("Address");
                                            String GuardianName = documentSnapshot.getString("Guardian's Name");
                                            String GuardianNumber  = documentSnapshot.getString("Guardian's Number");
                                            String studentYear = documentSnapshot.getString("Year");

                                            DocumentReference getSem1 = fStore.collection("studentAboutInfo").document(""+SearchId)
                                                    .collection("Student_Marks")
                                                    .document("Sem1");
                                            getSem1.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                @Override
                                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                    String Sem1 = documentSnapshot.getString("total");
                                                    String grade1 = documentSnapshot.getString("grade");

                                                    DocumentReference getSem2 = fStore.collection("studentAboutInfo").document(""+SearchId)
                                                            .collection("Student_Marks")
                                                            .document("Sem2");
                                                    getSem2.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                        @Override
                                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                            String Sem2 = documentSnapshot.getString("total");
                                                            String grade2 = documentSnapshot.getString("grade");

                                                            DocumentReference getSem3 = fStore.collection("studentAboutInfo").document(""+SearchId)
                                                                    .collection("Student_Marks")
                                                                    .document("Sem3");
                                                            getSem3.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                                @Override
                                                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                                    String Sem3 = documentSnapshot.getString("total");
                                                                    String grade3 = documentSnapshot.getString("grade");

                                                                    DocumentReference getSem4 = fStore.collection("studentAboutInfo").document(""+SearchId)
                                                                            .collection("Student_Marks")
                                                                            .document("Sem4");
                                                                    getSem4.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                                        @Override
                                                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                                            String Sem4 = documentSnapshot.getString("total");
                                                                            String grade4 = documentSnapshot.getString("grade");

                                                                            DocumentReference getSem5 = fStore.collection("studentAboutInfo").document(""+SearchId)
                                                                                    .collection("Student_Marks")
                                                                                    .document("Sem5");
                                                                            getSem5.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                                                @Override
                                                                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                                                    String Sem5 = documentSnapshot.getString("total");
                                                                                    String grade5 = documentSnapshot.getString("grade");

                                                                                    DocumentReference getSem6 = fStore.collection("studentAboutInfo").document(""+SearchId)
                                                                                            .collection("Student_Marks")
                                                                                            .document("Sem6");
                                                                                    getSem6.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                                                        @Override
                                                                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                                                            String Sem6 = documentSnapshot.getString("total");
                                                                                            String grade6 = documentSnapshot.getString("grade");

                                                                                            StudentsInfoDisplayingAdmission.setText(SearchId);
                                                                                            StudentsInfoDisplayingDepartment.setText(studentDepartment);
                                                                                            StudentsInfoDisplayingYear.setText(studentYear);
                                                                                            StudentsInfoDisplayingGuardianName.setText(GuardianName);
                                                                                            StudentsInfoDisplayingGuardianNumber.setText(GuardianNumber);
                                                                                            StudentsInfoDisplayingAddress.setText(Address);
                                                                                            StudentsInfoDisplayingsemI.setText("600/"+Sem1+" ("+grade1+" Grade)");
                                                                                            StudentsInfoDisplayingsemII.setText("600/"+Sem2+" ("+grade2+" Grade)");
                                                                                            StudentsInfoDisplayingsemIII.setText("600/"+Sem3+" ("+grade3+" Grade)");
                                                                                            StudentsInfoDisplayingsemIV.setText("600/"+Sem4+" ("+grade4+" Grade)");
                                                                                            StudentsInfoDisplayingsemV.setText("600/"+Sem5+" ("+grade5+" Grade)");
                                                                                            StudentsInfoDisplayingsemVI.setText("600/"+Sem6+" ("+grade6 +" Grade)");

                                                                                            Query getSecondaryInfo = fStore.collection("Users").whereEqualTo("Admission", SearchId);
                                                                                            getSecondaryInfo.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                                                                                @Override
                                                                                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                                                                                    if(!queryDocumentSnapshots.isEmpty()){
                                                                                                        for(QueryDocumentSnapshot documentSnapshot1 : queryDocumentSnapshots){
                                                                                                            String name = documentSnapshot1.getString("FullName");
                                                                                                            String email = documentSnapshot1.getString("Email");
                                                                                                            String phone = documentSnapshot1.getString("Phone");
                                                                                                            StudentsInfoDisplayingEmail.setText(email);
                                                                                                            StudentsInfoDisplayingFullName.setText(name);
                                                                                                            StudentsInfoDisplayingPhone.setText(phone);
                                                                                                        }
                                                                                                    }
                                                                                                }
                                                                                            });

                                                                                        }
                                                                                    });

                                                                                }
                                                                            });

                                                                        }
                                                                    });

                                                                }
                                                            });

                                                        }
                                                    });

                                                }
                                            });

                                        }else{
                                            Toast.makeText(getContext(), "You don't have permissions to access this data", Toast.LENGTH_SHORT).show();
                                        }
                                    }else{
                                        Toast.makeText(getContext(), "Invalid Admission ID", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        }
                    });
                }
            }
        });

        return view;
    }
}