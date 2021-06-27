package com.campus.vcamp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class student_your_information extends Fragment {

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    TextView studentName, studentEmailID, studentAdmissionID, studentPhoneNumber, studentDepartment, studentYear, studentGuardianName, studentGuardianNumber, studentAddress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_student_your_information, container, false);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        studentName = view.findViewById(R.id.studentName);
        studentEmailID = view.findViewById(R.id.studentEmailID);
        studentAdmissionID = view.findViewById(R.id.studentAdmissionID);
        studentPhoneNumber = view.findViewById(R.id.studentPhoneNumber);
        studentDepartment = view.findViewById(R.id.studentDepartment);
        studentYear = view.findViewById(R.id.studentYear);
        studentGuardianName = view.findViewById(R.id.studentGuardianName);
        studentGuardianNumber = view.findViewById(R.id.studentGuardianNumber);
        studentAddress= view.findViewById(R.id.studentAddress);

        DocumentReference user = fStore.collection("Users").document(fAuth.getUid());

        user.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                studentName.setText(documentSnapshot.getString("FullName"));
                studentEmailID.setText(documentSnapshot.getString("Email"));
                studentPhoneNumber.setText(documentSnapshot.getString("Phone"));
                studentAdmissionID.setText(documentSnapshot.getString("Admission"));

                String admissionID = documentSnapshot.getString("Admission");
                DocumentReference aboutInfo = fStore.collection("studentAboutInfo").document(""+admissionID);
                aboutInfo.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        studentAddress.setText(documentSnapshot.getString("Address")+"\n");
                        studentDepartment.setText(documentSnapshot.getString("Department"));
                        studentGuardianName.setText(documentSnapshot.getString("Guardian's Name"));
                        studentGuardianNumber.setText(documentSnapshot.getString("Guardian's Number"));
                        studentYear.setText(documentSnapshot.getString("Year"));
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "Failed to connect to databbase", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}